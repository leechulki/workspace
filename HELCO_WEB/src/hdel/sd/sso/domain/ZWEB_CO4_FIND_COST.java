package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sso.domain.ZCOBT200;
import hdel.sd.sso.domain.ZCOBT202;
import hdel.sd.sso.domain.ZCOBT302;
import hdel.sd.sso.domain.ZCOBT204;
import hdel.sd.sso.domain.ZCOBT300;
import hdel.sd.sso.domain.ZCOBT304;
import hdel.sd.sso.domain.ZCOBT309;

public class ZWEB_CO4_FIND_COST extends WebServiceStub {

	public ZWEB_CO4_FIND_COST() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[22][22];
    	obj[0] = new Object[]{
    			"ZCOBS001"
    			, ZCOBS001.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZCOBS001"
				, "ZCOBS001"
				, "item"
				, ZCOBS001[].class };
    	obj[2] = new Object[]{
    			"ZCOBT309"
    			, ZCOBT309.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZCOBT309"
				, "ZCOBT309"
				, "item"
				, ZCOBT309[].class };
    	obj[4] = new Object[]{
    			"ZCOBT200"
    			, ZCOBT200.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZCOBT200"
				, "ZCOBT200"
				, "item"
				, ZCOBT200[].class };
    	obj[6] = new Object[]{
    			"ZCOBT202"
    			, ZCOBT202.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_ZCOBT202"
				, "ZCOBT202"
				, "item"
				, ZCOBT202[].class };
    	obj[8] = new Object[]{
    			"ZCOBT204"
    			, ZCOBT204.class
    	};
    	obj[9] = new Object[]{
				"TABLE_OF_ZCOBT204"
				, "ZCOBT204"
				, "item"
				, ZCOBT204[].class };
    	obj[10] = new Object[]{
    			"ZCOBT206"
    			, ZCOBT206.class
    	};
    	obj[11] = new Object[]{
				"TABLE_OF_ZCOBT206"
				, "ZCOBT206"
				, "item"
				, ZCOBT206[].class };
    	obj[12] = new Object[]{
    			"ZCOBT300"
    			, ZCOBT300.class
    	};
    	obj[13] = new Object[]{
				"TABLE_OF_ZCOBT300"
				, "ZCOBT300"
				, "item"
				, ZCOBT300[].class };
    	obj[14] = new Object[]{
    			"ZCOBT302"
    			, ZCOBT302.class
    	};
    	obj[15] = new Object[]{
				"TABLE_OF_ZCOBT302"
				, "ZCOBT302"
				, "item"
				, ZCOBT302[].class };
    	obj[16] = new Object[]{
    			"ZCOBT304"
    			, ZCOBT304.class
    	};
    	obj[17] = new Object[]{
				"TABLE_OF_ZCOBT304"
				, "ZCOBT304"
				, "item"
				, ZCOBT304[].class };
    	obj[18] = new Object[]{
    			"ZCOBT306"
    			, ZCOBT306.class
    	};
    	obj[19] = new Object[]{
				"TABLE_OF_ZCOBT306"
				, "ZCOBT306"
				, "item"
				, ZCOBT306[].class };
    	obj[20] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[21] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_CO4_FIND_COST";
	}

	@Override
	protected void initOperation(Object obj) {
		  
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeParam		("I_DIV"     , PARAM_IN  ));
		oper.addParameter(makeParam		("I_GBN"    , PARAM_IN  ));
		oper.addParameter(makeParam		("I_GJAHR" , PARAM_IN  ));
		oper.addParameter(makeParam		("I_POPER" , PARAM_IN  ));
		oper.addParameter(makeParam		("I_SEQ" , PARAM_IN  ));
		oper.addParameter(makeParam		("I_ZDATE" , PARAM_IN  ));
		
		oper.addParameter(makeParam		("E_TYPE"   , PARAM_OUT)); 
		
		oper.addParameter(makeArrayParam("T_CHAR"  , PARAM_INOUT, "TABLE_OF_ZCOBS001", ZCOBS001[].class, "item"));
		oper.addParameter(makeArrayParam("T_CHEK"  , PARAM_INOUT, "TABLE_OF_ZCOBT309", ZCOBT309[].class, "item"));  // ZCOBS002->ZCOBT309
		oper.addParameter(makeArrayParam("T_T200"   , PARAM_INOUT, "TABLE_OF_ZCOBT200", ZCOBT200[].class, "item"));  // 2012.08.30 : 추가
		oper.addParameter(makeArrayParam("T_T202"   , PARAM_INOUT, "TABLE_OF_ZCOBT202", ZCOBT202[].class, "item"));
		oper.addParameter(makeArrayParam("T_T204"   , PARAM_INOUT, "TABLE_OF_ZCOBT204", ZCOBT204[].class, "item"));
		oper.addParameter(makeArrayParam("T_T206"   , PARAM_INOUT, "TABLE_OF_ZCOBT206", ZCOBT206[].class, "item"));
		oper.addParameter(makeArrayParam("T_T300"   , PARAM_INOUT, "TABLE_OF_ZCOBT300", ZCOBT300[].class, "item"));  // 2012.08.30 : 추가
		oper.addParameter(makeArrayParam("T_T302"   , PARAM_INOUT, "TABLE_OF_ZCOBT302", ZCOBT302[].class, "item"));  // 2012.08.30 : 추가
		oper.addParameter(makeArrayParam("T_T304"   , PARAM_INOUT, "TABLE_OF_ZCOBT304", ZCOBT304[].class, "item"));
		oper.addParameter(makeArrayParam("T_T306"   , PARAM_INOUT, "TABLE_OF_ZCOBT306", ZCOBT306[].class, "item"));
	}
}
