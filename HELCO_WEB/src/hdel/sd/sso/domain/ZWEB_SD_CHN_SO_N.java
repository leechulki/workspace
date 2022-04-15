package hdel.sd.sso.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;

public class ZWEB_SD_CHN_SO_N extends WebServiceStub {

	public ZWEB_SD_CHN_SO_N() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[16][16];
    	obj[0] = new Object[]{"ZSDT0092", ZSDT0092.class};
    	obj[1] = new Object[]{"TABLE_OF_ZSDT0092", "ZSDT0092", "item", ZSDT0092[].class };
    	
    	obj[2] = new Object[]{"ZSDT0093", ZSDT0093.class};
    	obj[3] = new Object[]{"TABLE_OF_ZSDT0093", "ZSDT0093", "item", ZSDT0093[].class };

    	obj[4] = new Object[]{"ZSDT0090", ZSDT0090.class};
    	obj[5] = new Object[]{"TABLE_OF_ZSDT0090", "ZSDT0090", "item", ZSDT0090[].class };

    	obj[6] = new Object[]{"ZPSSEMSG", ZQMSEMSG.class};
    	obj[7] = new Object[]{"TABLE_OF_ZPSSEMSG", "ZPSSEMSG", "item", ZQMSEMSG[].class };

    	obj[8] = new Object[]{"ZSDT0091", ZSDT0091.class};
    	obj[9] = new Object[]{"TABLE_OF_ZSDT0091", "ZSDT0091", "item", ZSDT0091[].class };

    	obj[10] = new Object[]{"ZSDT0094", ZSDT0094.class};
    	obj[11] = new Object[]{"TABLE_OF_ZSDT0094", "ZSDT0094", "item", ZSDT0094[].class };

    	obj[12] = new Object[]{"ZSDT0094", ZSDT0094.class};
    	obj[13] = new Object[]{"TABLE_OF_ZSDT0094", "ZSDT0094", "item", ZSDT0094[].class };

    	obj[14] = new Object[]{"ZSDS0063", ZSDS0063.class};
    	obj[15] = new Object[]{"TABLE_OF_ZSDS0063", "ZSDS0063", "item", ZSDS0063[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_SO_N";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;


    	oper.addParameter(makeArrayParam("B1_ITAB", PARAM_INOUT, "TABLE_OF_ZSDT0092", ZSDT0092[].class, "item"));
    	oper.addParameter(makeArrayParam("B2_ITAB", PARAM_INOUT, "TABLE_OF_ZSDT0093", ZSDT0093[].class, "item"));
    	oper.addParameter(makeArrayParam("HD_ITAB", PARAM_INOUT, "TABLE_OF_ZSDT0090", ZSDT0090[].class, "item"));
		oper.addParameter(makeParam("I_CMD",PARAM_IN));
		oper.addParameter(makeParam("I_FR_SO",PARAM_IN));
		oper.addParameter(makeParam("I_SEQ",PARAM_IN));
    	oper.addParameter(makeArrayParam("O_ETAB", PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("PN_ITAB", PARAM_INOUT, "TABLE_OF_ZSDT0091", ZSDT0091[].class, "item"));
    	oper.addParameter(makeArrayParam("S1_ITAB", PARAM_INOUT, "TABLE_OF_ZSDT0094", ZSDT0094[].class, "item"));
    	oper.addParameter(makeArrayParam("S2_ITAB", PARAM_INOUT, "TABLE_OF_ZSDT0094", ZSDT0094[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB", PARAM_INOUT, "TABLE_OF_ZSDS0063", ZSDS0063[].class, "item"));
	}
}
