package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0340 extends CommonDomain {

	private String mandt;
	private String zconnr;
	private String atnam;
	private String atbez;
	private String atwrt;
	private String spec;
	private String zqty;
	private String zuam;
	private String zamt;
	private String zrmk;
	private int rowcnt;

	public String userid;  // ÀÛ¾÷ÀÚID

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZconnr() {
		return zconnr;
	}
	public void setZconnr(String zconnr) {
		this.zconnr = zconnr;
	}
	public String getAtnam() {
		return atnam;
	}
	public void setAtnam(String atnam) {
		this.atnam = atnam;
	}
	public String getAtbez() {
		return atbez;
	}
	public void setAtbez(String atbez) {
		this.atbez = atbez;
	}
	public String getAtwrt() {
		return atwrt;
	}
	public void setAtwrt(String atwrt) {
		this.atwrt = atwrt;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getZqty() {
		return zqty;
	}
	public void setZqty(String zqty) {
		this.zqty = zqty;
	}
	public String getZuam() {
		return zuam;
	}
	public void setZuam(String zuam) {
		this.zuam = zuam;
	}
	public String getZamt() {
		return zamt;
	}
	public void setZamt(String zamt) {
		this.zamt = zamt;
	}
	public String getZrmk() {
		return zrmk;
	}
	public void setZrmk(String zrmk) {
		this.zrmk = zrmk;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid=userid;
	}
	public int getRowcnt() {
		return rowcnt;
	}
	public void setRowcnt(int rowcnt) {
		this.rowcnt=rowcnt;
	}
}
