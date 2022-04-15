package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;


/**
 * 매출채권 명세 및 채권 현황(청구) _법조치관리 (ZWEB_SD_CHN_152_LAW) WebServiceStub
 * @Comment 
 *     	- Ssa0022C(매출채권 명세 및 채권 현황(청구) _법조치관리) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */
 

public class ZWEB_SD_CHN_152_LAW extends WebServiceStub {

	public ZWEB_SD_CHN_152_LAW() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[6][6];
    	obj[0] = new Object[]{
    			"ZSDS0048"
    			, ZSDS0048.class   	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0048"
				, "ZSDS0048"
				, "item"
				, ZSDS0048[].class };  
    	obj[2] = new Object[]{
    			"ZSDS0049"
    			, ZSDS0049.class   	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0049"
				, "ZSDS0049"
				, "item"
				, ZSDS0049[].class };  
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
		return "ZWEB_SD_CHN_152_LAW";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj; 
		
		oper.addParameter(makeArrayParam("CS_ITAB" 		, PARAM_INOUT, "ZSDS0049", ZSDS0049.class, "item")); // 법조치문자열 
		oper.addParameter(makeParam		("I_GUBUN"		, PARAM_IN		));  // 구분(Q:조회, S:저장)        
		oper.addParameter(makeParam		("I_PSPID"		, PARAM_IN		));  // 프로젝트번호         
    	oper.addParameter(makeArrayParam("O_ETAB" 		, PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"		, PARAM_INOUT, "TABLE_OF_ZSDS0048", ZSDS0048[].class, "item"));
    	 
	}
	
}
