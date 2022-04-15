package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0310;
import hdel.sd.ses.domain.Ses0310ParamVO;
import hdel.sd.ses.service.Ses0310S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0310")
public class Ses0310C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0310S service;
	
	@RequestMapping(value="find")
	public View Ses0310FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");	// UI�� return�� out dataset
		// �������� DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return

		try {
			dsList = listToDataset(dsList, dsCond, paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}	    
		resultVO.addDataset(dsList);

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	private Dataset listToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {
		
		Ses0310ParamVO param = new Ses0310ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

		param.setMandt (DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
		param.setZprdgbn(DatasetUtility.getString(dsCond,"ZPRDGBN"));	// ��ǰ����
		param.setZtplgbn(DatasetUtility.getString(dsCond,"ZTPLGBN"));	// ���ø�����
		param.setZwriter(DatasetUtility.getString(dsCond,"ZWRITER"));	// ���ø�����
		// 2020 �귣��
		param.setBrndcd(DatasetUtility.getString(dsCond,"BRNDCD"));	// �귣���ڵ�

		List<Ses0310> list = service.find(param);  // ����CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// ȣ����(list)�� ����Ÿ��(dsList)�� ����

			dsList.appendRow(); 	// ���߰�
			dsList.setColumn(i, "MANDT"   , list.get(i).getMandt());
			dsList.setColumn(i, "ZTPLNO"   , list.get(i).getZtplno()); 
			dsList.setColumn(i, "ZTPLGBN" , list.get(i).getZtplgbn()); 
			dsList.setColumn(i, "ZTPLNM"  , list.get(i).getZtplnm()); 
			dsList.setColumn(i, "ZPRDGBN", list.get(i).getZprdgbn()); 
			dsList.setColumn(i, "ZWRITER" , list.get(i).getZwriter());  
			dsList.setColumn(i, "ZRMK"     , list.get(i).getZrmk());  
			dsList.setColumn(i, "CDATE"    , list.get(i).getCdate());  
			dsList.setColumn(i, "CTIME"     , list.get(i).getCtime()); 
			dsList.setColumn(i, "CUSER"    , list.get(i).getCuser()); 
			dsList.setColumn(i, "UDATE"    , list.get(i).getUdate()); 
			dsList.setColumn(i, "UTIME"    , list.get(i).getUtime());  
			dsList.setColumn(i, "UUSER"    , list.get(i).getUuser());
			
			
			// 2020 �귣��
			dsList.setColumn(i, "BRNDCD"   , list.get(i).getBrndcd());
			dsList.setColumn(i, "BRNDNM"   , list.get(i).getBrndnm());
		}
				
		return dsList;
	}
}
