package hdel.lib.domain;

import com.sap.domain.Vbeln;

public class ZSDT1084 extends CommonDomain {
	private static final long serialVersionUID = -6788595853751521893L;

	private String mandt = "";
	private String zrqseq = "";
	private Vbeln vbeln = new Vbeln();
	private Integer seq = new Integer(0);
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZrqseq() {
		return zrqseq;
	}
	public void setZrqseq(String zrqseq) {
		this.zrqseq = zrqseq;
	}
	public Vbeln getVbeln() {
		return vbeln;
	}
	public void setVbeln(Vbeln vbeln) {
		this.vbeln = vbeln;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}