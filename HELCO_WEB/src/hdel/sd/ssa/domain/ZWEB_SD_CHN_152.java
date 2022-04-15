package hdel.sd.ssa.domain;


import hdel.sd.com.domain.RANGE_C10;
import hdel.sd.com.domain.RANGE_C30;

import org.apache.axis.AxisFault;
import org.apache.axis.description.OperationDesc;

import com.helco.xf.comm.WebServiceStub;
import com.helco.xf.comm.ZQMSEMSG;


/**
 * ����ä�� �� �� ä����Ȳ(û����) ��ȸ (ZWEB_SD_CHN_152) WebServiceStub
 * @Comment 
 *     	- Ssa0020C(����ä�� �� �� ä����Ȳ(û����)) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */
 

public class ZWEB_SD_CHN_152 extends WebServiceStub {

	public ZWEB_SD_CHN_152() throws AxisFault {
		super();
	}

	@Override
	protected Object[][] getCustTypes() {
    	Object[][] obj = new Object[10][10];
    	obj[0] = new Object[]{
    			"ZSDS0034"
    			, ZSDS0034.class   	};
    	obj[1] = new Object[]{
				"TABLE_OF_ZSDS0034"
				, "ZSDS0034"
				, "item"
				, ZSDS0034[].class };
    	obj[2] = new Object[]{
    			"RANGE_C10"
    			, RANGE_C10.class
    	}; 
    	obj[3] = new Object[]{
				"TABLE_OF_RANGE_C10"
				, "RANGE_C10"
				, "item"
				, RANGE_C10[].class 
		}; 
    	obj[4] = new Object[]{
    			"RANGE_C30"
    			, RANGE_C30.class
    	}; 
    	obj[5] = new Object[]{
				"TABLE_OF_ZRANGE_C30"
				, "RANGE_C30"
				, "item"
				, RANGE_C30[].class 
		}; 
    	obj[6] = new Object[]{
    			"ZSDS0036"
    			, ZSDS0036.class   	};
    	obj[7] = new Object[]{
				"TABLE_OF_ZSDS0036"
				, "ZSDS0036"
				, "item"
				, ZSDS0036[].class };
    	obj[8] = new Object[]{
    			"ZPSSEMSG"
    			, ZQMSEMSG.class  	};
    	obj[9] = new Object[]{
				"TABLE_OF_ZPSSEMSG"
				, "ZPSSEMSG"
				, "item"
				, ZQMSEMSG[].class };

    	return obj;
	}

	@Override
	protected String getOperName() {
		return "ZWEB_SD_CHN_152";
	}

	@Override
	protected void initOperation(Object obj) {
		OperationDesc oper = (OperationDesc)obj;
		
		oper.addParameter(makeParam("I_FR_AUART"	,PARAM_IN));  // ���������ڵ�_FR                                     
		oper.addParameter(makeParam("I_FR_EMPL"		,PARAM_IN));  // ������ڵ�_FR                                       
		oper.addParameter(makeParam("I_FR_PAYER"	,PARAM_IN));  // �ŷ����ڵ�_FR                                       
		oper.addParameter(makeParam("I_FR_PJT"		,PARAM_IN));  // ������Ʈ_FR                                         
		oper.addParameter(makeParam("I_FR_VB"		,PARAM_IN));  // �μ��ڵ�_FR                                         
		oper.addParameter(makeParam("I_FR_VG"		,PARAM_IN));  // ���ڵ�_FR                                           
		oper.addParameter(makeParam("I_JOB_GBN"		,PARAM_IN));  // �������� (1:�̼�, 2:�ε�, 3:���ݰ�ȹ)               
		oper.addParameter(makeParam("I_PRT_GBN"		,PARAM_IN));  // ����Ʈ��ı��� (1:����/�ؿܿ���, 2:��������, 3:�ε�)
		oper.addParameter(makeParam("I_TO_AUART"	,PARAM_IN));  // ���������ڵ�_TO                                     
		oper.addParameter(makeParam("I_TO_EMPL"		,PARAM_IN));  // ������ڵ�_TO                                       
		oper.addParameter(makeParam("I_TO_PAYER"	,PARAM_IN));  // �ŷ����ڵ�_TO                                       
		oper.addParameter(makeParam("I_TO_PJT"		,PARAM_IN));  // ������Ʈ_TO                                         
		oper.addParameter(makeParam("I_TO_VB"		,PARAM_IN));  // �μ��ڵ�_TO                                         
		oper.addParameter(makeParam("I_TO_VG"		,PARAM_IN));  // ���ڵ�_TO                                           
		oper.addParameter(makeParam("I_YEARM"		,PARAM_IN));  // ���س��        
    	oper.addParameter(makeArrayParam("O_ETAB" , PARAM_INOUT, "TABLE_OF_ZPSSEMSG"	, ZQMSEMSG[].class	, "item")); 
    	oper.addParameter(makeArrayParam("S_KIDNO", PARAM_INOUT, "TABLE_OF_ZRANGE_C30"	, RANGE_C30[].class	, "item"));
    	oper.addParameter(makeArrayParam("S_KUNNR", PARAM_INOUT, "TABLE_OF_RANGE_C10"	, RANGE_C10[].class	, "item")); 
    	oper.addParameter(makeArrayParam("T_ITAB" , PARAM_INOUT, "TABLE_OF_ZSDS0034"	, ZSDS0034[].class	, "item"));
    	oper.addParameter(makeArrayParam("T_ITAB2", PARAM_INOUT, "TABLE_OF_ZSDS0036"	, ZSDS0036[].class	, "item"));
	}
}
