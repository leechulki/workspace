package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.service.ComboS;

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

import org.apache.log4j.Logger; 
 
/**
 * �����ڵ� ��� ��ȸ (Com0050C) Control
 * @Comment 
 *     	1. View find ( �����ڵ��� ��ȸ )
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("com0050")
public class Com0050C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private ComboS service;
	
	// 2012.05.30 : GRY : �߰� : �����ڵ�����
	@RequestMapping(value="find") 
	public View Com0050FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond_dd07t = paramVO.getDataset("ds_cond_dd07t");
		
		// INPUT INFO
		System.out.print("\n@@@ ds_cond_dd07t.DOMNAME	===>"+DatasetUtility.getString(ds_cond_dd07t,"DOMNAME"		)+"\n");
		System.out.print("\n@@@ ds_cond_dd07t.DOMVALUE_L===>"+DatasetUtility.getString(ds_cond_dd07t,"DOMVALUE_L"	)+"\n");
		
		// OUTPUT DATASET GET
		Dataset ds_list_dd07t = paramVO.getDataset("ds_list_dd07t");
		ds_list_dd07t.deleteAll();
		
		try { 
			 
			// Connection
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
			
			// PARAM SET
			ComboParamVO param = new ComboParamVO();
			//param.setMandt(paramVO.getVariable("G_MANDT"));							// ä��
			param.setDomname(DatasetUtility.getString(ds_cond_dd07t,"DOMNAME"));   		// �����θ�
			param.setDomvalue_l(DatasetUtility.getString(ds_cond_dd07t,"DOMVALUE_L")); 	// �ڵ� 
	
			List<ComboVO> listCombo = null; 
			 
			listCombo = service.selectListDD07T(param); 
	
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int iRow=0;iRow<listCombo.size();iRow++)
			{   
				// ���߰�
				ds_list_dd07t.appendRow(); 
	    		
	    		// �÷�SET 
				ds_list_dd07t.setColumn(iRow, "CODE"	 , listCombo.get(iRow).getCode());		// �ڵ�  
				ds_list_dd07t.setColumn(iRow, "CODE_NAME", listCombo.get(iRow).getCodeName());	// �ڵ��  
			}  
	
			logger.debug("\n@@@ Com0050FindView SUCCESS!!!" + "\n"); 
			
			// dataset�� return
			MipResultVO resultVO = new MipResultVO();
			ds_list_dd07t.setId	("ds_list_dd07t");  
			resultVO.addDataset(ds_list_dd07t);
			model.addAttribute("resultVO", resultVO);  
			
		} catch (Exception e) { 
			logger.debug("\n@@@ Com0050FindView Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	
		
		return view;
	}
	 
}
