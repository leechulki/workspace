package com.helco.xf.ps02.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PS_SET_0001 extends WebServiceStub {

	public ZWEB_PS_SET_0001() throws AxisFault {
		super();
	}
 
	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_MODE", PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPSS004", ZPSS004[].class, "item" ));
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPSS004"
    			, ZPSS004.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSS004"
				, "ZPSS004"
				, "item"
				, ZPSS004[].class };
	
    	obj[2] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMSEMSG"
				, "ZQMSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PS_SET_0001";
	}

}
