package com.helco.xf.bs01.ws;

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

public class BS0104002A_ACT extends AbstractAction {
	public static final String ZWEB_SD_B400_ONHAND
		= "com.helco.xf.bs01.ws.ZWEB_SD_B400_ONHAND";
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZSDS0026[] list = new ZSDS0026[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;

		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "CBOX1")
				, DatasetUtility.getString(dsCond, "CBOX2")
				, DatasetUtility.getString(dsCond, "CBOX3")
				, DatasetUtility.getString(dsCond, "CBOX4")
				, DatasetUtility.getString(dsCond, "CBOX5")
				, DatasetUtility.getString(dsCond, "CBOX6")
				, DatasetUtility.getString(dsCond, "KUNNR")
				, DatasetUtility.getString(dsCond, "VKBUR")
				, DatasetUtility.getString(dsCond, "VKGRP")
				, DatasetUtility.getString(dsCond, "ZZPJT_ID")
				, listMsg
				, list
		};

		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_B400_ONHAND, obj);
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZSDS0026.getDataset();
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
