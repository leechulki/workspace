package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.pp01.ws.PP0102013A_ACT.MyDatasetHelper;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102030A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR024P 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR024P";

/**
 * ��ȸ ó�� 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	
	// �Է� �Ķ���� ó�� 
	ZPPS024P[] list = new ZPPS024P[]{};
	Object obj[] = new Object[]{
			  CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
			, DatasetUtility.getString(dsCond, "I_GUBUN")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
			, DatasetUtility.getString(dsCond, "I_TYPE")
			, list
	};		

	// ȣ�� 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR024P, obj);

	// ��� Dataset ���� 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS024P.getDataset();
	ds.setId("ds_list");

	// ��� ���� �ű�� 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
	ctx.addOutputDataset(ds);
}

class MyDatasetHelper implements DatasetHelper {
	public Object getDsValue(String dsColName, Object colValue,
			Object orgObj) {

		if ( colValue == null ) {
			return null;
		} 

		if ( !dsColName.equals("ILDAT") && !dsColName.equals("DAYTXT") ) {
			return Double.parseDouble((String)colValue) <= 0 ? "" : Integer.parseInt((String)colValue);
		} 
		return colValue;
	}
}
}
