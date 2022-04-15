package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;


/**
 * 사양복사(Sso0060ParamVO) ParameterVO
 * @Comment 
 *     	- Sso0051C(사양복사) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2013.03.11  김선호  :  initial version 
 * @version 1.0
 */ 


public class Sso0051ParamVO extends ParameterVO {

	private String MANDT;	// CLIENT
	private String HOGI;	// 호기번호
	private String SEQ;	// 변경차수
	
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
