package com.helco.xf.ps02.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZPP_G_GET_SPEC_LAYO extends WebServiceStub {

	public ZPP_G_GET_SPEC_LAYO() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][2];
    	
    	obj[0] = new Object[]{
    			"ZPPTSLAYO"
    			, ZPPTSLAYO.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPTSLAYO"
				, "ZPPTSLAYO"
				, "item"
				, ZPPTSLAYO[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZPP_G_GET_SPEC_LAYO";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		oper.addParameter(makeParam("I_BLOCK", PARAM_IN));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPTSLAYO", ZPPTSLAYO[].class, "item"));
		
		
	}
}
