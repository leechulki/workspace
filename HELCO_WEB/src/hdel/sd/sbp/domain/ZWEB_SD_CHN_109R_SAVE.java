package hdel.sd.sbp.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_109R_SAVE extends WebServiceStub {

	public ZWEB_SD_CHN_109R_SAVE() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0040"
    			, ZSDS0040.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0040"
				, "ZSDS0040"
				, "item"
				, ZSDS0040[].class };

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
		return "ZWEB_SD_CHN_109R_SAVE";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_FR_YM",PARAM_IN));
    //	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0040", ZSDS0040[].class, "item"));
	}
}
