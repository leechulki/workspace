package hdel.lib.domain;

import com.sap.domain.Vbeln;

public class ZSDT1074 extends CommonDomain {
	private static final long serialVersionUID = -3960318633530828556L;

	private String mandt="";
	private Vbeln vbeln = new Vbeln();
	private String lh="";
	private String kunaprv="";
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
	public String getLh() {
		return lh;
	}
	public void setLh(String lh) {
		this.lh = "X".equals(lh) ? lh : "";
	}
	public String getKunaprv() {
		return kunaprv;
	}
	public void setKunaprv(String kunaprv) {
		this.kunaprv = "X".equals(kunaprv) ? kunaprv : "";
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}

}