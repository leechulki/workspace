package hdel.sd.sso.domain;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_UPDATE_JQPR_SDINFO extends WebServiceStub {

	public ZWEB_QM_UPDATE_JQPR_SDINFO() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		return null;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_QM_UPDATE_JQPR_SDINFO";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeParam		("I_JQPRNUM"  , PARAM_IN  ));
	}
	
}
