package com.helco.xf.qm01.ws;

import java.util.List;
import com.helco.xf.comm.JCOMgr;

public class ZWEB_QM_GET_BTR_REQUESTED_LIST_JCO extends JCOMgr {

	public ZWEB_QM_GET_BTR_REQUESTED_LIST_JCO() {
		super();
	}
	
	/**
     * Operation 초기화 - 상속받아 구현 
     * @return
     */
    protected void initOperation(Object oper1) {
    	List list = (List) oper1;
    	list.add("I_EINDTF");
    	list.add("I_EINDTT");
    	list.add("I_INVNRF");
    	list.add("I_INVNRT");
    	list.add("I_LIFNR");
    	list.add("I_PACFDTF");
    	list.add("I_PACFDTT");
    	list.add("I_PAPRDTF");
    	list.add("I_PAPRDTT");
    	list.add("I_PARQDTF");
    	list.add("I_PARQDTT");
    	list.add("I_RPHOGIF");
    	list.add("I_RPHOGIT");
    	
    	list.add("I_STATUS2");
    	list.add("I_STATUS3");
    	list.add("I_STATUS4");
    	list.add("I_STATUS5");
    }
    
    /**
     * Operation Name
     * @return
     */
    protected String getOperName() {
    	return "ZWEB_QM_GET_BTR_REQUESTED_LIST";
    }
}
