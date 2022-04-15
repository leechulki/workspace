package com.helco.xf.qm03.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class QM0301002A_ACT extends AbstractAction {

	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 호기정보 처리
	    String hogit = dsCond.getColumnAsString(0, "HOGIT");
		if (hogit.length() == 9)
		{
			dsCond.setColumn(0, "HOGIT", hogit+"-P");
		}
		
		
		ZQMS021[] list = new ZQMS021[]{};
		Object obj[] = null;
		SapFunction stub = null;
		if ( DatasetUtility.getString(dsCond, "STATUS1", "").equals("X")) {
			obj = new Object[]{
				DatasetUtility.getString(dsCond, "HOGIF","")
				, DatasetUtility.getString(dsCond, "HOGIT","")
				, DatasetUtility.getString(dsCond, "LIFNR","")
				, list
			};
			
			// SAP Call 
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.qm03.ws.ZWEB_QM_GET_OUT_REQUEST_LIST", obj);
		} else {
			obj = new Object[]{
				DatasetUtility.getString(dsCond, "HOGIF")
				, DatasetUtility.getString(dsCond, "HOGIT")
				, DatasetUtility.getString(dsCond, "LIFNR")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PACFDTF"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PACFDTT"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PAPRDTF"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PAPRDTT"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PARQDTF"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PARQDTT"))
			    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "QMCHECK"))
			    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS2"))
			    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS3"))
			    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS4"))
			    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS5"))
			    , list
				};
			// SAP Call 
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.qm03.ws.ZWEB_QM_GET_OUT_REQUESTED_LIST", obj);
		}
		// Dataset make
		Dataset dsList = CallSAP.isJCO() ? new Dataset() : ZQMS021.getDataset();
		dsList.setId("ds_list");
		// 필요 컬럼 - 추가 
		CallSAP.makeDsCol(dsList, "CHK", 1);
		CallSAP.makeDsCol(dsList, "FLAG", 1);
		CallSAP.makeDsCol(dsList, "TMODE", 1);
		CallSAP.makeDsCol(dsList, "PS_PSP_PNR", 1);
		
		// ZQMS021[] -> dsList
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
			dsError = new Dataset("ds_errr");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.qm03.ws.ZWEB_QM_GET_OUT_REQUEST_SAVE", new Object[]{dsList});
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리 
			ZQMS022[] list = (ZQMS022[])
					CallSAP.moveDs2Obj(dsList, ZQMS022.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// 저장 처리 
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.qm03.ws.ZWEB_QM_GET_OUT_REQUEST_SAVE", new Object[]{msgList, list});
			
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
