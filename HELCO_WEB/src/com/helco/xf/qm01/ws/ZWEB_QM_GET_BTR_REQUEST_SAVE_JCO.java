package com.helco.xf.qm01.ws;

import java.util.List;
import com.helco.xf.comm.JCOMgr;

/**
 * 출장 요청 정보 등록 
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
     * Operation 초기화 - 상속받아 구현 
     * @return
     */
    protected void initOperation(Object oper1) {
    	List list = (List) oper1;
    	list.add("O_TAB");
    }
}
