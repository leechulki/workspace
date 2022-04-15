package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_197 extends WebServiceStub {

	public ZWEB_SD_CHN_197() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0043"
    			, ZSDS0043.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0043"
				, "ZSDS0043"
				, "item"
				, ZSDS0043[].class };

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
		return "ZWEB_SD_CHN_197";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_FR_AUART",PARAM_IN));
		oper.addParameter(makeParam("I_FR_EMPL",PARAM_IN));
		oper.addParameter(makeParam("I_FR_KUNZ1",PARAM_IN));
		oper.addParameter(makeParam("I_FR_PAYER",PARAM_IN));
		oper.addParameter(makeParam("I_FR_SO",PARAM_IN));
		oper.addParameter(makeParam("I_FR_SPART",PARAM_IN));
		oper.addParameter(makeParam("I_FR_VB",PARAM_IN));
		oper.addParameter(makeParam("I_FR_VG",PARAM_IN));
		oper.addParameter(makeParam("I_FR_YMD",PARAM_IN));
		oper.addParameter(makeParam("I_TO_AUART",PARAM_IN));
		oper.addParameter(makeParam("I_TO_EMPL",PARAM_IN));
		oper.addParameter(makeParam("I_TO_KUNZ1",PARAM_IN));
		oper.addParameter(makeParam("I_TO_PAYER",PARAM_IN));
		oper.addParameter(makeParam("I_TO_SO",PARAM_IN));
		oper.addParameter(makeParam("I_TO_SPART",PARAM_IN));
		oper.addParameter(makeParam("I_TO_VB",PARAM_IN));
		oper.addParameter(makeParam("I_TO_VG",PARAM_IN));
		oper.addParameter(makeParam("I_TO_YMD",PARAM_IN));
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0043", ZSDS0043[].class, "item"));
	}
}
