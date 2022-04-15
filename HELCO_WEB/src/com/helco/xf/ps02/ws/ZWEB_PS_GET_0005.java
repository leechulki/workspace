package com.helco.xf.ps02.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;


import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PS_GET_0005 extends WebServiceStub {

	public ZWEB_PS_GET_0005() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPSS009"
    			, ZPSS009.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSS009"
				, "ZPSS009"
				, "item"
				, ZPSS009[].class };
    	
    	obj[2] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_PS_GET_0005";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_PSPID", PARAM_IN));
		oper.addParameter(makeParam("I_ZZACTSS", PARAM_IN));
		oper.addParameter(makeParam("I_ZZLIFNR", PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPSS009", ZPSS009[].class, "item"));
	}
}
