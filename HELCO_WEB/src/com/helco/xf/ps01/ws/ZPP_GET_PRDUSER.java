package com.helco.xf.ps01.ws;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;
import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZPP_GET_PRDUSER extends WebServiceStub {

	public ZPP_GET_PRDUSER() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
		return null;
	}
 
	@Override
	protected String getOperName() {
		return "ZPP_GET_PRDUSER";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc) obj;
		oper.addParameter(makeParam("I_POSID", PARAM_IN));
		
		oper.addParameter(makeParam("O_PUSER", PARAM_OUT));
		oper.addParameter(makeParam("O_PUID", PARAM_OUT));
		//oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
		//oper.addParameter(makeArrayParam("O_TAB", PARAM_INOUT, "TABLE_OF_ZQMS028", ZQMS028[].class, "item"));
		//oper.addParameter(makeArrayParam("O_TEXT", PARAM_INOUT, "TABLE_OF_TLINE", TLINE[].class, "item"));	
		}
}
