package com.helco.xf.qm01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_QM_GET_BTR_REQUESTED_LIST extends WebServiceStub {

	public ZWEB_QM_GET_BTR_REQUESTED_LIST() throws AxisFault {
		super();
	}
	
	/**
     * Operation �ʱ�ȭ - ��ӹ޾� ���� 
     * @return
     */
    protected void initOperation(Object oper1) {
    	OperationDesc oper = (OperationDesc) oper1;
    	oper.addParameter(makeParam("I_EINDTF", PARAM_IN ));
    	oper.addParameter(makeParam("I_EINDTT", PARAM_IN ));
    	oper.addParameter(makeParam("I_INVNRF", PARAM_IN ));
    	oper.addParameter(makeParam("I_INVNRT", PARAM_IN ));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_PACFDTF", PARAM_IN ));
    	oper.addParameter(makeParam("I_PACFDTT", PARAM_IN ));
    	oper.addParameter(makeParam("I_PAPRDTF", PARAM_IN ));
    	oper.addParameter(makeParam("I_PAPRDTT", PARAM_IN ));
    	oper.addParameter(makeParam("I_PARQDTF", PARAM_IN ));
    	oper.addParameter(makeParam("I_PARQDTT", PARAM_IN ));
    	oper.addParameter(makeParam("I_RPHOGIF", PARAM_IN ));
    	oper.addParameter(makeParam("I_RPHOGIT", PARAM_IN ));
    	
    	oper.addParameter(makeParam("I_STATUS2", PARAM_IN ));
    	oper.addParameter(makeParam("I_STATUS3", PARAM_IN ));
    	oper.addParameter(makeParam("I_STATUS4", PARAM_IN ));
    	oper.addParameter(makeParam("I_STATUS5", PARAM_IN ));
    	
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS023", ZQMS023[].class, "item" ));
    }
    
    /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZWEB_QM_GET_BTR_REQUESTED_LIST";
    }
    /**
     * String�� �ش��ϴ� Type ���� 
     * @return
     
    protected String[] getCharTypes() {
    	return new String[]{
    		"char1"
    		,"char10"
    		,"char18"
    		,"char22"
    		,"char24"
    		,"char30"
    		,"char35"
    		,"char4"
    		,"char40"
    		,"date"
    		,"numeric12"
    		,"numeric5"
    		,"unit3"
    	};
    }
    */
    /**
     * Decimal �� �ش��ϴ� Ÿ�� ���� 
     * @return
     
    protected String[] getBigDecimalTypes() {
    	return new String[]{
    			"quantum13.3"
        	};
    }
    */
    /**
     * �迭 �� �Ϲ� Ÿ�Կ� �ش��ϴ� Ÿ�� ���� 
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
