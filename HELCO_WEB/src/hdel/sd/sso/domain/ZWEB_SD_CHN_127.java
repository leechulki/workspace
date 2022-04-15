package hdel.sd.sso.domain;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_127 extends WebServiceStub {

	public ZWEB_SD_CHN_127() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0027"
    			, ZSDS0027.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0027"
				, "ZSDS0027"
				, "item"
				, ZSDS0027[].class };

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
		return "ZWEB_SD_CHN_127";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_FR_KUN",PARAM_IN));
		oper.addParameter(makeParam("I_KTOKD",PARAM_IN));
		oper.addParameter(makeParam("I_NAME1" ,PARAM_IN));
		oper.addParameter(makeParam("I_STCD1",PARAM_IN));
		oper.addParameter(makeParam("I_STCD2",PARAM_IN));
		oper.addParameter(makeParam("I_TO_KUN",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0027", ZSDS0027[].class, "item"));
	}
}

