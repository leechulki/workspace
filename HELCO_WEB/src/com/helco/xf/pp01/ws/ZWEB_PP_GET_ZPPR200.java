package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR200 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR200() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS200"
    			, ZPPS200.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS200"
				, "ZPPS200"
				, "item"
				, ZPPS200[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR200";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
    	oper.addParameter(makeParam("I_AUFNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_FDATE2", PARAM_IN ));
    	oper.addParameter(makeParam("I_GUBUN", PARAM_IN ));
    	oper.addParameter(makeParam("I_GUBUN2", PARAM_IN ));    	
    	oper.addParameter(makeParam("I_JO", PARAM_IN ));
    	oper.addParameter(makeParam("I_NOK", PARAM_IN ));    	
    	oper.addParameter(makeParam("I_NOWORK", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_PSPID", PARAM_IN ));
    	oper.addParameter(makeParam("I_SHIP", PARAM_IN ));
    	oper.addParameter(makeParam("I_STATUS", PARAM_IN ));    	
    	oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_TDATE2", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS200", ZPPS200[].class, "item" ));
	}

}
