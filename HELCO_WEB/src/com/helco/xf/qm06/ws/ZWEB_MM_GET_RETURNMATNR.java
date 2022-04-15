package com.helco.xf.qm06.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_MM_GET_RETURNMATNR extends WebServiceStub {

	public ZWEB_MM_GET_RETURNMATNR() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZMMS014"
    			, ZMMS014.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMS014"
				, "ZMMS014"
				, "item"
				, ZMMS014[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_MM_GET_RETURNMATNR";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
    	oper.addParameter(makeParam("I_CGROUP", PARAM_IN ));
    	oper.addParameter(makeParam("I_CRDAT1", PARAM_IN ));
    	oper.addParameter(makeParam("I_CRDAT2", PARAM_IN ));
    	oper.addParameter(makeParam("I_NAMET", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS014", ZMMS014[].class, "item" ));
	}

}
