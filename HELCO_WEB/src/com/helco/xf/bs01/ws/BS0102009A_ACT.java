package com.helco.xf.bs01.ws;

import java.math.BigDecimal;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.bs01.ws.ZSDS0016;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class BS0102009A_ACT extends AbstractAction {
	public static final String ZWEB_SD_ESTIMATED_COST
		= "com.helco.xf.bs01.ws.ZWEB_SD_ESTIMATED_COST";
	/**
	 * 원가 계산 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsIn = ctx.getInputDataset("ds_in");
		Dataset dsList3 = ctx.getInputDataset("ds_list3");
		SapFunction stub = null;

		// 입력 파라메터 처리 
		ZSDS0024[] list2 = new ZSDS0024[]{};
		ZSDS0016[] list3 = new ZSDS0016[]{};
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		Dataset dsError = null;

		list3 = (ZSDS0016[])CallSAP.moveDs2Obj(dsList3, ZSDS0016.class, "");
		msgList = new ZQMSEMSG[]{};

		Object obj[] = new Object[]{
				list2
				, DatasetUtility.getString(dsIn, "ISR")
				, DatasetUtility.getString(dsIn, "RDT")
				, DatasetUtility.getString(dsIn, "SEQ")
				, DatasetUtility.getString(dsIn, "SIR")
				, msgList
				, list3
		};

		// 저장 처리 
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_ESTIMATED_COST, obj);

		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(msgList);
		
		ctx.addOutputDataset(dsError);
	}
}
