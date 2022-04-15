package hdel.sd.scl.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_117E extends WebServiceStub {

	public ZWEB_SD_CHN_117E() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZSDS0015E"
    			, ZSDS0015E.class   	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0015E"
				, "ZSDS0015E"
				, "item"
				, ZSDS0015E[].class };
    	obj[2] = new Object[]{
    			"ZSDS0014E"
    			, ZSDS0014E.class   	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0014E"
				, "ZSDS0014E"
				, "item"
				, ZSDS0014E[].class };
    	obj[4] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class  	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_117E";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam		("C_ISVAT"	, PARAM_INOUT	));  	// 부가세여부
		oper.addParameter(makeParam		("I_PROJ"	, PARAM_IN		));		// 프로젝트ID
    	oper.addParameter(makeArrayParam("O_ETAB"	, PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("P_ITAB"	, PARAM_INOUT, "TABLE_OF_ZSDS0015E", ZSDS0015E[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"	, PARAM_INOUT, "TABLE_OF_ZSDS0014E", ZSDS0014E[].class, "item"));
	}
}
