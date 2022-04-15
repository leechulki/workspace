package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_PP_GET_ZPPR007 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR007() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		Object[][] obj = new Object[8][8];
    	obj[0] = new Object[]{
    			"ZPPSITAB1"
    			, ZPPSITAB1.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPSITAB1"
				, "ZPPSITAB1"
				, "item"
				, ZPPSITAB1[].class };
    	obj[2] = new Object[]{
    			"ZPPSITAB2"
    			, ZPPSITAB2.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPPSITAB2"
				, "ZPPSITAB2"
				, "item"
				, ZPPSITAB2[].class };
    	obj[4] = new Object[]{
    			"ZPPSITAB3"
    			, ZPPSITAB3.class
    	};
    	obj[5] = new Object[]{
				"TABLE_OF_ZPPSITAB3"
				, "ZPPSITAB3"
				, "item"
				, ZPPSITAB3[].class };
    	obj[6] = new Object[]{
    			"ZPPS007A"
    			, ZPPS007A.class
    	};
    	obj[7] = new Object[]{
				"TABLE_OF_ZPPS007A"
				, "ZPPS007A"
				, "item"
				, ZPPS007A[].class };
	
    	return obj;
	}
 
	@Override
	protected String getOperName() {
		return "ZWEB_PP_GET_ZPPR007";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_ARBPL" , PARAM_IN));
		oper.addParameter(makeParam("I_AUFNR" , PARAM_IN));		
		oper.addParameter(makeParam("I_FDATE" , PARAM_IN));
		oper.addParameter(makeParam("I_FLAG"  , PARAM_IN));
		oper.addParameter(makeParam("I_GUBUN" , PARAM_IN));
		oper.addParameter(makeParam("I_ITEMNO", PARAM_IN));
		oper.addParameter(makeParam("I_PART"  , PARAM_IN));
		oper.addParameter(makeParam("I_POSID" , PARAM_IN));
		oper.addParameter(makeParam("I_RBT"   , PARAM_IN));

		oper.addParameter(makeArrayParam("I_TAB1", PARAM_INOUT, "TABLE_OF_ZPPSITAB1", ZPPSITAB1[].class, "item"));
		oper.addParameter(makeArrayParam("I_TAB2", PARAM_INOUT, "TABLE_OF_ZPPSITAB2", ZPPSITAB2[].class, "item"));		
		oper.addParameter(makeArrayParam("I_TAB3", PARAM_INOUT, "TABLE_OF_ZPPSITAB3", ZPPSITAB3[].class, "item"));
		
		oper.addParameter(makeParam("I_TDATE" , PARAM_IN));
		
		oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS007A", ZPPS007A[].class, "item"));
	}
}
