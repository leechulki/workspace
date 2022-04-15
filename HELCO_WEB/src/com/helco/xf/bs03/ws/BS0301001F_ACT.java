package com.helco.xf.bs03.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class BS0301001F_ACT extends AbstractAction {
	public static final String ZWEB_MM_PJT_ITEM_STATE 
	= "com.helco.xf.bs03.ws.ZWEB_MM_PJT_ITEM_STATE";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	Dataset dsError = null;
	
	// 입력 파라메터 처리 
	ZMMS010[] list = new ZMMS010[]{};
	ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	Object obj[] = new Object[]{
			  DatasetUtility.getString(dsCond, "I_CHECK")
			, DatasetUtility.getString(dsCond, "I_MATNR")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, listMsg
			, list
	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_PJT_ITEM_STATE, obj);

	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS010.getDataset();
	ds.setId("ds_list");

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
	
	if( DatasetUtility.getString(dsCond, "GBN").equals("10"))
	{
		for(int i=0; i<ds.getRowCount(); i++){
			ds.setColumn(i, "LIFNR","");
			ds.setColumn(i, "LNAME","");
		}
	}
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
		
		if (     dsColName.equals("BADAT") || dsColName.equals("BEDAT")
		      || dsColName.equals("BUDAT") || dsColName.equals("BUDAT2")) 
		{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
		} 
		if (     dsColName.equals("MENGE1") || dsColName.equals("MENGE2") || dsColName.equals("MENGE3")  
				  || dsColName.equals("MENGE4")) 
		{
			return Double.parseDouble((String)colValue) <= 0 ? "" : (String) colValue;
		} 
		return colValue;
	}
}
}
