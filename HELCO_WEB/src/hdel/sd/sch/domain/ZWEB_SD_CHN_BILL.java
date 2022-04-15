package hdel.sd.sch.domain;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_BILL extends WebServiceStub {

	public ZWEB_SD_CHN_BILL() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	
    	obj[2] = new Object[]{
    			"ZSDS0069"
    			, ZSDS0069.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0069"
				, "ZSDS0069"
				, "item"
				, ZSDS0069[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_BILL";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_CMD",PARAM_IN));
		oper.addParameter(makeParam("I_FR_SO",PARAM_IN));
		oper.addParameter(makeParam("I_SMAN",PARAM_IN));
		oper.addParameter(makeParam("I_VKBUR",PARAM_IN));
		oper.addParameter(makeParam("I_VKGRP",PARAM_IN));		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0069", ZSDS0069[].class, "item"));
	}
}
