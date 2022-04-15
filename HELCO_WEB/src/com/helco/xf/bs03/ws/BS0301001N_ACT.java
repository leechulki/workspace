package com.helco.xf.bs03.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.bs03.ws.BS0301001N_ACT.MyDatasetHelper;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class BS0301001N_ACT extends AbstractAction 
{
	public static final String ZWEB_MM_GET_ZMMR057 	= "com.helco.xf.bs03.ws.ZWEB_MM_GET_ZMMR057";

/**
 * ��ȸ ó�� 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception 
{
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	Dataset dsError = null;
	
	// �Է� �Ķ���� ó�� 
	ZMMS057[] list = new ZMMS057[]{};
	ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	Object obj[] = new Object[]{
			  DatasetUtility.getString(dsCond, "I_ALTKN")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_ERDAT"))
			, DatasetUtility.getString(dsCond, "I_KTOKK")
			, DatasetUtility.getString(dsCond, "I_KUNNR")
			, DatasetUtility.getString(dsCond, "I_LIFNR")
			, DatasetUtility.getString(dsCond, "I_NAME1")
			, DatasetUtility.getString(dsCond, "I_SORTL")
			, listMsg
			, list
	};		

	// ȣ�� 
	//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_ZMMR057, obj);

	// eai �������� ���� ����
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_ZMMR057, obj, false);
	
	// ��� Dataset ���� 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS057.getDataset();
	ds.setId("ds_list");

	// ��� ���� �ű�� 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
	listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	dsError = CallSAP.makeErrorInfo(listMsg);

	if(dsError.getRowCount() == 0) {
		ctx.addOutputDataset(ds);
	} else {
		ctx.addOutputDataset(dsError);
	}
}

class MyDatasetHelper implements DatasetHelper {
	public Object getDsValue(String dsColName, Object colValue,
			Object orgObj) {

		if ( colValue == null ) {
			return null;
		}
		
		if (dsColName.equals("ERDAT")) 
		{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
		} 
		 
		return colValue;
	}
}
}
