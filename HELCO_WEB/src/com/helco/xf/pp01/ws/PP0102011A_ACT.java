package com.helco.xf.pp01.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

public class PP0102011A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR041 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR041";

/**
 * ��ȸ ó�� 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	
	// �Է� �Ķ���� ó�� 
	ZPPS041[] list = new ZPPS041[]{};
	Object obj[] = new Object[]{
			  DatasetUtility.getString(dsCond, "I_CHK")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
			, DatasetUtility.getString(dsCond, "I_GUBUN")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
			, list
	};		

	// ȣ�� 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR041, obj);

	// ��� Dataset ���� 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS041.getDataset();
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
		
		if (     dsColName.equals("PNDAT") || dsColName.equals("SHDAT")|| dsColName.equals("SDDAT")
				|| dsColName.equals("PLDAT")|| dsColName.equals("PPDAT")) 
		{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
		} 

		return colValue;
	}
}
}
