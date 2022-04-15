package com.helco.xf.mm01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.pp01.ws.ZPPS024;

public class ZWEB_PP_GET_ZPPR005_S extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR005_S() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS012"
    			, ZPPS012.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS012"
				, "ZPPS012"
				, "item"
				, ZPPS012[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_GET_ZPPR005_S";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		
        oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS012", ZPPS012[].class, "item"));
        
        oper.addParameter(makeParam("S_ITEMNO", PARAM_IN));
        oper.addParameter(makeParam("S_POSID", PARAM_IN));
	}

}
