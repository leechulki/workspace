package com.helco.xf.qm01.ws;

import java.util.List;
import com.helco.xf.comm.JCOMgr;

/**
 * ���� ��û ���� ��� 
 */
public class ZWEB_QM_GET_BTR_REQUEST_SAVE_JCO extends JCOMgr {

	public ZWEB_QM_GET_BTR_REQUEST_SAVE_JCO() {
		super();
	}
	
	 /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZWEB_QM_GET_BTR_REQUEST_SAVE";
    }
    
	/**
     * Operation �ʱ�ȭ - ��ӹ޾� ���� 
     * @return
     */
    protected void initOperation(Object oper1) {
    	List list = (List) oper1;
    	list.add("O_TAB");
    }
}
