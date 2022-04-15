package com.helco.xf.mm05.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class MM0501003A_ACT extends AbstractAction {

	/**
	 * ���� 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsChulha = ctx.getInputDataset("ds_chulha");
		Dataset dsError = null;
		SapFunction stub = null;
		
		// �Է� �Ķ���� ó�� 
		ZLES007[] list = (ZLES007[])CallSAP.moveDs2Obj(dsChulha, ZLES007.class,"", new SaveDs2ObjHelper());
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		
		
		
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT")
				 	,"com.helco.xf.mm05.ws.ZWEB_LE_SET_281GI" 
					, new Object[]{msgList,list});

		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );

		ctx.addOutputDataset(dsError);
	}

	/**
	 * ��� 
	 * @param ctx
	 * @throws Exception
	 */
	public void cancel(BusinessContext ctx) throws Exception {
		Dataset dsCancel = ctx.getInputDataset("ds_cancel");
		Dataset dsError = null;
		SapFunction stub = null;
		
		// �Է� �Ķ���� ó�� 
		ZLES008[] list = new ZLES008[]{};
		list = (ZLES008[])CallSAP.moveDs2Obj(dsCancel, ZLES008.class,"", new CancelDs2ObjHelper());
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		
		
		
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT")
				 	,"com.helco.xf.mm05.ws.ZWEB_LE_SET_281CANCEL" 
					, new Object[]{msgList,list});

		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );

		ctx.addOutputDataset(dsError);
	}
	
	class SaveDs2ObjHelper implements Ds2ObjHelper {
		public void endMoveRow(Dataset ds, int row, Object obj) {
			// ���� ���� ��Ŵ. 
			ZLES007 tmpObj = ((ZLES007)obj);
			tmpObj.setBUDAT(CallSAP.getFormatDate(tmpObj.getBUDAT()));	// ��������
		}
	}

	class CancelDs2ObjHelper implements Ds2ObjHelper {
		public void endMoveRow(Dataset ds, int row, Object obj) {
			// ���� ���� ��Ŵ. 
			ZLES008 tmpObj = ((ZLES008)obj);
			tmpObj.setBUDAT(CallSAP.getFormatDate(tmpObj.getBUDAT()));	// ��������
		}
	}
}
