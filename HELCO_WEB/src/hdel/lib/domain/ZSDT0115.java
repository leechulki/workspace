package hdel.lib.domain;

import com.sap.domain.Lifnr;

public class ZSDT0115 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private Lifnr lifnr = new Lifnr();
	private String cvabr = "";
	private String cvabrt = "";
	private String email = "";
	private String delfg = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public Lifnr getLifnr() {
		return lifnr;
	}
	public void setLifnr(Lifnr lifnr) {
		this.lifnr = lifnr;
	}
	public String getCvabr() {
		return cvabr;
	}
	public void setCvabr(String cvabr) {
		this.cvabr = cvabr;
	}
	public String getCvabrt() {
		return cvabrt;
	}
	public void setCvabrt(String cvabrt) {
		this.cvabrt = cvabrt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDelfg() {
		return delfg;
	}
	public void setDelfg(String delfg) {
		this.delfg = delfg;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}