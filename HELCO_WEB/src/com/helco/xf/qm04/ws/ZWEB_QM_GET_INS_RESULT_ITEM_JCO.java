package com.helco.xf.qm04.ws;

import java.util.List;
import com.helco.xf.comm.JCOMgr;

public class ZWEB_QM_GET_INS_RESULT_ITEM_JCO extends JCOMgr {

	public ZWEB_QM_GET_INS_RESULT_ITEM_JCO() {
		super();
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_INS_RESULT_ITEM";
	}

	@Override
	protected void initOperation(Object obj) {
		List list = (List)obj;
    	list.add("I_FENUM");
    	list.add("I_QMNUM");
	}
}
