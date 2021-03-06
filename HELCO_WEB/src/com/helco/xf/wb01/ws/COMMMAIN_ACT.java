package com.helco.xf.wb01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.wb01.ws.ZBCS001;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class COMMMAIN_ACT extends AbstractAction {
	public static final String ZLOGIN
		= "com.helco.xf.wb01.ws.ZLOGIN";
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void login(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_uid");
		Dataset dsError = null;

		// 입력 파라메터 처리 
		ZBCS001[] list = new ZBCS001[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};

		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "I_GW_ID")
				, DatasetUtility.getString(dsCond, "I_GW_IP")
				, listMsg
				, list
		};

		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZLOGIN, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZLOGIN, obj, false);		 // 기존  SAP 연결
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZLOGIN, obj, false);  // EAI 연결


		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZBCS001.getDataset();
		ds.setId("ds_url");

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);

		if (dsError.getRowCount() == 0) {
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
}