package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102006A_ACT extends AbstractAction {

	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		ZPPS203A[] list = new ZPPS203A[]{};
//		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "DGBN","")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "FDATE",""))
				, DatasetUtility.getString(dsCond, "FDOOR","")
				, DatasetUtility.getString(dsCond, "HGBN","")
				, DatasetUtility.getString(dsCond, "ITEMNO","")
				, DatasetUtility.getString(dsCond, "POSID","")
				, DatasetUtility.getString(dsCond, "PSPID","")
				, DatasetUtility.getString(dsCond, "SGBN","")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "TDATE",""))
//				, listMsg
				, list
		};
		
		// SAP Call 
		//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR203A", obj);
		
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR203A", obj, false);		 // 기존  SAP 연결
		
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPPS203A.getDataset();

		dsList.setId("ds_list");
		// 필요 컬럼 - 추가 
//		CallSAP.makeDsCol(dsList, "R_GBN", 1);
		
		// ZPPS203A[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));
		ctx.addOutputDataset(dsList);
	}

	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			}
			
			if ( dsColName.equals("EXPORT") ) {
				return ((String) colValue).equals("0") ? "1" : "0";
			} else if ( dsColName.equals("INSGB")) {
				return ((String) colValue).equals("X") ? "1" : "0";
			}
			return colValue;
		}
	}	
}
