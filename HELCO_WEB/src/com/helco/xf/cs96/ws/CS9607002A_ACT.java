package com.helco.xf.cs96.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.cs96.ws.ZCSS004;
import com.helco.xf.cs96.ws.ZCSS004A;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class CS9607002A_ACT extends AbstractAction {

	public static final String ZWEB_CS_GET_LTEXT_READ
		= "com.helco.xf.cs96.ws.ZWEB_CS_GET_LTEXT_READ";
	public static final String ZWEB_CS_GET_LTEXT_SAVE
		= "com.helco.xf.cs96.ws.ZWEB_CS_GET_LTEXT_SAVE";

	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");

		// �Է� �Ķ���� ó�� 
		ZCSS004[] list = (ZCSS004[])
			CallSAP.moveDs2Obj(dsCond, ZCSS004.class, "FLAG");
		ZCSS004A[] listA = new ZCSS004A[]{};

		Object obj[] = new Object[]{
				list
				,listA
		};

		// ȣ�� 
		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_LTEXT_READ, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_LTEXT_READ, obj, false);		 // ����  SAP ����
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_LTEXT_READ, obj, false);  // EAI ����


		// ��� Dataset ����
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCSS004A.getDataset();
		Dataset ds2 =  new Dataset();
		ds.setId("ds_list");
		ds2.setId("ds_list2");
		CallSAP.makeDsCol(ds, "GBN", 1);
		CallSAP.makeDsCol(ds, "TEXT", 132);
		CallSAP.makeDsCol(ds2, "TXA", 255);
		CallSAP.makeDsCol(ds2, "TXB", 255);
		CallSAP.makeDsCol(ds2, "TXC", 255);
		CallSAP.makeDsCol(ds2, "TXD", 255);
		CallSAP.makeDsCol(ds2, "TXE", 255);
		CallSAP.makeDsCol(ds2, "TXF", 255);

		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds, stub.getOutput("I_TAB1"));

		if(ds2.getRowCount() <= 0) {
			ds2.appendRow();
		}

		for(int i=0; i<ds.getRowCount(); i++) {
			if(ds.getColumnAsString(i, "GBN").equals("A")) {
				ds2.setColumn(0, "TXA", StringOperator.replaceString(ds.getColumnAsString(i, "TEXT"), "<br>", ""));
			} else if(ds.getColumnAsString(i, "GBN").equals("B")) {
				ds2.setColumn(0, "TXB", StringOperator.replaceString(ds.getColumnAsString(i, "TEXT"), "<br>", ""));
			} else if(ds.getColumnAsString(i, "GBN").equals("C")) {
				ds2.setColumn(0, "TXC", StringOperator.replaceString(ds.getColumnAsString(i, "TEXT"), "<br>", ""));
			} else if(ds.getColumnAsString(i, "GBN").equals("D")) {
				ds2.setColumn(0, "TXD", StringOperator.replaceString(ds.getColumnAsString(i, "TEXT"), "<br>", ""));
			} else if(ds.getColumnAsString(i, "GBN").equals("E")) {
				ds2.setColumn(0, "TXE", StringOperator.replaceString(ds.getColumnAsString(i, "TEXT"), "<br>", ""));
			} else if(ds.getColumnAsString(i, "GBN").equals("F")) {
				ds2.setColumn(0, "TXF", StringOperator.replaceString(ds.getColumnAsString(i, "TEXT"), "<br>", ""));
			}
		}

		ctx.addOutputDataset(ds2);
	}

	/**
	 * ���� ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsDetail = ctx.getInputDataset("ds_detail");
		Dataset ds = new Dataset();
		Dataset ds2 = new Dataset();
		SapFunction stub = null;
		
		ds.setId("ds_list");
		CallSAP.makeDsCol(ds, "FLAG", 1);
		CallSAP.makeDsCol(ds, "GBN", 1);
		CallSAP.makeDsCol(ds, "TEXT", 255);
		
		ds2.setId("ds_list2");
		CallSAP.makeDsCol(ds2, "FLAG", 1);
		CallSAP.makeDsCol(ds2, "UPN", 6);
		CallSAP.makeDsCol(ds2, "SEQ", 2);
		CallSAP.makeDsCol(ds2, "DAT", 8);
		CallSAP.makeDsCol(ds2, "MUR", 8);
		
		ds.appendRow();
		ds.setColumn(0, "FLAG", "U");
		ds.setColumn(0, "GBN", "A");
		ds.setColumn(0, "TEXT", dsDetail.getColumnAsString(0, "TXA"));
		ds.appendRow();
		ds.setColumn(1, "FLAG", "U");
		ds.setColumn(1, "GBN", "B");
		ds.setColumn(1, "TEXT", dsDetail.getColumnAsString(0, "TXB"));
		ds.appendRow();
		ds.setColumn(2, "FLAG", "U");
		ds.setColumn(2, "GBN", "C");
		ds.setColumn(2, "TEXT", dsDetail.getColumnAsString(0, "TXC"));
		ds.appendRow();
		ds.setColumn(3, "FLAG", "U");
		ds.setColumn(3, "GBN", "D");
		ds.setColumn(3, "TEXT", dsDetail.getColumnAsString(0, "TXD"));
		ds.appendRow();
		ds.setColumn(4, "FLAG", "U");
		ds.setColumn(4, "GBN", "E");
		ds.setColumn(4, "TEXT", dsDetail.getColumnAsString(0, "TXE"));
		ds.appendRow();
		ds.setColumn(5, "FLAG", "U");
		ds.setColumn(5, "GBN", "F");
		ds.setColumn(5, "TEXT", dsDetail.getColumnAsString(0, "TXF"));
		
		ds2.appendRow();
		ds2.setColumn(0, "FLAG", "U");
		ds2.setColumn(0, "UPN", dsDetail.getColumnAsString(0, "UPN"));
		ds2.setColumn(0, "SEQ", dsDetail.getColumnAsString(0, "SEQ"));
		ds2.setColumn(0, "DAT", dsDetail.getColumnAsString(0, "DAT"));
		ds2.setColumn(0, "MUR", dsDetail.getColumnAsString(0, "MUR"));

		// �Է� �Ķ���� ó�� 
		ZCSS004[] list = (ZCSS004[])
			CallSAP.moveDs2Obj(ds2, ZCSS004.class, "FLAG");

		ZCSS004A[] listA = (ZCSS004A[])
			CallSAP.moveDs2Obj(ds, ZCSS004A.class, "FLAG");

		// ���� ó�� 
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_LTEXT_SAVE, new Object[]{list, listA});
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_LTEXT_SAVE, new Object[]{list, listA}, false);		 // ����  SAP ����
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_LTEXT_SAVE, new Object[]{list, listA}, false);  // EAI ����

/*		
		if(dsError.getRowCount() == 0) {
			// ��ȸ ȣ�� 
			query(ctx);
		}
*/
//		ctx.addOutputDataset(dsError);
	}
}