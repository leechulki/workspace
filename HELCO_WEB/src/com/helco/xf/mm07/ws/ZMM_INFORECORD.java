package com.helco.xf.mm07.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

//import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZMM_INFORECORD extends WebServiceStub {

	public ZMM_INFORECORD() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZMMS021"
    			, ZMMS021.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMS021"
				, "ZMMS021"
				, "item"
				, ZMMS021[].class };
    	
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
		return "ZMM_INFORECORD";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
		oper.addParameter(makeParam("I_DZEINR", PARAM_IN ));
		oper.addParameter(makeParam("I_GUBN", PARAM_IN ));
		oper.addParameter(makeParam("I_MATNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_MATNR_NM", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_SPEC1", PARAM_IN ));
    	oper.addParameter(makeParam("I_SPEC2", PARAM_IN ));
    	oper.addParameter(makeParam("I_SPEC3", PARAM_IN ));
    	oper.addParameter(makeParam("I_SPEC4", PARAM_IN ));
    	oper.addParameter(makeParam("I_SPEC5", PARAM_IN ));
    	oper.addParameter(makeParam("I_USNAM", PARAM_IN ));
    	
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS021", ZMMS021[].class, "item" ));
    	
	}

}
