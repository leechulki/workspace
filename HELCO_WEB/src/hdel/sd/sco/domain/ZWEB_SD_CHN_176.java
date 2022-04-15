package hdel.sd.sco.domain;


import hdel.sd.com.domain.RANGE_C10;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;


/**
 * 월별 수금계획/실적/진행현황 (ZWEB_SD_CHN_176) WebServiceStub
 * @Comment 
 *     	- Sco0100C(월별 수금계획/실적/진행현황) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */
 

public class ZWEB_SD_CHN_176 extends WebServiceStub {

	public ZWEB_SD_CHN_176() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZSDS0031"
    			, ZSDS0031.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0031"
				, "ZSDS0031"
				, "item"
				, ZSDS0031[].class };
    	obj[2] = new Object[]{
    			"RANGE_C10"
    			, RANGE_C10.class
    	}; 
    	obj[3] = new Object[]{
				"TABLE_OF_RANGE_C10"
				, "RANGE_C10"
				, "item"
				, RANGE_C10[].class 
		};  
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
		return "ZWEB_SD_CHN_176";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;  
		oper.addParameter(makeParam("I_FR_SO"	,PARAM_IN));   	// SO_FR
		oper.addParameter(makeParam("I_FR_VB"	,PARAM_IN)); 	// 부서코드
		oper.addParameter(makeParam("I_FR_VG"	,PARAM_IN)); 	// 팀코드_FR
		oper.addParameter(makeParam("I_FR_YM"	,PARAM_IN)); 	// 수주년월 
		oper.addParameter(makeParam("I_SMAN"	,PARAM_IN)); 	// 담당자코드
		oper.addParameter(makeParam("I_TO_SO"	,PARAM_IN)); 	// SO_TO
		oper.addParameter(makeParam("I_TO_VG"	,PARAM_IN)); 	// 팀코드_TO
    	oper.addParameter(makeArrayParam("O_ETAB"	, PARAM_INOUT, "TABLE_OF_ZPSSEMSG"	, ZQMSEMSG[].class	, "item"));
    	oper.addParameter(makeArrayParam("S_VBELN"	, PARAM_INOUT, "TABLE_OF_RANGE_C10"	, RANGE_C10[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"	, PARAM_INOUT, "TABLE_OF_ZSDS0031"	, ZSDS0031[].class	, "item"));
	}
}
