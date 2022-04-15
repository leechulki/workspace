package com.helco.xf.qm04.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.qm04.ws.ZQMS027;
import com.helco.xf.qm04.ws.ZQMS027T;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class QM0401002C_ACT extends AbstractAction {

	public static final String ZWEB_QM_GET_INS_RESULT_ITEM
		= "com.helco.xf.qm04.ws.ZWEB_QM_GET_INS_RESULT_ITEM";
	public static final String ZWEB_QM_SET_INS_RESULT_ITEM
		= "com.helco.xf.qm04.ws.ZWEB_QM_SET_INS_RESULT_ITEM";
	
	public static boolean flag = false;
	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
			
		// �Է� �Ķ���� ó�� 
		ZQMS024[] list1 = new ZQMS024[]{};
		ZQMS025[] list2 = new ZQMS025[]{};
		
		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "FENUM")
				, DatasetUtility.getString(dsCond, "QMNUM")
				, list1
				, list2
		};
		// ȣ�� 
		SapFunction stub 
				= CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_GET_INS_RESULT_ITEM, obj);

		// ��� Dataset ���� 
		Dataset ds1 = CallSAP.isJCO() ? new Dataset() : ZQMS024.getDataset();
		ds1.setId("ds_list1");
		CallSAP.makeDsCol(ds1, "FLAG", 1);
		CallSAP.makeDsCol(ds1, "MATXT", 40);
		CallSAP.makeDsCol(ds1, "MANUM", 4);
		CallSAP.makeDsCol(ds1, "ERLNAM", 12);
		
		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds1, stub.getOutput("O_ITAB"));
		ctx.addOutputDataset(ds1);
		// ��� Dataset ���� 
		Dataset ds2 = CallSAP.isJCO() ? new Dataset() : ZQMS025.getDataset();
		ds2.setId("ds_list2");
		CallSAP.makeDsCol(ds2, "FLAG", 1);
		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds2, stub.getOutput("O_TTAB"));
		ds1.setColumn(0, "LNTXT", 
				StringOperator.replaceString(
						ds1.getColumnAsString(0, "LNTXT"), "<br>", ""));
		if ( ds2.getRowCount() <= 0) {
			flag = false;
			ds2.appendRow();
		} else {
			flag = true;
		}
		ds2.setColumn(0, "LNTXT", 
				StringOperator.replaceString(
						ds2.getColumnAsString(0, "LNTXT"), "<br>", ""));
		ctx.addOutputDataset(ds2);
	}
	
	/**
	 * ���� ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList1 = ctx.getInputDataset("ds_list1");
		Dataset dsList2 = ctx.getInputDataset("ds_list2");
		Dataset dsError = null;
		SapFunction stub = null;
		
		// �Է� �Ķ���� ó�� 
		if ( CallSAP.isJCO() ) {
			dsError = new Dataset("ds_error");
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT")
					, ZWEB_QM_SET_INS_RESULT_ITEM
					, new Object[]{dsList1, dsList2});

			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			ZQMS027[] list1 = (ZQMS027[])
					CallSAP.moveDs2Obj(dsList1, ZQMS027.class, "FLAG");
			ZQMS027T[] list2 = (ZQMS027T[])
					CallSAP.moveDs2Obj(dsList2, ZQMS027T.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// ���� ó�� 
			if(flag) {
				stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_SET_INS_RESULT_ITEM
						, new Object[]{list1, "C", list2, msgList});
			} else {
				stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_SET_INS_RESULT_ITEM
						, new Object[]{list1, "N", list2, msgList});			
			}
			
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}
		
		if ( dsError.getRowCount() == 0 ) {
			// ��ȸ ȣ�� 
			query(ctx);
		}
		ctx.addOutputDataset(dsError);
	}

	/**
	 * ������ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void release(BusinessContext ctx) throws Exception {
		Dataset dsList1 = ctx.getInputDataset("ds_list1");
		Dataset dsList2 = ctx.getInputDataset("ds_list2");
		Dataset dsError = null;
		SapFunction stub = null;
		// �Է� �Ķ���� ó�� 
		if ( CallSAP.isJCO() ) {
			dsError = new Dataset("ds_error");
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT")
					, ZWEB_QM_SET_INS_RESULT_ITEM
					, new Object[]{dsList1, dsList2});

			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			ZQMS027[] list1 = (ZQMS027[])
					CallSAP.moveDs2Obj(dsList1, ZQMS027.class, "FLAG");
			ZQMS027T[] list2 = (ZQMS027T[])
					CallSAP.moveDs2Obj(dsList2, ZQMS027T.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// ���� ó�� 
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_SET_INS_RESULT_ITEM
					, new Object[]{list1, "R", list2, msgList});
			
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}
		
		if ( dsError.getRowCount() == 0 ) {
			// ��ȸ ȣ�� 
			query(ctx);
		}
		ctx.addOutputDataset(dsError);
	}
}
