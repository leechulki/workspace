package com.helco.xf.qm03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_GET_OUT_REQUEST_LIST extends WebServiceStub {

	public ZWEB_QM_GET_OUT_REQUEST_LIST() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZQMS021"
    			, ZQMS021.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS021"
				, "ZQMS021"
				, "item"
				, ZQMS021[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_OUT_REQUEST_LIST";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_HOGIF", PARAM_IN));
		oper.addParameter(makeParam("I_HOGIT", PARAM_IN));
		oper.addParameter(makeParam("I_LIFNR", PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS021", ZQMS021[].class, "item"));
	}

}
