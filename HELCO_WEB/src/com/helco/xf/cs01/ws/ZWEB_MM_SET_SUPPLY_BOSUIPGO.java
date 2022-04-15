package com.helco.xf.cs01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_MM_SET_SUPPLY_BOSUIPGO extends WebServiceStub {

	public ZWEB_MM_SET_SUPPLY_BOSUIPGO() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZMMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMSEMSG"
				, "ZMMSEMSG" 
				, "item"
				, ZQMSEMSG[].class };
    	obj[2] = new Object[]{
    			"ZMMS006"
    			, ZMMS006.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_MMS006"
				, "ZMMS006" 
				, "item"
				, ZMMS006[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_MM_SET_SUPPLY_BOSUIPGO";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_LGORT", PARAM_IN ));
    	oper.addParameter(makeParam("I_MATNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_MENGE", PARAM_IN ));
    	oper.addParameter(makeParam("I_REQITEM", PARAM_IN ));
    	oper.addParameter(makeParam("I_REQNR", PARAM_IN ));
    	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS006", ZMMS006[].class, "item" ));

	}
	protected boolean alwaysUseJaxRpcCall() {
		return true;
		}
}
