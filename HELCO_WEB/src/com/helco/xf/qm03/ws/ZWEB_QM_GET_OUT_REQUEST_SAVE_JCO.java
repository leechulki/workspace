package com.helco.xf.qm03.ws;

import java.util.List;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.JCOMgr;
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.data.Dataset;

public class ZWEB_QM_GET_OUT_REQUEST_SAVE_JCO extends JCOMgr {

	public ZWEB_QM_GET_OUT_REQUEST_SAVE_JCO(){
		super();
	}
 
	@Override
	protected void initOperation(Object obj) {
		List list = (List) obj;
    	list.add("O_TAB");
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_OUT_REQUEST_SAVE";
	}
	public void addTableParameter(JCO.Function f, Dataset ds) throws Exception {
		JCO.Table t = f.getTableParameterList().getTable("O_TAB");
		CallSAP.moveDs2ObjForJCO(ds, t, "TMODE", null);
	}
}
