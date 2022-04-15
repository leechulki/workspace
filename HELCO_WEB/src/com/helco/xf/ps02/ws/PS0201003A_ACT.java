package com.helco.xf.ps02.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PS0201003A_ACT extends AbstractAction {

	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		ZPSS006[] list = new ZPSS006[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATEF",""))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATET",""))
				, DatasetUtility.getString(dsCond, "GUBUN","")
				, DatasetUtility.getString(dsCond, "PSPID","")
				, DatasetUtility.getString(dsCond, "ZZACTSS","")
				, DatasetUtility.getString(dsCond, "ZZGUBUN10","")
				, DatasetUtility.getString(dsCond, "ZZGUBUN11","")
				, DatasetUtility.getString(dsCond, "ZZGUBUN12","")
				, DatasetUtility.getString(dsCond, "ZZGUBUN20","")
				, DatasetUtility.getString(dsCond, "ZZINTER","")
				, DatasetUtility.getString(dsCond, "ZZLIFNR","")
				, DatasetUtility.getString(dsCond, "ZZPMNUM","")
				, DatasetUtility.getString(dsCond, "ZZSHIP","")
				, DatasetUtility.getString(dsCond, "ZZTEMNO","")
				, listMsg
				, list
		};
		
		// SAP Call 
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0002", obj,false);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPSS006.getDataset();
		dsList.setId("ds_list");
		// 필요 컬럼 - 추가 
		CallSAP.makeDsCol(dsList, "R_GBN", 1);

		// ZPSS006[] -> dsList
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
