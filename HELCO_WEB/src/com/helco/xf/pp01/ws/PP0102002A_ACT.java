package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102002A_ACT extends AbstractAction {

	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsItab1 = ctx.getInputDataset("ds_posid");
		Dataset dsItab2 = ctx.getInputDataset("ds_aufnr");
		Dataset dsItab3 = ctx.getInputDataset("ds_item");
		SapFunction stub = null;
		Dataset dsList = null;
		ZPPS007A[] list = new ZPPS007A[]{};
		ZPPSITAB1[] posid = (ZPPSITAB1[])CallSAP.moveDs2Obj(dsItab1, ZPPSITAB1.class, "POSID");
		ZPPSITAB2[] aufnr = (ZPPSITAB2[])CallSAP.moveDs2Obj(dsItab2, ZPPSITAB2.class, "AUFNR");
		ZPPSITAB3[] itemno = (ZPPSITAB3[])CallSAP.moveDs2Obj(dsItab3, ZPPSITAB3.class, "ITEMNO");
		Object obj[] = new Object[]{
		            DatasetUtility.getString(dsCond, "ARBPL" ,"")
		          , DatasetUtility.getString(dsCond, "AUFNR" ,"")
		          , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "FDATE" ,""))
		          , DatasetUtility.getString(dsCond, "FLAG"  ,"")
		          , DatasetUtility.getString(dsCond, "GUBUN" ,"")
		          , DatasetUtility.getString(dsCond, "ITEMNO","")
		          , DatasetUtility.getString(dsCond, "PART"  ,"")
		          , DatasetUtility.getString(dsCond, "POSID" ,"")
		          , DatasetUtility.getString(dsCond, "RBT"   ,"")
		          , posid
		          , aufnr
		          , itemno
		          , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "TDATE" ,""))
				  , list
		};		
		
		// SAP Call 
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR007", obj);

		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPPS007A.getDataset();
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
		Dataset dsList2 = null;
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );

		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.pp01.ws.ZWEB_PP_SET_ZPPR007", new Object[]{ctx.getInputVariable("FLAG"),ctx.getInputVariable("RBT"),dsList});
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리 
			ZPPS007A[] list = (ZPPS007A[])CallSAP.moveDs2Obj(dsList, ZPPS007A.class, "", new MyDs2ObjHelper());

			// 저장 처리 
			SapFunction stub = CallSAP.callSap(
					ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.pp01.ws.ZWEB_PP_SET_ZPPR007", 
					new Object[]{ctx.getInputVariable("FLAG"),ctx.getInputVariable("RBT"),list});

			dsList2 = CallSAP.isJCO() ? new Dataset() : ZPPS007A.getDataset();
			dsList2.setId("ds_list");
			CallSAP.moveObj2Ds(dsList2, stub.getOutput("O_TAB"));
			
			ctx.addOutputDataset(dsList2);			
		}
	}
	
	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,	Object orgObj) {
			if ( colValue == null ) {
				return null;
			}
			return colValue;
		}
	}
	class MyDs2ObjHelper implements Ds2ObjHelper {
		public void endMoveRow(Dataset ds, int row, Object obj) {
			// 일자 변경 시킴.
			ZPPS007A tmpObj = ((ZPPS007A)obj);
			tmpObj.setBUDAT(CallSAP.getFormatDate(tmpObj.getBUDAT()));	// 실적일
		}
	}	
}
