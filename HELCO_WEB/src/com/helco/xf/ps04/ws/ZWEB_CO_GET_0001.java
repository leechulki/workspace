package com.helco.xf.ps04.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_CO_GET_0001 extends WebServiceStub {

	public ZWEB_CO_GET_0001() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZTAXV001"
    			, ZTAXV001.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZTAXV001"
				, "ZTAXV001"
				, "item"
				, ZTAXV001[].class };
    	
    	obj[2] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_CO_GET_0001";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_BELNR", PARAM_IN));
		oper.addParameter(makeParam("I_BLDAT", PARAM_IN));
		oper.addParameter(makeParam("I_WRKLFN", PARAM_IN));

		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZTAXV001", ZTAXV001[].class, "item"));
	}
}
