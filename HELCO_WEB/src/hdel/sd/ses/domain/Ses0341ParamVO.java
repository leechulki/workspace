package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0341ParamVO extends ParameterVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mandt;
	private String zconnr;
	private String pspid;
	private String zbstdkFr;
	private String zbstdkTo;
	private String costzseqFr;
	private String costzseqTo;
	private String lang;
	
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZconnr() {
		return zconnr;
	}
	public void setZconnr(String zconnr) {
		this.zconnr = zconnr;
	}
	public String getPspid() {
		return pspid;
	}
	public void setPspid(String pspid) {
		this.pspid = pspid;
	}
	public String getZbstdkFr() {
		return zbstdkFr;
	}
	public void setZbstdkFr(String zbstdkFr) {
		this.zbstdkFr = zbstdkFr;
	}
	public String getZbstdkTo() {
		return zbstdkTo;
	}
	public void setZbstdkTo(String zbstdkTo) {
		this.zbstdkTo = zbstdkTo;
	}
	public String getCostzseqFr() {
		return costzseqFr;
	}
	public void setCostzseqFr(String costzseqFr) {
		this.costzseqFr = costzseqFr;
	}
	public String getCostzseqTo() {
		return costzseqTo;
	}
	public void setCostzseqTo(String costzseqTo) {
		this.costzseqTo = costzseqTo;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

}
