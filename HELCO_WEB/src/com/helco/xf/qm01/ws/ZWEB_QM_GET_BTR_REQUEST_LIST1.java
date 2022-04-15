package com.helco.xf.qm01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_GET_BTR_REQUEST_LIST1 extends WebServiceStub {

	public ZWEB_QM_GET_BTR_REQUEST_LIST1() throws AxisFault {
		super();
	}
	
	@Override
	protected Object[][] getCustTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_GET_BTR_REQUEST_LIST";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_EINDTF", PARAM_IN ));
    	oper.addParameter(makeParam("I_EINDTT", PARAM_IN ));
    	oper.addParameter(makeParam("I_INVNRF", PARAM_IN ));
    	oper.addParameter(makeParam("I_INVNRT", PARAM_IN ));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_RPHOGIF", PARAM_IN ));
    	oper.addParameter(makeParam("I_RPHOGIT", PARAM_IN ));
    	
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS023", ZQMS023[].class, "item" ));
	}

}
