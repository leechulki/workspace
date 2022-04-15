package com.helco.xf.cs36.ws;

import java.math.BigDecimal;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class CS3607001A_ACT extends AbstractAction {
	public static final String ZWEB_CS_DGNO
		= "com.helco.xf.cs36.ws.ZWEB_CS_DGNO";
	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// �Է� �Ķ���� ó�� 
		ZCSS009[] list = new ZCSS009[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;

		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:LIFNR_S1");

		sqlRequest.addParamObject("ds_cond", dsCond);
		sqlRequest.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		String db_con = Utillity.getSapJndi(ctx.getInputVariable("G_MANDT"));
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		Dataset dsRtn = (Dataset)executor.query(sqlRequest).getResultObject();
		String lifnr = dsRtn.getColumnAsString(0, "LIFNR");	
		
		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "ARA")
				, DatasetUtility.getString(dsCond, "AUART")
				, DatasetUtility.getString(dsCond, "BPM")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "EDATE"))
				, lifnr
				, DatasetUtility.getString(dsCond, "PROJ")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "SDATE"))
				, DatasetUtility.getString(dsCond, "VKGRP")
				, listMsg
				, list
		};
		
		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_DGNO, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_DGNO, obj, false);		 // ����  SAP ����
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_DGNO, obj, false);  // EAI ����

		
		// ��� Dataset ���� 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCSS009.getDataset();
		ds.setId("ds_list");

		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);
		
		if (dsError.getRowCount() == 0) {
			for(int i=0; i<ds.getRowCount(); i++) {
				ds.setColumn(i, "FAKWR", StrictMath.round(ds.getColumnAsDouble(i, "FAKWR") * 100));
			}
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
}