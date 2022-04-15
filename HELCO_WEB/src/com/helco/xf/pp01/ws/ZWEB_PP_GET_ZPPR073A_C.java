package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR073A_C extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR073A_C() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS073A_C"
    			, ZPPS073A_C.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS073A_C"
				, "ZPPS073A_C"
				, "item"
				, ZPPS073A_C[].class };
    		return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR073A_C";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
		oper.addParameter(makeParam("I_GUBUN", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN2", PARAM_IN));
		oper.addParameter(makeParam("I_MATNR", PARAM_IN));
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		oper.addParameter(makeParam("I_PSPID", PARAM_IN));		
		oper.addParameter(makeParam("I_YYMM", PARAM_IN));
		
		
		
//		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
//		oper.addParameter(makeArrayParam("O_LIST", PARAM_INOUT, "TABLE_OF_ZPPS073AL", ZPPS073AL[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS073A_C", ZPPS073A_C[].class, "item"));
	}

}
