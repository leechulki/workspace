package com.helco.xf.wb01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

/**
 * 휴일 정보 가져오기 
 */
public class ZWEB_MM_CHECK_MISU extends WebServiceStub {

	public ZWEB_MM_CHECK_MISU() throws AxisFault {
		super();
	}
	
	/**
     * Operation 초기화 - 상속받아 구현 
     * @return
     */
    protected void initOperation(Object oper1) {
    	OperationDesc oper = (OperationDesc) oper1;
    	oper.addParameter(makeParam("I_PSPID", PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
    	
    	oper.addParameter(makeParam("E_MISU", PARAM_OUT));
    }
    
    /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZWEB_MM_CHECK_MISU";
    }
    /**
     * 배열 및 일반 타입에 해당하는 타입 정의 
     * @return
     */
    protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];

    	obj[0] = new Object[]{
    			"ZMMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMSEMSG"
				, "ZMMSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	
    	return obj;
    }
}
