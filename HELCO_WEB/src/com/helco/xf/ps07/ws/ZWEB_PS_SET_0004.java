package com.helco.xf.ps07.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PS_SET_0004 extends WebServiceStub {

	public ZWEB_PS_SET_0004() throws AxisFault {
		super();
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		//oper.addParameter(makeParam("PSPID", PARAM_IN));
		//oper.addParameter(makeParam("POSID", PARAM_IN));
		//oper.addParameter(makeParam("ZZJUNGD", PARAM_IN));
		//oper.addParameter(makeParam("MODE1", PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPSS018", ZPSS018[].class, "item"));
	}
	
	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPSS018"
    			, ZPSS018.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSS018"
				, "ZPSS018"
				, "item"
				, ZPSS018[].class };
    	
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
		return "ZWEB_PS_SET_0004";
	}

	
}
