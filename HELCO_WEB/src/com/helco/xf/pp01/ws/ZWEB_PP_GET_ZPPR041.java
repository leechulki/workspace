package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR041 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR041() throws AxisFault {
		super();// TODO Auto-generated constructor stub
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS041"
    			, ZPPS041.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS041"
				, "ZPPS041"
				, "item"
				, ZPPS041[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR041";
	}

	@Override
	protected void initOperation(Object obj) {
		// TODO Auto-generated method stub
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_CHK", PARAM_IN ));
    	oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_GUBUN", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS041", ZPPS041[].class, "item" ));

	}

}
