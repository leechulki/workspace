package hdel.sd.ssa.domain;

import hdel.lib.domain.CommonDomain;


/**
 * 세금계산서C청구현황(Ssa0040) CommonDomain
 * @Comment 
 *     	- Ssa0040C(세금계산서C청구현황) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.13  khs  :  initial version 
 * @version 1.0
 */ 


public class Ssa0040 extends CommonDomain {
 
	private String mandt;
	private String acode;			// 오더유형 
	private String acode_name;	// 오더유형명
	private String scode;			// 사유코드 
	private String scode_t;			// 사유명
	 
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getAcode() {
		return acode;
	}
	public void setAcode(String acode) {
		this.acode = acode;
	}
	public String getAcode_name() {
		return acode_name;
	}
	public void setAcode_name(String acode_name) {
		this.acode_name = acode_name;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	} 	 
	public String getScode_t() {
		return scode_t;
	}
	public void setScode_t(String scode_t) {
		this.scode_t = scode_t;
	} 	
}
