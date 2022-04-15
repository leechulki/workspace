package com.helco.xf.qm04.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_GET_INS_RESULT_ITEM extends WebServiceStub {

	public ZWEB_QM_GET_INS_RESULT_ITEM() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZQMS024"
    			, ZQMS024.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS024"
				, "ZQMS024"
				, "item"
				, ZQMS024[].class };
    	obj[2] = new Object[]{
    			"ZQMS025"
    			, ZQMS025.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMS025"
				, "ZQMS025"
				, "item"
				, ZQMS025[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_INS_RESULT_ITEM";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_FENUM", PARAM_IN ));
    	oper.addParameter(makeParam("I_QMNUM", PARAM_IN ));
    	
    	oper.addParameter(makeArrayParam("O_ITAB", PARAM_INOUT, "TABLE_OF_ZQMS024", ZQMS024[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TTAB", PARAM_INOUT, "TABLE_OF_ZQMS025", ZQMS025[].class, "item" ));
	}
}
