package hdel.sd.sag.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_139 extends WebServiceStub {

	public ZWEB_SD_CHN_139() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0037"
    			, ZSDS0037.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0037"
				, "ZSDS0037"
				, "item"
				, ZSDS0037[].class };

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
		return "ZWEB_SD_CHN_139";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

        oper.addParameter(makeParam("I_FR_SO",PARAM_IN));
        oper.addParameter(makeParam("I_FR_VB",PARAM_IN));
        oper.addParameter(makeParam("I_FR_VG",PARAM_IN));
        oper.addParameter(makeParam("I_FR_YMD",PARAM_IN));
        oper.addParameter(makeParam("I_LIFNR",PARAM_IN));
        oper.addParameter(makeParam("I_SMAN",PARAM_IN));
        oper.addParameter(makeParam("I_TO_SO",PARAM_IN));
        oper.addParameter(makeParam("I_TO_VB",PARAM_IN));
        oper.addParameter(makeParam("I_TO_VG",PARAM_IN));
        oper.addParameter(makeParam("I_TO_YMD",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0037", ZSDS0037[].class, "item"));
	}
}
