package com.helco.xf.ps11.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHARS_BY_POSID extends WebServiceStub {

	public ZWEB_SD_CHARS_BY_POSID() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[8][8];
		obj[0] = new Object[]{
    			"BAPI_WBS_LIST_TAB"
    			, BAPI_WBS_LIST_TAB.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_BAPI_WBS_LIST_TAB"
				, "BAPI_WBS_LIST_TAB"
				, "item"
				, BAPI_WBS_LIST_TAB[].class };
    	obj[2] = new Object[]{
    			"CIF_T_DISP_T_CHR"
    			, CIF_T_DISP_T_CHR.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_CIF_T_DISP_T_CHR"
				, "CIF_T_DISP_T_CHR"
				, "item"
				, CIF_T_DISP_T_CHR[].class };
    	
    	obj[4] = new Object[]{
    			"ZWBS0004"
    			, ZWBS0004.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZWBS0004"
				, "ZWBS0004"
				, "item"
				, ZWBS0004[].class };
    	
    	obj[6] = new Object[]{
    			"BAPIRET2"
    			, BAPIRET2.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_BAPIRET2"
				, "BAPIRET2"
				, "item"
				, BAPIRET2[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHARS_BY_POSID";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;

		oper.addParameter(makeArrayParam("IT_CHARS", PARAM_INOUT, "TABLE_OF_CIF_T_DISP_T_CHR", CIF_T_DISP_T_CHR[].class, "item"));
		oper.addParameter(makeArrayParam("IT_POSID", PARAM_INOUT, "TABLE_OF_BAPI_WBS_LIST_TAB", BAPI_WBS_LIST_TAB[].class, "item"));
		oper.addParameter(makeParam("IV_PSPID", PARAM_IN));
		oper.addParameter(makeArrayParam("TT_BAPIRET2", PARAM_INOUT, "TABLE_OF_BAPIRET2", BAPIRET2[].class, "item"));
		oper.addParameter(makeArrayParam("TT_CHARS", PARAM_INOUT, "TABLE_OF_ZWBS0004", ZWBS0004[].class, "item"));
		
	}
}
