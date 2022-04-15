package com.helco.xf.bs03.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class BS0301001S_ACT extends AbstractAction {

	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		ZPPS081N[] list = new ZPPS081N[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
		          DatasetUtility.getString(dsCond, "CREID","")
		          , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "FCRDT",""))
		          , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "FDATE",""))
		          , DatasetUtility.getString(dsCond, "FLT_GUBUN","")
		          , DatasetUtility.getString(dsCond, "GUBUN","")
		          , DatasetUtility.getString(dsCond, "PSPID","")
		          , DatasetUtility.getString(dsCond, "STAT1","")
		          , DatasetUtility.getString(dsCond, "STAT2","")
		          , DatasetUtility.getString(dsCond, "STAT3","")
		          , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "TCRDT",""))
		          , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "TDATE",""))
		          , DatasetUtility.getString(dsCond, "TNAME","")
  				  , listMsg
  				  , list
		};
		
		// SAP Call 
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.bs03.ws.ZWEB_PP_GET_ZPPR081N", obj);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPPS081N.getDataset();
		dsList.setId("ds_list");
		
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
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.bs03.ws.ZWEB_PP_DEL_ZPPR081N", new Object[]{dsList});
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리 
			ZPPS081N[] list = (ZPPS081N[])
					CallSAP.moveDs2Obj(dsList, ZPPS081N.class, "GBN2");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// 저장 처리 
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.bs03.ws.ZWEB_PP_DEL_ZPPR081N", new Object[]{msgList, list});
			
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
