package hdel.egis.user.domain;

import hdel.lib.domain.ParameterVO;

public class UserParamVO extends ParameterVO {	
	
	private String mandt;
	private String userid;
	private String lang;
	private String sysid;
	
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}
