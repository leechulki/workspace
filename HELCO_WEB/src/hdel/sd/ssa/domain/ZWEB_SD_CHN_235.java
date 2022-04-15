package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_235 extends WebServiceStub {

	public ZWEB_SD_CHN_235() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0073"
    			, ZSDS0073.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0073"
				, "ZSDS0073"
				, "item"
				, ZSDS0073[].class };

    	obj[2] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_235";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_AUART",PARAM_IN));
		oper.addParameter(makeParam("I_CHK1",PARAM_IN));
		oper.addParameter(makeParam("I_GBN",PARAM_IN));
		oper.addParameter(makeParam("I_RAD1",PARAM_IN));
		oper.addParameter(makeParam("I_RAD2",PARAM_IN));
		oper.addParameter(makeParam("I_RAD3",PARAM_IN));
		oper.addParameter(makeParam("I_VKBUR",PARAM_IN));
		oper.addParameter(makeParam("I_VKGRP",PARAM_IN));
		oper.addParameter(makeParam("I_YMD",PARAM_IN));		
		oper.addParameter(makeParam("I_ZKUNNR",PARAM_IN));
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB1", PARAM_INOUT, "TABLE_OF_ZSDS0073", ZSDS0073[].class, "item"));
	}
}
