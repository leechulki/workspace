package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

import hdel.lib.domain.BAPIRET2;
 

/**
 * 수주생성 조회 및 저장  (ZWEB_SD_CREATE_SO) WebServiceStub
 * @Comment 
 *     	- Sso0060C(수주생성) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZWEB_SD_CREATE_SO extends WebServiceStub {

	public ZWEB_SD_CREATE_SO() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[10][10]; 
    	obj[0] = new Object[]{
    			"ZSDS0060T"
    			, ZSDS0060T.class   	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0060T"
				, "ZSDS0060T"
				, "item"
				, ZSDS0060T[].class }; 
    	obj[2] = new Object[]{
    			"ZSDS0062"
    			, ZSDS0062.class   	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZSDS0062"
				, "ZSDS0062"
				, "item"
				, ZSDS0062[].class }; 
    	obj[4] = new Object[]{
    			"ZSDS0061"
    			, ZSDS0061.class   	}; 
    	obj[5] = new Object[]{
				"TABLE_OF_ZSDS0061"
				, "ZSDS0061"
				, "item"
				, ZSDS0061[].class }; 
    	obj[6] = new Object[]{
    			"BAPIRET2"
    			, BAPIRET2.class  	};
    	obj[7] = new Object[]{
				"TABLE_OF_BAPIRET2"
				, "BAPIRET2"
				, "item"
				, BAPIRET2[].class };
    	obj[8] = new Object[]{
    			"ZSDS0060"
    			, ZSDS0060.class   	};
    	obj[9] = new Object[]{
				"TABLE_OF_ZSDS0060"
				, "ZSDS0060"
				, "item"
				, ZSDS0060[].class };  
    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CREATE_SO";
	}

	@Override
	protected void initOperation(Object obj) { 
		
		OperationDesc oper = (OperationDesc)obj; 
		oper.addParameter(makeArrayParam("BIGO" 	, PARAM_INOUT, "TABLE_OF_ZSDS0060T", ZSDS0060T[].class, "item"));	// TEXT
		oper.addParameter(makeArrayParam("B_ITAB" 	, PARAM_INOUT, "TABLE_OF_ZSDS0062", ZSDS0062[].class, "item"));		// BILLING
		oper.addParameter(makeParam		("I_CMD"	, PARAM_IN));  // 구분(조회:DISP, 저장:SAVE, 변경:UPDT)              
		oper.addParameter(makeParam		("I_PSPID"	, PARAM_IN));  // 프로젝트
		oper.addParameter(makeParam		("I_QTNUM"	, PARAM_IN));  // 견적번호
		oper.addParameter(makeParam		("I_QTSER"	, PARAM_IN));  // 견적차수
    	oper.addParameter(makeArrayParam("P_ITAB" 	, PARAM_INOUT, "TABLE_OF_ZSDS0061", ZSDS0061[].class, "item")); 	// ITEM
		oper.addParameter(makeArrayParam("TT_BAPIRET2", PARAM_INOUT, "TABLE_OF_BAPIRET2", BAPIRET2[].class, "item"));		// ERROR
    	oper.addParameter(makeArrayParam("T_ITAB" 	, PARAM_INOUT, "TABLE_OF_ZSDS0060", ZSDS0060[].class, "item")); 	// HEADER
	}	
}
