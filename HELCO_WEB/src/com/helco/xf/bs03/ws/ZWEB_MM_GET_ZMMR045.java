package com.helco.xf.bs03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_MM_GET_ZMMR045 extends WebServiceStub {

	public ZWEB_MM_GET_ZMMR045() throws AxisFault {
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
    			"ZMMS045"
    			, ZMMS045.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZMMS045"
				, "ZMMS045"
				, "item"
				, ZMMS045[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_MM_GET_ZMMR045";
	}

	@Override
	protected void initOperation(Object obj) {

		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_GBN", PARAM_IN ));
		oper.addParameter(makeParam("I_ITEM", PARAM_IN ));
    	oper.addParameter(makeParam("I_LOFO", PARAM_IN ));
    	oper.addParameter(makeParam("I_MNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_NAME1", PARAM_IN ));
    	oper.addParameter(makeParam("I_PJT", PARAM_IN ));
    	oper.addParameter(makeParam("I_RA", PARAM_IN ));
    	oper.addParameter(makeParam("I_SP2", PARAM_IN ));
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS045", ZMMS045[].class, "item" ));
	}

}
