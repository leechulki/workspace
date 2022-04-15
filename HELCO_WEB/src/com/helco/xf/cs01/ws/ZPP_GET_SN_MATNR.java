package com.helco.xf.cs01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZPP_GET_SN_MATNR extends WebServiceStub {

	public ZPP_GET_SN_MATNR() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][2];
    	obj[0] = new Object[]{
    			"ZPPS016"
    			, ZPPS016.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS016"
				, "ZPPS016"
				, "item"
				, ZPPS016[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZPP_GET_SN_MATNR";
	}

	@Override
	protected void initOperation(Object obj1) {
		OperationDesc oper = (OperationDesc)obj1;
		oper.addParameter(makeArrayParam("E_INTAB", PARAM_INOUT, "TABLE_OF_ZPPS016", ZPPS016[].class, "item" ));
		oper.addParameter(makeParam("I_FCRDAT", PARAM_IN ));
		oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_SCODE", PARAM_IN ));
    	oper.addParameter(makeParam("I_TCRDAT", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item" ));
	}

}
