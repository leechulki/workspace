package com.helco.xf.bs03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR081A extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR081A() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZMMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMSEMSG"
				, "ZMMSEMSG" 
				, "item"
				, ZQMSEMSG[].class 
		};
    	obj[2] = new Object[]{
    			"ZPPS081A"
    			, ZPPS081A.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPPS081A"
				, "ZPPS081A"
				, "item"
				, ZPPS081A[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_GET_ZPPR081A";
	}

	@Override
	protected void initOperation(Object obj) {

		OperationDesc oper = (OperationDesc)obj;
	    oper.addParameter(makeParam("I_CREID", PARAM_IN ));
	    oper.addParameter(makeParam("I_FCRDT", PARAM_IN ));
	    oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
	    oper.addParameter(makeParam("I_GUBUN", PARAM_IN ));
	    oper.addParameter(makeParam("I_PSPID", PARAM_IN ));
	    oper.addParameter(makeParam("I_STAT1", PARAM_IN ));
	    oper.addParameter(makeParam("I_STAT2", PARAM_IN ));
	    oper.addParameter(makeParam("I_STAT3", PARAM_IN ));
	    oper.addParameter(makeParam("I_STAT4", PARAM_IN ));
	    oper.addParameter(makeParam("I_TCRDT", PARAM_IN ));
	    oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS081A", ZPPS081A[].class, "item" ));

	}

}
