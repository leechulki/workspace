package hdel.lib.domain;

import com.sap.domain.Vbeln;

public class ZSDT0129 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private Vbeln vbeln = new Vbeln();
	private Double kbetr = 0.0;
	private String delfg = "";
	private String reasn = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
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
	public Double getKbetr() {
		return kbetr;
	}
	public void setKbetr(Double kbetr) {
		this.kbetr = kbetr;
	}
	public String getDelfg() {
		return delfg;
	}
	public void setDelfg(String delfg) {
		this.delfg = delfg;
	}
	public String getReasn() {
		return reasn;
	}
	public void setReasn(String reasn) {
		this.reasn = reasn;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}