package com.helco.xf.qm04.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.qm01.ws.ZQMS023;

public class ZWEB_QM_GET_TOG_RESULT_LIST extends WebServiceStub {

	public ZWEB_QM_GET_TOG_RESULT_LIST() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZQMS019R"
    			, ZQMS019R.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS019R"
				, "ZQMS019R"
				, "item"
				, ZQMS019R[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_TOG_RESULT_LIST";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
        oper.addParameter(makeParam("I_MANAGE", PARAM_IN ));
        oper.addParameter(makeParam("I_OTEIL", PARAM_IN ));
        oper.addParameter(makeParam("I_PARQDTF", PARAM_IN ));
        oper.addParameter(makeParam("I_PARQDTT", PARAM_IN ));
        oper.addParameter(makeParam("I_PSPID", PARAM_IN )); 
        oper.addParameter(makeParam("I_STATUS1", PARAM_IN ));
        oper.addParameter(makeParam("I_STATUS2", PARAM_IN ));
        oper.addParameter(makeParam("I_STATUS3", PARAM_IN ));
        oper.addParameter(makeParam("I_STATUS4", PARAM_IN ));
        oper.addParameter(makeParam("I_STATUS5", PARAM_IN ));
        oper.addParameter(makeParam("I_ZBOSUINF", PARAM_IN ));
        oper.addParameter(makeParam("I_ZBOSUINT", PARAM_IN ));
        oper.addParameter(makeParam("I_ZZBSU", PARAM_IN ));
    	
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS019R", ZQMS019R[].class, "item" ));
	}
}
