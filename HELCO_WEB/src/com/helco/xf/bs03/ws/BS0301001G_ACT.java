package com.helco.xf.bs03.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class BS0301001G_ACT extends AbstractAction {
	public static final String ZWEB_MM_GET_ZMMR018 
	= "com.helco.xf.bs03.ws.ZWEB_MM_GET_ZMMR018";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	Dataset dsError = null;
	
	// 입력 파라메터 처리 
	ZMMS018[] list = new ZMMS018[]{};
	ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	Object obj[] = new Object[]{
			      CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_CRDAT1"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_CRDAT2"))
				, DatasetUtility.getString(dsCond, "I_IDNRK")
				, DatasetUtility.getString(dsCond, "I_MATKL")
				, DatasetUtility.getString(dsCond, "I_MATKLU")
				, DatasetUtility.getString(dsCond, "I_MATNR")
				, DatasetUtility.getString(dsCond, "I_RA")
				, DatasetUtility.getString(dsCond, "I_SPECT")
				, DatasetUtility.getString(dsCond, "I_WOKNUM")
			, listMsg
			, list
	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_ZMMR018, obj);

	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS018.getDataset();
	ds.setId("ds_list");

	// 목록 정보 옮기기 
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
		
		if (     dsColName.equals("CRDAT")) 
		{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
		} 
		return colValue;
	}
}
}
