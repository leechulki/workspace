package com.helco.xf.mm03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.mm03.ws.ZMMS011;

public class ZWEB_MM_BARCODE_TAG extends WebServiceStub {

	public ZWEB_MM_BARCODE_TAG() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZMMS011"
    			, ZMMS011.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMS011"
				, "ZMMS011" 
				, "item"
				, ZMMS011[].class };
    	obj[2] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMSEMSG"
				, "ZMMS011" 
				, "item"
				, ZQMSEMSG[].class };
    	return obj;
	}	
	


	@Override
	protected String getOperName() {
		return "ZWEB_MM_BARCODE_TAG";
	}

    @Override
    protected void initOperation(Object obj) {
    	OperationDesc oper = (OperationDesc)obj;
       	
       	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
       	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS011", ZMMS011[].class, "item" ));
   
	}
}
