package hdel.sd.sco.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_236 extends WebServiceStub {

	public ZWEB_SD_CHN_236() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZSDS0065"
    			, ZSDS0065.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0065"
				, "ZSDS0065"
				, "item"
				, ZSDS0065[].class };
    	
    	obj[2] = new Object[]{
    			"ZSDS0066"
    			, ZSDS0066.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0066"
				, "ZSDS0066"
				, "item"
				, ZSDS0066[].class };
    	
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
		return "ZWEB_SD_CHN_236";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_DUED" ,PARAM_IN));
		oper.addParameter(makeParam("I_REALT",PARAM_IN));
		oper.addParameter(makeParam("I_YMD"  ,PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB" , PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB1", PARAM_INOUT, "TABLE_OF_ZSDS0065", ZSDS0065[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB2", PARAM_INOUT, "TABLE_OF_ZSDS0066", ZSDS0066[].class, "item"));
	}
}
