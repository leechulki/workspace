package com.helco.xf.ps02.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

/**
 * ���� ���� �������� 
 */
public class ZWEB_PS_CHECK_WORKING_DAY extends WebServiceStub {

	public ZWEB_PS_CHECK_WORKING_DAY() throws AxisFault {
		super();
	}
	
	/**
     * Operation �ʱ�ȭ - ��ӹ޾� ���� 
     * @return
     */
    protected void initOperation(Object oper1) {
    	OperationDesc oper = (OperationDesc) oper1;
    	oper.addParameter(makeParam("I_CHECK_DATE", PARAM_IN));
    	oper.addParameter(makeParam("E_NEXT_DAY", PARAM_OUT));
    	oper.addParameter(makeParam("E_PREV_DAY", PARAM_OUT));
    }
    
    /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZWEB_PS_CHECK_WORKING_DAY";
    }
    /**
     * �迭 �� �Ϲ� Ÿ�Կ� �ش��ϴ� Ÿ�� ���� 
     * @return
     */
    protected Object[][] getCustTypes() {
    	return null;
    }
}
