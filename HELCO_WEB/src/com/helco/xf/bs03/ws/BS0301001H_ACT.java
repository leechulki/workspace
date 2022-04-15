package com.helco.xf.bs03.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class BS0301001H_ACT extends AbstractAction {
	public static final String ZWEB_MM_GET_ZMMR045 
	= "com.helco.xf.bs03.ws.ZWEB_MM_GET_ZMMR045";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	Dataset dsError = null;
	
	// 입력 파라메터 처리 
	ZMMS045[] list = new ZMMS045[]{};
	ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	Object obj[] = new Object[]{
			      DatasetUtility.getString(dsCond, "I_GBN")			
			    , DatasetUtility.getString(dsCond, "I_ITEM")
				, DatasetUtility.getString(dsCond, "I_LOFO")
				, DatasetUtility.getString(dsCond, "I_MNO")
				, DatasetUtility.getString(dsCond, "I_NAME1")
				, DatasetUtility.getString(dsCond, "I_PJT")
				, DatasetUtility.getString(dsCond, "I_RA")
				, DatasetUtility.getString(dsCond, "I_SP2")
			, listMsg
			, list
	};		

	// 호출 
	//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_ZMMR045, obj);

	// eai 도입으로 인해 변경
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_ZMMR045, obj, false);
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS045.getDataset();
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
		
		if ( dsColName.equals("CEBOMPRDAT") || dsColName.equals("CEBOMPODAT")
			 || dsColName.equals("CEBOMGRDAT") || dsColName.equals("CEBOMCHGDAT")) 
		{
			return ((String) colValue).equals("00000000") ? "" : (String) colValue;
		} 
		if ( dsColName.equals("CEBOMBDMNG")|| dsColName.equals("CEBOMMENGE") || dsColName.equals("CEBOMPOAMT") 
			|| dsColName.equals("CEBOMIPGO") || dsColName.equals("CEBOMGRAMT")
			|| dsColName.equals("CEBOMCHGO") || dsColName.equals("CEBOMCHAMT") || dsColName.equals("MENGE")) 
		{
			return Double.parseDouble((String)colValue) <= 0 ? "" : (String) colValue;
		} 
		return colValue;
	}
}
}
