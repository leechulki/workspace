package hdel.sd.scl.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.scl.domain.ZSDS0067;  		// 거래선 재무구조 
import hdel.sd.scl.domain.ZSDS0068;  		// 거래선 채권현황 

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
 * 거래선 채권현황 (Scl0060C) Control
 * @Comment 
 *     	1. View find ( 거래선 채권현황 )     
 *        	- T-CODE             : ZSDR506 
 *   	 	- RFC URL 			 : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_500E1055856301DDE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : ZWEB_SD_CHN_506
 *   	 	- RFC OBJ NAME 		 : ZSDS0067, ZSDS0068 
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  김선호  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("scl0060")
public class Scl0060C {
	 
	Logger logger = Logger.getLogger(this.getClass()); 

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	
	@RequestMapping(value="find")
	public View Scl0060FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");			// 기본 검색 조건
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0067[] 	list 		= new ZSDS0067[]{};  // 거래선 목록
		ZSDS0068[] 	list_d 		= new ZSDS0068[]{};  // 거래선 채권현황  
		ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};	
		 
		// OUTPUT DATASET DECLARE
		Dataset 	ds_list		= null;				// UI로 return할 out dataset
		Dataset 	ds_list_dtl	= null;				// UI로 return할 out dataset  
		Dataset 	ds_error	= null;				// UI로 return할 error out dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			+"");  
		logger.debug("@@@ ds_cond.BUYR	===>"+DatasetUtility.getString(ds_cond,"BUYR")+"");
		logger.debug("@@@ ds_cond.RANK	===>"+DatasetUtility.getString(ds_cond,"RANK")+"");
		logger.debug("@@@ ds_cond.STADA	===>"+DatasetUtility.getString(ds_cond,"STADA")	+""); 
		
		//--------------------------------------------------------------------------------------------
		
		// RFC INPUT SET (파라메터 순서엄수)
		Object obj[] = new Object[]{
				  DatasetUtility.getString(ds_cond, "BUYR")  	// IN_거래선코드
				 ,DatasetUtility.getString(ds_cond, "RANK")  	// IN_시공능력순위
				 ,DatasetUtility.getString(ds_cond, "STADA")  	// IN_기준일
				 ,listMsg										// O_ERROR INFO 
				 ,list_d                                        // O_ZSDS0068 : 거래선 채권현황
				 ,list											// O_목록 : ZSDS0067 : 거래선 재무구조
		}; 
		 
		try { 

			logger.debug("@@@ ZWEB_SD_CHN_506 start" + "");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.scl.domain.ZWEB_SD_CHN_506"
												, obj, false);	
			
			// RFC 출력구조체로 out dataset(ds) 생성
			ds_list     = CallSAP.isJCO() ? new Dataset() : ZSDS0067.getDataset();
			ds_list_dtl = CallSAP.isJCO() ? new Dataset() : ZSDS0068.getDataset();
			
			// RFC 호출결과(T_ITAB)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds_list, stub.getOutput("T_ITAB"));  
			CallSAP.moveObj2Ds(ds_list_dtl, stub.getOutput("P_ITAB"));  

			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);
			 
			logger.debug("@@@ ZWEB_SD_CHN_506 SUCCESS!!!" + "");
			
		} catch (CommRfcException e) { 
			logger.debug("@@@ ZWEB_SD_CHN_506 CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
		} catch (Exception e) { 
			logger.debug("@@@ ZWEB_SD_CHN_506 Exception ERROR!!!" + "");
			e.printStackTrace(); 
		}	 
		 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount() + "");
		logger.debug("@@@ ds_list_dtl.getRowCount ===>" + ds_list_dtl.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId	("ds_list");
		ds_list_dtl.setId	("ds_list_dtl");
		ds_error.setId	("ds_error"); 
		resultVO.addDataset(ds_list); 		    // 거래선 재무구조
		resultVO.addDataset(ds_list_dtl); 		// 거래선 채권현황
		resultVO.addDataset(ds_error);  	    // 오류정보
		model.addAttribute("resultVO", resultVO);   

		logger.debug("@@@ addAttribute end" + "");
		
		return view;
	}
		
} 