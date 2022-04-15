package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;


/**
 * 납품처목록 조회 (Com0190) CommonDomain
 * @Comment 
 *     	- Com0190C(납품처목록 조회) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public class Com0190 extends CommonDomain {
 
	private String CODE;			// 납품처코드
	private String CODE_NAME; 		// 납품처명 
	private String INFO;			// 납품처정보  
	
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
	public String getINFO() {
		return INFO;
	}
	public void setINFO(String INFO) {
		this.INFO = INFO;
	} 
}
