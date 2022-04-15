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

public class CS3405001A_ACT extends AbstractAction {
	public static final String ZWEB_CS_GET_CBILL
		= "com.helco.xf.cs34.ws.ZWEB_CS_GET_CBILL";

	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZCST010[] list = new ZCST010[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;

		Object obj[] = new Object[]{
				CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "GIJUN"))
				, DatasetUtility.getString(dsCond, "AREA")
				, DatasetUtility.getString(dsCond, "AUART")
				, DatasetUtility.getString(dsCond, "LGORT")
				, DatasetUtility.getString(dsCond, "PM")
				, DatasetUtility.getString(dsCond, "PROJ")
				, DatasetUtility.getString(dsCond, "SPART")	
				, DatasetUtility.getString(dsCond, "VKBUR")	
				, DatasetUtility.getString(dsCond, "VKGRP")			
				, listMsg
				, list
		};

		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_CBILL, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_CBILL, obj, false);		 // 기존  SAP 연결
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_CBILL, obj, false);  // EAI 연결

		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCST010.getDataset();
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
				ds.setColumn(i, "BILL", StrictMath.round(ds.getColumnAsDouble(i, "BILL") * 100));
				ds.setColumn(i, "CBILL", StrictMath.round(ds.getColumnAsDouble(i, "CBILL") * 100));
				ds.setColumn(i, "CBILL1", StrictMath.round(ds.getColumnAsDouble(i, "CBILL1") * 100));
				ds.setColumn(i, "CBILL2", StrictMath.round(ds.getColumnAsDouble(i, "CBILL2") * 100));
				ds.setColumn(i, "CBILL3", StrictMath.round(ds.getColumnAsDouble(i, "CBILL3") * 100));
				ds.setColumn(i, "CBILL4", StrictMath.round(ds.getColumnAsDouble(i, "CBILL4") * 100));
				ds.setColumn(i, "YBILL1", StrictMath.round(ds.getColumnAsDouble(i, "YBILL1") * 100));
				ds.setColumn(i, "YBILL2", StrictMath.round(ds.getColumnAsDouble(i, "YBILL2") * 100));
				ds.setColumn(i, "YBILL3", StrictMath.round(ds.getColumnAsDouble(i, "YBILL3") * 100));
			}
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
}