package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;

public class Sso0210ParamVO extends ParameterVO {

	private String mandt;
	private String pspid;
	private String seq;
	private String seqpre;
	private String hogi;
	private String frzrqdat;
	private String tozrqdat;
	private String sman;
	
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
	
	
	
	public String getPspid() {
		return pspid;
	}
	public void setPspid(String pspid) {
		this.pspid = pspid;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getSeqpre() {
		return seqpre;
	}
	public void setSeqpre(String seqpre) {
		this.seqpre = seqpre;
	}
	public String getHogi() {
		return hogi;
	}
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}
	/**
	 * @return the frzrqdat
	 */
	public String getFrzrqdat() {
		return frzrqdat;
	}
	/**
	 * @param frzrqdat the frzrqdat to set
	 */
	public void setFrzrqdat(String frzrqdat) {
		this.frzrqdat = frzrqdat;
	}
	/**
	 * @return the tozrqdat
	 */
	public String getTozrqdat() {
		return tozrqdat;
	}
	/**
	 * @param tozrqdat the tozrqdat to set
	 */
	public void setTozrqdat(String tozrqdat) {
		this.tozrqdat = tozrqdat;
	}
	public String getSman() {
		return sman;
	}
	public void setSman(String sman) {
		this.sman = sman;
	}
	
}
