package com.helco.xf.pp01.ws;

import org.apache.commons.lang.StringUtils;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102020A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR092
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR092";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;

	// 입력 파라메터 처리 
	ZPPS092[]  list = new ZPPS092[]{};
	
	Object obj[] = new Object[]{
			//, listMsg
			  CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATE"))	
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATE2"))	
			, DatasetUtility.getString(dsCond, "I_GBN")
			, DatasetUtility.getString(dsCond, "I_ITEMNO")
			, DatasetUtility.getString(dsCond, "I_LIFNR")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, list

	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR092, obj);
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS092.getDataset();
	ds.setId("ds_list");
	
	// 출력 Dataset 생성 
	//Dataset ds2 = CallSAP.isJCO() ? new Dataset() : ZPPS073AL.getDataset();
	//ds2.setId("ds_list");
	//ctx.addOutputDataset(ds2);

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
	ctx.addOutputDataset(ds);
	//CallSAP.moveObj2Ds(ds2, stub.getOutput("O_LIST"));
	//ctx.addOutputDataset(ds2);
	//listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	//dsError = CallSAP.makeErrorInfo(listMsg);
}
}
