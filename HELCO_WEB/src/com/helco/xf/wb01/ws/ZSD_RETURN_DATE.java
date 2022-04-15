package com.helco.xf.wb01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;

/**
 * ���࿩�� ���� �������� 
 */
public class ZSD_RETURN_DATE extends WebServiceStub {

	public ZSD_RETURN_DATE() throws AxisFault {
		super();
	}
	
	/**
     * Operation �ʱ�ȭ - ��ӹ޾� ���� 
     * @return
     */
    protected void initOperation(Object oper1) {
    	OperationDesc oper = (OperationDesc) oper1;
    	oper.addParameter(makeParam("I_DATE", PARAM_IN));
    	oper.addParameter(makeParam("I_GUBUN", PARAM_IN));
    	oper.addParameter(makeParam("I_PSPID", PARAM_IN));

    	oper.addParameter(makeParam("E_DATE", PARAM_OUT));
    	oper.addParameter(makeParam("E_FAKWR", PARAM_OUT));
    	oper.addParameter(makeParam("E_FLAG", PARAM_OUT));
    	oper.addParameter(makeParam("E_MECH", PARAM_OUT));
    	oper.addParameter(makeParam("E_SUGM", PARAM_OUT));
    }
    
    /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZSD_RETURN_DATE";
    }
    /**
     * �迭 �� �Ϲ� Ÿ�Կ� �ش��ϴ� Ÿ�� ���� 
     * @return
     */
    protected Object[][] getCustTypes() {
    	return null;
    }
}
