package hdel.sd.scl.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;
 

/**
 * 수금상세정보 조회 (ZWEB_SD_CHN_117) WebServiceStub
 * @Comment 
 *     	- Scl0041C(수금상세정보 조회) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZWEB_SD_CHN_117 extends WebServiceStub {

	public ZWEB_SD_CHN_117() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZSDS0015"
    			, ZSDS0015.class   	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0015"
				, "ZSDS0015"
				, "item"
				, ZSDS0015[].class };
    	obj[2] = new Object[]{
    			"ZSDS0014"
    			, ZSDS0014.class   	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0014"
				, "ZSDS0014"
				, "item"
				, ZSDS0014[].class };
    	obj[4] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class  	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_117";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		
		oper.addParameter(makeParam		("C_ISVAT"	, PARAM_INOUT	));  	// 부가세여부
		oper.addParameter(makeParam		("I_PROJ"	, PARAM_IN		));		// 프로젝트ID
    	oper.addParameter(makeArrayParam("O_ETAB"	, PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("P_ITAB"	, PARAM_INOUT, "TABLE_OF_ZSDS0015", ZSDS0015[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"	, PARAM_INOUT, "TABLE_OF_ZSDS0014", ZSDS0014[].class, "item"));
	}
}
