package com.helco.xf.mm05.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_LE_GET_DN002 extends WebServiceStub {

	public ZWEB_LE_GET_DN002() throws AxisFault {
		super();
	} 

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZLES006"
    			, ZLES006.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZLES006"
				, "ZLES006"
				, "item"
				, ZLES006[].class };
    	obj[2] = new Object[]{
    			"ZLET017"
    			, ZLET017.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZLET017"
				, "ZLET017"
				, "item"
				, ZLET017[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_LE_GET_DN002";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeParam("I_EXTWG2", PARAM_IN ));
		oper.addParameter(makeParam("I_MATNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
    	oper.addParameter(makeParam("I_PSPID", PARAM_IN ));
    	oper.addParameter(makeParam("I_VSTEL", PARAM_IN ));
    	oper.addParameter(makeParam("I_ZZPROF", PARAM_IN ));
    	oper.addParameter(makeParam("I_ZZPROT", PARAM_IN ));
    	    	
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZLES006", ZLES006[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TABL", PARAM_INOUT, "TABLE_OF_ZLET017", ZLET017[].class, "item" ));
	}
}
