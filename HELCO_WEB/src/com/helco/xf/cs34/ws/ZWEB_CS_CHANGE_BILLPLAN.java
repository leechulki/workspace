package com.helco.xf.cs34.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs34.ws.ZCSS011;
import com.helco.xf.cs34.ws.ZCSS007;

public class ZWEB_CS_CHANGE_BILLPLAN extends WebServiceStub {

	public ZWEB_CS_CHANGE_BILLPLAN() throws AxisFault {
		super();
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		//oper.addParameter(makeParam("E_TYPE", PARAM_OUT));
		//oper.addParameter(makeArrayParam("M_ITAB", PARAM_INOUT, "TABLE_OF_ZCSS007", ZCSS007[].class, "item"));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZCSS011", ZCSS011[].class, "item"));
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZCSS011"
    			, ZCSS011.class	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCSS011"
				, "ZCSS011"
				, "item"
				, ZCSS011[].class };

    	obj[2] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class };
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMSEMSG"
				, "ZQMSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_CS_CHANGE_BILLPLAN";
	}
}
