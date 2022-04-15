package hdel.sd.scl.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.scl.domain.ZSDS_PJT;  		// 프로젝트별 수금관리대장 (Scl0040)
import hdel.sd.scl.domain.ZSDS0014;  		// 수금관리대장 마스터정보 (Scl00401-팝업)
import hdel.sd.scl.domain.ZSDS0015;  		// 수금관리대장 상세정보    (Scl00401-팝업)
import hdel.sd.ssa.domain.ZSDT0053;
import hdel.sd.com.domain.RANGE_C10;		// SO, 거래선용 코드범위선택 DATASET

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
// import com.helco.xf.cs96.ws.ZCSS_TEST;   // 테스트용 샘플
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger;  


/**
 * 프로젝트별 수금대장(Scl0040C) Control
 * @Comment 
 *     	1. View find ( 프로젝트별 수금목록 조회 )     
 *        	- T-CODE             : ZSDR117 
 *   	 	- RFC URL 			 : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FA8CB6DB8C3013FE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : ZWEB_SD_PROJECT
 *   	 	- RFC OBJ NAME 		 : ZSDS_PJT 
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("scl0040")
public class Scl0040C {
	 
	Logger logger = Logger.getLogger(this.getClass()); 

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	
	@RequestMapping(value="find")
	public View Scl0040FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Scl0040FindView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");			// 기본 검색 조건
		Dataset ds_cond_so_list 	= paramVO.getDataset("ds_cond_so_list");	// SO번호목록 조건
		Dataset ds_cond_buyr_list 	= paramVO.getDataset("ds_cond_buyr_list");	// 거래선목록 조건 
		  
		// OUTPUT DATASET DECLARE
		Dataset ds_list	= null;											// UI로 return할 out dataset  
		Dataset ds_error= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 			+"");  
		logger.debug("@@@ ds_cond.FR_YMD	===>"+DatasetUtility.getString(ds_cond,"FR_YMD")+"");
		logger.debug("@@@ ds_cond.TO_YMD	===>"+DatasetUtility.getString(ds_cond,"TO_YMD")+"");
		logger.debug("@@@ ds_cond.FR_SO		===>"+DatasetUtility.getString(ds_cond,"FR_SO")	+"");
		logger.debug("@@@ ds_cond.TO_SO		===>"+DatasetUtility.getString(ds_cond,"TO_SO")	+"");
		logger.debug("@@@ ds_cond.FR_VB		===>"+DatasetUtility.getString(ds_cond,"FR_VB")	+"");
		logger.debug("@@@ ds_cond.TO_VB		===>"+DatasetUtility.getString(ds_cond,"TO_VB")	+"");
		logger.debug("@@@ ds_cond.FR_VG		===>"+DatasetUtility.getString(ds_cond,"FR_VG")	+"");
		logger.debug("@@@ ds_cond.TO_VG		===>"+DatasetUtility.getString(ds_cond,"TO_VG")	+"");
		logger.debug("@@@ ds_cond.SMAN		===>"+DatasetUtility.getString(ds_cond,"SMAN")	+"");
		logger.debug("@@@ ds_cond.BUYR		===>"+DatasetUtility.getString(ds_cond,"BUYR")	+"");
		logger.debug("@@@ ds_cond.AUART		===>"+DatasetUtility.getString(ds_cond,"AUART")	+"");
		logger.debug("@@@ ds_cond.SPART		===>"+DatasetUtility.getString(ds_cond,"SPART")	+"");
		logger.debug("@@@ ds_cond.STADA		===>"+DatasetUtility.getString(ds_cond,"STADA")	+"");
		logger.debug("@@@ ds_cond.BSTNK		===>"+DatasetUtility.getString(ds_cond,"BSTNK")	+"");  
		logger.debug("@@@ ds_cond_so_list.getRowCount	===>"+ds_cond_so_list.getRowCount() + "");
		for( int irow = 0; irow < ds_cond_so_list.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_cond_so_list.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_cond_so_list["+irow+"Record : "
								+ ds_cond_so_list.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_cond_so_list, irow, ds_cond_so_list.getColumnID(icol)));
			}  
		}
		logger.debug("@@@ ds_cond_buyr_list.getRowCount	===>"+ds_cond_buyr_list.getRowCount() 	+ "");
		for( int irow = 0; irow < ds_cond_buyr_list.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_cond_buyr_list.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_cond_buyr_list["+irow+"Record : "
								+ ds_cond_buyr_list.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_cond_buyr_list, irow, ds_cond_buyr_list.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
		  
		try {  

			// RFC INPUT PARAM DECLARE 
			ZSDS_PJT[] 	list 		= new ZSDS_PJT[]{};  // 프로젝트별 수금관리목록 RFC output list
			ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};	
			RANGE_C10[] list_so 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_so_list	, RANGE_C10.class, "");	// SO조건목록
			RANGE_C10[] list_buyr 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_buyr_list, RANGE_C10.class, "");	// 거래선조건목록
			 
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{
					  DatasetUtility.getString(ds_cond, "AUART")  	// IN_오더유형
					 ,DatasetUtility.getString(ds_cond, "BSTNK")  	// IN_현장명
					 ,DatasetUtility.getString(ds_cond, "BUYR")  	// IN_지급처
					 ,DatasetUtility.getString(ds_cond, "FR_SO")  	// IN_S/O _FR
					 ,DatasetUtility.getString(ds_cond, "FR_VB")  	// IN_부서코드 _FR
					 ,DatasetUtility.getString(ds_cond, "FR_VG")  	// IN_팀코드 _FR
					 ,DatasetUtility.getString(ds_cond, "FR_YMD")  	// IN_수주일 _FR
					 ,DatasetUtility.getString(ds_cond, "SMAN")  	// IN_담당자
					 ,DatasetUtility.getString(ds_cond, "SPART")  	// IN_제품군
					 ,DatasetUtility.getString(ds_cond, "STADA")  	// IN_기준일
					 ,DatasetUtility.getString(ds_cond, "TO_SO")  	// IN_S/O _TO
					 ,DatasetUtility.getString(ds_cond, "TO_VB") 	// IN_부서코드 _TO
					 ,DatasetUtility.getString(ds_cond, "TO_VG")  	// IN_팀코드 _TO
					 ,DatasetUtility.getString(ds_cond, "TO_YMD")  	// IN_수주일 _TO
					, listMsg										// O_ERROR INFO
					, list_buyr										// 거래선조건목록
					, list_so										// SO조건목록
					, list											// O_목록 : ZSDS_PJT : 프로젝트목록
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.scl.domain.ZWEB_SD_PROJECT"
												, obj, false);	
			
			// RFC 출력구조체로 out dataset(ds) 생성
			ds_list = CallSAP.isJCO() ? new Dataset() : ZSDS_PJT.getDataset();
			
			// RFC 호출결과(T_ITAB)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds_list, stub.getOutput("T_ITAB"));  

			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Scl0040FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Scl0040FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Scl0040FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Scl0040FindView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	 
		 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		("ds_list");
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_list); 		// 프로젝트목록
		resultVO.addDataset	(ds_error);  	// 오류정보
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Scl0040FindView addAttribute end" + "");
		
		return view;
	}
	 
	
} 