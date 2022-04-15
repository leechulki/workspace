package com.helco.xf.mm03.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class MM0301003A_ACT extends AbstractAction {

	/**
	 * 저장 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsSum3 = ctx.getInputDataset("ds_sum3");
		Dataset dsError = null;
		SapFunction stub = null;
			
		// 입력 파라메터 처리 
		ZMMS011[] list =  (ZMMS011[])CallSAP.moveDs2Obj(dsSum3, ZMMS011.class,"");
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		
		
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT")
				 	,"com.helco.xf.mm03.ws.ZWEB_MM_BARCODE_TAG" 
					, new Object[]{msgList,list});

		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );

		ctx.addOutputDataset(dsError);
	}
}
