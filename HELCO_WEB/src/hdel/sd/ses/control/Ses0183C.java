package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbi.domain.SanyangPrh;
import hdel.sd.ses.domain.Ses0183;
import hdel.sd.ses.domain.Ses0183ParamVO;
import hdel.sd.ses.service.Ses0183S;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("ses0183")
public class Ses0183C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0183S service;
	
	@RequestMapping(value="find")
	public View Ses0183FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
		
		// dataset return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsList);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	private Dataset listToDataset(String sysid, String mandt, Dataset dsList, Dataset dsCond) {

		service.createDao(sqlSession.getSqlSessionBySysid(sysid));
		
		Ses0183ParamVO param = new Ses0183ParamVO();

		param.setMANDT(mandt);
		param.setFR_BDDAT(DatasetUtility.getString(dsCond, "FR_BDDAT"));
		param.setTO_BDDAT(DatasetUtility.getString(dsCond, "TO_BDDAT"));
		param.setFR_BDRSLTDT(DatasetUtility.getString(dsCond, "FR_BDRSLTDT"));
		param.setTO_BDRSLTDT(DatasetUtility.getString(dsCond, "TO_BDRSLTDT"));
		param.setVKBUR(DatasetUtility.getString(dsCond, "VKBUR"));
		param.setVKGRP(DatasetUtility.getString(dsCond, "VKGRP"));
		param.setZKUNNR(DatasetUtility.getString(dsCond, "ZKUNNR"));
		param.setKUNNR(DatasetUtility.getString(dsCond, "KUNNR"));
		param.setQTNUM(DatasetUtility.getString(dsCond, "QTNUM"));
		param.setGSNAM(DatasetUtility.getString(dsCond, "GSNAM"));
		param.setGUBUN(DatasetUtility.getString(dsCond, "GUBUN"));
		param.setFR_QTDAT(DatasetUtility.getString(dsCond, "FR_QTDAT"));
		param.setTO_QTDAT(DatasetUtility.getString(dsCond, "TO_QTDAT"));
		param.setAUART(DatasetUtility.getString(dsCond, "AUART"));
		
		List<Ses0183> listSes0183 = service.selectZSDT1055(param);

		dsList.deleteAll();

		CallSAPHdel.moveListToDs(dsList, Ses0183.class, listSes0183);
		dsList.setId("ds_list");
		
		return dsList;
	}

	@RequestMapping(value="print")
public View Ses0183PrintView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsReport = paramVO.getDatasetList().getDataset("ds_report");

		dsReport = listToDataset_print(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_LANG"), paramVO.getVariable("SQLSTR"), dsReport);
		
		// dataset return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsReport);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	private Dataset listToDataset_print(String sysid, String mandt, String lang, String sqlstr, Dataset dsReport) {
		
		String spras = "3";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, String>> paramMapList = new ArrayList<Map<String, String>>();
		String[] sqlstrs = sqlstr.split(",");
		
		service.createDao(sqlSession.getSqlSessionBySysid(sysid));

		if (lang.equals("ko"))
			spras = "3";
		else
			spras = "E";
		
		paramMap.put("MANDT", mandt);		
		paramMap.put("SPRAS", spras);
		for(int i=0; i < sqlstrs.length; i++) {
			Map<String, String> sqlstrMap = new HashMap<String, String>();
			sqlstrMap.put("QTNUMQTSER", sqlstrs[i].trim());
			paramMapList.add(sqlstrMap);
		}
		paramMap.put("sqlstrList", paramMapList);		
		
		List<Ses0183> listSes0183 = service.selectPrint(paramMap);

		dsReport.deleteAll();

		CallSAPHdel.moveListToDs(dsReport, Ses0183.class, listSes0183);
		dsReport.setId("ds_report");
		
		return dsReport;
	}
}
