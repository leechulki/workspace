package com.helco.xf.ps02.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PS_GET_0012 extends WebServiceStub {

	public ZWEB_PS_GET_0012() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPSS034"
    			, ZPSS034.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSS034"
				, "ZPSS034"
				, "item"
				, ZPSS034[].class };
    	
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
		return "ZWEB_PS_GET_0012";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_DATEF", PARAM_IN));
		oper.addParameter(makeParam("I_DATET", PARAM_IN));
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		oper.addParameter(makeParam("I_SMAN", PARAM_IN));
		oper.addParameter(makeParam("I_VKGRP", PARAM_IN));
		oper.addParameter(makeParam("I_ZZACTSS", PARAM_IN));
		oper.addParameter(makeParam("I_ZZPMNUM", PARAM_IN));
		
		
		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPSS034", ZPSS034[].class, "item"));
	}
}
