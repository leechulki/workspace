package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0220;
import hdel.sd.com.domain.Com0220ParamVO;
import hdel.sd.com.service.Com0220S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0220")
public class Com0220C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0220S service;
	
	@RequestMapping(value="find")
	public View Com0220FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_atnam");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_atnam = paramVO.getDataset("ds_list_atnam");	// UI�� return�� out dataset  
		 
		try { 
 
			Com0220ParamVO param = new Com0220ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setKlart(DatasetUtility.getString(dsCond,"KLART"));		// ���¾�ü�� 
			param.setClass1(DatasetUtility.getString(dsCond,"CLASS1"));		// 
			param.setAtkla(DatasetUtility.getString(dsCond,"ATKLA"));		// 
			param.setAtnam(DatasetUtility.getString(dsCond,"ATNAM"));		// 
			param.setWhere(DatasetUtility.getString(dsCond,"WHERE"));
			param.setSpart("3");	// LANG default

			if (!"ko".equals(paramVO.getVariable("G_LANG")))		{
				param.setSpart("E");
			}
			// ����CALL
			List<Com0220> list = service.find(param);  
			
			ds_list_atnam.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(ds_list_atnam)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list_atnam.appendRow(); 
	    		
	    		// �÷�SET
				for (int iCol = 0; iCol < ds_list_atnam.getColumnCount(); iCol++)
				{    
					if (ds_list_atnam.getColumnID(iCol).equals("CLASS1")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getClass1()); 
					} else if (ds_list_atnam.getColumnID(iCol).equals("ATKLA")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtkla()); 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATNAM")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtnam()); 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATBEZ")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtbez()); 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATINN")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtinn()); 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATZHL")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtzhl()); 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATWRT")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtwrt()); 
					}else if (ds_list_atnam.getColumnID(iCol).equals("ATWTB")) {
						ds_list_atnam.setColumn(iRow, iCol	, list.get(iRow).getAtwtb());
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
