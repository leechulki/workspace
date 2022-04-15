package com.helco.xf.qm04.ws;

import java.util.List;
import com.helco.xf.comm.JCOMgr;

public class ZWEB_QM_GET_INS_RESULT_LIST_JCO extends JCOMgr {
	
	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_INS_RESULT_LIST";
	}

	@Override
	protected void initOperation(Object obj) {
		List list = (List)obj;
    	list.add("I_LIFNR");
    	list.add("I_MANAGE");
    	list.add("I_PAPRDTF");
    	list.add("I_PAPRDTT");
    	list.add("I_QMDATF");
    	list.add("I_QMDATT");
    	list.add("I_STATUS1");
    	list.add("I_STATUS2");
    	list.add("I_STATUS3");
    	list.add("I_STATUS4");
    	list.add("I_STATUS5");
    	list.add("I_TEMNO");
	}
}
