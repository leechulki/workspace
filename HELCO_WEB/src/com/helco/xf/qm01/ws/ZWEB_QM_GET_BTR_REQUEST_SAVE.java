package com.helco.xf.qm01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

/**
 * ���� ��û ���� ��� 
 */
public class ZWEB_QM_GET_BTR_REQUEST_SAVE extends WebServiceStub {

	public ZWEB_QM_GET_BTR_REQUEST_SAVE() throws AxisFault {
		super();
	}
	
	/**
     * Operation �ʱ�ȭ - ��ӹ޾� ���� 
     * @return
     */
    protected void initOperation(Object oper1) {
    	OperationDesc oper = (OperationDesc) oper1;
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZQMSEMSG", ZQMSEMSG[].class, "item" ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS026", ZQMS026[].class, "item" ));
    }
    
    /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZWEB_QM_GET_BTR_REQUEST_SAVE";
    }
    /**
     * �迭 �� �Ϲ� Ÿ�Կ� �ش��ϴ� Ÿ�� ���� 
     * @return
     */
    protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZQMS026"
    			, ZQMS026.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZQMS026"
				, "ZQMS026"
				, "item"
				, ZQMS026[].class };
	
    	obj[2] = new Object[]{
    			"ZQMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZQMSEMSG"
				, "ZQMSEMSG"
				, "item"
				, ZQMSEMSG[].class };
    	
    	return obj;
    }
}
