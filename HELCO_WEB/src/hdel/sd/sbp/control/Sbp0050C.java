package hdel.sd.sbp.control;


import hdel.lib.control.SrmView; 
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.lib.exception.BizException;

import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.sbp.service.Sbp0050S; 
import hdel.sd.sbp.domain.Sbp0050;
import hdel.sd.sbp.domain.Sbp0050ParamVO;    
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoBpNumberS;  

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.commons.lang.StringUtils; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.biz.BusinessException;
import tit.util.DatasetUtility;
      
import com.helco.xf.comm.CallSAPHdel;  
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;


import org.apache.log4j.Logger; 


/**
 * 사업계획매출채권관리(정기보수)(Sbp0050C) Control
 * @Comment
 *     	1. View   find_gtype   	( 기종 목록 조회 )
 *     	2. View   find_opendtm 	( 편성년도에 해당하는 오픈된 사업계획차수의 등록일시 조회 )
 *     	3. View   find 			( 계획년월에 해당하는 사업계획수주 목록 조회 )
 *     	4. View   find_detail 	( 사업계획번호에 해당하는 매출/청구/수금 목록 조회 )
 *     	5. View   save 			( 계획년월에 해당하는 사업계획 등록/수정/삭제 및 매출/청구/수금 삭제후 등록 후 사업계획번호를 화면으로 전송  )
 *     	6. String getNewZbpnnr 	( 사업계획번호 채번  )
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("sbp0050")
public class Sbp0050C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0050S service;   

	@Autowired
	private AutoBpNumberS 	Autoservice;	// 사업계획번호채번 서비스 

	/*-----------------------------------------------------
	 *  기종목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_gtype")
	public View Sbp0050FindGTypeView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0050FindGTypeView START!!!");
		
		// INPUT DATASET GET 
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_gtype 	= paramVO.getDataset("ds_list_gtype");			// UI로 return할 out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG"));
		//--------------------------------------------------------------------------------------------
		 
		try {  
			
			// 기종목록 조회
			List<ZGType> list = ZGTypes.find();   
			
			// 호출결과(list)를 데이타셋(dsList)에 복사   
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				ds_list_gtype.appendRow();  
	    		// 컬럼SET 
				ds_list_gtype.setColumn(iRow, "gtype"	, list.get(iRow).getZgtype	());	// 기종
				ds_list_gtype.setColumn(iRow, "auart"	, list.get(iRow).getAuart	());	// 판매문서유형(오더유형)
				ds_list_gtype.setColumn(iRow, "matnr"	, list.get(iRow).getMatnr	());	// 자재번호
				ds_list_gtype.setColumn(iRow, "spart"	, list.get(iRow).getSpart	());	// 제품군 
			}   
			
			logger.debug("@@@ Sbp0050FindGTypeView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindGTypeView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		 
		// 출력 dataset을 return
		logger.debug("@@@ ds_list_gtype.getRowCount ===>" + ds_list_gtype.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");  
		ds_list_gtype.setId	("ds_list_gtype");  
		resultVO.addDataset	(ds_error);  		// 오류정보
		resultVO.addDataset	(ds_list_gtype); 	 
		model.addAttribute	("resultVO", resultVO);     

		logger.debug("@@@ Sbp0050FindGTypeView addAttribute end" + "");
 
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  사업계획년도에 해당하는 오픈일시 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_opendtm")
	public View Sbp0050FindOpenDtmView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0050FindOpenDtmView START!!!" + "\n");
		
		// INPUT DATASET GET
		Dataset ds_cond_opendtm = paramVO.getDataset("ds_cond_opendtm");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_opendtm = paramVO.getDataset("ds_list_opendtm");		// UI로 return할 out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 			===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG			===>"+paramVO.getVariable("G_LANG") );
		logger.debug("@@@ ds_cond_opendtm.ZPYEAR	===>"+DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR")); 
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0050ParamVO param = new Sbp0050ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			//param.setLang	(paramVO.getVariable("G_LANG"));	 			// 언어
			param.setZPYEAR	(DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR"	));	// 편성년도 
			
			// 서비스CALL (사업계획년도에 해당하는 오픈일자 조회)
			List<Sbp0050> list = service.selectOpenDtm(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사  
			CallSAPHdel.moveListToDs(ds_list_opendtm, Sbp0050.class, list);  

			logger.debug("@@@ Sbp0050FindOpenDtmView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindOpenDtmView Exception ERROR!!!");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		// 출력 dataset을 return
		logger.debug("@@@ ds_list_opendtm.getRowCount ===>" + ds_list_opendtm.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId			("ds_error");   
		ds_list_opendtm.setId	("ds_list_opendtm");  
		resultVO.addDataset		(ds_list_opendtm); 
		resultVO.addDataset		(ds_error);  		// 오류정보
		model.addAttribute		("resultVO", resultVO);   

		logger.debug("@@@ Sbp0050FindOpenDtmView addAttribute end" + "");
		
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  사업계획수주목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find")
	public View Sbp0050FindView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Sbp0050FindView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 	= paramVO.getDataset("ds_list");				// UI로 return할 out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				);
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG") 				); 
		logger.debug("@@@ ds_cond.ZPYEAR	===>"+DatasetUtility.getString(ds_cond,"ZPYEAR")	);
		logger.debug("@@@ ds_cond.ZPYM		===>"+DatasetUtility.getString(ds_cond,"ZPYM")		);
		logger.debug("@@@ ds_cond.SPART		===>"+DatasetUtility.getString(ds_cond,"SPART")		);
		logger.debug("@@@ ds_cond.VKBUR_FR	===>"+DatasetUtility.getString(ds_cond,"VKBUR_FR")	);   
		logger.debug("@@@ ds_cond.VKBUR_TO	===>"+DatasetUtility.getString(ds_cond,"VKBUR_TO")	);
		logger.debug("@@@ ds_cond.VKGRP_FR	===>"+DatasetUtility.getString(ds_cond,"VKGRP_FR")	);   
		logger.debug("@@@ ds_cond.VKGRP_TO	===>"+DatasetUtility.getString(ds_cond,"VKGRP_TO")	);
		logger.debug("@@@ ds_cond.ZKUNNR	===>"+DatasetUtility.getString(ds_cond,"ZKUNNR")	);
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0050ParamVO param = new Sbp0050ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setLANG		(paramVO.getVariable("G_LANG"));	 			// 언어
			param.setZPYEAR		(DatasetUtility.getString(ds_cond,"ZPYEAR"));	// 편성년도
			param.setZPYM		(DatasetUtility.getString(ds_cond,"ZPYM"));		// 계획년월
			param.setSPART		(DatasetUtility.getString(ds_cond,"SPART"));	// 제품군코드
			param.setVKBUR_FR	(DatasetUtility.getString(ds_cond,"VKBUR_FR"));	// 부서코드_FR
			param.setVKBUR_TO	(DatasetUtility.getString(ds_cond,"VKBUR_TO"));	// 부서코드_TO
			param.setVKGRP_FR	(DatasetUtility.getString(ds_cond,"VKGRP_FR"));	// 팀코드_FR
			param.setVKGRP_TO	(DatasetUtility.getString(ds_cond,"VKGRP_TO"));	// 팀코드_TO
			param.setZKUNNR		(DatasetUtility.getString(ds_cond,"ZKUNNR"));	// 담당자코드 
				 
			// 서비스CALL (사업계획목록 조회)
			List<Sbp0050> list = service.selectList(param);  
			
			logger.debug("@@@ list.size() ===>" + list.size());
		
			// 호출결과(list)를 데이타셋(dsList)에 복사 
			CallSAPHdel.moveListToDs(ds_list, Sbp0050.class, list);  

			logger.debug("@@@ Sbp0050FindView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindView Exception ERROR!!!" + "\n");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount()); 
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_list.setId		("ds_list");  
		resultVO.addDataset	(ds_error);  		// 오류정보
		resultVO.addDataset (ds_list);  
		model.addAttribute	("resultVO", resultVO);     

		logger.debug("@@@ Sbp0050FindView addAttribute end" + "");
		
		return view;
	}
	

	/*-----------------------------------------------------
	 *  사업계획번호에 해당하는 매출/청구/수금 조회
	 ------------------------------------------------------*/ 
	@RequestMapping(value="find_detail")
	public View Sbp0050FindDetailView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Sbp0050FindDetailView START!!!" + "\n");
		
		// INPUT DATASET GET
		Dataset ds_cond_detail 	= paramVO.getDataset("ds_cond_detail");
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_bill 	= paramVO.getDataset("ds_list_bill");			// UI로 return할 out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 		===>"+paramVO.getVariable("G_MANDT") 					);
		logger.debug("@@@ paramVO.G_LANG		===>"+paramVO.getVariable("G_LANG") 					); 
		logger.debug("@@@ ds_cond_detail.PSPIDSV	===>"+DatasetUtility.getString(ds_cond_detail,"PSPIDSV")	);
		logger.debug("@@@ ds_yearm.getRowCount 	===>"+ds_yearm.getRowCount() 						);
		for( int irow = 0; irow < ds_yearm.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_yearm.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_yearm["+irow+"Record : "+ds_yearm.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_yearm, irow, ds_yearm.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0050ParamVO param = new Sbp0050ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 보수프로젝트번호  
			String pspidsv = DatasetUtility.getString(ds_cond_detail,"PSPIDSV");
			
			// 파라메터 SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT 
			param.setPSPIDSV	(pspidsv);									// 보수프로젝트번호
			param.setM00		(ds_yearm.getColumnAsString( 0, "YEARM"));	// 계획년월 + 00월
			param.setM01		(ds_yearm.getColumnAsString( 1, "YEARM"));	// 계획년월 + 01월
			param.setM02		(ds_yearm.getColumnAsString( 2, "YEARM"));	// 계획년월 + 02월
			param.setM03		(ds_yearm.getColumnAsString( 3, "YEARM"));	// 계획년월 + 03월
			param.setM04		(ds_yearm.getColumnAsString( 4, "YEARM"));	// 계획년월 + 04월
			param.setM05		(ds_yearm.getColumnAsString( 5, "YEARM"));	// 계획년월 + 05월
			param.setM06		(ds_yearm.getColumnAsString( 6, "YEARM"));	// 계획년월 + 06월
			param.setM07		(ds_yearm.getColumnAsString( 7, "YEARM"));	// 계획년월 + 07월
			param.setM08		(ds_yearm.getColumnAsString( 8, "YEARM"));	// 계획년월 + 08월
			param.setM09		(ds_yearm.getColumnAsString( 9, "YEARM"));	// 계획년월 + 09월
			param.setM10		(ds_yearm.getColumnAsString(10, "YEARM"));	// 계획년월 + 10월
			param.setM11		(ds_yearm.getColumnAsString(11, "YEARM"));	// 계획년월 + 11월
			param.setM12		(ds_yearm.getColumnAsString(12, "YEARM"));	// 계획년월 + 12월
			param.setM13		(ds_yearm.getColumnAsString(13, "YEARM"));	// 계획년월 + 13월
			param.setM14		(ds_yearm.getColumnAsString(14, "YEARM"));	// 계획년월 + 14월
			param.setM15		(ds_yearm.getColumnAsString(15, "YEARM"));	// 계획년월 + 15월
			param.setM16		(ds_yearm.getColumnAsString(16, "YEARM"));	// 계획년월 + 16월
			param.setM17		(ds_yearm.getColumnAsString(17, "YEARM"));	// 계획년월 + 17월
			param.setM18		(ds_yearm.getColumnAsString(18, "YEARM"));	// 계획년월 + 18월
			param.setM19		(ds_yearm.getColumnAsString(19, "YEARM"));	// 계획년월 + 19월 
			param.setM20		(ds_yearm.getColumnAsString(20, "YEARM"));	// 계획년월 + 20월
			param.setM21		(ds_yearm.getColumnAsString(21, "YEARM"));	// 계획년월 + 21월
			param.setM22		(ds_yearm.getColumnAsString(22, "YEARM"));	// 계획년월 + 22월
			param.setM23		(ds_yearm.getColumnAsString(23, "YEARM"));	// 계획년월 + 23월
			param.setM24		(ds_yearm.getColumnAsString(24, "YEARM"));	// 계획년월 + 24월
			param.setM25		(ds_yearm.getColumnAsString(25, "YEARM"));	// 계획년월 + 25월
			param.setM26		(ds_yearm.getColumnAsString(26, "YEARM"));	// 계획년월 + 26월
			param.setM27		(ds_yearm.getColumnAsString(27, "YEARM"));	// 계획년월 + 27월
			param.setM28		(ds_yearm.getColumnAsString(28, "YEARM"));	// 계획년월 + 28월
			param.setM29		(ds_yearm.getColumnAsString(29, "YEARM"));	// 계획년월 + 29월 

			// 서비스CALL (사업계획 매출/청구/수금 조회)
			List<Sbp0050> list = service.selectListDetail(param);  
			
			logger.debug("@@@ list.size ===>" + list.size());
		
			// 호출결과(list)를 데이타셋(ds_list_bill)에 복사 
			CallSAPHdel.moveListToDs(ds_list_bill, Sbp0050.class, list); 
			 
			logger.debug("@@@ Sbp0050FindDetailView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindDetailView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list_bill.getRowCount ===>" + ds_list_bill.getRowCount()); 
		MipResultVO resultVO = new MipResultVO();
		ds_list_bill.setId("ds_list_bill");  
		ds_error.setId		("ds_error");   
		resultVO.addDataset (ds_list_bill);  
		resultVO.addDataset	(ds_error);  		// 오류정보
		model.addAttribute	("resultVO", resultVO);    

		logger.debug("@@@ Sbp0050FindDetailView addAttribute end" + "");
		
		return view;
	}
 
	/*------------------------------------------------------------
	 *  계획년월에 해당하는 사업계획 등록/수정/삭제 (매출/청구/수금 삭제후등록)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Sbp0050SaveView(MipParameterVO paramVO, Model model) { 
  
		logger.debug("@@@ Sbp0050SaveView START!!!" + "\n");
		
		// INPUT DATASET GET 
		Dataset ds_detail 	= paramVO.getDataset("ds_detail");  					// 수주 등록,수정,삭제 대상정보
		String 	pspidsv 	= DatasetUtility.getString(ds_detail, 0, "PSPIDSV");  	// 보수프로젝트번호(정상 종료 시 화면으로 전송)
		String 	auart		= DatasetUtility.getString(ds_detail, 0, "AUART");		// 판매문서유형(오더유형)
		String 	flag 		= DatasetUtility.getString(ds_detail, 0, "FLAG");		// 저장구분(I,U,D) 
		String 	mandt 		= paramVO.getVariable("G_MANDT");						// SAP CLIENT 
				
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// 결과정보  DATASET GET
		Dataset ds_result 	= paramVO.getDataset("ds_result");   

		// 사업계획을 저장한다.
		try { 
		
			// 1.Session GET
			logger.debug("@@@ 1.Session GET"); 
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); 
			  
			// 3.저장 호출
			logger.debug("@@@ 3.저장 호출"); 
			service.save(paramVO, model, pspidsv);
			
			// 4.보수프로젝트번호 반환
			logger.debug("@@@ 4.보수프로젝트번호 반환"); 
			ds_result.appendRow();
			ds_result.setColumn(0, "PSPIDSV", pspidsv);
			
			logger.debug("@@@ Sbp0050SaveView SUCCESS!!!");
			 
		} catch (BizException e) {  
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			logger.info("@@@ Sbp0050SaveView BizException ERROR!!! : " + e.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
		} catch (Exception e) { 
			// 예기치못한 비정상종료인 경우		 (e.getMessage()에 오류메세지 전체가 담겨져 옴)
			e.printStackTrace();
			logger.error("@@@ Sbp0050SaveView Exception ERROR!!! : " + e.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO(); 
		logger.debug("@@@ ds_result.getRowCount ===>"+ds_result.getRowCount());
		logger.debug("@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount());
		ds_result.setId		("ds_result");   
		ds_error.setId		("ds_error");   
		resultVO.addDataset	(ds_result);  	// 결과INFO(사업계획번호) 
		resultVO.addDataset	(ds_error);  	// 오류INFO 
		model.addAttribute(	"resultVO", resultVO);   

		logger.debug("@@@ Sbp0050SaveView addAttribute end" + "");

		return view;

	}
	 
	// 사업계획번호 채번
	public String getNewZbpnnr(String sysid, String mandt 
								, String gbn		// 채번구분(0:사업계획, 1:수주계획 , 2:견적)
								, String auart		// 판매문서유형 (오더유형)
								)
	{   

		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr START!!!");
		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr.mandt	===> " 	+ mandt);
		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr.gbn		===> " 	+ gbn);
		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr.auart	===> " 	+ auart);
		 
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(sysid); 
		
		AutoNumberParamVO param = new AutoNumberParamVO();
		
		// DAO생성 
		Autoservice.createDao(session); 

		// Parameter SET
		param.setMandt		(mandt);  	// SAP CLIENT
		param.setDeptFlag	(auart);	// 기종
		param.setSoQtFlag	(gbn);		// 채번구분(0:사업계획, 1:수주계획 , 2:견적)

		// 사업계획번호 채번 서비스 호출
		List<AutoNumberVO> listVO = Autoservice.AutoBpNumber(param);   

		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr SUCCESS !!!");
		return listVO.get(0).getNumber().toString();  // 채번된 사업계획번호 RETURN
		
	}
	
}
