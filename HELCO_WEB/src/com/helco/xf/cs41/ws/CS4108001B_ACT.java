package com.helco.xf.cs41.ws;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;


public class CS4108001B_ACT extends AbstractAction {
	public static final String ZCS_GET_MATNR
		= "com.helco.xf.cs41.ws.ZCS_GET_MATNR";

	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZCSS012[] list = new ZCSS012[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;

		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "PSPID")	
				, listMsg
				, list
		};

		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZCS_GET_MATNR, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZCS_GET_MATNR, obj, false);		 // 기존  SAP 연결
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZCS_GET_MATNR, obj, false);  // EAI 연결

		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCSS012.getDataset();
		ds.setId("ds_list");
		CallSAP.makeDsCol(ds, "CHK", 0);

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);

		if (dsError.getRowCount() == 0) {
			for(int i=0; i<ds.getRowCount(); i++) {
//				if (TitUtility.isNull(DatasetUtility.getString(ds, "MBLNR"))) {
//					ds.setColumn(i, "MBLNR", "");	
//				}
				ds.setColumn(i, "WON", StrictMath.round(ds.getColumnAsDouble(i, "WON") * 100));
				ds.setColumn(i, "MAT_AMT", StrictMath.round(ds.getColumnAsDouble(i, "MAT_AMT") * 100));
				ds.setColumn(i, "MAN_AMT", StrictMath.round(ds.getColumnAsDouble(i, "MAN_AMT") * 100));
			}
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
}