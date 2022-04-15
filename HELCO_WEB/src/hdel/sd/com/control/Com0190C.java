package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0190;
import hdel.sd.com.domain.Com0190ParamVO;
import hdel.sd.com.service.Com0190S;

import java.io.IOException;
import java.util.List;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility; 

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger; 


/**
 * 납품처목록 조회 (Com0190C) Control
 * @Comment 
 *     	1. View find ( 납품처목록 조회 )
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("com0190")
public class Com0190C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0190S service;
	
	/* 납품처목록 조회 */
	@RequestMapping(value="find")
	public View Com0190FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list		= paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT")); 	 
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG"));  
		logger.debug("@@@ ds_cond.CODE		===>"+DatasetUtility.getString(ds_cond,"CODE")); 
		logger.debug("@@@ ds_cond.CODE_NAME	===>"+DatasetUtility.getString(ds_cond,"CODE_NAME"));
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Com0190ParamVO param = new Com0190ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
			
			// 파라메터SET 
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT 
			param.setCODE		(DatasetUtility.getString(ds_cond,"CODE"));		// 납품처코드
			param.setCODE_NAME	(DatasetUtility.getString(ds_cond,"CODE_NAME"));// 납품처명 
			
			// 서비스CALL
			List<Com0190> list = service.selectList(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사 
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				ds_list.appendRow();   
	    		// 컬럼SET 
				ds_list.setColumn(iRow, "CODE"		, list.get(iRow).getCODE());		// 납품처코드 
				ds_list.setColumn(iRow, "CODE_NAME"	, list.get(iRow).getCODE_NAME());	// 납품처명  
				ds_list.setColumn(iRow, "INFO"		, list.get(iRow).getINFO());		// 납품처정보

			} 
			
			logger.debug("@@@ Com0190FindView SUCCESS!!!"); 
			 
		} catch (Exception e) { 
			logger.debug("@@@ Com0190FindView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount()); 
		logger.debug("@@@ ds_error.getRowCount ===>" + ds_error.getRowCount()); 
		MipResultVO resultVO = new MipResultVO(); 
		ds_list.setId	("ds_list");  
		resultVO.addDataset(ds_list); 
		ds_error.setId	("ds_error");  
		resultVO.addDataset(ds_error); 
		model.addAttribute("resultVO", resultVO);     

		return view;
		
	}
	 
}
