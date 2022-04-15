package hdel.sd.scl.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
import hdel.sd.com.domain.RANGE_C10;		// SO, 거래선용 코드범위선택 DATASET
 

/**
 * 프로젝트별 수금대장 (ZWEB_SD_PROJECT) WebServiceStub
 * @Comment 
 *     	- Scl0040C(프로젝트별 수금대장) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */
 

public class ZWEB_SD_PROJECT extends WebServiceStub {

	public ZWEB_SD_PROJECT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[8][8];
    	obj[0] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };
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
    			"RANGE_C10"
    			, RANGE_C10.class
    	}; 
    	obj[5] = new Object[]{
				"TABLE_OF_RANGE_C10"
				, "RANGE_C10"
				, "item"
				, RANGE_C10[].class 
		}; 
    	obj[6] = new Object[]{
    			"ZSDS_PJT"
    			, ZSDS_PJT.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_ZSDS_PJT"
				, "ZSDS_PJT"
				, "item"
				, ZSDS_PJT[].class 
		};

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_PROJECT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;  
		oper.addParameter(makeParam("I_AUART"	,PARAM_IN)); 
		oper.addParameter(makeParam("I_BSTNK"	,PARAM_IN));
		oper.addParameter(makeParam("I_BUYR"	,PARAM_IN));
		oper.addParameter(makeParam("I_FR_SO"	,PARAM_IN));
		oper.addParameter(makeParam("I_FR_VB"	,PARAM_IN));
		oper.addParameter(makeParam("I_FR_VG"	,PARAM_IN));
		oper.addParameter(makeParam("I_FR_YMD"	,PARAM_IN));
		oper.addParameter(makeParam("I_SMAN"	,PARAM_IN));
		oper.addParameter(makeParam("I_SPART"	,PARAM_IN));
		oper.addParameter(makeParam("I_STADA"	,PARAM_IN));
		oper.addParameter(makeParam("I_TO_SO"	,PARAM_IN));
		oper.addParameter(makeParam("I_TO_VB"	,PARAM_IN));
		oper.addParameter(makeParam("I_TO_VG"	,PARAM_IN));
		oper.addParameter(makeParam("I_TO_YMD"	,PARAM_IN));  
    	oper.addParameter(makeArrayParam("O_ETAB"	, PARAM_INOUT, "TABLE_OF_ZPSSEMSG"	, ZQMSEMSG[].class	, "item"));
    	oper.addParameter(makeArrayParam("S_KUNNR"	, PARAM_INOUT, "TABLE_OF_RANGE_C10"	, RANGE_C10[].class	, "item"));
    	oper.addParameter(makeArrayParam("S_VBELN"	, PARAM_INOUT, "TABLE_OF_RANGE_C10"	, RANGE_C10[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"	, PARAM_INOUT, "TABLE_OF_ZSDS_PJT"	, ZSDS_PJT[].class	, "item"));
	}
}
