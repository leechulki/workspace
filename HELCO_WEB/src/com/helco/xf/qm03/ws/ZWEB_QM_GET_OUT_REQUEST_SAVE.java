package com.helco.xf.qm03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_QM_GET_OUT_REQUEST_SAVE extends WebServiceStub {

	public ZWEB_QM_GET_OUT_REQUEST_SAVE() throws AxisFault {
		super();
	}
 
	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS022", ZQMS022[].class, "item" ));
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZQMS022"
    			, ZQMS022.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS022"
				, "ZQMS022"
				, "item"
				, ZQMS022[].class };
	
    	obj[2] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMSEMSG"
				, "ZQMSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_OUT_REQUEST_SAVE";
	}

}
