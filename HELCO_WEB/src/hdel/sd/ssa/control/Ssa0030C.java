package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.com.domain.RANGE_C10;
import hdel.sd.com.domain.RANGE_C30;
import hdel.sd.ssa.domain.ZSDS0032;  		// [SD]매출채권 AGING SCHEDULE 

import java.io.IOException;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger; 


/**
 * [SD]매출채권 AGING SCHEDULE (Ssa0030C) Control
 * @Comment      
 *     	1. View find 		( 매출채권 명세 및 채권현황(청구별) 목록 조회 )     
 *        	- T-CODE             : 	ZSDR183 
 *   	 	- RFC URL 			 : 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FB22EEE04670046E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910				
 *   	 	- RFC OPERATION NAME : 	ZWEB_SD_CHN_183
 *   	 	- RFC OBJ NAME 		 : 	ZSDS0032 
 *   
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0030")
public class Ssa0030C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	
	@RequestMapping(value="find")
	public View Ssa0030CFindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");
		Dataset ds_cond_prjt_list 	= paramVO.getDataset("ds_cond_prjt_list");	// 프로젝트번호목록 조건
		Dataset ds_cond_buyr_list 	= paramVO.getDataset("ds_cond_buyr_list");	// 거래선목록 조건 
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0032[] 	list 		= new ZSDS0032[]{};  // [SD]매출채권 AGING SCHEDULE RFC output list
		ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};
		RANGE_C30[] list_prjt 	= (RANGE_C30[])CallSAPHdel.moveDs2Obj2(ds_cond_prjt_list, RANGE_C30.class, "");	// 프로젝트조건목록
		RANGE_C10[] list_buyr 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_buyr_list, RANGE_C10.class, "");	// 거래선조건목록
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset 
		Dataset 	ds_error 	= null;			// UI로 return할 out dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				+"");  
		logger.debug("@@@ ds_cond.BS_YMD	===>"+DatasetUtility.getString(ds_cond,"BS_YMD")	+""); 	// 기준일자
		logger.debug("@@@ ds_cond.FR_PAYER	===>"+DatasetUtility.getString(ds_cond,"FR_PAYER")	+"");	// 고객코드_FR
		logger.debug("@@@ ds_cond.FR_PJT	===>"+DatasetUtility.getString(ds_cond,"FR_PJT")  	+""); 	// 프로젝트_FR
		logger.debug("@@@ ds_cond.FR_VB		===>"+DatasetUtility.getString(ds_cond,"FR_VB")   	+""); 	// 부서코드_FR
		logger.debug("@@@ ds_cond.FR_VG		===>"+DatasetUtility.getString(ds_cond,"FR_VG")   	+""); 	// 팀코드_FR
		logger.debug("@@@ ds_cond.MG_YMD	===>"+DatasetUtility.getString(ds_cond,"MG_YMD")  	+"");	// 마감일자
		logger.debug("@@@ ds_cond.PRT_GBN	===>"+DatasetUtility.getString(ds_cond,"PRT_GBN") 	+""); 	// 출력구분(O,C)
		logger.debug("@@@ ds_cond.TO_PAYER	===>"+DatasetUtility.getString(ds_cond,"TO_PAYER")	+""); 	// 고객코드_TO
		logger.debug("@@@ ds_cond.TO_PJT	===>"+DatasetUtility.getString(ds_cond,"TO_PJT")  	+""); 	// 프로젝트_TO
		logger.debug("@@@ ds_cond.TO_VB		===>"+DatasetUtility.getString(ds_cond,"TO_VB")   	+""); 	// 부서코드_TO
		logger.debug("@@@ ds_cond.TO_VG		===>"+DatasetUtility.getString(ds_cond,"TO_VG")   	+"");	// 팀코드_TO
		logger.debug("@@@ ds_cond.YMD_GBN	===>"+DatasetUtility.getString(ds_cond,"YMD_GBN") 	+""); 	// 일자구분(D/M)
		//--------------------------------------------------------------------------------------------
		
		// RFC INPUT SET (파라메터 순서엄수)
		Object obj[] = new Object[]{
				  DatasetUtility.getString(ds_cond, "BS_YMD") 	// 기준일자     
				 ,DatasetUtility.getString(ds_cond, "FR_PAYER")	// 고객코드_FR  
				 ,DatasetUtility.getString(ds_cond, "FR_PJT")  	// 프로젝트_FR  
				 ,DatasetUtility.getString(ds_cond, "FR_VB")   	// 부서코드_FR  
				 ,DatasetUtility.getString(ds_cond, "FR_VG")   	// 팀코드_FR    
				 ,DatasetUtility.getString(ds_cond, "MG_YMD")  	// 마감일자     
				 ,DatasetUtility.getString(ds_cond, "PRT_GBN") 	// 출력구분(O,C)
				 ,DatasetUtility.getString(ds_cond, "TO_PAYER")	// 고객코드_TO  
				 ,DatasetUtility.getString(ds_cond, "TO_PJT")  	// 프로젝트_TO  
				 ,DatasetUtility.getString(ds_cond, "TO_VB")   	// 부서코드_TO  
				 ,DatasetUtility.getString(ds_cond, "TO_VG")   	// 팀코드_TO    
				 ,DatasetUtility.getString(ds_cond, "YMD_GBN") 	// 일자구분(D/M) 
				, listMsg										// 오류메세지
				, list_prjt										// 프로젝트조건목록
				, list_buyr										// 거래선조건목록
				, list											// O_목록 : ZSDS0032
		}; 
		 
		try { 

			logger.debug("@@@ ZWEB_SD_CHN_183 start" + "");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_183"
												, obj, false);	 
			
			logger.debug("@@@ 11111111111111111111111" + "");
			
			// RFC 출력구조체로 out dataset(ds) 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0032.getDataset(); 
			
			// RFC 호출결과(T_ITAB)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));  
			
			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			
			logger.debug("@@@ ZWEB_SD_CHN_183 SUCCESS!!!" + "");
			
		} catch (CommRfcException e) { 
			logger.debug("@@@ ZWEB_SD_CHN_183 CommRfcException ERROR!!!" + "");
			e.printStackTrace();  
		} catch (Exception e) { 
			logger.debug("@@@ ZWEB_SD_CHN_183 Exception ERROR!!!" + "");
			e.printStackTrace(); 
		}	 
		 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount 	===>" + ds.getRowCount() + "");
		logger.debug("@@@ ds_error.getRowCount 	===>" + ds_error.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds.setId("ds_list");  
		ds_error.setId("ds_error");
		resultVO.addDataset(ds); 			// 매출채권 AGING SCHEDULE RFC output list
		resultVO.addDataset(ds_error);  	// 오류정보
		model.addAttribute("resultVO", resultVO);   
		 
		logger.debug("@@@ addAttribute end" + "");
		
		return view;
	}

}
	