package hdel.sd.scl.domain;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_506 extends WebServiceStub {

	public ZWEB_SD_CHN_506() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };    	
    	obj[2] = new Object[]{
    			"ZSDS0067"
    			, ZSDS0067.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0067"
				, "ZSDS0067"
				, "item"
				, ZSDS0067[].class };
    	obj[4] = new Object[]{
    			"ZSDS0068"
    			, ZSDS0068.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZSDS0068"
				, "ZSDS0068"
				, "item"
				, ZSDS0068[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_506";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_BUYR",PARAM_IN));
		oper.addParameter(makeParam("I_RANK",PARAM_IN));
		oper.addParameter(makeParam("I_STADA",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("P_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0068", ZSDS0068[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0067", ZSDS0067[].class, "item"));
	}
}
