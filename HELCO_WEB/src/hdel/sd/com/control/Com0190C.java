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
 * ��ǰó��� ��ȸ (Com0190C) Control
 * @Comment 
 *     	1. View find ( ��ǰó��� ��ȸ )
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
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
	
	/* ��ǰó��� ��ȸ */
	@RequestMapping(value="find")
	public View Com0190FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list		= paramVO.getDataset("ds_list");	// UI�� return�� out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT")); 	 
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG"));  
		logger.debug("@@@ ds_cond.CODE		===>"+DatasetUtility.getString(ds_cond,"CODE")); 
		logger.debug("@@@ ds_cond.CODE_NAME	===>"+DatasetUtility.getString(ds_cond,"CODE_NAME"));
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Com0190ParamVO param = new Com0190ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
			
			// �Ķ����SET 
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT 
			param.setCODE		(DatasetUtility.getString(ds_cond,"CODE"));		// ��ǰó�ڵ�
			param.setCODE_NAME	(DatasetUtility.getString(ds_cond,"CODE_NAME"));// ��ǰó�� 
			
			// ����CALL
			List<Com0190> list = service.selectList(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ���� 
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list.appendRow();   
	    		// �÷�SET 
				ds_list.setColumn(iRow, "CODE"		, list.get(iRow).getCODE());		// ��ǰó�ڵ� 
				ds_list.setColumn(iRow, "CODE_NAME"	, list.get(iRow).getCODE_NAME());	// ��ǰó��  
				ds_list.setColumn(iRow, "INFO"		, list.get(iRow).getINFO());		// ��ǰó����

			} 
			
			logger.debug("@@@ Com0190FindView SUCCESS!!!"); 
			 
		} catch (Exception e) { 
			logger.debug("@@@ Com0190FindView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		
		// RFC ��� dataset�� return
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
