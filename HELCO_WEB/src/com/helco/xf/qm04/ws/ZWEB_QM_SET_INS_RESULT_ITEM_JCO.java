package com.helco.xf.qm04.ws;

import java.util.List;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.JCOMgr;
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.data.Dataset;

public class ZWEB_QM_SET_INS_RESULT_ITEM_JCO extends JCOMgr {
	@Override
	protected void initOperation(Object obj) {
		List list = (List)obj;
		list.add("I_TMODE");
		list.add("I_LTAB");
    	list.add("I_TTAB");
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_SET_INS_RESULT_ITEM";
	}
	public void addTableParameter(JCO.Function f, Dataset ds) throws Exception {
		String tName = "I_LTAB";
		if( ds.getId().equals("ds_list2")) {
			tName = "I_TTAB";
		}
		
		JCO.Table t = f.getTableParameterList().getTable(tName);
		CallSAP.moveDs2ObjForJCO(ds, t, "FLAG", null);
	}
}
