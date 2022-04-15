package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_HOGI_SPEC2 extends WebServiceStub {

	public ZWEB_SD_HOGI_SPEC2() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{"ZPSSEMSG", ZQMSEMSG.class};
    	obj[1] = new Object[]{"TABLE_OF_ZPSSEMSG", "ZPSSEMSG", "item", ZQMSEMSG[].class };

    	obj[2] = new Object[]{"ZSDT0094", ZSDT0094.class};
    	obj[3] = new Object[]{"TABLE_OF_ZSDT0094", "ZSDT0094", "item", ZSDT0094[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_HOGI_SPEC2";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_FR_SO",PARAM_IN));
		oper.addParameter(makeParam("I_HOGI", PARAM_IN));
		oper.addParameter(makeParam("I_POSNR",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("S_ITAB", PARAM_INOUT, "TABLE_OF_ZSDT0094", ZSDT0094[].class, "item"));
	}
}
