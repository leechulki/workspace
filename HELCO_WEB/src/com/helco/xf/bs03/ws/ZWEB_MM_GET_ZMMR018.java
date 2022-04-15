package com.helco.xf.bs03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_MM_GET_ZMMR018 extends WebServiceStub {

	public ZWEB_MM_GET_ZMMR018() throws AxisFault {
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
    			, ZMMS018.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZMMS018"
				, "ZMMS010"
				, "item"
				, ZMMS018[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_MM_GET_ZMMR018";
	}

	@Override
	protected void initOperation(Object obj) {

		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_CRDAT1", PARAM_IN ));
    	oper.addParameter(makeParam("I_CRDAT2", PARAM_IN ));
    	oper.addParameter(makeParam("I_IDNRK", PARAM_IN ));
    	oper.addParameter(makeParam("I_MATKL", PARAM_IN ));
    	oper.addParameter(makeParam("I_MATKLU", PARAM_IN ));
    	oper.addParameter(makeParam("I_MATNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_RA", PARAM_IN ));
    	oper.addParameter(makeParam("I_SPECT", PARAM_IN ));
    	oper.addParameter(makeParam("I_WOKNUM", PARAM_IN ));
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS018", ZMMS018[].class, "item" ));

	}

}
