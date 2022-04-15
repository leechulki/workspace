package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.pp01.ws.PP0101001A_ACT.MyDatasetHelper;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102005A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR072 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR072";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	
	// 입력 파라메터 처리 
	ZPPS073[] list = new ZPPS073[]{};
	Object obj[] = new Object[]{
			  DatasetUtility.getString(dsCond, "I_ASPDF")
			, DatasetUtility.getString(dsCond, "I_ASPDT")
			, DatasetUtility.getString(dsCond, "I_ATYPE")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
			, DatasetUtility.getString(dsCond, "I_ITEMNO")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, DatasetUtility.getString(dsCond, "I_RAD1")
			, DatasetUtility.getString(dsCond, "I_RAD2")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
			, DatasetUtility.getString(dsCond, "I_WERKS")
			, DatasetUtility.getString(dsCond, "I_WOGBN")
			, list
	};		

	// 호출 
	//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR072, obj);

	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR072, obj, false);		 // 기존  SAP 연결
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS072.getDataset();
	ds.setId("ds_list");

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
	ctx.addOutputDataset(ds);
}

class MyDatasetHelper implements DatasetHelper {
	public Object getDsValue(String dsColName, Object colValue,
			Object orgObj) {

		if ( colValue == null ) {
			return null;
		}
		
		if (     dsColName.equals("PPEDAT") || dsColName.equals("PPMDAT")
			  || dsColName.equals("ILDAT") || dsColName.equals("PPIDAT")) 
		{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
		} 
		if (     dsColName.equals("PMENGE") || dsColName.equals("TMENGE") || dsColName.equals("EMENGE")) 
		{
			return Double.parseDouble((String)colValue) <= 0 ? "" : (String) colValue;
		} 
		return colValue;
	}
}
}
