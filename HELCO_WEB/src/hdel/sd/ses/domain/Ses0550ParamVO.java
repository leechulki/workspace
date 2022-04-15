package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0550ParamVO extends ParameterVO {

	private String mandt;
	private String lang;
	private String matnr;
	
	/**
	 * @return the mandt
	 */
	public String getMandt() {
		return mandt;
	}
	/**
	 * @param mandt the mandt to set
	 */
	public void setMandt(String mandt) {
		this.mandt = mandt;
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
	/**
	 * @return the matnr
	 */
	public String getMatnr() {
		return matnr;
	}
	/**
	 * @param matnr the matnr to set
	 */
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
}
