package com.helco.xf.wb01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZQM_SAVE_TEXT extends WebServiceStub {

	public ZQM_SAVE_TEXT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];

    	obj[0] = new Object[]{
    			"ZQMS006"
    			, ZQMS006.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS006"
				, "ZQMS006"
				, "item"
				, ZQMS006[].class };
    	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZQM_SAVE_TEXT";
	}

	@Override
	protected void initOperation(Object oper1) {
		OperationDesc oper = (OperationDesc) oper1;
		oper.addParameter(makeArrayParam("I_TEXT", PARAM_INOUT, "TABLE_OF_ZQMS006", ZQMS006[].class, "item" ));
		
		oper.addParameter( makeParam("TDID", PARAM_IN));
		oper.addParameter( makeParam("TDNAME", PARAM_IN));
		oper.addParameter( makeParam("TDOBJECT", PARAM_IN));
	}

}
