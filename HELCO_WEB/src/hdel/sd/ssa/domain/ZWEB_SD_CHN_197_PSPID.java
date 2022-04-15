package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

import hdel.sd.ssa.domain.ZSDS0047;

public class ZWEB_SD_CHN_197_PSPID extends WebServiceStub {

	public ZWEB_SD_CHN_197_PSPID() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];

    	obj[0] = new Object[]{
    			"ZSDS0047"
    			, ZSDS0047.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0047"
				, "ZSDS0047"
				, "item"
				, ZSDS0047[].class };
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
		return "ZWEB_SD_CHN_197_PSPID";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeArrayParam("CS_ITAB", PARAM_INOUT, "ZSDS0047", ZSDS0047.class, "item"));
		oper.addParameter(makeParam		("I_PROTP", PARAM_IN   ));
		oper.addParameter(makeParam		("I_PSPID" , PARAM_IN   ));
		oper.addParameter(makeParam		("E_TYPE"  , PARAM_OUT));
		oper.addParameter(makeArrayParam("O_ETAB" , PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
	}
}
