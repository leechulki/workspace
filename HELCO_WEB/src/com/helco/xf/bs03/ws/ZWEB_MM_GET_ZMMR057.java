package com.helco.xf.bs03.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_MM_GET_ZMMR057 extends WebServiceStub 
{

	public ZWEB_MM_GET_ZMMR057() throws AxisFault 
	{
		super();
	}

	@Override
	protected Object[][] getCustTypes() 
	{
		Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZMMSEMSG"
    			, ZQMSEMSG.class
    	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZMMSEMSG"
				, "ZMMSEMSG" 
				, "item"
				, ZQMSEMSG[].class 
		};
    	obj[2] = new Object[]{
    			"ZMMS057"
    			, ZMMS057.class
    	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZMMS057"
				, "ZMMS057"
				, "item"
				, ZMMS057[].class };
	
    	return obj;
	}

	@Override
	protected String getOperName() 
	{
		return "ZWEB_MM_GET_ZMMR057";
	}

	@Override
	protected void initOperation(Object obj) 
	{
		OperationDesc oper = (OperationDesc)obj;
		oper.addParameter(makeParam("I_ALTKN", PARAM_IN ));
    	oper.addParameter(makeParam("I_ERDAT", PARAM_IN ));
    	oper.addParameter(makeParam("I_KTOKK", PARAM_IN ));
    	oper.addParameter(makeParam("I_KUNNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_LIFNR", PARAM_IN ));
    	oper.addParameter(makeParam("I_NAME1", PARAM_IN ));
    	oper.addParameter(makeParam("I_SORTL", PARAM_IN ));
		oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZMMSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZMMS057", ZMMS057[].class, "item" ));

	}

}
