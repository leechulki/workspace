package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0010;
import hdel.sd.ses.domain.Ses0010ParamVO;
import hdel.sd.ses.service.Ses0010S;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0010")
public class Ses0010C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0010S service;
	
	@RequestMapping(value="find")
	public View Ses0010FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");	// UI�� return�� out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset

		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
		try { 
			dsList = listToDataset(dsList, dsCond, paramVO);
			resultVO.addDataset(dsList);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="abrand")
	public View Ses0010Abrand(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset ds_abrand = paramVO.getDataset("ds_abrand");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset

		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
		try { 

			ds_abrand = AbrandToDataset(ds_abrand, dsCond, paramVO);
			resultVO.addDataset(ds_abrand);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	private Dataset listToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) throws Exception{
		
		Ses0010ParamVO param = new Ses0010ParamVO();
		try
		{
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			param.setMandt(DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setZprdgbn(DatasetUtility.getString(dsCond,"ZPRDGBN"));	// ��ǰ����
			param.setZtplgbn(DatasetUtility.getString(dsCond,"ZTPLGBN"));	// ���ø�����
			param.setBrndcd(DatasetUtility.getString(dsCond,"BRNDCD"));	// �귣���ڵ�
			
			String zwriter =DatasetUtility.getString(dsCond, "ZWRITER");	// 
	
			if (zwriter == null) zwriter = "";
			param.setZwriter(zwriter);	// �ۼ���
	
			List<Ses0010> list = service.find(param);  // ����CALL
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) {	// ȣ����(list)�� ����Ÿ��(dsList)�� ����
	
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
		}catch(Exception e){
			throw e;
		}
	}
	
	private Dataset AbrandToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) throws Exception{
		Ses0010ParamVO param = new Ses0010ParamVO();
		try
		{
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			param.setMandt(DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			List<Map> list = service.selectListAbrand(param);
			dsList.deleteAll();
			// �����׸� �߰�
			int nRow = 0;
			for (int i = 0; i < list.size(); i++) {	// ȣ����(list)�� ����Ÿ��(dsList)�� ����
				nRow = dsList.appendRow();
				Map rowData = list.get(i);

				dsList.setColumn(nRow, "CODE"   , (String)rowData.get("CODE"));
				dsList.setColumn(nRow, "CODE_NAME"   , (String)rowData.get("CODE_NAME"));
			}
			return dsList;
		}catch(Exception e){
			throw e;
		}
	}
}
