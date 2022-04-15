package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR072 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR072() throws AxisFault {
		super();// TODO Auto-generated constructor stub
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS072"
    			, ZPPS072.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS072"
				, "ZPPS072"
				, "item"
				, ZPPS072[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR072";
	}

	@Override
	protected void initOperation(Object obj) {
		// TODO Auto-generated method stub
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_ASPDF", PARAM_IN ));
    	oper.addParameter(makeParam("I_ASPDT", PARAM_IN ));
    	oper.addParameter(makeParam("I_ATYPE", PARAM_IN ));
    	oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_ITEMNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_RAD1", PARAM_IN ));
    	oper.addParameter(makeParam("I_RAD2", PARAM_IN ));
    	oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_WERKS", PARAM_IN ));
    	oper.addParameter(makeParam("I_WOGBN", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS072", ZPPS072[].class, "item" ));

	}

}
