package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0170;
import hdel.sd.com.domain.Com0170ParamVO;
import hdel.sd.com.service.Com0170S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0170")
public class Com0170C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0170S service;
	
	@RequestMapping(value="find")
	public View Com0170FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_atnam");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_atnam 		= paramVO.getDataset("ds_list_atnam");	// UI�� return�� out dataset  
		 
		try { 
 
			Com0170ParamVO param = new Com0170ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n");  
			System.out.print("\n@@@ dsCond.KLART	===>"+DatasetUtility.getString(dsCond,"KLART")	+"\n");
			System.out.print("\n@@@ dsCond.CLASS1		===>"+DatasetUtility.getString(dsCond,"CLASS1")	+"\n");
			System.out.print("\n@@@ dsCond.ATKLA	===>"+DatasetUtility.getString(dsCond,"ATKLA")	+"\n");
			System.out.print("\n@@@ dsCond.ATNAM	===>"+DatasetUtility.getString(dsCond,"ATNAM")	+"\n");
			System.out.print("\n@@@ dsCond.WHERE	===>"+DatasetUtility.getString(dsCond,"WHERE")	+"\n");
			
			// �Ķ����SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setKlart(DatasetUtility.getString(dsCond,"KLART"));		// ���¾�ü�� 
			param.setClass1(DatasetUtility.getString(dsCond,"CLASS1"));		// 
			param.setAtkla(DatasetUtility.getString(dsCond,"ATKLA"));		// 
			param.setAtnam(DatasetUtility.getString(dsCond,"ATNAM"));		// 
			param.setWhere(DatasetUtility.getString(dsCond,"WHERE"));

			// ����CALL
			List<Com0170> list = service.find(param);  
			
			ds_list_atnam.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(ds_list_atnam)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list_atnam.appendRow(); 
	    		
	    		// �÷�SET
				for (int iCol=0;iCol<ds_list_atnam.getColumnCount();iCol++)
				{    
					if (ds_list_atnam.getColumnID(iCol).equals("CLASS1")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getClass1());			// �ŷ�ó�ڵ� 
					} else if (ds_list_atnam.getColumnID(iCol).equals("ATKLA")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtkla());			// �ŷ�ó�� 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATNAM")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtnam());			// �ŷ�ó�� 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATBEZ")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtbez());			// �ŷ�ó�� 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATINN")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtinn());			// �ŷ�ó�� 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATZHL")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtzhl());			// �ŷ�ó�� 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATWRT")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtwrt());			// �ŷ�ó�� 
					}
				} 
			} 
			
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_list_atnam); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	 
}
