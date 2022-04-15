package hdel.sd.ssa.domain;


import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;


/**
 * ����ä�� �� �� ä�� ��Ȳ(û��) _������װ��� (ZWEB_SD_CHN_152_INGT) WebServiceStub
 * @Comment 
 *     	- Ssa0021C(����ä�� �� �� ä�� ��Ȳ(û��) _������װ���) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */
 

public class ZWEB_SD_CHN_152_INGT extends WebServiceStub {

	public ZWEB_SD_CHN_152_INGT() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[4][4];
    	obj[0] = new Object[]{
    			"ZSDS0042"
    			, ZSDS0042.class   	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0042"
				, "ZSDS0042"
				, "item"
				, ZSDS0042[].class };  
    	obj[2] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class  	};
    	obj[3] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_152_INGT";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		            
		oper.addParameter(makeParam		("C_INGTEXT"	, PARAM_INOUT	));  // ������� ���
		oper.addParameter(makeParam		("I_BELNR"		, PARAM_IN		));  // û����ȣ
		oper.addParameter(makeParam		("I_GUBUN"		, PARAM_IN		));  // ����(Q:��ȸ, S:����)        
		oper.addParameter(makeParam		("I_KIDNO"		, PARAM_IN		));  // ������Ʈ��ȣ        
		oper.addParameter(makeParam		("I_KUNNR"		, PARAM_IN		));  // �ŷ�����ȣ        
    	oper.addParameter(makeArrayParam("O_ETAB" 		, PARAM_INOUT, "TABLE_OF_ZPSSEMSG", ZQMSEMSG[].class, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB"		, PARAM_INOUT, "TABLE_OF_ZSDS0042", ZSDS0042[].class, "item"));
    	 
	}
	
}
