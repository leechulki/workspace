package hdel.sd.scl.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.scl.domain.ZSDS_PJT;  		// 프로젝트별 수금관리대장 (Scl0040)
import hdel.sd.scl.domain.ZSDS0014;  		// 수금관리대장 마스터정보 (Scl00401-팝업)
import hdel.sd.scl.domain.ZSDS0015;  		// 수금관리대장 상세정보    (Scl00401-팝업)
import hdel.sd.scl.domain.ZSDS0014E;  		// 수금관리대장 마스터정보 (Scl00403-팝업)
import hdel.sd.scl.domain.ZSDS0015E;  		// 수금관리대장 상세정보    (Scl00403-팝업)
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
 * 해외수금상세정보 조회(Scl0043C) Control
 * @Comment 
 *     1. View 	find ( 수금상세정보 조회 )     
 *        	- T-CODE             : ZSDR117E 
 *   	 	- RFC URL 			 : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FE26175B54D0205E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : ZWEB_SD_CHN_117E
 *   	 	- RFC OBJ NAME 		 : ZSDS0014E(수금 마스터정보), ZSDS0015E(수금 상세정보)
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  김선호  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("scl0043")
public class Scl0043C {
	 
	Logger logger = Logger.getLogger(this.getClass()); 

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	   
	@RequestMapping(value="find")
	public View Scl0043FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");
		 
		// RFC INPUT PARAM DECLARE 
		ZSDS0014E[] 	list_m 	= new ZSDS0014E[]{};  // 수금관리대장 마스터정보 RFC output list
		ZSDS0015E[] 	list_h 	= new ZSDS0015E[]{};  // 수금관리대장 상세정보 RFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};  // 오류메세지
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds_m 	= null;				// UI로 return할 out dataset (수금관리대장 마스터정보 )
		Dataset 	ds_h 	= null;				// UI로 return할 out dataset (수금관리대장 상세정보) 
		Dataset 	ds_error= null;				// UI로 return할 error out dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			);  
		logger.debug("@@@ ds_cond.PROJ	===>"+DatasetUtility.getString(ds_cond,"PROJ")	);;  
		//--------------------------------------------------------------------------------------------
		
		// RFC INPUT SET (파라메터 순서엄수)
		Object obj[] = new Object[]{
				""   // C_ISVAT
				, DatasetUtility.getString(ds_cond, "PROJ")  // IN_프로젝트ID (6자리)
				, listMsg 									// O_ERROR INFO
				, list_h									// O_목록 : ZSDS0015 : 수금관리대장 상세목록정보
				, list_m									// O_목록 : ZSDS0014 : 수금관리대장 마스터정보
		}; 

		String c_isvat = "";	// 부가세 대상여부
		MipResultVO resultVO = new MipResultVO();

		try { 

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.scl.domain.ZWEB_SD_CHN_117E", obj, false);	

			// RFC 출력구조체로 out dataset(ds) 생성
			ds_m = CallSAP.isJCO() ? new Dataset() : ZSDS0014E.getDataset(); // 마스터정보 
			ds_h = CallSAP.isJCO() ? new Dataset() : ZSDS0015E.getDataset();	// 상세목록정보
			
			// RFC 호출결과(T_ITAB,P_ITAB)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds_m, stub.getOutput("T_ITAB")); // 마스터정보
			CallSAP.moveObj2Ds(ds_h, stub.getOutput("P_ITAB"));	// 상세목록정보

			c_isvat = (String)stub.getOutput("C_ISVAT");		// 부가세 대상여부
			
			// 호출결과   
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);
			
			logger.debug("@@@ ZWEB_SD_CHN_117E SUCCESS!!!");

			// 부가세포함여부를 각 행에 설정한다.
			CallSAP.makeDsCol(ds_h, "C_ISVAT", 1);	
			for (int i=0;i<ds_h.getRowCount();i++)
			{
				ds_h.setColumn(i, "C_ISVAT", c_isvat); 
			}
			
			// RFC 출력 dataset을 return
			logger.debug("@@@ ds_list_m.getRowCount 		===>" + ds_m.getRowCount());
			logger.debug("@@@ ds_list_h_temp.getRowCount 	===>" + ds_h.getRowCount()); 
			logger.debug("@@@ ds_error.getRowCount 		===>" + ds_error.getRowCount());
			
			ds_m.setId		("ds_list_m");   
			ds_h.setId		("ds_list_h");   
			resultVO.addDataset(ds_m);  		// 마스터 정보
			resultVO.addDataset(ds_h);  		// 상세목록 정보 

			logger.debug("@@@ addAttribute end");
			
		} catch (CommRfcException e) {
			ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
			logger.debug("@@@ Scl0043FindView CommRfcException ERROR!!!");
			e.printStackTrace();  
		} catch (Exception e) {
			ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
			logger.debug("@@@ Scl0043FindView Exception ERROR!!!");
			e.printStackTrace(); 
		}	  

		ds_error.setId	("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류정보

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
} 