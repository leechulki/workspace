package hdel.sd.sbp.control;


import hdel.lib.control.SrmView; 
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.lib.exception.BizException;
 
import hdel.sd.sbp.service.Sbp0070S; 
import hdel.sd.sbp.domain.Sbp0070;
import hdel.sd.sbp.domain.Sbp0070ParamVO;    

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
 * �����ȹ����/���� (Sbp0070C) Control
 * @Comment 
 *     	1. View find( �����ȹ������� ��ȸ ) 
 *     	2. View save( �����ȹ�������� ���� )
 *        	- ó������ : IN(��������) DL(��������) OP(����) OC(�������) CL(����) CC(�������)
 *        
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("sbp0070")
public class Sbp0070C {
 
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0070S service;    
  
	/*-----------------------------------------------------
	 *  �����ȹ������� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find")
	public View Sbp0070FindView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Sbp0070FindView START!!!" + "\n");
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 		= paramVO.getDataset("ds_list");		// UI�� return�� out dataset(��� ����)
		Dataset ds_list_last 	= paramVO.getDataset("ds_list_last");	// UI�� return�� out dataset(���� �����ȹ���� ����)
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				+"");
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG") 				+""); 
		logger.debug("@@@ ds_cond.ZPYEAR	===>"+DatasetUtility.getString(ds_cond,"ZPYEAR")	+""); 
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0070ParamVO param = new Sbp0070ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setLANG		(paramVO.getVariable("G_LANG"));	 			// ���
			param.setZPYEAR		(DatasetUtility.getString(ds_cond,"ZPYEAR"));	// ���⵵ 
				 
			// 1.�����ȹ��� ��ȸ
			List<Sbp0070> list = service.selectList(param);  
			CallSAPHdel.moveListToDs(ds_list, Sbp0070.class, list); 
			
			// 2.���������ȹ���� ���� ��ȸ
			List<Sbp0070> list_last = service.selectListLast(param);  
			CallSAPHdel.moveListToDs(ds_list_last, Sbp0070.class, list_last);  

			logger.debug("@@@ Sbp0070FindView SUCCESS!!!" + ""); 
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0070FindView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		
		// RFC ��� dataset�� return 
		logger.debug("@@@ ds_list.getRowCount 	 	===>" + ds_list.getRowCount() + "");
		logger.debug("@@@ ds_list_last.getRowCount 	===>" + ds_list_last.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		("ds_list");  
		ds_list_last.setId	("ds_list_last");  
		ds_error.setId		("ds_error");   
		resultVO.addDataset	(ds_list);  
		resultVO.addDataset	(ds_list_last);
		resultVO.addDataset	(ds_error);  	// ����INFO 
		model.addAttribute	("resultVO", resultVO);     

		logger.debug("@@@ Sbp0070FindView addAttribute end" + "");
		
		return view;
	} 
	
	// ��������
	@RequestMapping(value="save")
	public View Sbp0070SaveView(MipParameterVO paramVO, Model model) { 
  
		logger.debug("@@@ Sbp0070SaveView START!!!" + "");
		
		// INPUT DATASET GET  
		 		
		// �������� DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		 
		try { 
				
				// �������� ���� ȣ��	
				service.save(paramVO, model);  
			
				logger.debug("@@@ Sbp0070SaveView SUCCESS!!!" + ""); 
			 
		} catch (BizException e) {  
		 	// ����������Ǿ� �����ڵ尡 ���ϵ� ��� (e.getMessage()�� �����ڵ常 ����� ��)
			logger.info("@@@ Sbp0070SaveView BizException ERROR!!! : " + e.getMessage() + "");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
		} catch (Exception e) { 
			// ����ġ���� ������������ ���		 (e.getMessage()�� �����޼��� ��ü�� ����� ��)
			e.printStackTrace();
			logger.error("@@@ Sbp0070SaveView Exception ERROR!!! : " + e.getMessage() + "");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();  
		logger.debug("@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ ""); 
		ds_error.setId		("ds_error");   
		resultVO.addDataset	(ds_error);  	// ����INFO 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Sbp0070SaveView addAttribute end" + "");

		return view;

	} 
	
}
