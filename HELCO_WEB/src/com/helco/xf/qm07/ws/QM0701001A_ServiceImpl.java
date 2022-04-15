package com.helco.xf.qm07.ws;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.resource.TransactionManager;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.tobesoft.platform.data.Dataset;

public class QM0701001A_ServiceImpl extends AbstractBusinessService implements
		QM0701001A_Service {

    /******************************��������******************************/
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ���������׸��� (ZQMT071) ������ �׸��ȣ ��ȸ
		DatasetSqlRequest zqmt071s
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_S3");	// ITEM seq����

		zqmt071s.addParamObject("ds_cond", ds_list);
		zqmt071s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ���������׸��� (ZQMT071) ���� ���
		DatasetSqlRequest zqmt071i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_I1");

		zqmt071i.addParamObject("ds_list",   ds_list);
		zqmt071i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071i.addParamObject("S_CONFDT",  ctx.getInputVariable("pConfDt"));		// Ȯ������

		// ���������׸��� (ZQMT071) ���� ����
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_U1");

		zqmt071u.addParamObject("ds_list",   ds_list);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ���������׸��� (ZQMT071) ���� ����
		DatasetSqlRequest zqmt071d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_D1");

		zqmt071d.addParamObject("ds_list", ds_list);
		zqmt071d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ���������׸��ȹ�ݾ׵�� (ZQMT072)
		DatasetSqlRequest zqmt072i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_I2");

		zqmt072i.addParamObject("ds_list",   ds_list);
		zqmt072i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt072i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ���������׸��ȹ�ݾ׻��� (ZQMT072)
		DatasetSqlRequest zqmt072d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_D2");

		zqmt072d.addParamObject("ds_list", ds_list);
		zqmt072d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����/���� ��ȣ ��� (ZQMT073)
		DatasetSqlRequest zqmt073i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_I3");

		zqmt073i.addParamObject("ds_list",   ds_list);
		zqmt073i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt073i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����/���� ��ȣ ���� (ZQMT073)
		DatasetSqlRequest zqmt073d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_D3");

		zqmt073d.addParamObject("ds_list", ds_list);
		zqmt073d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		for(int i = 0; i < ds_list.getRowCount(); i++) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{

				// �̏��׸��� �Է� ó�� ZQMT071
				if(ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					zqmt071i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// �׸��ȣ
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// �׸��ȣ
				}else	{
					zqmt071s.setRowIndex(i);
					Dataset dsRtn071 = (Dataset)executor.query(zqmt071s).getResultObject();
					
					if(dsRtn071.getRowCount() > 0 ) {
						zqmt071i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// �׸��ȣ
						zqmt072i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// �׸��ȣ
					} else {
						throw new BizException(String.valueOf(i + 1) + " ��° �׸� ������ ���簡 �߻��Ͽ����ϴ�. ó���� ���� �մϴ�.");
					}
				}

				// ���������׸��� (ZQMT071)
				zqmt071i.setRowIndex(i);
				executor.execute(zqmt071i);

				// ����������ȹ�ݾ� ���
				try {
					zqmt072Insert(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}

				// ����/���� ���� ó��
				if (ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					// ����/���� ��ȣ��� (ZQMT073)
					zqmt073i.setRowIndex(i);
					executor.execute(zqmt073i);
				}
			}else if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// ���������׸���� (ZQMT071)
				zqmt071u.setRowIndex(i);
				executor.execute(zqmt071u);
				
				// ����������ȹ�ݾ� ����
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// ����������ȹ�ݾ� ���
				try {
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));	// �׸��ȣ
					zqmt072Insert(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}
			}else if("D".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// ����������ȹ�ݾ� ����
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// ���������׸���� (ZQMT071)
				zqmt071d.setRowIndex(i);
				executor.execute(zqmt071d);
				
				// ����/���� ���� ����
				zqmt073d.setRowIndex(i);
				executor.execute(zqmt073d);
			}
		}
	}

	// ����������ȹ�ݾ� ��� (ZQMT072)
	public void zqmt072Insert(DatasetSqlExecutor executor, DatasetSqlRequest zqmt072i, Dataset ds_list, int index) throws Exception	{
		String sMonth  = "";
		String sPlanMM = "";
		for (int j = 1; j <= 12; j++)	{
			sPlanMM = String.format("%02d", j);
			sMonth = "M" + sPlanMM;
			if (ds_list.getColumnAsString(index, sMonth) != null)	{
				zqmt072i.addParamObject("S_PLANYM",  ds_list.getColumnAsString(index, "GJAHR") + sPlanMM); // ��ȹ���
				zqmt072i.addParamObject("S_PLANAMT", ds_list.getColumnAsString(index, sMonth)); // ��ȹ�ݾ�
				
				zqmt072i.setRowIndex(index);
				executor.execute(zqmt072i);
			}
		}		
	}

	// ����/���� ���� ���� ó��
	public void matnrProc(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ����/�����ȣ �� �������� ���� (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����� �� �׸� �������� ���� (ZQMT076) �ý��ۿ� ���Ͽ� �� �ڵ� ����� ����
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����(gubun=1) ����(gubun=2) ��ȣ�� ���� ����
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_S5");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����/�����ȣ �������� (ZQMT075)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����� �� �׸� �������� ���� (ZQMT076) ���� �Է°��� �ý��ۿ� ���Ͽ� ����Ȱ� ����
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// �׸� �������� (ZQMT076)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : ����,    S : �ý���
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : �Ѿױ���, U : �ܰ�����

		// ���� ������ ���� (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����ó�� �̷� (ZQMT074)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ���������� �׸��� ����/�����ȣ ��� ��ȸ
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{
			// �� ó���� �������� ���� ���� ó��
			// ����/�����ȣ �� �������� ���� (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// ����� �� �׸� �������� ���� (ZQMT076)
			zqmt076d.addParamObject("SGBN", "S");	// �ý��� ����
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// ����/����  ���� ��� ��ȸ
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// ����� Item ����
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

				String sStym = dsRtnMatnr.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsRtnMatnr.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 0�� ��� ���� ���� (�� ���� �������� �������� �ʾƾ� ��)
				if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
					continue;
				}

				// ���� ó��
				if (hashMap.get(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// ���� ��������� �����ϴ� ��� ""
				}

				// �շϵ� ����/���� ��ȣ�� ���� ó�� 
				zqmt075u.addParamObject("ITEM",  dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"));	// �׸�
				zqmt075u.addParamObject("GUBUN", dsRtnMatnr.getColumnAsString(iMatnr, "GUBUN"));	// ����/�����ȣ ����
				zqmt075u.addParamObject("MATNR", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR"));	// ����/�����ȣ
				zqmt075u.addParamObject("SVAMT", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// ������
				zqmt075u.addParamObject("WAERK", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_WAERK"));	// ȭ�����
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// ����
	
				// ����/���� ���� ���� ���(ZQMT075)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// �׸� ���� ó��
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""�� �ƴϸ� ������� ��� �ʿ�

				// ����� �� �׸� ���������� �ش� �׸� ���� (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// �׸� ���� ���� ��� (ZQMT076) :  ����/�����ȣ���� ����� ���� ����
				zqmt076i.addParamObject("ITEM", sKey);	// �׸�

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// ���� �������ڰ� ���� ��쿡�� �ݿ�
				if (sValue != null && !"".equals(sValue))	{
					// ���� ��������� ���� Update
					zqmt071u.addParamObject("ITEM", sKey);	// �׸�
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// ���ʽ�������

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// ���� ó�� �̷� ���(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
		}
	}

	// ����/���� ���� ���� ó�� (�������� �߰�)
	// 2013.07.17
	public void matnrProcCondAdd(BusinessContext ctx, Dataset ds_cond) throws Exception {
		System.out.println("b");
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ����/�����ȣ �� �������� ���� (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����� �� �׸� �������� ���� (ZQMT076) �ý��ۿ� ���Ͽ� �� �ڵ� ����� ����
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����(gubun=1) ����(gubun=2) ��ȣ�� ���� ���� (������ ����)
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_S6");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����(gubun=1) ����(gubun=2) ������ ��ȸ
		DatasetSqlRequest matnrsDetail
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_S7");

		matnrsDetail.addParamObject("ds_cond", ds_cond);
		matnrsDetail.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����/�����ȣ �������� (ZQMT075)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����� �� �׸� �������� ���� (ZQMT076) �Է°��� �ý��ۿ� ���Ͽ� ����Ȱ� ����
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// �׸� �������� (ZQMT076)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : ����,    S : �ý���
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : �Ѿױ���, U : �ܰ�����

		// ���� ������ ���� (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����ó�� �̷� (ZQMT074)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ���������� �׸��� ����/�����ȣ ��� ��ȸ
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// �� ó���� �������� ���� ���� ó��
			// ����/�����ȣ �� �������� ���� (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);
			System.out.println(i);
			// ����� �� �׸� �������� ���� (ZQMT076)
			zqmt076d.addParamObject("SGBN", "S");	// �ý��� ����
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// ����/����  ���� ��� ��ȸ
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// ����/���� ����ȸ ��� ��ȸ 2013.07.17
			matnrsDetail.setRowIndex(i);
			Dataset dsRtnMatnrDetail = (Dataset)executor.query(matnrsDetail).getResultObject();

			// �����ǿ� ���� ���� Ȯ�� �� ���� ó��
			Dataset dsTempSum = new Dataset();

			// �÷� setting
			dsTempSum.addColumn("ITEM",        (short)1, 256);
			dsTempSum.addColumn("STYM",        (short)1, 256);
			dsTempSum.addColumn("GUBUN",       (short)1, 256);
			dsTempSum.addColumn("MATNR_SVAMT", (short)1, 256);
			dsTempSum.addColumn("MATNR_WAERK", (short)1, 256);
			dsTempSum.addColumn("MATNR",       (short)1, 256);
			dsTempSum.addColumn("QNTY",        (short)1, 256);

			// ����(����) ���� 
			int iPos = 0;

			// �������� Ȯ��
			for(int iDetail = 0; iDetail < dsRtnMatnrDetail.getRowCount(); iDetail++)		{

				boolean bSumCheck = false;

				String sItem_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ITEM");
				String sMatnr_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR");
				String sSpec12_d = dsRtnMatnrDetail.getColumnAsString(iDetail,  "SPEC12");
				int iSpec3fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3FR");
				int iSpec3to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3TO");
				int iSpec5fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5FR");
				int iSpec5to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5TO");
				String sEcsf_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ECSF");
				String sEtm_d    = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ETM");
				String sMatkl_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATKL");
				String sSvamt_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_SVAMT");
				String sWaerk_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_WAERK");
				String sGubun_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "GUBUN");
				String sStym_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "STYM");
				//�ű��߰�(2016)
				String sAuse_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AUSE");        
				String sAcapa_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACAPA");    
				String sAopen_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AOPEN");    
				int iAfqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQFR");    
				int iAfqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQTO");    
				int iAstqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQFR");    
				int iAstqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQTO");    
				String sAdrv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ADRV");    
				String sAspc_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASPC");    
				String sAcd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD1");    
				String sAcd2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD2");    
				int iBfthfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHFR");    
				int iBfthto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHTO");    
				String sBwm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BWM");    
				String sBmopb_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BMOPB");    
				String sBcdm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BCDM");    
				String sBhr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BHR");    
				String sCjm1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1");    
				int iCjm1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QFR");    
				int iCjm1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QTO");    
				String sCjm1m_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1M");    
				String sCjm1fr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1FR");    
				String sChd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHD1");    
				int iChd1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QFR");    
				int iChd1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QTO");    
				String sChs1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHS1");    
				int iChs1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QFR");    
				int iChs1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QTO");    
				String sDeph_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DEPH");    
				int iDephtqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQFR");    
				int iDephtqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQTO");    
				int iEhofr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOFR");    
				int iEhoto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOTO");    
				int iEhtrhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHFR");    
				int iEhtrhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHTO");    
				int iEhpto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPTO");    
				int iEhpfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPFR");    
				int iEhhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHFR");    
				int iEhhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHTO");    
				int iEhvfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVFR");    
				int iEhvto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVTO");    
				String sEhm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "EHM");         
				int iEccafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCAFR");    
				int iEccato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCATO");    
				int iEccbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBFR");    
				int iEccbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBTO");    
				int iEcchfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHFR");    
				int iEcchto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHTO");    
				int iEcaafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAAFR");    
				int iEcaato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAATO");    
				int iEcbbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBFR");    
				int iEcbbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBTO");    
				int iEceefr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEEFR");    
				int iEceeto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEETO");    
				String sEtmm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ETMM");    
				String sErpd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPD");    
				String sErpw_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPW");    
				String sErpr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPR");    
				String sEcrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECRL");    
				String sEcgv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECGV");    
				String sEcwrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWRL");    
				String sEcww_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWW");    
				String sEcwsf_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWSF");    
				String sDsv1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV1");    
				String sDcctv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCCTV");    
				String sDcair_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCAIR");    
				int iEcjjfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJFR");    
				int iEcjjto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJTO");    
				int iEchhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHFR");    
				int iEchhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHTO");    
	 		    String sAstda_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTDA");
	 		 //�ű��߰�(2017.06)
	 		   String sAinov_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AINOV");
	 		   String sAstd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTD");
	 		   String sDeld_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DELD");
	 		   String sDsv2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV2");
	 		   String sDhsbt_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DHSBT");
	 		 
	 		 
				int iQntySum    = 0;	//����(����) �����Ǽ�

				
				
				// ����(����) ���� ��ö��
				for (int iMatnr = iPos; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

					boolean bSpec12 = false;
					boolean bSpec3  = false;
					boolean bSpec5  = false;
					boolean bEcsf   = false;
					boolean bEtm    = false;
					boolean bMatkl  = false;
					
					boolean bAuse   = false;
					boolean bAcapa  = false;
					boolean bAopen  = false;
					boolean bAfq    = false;
					boolean bAstq   = false;
					boolean bAdrv   = false;
					boolean bAspc   = false;
					boolean bAcd1   = false;
					boolean bAcd2   = false;
					boolean bBfth   = false;
					boolean bBwm    = false;
					boolean bBmopb  = false;
					boolean bBcdm   = false;
					boolean bBhr    = false;
					boolean bCjm1   = false;
					boolean bCjm1q  = false;
					boolean bCjm1m  = false;
					boolean bCjm1fr = false;
					boolean bChd1   = false;
					boolean bChd1q  = false;
					boolean bChs1   = false;
					boolean bChs1q  = false;
					boolean bDeph   = false;
					boolean bDephtq = false;
					boolean bEho    = false;
					boolean bEhtrh  = false;
					boolean bEhp    = false;
					boolean bEhh    = false;
					boolean bEhv    = false;
					boolean bEhm    = false;
					boolean bEcca   = false;
					boolean bEccb   = false;
					boolean bEcch   = false;
					boolean bEcaa   = false;
					boolean bEcbb   = false;
					boolean bEcee   = false;
					boolean bEtmm   = false;
					boolean bErpd   = false;
					boolean bErpw   = false;
					boolean bErpr   = false;
					boolean bEcrl   = false;
					boolean bEcgv   = false;
					boolean bEcwrl  = false;
					boolean bEcww   = false;
					boolean bEcwsf  = false;
					boolean bDsv1   = false;
					boolean bDcctv  = false;
					boolean bDcair  = false;
					boolean bEcjj   = false;
					boolean bEchh   = false;
					boolean bAstda  = false;
					boolean bAinov  = false;
					boolean bAstd   = false;
					boolean bDeld  = false;
					boolean bDsv2  = false;
					boolean bDhsbt  = false;
					

				

					String sItemM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ITEM");
					String sMatnrM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATNR");
					String sSpec12M = dsRtnMatnr.getColumnAsString(iMatnr,  "SPEC12");
					int iSpec3M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC3");
					int iSpec5M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC5");
					String sEcsfM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ECSF");
					String sEtmM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ETM");
					String sMatklM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATKL");
					
					//�ű��߰�(2016)
				    String sAuseM =  dsRtnMatnr.getColumnAsString(iMatnr, "AUSE");
					String sAcapaM =  dsRtnMatnr.getColumnAsString(iMatnr, "ACAPA");
					String sAopenM =  dsRtnMatnr.getColumnAsString(iMatnr, "AOPEN");
					int iAfqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "AFQ");
					int iAstqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ASTQ");
					String sAdrvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ADRV");
					String sAspcM =  dsRtnMatnr.getColumnAsString(iMatnr, "ASPC");
					String sAcd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD1");
					String sAcd2M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD2");
					int iBfthM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "BFTH");
					String sBwmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BWM");
					String sBmopbM =  dsRtnMatnr.getColumnAsString(iMatnr, "BMOPB");
					String sBcdmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BCDM");
					String sBhrM =  dsRtnMatnr.getColumnAsString(iMatnr, "BHR");
					String sCjm1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1");
					int iCjm1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CJM1Q");
					String sCjm1mM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1M");
					String sCjm1frM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1FR");
					String sChd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHD1");
					int iChd1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHD1Q");
					String sChs1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHS1");
					int iChs1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHS1Q");
					String sDephM =  dsRtnMatnr.getColumnAsString(iMatnr, "DEPH");
					int iDephtqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "DEPHTQ");
					int iEhoM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHO");
					int iEhtrhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHTRH");
					int iEhpM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHP");
					int iEhhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHH");
					int iEhvM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHV");
					String sEhmM =  dsRtnMatnr.getColumnAsString(iMatnr, "EHM");
					int iEccaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCA");
					int iEccbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCB");
					int iEcchM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCH");
					int iEcaaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECAA");
					int iEcbbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECBB");
					int iEceeM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECEE");
					String sEtmmM =  dsRtnMatnr.getColumnAsString(iMatnr, "ETMM");
					String sErpdM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPD");
					String sErpwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPW");
					String sErprM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPR");
					String sEcrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECRL");
					String sEcgvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECGV");
					String sEcwrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWRL");
					String sEcwwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWW");
					String sEcwsfM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWSF");
					String sDsv1M =  dsRtnMatnr.getColumnAsString(iMatnr, "DSV1");
					String sDcctvM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCCTV");
					String sDcairM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCAIR");
					int iEcjjM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECJJ");
					int iEchhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECHH");
					String sAstdaM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTDA");
					String sAinovM    = dsRtnMatnr.getColumnAsString(iMatnr,  "AINOV");
					String sAstdM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTD");
					String sDeldM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DELD");
					String sDsv2M    = dsRtnMatnr.getColumnAsString(iMatnr,  "DSV2");
					String sDhsbtM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DHSBT");
					
                    // �ű��߰�  ��	
                  
					int iQntyM      = dsRtnMatnr.getColumnAsDouble(iMatnr,  "QNTY").intValue();

					// ITEM��
					if (sItem_d != null && sItem_d.equals(sItemM))		{

						// ����(����)��ȣ �� : �����ϸ� Ȯ��
						if (sMatnr_d != null && sMatnr_d.equals(sMatnrM))	{

							// SPEC�� (�ش� ���� �����ϴ� ���)
							if (sSpec12_d == null || sSpec12_d.equals(""))	{
								bSpec12 = true;
							}else if (sSpec12_d != null && sSpec12M != null && !"".equals(sSpec12M) && sSpec12_d.indexOf(sSpec12M) >= 0)	{
								bSpec12 = true;
							}
							
							
							// �ӵ� ��
							if (iSpec3fr_d <= iSpec3M && iSpec3M <= iSpec3to_d )	{
								bSpec3 = true;
							}

							// �ν� ��
							if (iSpec5fr_d <= iSpec5M && iSpec5M <= iSpec5to_d )	{
								bSpec5 = true;
							}

							// CAR SAFETY �� (�ش� ���� �����ϴ� ���)
							if (sEcsf_d == null || sEcsf_d.equals(""))	{
								bEcsf = true;
							}else if (sEcsfM != null && sEcsfM.indexOf(sEcsf_d) >= 0)	{
								bEcsf = true;
							}

							// TM �� (�ش� ���� �����ϴ� ���)
						/*	if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
								bEtm = true;
							}
						*/	
							if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtm_d != null && sEtmM != null && !"".equals(sEtmM) && sEtm_d.indexOf(sEtmM) >= 0)	{
								bEtm = true;
							}
						    else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
							bEtm = true;
						    }
							

							// BLOK NO �� (�ش� ���� �����ϴ� ���)
							if (sMatkl_d == null || sMatkl_d.equals(""))	{
								bMatkl = true;
							}else if (sMatklM != null && sMatklM.indexOf(sMatkl_d) >= 0)	{
								bMatkl = true;
							}
							//2016.01 �߰�
							// �뵵 �� 
					       if (sAuse_d == null || sAuse_d.equals(""))	{
								bAuse = true;
							}else if (sAuseM != null && sAuseM.indexOf(sAuse_d) >= 0)	{
								bAuse  = true;
							}
							// �뷮 �� 
						   if (sAcapa_d == null || sAcapa_d.equals(""))	{
								bAcapa = true;
							}else if (sAcapaM != null && sAcapaM.indexOf(sAcapa_d) >= 0)	{
								bAcapa  = true;
							}
							// ���� �� 
						    if (sAopen_d == null || sAopen_d.equals(""))	{
								bAopen = true;
							}else if (sAopenM != null && sAopenM.indexOf(sAopen_d) >= 0)	{
								bAopen  = true;
							}
							// ���� �� 
							if (iAfqfr_d <= iAfqM && iAfqM <= iAfqto_d )	{
								bAfq = true;
							}
						//System.out.println(" iAfqfr_d : " +iAfqfr_d
						//		           +  " iAfqM : " + iAfqM  +   " iAfqto_d : " + iAfqto_d );
				              						
					    	// ���� ���� ��
						   if (iAstqfr_d <= iAstqM && iAstqM <= iAstqto_d )	{
								bAstq = true;
							}
							// ������ �� 
			   		       if (sAdrv_d == null || sAdrv_d.equals(""))	{
								bAdrv = true;
							}else if (sAdrvM != null && sAdrvM.indexOf(sAdrv_d) >= 0)	{
								bAdrv  = true;
							}
							// �ù漭 �� 
			   			    if (sAspc_d == null || sAspc_d.equals(""))	{
								bAspc = true;
							}else if (sAspcM != null && sAspcM.indexOf(sAspc_d) >= 0)	{
								bAspc  = true;
							}
							// ����/�ؿ� ���� �� 
			   			    if (sAcd1_d == null || sAcd1_d.equals(""))	{
								bAcd1 = true;
							}else if (sAcd1M != null && sAcd1M.indexOf(sAcd1_d) >= 0)	{
								bAcd1  = true;
							}
							//  �����ڵ�  �� 
			   			    if (sAcd2_d == null || sAcd2_d.equals(""))	{
								bAcd2 = true;
							}else if (sAcd2M != null && sAcd2M.indexOf(sAcd2_d) >= 0)	{
								bAcd2  = true;
							}
							// FLOOR �β�  ��
							if (iBfthfr_d <= iBfthM && iBfthM <= iBfthto_d )	{
								bBfth = true;
							}
							//  CAR WALL  �� 
			   			   if (sBwm_d == null || sBwm_d.equals(""))	{
								bBwm = true;
							}else if (sBwmM != null && sBwmM.indexOf(sBwm_d) >= 0)	{
								bBwm  = true;
							}
							//  main opb  �� 
			   			    if (sBmopb_d == null || sBmopb_d.equals(""))	{
								bBmopb = true;
							}else if (sBmopbM != null && sBmopbM.indexOf(sBmopb_d) >= 0)	{
								bBmopb  = true;
							}
							//    CAR DOOR �� 
			   			   if (sBcdm_d == null || sBcdm_d.equals(""))	{
								bBcdm = true;
							}else if (sBcdmM != null && sBcdmM.indexOf(sBcdm_d) >= 0)	{
								bBcdm  = true;
							}
							// handrail  �� 
			   			    if (sBhr_d == null || sBhr_d.equals(""))	{
								bBhr = true;
							}else if (sBhrM != null && sBhrM.indexOf(sBhr_d) >= 0)	{
								bBhr  = true;
							}
							// jamb  �� 
			   			    if (sCjm1_d == null || sCjm1_d.equals(""))	{
								bCjm1 = true;
							}else if (sCjm1M != null && sCjm1M.indexOf(sCjm1_d) >= 0)	{
								bCjm1  = true;
							}
							// jmb ����  ��
							if (iCjm1qfr_d <= iCjm1qM && iCjm1qM <= iCjm1qto_d )	{
								bCjm1q = true;
							}
							// jamb ���� �� 
			   			    if (sCjm1m_d == null || sCjm1m_d.equals(""))	{
								bCjm1m = true;
							}else if (sCjm1mM != null && sCjm1mM.indexOf(sCjm1m_d) >= 0)	{
								bCjm1m  = true;
							}
							// jamb ��ȭ���� �� 
			   			    if (sCjm1fr_d == null || sCjm1fr_d.equals(""))	{
								bCjm1fr = true;
							}else if (sCjm1frM != null && sCjm1frM.indexOf(sCjm1fr_d) >= 0)	{
								bCjm1fr  = true;
							}
							// HATCH DOOR  �� 
			   			    if (sChd1_d == null || sChd1_d.equals(""))	{
								bChd1 = true;
							}else if (sChd1M != null && sChd1M.indexOf(sChd1_d) >= 0)	{
								bChd1  = true;
							}
							// HATCH DOOR ����  ��
							if (iChd1qfr_d <= iChd1qM && iChd1qM <= iChd1qto_d )	{
								bChd1q = true;
							}
							// HATCH SILL  �� 
			   			   if (sChs1_d == null || sChs1_d.equals(""))	{
								bChs1 = true;
							}else if (sChs1M != null && sChs1M.indexOf(sChs1_d) >= 0)	{
								bChs1  = true;
							}
							// HATCH SILL ����  ��
							if (iChs1qfr_d <= iChs1qM && iChs1qM <= iChs1qto_d )	{
								bChs1q = true;
							}
							// �����ȭ��ġ  �� 
			   			    if (sDeph_d == null || sDeph_d.equals(""))	{
								bDeph = true;
							}else if (sDephM != null && sDephM.indexOf(sDeph_d) >= 0)	{
								bDeph  = true;
							}
							// �����ȭ��ġ ���� ���  ��
							if (iDephtqfr_d <= iDephtqM && iDephtqM <= iDephtqto_d )	{
								bDephtq = true;
							}
							// �°��� overhead  ��
							if (iEhofr_d <= iEhoM && iEhoM <= iEhoto_d )	{
								bEho = true;
							}
							// �°��� travel heigth   ��
							if (iEhtrhfr_d <= iEhtrhM && iEhtrhM <= iEhtrhto_d )	{
								bEhtrh = true;
							}
							//System.out.println(" iEhtrhfr_d : " +iEhtrhfr_d
								//	           +  " iEhtrhM : " + iEhtrhM  +   " iEhtrhto_d : " + iEhtrhto_d );
							// �°��� pit   ��
							if (iEhpfr_d <= iEhpM && iEhpM <= iEhpto_d )	{
								bEhp = true;
							}
							// �°���  ����   ��
							if (iEhhfr_d <= iEhhM && iEhhM <= iEhhto_d )	{
								bEhh = true;
							}
							// �°��� ����   ��
								if (iEhvfr_d <= iEhvM && iEhvM <= iEhvto_d )	{
								bEhv = true;
							}
							// �°��� ����   ��
							if (sEhm_d == null || sEhm_d.equals(""))	{
								bEhm = true;
							}else if (sEhmM != null && sEhmM.indexOf(sEhm_d) >= 0)	{
								bEhm  = true;
							}
							// car ca    ��
							if (iEccafr_d <= iEccaM && iEccaM <= iEccato_d )	{
								bEcca = true;
							}
							// car cb    ��
							if (iEccbfr_d <= iEccbM && iEccbM <= iEccbto_d )	{
								bEccb = true;
							}
							// car ch    ��
							if (iEcchfr_d <= iEcchM && iEcchM <= iEcchto_d )	{
								bEcch = true;
							}
							// door jj    ��
							if (iEcjjfr_d <= iEcjjM && iEcjjM <= iEcjjto_d )	{
								bEcjj = true;
							}
							// door hh   ��
							if (iEchhfr_d <= iEchhM && iEchhM <= iEchhto_d )	{
								bEchh = true;
							}
							// car aa  ��
							if (iEcaafr_d <= iEcaaM && iEcaaM <= iEcaato_d )	{
								bEcaa = true;
							}
							// car bb  ��
							if (iEcbbfr_d <= iEcbbM && iEcbbM <= iEcbbto_d )	{
								bEcbb = true;
							}
							// car ee  ��
							if (iEceefr_d <= iEceeM && iEceeM <= iEceeto_d )	{
								bEcee = true;
							}
							// motor �뷮  �� 
			   			    if (sEtmm_d == null || sEtmm_d.equals(""))	{
								bEtmm = true;
							}else if (sEtmmM != null && sEtmmM.indexOf(sEtmm_d) >= 0)	{
								bEtmm  = true;
							}					
			   			    // rope ����  �� 
			   			    if (sErpd_d == null || sErpd_d.equals(""))	{
								bErpd = true;
							}else if (sErpdM != null && sErpdM.indexOf(sErpd_d) >= 0)	{
								bErpd  = true;
							}
			   			    // rope ����  �� 
			   			    if (sErpw_d == null || sErpw_d.equals(""))	{
								bErpw = true;
							}else if (sErpwM != null && sErpwM.indexOf(sErpw_d) >= 0)	{
								bErpw  = true;
							}
			   			    // rope roping �� 
			   			    if (sErpr_d == null || sErpr_d.equals(""))	{
								bErpr = true;
							}else if (sErprM != null && sErprM.indexOf(sErpr_d) >= 0)	{
								bErpr  = true;
							}
			   			    // car rail(k) �� 
			   			    if (sEcrl_d == null || sEcrl_d.equals(""))	{
								bEcrl = true;
							}else if (sEcrlM != null && sEcrlM.indexOf(sEcrl_d) >= 0)	{
								bEcrl  = true;
							}
			   			    // car governor �� 
			   			    if (sEcgv_d == null || sEcgv_d.equals(""))	{
								bEcgv = true;
							}else if (sEcgvM != null && sEcgvM.indexOf(sEcgv_d) >= 0)	{
								bEcgv  = true;
							}
			   			    // cwt rail (k) �� 
			   			    if (sEcwrl_d == null || sEcwrl_d.equals(""))	{
								bEcwrl = true;
							}else if (sEcwrlM != null && sEcwrlM.indexOf(sEcwrl_d) >= 0)	{
								bEcwrl  = true;
							}	   			    
			   			    // cwt weight  �� 
			   			    if (sEcww_d == null || sEcww_d.equals(""))	{
								bEcww = true;
							}else if (sEcwwM != null && sEcwwM.indexOf(sEcww_d) >= 0)	{
								bEcww  = true;
							}	   	
			   			    // cwt safety  �� 
			   			    if (sEcwsf_d == null || sEcwsf_d.equals(""))	{
								bEcwsf = true;
							}else if (sEcwsfM != null && sEcwsfM.indexOf(sEcwsf_d) >= 0)	{
								bEcwsf  = true;
							}	  
			   			    // ���ù�  �� 
			   			    if (sDsv1_d == null || sDsv1_d.equals(""))	{
								bDsv1 = true;
							}else if (sDsv1M != null && sDsv1M.indexOf(sDsv1_d) >= 0)	{
								bDsv1  = true;
							}	  
			   			    // CCTVī�޶�  �� 
			   			    if (sDcctv_d == null || sDcctv_d.equals(""))	{
								bDcctv = true;
							}else if (sDcctvM != null && sDcctvM.indexOf(sDcctv_d) >= 0)	{
								bDcctv  = true;
							}	  
			   			    // CAR ������   �� 
			   			    if (sDcair_d == null || sDcair_d.equals(""))	{
								bDcair = true;
							}else if (sDcairM != null && sDcairM.indexOf(sDcair_d) >= 0)	{
								bDcair  = true;
							}
			   			   //�����㰡��
			   			    if (sAstda_d == null || sAstda_d.equals(""))	{
								bAstda = true;
							}else if (sAstdaM != null && sAstdaM.indexOf(sAstda_d) >= 0)	{
								bAstda  = true;
							}
				   			//�̳�� ���뿩��
			   			    if (sAinov_d == null || sAinov_d.equals(""))	{
								bAinov = true;
							}else if (sAinovM != null && sAinovM.indexOf(sAinov_d) >= 0)	{
								bAinov  = true;
							}
				   		 //2013�� 9�� 15�� ���� �����㰡
			   			    if (sAstd_d == null || sAstd_d.equals(""))	{
								bAstd = true;
							}else if (sAstdM != null && sAstdM.indexOf(sAstd_d) >= 0)	{
								bAstd  = true;
							}
				   		 //ELD ����
			   			    if (sDeld_d == null || sDeld_d.equals(""))	{
			   			    	bDeld = true;
							}else if (sDeldM != null && sDeldM.indexOf(sDeld_d) >= 0)	{
								bDeld  = true;
							}
				   		//���ù�2
			   			    if (sDsv2_d == null || sDsv2_d.equals(""))	{
								bDsv2 = true;
							}else if (sDsv2M != null && sDsv2M.indexOf(sDsv2_d) >= 0)	{
								bDsv2  = true;
							}
				   	   //�������ư
			   			    if (sDhsbt_d == null || sDhsbt_d.equals(""))	{
								bDhsbt = true;
							}else if (sDhsbtM != null && sDhsbtM.indexOf(sDhsbt_d) >= 0)	{
								bDhsbt  = true;
							}
			   			    
							 
                            /*================================����Ϸ� 2016.01 ======================================*/
			   			    
							// ��������� ���� (����Ǽ� ����)
							if (bSpec12 && bSpec3 && bSpec5 && bEcsf && bEtm && bMatkl && bAuse && bAcapa && bAopen && bAfq && bAstq && bAdrv && bAspc && bAcd1
									&& bAcd2 && bBfth && bBwm  && bBmopb && bBcdm && bBhr && bCjm1 && bCjm1q && bCjm1m && bCjm1fr && bChd1 && bChd1q && bChs1 && bChs1q
									&& bDeph && bDephtq && bEho && bEhtrh && bEhtrh && bEhp && bEhh && bEhv && bEhm && bEcca && bEccb && bEcch && bEcaa && bEcbb && bEcee  
									&& bEtmm && bErpd && bErpw && bErpr && bEcrl && bEcgv && bEcwrl && bEcww && bEcwsf && bDsv1 && bDcctv && bDcair && bEcjj && bEchh && bAstda
									&& bAinov && bAstd && bDeld && bDsv2 && bDhsbt)	{
								
								   iQntySum += iQntyM;

								// ���� ó���뵵
								bSumCheck = true;
							}

						}else	{	// ����(����) ��ȣ�� �����ϸ�
							// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
							// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

							//if (bSumCheck)	{
								// �ӽ�Dataset�� �߰�
								int iRow = dsTempSum.appendRow();
								
								dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
								dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
								dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
								dsTempSum.setColumn(iRow, "STYM",  sStym_d);
								dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
								dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
								dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
	
								bSumCheck = false;
							//}

							break;
						}
					}else	{	// ITEM�� �����ϸ� ���������� �������� ���� SKIP
						// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
						// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

						//if (bSumCheck)		{
							// �ӽ�Dataset�� �߰�
							int iRow = dsTempSum.appendRow();
							
							dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
							dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
							dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
							dsTempSum.setColumn(iRow, "STYM",  sStym_d);
							dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
							dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
							dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
							
							bSumCheck = false;
						//}

						break;
					}

					//�ڷ��� ��ġ
					iPos++;
				}
				// �������� ����
				if (iPos == dsRtnMatnr.getRowCount())	{
					// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
					// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

					//if (bSumCheck)		{
						// �ӽ�Dataset�� �߰�
						int iRow = dsTempSum.appendRow();
						
						dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
						dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
						dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
						dsTempSum.setColumn(iRow, "STYM",  sStym_d);
						dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
						dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
						dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
						
						bSumCheck = false;
					//}
					// ���� �Ǽ� �������� ó�� ���ʿ�
					break;
				}

				//
			}

			/*
			// log���
			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{
				System.out.println(" ITEM : " + dsTempSum.getColumnAsString(iMatnr, "ITEM") + "     " +
						           " MATNR : " + dsTempSum.getColumnAsString(iMatnr, "MATNR") + "     " +
						           " GUBUN : " + dsTempSum.getColumnAsString(iMatnr, "GUBUN") + "     " +
						           " STYM : " + dsTempSum.getColumnAsString(iMatnr, "STYM") + "     " +
						           " QNTY : " + dsTempSum.getColumnAsString(iMatnr, "QNTY") + "     " +
						           " MATNR_SVAMT : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT") + "     " +
						           " MATNR_WAERK : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK") + "....."
						);
			}

			//TransactionManager.rollback();

			*/
			
