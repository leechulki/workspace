package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.sbp.domain.ZSDS0038;
import hdel.sd.ssa.domain.Ssa0020;
import hdel.sd.ssa.domain.Ssa0020ParamVO;
import hdel.sd.ssa.service.Ssa0020S;
import hdel.sd.ssa.domain.ZSDS0034;	// 미수(1), 부도(2) 출력리스트
import hdel.sd.ssa.domain.ZSDS0036;	// 수금계획리포트(3) 출력리스트
import hdel.sd.ssa.domain.ZSDS0039;	// 미수(1), 부도(2) 수금계획 변경 입력리스트
import hdel.sd.ssa.domain.ZSDT0053;	// 미수(1), 부도(2) 채권사유 변경 입력리스트 
import hdel.sd.com.domain.RANGE_C10;// SO, 거래선용 코드범위선택 DATASET 
import hdel.sd.com.domain.RANGE_C30;// 프로젝트용 코드범위선택 DATASET
 
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
 * 매출채권 명세 및 채권현황(청구별)(Ssa0020C) Control
 * @Comment 
 * 		1. View find_zclose ( 기준년월에 해당하는 마감년월 조회 )     
 *     	2. View find 		( 매출채권 명세 및 채권현황(청구별) 목록 조회 )     
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	(조회) 					http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FBE0081D32E0110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : 	(미수, 부도 조회)			ZWEB_SD_CHN_152 		(ZSDS0034)
 *   								(수금리포트 조회)			ZWEB_SD_CHN_152 		(ZSDS0036)
 *   	 	- RFC OBJ NAME 		 : 	(미수, 부도 조회)			ZSDS0034
 *   								(수금리포트 조회)			ZSDS0036
 *     	3. View update_plan ( 기준년월에 해당하는 미수, 부도의 수금계획변경 )     
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	(미수, 부도 수금계획 저장) 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC4981489370110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910									
 *   	 	- RFC OPERATION NAME : 	(미수, 부도 수금계획 저장)	ZWEB_SD_CHN_152_PLAN	(ZSDS0039) 
 *   	 	- RFC OBJ NAME 		 : 	(미수, 부도 수금계획 저장)	ZSDS0039 
 *     	4. View update_reason ( 기준년월에 해당하는 미수, 부도의 채권사유변경 )     
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	(미수, 부도 채권사유 저장) 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC22D3D89930018E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910									
 *   	 	- RFC OPERATION NAME : 	(미수, 부도 채권사유 저장) 	ZWEB_SD_CHN_152_RES   	(ZSDT0053) 
 *   	 	- RFC OBJ NAME 		 : 	(미수, 부도 채권사유 저장) 	ZSDT0053 
 *   
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0020")
public class Ssa0020C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ssa0020S service;
	
	/*-----------------------------------------------------
	 *  기준년월에 해당하는 마감년월 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_zclose")
	public View Ssa0020FindZcloseView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ssa0020FindZcloseView START" + "");  
		
		// INPUT DATASET GET
		Dataset ds_cond_zclose 	= paramVO.getDataset("ds_cond_zclose");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_zclose 	= paramVO.getDataset("ds_list_zclose");	// UI로 return할 out dataset   
		
		// 오류정보 DATASET GET
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
						
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 		===>"+paramVO.getVariable("G_MANDT") 			+"");
		//logger.debug("@@@ paramVO.G_LANG		===>"+paramVO.getVariable("G_LANG") 			+""); 
		logger.debug("@@@ ds_cond_zclose.ZYEAR	===>"+DatasetUtility.getString(ds_cond_zclose,"ZYEAR")+"");
		logger.debug("@@@ ds_cond_zclose.ZMONTH	===>"+DatasetUtility.getString(ds_cond_zclose,"ZMONTH")+"");   
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Ssa0020ParamVO param = new Ssa0020ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  						// SAP CLIENT
			//param.setLang	(paramVO.getVariable("G_LANG"));	 					// 언어
			param.setZyear	(DatasetUtility.getString(ds_cond_zclose,"ZYEAR"));		// 기준년도
			param.setZmonth	(DatasetUtility.getString(ds_cond_zclose,"ZMONTH"));	// 기준월도 
			
			// 서비스CALL
			List<Ssa0020> list = service.find_zclose(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{    
				ds_list_zclose.appendRow();  
				ds_list_zclose.setColumn(iRow, "ZCLOSE", list.get(iRow).getZclose());		// 마감일자
			}    
			
			logger.debug("@@@ Ssa0020FindZcloseView SUCCESS!!!" + ""); 
			 
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020FindZcloseView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y"); 
		}	     

		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount 	===>" + ds_list_zclose.getRowCount() + "");
		logger.debug("@@@ ds_error.getRowCount 	===>" + ds_error.getRowCount() + ""); 
		MipResultVO resultVO = new MipResultVO(); 
		ds_list_zclose.setId	("ds_list_zclose");
		ds_error.setId			("ds_error");  
		resultVO.addDataset		(ds_list_zclose);
		resultVO.addDataset		(ds_error); 
		model.addAttribute		("resultVO", resultVO);     
		 
		logger.debug("@@@ Ssa0020FindZcloseView addAttribute end" + "");
		
		return view;
		
	}
	
	/*-----------------------------------------------------
	 *  기준년월에 해당하는 미수, 부도, 수금계획리포트 목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0020FindView(MipParameterVO paramVO, Model model) { 

		logger.debug("@@@ Ssa0020FindView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");  
		Dataset ds_cond_prjt_list 	= paramVO.getDataset("ds_cond_prjt_list");	// 프로젝트번호목록 조건
		Dataset ds_cond_buyr_list 	= paramVO.getDataset("ds_cond_buyr_list");	// 거래선목록 조건 
		
		// OUTPUT DATASET DECLARE
		Dataset ds_itab = null;				// UI로 return할 out dataset (미수(업무구분:1)/부도(업무구분:2) )
		Dataset ds_itab2= null;				// UI로 return할 out dataset (수금계획(업무구분:3))  
		Dataset ds_error= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				);  
		logger.debug("@@@ ds_cond.FR_AUART 	===>"+DatasetUtility.getString(ds_cond,"FR_AUART"	)); // 오더유형코드_FR
		logger.debug("@@@ ds_cond.FR_EMPL  	===>"+DatasetUtility.getString(ds_cond,"FR_EMPL"	)); // 담당자코드_FR
		logger.debug("@@@ ds_cond.FR_PAYER 	===>"+DatasetUtility.getString(ds_cond,"FR_PAYER"	)); // 거래선코드_FR
		logger.debug("@@@ ds_cond.FR_PJT   	===>"+DatasetUtility.getString(ds_cond,"FR_PJT"		));	// 프로젝트_FR
		logger.debug("@@@ ds_cond.FR_VB    	===>"+DatasetUtility.getString(ds_cond,"FR_VB"		));	// 부서코드_FR
		logger.debug("@@@ ds_cond.FR_VG    	===>"+DatasetUtility.getString(ds_cond,"FR_VG"		));	// 팀코드_FR
		logger.debug("@@@ ds_cond.JOB_GBN  	===>"+DatasetUtility.getString(ds_cond,"JOB_GBN"	)); // 업무구분 (1:미수, 2:부도, 3:수금계획)
		logger.debug("@@@ ds_cond.PRT_GBN  	===>"+DatasetUtility.getString(ds_cond,"PRT_GBN"	)); // 리스트양식구분 (1:국내/해외영업, 2:보수영업, 3:부도)
		logger.debug("@@@ ds_cond.TO_AUART 	===>"+DatasetUtility.getString(ds_cond,"TO_AUART"	)); // 오더유형코드_TO
		logger.debug("@@@ ds_cond.TO_EMPL  	===>"+DatasetUtility.getString(ds_cond,"TO_EMPL"	)); // 담당자코드_TO
		logger.debug("@@@ ds_cond.TO_PAYER 	===>"+DatasetUtility.getString(ds_cond,"TO_PAYER"	)); // 거래선코드_TO
		logger.debug("@@@ ds_cond.TO_PJT   	===>"+DatasetUtility.getString(ds_cond,"TO_PJT"		));	// 프로젝트_TO
		logger.debug("@@@ ds_cond.TO_VB    	===>"+DatasetUtility.getString(ds_cond,"TO_VB"		));	// 부서코드_TO
		logger.debug("@@@ ds_cond.TO_VG    	===>"+DatasetUtility.getString(ds_cond,"TO_VG"		));	// 팀코드_TO
		logger.debug("@@@ ds_cond.YEARM    	===>"+DatasetUtility.getString(ds_cond,"YEARM"		));	// 기준년월 
		//--------------------------------------------------------------------------------------------
		  
		try {  
			
			// RFC INPUT PARAM DECLARE 
			ZSDS0034[] 	list_m 		= new ZSDS0034[]{};  // 미수(업무구분:1)/부도(업무구분:2) RFC output list
			ZSDS0036[] 	list_h 		= new ZSDS0036[]{};  // 수금계획리포트(업무구분:3) RFC output list
			ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};  // 오류메세지
			RANGE_C30[] list_prjt 	= (RANGE_C30[])CallSAPHdel.moveDs2Obj2(ds_cond_prjt_list, RANGE_C30.class, "");	// 프로젝트조건목록
			RANGE_C10[] list_buyr 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_buyr_list, RANGE_C10.class, "");	// 거래선조건목록
			 
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{ 
					  DatasetUtility.getString(ds_cond, "FR_AUART")  	// 오더유형코드_FR
					, DatasetUtility.getString(ds_cond, "FR_EMPL")   	// 담당자코드_FR
					, DatasetUtility.getString(ds_cond, "FR_PAYER")  	// 거래선코드_FR
					, DatasetUtility.getString(ds_cond, "FR_PJT")  		// 프로젝트_FR
					, DatasetUtility.getString(ds_cond, "FR_VB")  		// 부서코드_FR
					, DatasetUtility.getString(ds_cond, "FR_VG")  		// 팀코드_FR
					, DatasetUtility.getString(ds_cond, "JOB_GBN")  	// 업무구분 (1:미수, 2:부도, 3:수금계획)
					, DatasetUtility.getString(ds_cond, "PRT_GBN")  	// 리스트양식구분 (1:국내/해외영업, 2:보수영업, 3:부도)
					, DatasetUtility.getString(ds_cond, "TO_AUART")  	// 오더유형코드_TO
					, DatasetUtility.getString(ds_cond, "TO_EMPL")  	// 담당자코드_TO
					, DatasetUtility.getString(ds_cond, "TO_PAYER")  	// 거래선코드_TO
					, DatasetUtility.getString(ds_cond, "TO_PJT") 		// 프로젝트_TO
					, DatasetUtility.getString(ds_cond, "TO_VB")  		// 부서코드_TO
					, DatasetUtility.getString(ds_cond, "TO_VG")  		// 팀코드_TO
					, DatasetUtility.getString(ds_cond, "YEARM")  		// 기준년월
					, listMsg 											// O_ERROR INFO
					, list_prjt											// 프로젝트조건목록
					, list_buyr											// 거래선조건목록
					, list_h											// O_목록 : ZSDS0034 : 미수(업무구분:1)/수금(업무구분:2)
					, list_m											// O_목록 : ZSDS0036 : 수금계획(업무구분:3)
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID")
								,paramVO.getVariable("G_MANDT")
								, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152"
								, obj
								,false); 
			 
			// RFC 출력구조체로 out dataset(ds) 생성
			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0034.getDataset(); // 미수(업무구분:1)/수금(업무구분:2)   
			ds_itab2 = CallSAP.isJCO() ? new Dataset() : ZSDS0036.getDataset();	// 수금계획(업무구분:3) 
			
			// RFC 호출결과(T_ITAB,T_ITAB2)를 out dataset(ds)에 옮기기  
			CallSAP.moveObj2Ds(ds_itab, stub.getOutput("T_ITAB")); 		// 미수(업무구분:1)/수금(업무구분:2)
			CallSAP.moveObj2Ds(ds_itab2, stub.getOutput("T_ITAB2"));	// 수금계획(업무구분:3)   

			// 결과  dataset에 컬럼 추가 
			CallSAP.makeDsCol(ds_itab , "SAVE_YN"	, 1);	// 저장대상여부
			CallSAP.makeDsCol(ds_itab , "CHK"		, 1);	// 선택여부
			CallSAP.makeDsCol(ds_itab , "FLAG"		, 1);	// 변경여부    
			for (int i=0;i<ds_itab.getRowCount();i++)
			{ 
				ds_itab.setColumn(i, "SAVE_YN"		, "N");
				ds_itab.setColumn(i, "CHK"			, "0");
				ds_itab.setColumn(i, "FLAG"			, ""); 
				// 회수계획일 8자리로 재설정
				ds_itab.setColumn(i, "PLDAT", CallSAPHdel.dateReplace(ds_itab.getColumnAsString(i, "PLDAT")));  
			}  
			
			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0020FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0020FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0020FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020FindView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	 
		
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list_itab.getRowCount  ===>" + ds_itab.getRowCount() 	);
		logger.debug("@@@ ds_list_itab2.getRowCount	===>" + ds_itab2.getRowCount() 	);
		logger.debug("@@@ ds_error.getRowCount		===>" + ds_error.getRowCount()	); 
		MipResultVO resultVO = new MipResultVO();
		ds_itab.setId		("ds_list_itab");  
		ds_itab2.setId		("ds_list_itab2");
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_itab);  	// 미수(업무구분:1)/수금(업무구분:2)
		resultVO.addDataset	(ds_itab2); 	// 수금계획(업무구분:3)\
		resultVO.addDataset	(ds_error); 	// 오류정보
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0020FindView addAttribute end" + "");
		
		return view;
	}
	
	

	/*-----------------------------------------------------
	 *  기준년월에 해당하는 미수, 부도의 수금계획변경
	 ------------------------------------------------------*/
	@RequestMapping(value="update_plan")
	//public View Ssa0020UpdatePlanView(MipParameterVO paramVO, Model model) {
	public View Ssa0020UpdatePlanView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ssa0020UpdatePlanView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_update_yearm 		= paramVO.getDataset("ds_update_yearm");        // 기준년월  
		Dataset ds_list_update_plan 	= paramVO.getDataset("ds_list_update_plan");  	// 수금계획수정리스트 
		 
		// OUTPUT DATASET DECLARE 
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 					===>"+paramVO.getVariable("G_MANDT") 			+"");  
		logger.debug("@@@ ds_update_yearm.YEARM 			===>"+DatasetUtility.getString(ds_update_yearm,"YEARM")); // 기준년월
		logger.debug("@@@ ds_list_update_plan.getRowCount 	===>"+ds_list_update_plan.getRowCount() + "");
		for( int irow = 0; irow < ds_list_update_plan.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_update_plan.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_update_plan["+irow+"Record : "+ds_list_update_plan.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_update_plan, irow, ds_list_update_plan.getColumnID(icol)) );
			}  
		}
		//--------------------------------------------------------------------------------------------

		
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset을 CLASS(ZSDS0039) 에 옮기기    
			// 미수(업무구분:1)/부도(업무구분:2) 수금계획 RFC input list
			ZSDS0039[] 	list 	= (ZSDS0039[])CallSAPHdel.moveDs2Obj2(ds_list_update_plan, ZSDS0039.class, "");    
			// 오류메세지
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		  
			
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{  
										  DatasetUtility.getString(ds_update_yearm, "YEARM")  	// 기준년월
										, listMsg 												// O_ERROR INFO
										, list													// 변경목록 : ZSDS0039 : 미수(업무구분:1)/수금(업무구분:2) 
										};   
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_PLAN"
												, obj, false);  
			 
			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);  
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0020UpdatePlanView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0020UpdatePlanView SUCCESS!!!" + "");
			}  

		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0020UpdatePlanView CommRfcException ERROR!!!" + "");
			e.printStackTrace();     
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020UpdatePlanView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	   
		  
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO(); 
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_error);  	// 오류정보
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0020UpdatePlanView addAttribute end" + "");
		
		return view;
		
	}
	

	/*-----------------------------------------------------
	 *  기준년월에 해당하는 채권사유 변경
	 ------------------------------------------------------*/
	@RequestMapping(value="update_reason")
	public View Ssa0020UpdateReasonView(MipParameterVO paramVO, Model model)  {

		logger.debug("@@@ Ssa0020UpdateReasonView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_list_update_reason 	= paramVO.getDataset("ds_list_update_reason");  	// 채권사유수정리스트
		
		// OUTPUT DATASET DECLARE 
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 					===>"+paramVO.getVariable("G_MANDT") 		+"");  
		logger.debug("@@@ ds_list_update_reason.getRowCount	===>"+ds_list_update_reason.getRowCount() 	+ "");
		for( int irow = 0; irow < ds_list_update_reason.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_update_reason.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_update_plan["+irow+"Record : "
								+ ds_list_update_reason.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_update_reason, irow, ds_list_update_reason.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
 
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset을 CLASS(ZSDS0039) 에 옮기기    
			// 미수(업무구분:1)/부도(업무구분:2) 채권사유 RFC input list
			ZSDT0053[] 	list 	= (ZSDT0053[])CallSAPHdel.moveDs2Obj2(ds_list_update_reason, ZSDT0053.class, "");  
			// 오류메세지
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		  
			
			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[]{   
										  listMsg 		// O_ERROR INFO
										, list			// 변경목록 : ZSDT0053 : 미수(업무구분:1)/수금(업무구분:2) 
										};   
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_RES", obj, false);	
			logger.debug("@@@ Ssa0020UpdateReasonView RFC CALL SUCCESS " + ""); 

			// RFC 호출결과 오류정보
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0020UpdateReasonView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0020UpdateReasonView SUCCESS!!!" + "");
			}  
			 
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0020UpdateReasonView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020UpdateReasonView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	
		  
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_error);  	// 오류정보 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0020UpdateReasonView addAttribute end" + "");
		
		return view;
		
	} 
}
