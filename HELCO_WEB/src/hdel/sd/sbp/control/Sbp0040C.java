package hdel.sd.sbp.control;


import hdel.lib.control.SrmView; 
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.lib.exception.BizException;

import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.sbp.service.Sbp0040S;   
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0040ParamVO;    
import hdel.sd.sbp.domain.ZSDS0071;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoSoNumberS;  

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
      
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;  
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;


import org.apache.log4j.Logger; 


/**
 * 사업계획관리(보수)(Sbp0040C) Control
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
@RequestMapping("sbp0040")
public class Sbp0040C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0040S service;   

	@Autowired
	private AutoSoNumberS 	Autoservice;	// 사업계획번호채번 서비스 

	/*-----------------------------------------------------
	 *  협럭업체코드로 협력업체명 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_lifnr")
	public View Sbp0040FindLifnrView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0040FindLifnrView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_lifnr = paramVO.getDataset("ds_lifnr");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG")); 
		logger.debug("@@@ ds_lifnr.ZAGNT	===>"+DatasetUtility.getString(ds_lifnr,"ZAGNT")); 
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				   	// SAP CLIENT 
			param.setZAGNT	(DatasetUtility.getString(ds_lifnr,"ZAGNT"	));		// 대리점코드 
			
			// 서비스CALL (협력업체명조회)
			List<Sbp0040> list = service.selectListLifnr(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사  
			ds_lifnr.deleteAll();
			CallSAPHdel.moveListToDs(ds_lifnr, Sbp0040.class, list); 
			
			logger.debug("@@@ ds_lifnr.lifnr : " + ds_lifnr.getColumnAsString(0, "ZAGNT"));
			logger.debug("@@@ ds_lifnr.lifnr_nm : " + ds_lifnr.getColumnAsString(0, "ZAGNT_NM"));
			
			logger.debug("@@@ Sbp0040FindLifnrView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindLifnrView Exception ERROR!!!");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		
		// 출력 dataset을 return
		logger.debug("@@@ ds_lifnr.getRowCount ===>" + ds_lifnr.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_lifnr.setId		("ds_lifnr");  
		resultVO.addDataset	(ds_error);  		// 오류정보
		resultVO.addDataset	(ds_lifnr); 
		model.addAttribute("resultVO", resultVO);     

		logger.debug("@@@ Sbp0040FindLifnrView addAttribute end" + "");
		
		return view;  
	} 
	
	/*-----------------------------------------------------
	 *  기종목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_gtype")
	public View Sbp0040FindGTypeView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0040FindGTypeView START!!!");
		
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
			
			logger.debug("@@@ Sbp0040FindGTypeView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindGTypeView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0040FindGTypeView addAttribute end" + "");
 
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  사업계획년도에 해당하는 오픈일시 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find_opendtm")
	public View Sbp0040FindOpenDtmView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0040FindOpenDtmView START!!!" + "\n");
		
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
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			//param.setLang	(paramVO.getVariable("G_LANG"));	 			// 언어
			param.setZPYEAR	(DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR"	));	// 편성년도 
			
			// 서비스CALL (사업계획년도에 해당하는 오픈일자 조회)
			List<Sbp0040> list = service.selectOpenDtm(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사  
			CallSAPHdel.moveListToDs(ds_list_opendtm, Sbp0040.class, list);  

			logger.debug("@@@ Sbp0040FindOpenDtmView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindOpenDtmView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0040FindOpenDtmView addAttribute end" + "");
		
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  사업계획수주목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find")
	public View Sbp0040FindView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Sbp0040FindView START!!!");
		
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
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
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
			List<Sbp0040> list = service.selectList(param);  
			
			logger.debug("@@@ list.size() ===>" + list.size());
		
			// 호출결과(list)를 데이타셋(dsList)에 복사 
			CallSAPHdel.moveListToDs(ds_list, Sbp0040.class, list);  

			logger.debug("@@@ Sbp0040FindView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindView Exception ERROR!!!" + "\n");
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

		logger.debug("@@@ Sbp0040FindView addAttribute end" + "");
		
		return view;
	}
	

	/*-----------------------------------------------------
	 *  사업계획번호에 해당하는 매출/청구/수금 조회
	 ------------------------------------------------------*/ 
	@RequestMapping(value="find_detail")
	public View Sbp0040FindDetailView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Sbp0040FindDetailView START!!!" + "\n");
		
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
		logger.debug("@@@ ds_cond_detail.ZBPNNR	===>"+DatasetUtility.getString(ds_cond_detail,"ZBPNNR")	);
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
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 사업계획번호  
			String zbpnnr = DatasetUtility.getString(ds_cond_detail,"ZBPNNR");
			
			// 파라메터 SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT 
			param.setZBPNNR		(zbpnnr);									// 사업계획번호
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
			List<Sbp0040> list = service.selectListDetail(param);  
			
			logger.debug("@@@ list.toString ===>" + list.toString());
		
			// 호출결과(list)를 데이타셋(dsList)에 복사 
			CallSAPHdel.moveListToDs(ds_list_bill, Sbp0040.class, list); 
			 
			logger.debug("@@@ Sbp0040FindDetailView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindDetailView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0040FindDetailView addAttribute end" + "");
		
		return view;
	}
 
	/*------------------------------------------------------------
	 *  계획년월에 해당하는 사업계획 등록/수정/삭제 (매출/청구/수금 삭제후등록)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Sbp0040SaveView(MipParameterVO paramVO, Model model) { 
  
		logger.debug("@@@ Sbp0040SaveView START!!!" + "\n");
		
		// INPUT DATASET GET  
		Dataset ds_detail 		= paramVO.getDataset("ds_detail");  		// 수주 등록,수정,삭제 대상정보
		Dataset ds_compute_cond	= paramVO.getDataset("ds_compute_cond");  	// 매출/청구/수금 자동산출용 조건항목
		Dataset ds_list_bill 	= paramVO.getDataset("ds_list_bill"); 	// 매출/청구/수금 저장 대상정보(예상금액)
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm"); 			// 매출/청구/수금 저장 대상정보(18개월치 예상년월)
				
		// INPUT ITEM 
		String 	mandt 			= paramVO.getVariable("G_MANDT");						// SAP CLIENT 
		String 	user_id 		= paramVO.getVariable("G_USER_ID");						// WEB USER ID 
		String 	flag 			= DatasetUtility.getString(ds_detail, 0, "FLAG");		// 저장구분(I,U,D) 
		String 	zbpnnr 			= DatasetUtility.getString(ds_detail, 0, "ZBPNNR");  	// 사업계획번호(정상 종료 시 화면으로 전송)
		String 	auart			= DatasetUtility.getString(ds_detail, 0, "AUART");		// 판매문서유형(오더유형)
		String 	matnr			= DatasetUtility.getString(ds_detail, 0, "MATNR");		// 자재번호
		String 	posid 			= "";													// 호기번호
		String  pspidsv			= DatasetUtility.getString(ds_detail, 0, "PSPIDSV");  	// 보수프로젝트번호
		 	
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// 결과정보  DATASET GET
		Dataset ds_result 	= paramVO.getDataset("ds_result");   

		String call_sap_yn 		= "";	// 매출/청구/수금 산출 SAP함수 호출여부 (SAP용)
		String auto_compute_yn  = "";	// 매출/청구/수금 자동산출 반영여부 	 (WEB용)
		
		// 사업계획을 저장한다.
		try { 
		
			// 1.Session GET
			logger.debug("@@@ 1.Session GET"); 
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); 
			
			// 2.신규 사업계획인 경우 사업계획번호 GET (계획구분별(사업계획:0), 판매문서유형별 채번)   
			logger.debug("@@@ 2.신규 사업계획인 경우 사업계획번호 GET (계획구분별(사업계획:0), 판매문서유형별 채번)");  
			if ("I".equals(flag))
			{
				logger.debug("@@@ 2. 사업계획 신규생성요청인 경우 ");
				 
				logger.debug("@@@ 2.1 신규사업계획번호 채번"); 
				zbpnnr = getNewZbpnnr(paramVO.getVariable("G_SYSID"), mandt, "1", auart); 
				logger.debug("@@@ 2.1 신규사업계획번호 채번 결과 : " + zbpnnr);  

				logger.debug("@@@ 2.2  매출/청구/수금 산출 SAP함수 호출여부 SET"); 
				call_sap_yn = "Y"; 
				logger.debug("@@@ 2.2 call_sap_yn : " + call_sap_yn); 
				 
				// 매출/청구/수금 자동산출 대상여부
				auto_compute_yn = "Y"; 
				logger.debug("@@@ 2.3 auto_compute_yn : " + auto_compute_yn);
				
				logger.debug("@@@ 2.4 호기번호 재설정"); 
				posid 			= f_posid_make(zbpnnr, matnr);	
				logger.debug("@@@ 2.4 posid : " + posid);    
				
			}
			
			// 2. 사업계획 변경요청인 경우
			else if ("U".equals(flag))
			{  
				logger.debug("@@@ 2. 사업계획 변경요청인 경우"); 

				logger.debug("@@@ 2.1  매출/청구/수금 산출 SAP함수 호출여부 SET");  
				call_sap_yn = chkAutoComputetSapYN(session ,ds_detail ,mandt, zbpnnr); 
				logger.debug("@@@ 2.1 call_sap_yn : " + call_sap_yn);
				
				// 매출/청구/수금 자동산출 대상여부
				auto_compute_yn =  chkAutoComputetWebYN(  session
						                                 , mandt 		// CLIENT  
														 , ds_detail	// 사업계획정보
														 ); 
				logger.debug("@@@ 2.2 auto_compute_yn : " + auto_compute_yn);
				
				logger.debug("@@@ 2.3 호기번호 재설정"); 
				posid 			= f_posid_make(zbpnnr, matnr);	
				logger.debug("@@@ 2.3 posid : " + posid);  
 
				
			}	// end of else if ("U".equals(flag))
			

			// 2. 사업계획 삭제요청인 경우    
			else if ("D".equals(flag))
			{ 
				logger.debug("@@@ 2. 사업계획 삭제요청인 경우    "); 

				logger.debug("@@@ 2.1  매출/청구/수금 산출 SAP함수 호출여부 SET");  
				call_sap_yn = "";   // 삭제인 경우 매출/청구/수금 테이블을 삭제하지 않는다.
				logger.debug("@@@ 2.1 call_sap_yn : " + call_sap_yn); 
				
				// 매출/청구/수금 자동산출 대상여부
				auto_compute_yn = ""; 
				logger.debug("@@@ 2.2 auto_compute_yn : " + auto_compute_yn);
				
			}	// end of else if ("D".equals(flag))  

			// 3.저장 호출
			logger.debug("@@@ 3.저장 호출");     
			service.save( 	  ds_detail  		// 사업계획상세
							, ds_list_bill  	// 매출/청구/수금 저장 대상정보(예상금액)
							, ds_yearm  		// 매출/청구/수금 저장 대상정보(30개월치 예상년월)
							, flag				// 저장구분(I,U,D)
							, paramVO.getVariable("G_SYSID")
							, mandt				// CLIENT
							, user_id			// 사용자ID
							, zbpnnr			// 사업계획번호 
							, pspidsv			// 보수프로젝트번호
							, call_sap_yn		// 매출/청구/수금 자동산출 대상여부(SAP용)
							, auto_compute_yn	// 매출/청구/수금 자동산출 대상여부(WEB용)
							, posid				// 호기
							) ;
			
			// 4. 매출/청구/수금 산출 SAP함수 호출여부=='Y'인 경우 매출/청구/수금 자동산출  RFC FUCNTION 호출
			if ("Y".equals(call_sap_yn))
			{ 
				logger.debug("@@@ 4. 매출/청구/수금 산출 SAP함수 호출여부=='Y'인 경우 매출/청구/수금 자동산출  RFC FUCNTION 호출 ");
				
				 
				// 4.1 수주 Input Dataset(ds_detail) --> class(Sbp0010)로 copy
				logger.debug("@@@ 4.1 수주 Input Dataset(ds_detail) --> class(Sbp0010)로 copy "); 
				Sbp0040[] param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);    
				 
				// 4.2  일부 항목 재설정
				logger.debug("@@@ 4.2  일부 항목 재설정 "); 
				ds_compute_cond.setColumn(0, "SONNR", zbpnnr);  // 사업계획번호
				ds_compute_cond.setColumn(0, "POSID", posid); 	// 호기번호
				
				// 4.3 RFC FUCNTION 호출
				logger.debug("@@@ 4.3 RFC FUCNTION 호출 ");
				ZQMSEMSG[] 	list_msg		= new ZQMSEMSG[]{};	 	// 오류메시지  
				ZSDS0072[] 	list_out		= new ZSDS0072[]{};		// 매출/청구/수금 자동산출 결과
				ZSDS0071[]	list_in 		= (ZSDS0071[])CallSAPHdel.moveDs2Obj2(ds_compute_cond, ZSDS0071.class, ""); 
				Object 		obj[] 			= new Object[]{"Y", list_msg , list_in , list_out};   
				SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), mandt , "hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);
				Dataset   	ds_compute_out 	= CallSAP.isJCO() ? new Dataset() : ZSDS0072.getDataset(); 
				CallSAP.moveObj2Ds(ds_compute_out, stub.getOutput("T_ITAB2")); 
				list_msg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				
				// 오류정보 존재시 에러 테이블에 SETTING
				if (list_msg.length > 0)
				{  
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
																, list_msg[0].getIDX().toString()
																, list_msg[0].getERRMSG().toString()
																, "Y"
																, "Y"); 
				} 
				
				// 5.2 호출 결과 오류정보 존재 시
				if ( ds_error.getRowCount() > 0 )
				{  
					logger.error("@@@ 5.2 호출 결과 오류정보 존재 시");
					logger.error("@@@ 5.2 ZWEB_SD_PLAN_COMPUTE FAILED!!!" + "");
					logger.error("@@@ 5.2 ERRCODE : " + ds_error.getColumnAsString(0, "ERRCODE"));  
					logger.error("@@@ 5.2 ERRMSG : " + ds_error.getColumnAsString(0, "ERRMSG")); 
					
					String save_stat    = ds_error.getColumnAsString(0, "ERRCODE"); 
					if ("1".equals(save_stat) 	|| "2".equals(save_stat) 
												|| "3".equals(save_stat))	
					{  
						// 사업계획 저장상태 변경 (SAVE_STAT : E/F/G)
						logger.debug("@@@ 5.2 사업계획 저장상태 변경  "); 
						service.updateSaveStat(mandt, zbpnnr, user_id, save_stat);   
					}   
				} 	// end of else if ( ds_error.getRowCount() > 0 )
				
				// 5.2 자동산출결과 미존재 시 오류 처리  
				else if ( ds_compute_out.getRowCount() == 0 )
				{
					logger.debug("@@@ 5.2 자동산출결과 미존재 시 오류 처리   ");
					// "CE10003", "사업계획 저장 시 매출/청구/수금 자동산출 결과가 존재하지 않습니다. 확인 바랍니다.");   
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE10003", "", "Y", "Y");
				}	// end of if ( ds_compute_out.getRowCount() == 0 ) 
				
				// 5.3 호출 결과 정상 일 경우
				else
				{
					 
					// 5.4 사업계획저장상태변경 (SAVE_STAT=Z)
					logger.debug("@@@ 5.3 사업계획저장상태변경 (SAVE_STAT=Z)");   
					service.bill_save_auto_sap(   ds_compute_out	// 매출/청구/수금 자동산출결과 
												, mandt				// CLIENT
												, user_id 			// WEB USER_ID
												, zbpnnr 			// 사업계획번호
												);   
					 
					
				} 
				 
			}
			
			// 4.사업계획번호 반환
			logger.debug("@@@ 4.사업계획번호 반환"); 
			ds_result.appendRow();
			ds_result.setColumn(0, "ZBPNNR", zbpnnr);
			
			logger.debug("@@@ Sbp0040SaveView SUCCESS!!!");
			 
		} catch (BizException e) {  
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			logger.info("@@@ Sbp0040SaveView BizException ERROR!!! : " + e.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
		} catch (Exception e) { 
			// 예기치못한 비정상종료인 경우		 (e.getMessage()에 오류메세지 전체가 담겨져 옴)
			e.printStackTrace();
			logger.error("@@@ Sbp0040SaveView Exception ERROR!!! : " + e.getMessage());
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

		logger.debug("@@@ Sbp0040SaveView addAttribute end" + "");

		return view;

	}
	 
	// 사업계획번호 채번
	public String getNewZbpnnr(String sysid, String mandt 
								, String gbn		// 채번구분(1:사업계획, 1:수주계획 , 2:견적)
								, String auart		// 판매문서유형 (오더유형)
								)
	{   

		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr START!!!");
		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr.mandt	===> " 	+ mandt);
		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr.gbn		===> " 	+ gbn);
		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr.auart	===> " 	+ auart);
		 
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
		List<AutoNumberVO> listVO = Autoservice.AutoSoNumber(param);   

		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr SUCCESS !!!");
		return listVO.get(0).getNumber().toString();  // 채번된 사업계획번호 RETURN
		
	}
	
	// 사업계획 변경 요청인 경우 (flag=="U") 매출/청구/수금 산출 SAP함수 호출여부 대상여부 체크 
	// 특정항목의 변경 전 값을  조회 후 변경되었을 경우 자동산출여부 = Y로 SET
	// 특정항목 : 오더유형, 제품군, 기종, 자재번호, 수주예상액
	public String chkAutoComputetSapYN(  SqlSession session
				                    , Dataset 	 ds_detail
				                    , String 	 mandt 		// CLIENT
									, String 	 zbpnnr		// 사업계획번호  
									)
	{    
		// DAO생성
		service.createDao(session);

		logger.debug("@@@ Sbp0040C.chkAutoComputetYN START!!!" 	+ "");
		logger.debug("@@@ Sbp0040C.chkAutoComputetYN.mandt : " 	+ mandt + "");
		logger.debug("@@@ Sbp0040C.chkAutoComputetYN.zbpnnr : " + zbpnnr + ""); 

		String call_sap_yn = "N";	// 매출/청구/수금 산출 SAP함수 호출여부
		
		// 현재 DB에 저장되어 있는 값 조회
		Sbp0040ParamVO 	param_item 		= new Sbp0040ParamVO(); 
		
		param_item.setMANDT(mandt);		// CLIENT
		param_item.setZBPNNR(zbpnnr);  	// 사업계획번호
		
		List<Sbp0040> list_item = service.selectListComputeItem(param_item);
		 
		Double list_sofoca  = list_item.get(0).getSOFOCA().doubleValue();	// T/B_수주예상금액
		
		String auart 		= ds_detail.getColumnAsString(0, "AUART");	// IN_오더유형
		String spart 		= ds_detail.getColumnAsString(0, "SPART");	// IN_제품군
		String gtype 		= ds_detail.getColumnAsString(0, "GTYPE");	// IN_기종
		String mantr 		= ds_detail.getColumnAsString(0, "MATNR");	// IN_자재코드 
		Double detail_sofoca = ds_detail.getColumnAsDouble(0,"SOFOCA"); // IN_수주예상금액
		if (StringUtils.isBlank(auart)) auart = "";
		if (StringUtils.isBlank(spart)) spart = "";
		if (StringUtils.isBlank(gtype)) gtype = "";
		if (StringUtils.isBlank(mantr)) mantr = ""; 
		
		logger.debug("@@@ list.AUART() :[" + list_item.get(0).getAUART() + "");
		logger.debug("@@@ list.SPART() :[" + list_item.get(0).getSPART() + "");
		logger.debug("@@@ list.GTYPE() :[" + list_item.get(0).getGTYPE() + "");
		logger.debug("@@@ list.MATNR() :[" + list_item.get(0).getMATNR() + ""); 
		logger.debug("@@@ list.getSOFOCA() :[" + list_item.get(0).getSOFOCA().doubleValue()+ "");
		logger.debug("@@@ ds_detail.AUART() :[" + auart + "");
		logger.debug("@@@ ds_detail.SPART() :[" + spart + "");
		logger.debug("@@@ ds_detail.GTYPE() :[" + gtype + "");
		logger.debug("@@@ ds_detail.MATNR() :[" + mantr + "");  	
		logger.debug("@@@ ds_detail.SOFOCA() :[" + detail_sofoca+ ""); 
				
				
		if ( 
			   !auart.equals(list_item.get(0).getAUART().toString()) 	// 오더유형(판매문서유형)
			|| !spart.equals(list_item.get(0).getSPART().toString())  	// 제품군
			|| !gtype.equals(list_item.get(0).getGTYPE().toString())  	// 기종
			|| !mantr.equals(list_item.get(0).getMATNR().toString()) 	// 자재번호  
			|| (list_sofoca.compareTo(detail_sofoca) != 0)				// 수주예상액 
			)
		{
			// 변경 전과 후가 상이할 경우 다시 매출/청구/수금 산출 SAP함수 호출  해야 함 
			call_sap_yn	= "Y";		
		} 
		  
		
		return call_sap_yn;
	}
	
	// 사업계획 변경 요청인 경우 (flag=="U") 기존 사업계획 정보 조회하여
	// 오더유형, 수주예상금액이 변경된 경우 매출/청구/수금 자동산출 후 저장
	public String chkAutoComputetWebYN(  SqlSession session
										, String mandt 		// CLIENT  
										, Dataset ds_detail
										)
	{     
   
		// DAO생성
		service.createDao(session);
		
		String  zbpnnr 	= ds_detail.getColumnAsString(0, "ZBPNNR");		// 사업계획번호 
		Double  sofoca  = ds_detail.getColumnAsDouble(0, "SOFOCA");		// 수주예상액   
		String 	auart 	= ds_detail.getColumnAsString(0, "AUART");		// IN_오더유형
		
		logger.debug("@@@ Sbp0040C.selectListComputeItem START!!!" 	+ "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.mandt : " 	+ mandt + "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.zbpnnr : " + zbpnnr + "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.sofoca : " + sofoca + "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.auart : " + auart + "");  
		
		// 현재 DB에 저장되어 있는 값 조회
		Sbp0040ParamVO 	param_item 		= new Sbp0040ParamVO(); 
		
		param_item.setMANDT(mandt);		// CLIENT
		param_item.setZBPNNR(zbpnnr);  	// 사업계획번호
		
		List<Sbp0040> list_item = service.selectListComputeItem(param_item);
		 
		Double old_sofoca   = list_item.get(0).getSOFOCA().doubleValue();	// T/B_수주예상금액    
		 
		logger.debug("@@@ old_sofoca :[" + list_item.get(0).getSOFOCA().doubleValue()+ ""); 
		logger.debug("@@@ new_sofoca :[" + sofoca + "");  
				
		if (
			 !auart.equals(list_item.get(0).getAUART().toString()) 	// 오더유형(판매문서유형)이 상이할 경우
			 || old_sofoca.compareTo(sofoca) != 0					// 수주예상액이 상이할 경우 
			)	
		{
			//  매출/청구/수금 산출을 다시  해야 함 
			return "Y";		
		}  
		
		// 자동산출 다시 하지 않음
		return "N";
	}
	
	// 사업계획번호, 자재번호로 호기번호   make하여 return
	private String f_posid_make(String zbpnnr, String matnr)
	{
		String 		posid   = "";		// 호기		
		if ("NS-100".equals(matnr))
		{
			posid = zbpnnr + "NS";
		}
		else
		{
			posid = zbpnnr + StringUtils.substring(matnr, 0, 1) + "01";
		}  
		return posid;
	}
	
}
