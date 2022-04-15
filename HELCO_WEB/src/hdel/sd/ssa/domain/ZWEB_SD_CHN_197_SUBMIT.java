package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

import hdel.sd.ssa.domain.ZSDS0055;

public class ZWEB_SD_CHN_197_SUBMIT extends WebServiceStub {

	public ZWEB_SD_CHN_197_SUBMIT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];

    	obj[0] = new Object[]{
    			"ZSDS0055"
    			, ZSDS0055.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0055"
				, "ZSDS0055"
				, "item"
				, ZSDS0055[].class };
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
		return "ZWEB_SD_CHN_197_SUBMIT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam		("I_UNAME", PARAM_IN));
		oper.addParameter(makeParam		("E_TYPE"   , PARAM_OUT));
		oper.addParameter(makeArrayParam("O_ETAB"  , PARAM_INOUT	, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_ETAB2", PARAM_INOUT	, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"	  , PARAM_INOUT	, "TABLE_OF_ZSDS0055", ZSDS0055[].class, "item"));
	}
}
