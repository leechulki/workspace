package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PP_GET_ZPPR024P extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR024P() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS024P"
    			, ZPPS024P.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS024P"
				, "ZPPS024P"
				, "item"
				, ZPPS024P[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR024P";
	}

	@Override
	protected void initOperation(Object obj) {
        OperationDesc oper = (OperationDesc) obj;
                
        oper.addParameter(makeParam("I_FDATE", PARAM_IN));
        oper.addParameter(makeParam("I_GUBUN", PARAM_IN));
        oper.addParameter(makeParam("I_TDATE", PARAM_IN));
        oper.addParameter(makeParam("I_TYPE", PARAM_IN));
        oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS024P", ZPPS024P[].class, "item"));
        
        
	}

}
