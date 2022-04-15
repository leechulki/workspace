package hdel.sd.sbp.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
 

/**
 * 매출/청구/수금 자동산출 (ZWEB_SD_PLAN_COMPUTE) WebServiceStub
 * @Comment 
 *     	- Sbp0010C(사업계획관리), Sbp0040C(사업계획관리(보수)) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */
 

public class ZWEB_SD_PLAN_COMPUTE extends WebServiceStub {

	public ZWEB_SD_PLAN_COMPUTE() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[16][16];
    	obj[0] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class
		};
    	obj[2] = new Object[]{
    			"ZSDT0014"
    			, ZSDT0014.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDT0014"
				, "ZSDT0014"
				, "item"
				, ZSDT0014[].class
		};
    	obj[4] = new Object[]{
    			"ZSDT1001"
    			, ZSDT1001.class
    	}; 
    	obj[5] = new Object[]{
				"TABLE_OF_ZSDT1001"
				, "ZSDT1001"
				, "item"
				, ZSDT1001[].class
		};
    	obj[6] = new Object[]{
    			"ZSDT1005"
    			, ZSDT1005.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_ZSDT1005"
				, "ZSDT1005"
				, "item"
				, ZSDT1005[].class
		};
    	obj[8] = new Object[]{
    			"ZSDT0014S"
    			, ZSDT0014S.class
    	}; 
    	obj[9] = new Object[]{
				"TABLE_OF_ZSDT0014S"
				, "ZSDT0014S"
				, "item"
				, ZSDT0014S[].class
		};
    	obj[10] = new Object[]{
    			"ZSDT1012"
    			, ZSDT1012.class
    	};
    	obj[11] = new Object[]{
				"TABLE_OF_ZSDT1012"
				, "ZSDT1012"
				, "item"
				, ZSDT1012[].class
		};
    	obj[12] = new Object[]{
    			"ZSDT1023"
    			, ZSDT1023.class
    	}; 
    	obj[13] = new Object[]{
				"TABLE_OF_ZSDT1023"
				, "ZSDT1023"
				, "item"
				, ZSDT1023[].class
		};
    	obj[14] = new Object[]{
    			"ZSDS0072"
    			, ZSDS0072.class
    	}; 
    	obj[15] = new Object[]{
				"TABLE_OF_ZSDS0072"
				, "ZSDS0072"
				, "item"
				, ZSDS0072[].class
		};

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_PLAN_COMPUTE";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeParam("I_BOSUGBN",PARAM_IN));	// R:보수, S:영업
		oper.addParameter(makeParam("I_DELEGBN",PARAM_IN));	// D:삭제(사업계획의 경우 필요)
		oper.addParameter(makeParam("I_PLANGBN",PARAM_IN));	// Y:사업계획, M:월수주계획
		oper.addParameter(makeParam("I_REGBN",PARAM_IN));	// 매출 청구 수금 재산출 여부  (X, " " )
    	oper.addParameter(makeArrayParam("O_ETAB"	, PARAM_INOUT, "TABLE_OF_ZPSSEMSG"	, ZQMSEMSG[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB1"	, PARAM_INOUT, "TABLE_OF_ZSDT0014"	, ZSDT0014[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB2"	, PARAM_INOUT, "TABLE_OF_ZSDT1001"	, ZSDT1001[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB3"	, PARAM_INOUT, "TABLE_OF_ZSDT1005"	, ZSDT1005[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB4"	, PARAM_INOUT, "TABLE_OF_ZSDT0014S"	, ZSDT0014S[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB5"	, PARAM_INOUT, "TABLE_OF_ZSDT1012"	, ZSDT1012[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB6"	, PARAM_INOUT, "TABLE_OF_ZSDT1023"	, ZSDT1023[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB7"	, PARAM_INOUT, "TABLE_OF_ZSDS0072"	, ZSDS0072[].class	, "item"));
	}
}
