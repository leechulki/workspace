package hdel.sd.ssa.domain;


import hdel.sd.com.domain.RANGE_C10;
import hdel.sd.com.domain.RANGE_C30;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;


/**
 * [SD]매출채권 AGING SCHEDULE (ZWEB_SD_CHN_183) WebServiceStub
 * @Comment 
 *     	- Ssa0030C([SD]매출채권 AGING SCHEDULE) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */
 

public class ZWEB_SD_CHN_183 extends WebServiceStub {

	public ZWEB_SD_CHN_183() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[8][8];
    	obj[0] = new Object[]{
    			"ZSDS0032"
    			, ZSDS0032.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0032"
				, "ZSDS0032"
				, "item"
				, ZSDS0032[].class }; 
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
    			"RANGE_C30"
    			, RANGE_C30.class
    	}; 
    	obj[5] = new Object[]{
				"TABLE_OF_ZRANGE_C30"
				, "RANGE_C30"
				, "item"
				, RANGE_C30[].class 
		}; 
    	obj[6] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_183";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;

		oper.addParameter(makeParam("I_BS_YMD"	,PARAM_IN));
		oper.addParameter(makeParam("I_FR_PAYER",PARAM_IN));
		oper.addParameter(makeParam("I_FR_PJT"	,PARAM_IN));
		oper.addParameter(makeParam("I_FR_VB"	,PARAM_IN));
		oper.addParameter(makeParam("I_FR_VG"	,PARAM_IN));
		oper.addParameter(makeParam("I_MG_YMD"	,PARAM_IN));
		oper.addParameter(makeParam("I_PRT_GBN"	,PARAM_IN));
		oper.addParameter(makeParam("I_TO_PAYER",PARAM_IN));
		oper.addParameter(makeParam("I_TO_PJT"	,PARAM_IN));
		oper.addParameter(makeParam("I_TO_VB"	,PARAM_IN));
		oper.addParameter(makeParam("I_TO_VG"	,PARAM_IN));
		oper.addParameter(makeParam("I_YMD_GBN"	,PARAM_IN)); 
    	oper.addParameter(makeArrayParam("O_ETAB"	, PARAM_INOUT, "TABLE_OF_ZPSSEMSG"	, ZQMSEMSG[].class	, "item"));
    	oper.addParameter(makeArrayParam("S_KIDNO"	, PARAM_INOUT, "TABLE_OF_ZRANGE_C30", RANGE_C30[].class	, "item"));
    	oper.addParameter(makeArrayParam("S_KUNNR"	, PARAM_INOUT, "TABLE_OF_RANGE_C10"	, RANGE_C10[].class	, "item")); 
    	oper.addParameter(makeArrayParam("T_ITAB"	, PARAM_INOUT, "TABLE_OF_ZSDS0032"	, ZSDS0032[].class	, "item"));
	}
}
