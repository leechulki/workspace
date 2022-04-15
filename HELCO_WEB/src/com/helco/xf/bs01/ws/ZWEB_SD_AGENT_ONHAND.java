package com.helco.xf.bs01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_AGENT_ONHAND extends WebServiceStub {

	public ZWEB_SD_AGENT_ONHAND() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0023"
    			, ZSDS0023.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0023"
				, "ZSDS0023"
				, "item"
				, ZSDS0023[].class };

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
		return "ZWEB_SD_AGENT_ONHAND";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeParam("I_CBOX1", PARAM_IN));
		oper.addParameter(makeParam("I_CBOX2", PARAM_IN));
		oper.addParameter(makeParam("I_CBOX3", PARAM_IN));
		oper.addParameter(makeParam("I_CBOX4", PARAM_IN));
		oper.addParameter(makeParam("I_CBOX5", PARAM_IN));
		oper.addParameter(makeParam("I_CBOX6", PARAM_IN));
		oper.addParameter(makeParam("I_CBOX7", PARAM_IN));
    	oper.addParameter(makeParam("I_KUNNR", PARAM_IN));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN));
    	oper.addParameter(makeParam("I_VKBUR", PARAM_IN));
    	oper.addParameter(makeParam("I_ZZPJT_ID", PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0023", ZSDS0023[].class, "item"));
	}
}
