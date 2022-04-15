package com.helco.xf.wb01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZQM_READ_TEXT extends WebServiceStub {

	public ZQM_READ_TEXT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];

    	obj[0] = new Object[]{
    			"ZQMS028"
    			, ZQMS028.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS028"
				, "ZQMS028"
				, "item"
				, ZQMS028[].class };
    	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZQM_READ_TEXT";
	}

	@Override
	protected void initOperation(Object oper1) {
		OperationDesc oper = (OperationDesc) oper1;
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS028", ZQMS028[].class, "item" ));
		oper.addParameter( makeParam("TDID", PARAM_IN));
		oper.addParameter( makeParam("TDNAME", PARAM_IN));
		oper.addParameter( makeParam("TDOBJECT", PARAM_IN));
	}

}
