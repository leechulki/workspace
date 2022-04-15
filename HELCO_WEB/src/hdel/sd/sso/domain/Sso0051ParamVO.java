package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;


/**
 * ��纹��(Sso0060ParamVO) ParameterVO
 * @Comment 
 *     	- Sso0051C(��纹��) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2013.03.11  �輱ȣ  :  initial version 
 * @version 1.0
 */ 


public class Sso0051ParamVO extends ParameterVO {

	private String MANDT;	// CLIENT
	private String HOGI;	// ȣ���ȣ
	private String SEQ;	// ��������
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getHOGI() {
		return HOGI;
	}
	public void setHOGI(String hOGI) {
		HOGI = hOGI;
	}
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}
		
	
}
