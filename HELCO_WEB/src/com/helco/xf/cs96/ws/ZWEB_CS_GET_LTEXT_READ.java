package com.helco.xf.cs96.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.qm04.ws.ZQMS024;
import com.helco.xf.qm04.ws.ZQMS025;

public class ZWEB_CS_GET_LTEXT_READ extends WebServiceStub {

	public ZWEB_CS_GET_LTEXT_READ() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][2];

    	obj[0] = new Object[]{
    			"ZCSS004"
    			, ZCSS004.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCSS004"
				, "ZCSS004"
				, "item"
				, ZCSS004[].class };
    	obj[2] = new Object[]{
    			"ZCSS004A"
    			, ZCSS004A.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZCSS004A"
				, "ZCSS004A"
				, "item"
				, ZCSS004A[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_CS_GET_LTEXT_READ";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeArrayParam("I_TAB", PARAM_INOUT, "TABLE_OF_ZCSS004", ZCSS004[].class, "item"));
		oper.addParameter(makeArrayParam("I_TAB1", PARAM_INOUT, "TABLE_OF_ZCSS004A", ZCSS004A[].class, "item"));
	}
}
