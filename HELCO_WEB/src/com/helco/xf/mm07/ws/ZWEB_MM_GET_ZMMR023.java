package com.helco.xf.mm07.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_MM_GET_ZMMR023 extends WebServiceStub {

	public ZWEB_MM_GET_ZMMR023() throws AxisFault {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		// TODO Auto-generated method stub
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZMMS023"
    			, ZMMS023.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMS023"
				, "ZMMS023"
				, "item"
				, ZMMS023[].class };
    	
    	obj[2] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZMMSEMSG"
				, "ZMMSEMSG"
				, "item"
				, ZQMSEMSG[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_MM_GET_ZMMR023";
	}

	@Override
	protected void initOperation(Object obj) {
		// TODO Auto-generated method stub
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_CODE", PARAM_IN));
		oper.addParameter(makeParam("I_EKGRP", PARAM_IN));
		oper.addParameter(makeParam("I_LIFNR", PARAM_IN));
		oper.addParameter(makeParam("I_MATNR", PARAM_IN));
		oper.addParameter(makeParam("I_PSPNR", PARAM_IN));
		oper.addParameter(makeParam("I_UDATE", PARAM_IN));
		oper.addParameter(makeParam("I_VDATE", PARAM_IN));
		oper.addParameter(makeParam("I_ZSHIP", PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS023", ZMMS023[].class, "item"));
	}

}