//			/* ���� ��ȸ ��� Ȯ�� �� ó��
			// ����� Item ����
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{

				String sStym = dsTempSum.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsTempSum.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
				// ���� ó���� �ǵ��� ��û�� ���Ͽ� comment

				// 0�� ��� ���� ���� (�� ���� �������� �������� �ʾƾ� ��)
				//if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
				//	continue;
				//}


				// ���� ó��
				if (hashMap.get(dsTempSum.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsTempSum.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// ���� ��������� �����ϴ� ��� ""
				}

				// �շϵ� ����/���� ��ȣ�� ���� ó�� 
				zqmt075u.addParamObject("ITEM",  dsTempSum.getColumnAsString(iMatnr, "ITEM"));	// �׸�
				zqmt075u.addParamObject("GUBUN", dsTempSum.getColumnAsString(iMatnr, "GUBUN"));	// ����/�����ȣ ����
				zqmt075u.addParamObject("MATNR", dsTempSum.getColumnAsString(iMatnr, "MATNR"));	// ����/�����ȣ
				zqmt075u.addParamObject("SVAMT", dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// ������
				zqmt075u.addParamObject("WAERK", dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK"));	// ȭ�����
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// ����
	
				// ����/���� ���� ���� ���(ZQMT075)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// �׸� ���� ó��
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""�� �ƴϸ� ������� ��� �ʿ�

				// ����� �� �׸� ���������� �ش� �׸� ���� (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// �׸� ���� ���� ��� (ZQMT076) :  ����/�����ȣ���� ����� ���� ����
				zqmt076i.addParamObject("ITEM", sKey);	// �׸�

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// ���� �������ڰ� ���� ��쿡�� �ݿ�
				if (sValue != null && !"".equals(sValue))	{
					// ���� ��������� ���� Update
					zqmt071u.addParamObject("ITEM", sKey);	// �׸�
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// ���ʽ�������

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// ���� ó�� �̷� ���(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
//			*/
		}
	}

	// ����/���� ���� ���� ó�� (�������� �߰�)
	// TEST
	public void matnrProcCondAdd3(BusinessContext ctx, Dataset ds_cond) throws Exception {
		//System.out.println("a");
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ����/�����ȣ �� �������� ���� (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����� �� �׸� �������� ���� (ZQMT076) �ý��ۿ� ���Ͽ� �� �ڵ� ����� ����
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����(gubun=1) ����(gubun=2) ��ȣ�� ���� ���� (������ ����)
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001T_S6");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����(gubun=1) ����(gubun=2) ������ ��ȸ
		DatasetSqlRequest matnrsDetail
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001T_S7");

		matnrsDetail.addParamObject("ds_cond", ds_cond);
		matnrsDetail.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����/�����ȣ �������� (ZQMT075)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����� �� �׸� �������� ���� (ZQMT076) �Է°��� �ý��ۿ� ���Ͽ� ����Ȱ� ����
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// �׸� �������� (ZQMT076)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : ����,    S : �ý���
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : �Ѿױ���, U : �ܰ�����

		// ���� ������ ���� (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����ó�� �̷� (ZQMT074)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ���������� �׸��� ����/�����ȣ ��� ��ȸ
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{
	
			// �� ó���� �������� ���� ���� ó��
			// ����/�����ȣ �� �������� ���� (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);
			System.out.println(i);
			// ����� �� �׸� �������� ���� (ZQMT076)
			zqmt076d.addParamObject("SGBN", "S");	// �ý��� ����
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// ����/����  ���� ��� ��ȸ
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// ����/���� ����ȸ ��� ��ȸ 2013.07.17
			matnrsDetail.setRowIndex(i);
			Dataset dsRtnMatnrDetail = (Dataset)executor.query(matnrsDetail).getResultObject();

			// �����ǿ� ���� ���� Ȯ�� �� ���� ó��
			Dataset dsTempSum = new Dataset();

			// �÷� setting
			dsTempSum.addColumn("ITEM",        (short)1, 256);
			dsTempSum.addColumn("STYM",        (short)1, 256);
			dsTempSum.addColumn("GUBUN",       (short)1, 256);
			dsTempSum.addColumn("MATNR_SVAMT", (short)1, 256);
			dsTempSum.addColumn("MATNR_WAERK", (short)1, 256);
			dsTempSum.addColumn("MATNR",       (short)1, 256);
			dsTempSum.addColumn("QNTY",        (short)1, 256);

			// ����(����) ���� 
			int iPos = 0;

			// �������� Ȯ��
			for(int iDetail = 0; iDetail < dsRtnMatnrDetail.getRowCount(); iDetail++)		{

				boolean bSumCheck = false;

				String sItem_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ITEM");
				String sMatnr_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR");
				String sSpec12_d = dsRtnMatnrDetail.getColumnAsString(iDetail,  "SPEC12");
				int iSpec3fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3FR");
				int iSpec3to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3TO");
				int iSpec5fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5FR");
				int iSpec5to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5TO");
				String sEcsf_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ECSF");
				String sEtm_d    = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ETM");
				String sMatkl_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATKL");
				String sSvamt_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_SVAMT");
				String sWaerk_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_WAERK");
				String sGubun_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "GUBUN");
				String sStym_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "STYM");
				//�ű��߰�(2016)
				String sAuse_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AUSE");        
				String sAcapa_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACAPA");    
				String sAopen_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AOPEN");    
				int iAfqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQFR");    
				int iAfqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQTO");    
				int iAstqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQFR");    
				int iAstqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQTO");    
				String sAdrv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ADRV");    
				String sAspc_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASPC");    
				String sAcd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD1");    
				String sAcd2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD2");    
				int iBfthfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHFR");    
				int iBfthto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHTO");    
				String sBwm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BWM");    
				String sBmopb_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BMOPB");    
				String sBcdm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BCDM");    
				String sBhr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BHR");    
				String sCjm1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1");    
				int iCjm1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QFR");    
				int iCjm1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QTO");    
				String sCjm1m_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1M");    
				String sCjm1fr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1FR");    
				String sChd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHD1");    
				int iChd1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QFR");    
				int iChd1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QTO");    
				String sChs1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHS1");    
				int iChs1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QFR");    
				int iChs1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QTO");    
				String sDeph_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DEPH");    
				int iDephtqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQFR");    
				int iDephtqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQTO");    
				int iEhofr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOFR");    
				int iEhoto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOTO");    
				int iEhtrhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHFR");    
				int iEhtrhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHTO");    
				int iEhpto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPTO");    
				int iEhpfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPFR");    
				int iEhhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHFR");    
				int iEhhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHTO");    
				int iEhvfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVFR");    
				int iEhvto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVTO");    
				String sEhm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "EHM");         
				int iEccafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCAFR");    
				int iEccato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCATO");    
				int iEccbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBFR");    
				int iEccbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBTO");    
				int iEcchfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHFR");    
				int iEcchto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHTO");    
				int iEcaafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAAFR");    
				int iEcaato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAATO");    
				int iEcbbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBFR");    
				int iEcbbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBTO");    
				int iEceefr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEEFR");    
				int iEceeto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEETO");    
				String sEtmm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ETMM");    
				String sErpd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPD");    
				String sErpw_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPW");    
				String sErpr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPR");    
				String sEcrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECRL");    
				String sEcgv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECGV");    
				String sEcwrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWRL");    
				String sEcww_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWW");    
				String sEcwsf_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWSF");    
				String sDsv1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV1");    
				String sDcctv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCCTV");    
				String sDcair_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCAIR");    
				int iEcjjfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJFR");    
				int iEcjjto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJTO");    
				int iEchhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHFR");    
				int iEchhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHTO");    
	 		    String sAstda_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTDA");
	 		   String sDuopb_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DUOPB");
		 		 //�ű��߰�(2017.06)
	 		   String sAinov_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AINOV");
	 		   String sAstd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTD");
	 		   String sDeld_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DELD");
	 		   String sDsv2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV2");
	 		   String sDhsbt_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DHSBT");

				int iQntySum    = 0;	//����(����) �����Ǽ�

				// ����(����) ���� ��ö��
				for (int iMatnr = iPos; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

					boolean bSpec12 = false;
					boolean bSpec3  = false;
					boolean bSpec5  = false;
					boolean bEcsf   = false;
					boolean bEtm    = false;
					boolean bMatkl  = false;
					
					boolean bAuse   = false;
					boolean bAcapa  = false;
					boolean bAopen  = false;
					boolean bAfq    = false;
					boolean bAstq   = false;
					boolean bAdrv   = false;
					boolean bAspc   = false;
					boolean bAcd1   = false;
					boolean bAcd2   = false;
					boolean bBfth   = false;
					boolean bBwm    = false;
					boolean bBmopb  = false;
					boolean bBcdm   = false;
					boolean bBhr    = false;
					boolean bCjm1   = false;
					boolean bCjm1q  = false;
					boolean bCjm1m  = false;
					boolean bCjm1fr = false;
					boolean bChd1   = false;
					boolean bChd1q  = false;
					boolean bChs1   = false;
					boolean bChs1q  = false;
					boolean bDeph   = false;
					boolean bDephtq = false;
					boolean bEho    = false;
					boolean bEhtrh  = false;
					boolean bEhp    = false;
					boolean bEhh    = false;
					boolean bEhv    = false;
					boolean bEhm    = false;
					boolean bEcca   = false;
					boolean bEccb   = false;
					boolean bEcch   = false;
					boolean bEcaa   = false;
					boolean bEcbb   = false;
					boolean bEcee   = false;
					boolean bEtmm   = false;
					boolean bErpd   = false;
					boolean bErpw   = false;
					boolean bErpr   = false;
					boolean bEcrl   = false;
					boolean bEcgv   = false;
					boolean bEcwrl  = false;
					boolean bEcww   = false;
					boolean bEcwsf  = false;
					boolean bDsv1   = false;
					boolean bDcctv  = false;
					boolean bDcair  = false;
					boolean bEcjj   = false;
					boolean bEchh   = false;
					boolean bAstda  = false;
					boolean bDuopb  = false;
					boolean bAinov  = false;
					boolean bAstd   = false;
					boolean bDeld  = false;
					boolean bDsv2  = false;
					boolean bDhsbt  = false;

					String sItemM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ITEM");
					String sMatnrM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATNR");
					String sSpec12M = dsRtnMatnr.getColumnAsString(iMatnr,  "SPEC12");
					int iSpec3M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC3");
					int iSpec5M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC5");
					String sEcsfM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ECSF");
					String sEtmM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ETM");
					String sMatklM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATKL");
					
					//�ű��߰�(2016)
				    String sAuseM =  dsRtnMatnr.getColumnAsString(iMatnr, "AUSE");
					String sAcapaM =  dsRtnMatnr.getColumnAsString(iMatnr, "ACAPA");
					String sAopenM =  dsRtnMatnr.getColumnAsString(iMatnr, "AOPEN");
					int iAfqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "AFQ");
					int iAstqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ASTQ");
					String sAdrvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ADRV");
					String sAspcM =  dsRtnMatnr.getColumnAsString(iMatnr, "ASPC");
					String sAcd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD1");
					String sAcd2M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD2");
					int iBfthM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "BFTH");
					String sBwmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BWM");
					String sBmopbM =  dsRtnMatnr.getColumnAsString(iMatnr, "BMOPB");
					String sBcdmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BCDM");
					String sBhrM =  dsRtnMatnr.getColumnAsString(iMatnr, "BHR");
					String sCjm1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1");
					int iCjm1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CJM1Q");
					String sCjm1mM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1M");
					String sCjm1frM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1FR");
					String sChd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHD1");
					int iChd1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHD1Q");
					String sChs1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHS1");
					int iChs1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHS1Q");
					String sDephM =  dsRtnMatnr.getColumnAsString(iMatnr, "DEPH");
					int iDephtqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "DEPHTQ");
					int iEhoM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHO");
					int iEhtrhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHTRH");
					int iEhpM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHP");
					int iEhhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHH");
					int iEhvM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHV");
					String sEhmM =  dsRtnMatnr.getColumnAsString(iMatnr, "EHM");
					int iEccaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCA");
					int iEccbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCB");
					int iEcchM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCH");
					int iEcaaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECAA");
					int iEcbbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECBB");
					int iEceeM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECEE");
					String sEtmmM =  dsRtnMatnr.getColumnAsString(iMatnr, "ETMM");
					String sErpdM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPD");
					String sErpwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPW");
					String sErprM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPR");
					String sEcrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECRL");
					String sEcgvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECGV");
					String sEcwrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWRL");
					String sEcwwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWW");
					String sEcwsfM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWSF");
					String sDsv1M =  dsRtnMatnr.getColumnAsString(iMatnr, "DSV1");
					String sDcctvM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCCTV");
					String sDcairM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCAIR");
					int iEcjjM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECJJ");
					int iEchhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECHH");
					String sAstdaM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTDA");
					String sDuopbM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DUOPB");
                    // �ű��߰�  ��	
					String sAinovM    = dsRtnMatnr.getColumnAsString(iMatnr,  "AINOV");
					String sAstdM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTD");
					String sDeldM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DELD");
					String sDsv2M    = dsRtnMatnr.getColumnAsString(iMatnr,  "DSV2");
					String sDhsbtM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DHSBT");
					
					int iQntyM      = dsRtnMatnr.getColumnAsDouble(iMatnr,  "QNTY").intValue();

					// ITEM��
					if (sItem_d != null && sItem_d.equals(sItemM))		{

						// ����(����)��ȣ �� : �����ϸ� Ȯ��
						if (sMatnr_d != null && sMatnr_d.equals(sMatnrM))	{

							// SPEC�� (�ش� ���� �����ϴ� ���)
							if (sSpec12_d == null || sSpec12_d.equals(""))	{
								bSpec12 = true;
							}else if (sSpec12_d != null && sSpec12M != null && !"".equals(sSpec12M) && sSpec12_d.indexOf(sSpec12M) >= 0)	{
								bSpec12 = true;
							}
							
							
							// �ӵ� ��
							if (iSpec3fr_d <= iSpec3M && iSpec3M <= iSpec3to_d )	{
								bSpec3 = true;
							}

							// �ν� ��
							if (iSpec5fr_d <= iSpec5M && iSpec5M <= iSpec5to_d )	{
								bSpec5 = true;
							}

							// CAR SAFETY �� (�ش� ���� �����ϴ� ���)
							if (sEcsf_d == null || sEcsf_d.equals(""))	{
								bEcsf = true;
							}else if (sEcsfM != null && sEcsfM.indexOf(sEcsf_d) >= 0)	{
								bEcsf = true;
							}

							// TM �� (�ش� ���� �����ϴ� ���)
						/*	if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
								bEtm = true;
							}
						*/	
							if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtm_d != null && sEtmM != null && !"".equals(sEtmM) && sEtm_d.indexOf(sEtmM) >= 0)	{
								bEtm = true;
							}
						    else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
							bEtm = true;
						    }
							

							// BLOK NO �� (�ش� ���� �����ϴ� ���)
							if (sMatkl_d == null || sMatkl_d.equals(""))	{
								bMatkl = true;
							}else if (sMatklM != null && sMatklM.indexOf(sMatkl_d) >= 0)	{
								bMatkl = true;
							}
							//2016.01 �߰�
							// �뵵 �� 
					       if (sAuse_d == null || sAuse_d.equals(""))	{
								bAuse = true;
							}else if (sAuseM != null && sAuseM.indexOf(sAuse_d) >= 0)	{
								bAuse  = true;
							}
							// �뷮 �� 
						   if (sAcapa_d == null || sAcapa_d.equals(""))	{
								bAcapa = true;
							}else if (sAcapaM != null && sAcapaM.indexOf(sAcapa_d) >= 0)	{
								bAcapa  = true;
							}
							// ���� �� 
						    if (sAopen_d == null || sAopen_d.equals(""))	{
								bAopen = true;
							}else if (sAopenM != null && sAopenM.indexOf(sAopen_d) >= 0)	{
								bAopen  = true;
							}
							// ���� �� 
							if (iAfqfr_d <= iAfqM && iAfqM <= iAfqto_d )	{
								bAfq = true;
							}
						//System.out.println(" iAfqfr_d : " +iAfqfr_d
						//		           +  " iAfqM : " + iAfqM  +   " iAfqto_d : " + iAfqto_d );
				              						
					    	// ���� ���� ��
						   if (iAstqfr_d <= iAstqM && iAstqM <= iAstqto_d )	{
								bAstq = true;
							}
							// ������ �� 
			   		       if (sAdrv_d == null || sAdrv_d.equals(""))	{
								bAdrv = true;
							}else if (sAdrvM != null && sAdrvM.indexOf(sAdrv_d) >= 0)	{
								bAdrv  = true;
							}
							// �ù漭 �� 
			   			    if (sAspc_d == null || sAspc_d.equals(""))	{
								bAspc = true;
							}else if (sAspcM != null && sAspcM.indexOf(sAspc_d) >= 0)	{
								bAspc  = true;
							}
							// ����/�ؿ� ���� �� 
			   			    if (sAcd1_d == null || sAcd1_d.equals(""))	{
								bAcd1 = true;
							}else if (sAcd1M != null && sAcd1M.indexOf(sAcd1_d) >= 0)	{
								bAcd1  = true;
							}
							//  �����ڵ�  �� 
			   			    if (sAcd2_d == null || sAcd2_d.equals(""))	{
								bAcd2 = true;
							}else if (sAcd2M != null && sAcd2M.indexOf(sAcd2_d) >= 0)	{
								bAcd2  = true;
							}
							// FLOOR �β�  ��
							if (iBfthfr_d <= iBfthM && iBfthM <= iBfthto_d )	{
								bBfth = true;
							}
							//  CAR WALL  �� 
			   			   if (sBwm_d == null || sBwm_d.equals(""))	{
								bBwm = true;
							}else if (sBwmM != null && sBwmM.indexOf(sBwm_d) >= 0)	{
								bBwm  = true;
							}
							//  main opb  �� 
			   			    if (sBmopb_d == null || sBmopb_d.equals(""))	{
								bBmopb = true;
							}else if (sBmopbM != null && sBmopbM.indexOf(sBmopb_d) >= 0)	{
								bBmopb  = true;
							}
							//    CAR DOOR �� 
			   			   if (sBcdm_d == null || sBcdm_d.equals(""))	{
								bBcdm = true;
							}else if (sBcdmM != null && sBcdmM.indexOf(sBcdm_d) >= 0)	{
								bBcdm  = true;
							}
							// handrail  �� 
			   			    if (sBhr_d == null || sBhr_d.equals(""))	{
								bBhr = true;
							}else if (sBhrM != null && sBhrM.indexOf(sBhr_d) >= 0)	{
								bBhr  = true;
							}
							// jamb  �� 
			   			    if (sCjm1_d == null || sCjm1_d.equals(""))	{
								bCjm1 = true;
							}else if (sCjm1M != null && sCjm1M.indexOf(sCjm1_d) >= 0)	{
								bCjm1  = true;
							}
							// jmb ����  ��
							if (iCjm1qfr_d <= iCjm1qM && iCjm1qM <= iCjm1qto_d )	{
								bCjm1q = true;
							}
							// jamb ���� �� 
			   			    if (sCjm1m_d == null || sCjm1m_d.equals(""))	{
								bCjm1m = true;
							}else if (sCjm1mM != null && sCjm1mM.indexOf(sCjm1m_d) >= 0)	{
								bCjm1m  = true;
							}
							// jamb ��ȭ���� �� 
			   			    if (sCjm1fr_d == null || sCjm1fr_d.equals(""))	{
								bCjm1fr = true;
							}else if (sCjm1frM != null && sCjm1frM.indexOf(sCjm1fr_d) >= 0)	{
								bCjm1fr  = true;
							}
							// HATCH DOOR  �� 
			   			    if (sChd1_d == null || sChd1_d.equals(""))	{
								bChd1 = true;
							}else if (sChd1M != null && sChd1M.indexOf(sChd1_d) >= 0)	{
								bChd1  = true;
							}
							// HATCH DOOR ����  ��
							if (iChd1qfr_d <= iChd1qM && iChd1qM <= iChd1qto_d )	{
								bChd1q = true;
							}
							// HATCH SILL  �� 
			   			   if (sChs1_d == null || sChs1_d.equals(""))	{
								bChs1 = true;
							}else if (sChs1M != null && sChs1M.indexOf(sChs1_d) >= 0)	{
								bChs1  = true;
							}
							// HATCH SILL ����  ��
							if (iChs1qfr_d <= iChs1qM && iChs1qM <= iChs1qto_d )	{
								bChs1q = true;
							}
							// �����ȭ��ġ  �� 
			   			    if (sDeph_d == null || sDeph_d.equals(""))	{
								bDeph = true;
							}else if (sDephM != null && sDephM.indexOf(sDeph_d) >= 0)	{
								bDeph  = true;
							}
							// �����ȭ��ġ ���� ���  ��
							if (iDephtqfr_d <= iDephtqM && iDephtqM <= iDephtqto_d )	{
								bDephtq = true;
							}
							// �°��� overhead  ��
							if (iEhofr_d <= iEhoM && iEhoM <= iEhoto_d )	{
								bEho = true;
							}
							// �°��� travel heigth   ��
							if (iEhtrhfr_d <= iEhtrhM && iEhtrhM <= iEhtrhto_d )	{
								bEhtrh = true;
							}
							//System.out.println(" iEhtrhfr_d : " +iEhtrhfr_d
								//	           +  " iEhtrhM : " + iEhtrhM  +   " iEhtrhto_d : " + iEhtrhto_d );
							// �°��� pit   ��
							if (iEhpfr_d <= iEhpM && iEhpM <= iEhpto_d )	{
								bEhp = true;
							}
							// �°���  ����   ��
							if (iEhhfr_d <= iEhhM && iEhhM <= iEhhto_d )	{
								bEhh = true;
							}
							// �°��� ����   ��
								if (iEhvfr_d <= iEhvM && iEhvM <= iEhvto_d )	{
								bEhv = true;
							}
							// �°��� ����   ��
							if (sEhm_d == null || sEhm_d.equals(""))	{
								bEhm = true;
							}else if (sEhmM != null && sEhmM.indexOf(sEhm_d) >= 0)	{
								bEhm  = true;
							}
							// car ca    ��
							if (iEccafr_d <= iEccaM && iEccaM <= iEccato_d )	{
								bEcca = true;
							}
							// car cb    ��
							if (iEccbfr_d <= iEccbM && iEccbM <= iEccbto_d )	{
								bEccb = true;
							}
							// car ch    ��
							if (iEcchfr_d <= iEcchM && iEcchM <= iEcchto_d )	{
								bEcch = true;
							}
							// door jj    ��
							if (iEcjjfr_d <= iEcjjM && iEcjjM <= iEcjjto_d )	{
								bEcjj = true;
							}
							// door hh   ��
							if (iEchhfr_d <= iEchhM && iEchhM <= iEchhto_d )	{
								bEchh = true;
							}
							// car aa  ��
							if (iEcaafr_d <= iEcaaM && iEcaaM <= iEcaato_d )	{
								bEcaa = true;
							}
							// car bb  ��
							if (iEcbbfr_d <= iEcbbM && iEcbbM <= iEcbbto_d )	{
								bEcbb = true;
							}
							// car ee  ��
							if (iEceefr_d <= iEceeM && iEceeM <= iEceeto_d )	{
								bEcee = true;
							}
							// motor �뷮  �� 
			   			    if (sEtmm_d == null || sEtmm_d.equals(""))	{
								bEtmm = true;
							}else if (sEtmmM != null && sEtmmM.indexOf(sEtmm_d) >= 0)	{
								bEtmm  = true;
							}					
			   			    // rope ����  �� 
			   			    if (sErpd_d == null || sErpd_d.equals(""))	{
								bErpd = true;
							}else if (sErpdM != null && sErpdM.indexOf(sErpd_d) >= 0)	{
								bErpd  = true;
							}
			   			    // rope ����  �� 
			   			    if (sErpw_d == null || sErpw_d.equals(""))	{
								bErpw = true;
							}else if (sErpwM != null && sErpwM.indexOf(sErpw_d) >= 0)	{
								bErpw  = true;
							}
			   			    // rope roping �� 
			   			    if (sErpr_d == null || sErpr_d.equals(""))	{
								bErpr = true;
							}else if (sErprM != null && sErprM.indexOf(sErpr_d) >= 0)	{
								bErpr  = true;
							}
			   			    // car rail(k) �� 
			   			    if (sEcrl_d == null || sEcrl_d.equals(""))	{
								bEcrl = true;
							}else if (sEcrlM != null && sEcrlM.indexOf(sEcrl_d) >= 0)	{
								bEcrl  = true;
							}
			   			    // car governor �� 
			   			    if (sEcgv_d == null || sEcgv_d.equals(""))	{
								bEcgv = true;
							}else if (sEcgvM != null && sEcgvM.indexOf(sEcgv_d) >= 0)	{
								bEcgv  = true;
							}
			   			    // cwt rail (k) �� 
			   			    if (sEcwrl_d == null || sEcwrl_d.equals(""))	{
								bEcwrl = true;
							}else if (sEcwrlM != null && sEcwrlM.indexOf(sEcwrl_d) >= 0)	{
								bEcwrl  = true;
							}	   			    
			   			    // cwt weight  �� 
			   			    if (sEcww_d == null || sEcww_d.equals(""))	{
								bEcww = true;
							}else if (sEcwwM != null && sEcwwM.indexOf(sEcww_d) >= 0)	{
								bEcww  = true;
							}	   	
			   			    // cwt safety  �� 
			   			    if (sEcwsf_d == null || sEcwsf_d.equals(""))	{
								bEcwsf = true;
							}else if (sEcwsfM != null && sEcwsfM.indexOf(sEcwsf_d) >= 0)	{
								bEcwsf  = true;
							}	  
			   			    // ���ù�  �� 
			   			    if (sDsv1_d == null || sDsv1_d.equals(""))	{
								bDsv1 = true;
							}else if (sDsv1M != null && sDsv1M.indexOf(sDsv1_d) >= 0)	{
								bDsv1  = true;
							}	  
			   			    // CCTVī�޶�  �� 
			   			    if (sDcctv_d == null || sDcctv_d.equals(""))	{
								bDcctv = true;
							}else if (sDcctvM != null && sDcctvM.indexOf(sDcctv_d) >= 0)	{
								bDcctv  = true;
							}	  
			   			    // CAR ������   �� 
			   			    if (sDcair_d == null || sDcair_d.equals(""))	{
								bDcair = true;
							}else if (sDcairM != null && sDcairM.indexOf(sDcair_d) >= 0)	{
								bDcair  = true;
							}
			   			   //�����㰡��
			   			    if (sAstda_d == null || sAstda_d.equals(""))	{
								bAstda = true;
							}else if (sAstdaM != null && sAstdaM.indexOf(sAstda_d) >= 0)	{
								bAstda  = true;
							}
				   			 //OPB ��� ���⿩�� 
			   			    if (sDuopb_d == null || sDuopb_d.equals(""))	{
								bDuopb = true;
							}else if (sDuopbM != null && sDuopbM.indexOf(sDuopb_d) >= 0)	{
								bDuopb  = true;
							}
				   			//�̳�� ���뿩��
			   			    if (sAinov_d == null || sAinov_d.equals(""))	{
								bAinov = true;
							}else if (sAinovM != null && sAinovM.indexOf(sAinov_d) >= 0)	{
								bAinov  = true;
							}
				   		 //2013�� 9�� 15�� ���� �����㰡
			   			    if (sAstd_d == null || sAstd_d.equals(""))	{
								bAstd = true;
							}else if (sAstdM != null && sAstdM.indexOf(sAstd_d) >= 0)	{
								bAstd  = true;
							}
				   		 //ELD ����
			   			    if (sDeld_d == null || sDeld_d.equals(""))	{
			   			    	bDeld = true;
							}else if (sDeldM != null && sDeldM.indexOf(sDeld_d) >= 0)	{
								bDeld  = true;
							}
				   		//���ù�2
			   			    if (sDsv2_d == null || sDsv2_d.equals(""))	{
								bDsv2 = true;
							}else if (sDsv2M != null && sDsv2M.indexOf(sDsv2_d) >= 0)	{
								bDsv2  = true;
							}
				   	   //�������ư
			   			    if (sDhsbt_d == null || sDhsbt_d.equals(""))	{
								bDhsbt = true;
							}else if (sDhsbtM != null && sDhsbtM.indexOf(sDhsbt_d) >= 0)	{
								bDhsbt  = true;
							}
                            /*================================����Ϸ� 2016.01 ======================================*/
			   			    
							// ��������� ���� (����Ǽ� ����)
			   			// ��������� ���� (����Ǽ� ����)
							if (bSpec12 && bSpec3 && bSpec5 && bEcsf && bEtm && bMatkl && bAuse && bAcapa && bAopen && bAfq && bAstq && bAdrv && bAspc && bAcd1
									&& bAcd2 && bBfth && bBwm  && bBmopb && bBcdm && bBhr && bCjm1 && bCjm1q && bCjm1m && bCjm1fr && bChd1 && bChd1q && bChs1 && bChs1q
									&& bDeph && bDephtq && bEho && bEhtrh && bEhtrh && bEhp && bEhh && bEhv && bEhm && bEcca && bEccb && bEcch && bEcaa && bEcbb && bEcee  
									&& bEtmm && bErpd && bErpw && bErpr && bEcrl && bEcgv && bEcwrl && bEcww && bEcwsf && bDsv1 && bDcctv && bDcair && bEcjj && bEchh && bAstda && bDuopb
									&& bAinov && bAstd && bDeld && bDsv2 && bDhsbt)	{								
								   iQntySum += iQntyM;

								// ���� ó���뵵
								bSumCheck = true;
							}

						}else	{	// ����(����) ��ȣ�� �����ϸ�
							// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
							// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

							//if (bSumCheck)	{
								// �ӽ�Dataset�� �߰�
								int iRow = dsTempSum.appendRow();
								
								dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
								dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
								dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
								dsTempSum.setColumn(iRow, "STYM",  sStym_d);
								dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
								dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
								dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
	
								bSumCheck = false;
							//}

							break;
						}
					}else	{	// ITEM�� �����ϸ� ���������� �������� ���� SKIP
						// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
						// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

						//if (bSumCheck)		{
							// �ӽ�Dataset�� �߰�
							int iRow = dsTempSum.appendRow();
							
							dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
							dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
							dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
							dsTempSum.setColumn(iRow, "STYM",  sStym_d);
							dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
							dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
							dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
							
							bSumCheck = false;
						//}

						break;
					}

					//�ڷ��� ��ġ
					iPos++;
				}
				// �������� ����
				if (iPos == dsRtnMatnr.getRowCount())	{
					// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
					// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

					//if (bSumCheck)		{
						// �ӽ�Dataset�� �߰�
						int iRow = dsTempSum.appendRow();
						
						dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
						dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
						dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
						dsTempSum.setColumn(iRow, "STYM",  sStym_d);
						dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
						dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
						dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
						
						bSumCheck = false;
					//}
					// ���� �Ǽ� �������� ó�� ���ʿ�
					break;
				}

				//
			}

			
			// log���
			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{
				System.out.println(" ITEM : " + dsTempSum.getColumnAsString(iMatnr, "ITEM") + "     " +
						           " MATNR : " + dsTempSum.getColumnAsString(iMatnr, "MATNR") + "     " +
						           " GUBUN : " + dsTempSum.getColumnAsString(iMatnr, "GUBUN") + "     " +
						           " STYM : " + dsTempSum.getColumnAsString(iMatnr, "STYM") + "     " +
						           " QNTY : " + dsTempSum.getColumnAsString(iMatnr, "QNTY") + "     " +
						           " MATNR_SVAMT : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT") + "     " +
						           " MATNR_WAERK : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK") + "....."
						);
			}

			//TransactionManager.rollback();

			
			
