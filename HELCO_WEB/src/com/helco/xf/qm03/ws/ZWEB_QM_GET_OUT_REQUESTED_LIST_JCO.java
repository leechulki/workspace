package com.helco.xf.qm03.ws;

import java.util.List;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.JCOMgr;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_GET_OUT_REQUESTED_LIST_JCO extends JCOMgr {

	public ZWEB_QM_GET_OUT_REQUESTED_LIST_JCO() {
		super();
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_OUT_REQUESTED_LIST";
	}
 
	@Override
	protected void initOperation(Object obj) {
		List list = (List) obj;
        list.add("I_HOGIF");
		list.add("I_HOGIT");
		list.add("I_LIFNR");
		list.add("I_PACFDTF");
		list.add("I_PACFDTT");
		list.add("I_PAPRDTF");
		list.add("I_PAPRDTT");
		list.add("I_PARQDTF");
		list.add("I_PARQDTT");
		list.add("I_QMCHECK");
		list.add("I_STATUS2");
		list.add("I_STATUS3");
		list.add("I_STATUS4");
		list.add("I_STATUS5");
	}

}
