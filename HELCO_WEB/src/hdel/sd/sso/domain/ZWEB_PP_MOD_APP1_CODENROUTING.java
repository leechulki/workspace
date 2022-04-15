package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_MOD_APP1_CODENROUTING extends WebServiceStub {

	public ZWEB_PP_MOD_APP1_CODENROUTING() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	return null;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_MOD_APP1_CODENROUTING";
	}

	@Override
	protected void initOperation(Object obj) {
		  
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_CNG_USEDAT", PARAM_IN));
		oper.addParameter(makeParam("I_CODE", PARAM_IN));
		oper.addParameter(makeParam("I_ILDAT",PARAM_IN));
		oper.addParameter(makeParam("I_ITEM_NO",PARAM_IN));
		oper.addParameter(makeParam("I_JTYPE",PARAM_IN));
		oper.addParameter(makeParam("I_MATNR",PARAM_IN));
		oper.addParameter(makeParam("I_POSID",PARAM_IN));
		oper.addParameter(makeParam("I_SEQ",PARAM_IN));
		oper.addParameter(makeParam("I_STATUS",PARAM_IN));
		oper.addParameter(makeParam("I_S_RQ_DAT",PARAM_IN));
		oper.addParameter(makeParam("I_S_RQ_USER",PARAM_IN));
		oper.addParameter(makeParam("I_VALUE",PARAM_IN));
		oper.addParameter(makeParam("I_VALUE_B",PARAM_IN));
	}
}
