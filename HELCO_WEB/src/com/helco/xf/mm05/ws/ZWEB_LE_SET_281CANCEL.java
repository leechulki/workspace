package com.helco.xf.mm05.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_LE_SET_281CANCEL extends WebServiceStub {

	public ZWEB_LE_SET_281CANCEL() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZLES008"
    			, ZLES008.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZLES008"
				, "ZLES008" 
				, "item"
				, ZLES007[].class };
    	obj[2] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMSEMSG"
				, "ZLES008" 
				, "item"
				, ZQMSEMSG[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_LE_SET_281CANCEL";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
   	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZLES008", ZLES008[].class, "item" ));
	}
}
