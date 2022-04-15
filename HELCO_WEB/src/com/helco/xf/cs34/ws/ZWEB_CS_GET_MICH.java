package com.helco.xf.cs34.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_CS_GET_MICH extends WebServiceStub {

	public ZWEB_CS_GET_MICH() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZCSS010"
    			, ZCSS010.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCSS010"
				, "ZCSS010"
				, "item"
				, ZCSS010[].class };

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
		return "ZWEB_CS_GET_MICH";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("ENDDA", PARAM_IN ));
    	oper.addParameter(makeParam("I_AREA", PARAM_IN ));
    	oper.addParameter(makeParam("I_BILDAT", PARAM_IN ));
    	oper.addParameter(makeParam("I_LGORT", PARAM_IN ));
    	oper.addParameter(makeParam("I_PM", PARAM_IN ));
    	oper.addParameter(makeParam("I_PROJ", PARAM_IN ));
    	oper.addParameter(makeParam("I_SPART", PARAM_IN ));
    	oper.addParameter(makeParam("I_VKGRP", PARAM_IN ));    	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeParam("STADA", PARAM_IN ));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZCSS010", ZCSS010[].class, "item" ));
	}
}
