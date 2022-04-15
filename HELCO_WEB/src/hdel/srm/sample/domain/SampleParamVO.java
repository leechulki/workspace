package hdel.srm.sample.domain;

import hdel.lib.domain.ParameterVO;

public class SampleParamVO extends ParameterVO {

	private String mandt;
	private String lang;
	private String code1;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	
}
