package com.helco.xf.ps02.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PS0203001A_ACT extends AbstractAction {

	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		ZPSS010[] list = new ZPSS010[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATEF",""))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATET",""))
				, DatasetUtility.getString(dsCond, "PSPID","")
				, DatasetUtility.getString(dsCond, "TYPE1","")
				, DatasetUtility.getString(dsCond, "TYPE2","")
				, DatasetUtility.getString(dsCond, "ZZACTSS","")
				, DatasetUtility.getString(dsCond, "ZZLIFNR","")
				, listMsg
				, list
		};
		
		// SAP Call 
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0006", obj,false);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPSS010.getDataset();
		dsList.setId("ds_list");
		// 필요 컬럼 - 추가 
		CallSAP.makeDsCol(dsList, "CHK", 1);
		CallSAP.makeDsCol(dsList, "FLAG", 1);
		CallSAP.makeDsCol(dsList, "TYPE1", 1);
		CallSAP.makeDsCol(dsList, "MODE1", 1);
		
		// ZPSS010[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));
		ctx.addOutputDataset(dsList);
	}
	
	/**
	 * 저장 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList = ctx.getInputDataset("ds_list");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );
		
		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0002", new Object[]{dsList},false);
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리 
			ZPSS011[] list = (ZPSS011[])
					CallSAP.moveDs2Obj(dsList, ZPSS011.class, "CHK");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// 저장 처리 
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0002", new Object[]{msgList, list},false);
			
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}
		
		if ( dsError.getRowCount() == 0 ) {
			// 조회 호출 
			query(ctx);
		}
		
		ctx.addOutputDataset(dsError);
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
