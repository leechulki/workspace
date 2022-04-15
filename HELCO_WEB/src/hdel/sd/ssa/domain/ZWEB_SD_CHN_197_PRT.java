package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

import hdel.sd.ssa.domain.ZSDS0051;
import hdel.sd.ssa.domain.ZSDS0053;

public class ZWEB_SD_CHN_197_PRT extends WebServiceStub {

	public ZWEB_SD_CHN_197_PRT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZSDS0051"
    			, ZSDS0051.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0051"
				, "ZSDS0051"
				, "item"
				, ZSDS0051[].class };
    	obj[2] = new Object[]{
    			"ZSDS0053"
    			, ZSDS0053.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0053"
				, "ZSDS0053"
				, "item"
				, ZSDS0053[].class };
    	obj[4] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_197_PRT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeArrayParam("CS_ITAB"      , PARAM_INOUT, "ZSDS0051", ZSDS0051.class, "item"));
		oper.addParameter(makeParam       ("I_DOCU"      , PARAM_IN));
		oper.addParameter(makeParam       ("I_KUNNR"    , PARAM_IN));
		oper.addParameter(makeParam       ("I_PLDAT"     , PARAM_IN));
		oper.addParameter(makeParam       ("I_VAT_RATE", PARAM_IN));
		oper.addParameter(makeArrayParam("O_ETAB"      , PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"	      , PARAM_INOUT, "TABLE_OF_ZSDS0053", ZSDS0053[].class, "item"));
	}
}
