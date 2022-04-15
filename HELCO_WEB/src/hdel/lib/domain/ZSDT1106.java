package hdel.lib.domain;

import java.math.BigDecimal;

public class ZSDT1106 extends CommonDomain {
	private static final long serialVersionUID = -3960318633530828556L;

	private String mandt = "";
	private String bsyym = "";
	private String sprno = "";
	private String pysts = "";
	private BigDecimal incamt = new BigDecimal(0);
	private Integer srtsc = new Integer(0);
	private Integer crtsc = new Integer(0);
	private Float salesrt = new Float(0);
	private Float costrt = new Float(0);
	private Float cltrt = new Float(0);
	private BigDecimal pdamt = new BigDecimal(0);
	private BigDecimal tmamt = new BigDecimal(0);
	private BigDecimal blamt = new BigDecimal(0);
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getBsyym() {
		return bsyym;
	}
	public void setBsyym(String bsyym) {
		this.bsyym = bsyym;
	}
	public String getSprno() {
		return sprno;
	}
	public void setSprno(String sprno) {
		this.sprno = sprno;
	}
	public String getPysts() {
		return pysts;
	}
	public void setPysts(String pysts) {
		this.pysts = pysts;
	}
	public BigDecimal getIncamt() {
		return incamt;
	}
	public void setIncamt(BigDecimal incamt) {
		this.incamt = incamt;
	}
	public Integer getSrtsc() {
		return srtsc;
	}
	public void setSrtsc(Integer srtsc) {
		this.srtsc = srtsc;
	}
	public Integer getCrtsc() {
		return crtsc;
	}
	public void setCrtsc(Integer crtsc) {
		this.crtsc = crtsc;
	}
	public Float getSalesrt() {
		return salesrt;
	}
	public void setSalesrt(Float salesrt) {
		this.salesrt = salesrt;
	}
	public Float getCostrt() {
		return costrt;
	}
	public void setCostrt(Float costrt) {
		this.costrt = costrt;
	}
	public Float getCltrt() {
		return cltrt;
	}
	public void setCltrt(Float cltrt) {
		this.cltrt = cltrt;
	}
	public BigDecimal getPdamt() {
		return pdamt;
	}
	public void setPdamt(BigDecimal pdamt) {
		this.pdamt = pdamt;
	}
	public BigDecimal getTmamt() {
		return tmamt;
	}
	public void setTmamt(BigDecimal tmamt) {
		this.tmamt = tmamt;
	}
	public BigDecimal getBlamt() {
		return blamt;
	}
	public void setBlamt(BigDecimal blamt) {
		this.blamt = blamt;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}