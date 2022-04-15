package hdel.sd.sch.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sch.domain.ComboParamVO;
import hdel.sd.sch.domain.ComboVO;
import hdel.sd.sch.domain.Sch0060;
import hdel.sd.sch.domain.Sch0060ParamVO;
import hdel.sd.sch.service.Sch0060S;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sch0060")
public class Sch0060C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sch0060S service;
	
	@RequestMapping(value="combo")
	public View combo(MipParameterVO paramVO, Model model) {
		Dataset dsComboGubun = paramVO.getDatasetList().getDataset("ds_combo_gubun");
		String strComboGubun = dsComboGubun.getColumnAsString(0, "GUBUN");
		Dataset dsCond = paramVO.getDatasetList().getDataset(strComboGubun);
		Dataset dsCombo = paramVO.getDatasetList().getDataset("ds_combo");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		List<ComboVO> listCombo = null;

		ComboParamVO param = new ComboParamVO();
		try {
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			if (strComboGubun.equals("ds_cond_ZSPEC")) {				// 사양1, 사양2, 사양3, 사양4
				param.setATNAM(dsCond.getColumnAsString(0, "ATNAM"));

				listCombo = service.selectZSPEC(param);
			} else if (strComboGubun.equals("ds_cond_CDATE")) {			// 기준년월
				param.setCDATE_YY(dsCond.getColumnAsString(0, "CDATE_YY"));
				param.setCDATE_MM(dsCond.getColumnAsString(0, "CDATE_MM"));

				listCombo = service.selectCDATE(param);
			} else if (strComboGubun.equals("ds_cond_GUBUN")) {			// 구분2, 구분3
				param.setLANG(paramVO.getVariable("G_LANG"));
				param.setCODE1(dsCond.getColumnAsString(0, "CODE1"));

				listCombo = service.selectGUBUN(param);
			} else if (strComboGubun.equals("ds_cond_JUDGE")) {			// 신용등급
				listCombo = service.selectJUDGE(param);
			} else if (strComboGubun.equals("ds_cond_ZZACTSS")) {		// 설치1
				param.setCODE1(dsCond.getColumnAsString(0, "CODE1"));
				param.setLANG(paramVO.getVariable("G_LANG"));

				listCombo = service.selectZZACTSS(param);
			} else if (strComboGubun.equals("ds_cond_TEMNO")) {			// 설치2
				listCombo = service.selectTEMNO(param);
			} else if (strComboGubun.equals("ds_cond_GUBUNC")) {		// 구분1, 수주유형, 납기경과개월수
				param.setDOMNAME(dsCond.getColumnAsString(0, "DOMNAME"));

				listCombo = service.selectGUBUNC(param);
			} else if (strComboGubun.equals("ds_cond_WWBLD")) {			// 건물용도
				listCombo = service.selectWWBLD(param);
			} else if (strComboGubun.equals("ds_cond_KUNZ1")) {			// 수주협력업체, 설치협력업체 팝업
				param.setSORTL(dsCond.getColumnAsString(0, "SORTL"));
				param.setLIFNR(dsCond.getColumnAsString(0, "LIFNR"));
				param.setNAME1(dsCond.getColumnAsString(0, "NAME1"));

				listCombo = service.selectKUNZ1(param);
			}

			CallSAPHdel.moveListToDs(dsCombo, ComboVO.class, listCombo);
			dsCombo.setId("ds_combo");

			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsCombo);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}

	@RequestMapping(value="find")
	public View main(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		List<Sch0060> listSch0060 = null;

		Sch0060ParamVO param = new Sch0060ParamVO();
		try {
			param = paramSet(param, paramVO.getVariable("G_MANDT"), dsCond);
			if (dsCond.getColumnAsString(0,"KIJUN").equals("A")) {						// 호기
				listSch0060 = service.selectHOKI(param);
			} else if (dsCond.getColumnAsString(0,"KIJUN").equals("B")) {				// 프로젝트
				listSch0060 = service.selectPROJECT(param);
			} else if (dsCond.getColumnAsString(0,"KIJUN").equals("C")) {				// 청구
				listSch0060 = service.selectCHEONG(param);
			} else if (dsCond.getColumnAsString(0,"KIJUN").equals("D")) {				// 수금
				listSch0060 = service.selectSUKEUM(param);
			}

			dsList.deleteAll();

			CallSAPHdel.moveListToDs(dsList, Sch0060.class, listSch0060);
			dsList.setId("ds_list");

			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	@RequestMapping(value="save")
	public View save(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");

		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		List<Sch0060> listSch0060 = null;

		Sch0060ParamVO param = new Sch0060ParamVO();

		try {
			if (dsCond.getColumnAsString(0,"KIJUN").equals("C")) {						// 청구
				service.mergeZSDT1010(paramVO, model, session);
			} else if (dsCond.getColumnAsString(0,"KIJUN").equals("D")) {				// 수금
				service.mergeZSDT1011(paramVO, model, session);
			}

			service.createDao(session);

			param = paramSet(param, paramVO.getVariable("G_MANDT"), dsCond);
			if (dsCond.getColumnAsString(0,"KIJUN").equals("A")) {						// 호기
				listSch0060 = service.selectHOKI(param);
			} else if (dsCond.getColumnAsString(0,"KIJUN").equals("B")) {				// 프로젝트
				listSch0060 = service.selectPROJECT(param);
			} else if (dsCond.getColumnAsString(0,"KIJUN").equals("C")) {				// 청구
				listSch0060 = service.selectCHEONG(param);
			} else if (dsCond.getColumnAsString(0,"KIJUN").equals("D")) {				// 수금
				listSch0060 = service.selectSUKEUM(param);
			}

			dsList.deleteAll();

			CallSAPHdel.moveListToDs(dsList, Sch0060.class, listSch0060);
			dsList.setId("ds_list");

			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	// Input Parameter Set
	private Sch0060ParamVO paramSet(Sch0060ParamVO param, String mandt, Dataset dsCond) {
		param.setMANDT(mandt);
		param.setCDATE(dsCond.getColumnAsString(0,"CDATE"));
		param.setPSPID(dsCond.getColumnAsString(0,"PSPID"));
		param.setBSTDK_FRYM(dsCond.getColumnAsString(0,"BSTDK_FRYM"));
		param.setBSTDK_TOYM(dsCond.getColumnAsString(0,"BSTDK_TOYM"));
		param.setVKBUR(dsCond.getColumnAsString(0,"VKBUR"));
		param.setVKGRP(dsCond.getColumnAsString(0,"VKGRP"));
		param.setZMAN(dsCond.getColumnAsString(0,"ZMAN"));
		param.setJUDGE(dsCond.getColumnAsString(0,"JUDGE"));
		param.setWAERK(dsCond.getColumnAsString(0,"WAERK"));
		param.setAUART_FR(dsCond.getColumnAsString(0,"AUART_FR"));
		param.setAUART_TO(dsCond.getColumnAsString(0,"AUART_TO"));
		param.setGUBUNC(dsCond.getColumnAsString(0,"GUBUNC"));
		param.setKUNZ1(dsCond.getColumnAsString(0,"KUNZ1"));
		param.setKUNNR(dsCond.getColumnAsString(0,"KUNNR"));
		param.setWWBLD(dsCond.getColumnAsString(0,"WWBLD"));
		param.setJMISU(dsCond.getColumnAsString(0,"JMISU"));
		if (dsCond.getColumnAsString(0,"KIJUN").equals("A")) {
			param.setZSPEC1(dsCond.getColumnAsString(0,"ZSPEC1"));
			param.setZSPEC4(dsCond.getColumnAsString(0,"ZSPEC4"));
			param.setZSPEC3(dsCond.getColumnAsString(0,"ZSPEC3"));
			param.setZSPEC5(dsCond.getColumnAsString(0,"ZSPEC5"));
			param.setZSPEC8(dsCond.getColumnAsString(0,"ZSPEC8"));
			param.setGBN(dsCond.getColumnAsString(0,"GBN"));
			param.setGUBUNA(dsCond.getColumnAsString(0,"GUBUNA"));
			param.setGUBUNB(dsCond.getColumnAsString(0,"GUBUNB"));
			param.setCLDATE1_FRYM(dsCond.getColumnAsString(0,"CLDATE1_FRYM"));
			param.setCLDATE1_TOYM(dsCond.getColumnAsString(0,"CLDATE1_TOYM"));
			param.setZZACTSS(dsCond.getColumnAsString(0,"ZZACTSS"));
			param.setTEMNO(dsCond.getColumnAsString(0,"TEMNO"));
			param.setZZSHIP1_FRYM(dsCond.getColumnAsString(0,"ZZSHIP1_FRYM"));
			param.setZZSHIP1_TOYM(dsCond.getColumnAsString(0,"ZZSHIP1_TOYM"));
			param.setLIFNR(dsCond.getColumnAsString(0,"LIFNR"));
			param.setAGING(dsCond.getColumnAsString(0,"AGING"));
		} else if (dsCond.getColumnAsString(0,"KIJUN").equals("C") || dsCond.getColumnAsString(0,"KIJUN").equals("D")) {
			param.setAMT00(dsCond.getColumnAsString(0,"AMT00"));
			param.setAMT01(dsCond.getColumnAsString(0,"AMT01"));
			param.setAMT02(dsCond.getColumnAsString(0,"AMT02"));
			param.setAMT03(dsCond.getColumnAsString(0,"AMT03"));
			param.setAMT04(dsCond.getColumnAsString(0,"AMT04"));
			param.setAMT05(dsCond.getColumnAsString(0,"AMT05"));
			param.setAMT06(dsCond.getColumnAsString(0,"AMT06"));
			param.setAMT07(dsCond.getColumnAsString(0,"AMT07"));
			param.setAMT08(dsCond.getColumnAsString(0,"AMT08"));
			param.setAMT09(dsCond.getColumnAsString(0,"AMT09"));
			param.setAMT10(dsCond.getColumnAsString(0,"AMT10"));
			param.setAMT11(dsCond.getColumnAsString(0,"AMT11"));
			param.setAMT12(dsCond.getColumnAsString(0,"AMT12"));
		}

		return param;
	}

}
