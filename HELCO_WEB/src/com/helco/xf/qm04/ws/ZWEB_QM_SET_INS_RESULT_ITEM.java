package com.helco.xf.qm04.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.qm04.ws.ZQMS027;
import com.helco.xf.qm04.ws.ZQMS027T;

public class ZWEB_QM_SET_INS_RESULT_ITEM extends WebServiceStub {

	public ZWEB_QM_SET_INS_RESULT_ITEM() throws AxisFault {
		super();
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeArrayParam("I_LTAB", PARAM_INOUT, "TABLE_OF_ZQMS027", ZQMS027[].class, "item" ));
		oper.addParameter(makeParam("I_TMODE", PARAM_IN ));
    	oper.addParameter(makeArrayParam("I_TTAB", PARAM_INOUT, "TABLE_OF_ZQMS027T", ZQMS027T[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][4];
    	obj[0] = new Object[]{
    			"ZQMS027"
    			, ZQMS027.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS027"
				, "ZQMS027"
				, "item"
				, ZQMS027[].class };
	
    	obj[2] = new Object[]{
    			"ZQMS027T"
    			, ZQMS027T.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMS027T"
				, "ZQMS027T"
				, "item"
				, ZQMS027T[].class };
    	
    	obj[4] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZQMSEMSG"
				, "ZQMSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_SET_INS_RESULT_ITEM";
	}
}
