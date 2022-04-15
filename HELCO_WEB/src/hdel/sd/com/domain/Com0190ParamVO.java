package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;


/**
 * 납품처목록 조회 (Com0190ParamVO) ParameterVO
 * @Comment 
 *     	- Com0190C(납품처목록 조회) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public class Com0190ParamVO extends ParameterVO {

	private String MANDT;			// CLIENT
	private String CODE;			// 납품처코드
	private String CODE_NAME; 		// 납품처명 
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String MANDT) {
		this.MANDT = MANDT;
	} 

	public String getCODE() {
		return CODE;
	}
	public void setCODE(String CODE) {
		this.CODE = CODE;
	} 	
	public String getCODE_NAME() {
		return CODE_NAME;
	}
	public void setCODE_NAME(String CODE_NAME) {
		this.CODE_NAME = CODE_NAME;
	} 
}
