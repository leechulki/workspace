package com.helco.xf.qm01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_GET_BTR_REQUEST_LIST extends WebServiceStub {

	public ZWEB_QM_GET_BTR_REQUEST_LIST() throws AxisFault {
		super();
	}
	
	/**
     * Operation 초기화 - 상속받아 구현 
     * @return
     */
    protected void initOperation(Object oper1) {
    	OperationDesc oper = (OperationDesc) oper1;
    	oper.addParameter(makeParam("I_EINDTF", PARAM_IN ));
    	oper.addParameter(makeParam("I_EINDTT", PARAM_IN ));
    	oper.addParameter(makeParam("I_INVNRF", PARAM_IN ));
    	oper.addParameter(makeParam("I_INVNRT", PARAM_IN ));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_RPHOGIF", PARAM_IN ));
    	oper.addParameter(makeParam("I_RPHOGIT", PARAM_IN ));
    	
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS023", ZQMS023[].class, "item" ));
    }
    
    /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZWEB_QM_GET_BTR_REQUEST_LIST";
    } 
    /**
     * 배열 및 일반 타입에 해당하는 타입 정의 
     * @return
     */
    protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZQMS023"
    			, ZQMS023.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS023"
				, "ZQMS023"
				, "item"
				, ZQMS023[].class };
	
    	return obj;
    }
}
