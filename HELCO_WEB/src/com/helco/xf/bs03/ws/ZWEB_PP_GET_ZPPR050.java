package com.helco.xf.bs03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.pp01.ws.ZPPS103;

public class ZWEB_PP_GET_ZPPR050 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR050() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS050"
    			, ZPPS050.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS050"
				, "ZPPS050"
				, "item"
				, ZPPS050[].class };
	
    	return obj;
	} 

	@Override
	protected String getOperName() {
		return "ZWEB_PP_GET_ZPPR050";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
    	oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_GUBUN", PARAM_IN ));
    	oper.addParameter(makeParam("I_GUBUN1", PARAM_IN ));
    	oper.addParameter(makeParam("I_P1", PARAM_IN ));
    	oper.addParameter(makeParam("I_P3", PARAM_IN ));
    	oper.addParameter(makeParam("I_P4", PARAM_IN ));
    	oper.addParameter(makeParam("I_P5", PARAM_IN ));
    	oper.addParameter(makeParam("I_PSPID", PARAM_IN ));
    	oper.addParameter(makeParam("I_RA", PARAM_IN ));
    	oper.addParameter(makeParam("I_REGIO", PARAM_IN ));
    	oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_TNAME", PARAM_IN ));
    	oper.addParameter(makeParam("I_WERKS", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS050", ZPPS050[].class, "item" ));

	}

}
