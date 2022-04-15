package hdel.sd.sco.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_189 extends WebServiceStub {

	public ZWEB_SD_CHN_189() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0033"
    			, ZSDS0033.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0033"
				, "ZSDS0033"
				, "item"
				, ZSDS0033[].class };

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
		return "ZWEB_SD_CHN_189";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_BOM",PARAM_IN));
		oper.addParameter(makeParam("I_FR_FLR",PARAM_IN));
		oper.addParameter(makeParam("I_FR_PSN" ,PARAM_IN));
		oper.addParameter(makeParam("I_FR_SPD",PARAM_IN));
		oper.addParameter(makeParam("I_FR_VB",PARAM_IN));
		
		oper.addParameter(makeParam("I_FR_YMD",PARAM_IN));
		oper.addParameter(makeParam("I_MOD",PARAM_IN));
		oper.addParameter(makeParam("I_OPEN",PARAM_IN));
		oper.addParameter(makeParam("I_TM",PARAM_IN));
		oper.addParameter(makeParam("I_TO_FLR",PARAM_IN));
		
		oper.addParameter(makeParam("I_TO_PSN",PARAM_IN));
		oper.addParameter(makeParam("I_TO_SPD",PARAM_IN));
		oper.addParameter(makeParam("I_TO_YMD",PARAM_IN));
		oper.addParameter(makeParam("I_USE",PARAM_IN));	
		
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0033", ZSDS0033[].class, "item"));
	}
}
