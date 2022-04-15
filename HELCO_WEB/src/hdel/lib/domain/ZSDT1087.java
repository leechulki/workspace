package hdel.lib.domain;

import com.sap.domain.Datum;

public class ZSDT1087 extends CommonDomain {
	private static final long serialVersionUID = -3960318633530828556L;

	private String mandt = "";
	private Integer seqno = 0;
	private Datum datbi = new Datum();
	private Datum datab = new Datum();
	private String crtsc = "";
	private Double crtfr = 0.0;
	private Double crtto = 0.0;
	private String delfg = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public Integer getSeqno() {
		return seqno;
	}
	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
	public Datum getDatbi() {
		return datbi;
	}
	public void setDatbi(Datum datbi) {
		this.datbi = datbi;
	}
	public Datum getDatab() {
		return datab;
	}
	public void setDatab(Datum datab) {
		this.datab = datab;
	}
	public String getCrtsc() {
		return crtsc;
	}
	public void setCrtsc(String crtsc) {
		this.crtsc = crtsc;
	}
	public Double getCrtfr() {
		return crtfr;
	}
	public void setCrtfr(Double crtfr) {
		this.crtfr = crtfr;
	}
	public Double getCrtto() {
		return crtto;
	}
	public void setCrtto(Double crtto) {
		this.crtto = crtto;
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