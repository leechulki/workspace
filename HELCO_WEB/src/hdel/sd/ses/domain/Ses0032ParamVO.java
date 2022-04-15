package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0032ParamVO extends ParameterVO {

	private String lang;
	private String mandt;
	private String zpym;
	private String zkunnr;
	private String spart;
	private String zagnt;
	private String kunnr;
	private String sonnr;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZpym() {
		return zpym;
	}
	public void setZpym(String zpym) {
		this.zpym = zpym;
	}
	public String getZkunnr() {
		return zkunnr;
	}
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
	public String getSpart() {
		return spart;
	}
	public void setSpart(String spart) {
		this.spart = spart;
	}
	public String getZagnt() {
		return zagnt;
	}
	public void setZagnt(String zagnt) {
		this.zagnt = zagnt;
	}
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	public String getSonnr() {
		return sonnr;
	}
	public void setSonnr(String sonnr) {
		this.sonnr = sonnr;
	}
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
}
