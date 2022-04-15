package com.helco.xf.bs01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_ESTIMATED_COST extends WebServiceStub {

	public ZWEB_SD_ESTIMATED_COST() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][4];
    	obj[0] = new Object[]{
    			"ZSDS0024"
    			, ZSDS0024.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0024"
				, "ZSDS0024"
				, "item"
				, ZSDS0024[].class 
		};
    	obj[2] = new Object[]{
    			"ZSDS0016"
    			, ZSDS0016.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0016"
				, "ZSDS0016"
				, "item"
				, ZSDS0016[].class 
		};
    	obj[4] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class 
		};

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_ESTIMATED_COST";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeArrayParam("E_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0024", ZSDS0024[].class, "item"));
		oper.addParameter(makeParam("I_ISR", PARAM_IN));
		oper.addParameter(makeParam("I_RDT", PARAM_IN));
		oper.addParameter(makeParam("I_SEQ", PARAM_IN));
		oper.addParameter(makeParam("I_SIR", PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0016", ZSDS0016[].class, "item"));
	}
}
