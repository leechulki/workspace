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

public class PP0102024A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR073A_C 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR073A_C";

/**
 * ��ȸ ó�� 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;
	

	// �Է� �Ķ���� ó�� 
	ZPPS073A_C[]  list = new ZPPS073A_C[]{};
	
	Object obj[] = new Object[]{
			//, listMsg
			  DatasetUtility.getString(dsCond, "I_GUBUN")
			, DatasetUtility.getString(dsCond, "I_GUBUN2")  
			, DatasetUtility.getString(dsCond, "I_MATNR")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, DatasetUtility.getString(dsCond, "I_PSPID")			
			, StringUtils.substring(DatasetUtility.getString(dsCond, "I_YYMM"),0,6)		
			, list

	};		

	// ȣ�� 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR073A_C, obj);
	
	// ��� Dataset ���� 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS073A_C.getDataset();
	ds.setId("ds_tab");
	
	// ��� ���� �ű�� 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
	ctx.addOutputDataset(ds);
	//CallSAP.moveObj2Ds(ds2, stub.getOutput("O_LIST"));
	//ctx.addOutputDataset(ds2);
	//listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	//dsError = CallSAP.makeErrorInfo(listMsg);
}
}
