package com.helco.xf.qm01.ws;


import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;


import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
/**
 * 자재협력사에서 자재 입고에 따른 출장검사요청 정보 조회,변경, 취소요청 화면 - SAP RPC 연동 
 */
public class QM0101002A_ACT extends AbstractAction 
{
	private static final String ZWEB_QM_GET_BTR_REQUEST_LIST   = "com.helco.xf.qm01.ws.ZWEB_QM_GET_BTR_REQUEST_LIST";
	private static final String ZWEB_QM_GET_BTR_REQUESTED_LIST = "com.helco.xf.qm01.ws.ZWEB_QM_GET_BTR_REQUESTED_LIST";
	private static final String ZWEB_QM_GET_BTR_REQUEST_SAVE   = "com.helco.xf.qm01.ws.ZWEB_QM_GET_BTR_REQUEST_SAVE";
	
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 호기정보 처리
	    String rphogit = dsCond.getColumnAsString(0, "RPHOGIT");
		if (rphogit.length() == 9)
		{
			dsCond.setColumn(0, "RPHOGIT", rphogit+"-P");
		}
		
		// 입력 파라메터 처리 
		ZQMS023[] list = new ZQMS023[]{};
		Object obj[] = null;
		SapFunction stub = null;
		// 등록정보 조회 
		if ( DatasetUtility.getString(dsCond, "STATUS1", "").equals("X")) 
		{
			obj = new Object[]{
					CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "EINDTF"))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "EINDTT"))
					, DatasetUtility.getString(dsCond, "INVNRF")
					, DatasetUtility.getString(dsCond, "INVNRT")
					, DatasetUtility.getString(dsCond, "LIFNR")
					, DatasetUtility.getString(dsCond, "RPHOGIF")
					, DatasetUtility.getString(dsCond, "RPHOGIT")
					, list
			};
			// 호출 
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_GET_BTR_REQUEST_LIST, obj);
		} 
		else 
		{
			obj = new Object[]{
					CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "EINDTF"))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "EINDTT"))
					, DatasetUtility.getString(dsCond, "INVNRF")
					, DatasetUtility.getString(dsCond, "INVNRT")
					, DatasetUtility.getString(dsCond, "LIFNR")
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PACFDTF", ""))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PACFDTT"))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PAPRDTF"))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PAPRDTT"))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PARQDTF"))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PARQDTT"))
					, DatasetUtility.getString(dsCond, "RPHOGIF")
					, DatasetUtility.getString(dsCond, "RPHOGIT")
				    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS2"))
				    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS3"))
				    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS4"))
				    , CallSAP.getCheckValue(DatasetUtility.getString(dsCond, "STATUS5"))
				    , list
				};
			// 호출 
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),ZWEB_QM_GET_BTR_REQUESTED_LIST, obj);
		}
		
		// 출력 Dataset 생성 
		Dataset ds = ( CallSAP.isJCO() ) ? new Dataset() : ZQMS023.getDataset();
		ds.setId("ds_list");
		// 필요 컬럼 - 추가 
		CallSAP.makeDsCol(ds, "CHK", 1);
		CallSAP.makeDsCol(ds, "FLAG", 1);
		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());

		ctx.addOutputDataset(ds);	
	}
	
	/**
	 * 저장 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception 
	{
		Dataset dsList = ctx.getInputDataset("ds_list");
		Dataset dsError = null;
		SapFunction stub = null;
		
		if ( CallSAP.isJCO() ) 		
		{
			dsError = new Dataset("ds_error");
			stub = CallSAP.callSap(
					ZWEB_QM_GET_BTR_REQUEST_SAVE
					, new Object[]{dsList});

			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리 
			ZQMS026[] list = (ZQMS026[])
					CallSAP.moveDs2Obj(dsList, ZQMS026.class, "FLAG", new MyDs2ObjHelper());
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			// 저장 처리 
			stub = CallSAP.callSap(
						ctx.getInputVariable("G_MANDT")
						, ZWEB_QM_GET_BTR_REQUEST_SAVE
						, new Object[]{msgList, list});
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}
		ctx.addOutputDataset(dsError);
		if ( dsError.getRowCount() == 0 ) {
			// 조회 호출 
			try {
				query(ctx);
			} catch( CommRfcException e) {}	
		}
	}
	
	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			}
			
			if ( dsColName.equals("EXPORT") ) {
				return ((String) colValue).equals("X") ? "1" : "0";
			} else if ( dsColName.equals("INSGB")) {
				return ((String) colValue).equals("X") ? "1" : "0";
			}
			return colValue;
		}
	}
	
	class MyDs2ObjHelper implements Ds2ObjHelper {
		public void endMoveRow(Dataset ds, int row, Object obj) {
			// 일자 변경 시킴. 
			ZQMS026 tmpObj = ((ZQMS026)obj);
			tmpObj.setPARQDT(CallSAP.getFormatDate(tmpObj.getPARQDT()));	// 검사 요청일
			tmpObj.setEINDT(CallSAP.getFormatDate(tmpObj.getEINDT()));	// 납품
		}
	}
}
