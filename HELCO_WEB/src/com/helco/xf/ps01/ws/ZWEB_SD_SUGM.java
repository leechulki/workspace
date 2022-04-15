package com.helco.xf.ps01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_SUGM extends WebServiceStub {

	public ZWEB_SD_SUGM() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][4];
    	obj[0] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG" 
				, "item"
				, ZQMSEMSG[].class 
		};
    	obj[2] = new Object[]{
    			"ZSDS0015"
    			, ZSDS0015.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0015"
				, "ZSDS0015" 
				, "item"
				, ZSDS0015[].class 
		};
    	obj[4] = new Object[]{
    			"ZSDS0014"
    			, ZSDS0014.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZSDS0014"
				, "ZSDS0014"
				, "item"
				, ZSDS0014[].class 
		};
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_SD_SUGM";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_PROJ", PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("P_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0015", ZSDS0015[].class, "item"));
		oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0014", ZSDS0014[].class, "item"));	
	}
}
