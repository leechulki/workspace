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
 * �������»翡�� ���� �԰� ���� ����˻��û ���� ��ȸ,����, ��ҿ�û ȭ�� - SAP RPC ���� 
 */
public class QM0101002A_ACT extends AbstractAction 
{
	private static final String ZWEB_QM_GET_BTR_REQUEST_LIST   = "com.helco.xf.qm01.ws.ZWEB_QM_GET_BTR_REQUEST_LIST";
	private static final String ZWEB_QM_GET_BTR_REQUESTED_LIST = "com.helco.xf.qm01.ws.ZWEB_QM_GET_BTR_REQUESTED_LIST";
	private static final String ZWEB_QM_GET_BTR_REQUEST_SAVE   = "com.helco.xf.qm01.ws.ZWEB_QM_GET_BTR_REQUEST_SAVE";
	
	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// ȣ������ ó��
	    String rphogit = dsCond.getColumnAsString(0, "RPHOGIT");
		if (rphogit.length() == 9)
		{
			dsCond.setColumn(0, "RPHOGIT", rphogit+"-P");
		}
		
		// �Է� �Ķ���� ó�� 
		ZQMS023[] list = new ZQMS023[]{};
		Object obj[] = null;
		SapFunction stub = null;
		// ������� ��ȸ 
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
			// ȣ�� 
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
			// ȣ�� 
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),ZWEB_QM_GET_BTR_REQUESTED_LIST, obj);
		}
		
		// ��� Dataset ���� 
		Dataset ds = ( CallSAP.isJCO() ) ? new Dataset() : ZQMS023.getDataset();
		ds.setId("ds_list");
		// �ʿ� �÷� - �߰� 
		CallSAP.makeDsCol(ds, "CHK", 1);
		CallSAP.makeDsCol(ds, "FLAG", 1);
		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());

		ctx.addOutputDataset(ds);	
	}
	
	/**
	 * ���� ó�� 
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
			// �Է� �Ķ���� ó�� 
			ZQMS026[] list = (ZQMS026[])
					CallSAP.moveDs2Obj(dsList, ZQMS026.class, "FLAG", new MyDs2ObjHelper());
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			// ���� ó�� 
			stub = CallSAP.callSap(
						ctx.getInputVariable("G_MANDT")
						, ZWEB_QM_GET_BTR_REQUEST_SAVE
						, new Object[]{msgList, list});
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}
		ctx.addOutputDataset(dsError);
		if ( dsError.getRowCount() == 0 ) {
			// ��ȸ ȣ�� 
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
			// ���� ���� ��Ŵ. 
			ZQMS026 tmpObj = ((ZQMS026)obj);
			tmpObj.setPARQDT(CallSAP.getFormatDate(tmpObj.getPARQDT()));	// �˻� ��û��
			tmpObj.setEINDT(CallSAP.getFormatDate(tmpObj.getEINDT()));	// ��ǰ
		}
	}
}
