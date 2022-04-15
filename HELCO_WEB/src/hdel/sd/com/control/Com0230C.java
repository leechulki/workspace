package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0230;
import hdel.sd.com.domain.Com0230ParamVO;
import hdel.sd.com.service.Com0230S;

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
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

@Controller
@RequestMapping("com0230")
public class Com0230C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0230S service;
	
	@RequestMapping(value="find")
	public View Com0230FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_sman");  
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_list_sman");	// UI�� return�� out dataset  
		//Dataset 	dsError = paramVO.getDataset("ds_error_buyr");	// UI�� return�� �����޼��� dataset  

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			+"\n");
		System.out.print("\n@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG") 			+"\n"); 
		System.out.print("\n@@@ dsCond.SMAN_CD	===>"+DatasetUtility.getString(dsCond,"SMAN_CD")+"\n");
		System.out.print("\n@@@ dsCond.SMAN_NM	===>"+DatasetUtility.getString(dsCond,"SMAN_NM")+"\n");   
		System.out.print("\n@@@ dsCond.SMAN_FILTER	===>"+DatasetUtility.getString(dsCond,"SMAN_FILTER")+"\n");
		System.out.print("\n@@@ dsCond.SMAN_FLAG	===>"+DatasetUtility.getString(dsCond,"SMAN_FLAG")+"\n");
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Com0230ParamVO param = new Com0230ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  			   // SAP CLIENT
			param.setSman_cd(DatasetUtility.getString(dsCond,"SMAN_CD"));   // ������ڵ�
			param.setSman_nm(DatasetUtility.getString(dsCond,"SMAN_NM")); // ����ڸ�
			param.setSman_filter(DatasetUtility.getString(dsCond,"SMAN_FILTER")); // �������������
			param.setSman_flag(DatasetUtility.getString(dsCond,"SMAN_FLAG"));

			// ����CALL
			List<Com0230> list = service.find(param);  
			
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
			System.out.print("\n@@@ ds_list.getRowCount ===>" + dsList.getRowCount() + "\n"); 
			
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			model.addAttribute("resultVO", resultVO);     

			System.out.print("\n@@@ Com0230FindView SUCCESS!!!" + "\n"); 
			 
		} catch (Exception e) { 
			System.out.print("\n@@@ Com0230FindView Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	 
}
