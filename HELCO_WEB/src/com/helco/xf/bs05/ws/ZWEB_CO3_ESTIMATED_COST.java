package com.helco.xf.bs05.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;
import com.tobesoft.platform.data.Dataset;

public class ZWEB_CO3_ESTIMATED_COST extends WebServiceStub {

	public ZWEB_CO3_ESTIMATED_COST() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[12][4];
    	obj[0] = new Object[]{
    			"ZCABS006"
    			, ZCABS006.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCABS006"
				, "ZCABS006"
				, "item"
				, ZCABS006[].class 
		};
    	obj[2] = new Object[]{
    			"ZCABS005"
    			, ZCABS005.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZCABS005"
				, "ZCABS005"
				, "item"
				, ZCABS005[].class 
		};
    	obj[4] = new Object[]{
    			"ZCABT002"
    			, ZCABT002.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZCABT002"
				, "ZCABT002"
				, "item"
				, ZCABT002[].class 
		};
    	obj[6] = new Object[]{
    			"ZCABT002"
    			, ZCABT002.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_ZCABT002"
				, "ZCABT002"
				, "item"
				, ZCABT002[].class 
		};
    	obj[8] = new Object[]{
    			"ZCABS004"
    			, ZCABS004.class
    	};
    	obj[9] = new Object[]{
				"TABLE_OF_ZCABS004"
				, "ZCABS004"
				, "item"
				, ZCABS004[].class 
		};
    	obj[10] = new Object[]{
    			"ZCABS007"
    			, ZCABS007.class
    	};
    	obj[11] = new Object[]{
				"TABLE_OF_ZCABS007"
				, "ZCABS007"
				, "item"
				, ZCABS007[].class 
		};

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_CO3_ESTIMATED_COST";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeArrayParam("GS_HEAD", PARAM_INOUT, "TABLE_OF_ZCABS006", ZCABS006[].class, "item"));
    	oper.addParameter(makeArrayParam("GS_STEP", PARAM_INOUT, "TABLE_OF_ZCABS005", ZCABS005[].class, "item"));
    	oper.addParameter(makeArrayParam("GT_C_CD", PARAM_INOUT, "TABLE_OF_ZCABT002", ZCABT002[].class, "item"));
    	oper.addParameter(makeArrayParam("GT_V_CD", PARAM_INOUT, "TABLE_OF_ZCABT002", ZCABT002[].class, "item"));
		oper.addParameter(makeParam("I_DEP", PARAM_IN));
		oper.addParameter(makeParam("I_GBN", PARAM_IN));
		oper.addParameter(makeParam("I_ISR", PARAM_IN));
		oper.addParameter(makeParam("I_RDT", PARAM_IN));
		oper.addParameter(makeParam("I_SEQ", PARAM_IN));
		oper.addParameter(makeParam("I_SIR", PARAM_IN));
    	oper.addParameter(makeArrayParam("T_CHAR", PARAM_INOUT, "TABLE_OF_ZCABS004", ZCABS004[].class, "item"));
    	oper.addParameter(makeArrayParam("T_SAVE", PARAM_INOUT, "TABLE_OF_ZCABS007", ZCABS007[].class, "item"));
	}
}
