package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.pp01.ws.PP0102013A_ACT.MyDatasetHelper;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102013A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR024 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR024";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	
	// 입력 파라메터 처리 
	ZPPS024[] list = new ZPPS024[]{};
	Object obj[] = new Object[]{
			  list
			, DatasetUtility.getString(dsCond, "I_P_GUBUN")
			, DatasetUtility.getString(dsCond, "I_P_TYPE")
			, DatasetUtility.getString(dsCond, "I_P_WERKS")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_S_DATE_HIGH"))
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_S_DATE_LOW"))
			, DatasetUtility.getString(dsCond, "I_S_NARA")
	};		

	// 호출 
	//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR024, obj);
    
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR024, obj, false);		 // 기존  SAP 연결
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS024.getDataset();
	ds.setId("ds_list");

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("GT_LIST"), new MyDatasetHelper());
	ctx.addOutputDataset(ds);
}

class MyDatasetHelper implements DatasetHelper {
	public Object getDsValue(String dsColName, Object colValue,
			Object orgObj) {

		if ( colValue == null ) {
			return null;
		} 

		if ( !dsColName.equals("DATE") && !dsColName.equals("DAYTXT") ) {
			return Double.parseDouble((String)colValue) <= 0 ? "" : Integer.parseInt((String)colValue);
		} 
		return colValue;
	}
}
}
