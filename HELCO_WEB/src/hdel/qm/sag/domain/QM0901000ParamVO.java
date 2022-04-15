package hdel.qm.sag.domain;

import hdel.lib.domain.ParameterVO;

public class QM0901000ParamVO extends ParameterVO {

	private String mandt;
	private String frqtdat;
	private String toqtdat;
	private String lifnr;
	private String zrqsta;
	private String purman;
	private String qcman;
	private String gubn;
			
	public String getGubn() {
		return gubn;
	}

	public void setGubn(String gubn) {
		this.gubn = gubn;
	}

	public String getPurman() {
		return purman;
	}

	public String getQcman() {
		return qcman;
	}

	public void setPurman(String purman) {
		this.purman = purman;
	}

	public void setQcman(String qcman) {
		this.qcman = qcman;
	}

	private String uuser;
	
	public String getZrqsta() {
		return zrqsta;
	}

	public void setZrqsta(String zrqsta) {
		this.zrqsta = zrqsta;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getMandt() {
		return mandt;
	}

	public String getFrqtdat() {
		return frqtdat;
	}

	public String getToqtdat() {
		return toqtdat;
	}

	public String getUuser() {
		return uuser;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}

	public void setFrqtdat(String frqtdat) {
		this.frqtdat = frqtdat;
	}

	public void setToqtdat(String toqtdat) {
		this.toqtdat = toqtdat;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}		
	
}