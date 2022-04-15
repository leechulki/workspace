package hdel.sd.sso.domain;

import com.sap.domain.Kunnr;
import com.sap.domain.Vbeln;

import hdel.lib.domain.CommonDomain;

public class Sso0020Partner extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private Vbeln vbeln = new Vbeln();
	private String parvw = "";
	private Kunnr kunnr = new Kunnr();
	private String email = "";

	public Sso0020Partner() {
	}
	public Sso0020Partner(String mandt) {
		this.mandt = mandt;
	}

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public Vbeln getVbeln() {
		return vbeln;
	}
	public void setVbeln(Vbeln vbeln) {
		this.vbeln = vbeln;
	}
	public String getParvw() {
		return parvw;
	}
	public void setParvw(String parvw) {
		this.parvw = parvw;
	}
	public Kunnr getKunnr() {
		return kunnr;
	}
	public void setKunnr(Kunnr kunnr) {
		this.kunnr = kunnr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}