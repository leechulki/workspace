package com.helco.xf.qm03.ws;

import java.util.List;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.JCOMgr;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_GET_OUT_REQUEST_LIST_JCO extends JCOMgr {

	public ZWEB_QM_GET_OUT_REQUEST_LIST_JCO() {
		super();
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_OUT_REQUEST_LIST";
	}

	@Override
	protected void initOperation(Object obj) {
		List list = (List) obj;
		list.add("I_HOGIF");
		list.add("I_HOGIT");
		list.add("I_LIFNR");
	}

}
