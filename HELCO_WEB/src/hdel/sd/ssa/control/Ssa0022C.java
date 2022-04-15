package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;   
import hdel.sd.ssa.domain.ZSDS0048;				// 법조치 출력 리스트 
import hdel.sd.ssa.domain.ZSDS0049;				// 법조치문자열 출력 구조체
import hdel.sd.ssa.domain.ZWEB_SD_CHN_152_LAW;	// 법조치 저장 리스트  
 
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
 * 매출채권 명세 및 채권 현황(청구) _법조치관리 (Ssa0022C) Control
 * @Comment 
 * 		1. View find ( 법조치목록 조회 )       
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FDFC4BCD68E0205E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910									
 *   	 	- RFC OPERATION NAME : 	ZWEB_SD_CHN_152_LAW 
 *   	 	- RFC OBJ NAME 		 : 	ZSDS0048(조회,저장목록), ZSDS0049(법조치별 최종자료)
 *     	2. View save ( 법조치 저장 )     
 *        	- 상동
 *     	 
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0022")
public class Ssa0022C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	 
	/*-----------------------------------------------------
	 * 법조치 목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0022FindView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Ssa0022FindView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 	= paramVO.getDataset("ds_cond"); 
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 	= null;			// UI로 return할 out dataset
		Dataset ds_last_law	= null;			// UI로 return할 out dataset   
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") );  
		logger.debug("@@@ ds_cond.PSPID 	===>"+DatasetUtility.getString(ds_cond,"PSPID")); 	// 청구번호(전표번호) 
		logger.debug("@@@ ds_cond.GUBUN  	===>"+DatasetUtility.getString(ds_cond,"GUBUN"));  	// 구분(Q:조회, S:저장)
		//--------------------------------------------------------------------------------------------
		 
		try {  
			
			// RFC INPUT PARAM DECLARE 
			ZSDS0048[] 	list48 	= new ZSDS0048[]{};  	// RFC output list    
			ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};  	// 오류메세지 
			
			// RFC INPUT SET (파라메터 순서엄수)  
			Object obj[] = new Object[]{ 
					  new ZSDS0049()								// IO_법조치별 최종일자 데이타 문자열 : ZSDS0049
					, DatasetUtility.getString(ds_cond, "GUBUN")  	// I_구분(Q:조회, S:저장)   
					, DatasetUtility.getString(ds_cond, "PSPID")  	// I_공사번호 (프로젝트번호)    
					, listMsg 										// IO_O_ERROR INFO
					, list48										// IO_법조치목록 : ZSDS0048
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_LAW"
												, obj, false);  
			
			// RFC 출력구조체로 out dataset(ds) 생성
			ds_list 	= CallSAP.isJCO() ? new Dataset() : ZSDS0048.getDataset(); // 법조치목록
			ds_last_law	= CallSAP.isJCO() ? new Dataset() : ZSDS0049.getDataset(); // 법조치별 최종일자 데이타 문자열
   
			// RFC 호출결과(T_ITAB)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds_list		, stub.getOutput("T_ITAB")); 	// 법조치목록
			CallSAP.moveObj2Ds(ds_last_law	, stub.getOutput("CS_ITAB")); 	// 법조치별 최종일자 데이타 문자열
			
			// 결과  dataset에 컬럼 추가 
			CallSAP.makeDsCol(ds_list , "SAVE_YN"	, 1);	// 저장대상여부
			CallSAP.makeDsCol(ds_list , "CHK"		, 1);	// 선택여부
			CallSAP.makeDsCol(ds_list , "FLAG"		, 1);	// 변경여부
			CallSAP.makeDsCol(ds_list , "NEW_YN"	, 1);	// 신규여부   
			for (int i=0;i<ds_list.getRowCount();i++)
			{ 
				ds_list.setColumn(i, "SAVE_YN"		, "N");
				ds_list.setColumn(i, "CHK"			, "0");
				ds_list.setColumn(i, "FLAG"			, "");
				ds_list.setColumn(i, "NEW_YN"		, "N");  
				// 회수계획일 8자리로 재설정
				ds_list.setColumn(i, "MEDAT", CallSAPHdel.dateReplace(ds_list.getColumnAsString(i, "MEDAT")));
			} 
			
			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0022FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0022FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0022FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();      
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0022FindView Exception ERROR!!!" + "");
			e.printStackTrace();    
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount  		===>" + ds_list.getRowCount()  		+ "");
		logger.debug("@@@ ds_last_law.getRowCount	===>" + ds_last_law.getRowCount()  	+ "");
		logger.debug("@@@ ds_error.getRowCount		===>" + ds_error.getRowCount() 		+ "");  
		MipResultVO resultVO = new MipResultVO(); 
		ds_list.setId		("ds_list");
		ds_last_law.setId	("ds_last_law");  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_list);  		// 법조치목록
		resultVO.addDataset	(ds_last_law);  	// 법조치별 최종일자 데이타 문자열
		resultVO.addDataset	(ds_error);  		// 오류정보 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0022FindView addAttribute end" + "");
		
		return view;
	} 

	/*-----------------------------------------------------
	 *  법조치 저장
	 ------------------------------------------------------*/
	@RequestMapping(value="save") 
	public View Ssa0022SaveProgressView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Ssa0022SaveProgressView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 		= paramVO.getDataset("ds_cond");  		// 법조치 저장 항목
		Dataset ds_list_save 	= paramVO.getDataset("ds_list_save");  	// 법조치 저장 리스트 
		
		// OUTPUT DATASET DECLARE 
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		 
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"+paramVO.getVariable("G_MANDT"));   
		logger.debug("@@@ ds_cond.PSPID 	===>"+DatasetUtility.getString(ds_cond,"PSPID")); 	// 청구번호(전표번호)
		logger.debug("@@@ ds_cond.GUBUN  	===>"+DatasetUtility.getString(ds_cond,"GUBUN"));  	// 구분(Q:조회, S:저장) 
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
			// input dataset을 CLASS(ZSDS0048) 에 옮기기    
			// 법조치 RFC input list 
			ZSDS0048[]	list48 	= (ZSDS0048[])CallSAPHdel.moveDs2Obj2(ds_list_save, ZSDS0048.class, ""); // RFC input list   
			ZSDS0049	list49 	= new ZSDS0049();  		
			ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};  	// 오류메세지  
		
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{ 
					  list49										// IO_법조치별 최종일자 데이타 문자열 : ZSDS0049
					, DatasetUtility.getString(ds_cond, "GUBUN")  	// I_구분(Q:조회, S:저장)   
					, DatasetUtility.getString(ds_cond, "PSPID")  	// I_공사번호 (프로젝트번호)    
					, listMsg 										// IO_O_ERROR INFO
					, list48										// IO_법조치목록 : ZSDS0048
			};  
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_LAW"
												, obj, false); 

			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);  
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0022SaveProgressView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0022SaveProgressView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0022SaveProgressView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0022SaveProgressView Exception ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	   
		 	 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_error.getRowCount  	===>" + ds_error.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO();  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_error);  		// 오류정보 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0022SaveProgressView addAttribute end" + "");
		
		return view;
		
	} 
	
}
