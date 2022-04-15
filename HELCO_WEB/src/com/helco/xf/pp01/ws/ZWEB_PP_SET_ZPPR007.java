package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_SET_ZPPR007 extends WebServiceStub {

	public ZWEB_PP_SET_ZPPR007() throws AxisFault {
		super();
	}
 
	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_FLAG", PARAM_IN));
		oper.addParameter(makeParam("I_RBT", PARAM_IN));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS007A", ZPPS007A[].class, "item" ));
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS007A"
    			, ZPPS007A.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS007A"
				, "ZPPS007A"
				, "item"
				, ZPPS007A[].class };
    	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_SET_ZPPR007";
	}

}
