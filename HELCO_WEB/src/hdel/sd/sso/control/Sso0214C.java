package hdel.sd.sso.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sso.domain.Sso0212;
import hdel.sd.sso.domain.Sso0214;
import hdel.sd.sso.domain.Sso0214ParamVO;
import hdel.sd.sso.service.Sso0214S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("sso0214")
public class Sso0214C {
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sso0214S service;
	
	@RequestMapping(value="find")
	public View Sso0214FindView(MipParameterVO paramVO, Model model) {
		Logger logger = Logger.getLogger(this.getClass());
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list");
		
		try {
			Sso0214ParamVO param = new Sso0214ParamVO();
			
			// DAO ����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // global sysid�� ������  sqlsession �ѱ�
			
			String zzpjt_id = DatasetUtility.getString(dsCond, "ZZPJT_ID");
			String fr_chakd = DatasetUtility.getString(dsCond, "FR_CHAKD");
			String to_chakd = DatasetUtility.getString(dsCond, "TO_CHAKD");
			String vkbur_f = DatasetUtility.getString(dsCond, "VKBUR_F");
			String vkbur_t = DatasetUtility.getString(dsCond, "VKBUR_T");
			String vkgrp_f = DatasetUtility.getString(dsCond, "VKGRP_F");
			String vkgrp_t = DatasetUtility.getString(dsCond, "VKGRP_T");
			String dealer = DatasetUtility.getString(dsCond, "DEALER");
			String manager = DatasetUtility.getString(dsCond, "MANAGER");
			String chkcol = DatasetUtility.getString(dsCond, "CHKCOL");
			String chktpdate2 = DatasetUtility.getString(dsCond, "CHKTPDATE2");
			String chktpdate3 = DatasetUtility.getString(dsCond, "CHKTPDATE3");
			
			// �Ķ���� SET
			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP Client
			param.setZzpjt_id(zzpjt_id); // ������Ʈ
			param.setFr_chakd(fr_chakd); // ����������_����
			param.setTo_chakd(to_chakd); // ����������_��
			param.setVkbur_f(vkbur_f); // �μ�_����
			param.setVkbur_t(vkbur_t); // �μ�_��
			param.setVkgrp_f(vkgrp_f); // ��_����
			param.setVkgrp_t(vkgrp_t); // ��_��
			param.setDealer(dealer); // ���������
			param.setManager(manager); // ���������
			param.setChkcol(chkcol); // ���� �̼���
			param.setChktpdate2(chktpdate2); // ������� ������
			param.setChktpdate3(chktpdate3); // ������ ������
			
			logger.info("eunha chkcol:" + chkcol);
			logger.info("eunha chktpdate2:" + chktpdate2);
			logger.info("eunha chktpdate3:" + chktpdate3);
			
			// ���� CALL
			List<Sso0214> list = service.find(param);
			
			// ȣ����(list)�� �����ͼ�(dsList)�� ����
			for (int iRow = 0; iRow < list.size(); iRow++) {
				// ���߰�
				dsList.appendRow();
				
				// �÷�SET
				for (int iCol = 0; iCol < dsList.getColumnCount(); iCol++) {
					dsList.setColumn(iRow, "ZZPJT_ID", list.get(iRow).getZzpjt_id());
					dsList.setColumn(iRow, "HOGI", list.get(iRow).getHogi());
					dsList.setColumn(iRow, "BSTNK", list.get(iRow).getBstnk());
					dsList.setColumn(iRow, "NAME1", list.get(iRow).getName1());
					dsList.setColumn(iRow, "CHAKD", list.get(iRow).getChakd());
					dsList.setColumn(iRow, "DPCPD", list.get(iRow).getDpcpd());
					dsList.setColumn(iRow, "CHKCOL", list.get(iRow).getChkcol());
					dsList.setColumn(iRow, "CHKTPDATE2", list.get(iRow).getChktpdate2());
					dsList.setColumn(iRow, "CHKTPDATE3", list.get(iRow).getChktpdate3());
					dsList.setColumn(iRow, "POCDT", list.get(iRow).getPocdt());
					dsList.setColumn(iRow, "PTPDT", list.get(iRow).getPtpdt());
					dsList.setColumn(iRow, "TPDATE2", list.get(iRow).getTpdate2());
					dsList.setColumn(iRow, "TPDATE3", list.get(iRow).getTpdate3());
					dsList.setColumn(iRow, "DEALER", list.get(iRow).getDealer());
					dsList.setColumn(iRow, "MANAGER", list.get(iRow).getManager());
					dsList.setColumn(iRow, "SDFIELD", list.get(iRow).getSdfield());
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
	
	@RequestMapping(value="merge")
	public View updateZsdt0218(MipParameterVO paramVO, Model model) {
		List<Sso0214> listSso0214 = new ArrayList<Sso0214>();
		Dataset ds_save_list = paramVO.getDataset("ds_save_list");
		String Pocdt;
		String Ptpdt;
		
		try {
			
			for(int i=0; i < ds_save_list.getRowCount(); i++) {
				Sso0214 sso0214 = new Sso0214();
				DatasetUtil.moveDsRowToObj(ds_save_list, i, sso0214);
				sso0214.setMandt(paramVO.getVariable("G_MANDT"));
				sso0214.setZzpjt_id(ds_save_list.getColumnAsString(i, "ZZPJT_ID"));
				// dataset���� ������ POCDT string�� null�� �� ���� ���� 2021.06.02.
				if(ds_save_list.getColumnAsString(i, "POCDT")==null){
					Pocdt = "";
				}
				else{
					Pocdt = ds_save_list.getColumnAsString(i, "POCDT");
				}
				sso0214.setPocdt(Pocdt);
				// dataset���� ������ PTPDT string�� null�� �� ���� ���� 2021.06.02.
				if(ds_save_list.getColumnAsString(i, "PTPDT")==null){
					Ptpdt = "";
				}
				else{
					Ptpdt = ds_save_list.getColumnAsString(i, "PTPDT");
				}
				sso0214.setPtpdt(Ptpdt);
				sso0214.getTstmp().setTimeStamp(paramVO.getVariable("G_USER_ID"));
				listSso0214.add(sso0214);
			}
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			service.merge(listSso0214);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
}
