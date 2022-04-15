package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.pp01.ws.PP0101001A_ACT.MyDatasetHelper;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102007A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR073 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR073";

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
			  DatasetUtility.getString(dsCond, "I_ATYPE")
			, DatasetUtility.getString(dsCond, "I_CHK")
			, DatasetUtility.getString(dsCond, "I_CHKF")
			, DatasetUtility.getString(dsCond, "I_FASPD")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
			, DatasetUtility.getString(dsCond, "I_ITEMNO")
			, DatasetUtility.getString(dsCond, "I_PGBN")
			, DatasetUtility.getString(dsCond, "I_RBT")
			, DatasetUtility.getString(dsCond, "I_SGBN")
			, DatasetUtility.getString(dsCond, "I_TASPD")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
			, DatasetUtility.getString(dsCond, "I_WGUBUN")
			, list
	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR073, obj);

	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS073.getDataset();
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
		
		if (     dsColName.equals("EDATU") || dsColName.equals("PPLDAT")|| dsColName.equals("SSCDAT")) 
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
