package com.helco.xf.cs34.ws;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.comm.ZPSSEMSG;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs34.ws.ZCSS006;
import com.helco.xf.cs34.ws.ZCSS007;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class CS3404001A_ACT extends AbstractAction {
	public static final String ZWEB_CS_GET_MICH
		= "com.helco.xf.cs34.ws.ZWEB_CS_GET_MICH";

	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZCSS010[] list = new ZCSS010[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;

		Object obj[] = new Object[]{
				CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "ENDDA"))
				, DatasetUtility.getString(dsCond, "AREA")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "BILDAT"))
				, DatasetUtility.getString(dsCond, "LGORT")
				, DatasetUtility.getString(dsCond, "PM")
				, DatasetUtility.getString(dsCond, "PROJ")
				, DatasetUtility.getString(dsCond, "SPART")
				, DatasetUtility.getString(dsCond, "VKGRP")				
				, listMsg
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "STADA"))
				, list
		};

		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_MICH, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_MICH, obj, false);		 // 기존  SAP 연결
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_MICH, obj, false);  // EAI 연결

		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCSS010.getDataset();
		ds.setId("ds_list");
		CallSAP.makeDsCol(ds, "CHK", 0);

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);

		if (dsError.getRowCount() == 0) {
			for(int i=0; i<ds.getRowCount(); i++) {
				ds.setColumn(i, "CHK", 0);
				ds.setColumn(i, "NETWR", StrictMath.round(ds.getColumnAsDouble(i, "NETWR") * 100));
				ds.setColumn(i, "BILAMT1", StrictMath.round(ds.getColumnAsDouble(i, "BILAMT1") * 100));
				ds.setColumn(i, "JANGO", StrictMath.round(ds.getColumnAsDouble(i, "JANGO") * 100));
			}
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
}