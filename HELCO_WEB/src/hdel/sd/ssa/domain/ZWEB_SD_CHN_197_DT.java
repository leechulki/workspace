package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

import hdel.sd.ssa.domain.ZSDS0047;
import hdel.sd.ssa.domain.ZSDS0043;
import hdel.sd.ssa.domain.ZSDS0078;

public class ZWEB_SD_CHN_197_DT extends WebServiceStub {

	public ZWEB_SD_CHN_197_DT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[10][10];
    	obj[0] = new Object[]{
    			"ZSDS0043"
    			, ZSDS0043.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0043"
				, "ZSDS0043"
				, "item"
				, ZSDS0043[].class };
    	obj[2] = new Object[]{
    			"ZSDS0047"
    			, ZSDS0047.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0047"
				, "ZSDS0047"
				, "item"
				, ZSDS0047[].class };
    	obj[4] = new Object[]{
    			"ZSDS0078"
    			, ZSDS0078.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZSDS0078"
				, "ZSDS0078"
				, "item"
				, ZSDS0078[].class };
    	obj[6] = new Object[]{
    			"TLINE"
    			, TLINE.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_TLINE"
				, "TLINE"
				, "item"
				, TLINE[].class };
    	obj[8] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[9] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_197_DT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj; 
		oper.addParameter(makeArrayParam("CS_ITAB"   , PARAM_INOUT	, "ZSDS0047", ZSDS0047.class, "item"));
		oper.addParameter(makeParam		("C_MODIFY", PARAM_INOUT));
		oper.addParameter(makeArrayParam("IS_ITAB"    , PARAM_IN		, "ZSDS0043", ZSDS0043.class, "item"));
		oper.addParameter(makeParam		("I_UNAME" , PARAM_IN));
		oper.addParameter(makeArrayParam("O_ETAB"   , PARAM_INOUT	, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"	   , PARAM_INOUT	, "TABLE_OF_ZSDS0078", ZSDS0078[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB2"   , PARAM_INOUT	, "TABLE_OF_TLINE", TLINE[].class, "item"));
	}
}
