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
 * ��ȸ ó�� 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;

	// �Է� �Ķ���� ó�� 
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

	// ȣ�� 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR092, obj);
	
	// ��� Dataset ���� 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS092.getDataset();
	ds.setId("ds_list");
	
	// ��� Dataset ���� 
	//Dataset ds2 = CallSAP.isJCO() ? new Dataset() : ZPPS073AL.getDataset();
	//ds2.setId("ds_list");
	//ctx.addOutputDataset(ds2);

	// ��� ���� �ű�� 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
	ctx.addOutputDataset(ds);
	//CallSAP.moveObj2Ds(ds2, stub.getOutput("O_LIST"));
	//ctx.addOutputDataset(ds2);
	//listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	//dsError = CallSAP.makeErrorInfo(listMsg);
}
}
