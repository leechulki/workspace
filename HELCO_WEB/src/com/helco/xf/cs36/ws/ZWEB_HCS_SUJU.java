package com.helco.xf.cs36.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_HCS_SUJU extends WebServiceStub {

	public ZWEB_HCS_SUJU() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZCSS013"
    			, ZCSS013.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCSS013"
				, "ZCSS013"
				, "item"
				, ZCSS013[].class };

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
		return "ZWEB_HCS_SUJU";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_AUART", PARAM_IN));
    	oper.addParameter(makeParam("I_EMON", PARAM_IN));
    	oper.addParameter(makeParam("I_INCGBN", PARAM_IN));
    	oper.addParameter(makeParam("I_LAND1", PARAM_IN));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN));
    	oper.addParameter(makeParam("I_SMON", PARAM_IN));
    	oper.addParameter(makeParam("I_SPART", PARAM_IN ));
    	oper.addParameter(makeParam("I_VKGRP", PARAM_IN));	   	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZCSS013", ZCSS013[].class, "item"));
	}
}
