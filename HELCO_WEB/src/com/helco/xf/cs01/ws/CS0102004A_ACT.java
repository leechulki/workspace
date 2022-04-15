package com.helco.xf.cs01.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class CS0102004A_ACT extends AbstractAction {
	
	public static final String ZPP_GET_SN_MATNR 
		= "com.helco.xf.cs01.ws.ZPP_GET_SN_MATNR";

	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		// 입력 파라메터 처리 
		ZPPS016[] list = new ZPPS016[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;
		
		Object obj[] = new Object[]{
				list
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "BUDAT_FM"))
				, DatasetUtility.getString(dsCond, "POSID")
				, DatasetUtility.getString(dsCond, "SCODE")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "BUDAT_TO"))
				, listMsg
		};		

		// 호출 
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZPP_GET_SN_MATNR, obj, false);		 // 기존  SAP 연결
		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS016.getDataset();
		ds.setId("ds_list");

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("E_INTAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);
		
		if (dsError.getRowCount() == 0) {
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}

}
