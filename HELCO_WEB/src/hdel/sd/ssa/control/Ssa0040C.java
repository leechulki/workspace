package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;
import hdel.sd.ssa.domain.Ssa0020ParamVO;
import hdel.sd.ssa.domain.Ssa0040;
import hdel.sd.ssa.domain.Ssa0040ParamVO;
import hdel.sd.ssa.domain.ZSDS0034;
import hdel.sd.ssa.domain.ZSDS0042;
import hdel.sd.ssa.domain.ZSDS0073;
import hdel.sd.ssa.service.Ssa0040S;
 
import java.io.IOException; 
import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.biz.BusinessContext;
import tit.biz.BusinessException;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.mm03.ws.ZMMS011;
// import com.helco.xf.cs96.ws.ZCSS_TEST;   // 테스트용 샘플
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger; 


/**
 * 세금계산서C청구현황(Ssa0040C) Control
 * @Comment  		
 *     	1. View find 		( 세금계산서C청구현황록 조회 )     
 *        	- T-CODE             : 	ZSDR236
 *   	 	- RFC URL 			 : 	(조회) 					http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50A008CD74B5010BE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : 	(조회)			ZWEB_SD_CHN_235 		(ZSDR236)
 *   	 	- RFC OBJ NAME 		 : 	(조회)			ZSDR236   
 *   	2. View save ( 세금계산서C청구현황록 저장 )     
 *        	- T-CODE             : 	ZSDR235
 *   	 	- RFC URL 			 : 	(저장) 					http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50A29ECE55B30084E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : 	(저장)			ZWEB_SD_CHN_235_SAVE 		(ZSDR235)
 *   	 	- RFC OBJ NAME 		 : 	(저장)			ZSDR235
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.13  khs  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0040")
public class Ssa0040C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
		
	@Autowired
	private Ssa0040S service;
	
	/*-----------------------------------------------------
	 *  오더유형 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_auart")
	public View Ssa0040FindAuartView(MipParameterVO paramVO, Model model) {
		
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_auart_cd");	// UI로 return할 out dataset  
		 
		try { 
			
			Ssa0040ParamVO param = new Ssa0040ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  						// SAP CLIENT
						
			// 서비스CALL
			List<Ssa0040> list = service.find_auart(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				dsList.appendRow();  
	    		// 컬럼SET 
				dsList.setColumn(iRow, "CODE"			,list.get(iRow).getAcode());				// 오더유형코드  
				dsList.setColumn(iRow, "CODE_NAME"	,list.get(iRow).getAcode_name());		// 오더유형코드명   
			} 
			
			//dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			dsList.setId	("ds_auart_cd");
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	
	/*-----------------------------------------------------
	 *  사유코드 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_reason")
	public View Ssa0040FindReasonView(MipParameterVO paramVO, Model model) {
		
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_reason");	// UI로 return할 out dataset  
		 
		try { 
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 서비스CALL
			List<Ssa0040> list = service.find_reason();  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				dsList.appendRow();  
	    		// 컬럼SET 
				dsList.setColumn(iRow, "CODE"			,list.get(iRow).getScode());			// 사유코드  
				dsList.setColumn(iRow, "CODE_NAME"	,list.get(iRow).getScode_t());		// 코드내역   
			} 
			
			//dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			dsList.setId	("ds_reason");
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	
	/*-----------------------------------------------------
	 *  기준일에 해당하는 세금계산서C청구현황 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0040FindView(MipParameterVO paramVO, Model model) { 

		logger.debug("@@@ Ssa0040FindView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_itab = null;				// UI로 return할 out dataset
		Dataset ds_error= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)						
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				);  
		logger.debug("@@@ ds_cond.I_YMD 			===>"+DatasetUtility.getString(ds_cond,"I_YMD"			)); // 기준년월일
		logger.debug("@@@ ds_cond.I_VKBUR  		===>"+DatasetUtility.getString(ds_cond,"I_VKBUR"		)); // 부서코드
		logger.debug("@@@ ds_cond.I_VKGRP 		===>"+DatasetUtility.getString(ds_cond,"I_VKGRP"		)); // 팀코드
		logger.debug("@@@ ds_cond.I_ZKUNNR   	===>"+DatasetUtility.getString(ds_cond,"I_ZKUNNR"		)); // 담당자
		logger.debug("@@@ ds_cond.I_AUART    		===>"+DatasetUtility.getString(ds_cond,"I_AUART"		)); // 오더유형_FR
		logger.debug("@@@ ds_cond.I_RAD1    		===>"+DatasetUtility.getString(ds_cond,"I_RAD1"			)); // 총괄표출력(전체)
		logger.debug("@@@ ds_cond.I_RAD2  		===>"+DatasetUtility.getString(ds_cond,"I_RAD2"			)); // 총괄표출력(지방)
		logger.debug("@@@ ds_cond.I_RAD3  		===>"+DatasetUtility.getString(ds_cond,"I_RAD3"			)); // 프로젝트별현황
		logger.debug("@@@ ds_cond.I_CHK1 			===>"+DatasetUtility.getString(ds_cond,"I_CHK1"			)); // 미착공리스트
		logger.debug("@@@ ds_cond.I_GBN  			===>"+DatasetUtility.getString(ds_cond,"I_GBN"			)); // 웹구분
		//--------------------------------------------------------------------------------------------
		  
		try {  
			
			// RFC INPUT PARAM DECLARE 
			ZSDS0073[] 	list 		= new ZSDS0073[]{}; 			 	//  RFC output list
			ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};  	 	// 오류메세지
			
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{ 
					 DatasetUtility.getString(ds_cond, "I_AUART")  		// 오더유형_FR
					, DatasetUtility.getString(ds_cond, "I_CHK1")  		// 미착공리스트
					, DatasetUtility.getString(ds_cond, "I_GBN")  		// 웹구분 INFO
					, DatasetUtility.getString(ds_cond, "I_RAD1")  		// 총괄표출력(전체)
					, DatasetUtility.getString(ds_cond, "I_RAD2")  		// 총괄표출력(지방)
					, DatasetUtility.getString(ds_cond, "I_RAD3")  		// 프로젝트별현황
					, DatasetUtility.getString(ds_cond, "I_VKBUR")   	// 부서코드
					, DatasetUtility.getString(ds_cond, "I_VKGRP")  		// 팀코드
					, DatasetUtility.getString(ds_cond, "I_YMD")  		// 기준년월일
					, DatasetUtility.getString(ds_cond, "I_ZKUNNR")  	// 담당자
					, listMsg 													// O_ERROR INFO
					, list														// O_목록 : ZSDR236
			};
						
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_235"
												, obj, false); 
			 
			// RFC 출력구조체로 out dataset(ds) 생성
			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0073.getDataset(); 
			
			// RFC 호출결과(T_ITAB)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds_itab, stub.getOutput("T_ITAB1")); 		//조회결과리스트

			// 결과  dataset에 컬럼 추가 
			/*for (int i=0;i<ds_itab.getRowCount();i++)
			{ 
				ds_itab.setColumn(i, "I_YMD"			, DatasetUtility.getString(ds_cond, "I_YMD") );
			}  */
			
			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0040FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0040FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0040FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0040FindView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	 
		
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list_itab.getRowCount  ===>" + ds_itab.getRowCount() 	);
		logger.debug("@@@ ds_error.getRowCount	  ===>" + ds_error.getRowCount()	); 
		
		MipResultVO resultVO = new MipResultVO();
		ds_itab.setId		("ds_list_itab");  
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_itab);  	// 리스트
		resultVO.addDataset	(ds_error); 	// 오류정보
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0040FindView addAttribute end" + "");
		
		return view;
	}
	
	/*-----------------------------------------------------
	 *  세금C청구현황 저장
	 ------------------------------------------------------*/
	@RequestMapping(value="save") 
	public View Ssa0040SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ssa0021SaveProgressView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_save_cond 		= paramVO.getDataset("ds_save_cond");  	//저장 항목	
		Dataset ds_list_save 	= paramVO.getDataset("ds_list_itab");  	// 저장 리스트 
		
		// OUTPUT DATASET DECLARE 
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
				
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"+paramVO.getVariable("G_MANDT"));   
		logger.debug("@@@ ds_cond.I_YMD   	===>"+DatasetUtility.getString(ds_save_cond,"I_YMD")); 			// 기준일
		logger.debug("@@@ ds_cond.I_UNAME  	===>"+DatasetUtility.getString(ds_save_cond,"I_UNAME"));  	// 사용자
		logger.debug("@@@ ds_list_save.getRowCount	===>"+ds_save_cond.getRowCount() 		+ "");
		
		for( int irow = 0; irow < ds_list_save.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_save.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_save["+irow+"Record : "+ds_list_save.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_save, irow, ds_list_save.getColumnID(icol)));
			}  
		}
		
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset을 CLASS(ZSDS0073) 에 옮기기    
			// RFC input list 
			ZSDS0073[] 	list 	= (ZSDS0073[])CallSAPHdel.moveDs2Obj2(ds_list_save, ZSDS0073.class, "");    
			// 오류메세지
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		 
			
			logger.debug("@@@ CLASS(ZSDS0073) 에 옮기기 success" + "");  
			logger.debug("@@@ list.length() : " + list.length + ""); 
			
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{   
									  	  DatasetUtility.getString(ds_save_cond, "I_UNAME")  		// 사용자      
										, DatasetUtility.getString(ds_save_cond, "I_YMD")  			// 기준일  	
										, listMsg 															// IO_O_ERROR INFO
										, list																// IO_저장목록 : ZSDS0073  
										};   

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
											, "hdel.sd.ssa.domain.ZWEB_SD_CHN_235_SAVE"
											, obj, false); 
			System.out.println("@@@ RFC CALL  success" + "");  
			
			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0040SaveView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0040SaveView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0040SaveView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0040SaveView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	   
		 	 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_error.getRowCount  	===>" + ds_error.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO();  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_error);  	// 오류정보 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0040SaveView addAttribute end" + "");
		
		return view;
	}
}
