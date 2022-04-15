package hdel.sd.sch.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.service.ComboS;
import hdel.sd.sch.domain.ZSDS0069;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import org.apache.log4j.Logger;  

@Controller
@RequestMapping("sch0010")
public class Sch0010C {

	Logger logger = Logger.getLogger(this.getClass()); 
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private ComboS service;
	
	@RequestMapping(value="find")
	public View main(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");

		// RFC INPUT PARAM DECLARE 
		ZQMSEMSG[] 	listMsg   = new ZQMSEMSG[]{};
		ZSDS0069[] 	list 	  = new ZSDS0069[]{};  // 프로젝트별 수금관리목록 RFC output list
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset

		logger.debug("@@@ ds_cond.I_FR_SO	===>"+DatasetUtility.getString(ds_cond,"FR_SO")+"");
		logger.debug("@@@ ds_cond.I_CMD	===>"+DatasetUtility.getString(ds_cond,"CMD")	+"");
		logger.debug("@@@ ds_cond.I_VKBUR	===>"+DatasetUtility.getString(ds_cond,"VKBUR")	+"");
		logger.debug("@@@ ds_cond.I_VKGRP	===>"+DatasetUtility.getString(ds_cond,"VKGRP")	+"");
		logger.debug("@@@ ds_cond.I_SMAN	===>"+DatasetUtility.getString(ds_cond,"SMAN")	+"");
		
		// RFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(ds_cond, "CMD")		// CMD
				, DatasetUtility.getString(ds_cond, "FR_SO")		// SO
				, DatasetUtility.getString(ds_cond, "SMAN")
				, DatasetUtility.getString(ds_cond, "VKBUR")		
				, DatasetUtility.getString(ds_cond, "VKGRP")
				, listMsg
				, list
		};

		try {
			
			// RFC CALL
			
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sch.domain.ZWEB_SD_CHN_BILL", obj, false);

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0069.getDataset();
			// RFC 호출결과를 out dataset에 옮기기  
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
		} catch (CommRfcException e) {
			logger.debug("@@@ CommRfcException ERROR!!!" + "");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("@@@ Exception ERROR!!!" + "");
			e.printStackTrace();
		}
		
		// RFC 출력 dataset을 return

		logger.debug("@@@ ds_list.getRowCount ===>" + ds.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds.setId("ds_list"); 
		dsError.setId	("ds_error"); 
		resultVO.addDataset(ds);
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="bill")
	public View main_Bill(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset ds_cond2 = paramVO.getDataset("ds_cond2");

		// RFC INPUT PARAM DECLARE 
		ZQMSEMSG[] 	listMsg   = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset

		logger.debug("@@@ ds_cond2.I_FR_SO	 ===>"+DatasetUtility.getString(ds_cond2,"FR_SO")    +"");
		logger.debug("@@@ ds_cond2.I_FKDAT	 ===>"+DatasetUtility.getString(ds_cond2,"FKDAT")	+"");
		logger.debug("@@@ ds_cond2.I_BILLDAT ===>"+DatasetUtility.getString(ds_cond2,"BILLDAT")	+""); 
		
		// RFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(ds_cond2, "BILLDAT")		// 실제 청구일
				, DatasetUtility.getString(ds_cond2, "FKDAT")		// 청구계획일
				, DatasetUtility.getString(ds_cond2, "FR_SO")		// CMD
				, listMsg
		};

		try {
			
			// RFC CALL

			logger.debug("@@@ CALLSAP START @@@ ");
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sch.domain.ZWEB_SD_CHN_BILLING", obj, false);
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
		} catch (CommRfcException e) {
			logger.debug("@@@ CommRfcException ERROR!!!" + "");
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("@@@ Exception ERROR!!!" + "");
			e.printStackTrace();
		}
		
		// RFC 출력 dataset을 return

		MipResultVO resultVO = new MipResultVO();
		dsError.setId	("ds_error"); 
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
}
