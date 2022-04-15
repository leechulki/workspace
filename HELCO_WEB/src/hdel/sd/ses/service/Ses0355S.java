package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0355D;
import hdel.sd.ses.domain.Ses0171;
import hdel.sd.ses.domain.Ses0355;
import hdel.sd.ses.domain.Ses0355ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0355S extends SrmService {

	private Ses0355D Ses0355Dao;

	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		Ses0355Dao = sqlSession.getMapper(Ses0355D.class);
	}

	public List<Ses0355> getContItem(Ses0355ParamVO param) {
		return Ses0355Dao.selectContItem(param);
	}

	public List<Ses0355> getContItemStd(Ses0355ParamVO param) {
		return Ses0355Dao.selectContItemStd(param);
	}

	public List<Ses0355> getListDetail(Ses0355ParamVO param) {
		return Ses0355Dao.selectListDetail(param);
	}

	public List<Ses0355> getContInfo(Ses0355ParamVO param) {
		return Ses0355Dao.selectContInfo(param);
	}


	public void mergeZSDT1050(Ses0355 param) {	// ����Header(ZSDT1054) ����
		Ses0355Dao.mergeZSDT1050(param);
	}

	public void deleteZSDT1050(Ses0355 param) {
		Ses0355Dao.deleteZSDT1050(param);
	}

	public void updateZSDT0091(Ses0355 param) {
		Ses0355Dao.updateZSDT0091(param);
	}

	// ��ຯ�����ó��
	public void savePartCost(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		Dataset ds_detail = paramVO.getDataset("ds_detail");		// INPUT DATASET GET 
		Dataset ds_list   = paramVO.getDataset("ds_list");

		try {
			Ses0355 param = new Ses0355();	// ���� �Ķ����
			createDao(session);	 			// DAO ����

			String flag = "";

			for( int i = 0; i < ds_detail.getRowCount(); i++ ) {
				param.setFlag(DatasetUtility.getString(ds_detail, i, "FLAG"));
				param.setMandt(DatasetUtility.getString(ds_detail, i, "MANDT"));
				param.setPspid(DatasetUtility.getString(ds_detail, i, "PSPID"));
				param.setHogi(DatasetUtility.getString(ds_detail, i, "HOGI"));
				param.setSeq(DatasetUtility.getString(ds_detail, i, "SEQ"));
				param.setZcostzseq(DatasetUtility.getString(ds_detail, i, "ZCOSTZSEQ"));
				param.setZccd(DatasetUtility.getString(ds_detail, i, "ZCCD"));
				param.setZcgrp(DatasetUtility.getString(ds_detail, i, "ZCGRP"));
				param.setZcct(DatasetUtility.getString(ds_detail, i, "ZCCT"));
				param.setZuam(DatasetUtility.getString(ds_detail, i, "ZUAM"));
				param.setZcost(DatasetUtility.getString(ds_detail, i, "ZCOST"));
				param.setZprcp(DatasetUtility.getString(ds_detail, i, "ZPRCP"));
				param.setZprdi(DatasetUtility.getString(ds_detail, i, "ZPRDI"));
				param.setZprds(DatasetUtility.getString(ds_detail, i, "ZPRDS"));
				param.setUserid(paramVO.getVariable("G_USER_ID"));
				param.setWaerk(DatasetUtility.getString(ds_detail, i, "WAERK"));
				
				flag = DatasetUtility.getString(ds_detail, i, "FLAG");

				if (flag.equals("D")) {
					deleteZSDT1050(param);
				} else if (flag.equals("U") || flag.equals("I")){
					mergeZSDT1050(param);
				}
			}

			// ��ຯ������ݿ� �κ��� ��ຯ�� ȭ�鿡 ����� ���� ������ ����
			/* 2012.12.20 ����
			Ses0355 param1 = new Ses0355();	// ���� �Ķ����

			if (!"part".equals(paramVO.getVariable("fa_part")))		{	// �ش� �������ο��� ���� ���� �Է� �� ó��
				for( int i = 0; i < ds_list.getRowCount(); i++ ) {
	
					flag = DatasetUtility.getString(ds_list, i, "FLAG");
	
					if ("U".equals(flag)) {
					
						param1.setFlag(DatasetUtility.getString(ds_list,  i, "FLAG"));
						param1.setMandt(DatasetUtility.getString(ds_list, i, "MANDT"));
						param1.setPspid(DatasetUtility.getString(ds_list, i, "PSPID"));
						param1.setHogi(DatasetUtility.getString(ds_list,  i, "HOGI"));
						param1.setSeq(DatasetUtility.getString(ds_list,   i, "SEQ"));
						// ȭ������� KRW, JPY
						String strWaerk = DatasetUtility.getString(ds_list,   i, "WAERK");
						param1.setChwavwr(DatasetUtility.getString(ds_list, i, "CHWAVWR"));
						if ("KRW".equals(strWaerk) || "JPY".equals(strWaerk))	{
							double dChwavwr = 0;
							dChwavwr = Double.parseDouble(DatasetUtility.getString(ds_list, i, "CHWAVWR")) / 100.0;
							// �ݾ׿� 100���� ����.
							param1.setChwavwr(dChwavwr + "");
						}
						param1.setUserid(paramVO.getVariable("G_USER_ID"));
	
						updateZSDT0091(param1);	 // ����Detail ������� �հ� ������Ʈ
					}
				}
			}
			*/

		}  catch (Exception e) {
			throw e;
		}
	}
}
