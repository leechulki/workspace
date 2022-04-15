package com.helco.xf.ps02.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_MM_BOXPROG extends WebServiceStub {

	public ZWEB_MM_BOXPROG() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZMMS016"
    			, ZMMS016.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMS016"
				, "ZMMS016"
				, "item"
				, ZMMS016[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_MM_BOXPROG";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
		oper.addParameter(makeParam("I_ATT", PARAM_IN ));
		oper.addParameter(makeParam("I_BOX", PARAM_IN ));
		oper.addParameter(makeParam("I_DAT1", PARAM_IN ));
    	oper.addParameter(makeParam("I_DAT2", PARAM_IN ));
    	oper.addParameter(makeParam("I_GUBN", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS016", ZMMS016[].class, "item" ));
	}

}
