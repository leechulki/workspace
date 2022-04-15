package hdel.sd.sbi.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_XD01 extends WebServiceStub {

	public ZWEB_SD_CHN_XD01() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0045"
    			, ZSDS0045.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0045"
				, "ZSDS0045"
				, "item"
				, ZSDS0045[].class };

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
		return "ZWEB_SD_CHN_XD01";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("C_SMSG",PARAM_INOUT));
		oper.addParameter(makeParam("I_CMD",PARAM_IN));
		oper.addParameter(makeParam("I_KTOKD",PARAM_IN));
		oper.addParameter(makeParam("I_KUNNR",PARAM_IN));
		oper.addParameter(makeParam("I_NAME1",PARAM_IN));
		oper.addParameter(makeParam("I_STCD1",PARAM_IN));
		oper.addParameter(makeParam("I_STCD2",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0045", ZSDS0045[].class, "item"));
	}
}

