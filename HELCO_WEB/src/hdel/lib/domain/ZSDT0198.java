package hdel.lib.domain;

import com.sap.domain.Lifnr;

public class ZSDT0198 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private String qtnum = "";
	private Lifnr lifnr = new Lifnr();
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public Lifnr getLifnr() {
		return lifnr;
	}
	public void setLifnr(Lifnr lifnr) {
		this.lifnr = lifnr;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}