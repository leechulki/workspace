package hdel.sd.com.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0290;
import hdel.sd.com.domain.Com0290ParamVO;
import hdel.sd.com.service.Com0290S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("com0290")
public class Com0290C {
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0290S service;
	
	@RequestMapping(value="find")
	public View Com0290FindView(MipParameterVO paramVO, Model model) {
		Logger logger = Logger.getLogger(this.getClass());
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list");
		
		try {
			
			Com0290ParamVO param = new Com0290ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // global sysid�� ������ sqlsession �ѱ�
			
			String vkbur = DatasetUtility.getString(dsCond, "VKBUR");
			String vkgrp = DatasetUtility.getString(dsCond, "VKGRP");
			String kunz2 = DatasetUtility.getString(dsCond, "KUNZ2");
			String fr_perdt = DatasetUtility.getString(dsCond, "FR_PERDT");
			String to_perdt = DatasetUtility.getString(dsCond, "TO_PERDT");
			String perno = DatasetUtility.getString(dsCond, "PERNO");
			String manag = DatasetUtility.getString(dsCond, "MANAG");
			String postn = DatasetUtility.getString(dsCond, "POSTN");
			String fr_chakd = DatasetUtility.getString(dsCond, "FR_CHAKD");
			String to_chakd = DatasetUtility.getString(dsCond, "TO_CHAKD");
			
			// �Ķ���� SET
			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP Client
			param.setVkbur(vkbur); // �����
			param.setVkgrp(vkgrp); // ���� �׷�
			param.setKunz2(kunz2); // �����
			param.setFr_perdt(fr_perdt); // �㰡�Ű��� 1
			param.setTo_perdt(to_perdt); // �㰡�Ű��� 2
			param.setPerno(perno); // �㰡��ȣ
			param.setManag(manag); // �������
			param.setPostn(postn); // ������ġ
			param.setFr_chakd(fr_chakd); // ���������� 1
			param.setTo_chakd(to_chakd); // ���������� 2
			
			// ���� CALL
			List<Com0290> list = service.find(param);
			
			// ȣ����(list)�� �����ͼ�(dsList)�� ����
			for (int iRow = 0; iRow < list.size(); iRow++) {
				// ���߰�
				dsList.appendRow();
				
				// �÷�SET
				for (int iCol = 0; iCol < dsList.getColumnCount(); iCol++) {
					dsList.setColumn(iRow, "PERNO", list.get(iRow).getPerno());
					dsList.setColumn(iRow, "PERDT", list.get(iRow).getPerdt());
					dsList.setColumn(iRow, "MANAG", list.get(iRow).getManag());
					dsList.setColumn(iRow, "POSTN", list.get(iRow).getPostn());
					dsList.setColumn(iRow, "BNAME", list.get(iRow).getBname());
					dsList.setColumn(iRow, "ZUSE", list.get(iRow).getZuse());
				}
			}
			
			// �����ͰǼ� INFO
			logger.info("@@@@ ds_list.getRowCount ===>" + dsList.getRowCount());
			
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
