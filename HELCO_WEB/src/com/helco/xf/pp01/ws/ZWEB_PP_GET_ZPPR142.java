package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR142 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR142() throws AxisFault {
		super();		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS142"
    			, ZPPS142.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS142"
				, "ZPPS142"
				, "item"
				, ZPPS142[].class };
	
    	return obj;
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
    	oper.addParameter(makeParam("I_AGUBUN", PARAM_IN ));
    	oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_GUBUN", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_PSPID", PARAM_IN ));
    	oper.addParameter(makeParam("I_RBT", PARAM_IN ));
    	oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS142", ZPPS142[].class, "item" ));

	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_GET_ZPPR142";
	}

}
