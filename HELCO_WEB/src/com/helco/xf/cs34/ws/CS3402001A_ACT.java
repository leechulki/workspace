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

public class CS3402001A_ACT extends AbstractAction {
	public static final String ZWEB_CS_GET_SUJU
		= "com.helco.xf.cs34.ws.ZWEB_CS_GET_SUJU";
	public static final String ZWEB_CS_INSERT_BILLING
		= "com.helco.xf.cs34.ws.ZWEB_CS_INSERT_BILLING";

	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZCSS003[] list = new ZCSS003[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;

		Object obj[] = new Object[]{
				CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "ENDDA"))
				, DatasetUtility.getString(dsCond, "AREA")
				, DatasetUtility.getString(dsCond, "LGORT")
				, DatasetUtility.getString(dsCond, "PM")
				, DatasetUtility.getString(dsCond, "PROJ")
				, DatasetUtility.getString(dsCond, "SPART")
				, DatasetUtility.getString(dsCond, "VKGRP")				
				, listMsg
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "STADA"))
				, list
		};

		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_SUJU, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_SUJU, obj, false);		 // 기존  SAP 연결
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_SUJU, obj, false);  // EAI 연결

		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCSS003.getDataset();
		ds.setId("ds_list");
		CallSAP.makeDsCol(ds, "CHK", 0);

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);

		if (dsError.getRowCount() == 0) {
			for(int i=0; i<ds.getRowCount(); i++) {
				ds.setColumn(i, "CHK", 0);
				ds.setColumn(i, "OFKDAT", CommonUtil.replace(ds.getColumnAsString(i, "OFKDAT"),"-",""));
				ds.setColumn(i, "RKFKDAT", CommonUtil.replace(ds.getColumnAsString(i, "RKFKDAT"),"-",""));
				ds.setColumn(i, "NETWR", StrictMath.round(ds.getColumnAsDouble(i, "NETWR") * 100));
				ds.setColumn(i, "RKNETWR", StrictMath.round(ds.getColumnAsDouble(i, "RKNETWR") * 100));
				ds.setColumn(i, "SUJU", StrictMath.round(ds.getColumnAsDouble(i, "SUJU") * 100));
			}
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}

	/**
	 * 저장 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList1 = ctx.getInputDataset("ds_list1");
		Dataset dsError = null;
		SapFunction stub = null;

		// 입력 파라메터 처리 
		ZCSS006[] list1 = new ZCSS006[]{};
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

		// 입력 파라메터 처리 
		if(CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_INSERT_BILLING, new Object[]{dsList1});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_INSERT_BILLING, new Object[]{dsList1}, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_INSERT_BILLING, new Object[]{dsList1}, false);  // EAI 연결

			//CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			list1 = (ZCSS006[])CallSAP.moveDs2Obj(dsList1, ZCSS006.class, "FLAG");
			msgList = new ZQMSEMSG[]{};

			Object obj[] = new Object[]{msgList, list1};

			for(int i=0; i<list1.length; i++) {
				list1[i].FAKWR = list1[i].FAKWR.setScale(2, RoundingMode.DOWN);
			}

			// 저장 처리 
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_INSERT_BILLING, obj);
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_INSERT_BILLING, obj, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_INSERT_BILLING, obj, false);  // EAI 연결


			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}

//		if(ds.getRowCount() == 0) {
//			query(ctx);
//		} else {
			ctx.addOutputDataset(dsError);
//		}
	}
}