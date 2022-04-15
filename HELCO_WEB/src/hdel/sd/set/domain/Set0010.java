package hdel.sd.set.domain;

import java.math.BigDecimal;
import java.util.List;

import hdel.lib.domain.CommonDomain;

public class Set0010 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt;
	private String srtsc;
	private String srtfr;
	private String srtto;
	private String crtsc;
	private String crtfr;
	private String crtto;
	private BigDecimal inctv;
	private List<Set0010> lstCrtSection;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getSrtsc() {
		return srtsc;
	}
	public void setSrtsc(String srtsc) {
		this.srtsc = srtsc;
	}
	public String getSrtfr() {
		return srtfr;
	}
	public void setSrtfr(String srtfr) {
		this.srtfr = srtfr;
	}
	public String getSrtto() {
		return srtto;
	}
	public void setSrtto(String srtto) {
		this.srtto = srtto;
	}
	public String getCrtsc() {
		return crtsc;
	}
	public void setCrtsc(String crtsc) {
		this.crtsc = crtsc;
	}
	public String getCrtfr() {
		return crtfr;
	}
	public void setCrtfr(String crtfr) {
		this.crtfr = crtfr;
	}
	public String getCrtto() {
		return crtto;
	}
	public void setCrtto(String crtto) {
		this.crtto = crtto;
	}
	public BigDecimal getInctv() {
		return inctv;
	}
	public void setInctv(BigDecimal inctv) {
		this.inctv = inctv;
	}
	public List<Set0010> getLstCrtSection() {
		return lstCrtSection;
	}
	public void setLstCrtSection(List<Set0010> lstCrtSection) {
		this.lstCrtSection = lstCrtSection;
	}
}