package hdel.sd.sso.domain;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_CHECK_CODENROUTING extends WebServiceStub {

	public ZWEB_PP_CHECK_CODENROUTING() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[2][2];
    	obj[0] = new Object[]{"ZPPT124", ZPPT124.class};
    	obj[1] = new Object[]{"TABLE_OF_ZPPT124", "ZPPT124", "item", ZPPT124[].class };
		return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_PP_CHECK_CODENROUTING";
	}

	@Override
	protected void initOperation(Object obj) {

		OperationDesc oper = (OperationDesc) obj;

		oper.addParameter(makeParam("I_CODE", PARAM_IN));
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		
		oper.addParameter(makeParam		("ROUTING_STATUS"   , PARAM_OUT));
		oper.addParameter(makeParam		("RTN_STATUS"   , PARAM_OUT));
		oper.addParameter(makeParam		("E_TYPE"   , PARAM_OUT)); 
		
		oper.addParameter(makeArrayParam("RTN_TBL"  , PARAM_INOUT, "TABLE_OF_ZPPT124", ZPPT124[].class, "item"));

	}
}
