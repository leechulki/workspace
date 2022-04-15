package com.helco.xf.qm06.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_MM_GET_JQPRRMATNR extends WebServiceStub {

	public ZWEB_MM_GET_JQPRRMATNR() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZMMS013"
    			, ZMMS013.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMS013"
				, "ZMMS013"
				, "item"
				, ZMMS013[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_MM_GET_JQPRRMATNR";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
    	oper.addParameter(makeParam("I_APPDT1", PARAM_IN ));
    	oper.addParameter(makeParam("I_APPDT2", PARAM_IN ));
    	oper.addParameter(makeParam("I_CREDT1", PARAM_IN ));
    	oper.addParameter(makeParam("I_CREDT2", PARAM_IN ));
    	oper.addParameter(makeParam("I_CRENM", PARAM_IN ));
    	oper.addParameter(makeParam("I_JQPRNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_JQPRNUM", PARAM_IN ));
    	oper.addParameter(makeParam("I_NAMET", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_SDATE1", PARAM_IN ));
    	oper.addParameter(makeParam("I_SDATE2", PARAM_IN ));    	
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS013", ZMMS013[].class, "item" ));
	}

}
