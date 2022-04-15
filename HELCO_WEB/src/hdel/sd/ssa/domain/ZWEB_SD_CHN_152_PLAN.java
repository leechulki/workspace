package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
 

/**
 * 매출채권 명세 및 채권현황(청구별) 수금계획변경  (ZWEB_SD_CHN_152_PLAN) WebServiceStub
 * @Comment 
 *     	- Ssa0020C(매출채권 명세 및 채권현황(청구별)) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZWEB_SD_CHN_152_PLAN extends WebServiceStub {

	public ZWEB_SD_CHN_152_PLAN() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0039"
    			, ZSDS0039.class   	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0039"
				, "ZSDS0039"
				, "item"
				, ZSDS0039[].class }; 
    	obj[2] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class  	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_152_PLAN";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		                                       
		oper.addParameter(makeParam("I_YEARM"		,PARAM_IN));  // 기준년월              
    	oper.addParameter(makeArrayParam("O_ETAB" , PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB" , PARAM_INOUT, "TABLE_OF_ZSDS0039", ZSDS0039[].class, "item")); 
	}
}
