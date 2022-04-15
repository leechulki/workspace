package com.helco.xf.ps04.ws;


import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.util.DatasetUtility;

public class PS0402001A_ACT extends AbstractAction {

	/**
	 * SMS발송 
	 * @param ctx
	 * @throws Exception
	 */
	public void sendMsg(BusinessContext ctx) throws Exception {
		PS0402001A_Service service = (PS0402001A_Service)
		ServiceFactoryManager.getService("PS0402001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");

		service.sendMsg(ctx, ds_list);
	}
	
	/**
	 * 기타외주작업지시서 RFC조회 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;

		ZTAXV001[] list = new ZTAXV001[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "BELNR","")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "BLDAT",""))
				, DatasetUtility.getString(dsCond, "WRKLFN","")
				, listMsg
				, list
		};

		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps04.ws.ZWEB_CO_GET_0001", obj);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZTAXV001.getDataset();

		dsList.setId("ds_list");
		// 필요 컬럼 - 추가
		//CallSAP.makeDsCol(dsList, "FLAG", 1);
		// ZPSS007[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));

		ctx.addOutputDataset(dsList);
	}
	
}
