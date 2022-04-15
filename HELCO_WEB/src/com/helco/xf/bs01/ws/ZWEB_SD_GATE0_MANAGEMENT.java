package com.helco.xf.bs01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_SD_GATE0_MANAGEMENT extends WebServiceStub{
	public ZWEB_SD_GATE0_MANAGEMENT() throws AxisFault {
		super();
	}
	
	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[1][1];
    	obj[0] = new Object[]{
    			"ZSDS0091"
    			, ZSDS0091.class
    	};

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_GATE0_MANAGEMENT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeArrayParam("TT_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0091", ZSDS0091[].class, "item"));
	}
}
