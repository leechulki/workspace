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

public class CS3606001A_ACT extends AbstractAction {
	public static final String ZWEB_CS_BSU_SUJU
		= "com.helco.xf.cs36.ws.ZWEB_CS_BSU_SUJU";
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZCSS008[] list = new ZCSS008[]{};
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
				lifnr
				, DatasetUtility.getString(dsCond, "REGION")
				, DatasetUtility.getString(dsCond, "SMON")
				, DatasetUtility.getString(dsCond, "VKGRP")
				, DatasetUtility.getString(dsCond, "ZMAN")
				, listMsg
				, list
		};

		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_BSU_SUJU, obj);
				
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_BSU_SUJU, obj, false);		 // 기존  SAP 연결
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_BSU_SUJU, obj, false);  // EAI 연결
		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCSS008.getDataset();
		ds.setId("ds_list");

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);

		if (dsError.getRowCount() == 0) {
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
}
