package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZGW_SD_GET_SD_DATA1 extends WebServiceStub {

	public ZGW_SD_GET_SD_DATA1() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[30][30];

    	obj[0] = new Object[]{
    			"ZSDS0003"
    			, ZSDS0003.class 	};

    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0003"
				, "ZSDS0003"
				, "item"
				, ZSDS0003[].class };

    	obj[2] = new Object[]{
    			"ZSDS0006"
    			, ZSDS0006.class 	};

    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0006"
				, "ZSDS0006"
				, "item"
				, ZSDS0006[].class };

    	obj[4] = new Object[]{
    			"ZSDS0002"
    			, ZSDS0002.class	};

    	obj[5] = new Object[]{
				"TABLE_OF_ZSDS0002"
				, "ZSDS0002"
				, "item"
				, ZSDS0002[].class };

    	obj[6] = new Object[]{
    			"ZSDS0005"
    			, ZSDS0005.class	};

    	obj[7] = new Object[]{
				"TABLE_OF_ZSDS0005"
				, "ZSDS0005"
				, "item"
				, ZSDS0005[].class };

    	obj[8] = new Object[]{
    			"ZSDS0008"
    			, ZSDS0008.class	};

    	obj[9] = new Object[]{
				"TABLE_OF_ZSDS0008"
				, "ZSDS0008"
				, "item"
				, ZSDS0008[].class };

    	obj[10] = new Object[]{
    			"ZSDS0001"
    			, ZSDS0001.class	};

    	obj[11] = new Object[]{
				"TABLE_OF_ZSDS0001"
				, "ZSDS0001"
				, "item"
				, ZSDS0001[].class };

    	obj[12] = new Object[]{
    			"ZSDS028"
    			, ZSDS028.class	};

    	obj[13] = new Object[]{
				"TABLE_OF_ZSDS028"
				, "ZSDS028"
				, "item"
				, ZSDS028[].class };

    	obj[14] = new Object[]{
    			"ZSDS0004"
    			, ZSDS0004.class	};

    	obj[15] = new Object[]{
				"TABLE_OF_ZSDS0004"
				, "ZSDS0004"
				, "item"
				, ZSDS0004[].class };

    	obj[16] = new Object[]{
    			"ZSDS0004"
    			, ZSDS0004.class	};

    	obj[17] = new Object[]{
				"TABLE_OF_ZSDS0004"
				, "ZSDS0004"
				, "item"
				, ZSDS0004[].class };

    	obj[18] = new Object[]{
    			"ZSDS0058"
    			, ZSDS0058.class	};

    	obj[19] = new Object[]{
				"TABLE_OF_ZSDS0058"
				, "ZSDS0058"
				, "item"
				, ZSDS0056[].class };
    	
    	obj[20] = new Object[]{
    			"ZSDS0020"
    			, ZSDS0020.class	};

    	obj[21] = new Object[]{
				"TABLE_OF_ZSDS0020"
				, "ZSDS0020"
				, "item"
				, ZSDS0020[].class };

    	obj[22] = new Object[]{
    			"ZSDS0020"
    			, ZSDS0020.class	};

    	obj[23] = new Object[]{
				"TABLE_OF_ZSDS0020"
				, "ZSDS0020"
				, "item"
				, ZSDS0020[].class };

    	obj[24] = new Object[]{
    			"TLINE"
    			, TLINE.class	};

    	obj[25] = new Object[]{
				"TABLE_OF_TLINE"
				, "TLINE"
				, "item"
				, TLINE[].class };

    	obj[26] = new Object[]{
    			"ZSDS0020"
    			, ZSDS0020.class	};

    	obj[27] = new Object[]{
				"TABLE_OF_ZSDS0020"
				, "ZSDS0020"
				, "item"
				, ZSDS0020[].class };

    	obj[28] = new Object[]{
    			"ZSDS0056"
    			, ZSDS0056.class	};

    	obj[29] = new Object[]{
				"TABLE_OF_ZSDS0056"
				, "ZSDS0056"
				, "item"
				, ZSDS0056[].class };
    	
    		return obj;
	}

	@Override
	protected String getOperName() {
		return "ZGW_SD_GET_SD_DATA1";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeArrayParam("BILLCON", PARAM_INOUT, "TABLE_OF_ZSDS0003", ZSDS0003[].class, "item"));
		oper.addParameter(makeArrayParam("BOTTINFO", PARAM_INOUT, "TABLE_OF_ZSDS0006", ZSDS0006[].class, "item"));
		oper.addParameter(makeArrayParam("CONCON", PARAM_INOUT, "TABLE_OF_ZSDS0002", ZSDS0002[].class, "item"));
		oper.addParameter(makeArrayParam("COSTINFO", PARAM_INOUT, "TABLE_OF_ZSDS0005", ZSDS0005[].class, "item"));
		oper.addParameter(makeArrayParam("COSTINFO2", PARAM_INOUT, "TABLE_OF_ZSDS0008", ZSDS0008[].class, "item"));
		oper.addParameter(makeArrayParam("HEAD", PARAM_INOUT, "TABLE_OF_ZSDS0001", ZSDS0001[].class, "item"));
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZSDS028", ZSDS028[].class, "item"));
		oper.addParameter(makeArrayParam("SOINFO", PARAM_INOUT, "TABLE_OF_ZSDS0004", ZSDS0004[].class, "item"));
		oper.addParameter(makeArrayParam("SOINFO1", PARAM_INOUT, "TABLE_OF_ZSDS0004", ZSDS0004[].class, "item"));
		oper.addParameter(makeArrayParam("SPDRFTINFO", PARAM_INOUT, "TABLE_OF_ZSDS0058", ZSDS0058[].class, "item"));		
		oper.addParameter(makeArrayParam("SUBTO", PARAM_INOUT, "TABLE_OF_ZSDS0020", ZSDS0020[].class, "item"));
		oper.addParameter(makeArrayParam("SUBTO1", PARAM_INOUT, "TABLE_OF_ZSDS0020", ZSDS0020[].class, "item"));
		oper.addParameter(makeArrayParam("TEXT", PARAM_INOUT, "TABLE_OF_TLINE", TLINE[].class, "item"));
		oper.addParameter(makeArrayParam("TOTAL", PARAM_INOUT, "TABLE_OF_ZSDS0020", ZSDS0020[].class, "item"));
		oper.addParameter(makeArrayParam("T_ADD_DATA", PARAM_INOUT, "TABLE_OF_ZSDS0056", ZSDS0056[].class, "item"));
		oper.addParameter(makeParam("VBELN",PARAM_IN));
	}
}
