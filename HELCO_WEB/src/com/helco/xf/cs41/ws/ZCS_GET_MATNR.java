package com.helco.xf.cs41.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZCS_GET_MATNR extends WebServiceStub {

	public ZCS_GET_MATNR() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZCSS012"
    			, ZCSS012.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCSS012"
				, "ZCSS012"
				, "item"
				, ZCSS012[].class };

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
		return "ZCS_GET_MATNR";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_PSPID", PARAM_IN ));   	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TAB" , PARAM_INOUT, "TABLE_OF_ZCSS012" , ZCSS012[].class, "item" ));
	}
}
