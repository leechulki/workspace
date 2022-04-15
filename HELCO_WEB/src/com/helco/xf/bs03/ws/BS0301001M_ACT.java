package com.helco.xf.bs03.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class BS0301001M_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR050 
	= "com.helco.xf.bs03.ws.ZWEB_PP_GET_ZPPR050";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	
	// 입력 파라메터 처리 
	ZPPS050[] list = new ZPPS050[]{};
	Object obj[] = new Object[]{
			  CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
			, DatasetUtility.getString(dsCond, "I_GUBUN")
			, DatasetUtility.getString(dsCond, "I_GUBUN1")
			, DatasetUtility.getString(dsCond, "I_P1")
			, DatasetUtility.getString(dsCond, "I_P3")
			, DatasetUtility.getString(dsCond, "I_P4")
			, DatasetUtility.getString(dsCond, "I_P5")
			, DatasetUtility.getString(dsCond, "I_PSPID")
			, DatasetUtility.getString(dsCond, "I_RA")
			, DatasetUtility.getString(dsCond, "I_REGIO")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
			, DatasetUtility.getString(dsCond, "I_TNAME")
			, DatasetUtility.getString(dsCond, "I_WERKS")
			, list
	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR050, obj);

	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS050.getDataset();
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
		
		if (     dsColName.equals("ILDAT") || dsColName.equals("EDATU") || dsColName.equals("ZDAT1")
				 || dsColName.equals("ZDAT2") || dsColName.equals("ZDAT3") || dsColName.equals("REMOV") || dsColName.equals("RECAD_DA")) 
		{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
		} 
/*		if (     dsColName.equals("JMENGE") || dsColName.equals("MMENGE") || dsColName.equals("SMENGE")  
				  || dsColName.equals("CMENGE") || dsColName.equals("GMENGE")) 
		{
			return Double.parseDouble((String)colValue) <= 0 ? "" : (String) colValue;
		} */
		return colValue;
	}
}
}
