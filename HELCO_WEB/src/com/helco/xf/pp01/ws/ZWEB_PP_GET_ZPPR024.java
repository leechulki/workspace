package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PP_GET_ZPPR024 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR024() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS024"
    			, ZPPS024.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS024"
				, "ZPPS024"
				, "item"
				, ZPPS024[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR024";
	}

	@Override
	protected void initOperation(Object obj) {
        OperationDesc oper = (OperationDesc) obj;
        
        oper.addParameter(makeArrayParam("GT_LIST", PARAM_INOUT, "TABLE_OF_ZPPS024", ZPPS024[].class, "item"));
        
        oper.addParameter(makeParam("I_P_GUBUN", PARAM_IN));
        oper.addParameter(makeParam("I_P_TYPE", PARAM_IN));
        oper.addParameter(makeParam("I_P_WERKS", PARAM_IN));
        oper.addParameter(makeParam("I_S_DATE_HIGH", PARAM_IN));
        oper.addParameter(makeParam("I_S_DATE_LOW", PARAM_IN));
        oper.addParameter(makeParam("I_S_NARA", PARAM_IN));
	}

}
