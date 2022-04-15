package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0030;
import hdel.sd.com.domain.Com0030ParamVO;
import hdel.sd.com.service.Com0030S;

import java.io.IOException;
import java.util.List;
	

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility; 

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

@Controller
@RequestMapping("com0030")
public class Com0030C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0030S service;
	
	@RequestMapping(value="find")
	public View Com0030FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_sman");  
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_list_sman");	// UI�� return�� out dataset  
		//Dataset 	dsError = paramVO.getDataset("ds_error_buyr");	// UI�� return�� �����޼��� dataset  

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			+"\n");
		logger.debug("\n@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG") 			+"\n"); 
		logger.debug("\n@@@ dsCond.SMAN_CD	===>"+DatasetUtility.getString(dsCond,"SMAN_CD")+"\n");
		logger.debug("\n@@@ dsCond.SMAN_NM	===>"+DatasetUtility.getString(dsCond,"SMAN_NM")+"\n");   
		logger.debug("\n@@@ dsCond.SMAN_FILTER	===>"+DatasetUtility.getString(dsCond,"SMAN_FILTER")+"\n");
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Com0030ParamVO param = new Com0030ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  			   // SAP CLIENT
			param.setSman_cd(DatasetUtility.getString(dsCond,"SMAN_CD"));   // ������ڵ�
			param.setSman_nm(DatasetUtility.getString(dsCond,"SMAN_NM")); // ����ڸ�
			param.setSman_filter(DatasetUtility.getString(dsCond,"SMAN_FILTER")); // �������������

			// ����CALL
			List<Com0030> list = service.find(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				dsList.appendRow(); 
	    		
	    		// �÷�SET
				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)
				{     
					dsList.setColumn(iRow, "SMAN_CD"	, list.get(iRow).getSman_cd());		// ������ڵ�  
					dsList.setColumn(iRow, "SMAN_NM"	, list.get(iRow).getSman_nm());	 	// ����ڸ�  
					dsList.setColumn(iRow, "SMAN_INFO"	, list.get(iRow).getSman_info());	// ���������
				} 
			} 
			// �����ͰǼ� INFO
			logger.debug("\n@@@ ds_list.getRowCount ===>" + dsList.getRowCount() + "\n"); 
			
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			model.addAttribute("resultVO", resultVO);     

			logger.debug("\n@@@ Com0030FindView SUCCESS!!!" + "\n"); 
			 
		} catch (Exception e) { 
			logger.debug("\n@@@ Com0030FindView Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	 
	@RequestMapping(value="loginCheck")
	public View Com0030LoginCheckView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_check");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_list_check");	// UI�� return�� out dataset  
		 
		try { 
 
			Com0030ParamVO param = new Com0030ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMandt(DatasetUtility.getString(dsCond,"MANDT"));  				// SAP CLIENT
			param.setLang(DatasetUtility.getString(dsCond,"LANG"));	
			param.setPgmid(DatasetUtility.getString(dsCond,"PGMID"));	
			
			// ����CALL
			List<Com0030> list = service.loginCheck(param);  
			
			dsList.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				logger.debug("\n@@@ list.get(iRow).getCnt()!!!" + list.get(iRow).getMandt() + "\n"); 
				
				// ���߰�
				dsList.appendRow();  
	    		// �÷�SET 
				dsList.setColumn(iRow, "MANDT"	, list.get(iRow).getMandt());			
				
			} 
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
}
