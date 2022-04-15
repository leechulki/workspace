package hdel.sd.ses.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.ps01.ws.ZSDS0016;
import com.helco.xf.ps01.ws.ZSDS0017;

public class ZWEB_SD_HOGI_SPEC extends WebServiceStub {

	public ZWEB_SD_HOGI_SPEC() throws AxisFault {
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
    			"ZSDS0016"
    			, ZSDS0016.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0016"
				, "ZSDS0016" 
				, "item"
				, ZSDS0016[].class 
		};
    	obj[4] = new Object[]{
    			"ZSDS0017"
    			, ZSDS0017.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZSDS0017"
				, "ZSDS0017"
				, "item"
				, ZSDS0017[].class 
		};
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_HOGI_SPEC";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		//oper.addParameter(makeParam("C_SMSG",PARAM_INOUT)); 
		oper.addParameter(makeArrayParam("H_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0017", ZSDS0017[].class, "item"));
		oper.addParameter(makeParam		("I_HOGI"  , PARAM_IN  ));		
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0016", ZSDS0016[].class, "item"));
    	
	}
}

