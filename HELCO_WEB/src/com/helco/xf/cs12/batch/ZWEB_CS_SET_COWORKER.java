package com.helco.xf.cs12.batch;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_CS_SET_COWORKER extends WebServiceStub {

	public ZWEB_CS_SET_COWORKER() throws AxisFault {
		super();
	}
 
	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		
		oper.addParameter(makeParam("I_LIFNR", PARAM_IN));
		oper.addParameter(makeParam("I_VBELN", PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item" ));
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_CS_SET_COWORKER";
	}

}
