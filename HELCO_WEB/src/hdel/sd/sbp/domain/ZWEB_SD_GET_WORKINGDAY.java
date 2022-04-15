package hdel.sd.sbp.domain;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_GET_WORKINGDAY extends WebServiceStub {

	public ZWEB_SD_GET_WORKINGDAY() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][2];
    	obj[0] = new Object[]{
    			"ZMMSEMSG"
    			, ZQMSEMSG.class };
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMSEMSG"
				, "ZMMSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_GET_WORKINGDAY";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("C_WORKINGDAY", PARAM_INOUT));
		oper.addParameter(makeParam("I_DATE_FROM", PARAM_IN));                                     
		oper.addParameter(makeParam("I_DAYS", PARAM_IN));                                       
		oper.addParameter(makeParam("I_WERKS", PARAM_IN));                                       
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
	}
}
