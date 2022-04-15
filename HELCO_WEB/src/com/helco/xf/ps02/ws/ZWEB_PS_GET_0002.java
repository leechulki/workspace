package com.helco.xf.ps02.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PS_GET_0002 extends WebServiceStub {

	public ZWEB_PS_GET_0002() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZPSS006"
    			, ZPSS006.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSS006"
				, "ZPSS006"
				, "item"
				, ZPSS006[].class };
    	
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
		return "ZWEB_PS_GET_0002";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_DATEF", PARAM_IN));
		oper.addParameter(makeParam("I_DATET", PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN", PARAM_IN));
		oper.addParameter(makeParam("I_PSPID", PARAM_IN));
		oper.addParameter(makeParam("I_ZZACTSS", PARAM_IN));
		oper.addParameter(makeParam("I_ZZGUBUN_10", PARAM_IN));
		oper.addParameter(makeParam("I_ZZGUBUN_11", PARAM_IN));
		oper.addParameter(makeParam("I_ZZGUBUN_12", PARAM_IN));
		oper.addParameter(makeParam("I_ZZGUBUN_20", PARAM_IN));
		oper.addParameter(makeParam("I_ZZINTER", PARAM_IN));
		oper.addParameter(makeParam("I_ZZLIFNR", PARAM_IN));
		oper.addParameter(makeParam("I_ZZPMNUM", PARAM_IN));
		oper.addParameter(makeParam("I_ZZSHIP", PARAM_IN));
		oper.addParameter(makeParam("I_ZZTEMNO", PARAM_IN));

		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPSS006", ZPSS006[].class, "item"));
	}
}
