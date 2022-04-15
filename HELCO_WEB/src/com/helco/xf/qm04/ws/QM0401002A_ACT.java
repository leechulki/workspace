package com.helco.xf.qm04.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class QM0401002A_ACT extends AbstractAction {
	public static final String ZWEB_QM_GET_INS_RESULT_DETAIL
		= "com.helco.xf.qm04.ws.ZWEB_QM_GET_INS_RESULT_DETAIL";
	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// �Է� �Ķ���� ó�� 
		ZQMS024[] list = new ZQMS024[]{};
		
		 Object obj[] = new Object[]{
				 DatasetUtility.getString(dsCond, "POSID")
				, DatasetUtility.getString(dsCond, "QMNUM")
				, list
		};

		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_GET_INS_RESULT_DETAIL, obj);
		// ��� Dataset ���� 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZQMS024.getDataset();
		ds.setId("ds_list");
		// �ʿ� �÷� - �߰� 
		CallSAP.makeDsCol(ds, "CHK", 1);
		CallSAP.makeDsCol(ds, "FLAG", 1);
		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
		
		for(int i=0; i<ds.getRowCount(); i++)
		{
			ds.setColumn(
					i, 
					"LNTXT",
					RegExp.replaceAll(
							ds.getColumnAsString(i, "LNTXT"),
							"<(B|b)(R|r)(\\/)?>",
							"")
			);
		}

		ctx.addOutputDataset(ds);
	}
}