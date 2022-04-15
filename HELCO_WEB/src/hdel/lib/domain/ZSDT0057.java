package hdel.lib.domain;

import com.sap.domain.Datum;
import com.sap.domain.SapBool;
import com.sap.domain.Vbeln;

public class ZSDT0057 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private Vbeln vbeln = new Vbeln();
	private SapBool dpclr = new SapBool();
	private Datum dpcpd = new Datum();
	private SapBool canfg = new SapBool();
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
	public SapBool getDpclr() {
		return dpclr;
	}
	public void setDpclr(SapBool dpclr) {
		this.dpclr = dpclr;
	}
	public Datum getDpcpd() {
		return dpcpd;
	}
	public void setDpcpd(Datum dpcpd) {
		this.dpcpd = dpcpd;
	}
	public SapBool getCanfg() {
		return canfg;
	}
	public void setCanfg(SapBool canfg) {
		this.canfg = canfg;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}