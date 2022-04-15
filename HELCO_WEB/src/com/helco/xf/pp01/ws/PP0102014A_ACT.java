package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102014A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR200 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR200";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;

	// 입력 파라메터 처리 
	ZPPS200[] list = new ZPPS200[]{};
	//ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	
	Object obj[] = new Object[]{
			//, listMsg
			  DatasetUtility.getString(dsCond, "I_AUFNR")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE2"))
			, DatasetUtility.getString(dsCond, "I_GUBUN")
			, DatasetUtility.getString(dsCond, "I_GUBUN2")
			, DatasetUtility.getString(dsCond, "I_JO")
			, DatasetUtility.getString(dsCond, "I_NOK")
			, DatasetUtility.getString(dsCond, "I_NOWORK")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, DatasetUtility.getString(dsCond, "I_PSPID")
			, DatasetUtility.getString(dsCond, "I_SHIP")
			, DatasetUtility.getString(dsCond, "I_STATUS")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE2"))
			, list

	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR200, obj);
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS200.getDataset();
	ds.setId("ds_list");
	ctx.addOutputDataset(ds);

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
	ctx.addOutputDataset(ds);
	//listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	//dsError = CallSAP.makeErrorInfo(listMsg);
}
}
