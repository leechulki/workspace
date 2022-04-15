package com.helco.xf.qm01.ws;

import java.util.List;

import com.helco.xf.comm.JCOMgr;

public class ZWEB_QM_GET_BTR_REQUEST_LIST_JCO extends JCOMgr {

	public ZWEB_QM_GET_BTR_REQUEST_LIST_JCO() {
		super();
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_BTR_REQUEST_LIST";
	}

	@Override
	protected void initOperation(Object oper) {
		List list = (List) oper;
		list.add("I_EINDTF");
		list.add("I_EINDTT");
		list.add("I_INVNRF");
		list.add("I_INVNRT");
		list.add("I_LIFNR");
		list.add("I_RPHOGIF");
		list.add("I_RPHOGIT");
	}
}
