package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PP_DLT_ZPPR103 extends WebServiceStub {

	public ZWEB_PP_DLT_ZPPR103() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS103"
    			, ZPPS103.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS103"
				, "ZPPS103"
				, "item"
				, ZPPS103[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_DLT_ZPPR103";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
    	oper.addParameter(makeParam("I_RBT", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS103", ZPPS103[].class, "item" ));

	}

}
