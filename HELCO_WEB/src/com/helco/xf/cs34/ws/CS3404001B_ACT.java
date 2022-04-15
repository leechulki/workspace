package com.helco.xf.cs34.ws;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.comm.ZPSSEMSG;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs34.ws.ZCSS011;
import com.helco.xf.cs34.ws.ZCSS007;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class CS3404001B_ACT extends AbstractAction {
	public static final String ZWEB_CS_CHANGE_BILLPLAN
		= "com.helco.xf.cs34.ws.ZWEB_CS_CHANGE_BILLPLAN";

	/**
	 * 저장 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList1 = ctx.getInputDataset("ds_list3");
		Dataset dsError = null;
		SapFunction stub = null;

		// 입력 파라메터 처리 
		ZCSS011[] list1 = new ZCSS011[]{};
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

		// 입력 파라메터 처리 
		if(CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_CHANGE_BILLPLAN, new Object[]{dsList1});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_CHANGE_BILLPLAN, new Object[]{dsList1}, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_CHANGE_BILLPLAN, new Object[]{dsList1}, false);  // EAI 연결


			//CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			list1 = (ZCSS011[])CallSAP.moveDs2Obj(dsList1, ZCSS011.class, "SO_NO");
			msgList = new ZQMSEMSG[]{};

			Object obj[] = new Object[]{msgList, list1};

			for(int i=0; i<list1.length; i++) {
				list1[i].FAKWR = list1[i].FAKWR.setScale(2, RoundingMode.DOWN);
			}

			// 저장 처리 
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_CHANGE_BILLPLAN, obj);
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_CHANGE_BILLPLAN, obj, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_CHANGE_BILLPLAN, obj, false);  // EAI 연결


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