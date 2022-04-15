package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0200;
import hdel.sd.com.domain.Com0200ParamVO;
import hdel.sd.com.service.Com0200S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0200")
public class Com0200C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0200S service;
	
	@RequestMapping(value="find")
	public View Com0200FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 		= paramVO.getDataset("ds_list");	// UI�� return�� out dataset  
		 
		try { 
 
			Com0200ParamVO param = new Com0200ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n");  
			System.out.print("\n@@@ dsCond.MATNR	===>"+DatasetUtility.getString(dsCond,"MATNR")	+"\n");
			System.out.print("\n@@@ dsCond.MAKTX		===>"+DatasetUtility.getString(dsCond,"MAKTX")	+"\n");
			System.out.print("\n@@@ dsCond.WHERE	===>"+DatasetUtility.getString(dsCond,"WHERE")	+"\n");
			
			// �Ķ����SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setMatnr(DatasetUtility.getString(dsCond,"MATNR"));		// ���¾�ü�� 
			param.setMaktx(DatasetUtility.getString(dsCond,"MAKTX"));		// 
			param.setWhere(DatasetUtility.getString(dsCond,"WHERE"));

			// ����CALL
			List<Com0200> list = service.find(param);  
			
			ds_list.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(ds_list)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list.appendRow(); 
	    		
	    		// �÷�SET
				for (int iCol=0;iCol<ds_list.getColumnCount();iCol++)
				{    
					if (ds_list.getColumnID(iCol).equals("MATNR")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getMatnr());			// �����ȣ 
					} else if (ds_list.getColumnID(iCol).equals("MAKTX")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getMaktx());			// ���系�� 
					}else if (ds_list.getColumnID(iCol).equals("MEINS")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getMeins());			// �⺻���� 
					}else if (ds_list.getColumnID(iCol).equals("SAMT1")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getSamt1());			// �ǸŴܰ� 
					}else if (ds_list.getColumnID(iCol).equals("SAMT3")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getSamt3());			// ����ǰ ���� 
					}else if (ds_list.getColumnID(iCol).equals("MAN")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getMan());			// �����ο� 
					}else if (ds_list.getColumnID(iCol).equals("JOBHOUR")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getJobhour());			// ǥ�ذ���(�ð�����) 
					}
				} 
			} 
			
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_list); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	 
}
