package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0085ParamVO extends ParameterVO {

	private String mandt;
	private String zdev;
	private String zgnm;
	private String gsnam;
	private String adr;
//----< 2017.01.17 판정일자 조회조건 추가 by mj.Shin Start	
	private String fr_zapdat;
	private String to_zapdat;
//----< 2017.01.17 판정일자 조회조건 추가 by mj.Shin End
	private String sid;
	

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZdev() {
		return zdev;
	}
	public void setZdev(String zdev) {
		this.zdev = zdev;
	}
	public String getZgnm() {
		return zgnm;
	}
	public void setZgnm(String zgnm) {
		this.zgnm = zgnm;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	public String getAdr() {
		return adr;
	}
	public void setAdr(String adr) {
		this.adr = adr;
	}
	/**
	 * @return the fr_zapdat
	 */
	public String getFr_zapdat() {
		return fr_zapdat;
	}
	/**
	 * @param fr_zapdat the fr_zapdat to set
	 */
	public void setFr_zapdat(String fr_zapdat) {
		this.fr_zapdat = fr_zapdat;
	}
	/**
	 * @return the to_zapdat
	 */
	public String getTo_zapdat() {
		return to_zapdat;
	}
	/**
	 * @param to_zapdat the to_zapdat to set
	 */
	public void setTo_zapdat(String to_zapdat) {
		this.to_zapdat = to_zapdat;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
}
