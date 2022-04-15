package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_133_PRINT extends WebServiceStub {

	public ZWEB_SD_CHN_133_PRINT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];

    	obj[0] = new Object[]{
    			"ZSDS0013"
    			, ZSDS0013.class 	};

    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0013"
				, "ZSDS0013"
				, "item"
				, ZSDS0013[].class };

    	obj[2] = new Object[]{
    			"ZSDS0012"
    			, ZSDS0012.class 	};

    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0012"
				, "ZSDS0012"
				, "item"
				, ZSDS0012[].class };

    	obj[4] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class	};

    	obj[5] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    		return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_133_PRINT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

    	oper.addParameter(makeArrayParam("A_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0013", ZSDS0013[].class, "item"));
    	oper.addParameter(makeArrayParam("B_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0013", ZSDS0013[].class, "item"));
    	oper.addParameter(makeArrayParam("C_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0013", ZSDS0013[].class, "item"));
    	oper.addParameter(makeArrayParam("D_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0013", ZSDS0013[].class, "item"));
    	oper.addParameter(makeArrayParam("E_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0013", ZSDS0013[].class, "item"));
		oper.addParameter(makeParam("I_CHK",PARAM_IN));
		oper.addParameter(makeParam("I_FR_PO",PARAM_IN));
		oper.addParameter(makeParam("I_FR_SO",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0012", ZSDS0012[].class, "item"));
	}
}
