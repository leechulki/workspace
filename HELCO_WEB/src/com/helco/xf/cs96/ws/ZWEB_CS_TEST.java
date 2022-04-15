package com.helco.xf.cs96.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_CS_TEST extends WebServiceStub {

	public ZWEB_CS_TEST() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZCSS_TEST"
    			, ZCSS_TEST.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCSS_TEST"
				, "ZCSS_TEST"
				, "item"
				, ZCSS_TEST[].class };

    	obj[2] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZQMSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_CS_TEST";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_HST", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZCSS_TEST2", ZCSS_TEST[].class, "item" ));
	}
}
