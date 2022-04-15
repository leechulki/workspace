package com.helco.xf.mm07.ws;

//import javax.faces.validator.LengthValidator;

import com.helco.xf.mm07.ws.MM0701003A_ACT.MyDatasetHelper;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
//import tit.util.StringOperator;

public class MM0701003A_ACT extends AbstractAction {
	public static final String ZMM_INFORECORD 
	= "com.helco.xf.mm07.ws.ZMM_INFORECORD";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	Dataset dsError = null;

	// 입력 파라메터 처리 
	ZMMS021[] list = new ZMMS021[]{};
	ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	
	Object obj[] = new Object[]{
			//, listMsg
			 DatasetUtility.getString(dsCond, "I_DZEINR")
			, DatasetUtility.getString(dsCond, "I_GUBN") 
			, DatasetUtility.getString(dsCond, "I_MATNR") 
			, DatasetUtility.getString(dsCond, "I_MATNR_NM") 
			, DatasetUtility.getString(dsCond, "I_POSID") 
			, DatasetUtility.getString(dsCond, "I_SPEC1") 
			, DatasetUtility.getString(dsCond, "I_SPEC2") 
			, DatasetUtility.getString(dsCond, "I_SPEC3")
		    , DatasetUtility.getString(dsCond, "I_SPEC4")
		    , DatasetUtility.getString(dsCond, "I_SPEC5")
		    , DatasetUtility.getString(dsCond, "I_USNAM")
			, listMsg
		    , list

	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZMM_INFORECORD, obj);
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS021.getDataset();
	ds.setId("ds_list");
	
	
	
	//CallSAP.makeDsCol(ds, "CHECK", 0);
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
	listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	dsError = CallSAP.makeErrorInfo(listMsg);
	
	if(dsError.getRowCount() == 0) {
		ctx.addOutputDataset(ds);
	} else {
		ctx.addOutputDataset(dsError);
	}

	//listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	//dsError = CallSAP.makeErrorInfo(listMsg);
}

class MyDatasetHelper implements DatasetHelper {
	public Object getDsValue(String dsColName, Object colValue,
			Object orgObj) {

		if ( colValue == null ) {
			return null;
		}
		
		return colValue;
	}
}

}
