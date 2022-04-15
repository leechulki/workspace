package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_100_CMD extends WebServiceStub {

	public ZWEB_SD_CHN_100_CMD() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZSDS0050"
    			, ZSDS0050.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0050"
				, "ZSDS0050"
				, "item"
				, ZSDS0050[].class };
    	obj[2] = new Object[]{
    			"ZSDS0052"
    			, ZSDS0052.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0052"
				, "ZSDS0052"
				, "item"
				, ZSDS0052[].class };

    	obj[4] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_100_CMD";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

	
		oper.addParameter(makeParam("I_CMD",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("P_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0052", ZSDS0052[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0050", ZSDS0050[].class, "item"));
	}
}
