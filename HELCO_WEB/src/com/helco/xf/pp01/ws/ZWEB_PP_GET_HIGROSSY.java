package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.qm04.ws.ZQMS019;
import com.helco.xf.qm04.ws.ZQMS024;

public class ZWEB_PP_GET_HIGROSSY extends WebServiceStub {

	public ZWEB_PP_GET_HIGROSSY() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS009"
    			, ZPPS009.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS009"
				, "ZPPS009"
				, "item"
				, ZPPS009[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_HIGROSSY";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
    	oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_STATUS", PARAM_IN ));
    	oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS009", ZPPS009[].class, "item" ));
	}

}
