package com.helco.xf.cs01.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class CS0102002B_ACT extends AbstractAction {

	/**
	 * 저장 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsList = ctx.getInputDataset("ds_list");
		Dataset dsError = null;
		SapFunction stub = null;
			
		// 입력 파라메터 처리 
		ZMMS006[] list =  (ZMMS006[])CallSAP.moveDs2Obj(dsList, ZMMS006.class,"");
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		
		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "LGORT")
				, DatasetUtility.getString(dsCond, "MATNR")
				, DatasetUtility.getString(dsCond, "MENGE")
				, DatasetUtility.getString(dsCond, "REQITEM")
				, DatasetUtility.getString(dsCond, "REQNR")
				, msgList
				, list
		};

		
		//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT") ,"com.helco.xf.cs01.ws.ZWEB_MM_SET_SUPPLY_BOSUIPGO", obj);
		
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), "com.helco.xf.cs01.ws.ZWEB_MM_SET_SUPPLY_BOSUIPGO", obj, false);		 // 기존  SAP 연결
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), "com.helco.xf.cs01.ws.ZWEB_MM_SET_SUPPLY_BOSUIPGO", obj, false);  // EAI 연결

		

		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );

		ctx.addOutputDataset(dsError);
	}
}
