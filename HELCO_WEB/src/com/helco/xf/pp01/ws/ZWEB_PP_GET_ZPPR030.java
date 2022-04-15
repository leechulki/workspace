package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PP_GET_ZPPR030 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR030() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS030"
    			, ZPPS030.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS030"
				, "ZPPS030"
				, "item"
				, ZPPS030[].class };
    	
//    	obj[2] = new Object[]{
//    			"ZPSSEMSG"
//    			, ZQMSEMSG.class
//    	};
//    	obj[3] = new Object[]{
//				"TABLE_OF_ZPSSEMSG"
//				, "ZPSSEMSG"
//				, "item"
//				, ZQMSEMSG[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_PP_GET_ZPPR030";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_CHECK", PARAM_IN));
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		
//		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS030", ZPPS030[].class, "item"));
	}
}
