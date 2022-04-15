package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0180;
import hdel.sd.ses.domain.Ses0180ParamVO;
import hdel.sd.ses.service.Ses0180S;
import hdel.sd.sso.domain.Sso0060ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0180")
public class Ses0180C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0180S service;
	
	@RequestMapping(value="find")
	public View Ses0180FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
		
		// dataset�� return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsList);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	
	
	@RequestMapping(value="find0181")
	public View Ses0180Find0181View(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		dsList = listToDataset0181(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
		
		// dataset�� return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsList);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	
	@RequestMapping(value="save")
	public View Ses0180Merge(MipParameterVO paramVO, Model model) {
		// INPUT DATASET GET
		Dataset dsList   = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond   = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		MipResultVO resultVO = new MipResultVO();

		try {
			// ���� ȣ��
			service.save(paramVO, model, session);

		} catch (Exception e ) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
	@RequestMapping(value="delete")
	public View Ses0180Delete(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		try {
			
			resultVO = service.delete(paramVO, model, session);

		} catch (Exception e ) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}

		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
	private Dataset listToDataset(String sysid, String mandt, Dataset dsList, Dataset dsCond) {

		service.createDao(sqlSession.getSqlSessionBySysid(sysid));
		
		Ses0180ParamVO param = new Ses0180ParamVO();

		param.setMandt(mandt);
		param.setQtdatfr(DatasetUtility.getString(dsCond, "QTDAT_FR"));
		param.setQtdatto(DatasetUtility.getString(dsCond, "QTDAT_TO"));
		param.setZestdatfr(DatasetUtility.getString(dsCond, "ZESTDAT_FR"));
		param.setZestdatto(DatasetUtility.getString(dsCond, "ZESTDAT_TO"));
		param.setVkbur(DatasetUtility.getString(dsCond, "VKBUR"));
		param.setVkgrp(DatasetUtility.getString(dsCond, "VKGRP"));
		param.setZprgflg(DatasetUtility.getString(dsCond, "ZPRGFLG"));
		param.setZkunnr(DatasetUtility.getString(dsCond, "ZKUNNR"));
		param.setZpstat(DatasetUtility.getString(dsCond, "ZPSTAT"));
		param.setBDDATFR(DatasetUtility.getString(dsCond, "BDDAT_FR"));
		param.setBDDATTO(DatasetUtility.getString(dsCond, "BDDAT_TO"));
		List<Ses0180> listSes0180 = service.selectZSDT1055(param);

		dsList.deleteAll();

		CallSAPHdel.moveListToDs(dsList, Ses0180.class, listSes0180);
		dsList.setId("ds_list");
		
		return dsList;
	}

	
	private Dataset listToDataset0181(String sysid, String mandt, Dataset dsList, Dataset dsCond) {

		service.createDao(sqlSession.getSqlSessionBySysid(sysid));
		
		Ses0180ParamVO param = new Ses0180ParamVO();

		param.setMandt(mandt);
		param.setZkunnr(DatasetUtility.getString(dsCond, "ZKUNNR"));
		
		List<Ses0180> listSes0180 = service.selectZSDT0181(param);

		dsList.deleteAll();

		CallSAPHdel.moveListToDs(dsList, Ses0180.class, listSes0180);
		dsList.setId("ds_list");
		
		return dsList;
	}
	
}
