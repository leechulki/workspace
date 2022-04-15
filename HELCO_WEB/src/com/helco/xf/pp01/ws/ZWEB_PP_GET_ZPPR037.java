package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PP_GET_ZPPR037 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR037() throws AxisFault {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		// TODO Auto-generated method stub
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPPS037"
    			, ZPPS037.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS037"
				, "ZPPS037"
				, "item"
				, ZPPS037[].class };
    	
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
		return "ZWEB_PP_GET_ZPPR037";
	}

	@Override
	protected void initOperation(Object obj) {
		// TODO Auto-generated method stub
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_DATA1", PARAM_IN));
		oper.addParameter(makeParam("I_DATA2", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN2", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN3", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN4", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN5", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN6", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN7", PARAM_IN));
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS037", ZPPS037[].class, "item"));
	}

}
