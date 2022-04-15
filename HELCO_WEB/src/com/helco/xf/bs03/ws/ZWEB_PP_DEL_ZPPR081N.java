package com.helco.xf.bs03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_DEL_ZPPR081N extends WebServiceStub {

	public ZWEB_PP_DEL_ZPPR081N() throws AxisFault {
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
    			"ZPPS081N"
    			, ZPPS081N.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPPS081N"
				, "ZPPS081N"
				, "item"
				, ZPPS081N[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_DEL_ZPPR081N";
	}

	@Override
	protected void initOperation(Object obj) {

		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS081N", ZPPS081N[].class, "item" ));

	}

}
