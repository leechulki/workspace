package hdel.sd.sbp.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.sbp.domain.ComboParamVO;
import hdel.sd.sbp.domain.ComboVO;
import hdel.sd.sbp.domain.Sbp0190;
import hdel.sd.sbp.domain.Sbp0190ParamVO;
import hdel.sd.sbp.service.Sbp0190S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbp0190")
public class Sbp0190C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0190S service;
	
	@RequestMapping(value="combo")
	public View combo(MipParameterVO paramVO, Model model) {
		Dataset dsComboGubun = paramVO.getDatasetList().getDataset("ds_combo_gubun");
		String strComboGubun = dsComboGubun.getColumnAsString(0, "GUBUN");
		Dataset dsCond = paramVO.getDatasetList().getDataset(strComboGubun);
		Dataset dsCombo = paramVO.getDatasetList().getDataset("ds_combo");
		Dataset dsGtypes = paramVO.getDataset("ds_gtypes");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		List<ComboVO> listCombo = null;

		ComboParamVO param = new ComboParamVO();
		try {
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			if (strComboGubun.equals("ds_cond_soable")) {					// 수주가능성
				param.setDOMNAME(dsCond.getColumnAsString(0, "DOMNAME"));

				listCombo = service.selectDD07T(param);
			} else if (strComboGubun.equals("ds_cond_gtype")) {				// 기종
				listCombo = service.selectGtype();

				dsGtypes.deleteAll();

				ZGTypes zgtype = new ZGTypes();
				CallSAPHdel.moveListToDs(dsGtypes, ZGType.class, zgtype.find());
				dsGtypes.setId("ds_gtypes");
			} else if (strComboGubun.equals("ds_cond_region")) {			// 설치
				param.setLANG(paramVO.getVariable("G_LANG"));

				listCombo = service.selectREGION(param);
			}

			CallSAPHdel.moveListToDs(dsCombo, ComboVO.class, listCombo);
			dsCombo.setId("ds_combo");

			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsCombo);
			if (strComboGubun.equals("ds_cond_gtype")) {
				resultVO.addDataset(dsGtypes);
			}
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@RequestMapping(value="find")
	public View Sbp0190FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsList);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	@RequestMapping(value="save")
	public View Sbp0190Merge(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		// 결과정보  DATASET GET
		Dataset dsResult = paramVO.getDataset("ds_result");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		try {
			// 저장 호출
			service.save(paramVO, model, session);

			dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
			
			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@RequestMapping(value="delete")
	public View Sbp0190Delete(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		// 결과정보  DATASET GET
		Dataset dsResult = paramVO.getDataset("ds_result");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		try {
			// 저장 호출
			service.delete(paramVO, model, session);

			dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
			
			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	private Dataset listToDataset(String sysid, String mandt, Dataset dsList, Dataset dsCond) {
		service.createDao(sqlSession.getSqlSessionBySysid(sysid));
		
		Sbp0190ParamVO param = new Sbp0190ParamVO();
		param.setMANDT(mandt);
		param.setFR_ZPYM(DatasetUtility.getString(dsCond, "FR_ZPYM"));
		param.setTO_ZPYM(DatasetUtility.getString(dsCond, "TO_ZPYM"));
		param.setZPYM(DatasetUtility.getString(dsCond, "ZPYM"));
		param.setSPART(DatasetUtility.getString(dsCond, "SPART"));
		param.setZKUNNR(DatasetUtility.getString(dsCond, "ZKUNNR"));
		param.setVKBUR(DatasetUtility.getString(dsCond, "VKBUR"));
		param.setVKGRP(DatasetUtility.getString(dsCond, "VKGRP"));
		param.setZMPFLG(DatasetUtility.getString(dsCond, "ZMPFLG"));
		param.setSORLT(DatasetUtility.getString(dsCond, "SORLT"));
		
		List<Sbp0190> listSbp0190 = service.selectZSDT1005(param);

		dsList.deleteAll();

		CallSAPHdel.moveListToDs(dsList, Sbp0190.class, listSbp0190);
		dsList.setId("ds_list");
		
		return dsList;
	}
	
}
