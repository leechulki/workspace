package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR091 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR091() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS091"
    			, ZPPS091.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS091"
				, "ZPPS091"
				, "item"
				, ZPPS091[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR091";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
    	oper.addParameter(makeParam("I_DATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_DATE2", PARAM_IN ));
    	oper.addParameter(makeParam("I_GBN", PARAM_IN ));
    	oper.addParameter(makeParam("I_ITEMNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS091", ZPPS091[].class, "item" ));
	}

}
