package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0171ParamVO extends ParameterVO {

	private static final long serialVersionUID = 1L;
	
	private String mandt;
	private String qtnum;
	private String qtser;
	private String qtseq;
	private String ukurs;
	private String fcurr;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getQtseq() {
		return qtseq;
	}
	public void setQtseq(String qtseq) {
		this.qtseq = qtseq;
	}
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public String getQtser() {
		return qtser;
	}
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}
	/**
	 * @return the ukurs
	 */
	public String getUkurs() {
		return ukurs;
	}
	/**
	 * @param ukurs the ukurs to set
	 */
	public void setUkurs(String ukurs) {
		this.ukurs = ukurs;
	}
	/**
	 * @return the fcurr
	 */
	public String getFcurr() {
		return fcurr;
	}
	/**
	 * @param fcurr the fcurr to set
	 */
	public void setFcurr(String fcurr) {
		this.fcurr = fcurr;
	}
}
