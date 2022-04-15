package com.helco.xf.ps01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZPP_GET_GATE_ALL extends WebServiceStub {

	public ZPP_GET_GATE_ALL() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][2];
    	
    	obj[0] = new Object[]{
    			"ZPPSGATESS"
    			, ZPPSGATESS.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPSGATESS"
				, "ZPPSGATESS"
				, "item"
				, ZPPSGATESS[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZPP_GET_GATE_ALL";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;

		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPSGATESS", ZPPSGATESS[].class, "item"));
		
		
	}
}
