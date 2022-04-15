package com.helco.xf.cs42.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_CS_GET_PRINT extends WebServiceStub {

	public ZWEB_CS_GET_PRINT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][2];
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
		return "ZWEB_CS_GET_PRINT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_CCGBN", PARAM_IN ));
    	oper.addParameter(makeParam("I_DAT1", PARAM_IN ));
    	oper.addParameter(makeParam("I_DAT2", PARAM_IN ));
    	oper.addParameter(makeParam("I_KIDNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_PRIFORM", PARAM_IN ));
    	oper.addParameter(makeParam("I_PRTNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_TAXNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_VKBUR", PARAM_IN ));
    	oper.addParameter(makeParam("I_VKGRP", PARAM_IN ));  
    	oper.addParameter(makeParam("I_ZDEL", PARAM_IN ));  	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item" ));
	}
}
