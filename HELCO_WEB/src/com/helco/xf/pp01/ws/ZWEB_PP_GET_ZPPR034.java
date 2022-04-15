package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PP_GET_ZPPR034 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR034() throws AxisFault {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		// TODO Auto-generated method stub
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPPS034"
    			, ZPPS034.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS034"
				, "ZPPS034"
				, "item"
				, ZPPS034[].class };
    	
    	obj[2] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZMMSEMSG"
				, "ZMMSEMSG"
				, "item"
				, ZQMSEMSG[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR034";
	}

	@Override
	protected void initOperation(Object obj) {
		// TODO Auto-generated method stub
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_ARBPL", PARAM_IN));
		oper.addParameter(makeParam("I_AUFNR", PARAM_IN));
		oper.addParameter(makeParam("I_DATE1", PARAM_IN));
		oper.addParameter(makeParam("I_DATE2", PARAM_IN));
		oper.addParameter(makeParam("I_ITEMNO", PARAM_IN));
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		oper.addParameter(makeParam("I_PSPID", PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS034", ZPPS034[].class, "item"));
	}

}
