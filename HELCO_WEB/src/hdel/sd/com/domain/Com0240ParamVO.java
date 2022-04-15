package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0240ParamVO extends ParameterVO {

	private String mandt;	  	// 
	private String code;	  	// ΔΪµε
	private String code_name; 	// Έν
	private String code_flag;
	private String lang;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	} 
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getCode_flag() {
		return code_flag;
	}
	public void setCode_flag(String code_flag) {
		this.code_flag = code_flag;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}
