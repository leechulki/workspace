package com.helco.xf.ps01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.ps01.ws.ZSDS0016;
import com.helco.xf.ps01.ws.ZSDS0017;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS0101004A_ACT extends AbstractAction {
	public static final String ZWEB_SD_HOGI_SPEC
		= "com.helco.xf.ps01.ws.ZWEB_SD_HOGI_SPEC";
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsError = null;

		// 입력 파라메터 처리 
		ZSDS0016[] list1 = new ZSDS0016[]{};
		ZSDS0017[] list2 = new ZSDS0017[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};

		Object obj[] = new Object[]{
				list2
				, DatasetUtility.getString(dsCond, "HNO")
				, listMsg
				, list1
		};
		
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"), ZWEB_SD_HOGI_SPEC, obj,false);
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),ZWEB_SD_HOGI_SPEC, obj, false);
		// 출력 Dataset 생성 
		Dataset ds1 = CallSAP.isJCO() ? new Dataset() : ZSDS0016.getDataset();
		Dataset ds2 = CallSAP.isJCO() ? new Dataset() : ZSDS0017.getDataset();
		ds1.setId("ds_list1");
		ds2.setId("ds_list2");

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds1, stub.getOutput("T_ITAB"));
		CallSAP.moveObj2Ds(ds2, stub.getOutput("H_ITAB"));
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);

		if(dsError.getRowCount() == 0) {
			ctx.addOutputDataset(ds1);
			ctx.addOutputDataset(ds2);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
}