//			/* ���� ��ȸ ��� Ȯ�� �� ó��
			// ����� Item ����
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{

				String sStym = dsTempSum.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsTempSum.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
				// ���� ó���� �ǵ��� ��û�� ���Ͽ� comment

				// 0�� ��� ���� ���� (�� ���� �������� �������� �ʾƾ� ��)
				//if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
				//	continue;
				//}


				// ���� ó��
				if (hashMap.get(dsTempSum.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsTempSum.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// ���� ��������� �����ϴ� ��� ""
				}

				// �շϵ� ����/���� ��ȣ�� ���� ó�� 
				zqmt075u.addParamObject("ITEM",  dsTempSum.getColumnAsString(iMatnr, "ITEM"));	// �׸�
				zqmt075u.addParamObject("GUBUN", dsTempSum.getColumnAsString(iMatnr, "GUBUN"));	// ����/�����ȣ ����
				zqmt075u.addParamObject("MATNR", dsTempSum.getColumnAsString(iMatnr, "MATNR"));	// ����/�����ȣ
				zqmt075u.addParamObject("SVAMT", dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// ������
				zqmt075u.addParamObject("WAERK", dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK"));	// ȭ�����
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// ����
	
				// ����/���� ���� ���� ���(ZQMT075)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// �׸� ���� ó��
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""�� �ƴϸ� ������� ��� �ʿ�

				// ����� �� �׸� ���������� �ش� �׸� ���� (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// �׸� ���� ���� ��� (ZQMT076) :  ����/�����ȣ���� ����� ���� ����
				zqmt076i.addParamObject("ITEM", sKey);	// �׸�

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// ���� �������ڰ� ���� ��쿡�� �ݿ�
				if (sValue != null && !"".equals(sValue))	{
					// ���� ��������� ���� Update
					zqmt071u.addParamObject("ITEM", sKey);	// �׸�
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// ���ʽ�������

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// ���� ó�� �̷� ���(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
//			*/
		}
	}
	// ����/���� ���� ���� ó�� ���
	// 2013.08.07
	public void cancelProcess(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ���� ������ �ʱ���·� ���� (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U3");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071u.addParamObject("SGBN",      "S");	// �ý��� ����Ǹ� ����

		// ����/�����ȣ �� �������� ���� (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����� �� �׸� �������� ���� (ZQMT076) �ý��ۿ� ���Ͽ� �� �ڵ� ����� ����
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt076d.addParamObject("SGBN",    "S");	// �ý��� ����

		// ����ó�� �̷� ���� (ZQMT074)
		DatasetSqlRequest zqmt074d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D4");

		zqmt074d.addParamObject("ds_list", ds_cond);
		zqmt074d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ���������� �׸��� ����/�����ȣ ��� ��ȸ
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// ������� �ʱ�ȭ
			zqmt071u.setRowIndex(i);
			executor.execute(zqmt071u);

			// �� ó���� �������� ���� ���� ó��
			// ����/�����ȣ �� �������� ���� (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// ����� �� �׸� �������� ���� (ZQMT076)
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);
			
			// ������� �̷� ����
			zqmt074d.setRowIndex(i);
			executor.execute(zqmt074d);
		}
	}
	
	


    /******************************�������******************************/
	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ��������׸��� (ZQMT071U) ������ �׸��ȣ ��ȸ
		DatasetSqlRequest zqmt071s
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_S3");	// ITEM seq����

		zqmt071s.addParamObject("ds_cond", ds_list);
		zqmt071s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ��������׸��� (ZQMT071U) ���� ���
		DatasetSqlRequest zqmt071i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_I1");

		zqmt071i.addParamObject("ds_list",   ds_list);
		zqmt071i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071i.addParamObject("S_CONFDT",  ctx.getInputVariable("pConfDt"));		// Ȯ������

		// ��������׸��� (ZQMT071U) ���� ����
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_U1");

		zqmt071u.addParamObject("ds_list",   ds_list);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ��������׸��� (ZQMT071U) ���� ����
		DatasetSqlRequest zqmt071d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_D1");

		zqmt071d.addParamObject("ds_list", ds_list);
		zqmt071d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ��������׸��ȹ�ݾ׵�� (ZQMT072U)
		DatasetSqlRequest zqmt072i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_I2");

		zqmt072i.addParamObject("ds_list",   ds_list);
		zqmt072i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt072i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ��������׸��ȹ�ݾ׻��� (ZQMT072U)
		DatasetSqlRequest zqmt072d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_D2");

		zqmt072d.addParamObject("ds_list", ds_list);
		zqmt072d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)����/���� ��ȣ ��� (ZQMT073U)
		DatasetSqlRequest zqmt073i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_I3");

		zqmt073i.addParamObject("ds_list",   ds_list);
		zqmt073i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt073i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)����/���� ��ȣ ���� (ZQMT073U)
		DatasetSqlRequest zqmt073d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_D3");

		zqmt073d.addParamObject("ds_list", ds_list);
		zqmt073d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		for(int i = 0; i < ds_list.getRowCount(); i++) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{

				// �̏��׸��� �Է� ó�� ZQMT071U
				if(ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					zqmt071i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// �׸��ȣ
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// �׸��ȣ
				}else	{
					zqmt071s.setRowIndex(i);
					Dataset dsRtn071 = (Dataset)executor.query(zqmt071s).getResultObject();
					
					if(dsRtn071.getRowCount() > 0 ) {
						zqmt071i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// �׸��ȣ
						zqmt072i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// �׸��ȣ
					} else {
						throw new BizException(String.valueOf(i + 1) + " ��° �׸� ������ ���簡 �߻��Ͽ����ϴ�. ó���� ���� �մϴ�.");
					}
				}

				// ��������׸��� (ZQMT071U)
				zqmt071i.setRowIndex(i);
				executor.execute(zqmt071i);

				// ������°�ȹ�ݾ� ���
				try {
					zqmt072Insert2(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}

				// (UP)����/���� ���� ó��
				if (ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					// ����/���� ��ȣ��� (ZQMT073U)
					zqmt073i.setRowIndex(i);
					executor.execute(zqmt073i);
				}
			}else if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// ��������׸���� (ZQMT071U)
				zqmt071u.setRowIndex(i);
				executor.execute(zqmt071u);
				
				// ������°�ȹ�ݾ� ����
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// ������°�ȹ�ݾ� ���
				try {
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));	// �׸��ȣ
					zqmt072Insert2(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}
			}else if("D".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// ������°�ȹ�ݾ� ����
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// ��������׸���� (ZQMT071U)
				zqmt071d.setRowIndex(i);
				executor.execute(zqmt071d);
				
				// (UP)����/���� ���� ����
				zqmt073d.setRowIndex(i);
				executor.execute(zqmt073d);
			}
		}
	}

	// ������°�ȹ�ݾ� ��� (ZQMT072)
	public void zqmt072Insert2(DatasetSqlExecutor executor, DatasetSqlRequest zqmt072i, Dataset ds_list, int index) throws Exception	{
		String sMonth  = "";
		String sPlanMM = "";
		for (int j = 1; j <= 12; j++)	{
			sPlanMM = String.format("%02d", j);
			sMonth = "M" + sPlanMM;
			if (ds_list.getColumnAsString(index, sMonth) != null)	{
				zqmt072i.addParamObject("S_PLANYM",  ds_list.getColumnAsString(index, "GJAHR") + sPlanMM); // ��ȹ���
				zqmt072i.addParamObject("S_PLANAMT", ds_list.getColumnAsString(index, sMonth)); // ��ȹ�ݾ�
				
				zqmt072i.setRowIndex(index);
				executor.execute(zqmt072i);
			}
		}		
	}

	// (UP)����/���� ���� ���� ó��
	public void matnrProc2(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// (UP)����/�����ȣ �� �������� ���� (ZQMT075U)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)����� �� �׸� �������� ���� (ZQMT076U) �ý��ۿ� ���Ͽ� �� �ڵ� ����� ����
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)����(gubun=1) ����(gubun=2) ��ȣ�� ���� ����
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_S5");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)����/�����ȣ �������� (ZQMT075U)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)����� �� �׸� �������� ���� (ZQMT076U) ���� �Է°��� �ý��ۿ� ���Ͽ� ����Ȱ� ����
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)�׸� �������� (ZQMT076U)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : ����,    S : �ý���
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : �Ѿױ���, U : �ܰ�����

		// (UP)���� ������ ���� (ZQMT071U)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)����ó�� �̷� (ZQMT074U)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)���������� �׸��� ����/�����ȣ ��� ��ȸ
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{
			// �� ó���� �������� ���� ���� ó��
			// ����/�����ȣ �� �������� ���� (ZQMT075U)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// ����� �� �׸� �������� ���� (ZQMT076U)
			zqmt076d.addParamObject("SGBN", "S");	// �ý��� ����
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// ����/����  ���� ��� ��ȸ
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// ����� Item ����
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

				String sStym = dsRtnMatnr.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsRtnMatnr.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 0�� ��� ���� ���� (�� ���� �������� �������� �ʾƾ� ��)
				if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
					continue;
				}

				// ���� ó��
				if (hashMap.get(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// ���� ��������� �����ϴ� ��� ""
				}

				// �շϵ� ����/���� ��ȣ�� ���� ó�� 
				zqmt075u.addParamObject("ITEM",  dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"));	// �׸�
				zqmt075u.addParamObject("GUBUN", dsRtnMatnr.getColumnAsString(iMatnr, "GUBUN"));	// ����/�����ȣ ����
				zqmt075u.addParamObject("MATNR", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR"));	// ����/�����ȣ
				zqmt075u.addParamObject("SVAMT", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// ������
				zqmt075u.addParamObject("WAERK", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_WAERK"));	// ȭ�����
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// ����
	
				// ����/���� ���� ���� ���(ZQMT075U)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// �׸� ���� ó��
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""�� �ƴϸ� ������� ��� �ʿ�

				// ����� �� �׸� ���������� �ش� �׸� ���� (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// �׸� ���� ���� ��� (ZQMT076U) :  ����/�����ȣ���� ����� ���� ����
				zqmt076i.addParamObject("ITEM", sKey);	// �׸�

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// ���� �������ڰ� ���� ��쿡�� �ݿ�
				if (sValue != null && !"".equals(sValue))	{
					// ���� ��������� ���� Update
					zqmt071u.addParamObject("ITEM", sKey);	// �׸�
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// ���ʽ�������

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// ���� ó�� �̷� ���(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
		}
	}

	// (UP)����/���� ���� ���� ó�� (�������� �߰�)
	// 2013.07.17
	public void matnrProcCondAdd2(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ����/�����ȣ �� �������� ���� (ZQMT075U)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����� �� �׸� �������� ���� (ZQMT076U) �ý��ۿ� ���Ͽ� �� �ڵ� ����� ����
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����(gubun=1) ����(gubun=2) ��ȣ�� ���� ���� (������ ����)
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_S6");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����(gubun=1) ����(gubun=2) ������ ��ȸ
		DatasetSqlRequest matnrsDetail
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_S7");

		matnrsDetail.addParamObject("ds_cond", ds_cond);
		matnrsDetail.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����/�����ȣ �������� (ZQMT075U)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����� �� �׸� �������� ���� (ZQMT076U) �Է°��� �ý��ۿ� ���Ͽ� ����Ȱ� ����
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// �׸� �������� (ZQMT076U)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : ����,    S : �ý���
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : �Ѿױ���, U : �ܰ�����

		// ���� ������ ���� (ZQMT071U)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ����ó�� �̷� (ZQMT074U)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// ���������� �׸��� ����/�����ȣ ��� ��ȸ
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// �� ó���� �������� ���� ���� ó��
			// ����/�����ȣ �� �������� ���� (ZQMT075U)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// ����� �� �׸� �������� ���� (ZQMT076U)
			zqmt076d.addParamObject("SGBN", "S");	// �ý��� ����
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// ����/����  ���� ��� ��ȸ
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// ����/���� ����ȸ ��� ��ȸ 2013.07.17
			matnrsDetail.setRowIndex(i);
			Dataset dsRtnMatnrDetail = (Dataset)executor.query(matnrsDetail).getResultObject();

			// �����ǿ� ���� ���� Ȯ�� �� ���� ó��
			Dataset dsTempSum = new Dataset();

			// �÷� setting
			dsTempSum.addColumn("ITEM",        (short)1, 256);
			dsTempSum.addColumn("STYM",        (short)1, 256);
			dsTempSum.addColumn("GUBUN",       (short)1, 256);
			dsTempSum.addColumn("MATNR_SVAMT", (short)1, 256);
			dsTempSum.addColumn("MATNR_WAERK", (short)1, 256);
			dsTempSum.addColumn("MATNR",       (short)1, 256);
			dsTempSum.addColumn("QNTY",        (short)1, 256);

			// ����(����) ���� 
			int iPos = 0;

			// �������� Ȯ��
			for(int iDetail = 0; iDetail < dsRtnMatnrDetail.getRowCount(); iDetail++)		{

				boolean bSumCheck = false;

				String sItem_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ITEM");
				String sMatnr_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR");
				String sSpec12_d = dsRtnMatnrDetail.getColumnAsString(iDetail,  "SPEC12");
				int iSpec3fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3FR");
				int iSpec3to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3TO");
				int iSpec5fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5FR");
				int iSpec5to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5TO");
				String sEcsf_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ECSF");
				String sEtm_d    = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ETM");
				String sMatkl_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATKL");
				String sSvamt_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_SVAMT");
				String sWaerk_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_WAERK");
				String sGubun_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "GUBUN");
				String sStym_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "STYM");

				int iQntySum    = 0;	//����(����) �����Ǽ�

				// ����(����) ���� ��ö��
				for (int iMatnr = iPos; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

					boolean bSpec12 = false;
					boolean bSpec3  = false;
					boolean bSpec5  = false;
					boolean bEcsf   = false;
					boolean bEtm    = false;
					boolean bMatkl  = false;

					String sItemM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ITEM");
					String sMatnrM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATNR");
					String sSpec12M = dsRtnMatnr.getColumnAsString(iMatnr,  "SPEC12");
					int iSpec3M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC3");
					int iSpec5M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC5");
					String sEcsfM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ECSF");
					String sEtmM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ETM");
					String sMatklM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATKL");
					int iQntyM      = dsRtnMatnr.getColumnAsDouble(iMatnr,  "QNTY").intValue();

					// ITEM��
					if (sItem_d != null && sItem_d.equals(sItemM))		{

						// ����(����)��ȣ �� : �����ϸ� Ȯ��
						if (sMatnr_d != null && sMatnr_d.equals(sMatnrM))	{

							// SPEC�� (�ش� ���� �����ϴ� ���)
							if (sSpec12_d == null || sSpec12_d.equals(""))	{
								bSpec12 = true;
							}else if (sSpec12_d != null && sSpec12M != null && !"".equals(sSpec12M) && sSpec12_d.indexOf(sSpec12M) >= 0)	{
								bSpec12 = true;
							}
							
							// �ӵ� ��
							if (iSpec3fr_d <= iSpec3M && iSpec3M <= iSpec3to_d )	{
								bSpec3 = true;
							}

							// �ν� ��
							if (iSpec5fr_d <= iSpec5M && iSpec5M <= iSpec5to_d )	{
								bSpec5 = true;
							}

							// CAR SAFETY �� (�ش� ���� �����ϴ� ���)
							if (sEcsf_d == null || sEcsf_d.equals(""))	{
								bEcsf = true;
							}else if (sEcsfM != null && sEcsfM.indexOf(sEcsf_d) >= 0)	{
								bEcsf = true;
							}

							// TM �� (�ش� ���� �����ϴ� ���)
						/*	if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
								bEtm = true;
							}
					    */
							if (sEtm_d == null || sEtm_d.equals(""))     {
								bEtm = true;
							 }else if (sEtm_d != null && sEtmM != null && !"".equals(sEtmM) && sEtm_d.indexOf(sEtmM) >= 0)	               {
							  bEtm = true;
							 }
						     else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
							 bEtm = true;
						    }
										

							// BLOK NO �� (�ش� ���� �����ϴ� ���)
							if (sMatkl_d == null || sMatkl_d.equals(""))	{
								bMatkl = true;
							}else if (sMatklM != null && sMatklM.indexOf(sMatkl_d) >= 0)	{
								bMatkl = true;
							}
							
							// ��������� ���� (����Ǽ� ����)
							if (bSpec12 && bSpec3 && bSpec5 && bEcsf && bEtm && bMatkl)	{
								iQntySum += iQntyM;

								// ���� ó���뵵
								bSumCheck = true;
							}

						}else	{	// ����(����) ��ȣ�� �����ϸ�
							// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
							// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

							//if (bSumCheck)	{
								// �ӽ�Dataset�� �߰�
								int iRow = dsTempSum.appendRow();
								
								dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
								dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
								dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
								dsTempSum.setColumn(iRow, "STYM",  sStym_d);
								dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
								dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
								dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
	
								bSumCheck = false;
							//}

							break;
						}
					}else	{	// ITEM�� �����ϸ� ���������� �������� ���� SKIP
						// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
						// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

						//if (bSumCheck)		{
							// �ӽ�Dataset�� �߰�
							int iRow = dsTempSum.appendRow();
							
							dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
							dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
							dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
							dsTempSum.setColumn(iRow, "STYM",  sStym_d);
							dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
							dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
							dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
							
							bSumCheck = false;
						//}

						break;
					}

					//�ڷ��� ��ġ
					iPos++;
				}
				// �������� ����
				if (iPos == dsRtnMatnr.getRowCount())	{
					// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
					// ���� ó���� �ǵ��� ��û�� ���Ͽ� bSumCheck�� false���� ó��

					//if (bSumCheck)		{
						// �ӽ�Dataset�� �߰�
						int iRow = dsTempSum.appendRow();
						
						dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
						dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
						dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
						dsTempSum.setColumn(iRow, "STYM",  sStym_d);
						dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
						dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
						dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
						
						bSumCheck = false;
					//}
					// ���� �Ǽ� �������� ó�� ���ʿ�
					break;
				}

				//
			}

			/*
			// log���
			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{
				System.out.println(" ITEM : " + dsTempSum.getColumnAsString(iMatnr, "ITEM") + "     " +
						           " MATNR : " + dsTempSum.getColumnAsString(iMatnr, "MATNR") + "     " +
						           " GUBUN : " + dsTempSum.getColumnAsString(iMatnr, "GUBUN") + "     " +
						           " STYM : " + dsTempSum.getColumnAsString(iMatnr, "STYM") + "     " +
						           " QNTY : " + dsTempSum.getColumnAsString(iMatnr, "QNTY") + "     " +
						           " MATNR_SVAMT : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT") + "     " +
						           " MATNR_WAERK : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK") + "....."
						);
			}

			//TransactionManager.rollback();

			*/
			
//			/* ���� ��ȸ ��� Ȯ�� �� ó��
			// ����� Item ����
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{

				String sStym = dsTempSum.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsTempSum.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 2013.08.09 �ý��� ������ ��� ��������� 0�� ��쵵 
				// ���� ó���� �ǵ��� ��û�� ���Ͽ� comment

				// 0�� ��� ���� ���� (�� ���� �������� �������� �ʾƾ� ��)
				//if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
				//	continue;
				//}


				// ���� ó��
				if (hashMap.get(dsTempSum.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsTempSum.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// ���� ��������� �����ϴ� ��� ""
				}

				// �շϵ� ����/���� ��ȣ�� ���� ó�� 
				zqmt075u.addParamObject("ITEM",  dsTempSum.getColumnAsString(iMatnr, "ITEM"));	// �׸�
				zqmt075u.addParamObject("GUBUN", dsTempSum.getColumnAsString(iMatnr, "GUBUN"));	// ����/�����ȣ ����
				zqmt075u.addParamObject("MATNR", dsTempSum.getColumnAsString(iMatnr, "MATNR"));	// ����/�����ȣ
				zqmt075u.addParamObject("SVAMT", dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// ������
				zqmt075u.addParamObject("WAERK", dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK"));	// ȭ�����
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// ����
	
				// ����/���� ���� ���� ���(ZQMT075U)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// �׸� ���� ó��
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""�� �ƴϸ� ������� ��� �ʿ�

				// ����� �� �׸� ���������� �ش� �׸� ���� (ZQMT076U)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// �׸� ���� ���� ��� (ZQMT076U) :  ����/�����ȣ���� ����� ���� ����
				zqmt076i.addParamObject("ITEM", sKey);	// �׸�

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// ���� �������ڰ� ���� ��쿡�� �ݿ�
				if (sValue != null && !"".equals(sValue))	{
					// ���� ��������� ���� Update
					zqmt071u.addParamObject("ITEM", sKey);	// �׸�
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// ���ʽ�������

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// ���� ó�� �̷� ���(ZQMT074U)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
//			*/
		}
	}

	// ����/���� ���� ���� ó�� ���
	// 2013.08.07
	public void cancelProcess2(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ���� ������ �ʱ���·� ���� (ZQMT071U)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U3");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071u.addParamObject("SGBN",      "S");	// �ý��� ����Ǹ� ����

		// ����/�����ȣ �� �������� ���� (ZQMT075U)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ����� �� �׸� �������� ���� (ZQMT076U) �ý��ۿ� ���Ͽ� �� �ڵ� ����� ����
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt076d.addParamObject("SGBN",    "S");	// �ý��� ����

		// ����ó�� �̷� ���� (ZQMT074U)
		DatasetSqlRequest zqmt074d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D4");

		zqmt074d.addParamObject("ds_list", ds_cond);
		zqmt074d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// ���������� �׸��� ����/�����ȣ ��� ��ȸ
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// ������� �ʱ�ȭ
			zqmt071u.setRowIndex(i);
			executor.execute(zqmt071u);

			// �� ó���� �������� ���� ���� ó��
			// ����/�����ȣ �� �������� ���� (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// ����� �� �׸� �������� ���� (ZQMT076)
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);
			
			// ������� �̷� ����
			zqmt074d.setRowIndex(i);
			executor.execute(zqmt074d);
		}
	}

	// �������� ���� ���� ���� 
	// 2015.12.08

	public void workareaProcessAdd(BusinessContext ctx, Dataset ds_list) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));		
		

		// ���������׸��� (ZQMT072) ������ �׸��ȣ ��ȸ
	DatasetSqlRequest zqmt072s
		= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_S4");	// ITEM ��ȸ

		zqmt072s.addParamObject("ds_cond", ds_list);
		zqmt072s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
	    Dataset dsRtn072 = (Dataset)executor.query(zqmt072s).getResultObject();  // executeQuery()

		//���� ó������ ��ȸ
		DatasetSqlRequest zqmt071s
		= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702003B_S2");
	
		zqmt071s.addParamObject("ds_cond",   ds_list);
		zqmt071s.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		Dataset dsRtn071 = (Dataset)executor.query(zqmt071s).getResultObject();  // executeQuery()
		
		// �������� ��ȸ (ZQMT073S)
		DatasetSqlRequest zqmt073s
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702003B_I1");
		
		zqmt073s.addParamObject("ds_list",   ds_list);
		zqmt073s.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt073s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
	
		// ����/�����ȣ �������� (ZQMT077i)
		for(int i = 0; i < dsRtn072.getRowCount(); i++) { 
			System.out.println(" DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
			           " GJAHR : " + dsRtn072.getColumnAsString(i, "GJAHR") + "     " +
			           " DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
			           " ITEM : " + dsRtn072.getColumnAsString(i, "ITEM"));
			//System.out.println(i);
	   	   	 zqmt073s.addParamObject("DEPT",      dsRtn072.getColumnAsString(i ,  "DEPT"));
	      	 zqmt073s.addParamObject("GJAHR",     dsRtn072.getColumnAsString(i ,  "GJAHR"));
	      	 zqmt073s.addParamObject("DEPT",      dsRtn072.getColumnAsString(i ,  "DEPT"));	
	      	 zqmt073s.addParamObject("ITEM",      dsRtn072.getColumnAsString(i ,  "ITEM"));	
	      	 zqmt073s.addParamObject("S_CRDAT",   dsRtn071.getColumnAsString(0 , "S_CRDAT"));	
	      	 zqmt073s.addParamObject("E_CRDAT",   dsRtn071.getColumnAsString(0 , "E_CRDAT") );	
	    
			zqmt073s.setRowIndex(i);
		    executor.execute(zqmt073s);
			//Dataset dsRtn073 = (Dataset)executor.query(zqmt073s).getResultObject();  // executeQuery()
			
	/*		// log���
		for(int j = 0; j < dsRtn073.getRowCount(); j++) { 
				System.out.println(" DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
						           " GJAHR : " + dsRtn072.getColumnAsString(i, "GJAHR") + "     " +
						           " DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
						           " ITEM : " + dsRtn072.getColumnAsString(i, "ITEM") + "     " +
						           " WBS : " + dsRtn073.getColumnAsString(j, "WBS") + "     " +
						           " SVAMT : " + dsRtn073.getColumnAsString(j, "SVAMT") + "     " +
						           " QNTY : " + dsRtn073.getColumnAsString(j, "QNTY") );
              
			//TransactionManager.rollback();
	
	       }
	*/       
			
	        
		  }
	}
}
	

