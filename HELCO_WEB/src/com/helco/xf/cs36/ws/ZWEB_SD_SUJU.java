package com.helco.xf.cs36.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_SUJU extends WebServiceStub {

	public ZWEB_SD_SUJU() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0018"
    			, ZSDS0018.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0018"
				, "ZSDS0018"
				, "item"
				, ZSDS0018[].class };

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
		return "ZWEB_SD_SUJU";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_AUART", PARAM_IN));
    	oper.addParameter(makeParam("I_EMON", PARAM_IN));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN));
    	oper.addParameter(makeParam("I_PROJ", PARAM_IN));
    	oper.addParameter(makeParam("I_REGION", PARAM_IN));
    	oper.addParameter(makeParam("I_SMON", PARAM_IN));
    	oper.addParameter(makeParam("I_SPART", PARAM_IN ));
    	oper.addParameter(makeParam("I_VKGRP", PARAM_IN));
    	oper.addParameter(makeParam("I_ZMAN", PARAM_IN));    	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0018", ZSDS0018[].class, "item"));
	}
}
