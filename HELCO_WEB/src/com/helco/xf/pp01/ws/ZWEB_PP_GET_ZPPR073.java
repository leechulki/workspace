package com.helco.xf.pp01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;

public class ZWEB_PP_GET_ZPPR073 extends WebServiceStub {

	public ZWEB_PP_GET_ZPPR073() throws AxisFault {
		super();// TODO Auto-generated constructor stub
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[2][4];
    	obj[0] = new Object[]{
    			"ZPPS073"
    			, ZPPS073.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZPPS073"
				, "ZPPS073"
				, "item"
				, ZPPS073[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() {
		// TODO Auto-generated method stub
		return "ZWEB_PP_GET_ZPPR073";
	}

	@Override
	protected void initOperation(Object obj) {
		// TODO Auto-generated method stub
		OperationDesc oper = (OperationDesc)obj;
    	oper.addParameter(makeParam("I_ATYPE", PARAM_IN ));
    	oper.addParameter(makeParam("I_CHK", PARAM_IN ));
    	oper.addParameter(makeParam("I_CHKF", PARAM_IN ));
    	oper.addParameter(makeParam("I_FASPD", PARAM_IN ));
    	oper.addParameter(makeParam("I_FDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_ITEMNO", PARAM_IN ));
    	oper.addParameter(makeParam("I_PGBN", PARAM_IN ));
    	oper.addParameter(makeParam("I_RBT", PARAM_IN ));
    	oper.addParameter(makeParam("I_SGBN", PARAM_IN ));
    	oper.addParameter(makeParam("I_TASPD", PARAM_IN ));
    	oper.addParameter(makeParam("I_TDATE", PARAM_IN ));
    	oper.addParameter(makeParam("I_WGUBUN", PARAM_IN ));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZPPS073", ZPPS073[].class, "item" ));

	}

}
