package com.helco.xf.bs03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_MM_PJT_ITEM_STATE extends WebServiceStub {

	public ZWEB_MM_PJT_ITEM_STATE() throws AxisFault {
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
    			"ZMMS010"
    			, ZMMS010.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZMMS010"
				, "ZMMS010"
				, "item"
				, ZMMS010[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_MM_PJT_ITEM_STATE";
	}

	@Override
	protected void initOperation(Object obj) {

		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_CHECK", PARAM_IN ));
    	oper.addParameter(makeParam("I_MATNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_POSID", PARAM_IN ));
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS010", ZMMS010[].class, "item" ));

	}

}
