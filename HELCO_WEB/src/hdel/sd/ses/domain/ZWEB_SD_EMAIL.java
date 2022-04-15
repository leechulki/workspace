package hdel.sd.ses.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_EMAIL extends WebServiceStub {

	public ZWEB_SD_EMAIL() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][2];
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
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_EMAIL";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		//oper.addParameter(makeParam("C_SMSG",PARAM_INOUT)); 
		oper.addParameter(makeParam		("I_QTSO"  , PARAM_IN  ));
		oper.addParameter(makeParam		("I_RQSEQ"  , PARAM_IN  ));		
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	
	}
}

