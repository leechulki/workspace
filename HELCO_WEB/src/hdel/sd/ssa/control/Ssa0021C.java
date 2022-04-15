package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;   
import hdel.sd.ssa.domain.ZSDS0042;				// 진행사항 출력 리스트 
import hdel.sd.ssa.domain.ZWEB_SD_CHN_152_INGT;	// 진행사항 저장 리스트  
 
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
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger; 


/**
 * 매출채권 명세 및 채권 현황(청구) _진행사항관리 (Ssa0021C) Control
 * @Comment 
 * 		1. View find ( 진행사항목록 조회 )       
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FDFC4BCD68E0205E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910						
 *   	 	- RFC OPERATION NAME : 	ZWEB_SD_CHN_152_INGT 
 *   	 	- RFC OBJ NAME 		 : 	ZSDS0042
 *     	2. View save ( 진행사항목록 저장 )     
 *        	- 상동
 *     	 
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0021")
public class Ssa0021C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	 
	/*-----------------------------------------------------
	 * 진행사항 목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0021FindView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Ssa0021FindView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 	= paramVO.getDataset("ds_cond");
		
		// 호출결과 (E_TYPE) DATASET GET
		Dataset ds_result 	= paramVO.getDataset("ds_result");
		ds_result.appendRow();  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 	= null;											// UI로 return할 out dataset
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") );  
		logger.debug("@@@ ds_cond.BELNR 	===>"+DatasetUtility.getString(ds_cond,"BELNR")); 	// 청구번호(전표번호) 
		logger.debug("@@@ ds_cond.GUBUN  	===>"+DatasetUtility.getString(ds_cond,"GUBUN"));  	// 구분(Q:조회, S:저장)
		logger.debug("@@@ ds_cond.KIDNO 	===>"+DatasetUtility.getString(ds_cond,"KIDNO")); 	// 프로젝트번호
		logger.debug("@@@ ds_cond.KUNNR   	===>"+DatasetUtility.getString(ds_cond,"KUNNR"));  	// 거래선번호 
		//--------------------------------------------------------------------------------------------
		 
		try {  
			
			// RFC INPUT PARAM DECLARE 
			ZSDS0042[] 	list 	= new ZSDS0042[]{};  // RFC output list 
			ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};  // 오류메세지
			
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{ 
					  ""											// IO_진행사유 결과
					, DatasetUtility.getString(ds_cond, "BELNR")  	// I_청구번호(전표번호)      
					, DatasetUtility.getString(ds_cond, "GUBUN")  	// I_구분(Q:조회, S:저장)  
					, DatasetUtility.getString(ds_cond, "KIDNO")  	// I_프로젝트번호          
					, DatasetUtility.getString(ds_cond, "KUNNR")  	// I_거래선번호         	
					, listMsg 										// IO_O_ERROR INFO
					, list											// IO_O_목록 : ZSDS0042 : 진행사항목록  
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_INGT"
												, obj, false);  
			
			// RFC 출력구조체로 out dataset(ds) 생성
			ds_list = CallSAP.isJCO() ? new Dataset() : ZSDS0042.getDataset(); 	// 진행사항목록
   
			// RFC 호출결과(T_ITAB,T_ITAB2)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds_list, stub.getOutput("T_ITAB")); 				// 진행사항목록   
			
			// 호출결과   : 진행사항문자열
			ds_result.setColumn(0, "R_INGTEXT", (String)stub.getOutput("C_INGTEXT"));	// 진행사항문자열
						
			// 결과  dataset에 컬럼 추가
			CallSAP.makeDsCol(ds_list , "SAVE_YN"	, 1);	// 저장대상여부
			CallSAP.makeDsCol(ds_list , "CHK"		, 1);	// 선택여부
			CallSAP.makeDsCol(ds_list , "FLAG"		, 1);	// 변경여부
			CallSAP.makeDsCol(ds_list , "NEW_YN"	, 1);	// 신규여부
			CallSAP.makeDsCol(ds_list , "DISGBN_CHK", 1);	// 표시여부(1:표시,0:미표시)   
			for (int i=0;i<ds_list.getRowCount();i++)
			{ 
				ds_list.setColumn(i, "SAVE_YN"		, "N");
				ds_list.setColumn(i, "CHK"			, "0");
				ds_list.setColumn(i, "FLAG"			, "");
				ds_list.setColumn(i, "NEW_YN"		, "N"); 
				if ("X".equals(ds_list.getColumn(i, "DISGBN").toString()))  // 표기여부
				{ 
					ds_list.setColumn(i, "DISGBN_CHK"	, "1");	// 표기
				}
				else
				{ 
					ds_list.setColumn(i, "DISGBN_CHK"	, "0");	// 미표기
				} 
			} 
			
			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0021FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0021FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0021FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");  
		} catch (Exception e) { 
			logger.error("@@@ Ssa0021FindView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y"); 
		}	    
		 
		
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount  	===>" + ds_list.getRowCount()  	+ "");
		logger.debug("@@@ ds_result.getRowCount ===>" + ds_result.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO(); 
		ds_list.setId		("ds_list");  
		ds_result.setId		("ds_result");
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_list);  		// 진행사항목록
		resultVO.addDataset	(ds_result);  		// 저장결과값 (진행사항문자열)
		resultVO.addDataset	(ds_error);  		// 오류정보 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0021FindView addAttribute end" + "");
		
		return view;
	} 

	/*-----------------------------------------------------
	 *  진행사항 저장
	 ------------------------------------------------------*/
	@RequestMapping(value="save") 
	public View Ssa0021SaveProgressView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Ssa0021SaveProgressView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 		= paramVO.getDataset("ds_cond");  		// 진행사항 저장 항목
		Dataset ds_list_save 	= paramVO.getDataset("ds_list_save");  	// 진행사항 저장 리스트 

		// OUTPUT DATASET DECLARE 
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		 
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"+paramVO.getVariable("G_MANDT"));   
		logger.debug("@@@ ds_cond.BELNR 	===>"+DatasetUtility.getString(ds_cond,"BELNR")); 	// 청구번호(전표번호) 
		logger.debug("@@@ ds_cond.GUBUN  	===>"+DatasetUtility.getString(ds_cond,"GUBUN"));  	// 구분(Q:조회, S:저장)
		logger.debug("@@@ ds_cond.KIDNO 	===>"+DatasetUtility.getString(ds_cond,"KIDNO")); 	// 프로젝트번호
		logger.debug("@@@ ds_cond.KUNNR   	===>"+DatasetUtility.getString(ds_cond,"KUNNR"));  	// 거래선번호 
		logger.debug("@@@ ds_list_save.getRowCount	===>"+ds_list_save.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_list_save.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_save.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_save["+irow+"Record : "+ds_list_save.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_save, irow, ds_list_save.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
  
					 		
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset을 CLASS(ZSDS0042) 에 옮기기    
			// 진행사항 RFC input list
			ZSDS0042[] 	list 	= (ZSDS0042[])CallSAPHdel.moveDs2Obj2(ds_list_save, ZSDS0042.class, "");    
			// 오류메세지
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		 
			
			logger.debug("@@@ CLASS(ZSDS0042) 에 옮기기 success" + "");  
			logger.debug("@@@ list.length() : " + list.length + ""); 
			
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{   
										""												// IO_진행사유 결과
									  	, DatasetUtility.getString(ds_cond, "BELNR")  	// I_청구번호(전표번호)      
										, DatasetUtility.getString(ds_cond, "GUBUN")  	// I_구분(Q:조회, S:저장)  
										, DatasetUtility.getString(ds_cond, "KIDNO")  	// I_프로젝트번호          
										, DatasetUtility.getString(ds_cond, "KUNNR")  	// I_거래선번호         	
										, listMsg 										// IO_O_ERROR INFO
										, list											// IO_진행사항저장목록 : ZSDS0039  
										};   
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
											, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_INGT"
											, obj, false); 

			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0021SaveProgressView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0021SaveProgressView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0021SaveProgressView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0021SaveProgressView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	   
		 	 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_error.getRowCount  	===>" + ds_error.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO();  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_error);  	// 오류정보 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0021SaveProgressView addAttribute end" + "");
		
		return view;
		
	} 
	
}
