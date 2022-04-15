package com.helco.xf.mm01.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

public class MM0102003A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR005_S 
	= "com.helco.xf.mm01.ws.ZWEB_PP_GET_ZPPR005_S";
	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		// �Է� �Ķ���� ó�� 
		ZPPS012[] list = new ZPPS012[]{};
		Object obj[] = new Object[]{
				  list
				, DatasetUtility.getString(dsCond, "MATNR")
				, DatasetUtility.getString(dsCond, "HOGIN")
		};		

		// ȣ�� (����)
		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR005_S, obj);
       
		// ȣ��(����)
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR005_S, obj, false);		 // ����  SAP ����
		
		// ��� Dataset ���� 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS012.getDataset();
		ds.setId("ds_list");

		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
		ctx.addOutputDataset(ds);
	}

	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			} 

			if ( dsColName.equals("CRDAT")) {
				return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
			} 
			return colValue;
		}
	}
}
