package hdel.lib.domain;

import org.apache.commons.lang.math.NumberUtils;

public class ZSDT0168 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private String kunnr;
	private String entdt;
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public String getKunnr() {
		return (kunnr==null || kunnr.equals("")) ? "0000000000" : kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = (kunnr==null || kunnr.equals("")) ? "0000000000" : kunnr;
	}
	public String getEntdt() {
		return String.format("%08d", NumberUtils.toInt(entdt, 0));
	}
	public void setEntdt(String entdt) {
		this.entdt = String.format("%08d", NumberUtils.toInt(entdt, 0));
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}