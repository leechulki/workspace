package hdel.lib.domain;

public class ZSDT0195 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private String atinn = "";
	private String delfg = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	private String atnam = "";
	private String atbez = "";

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getAtinn() {
		return atinn;
	}
	public void setAtinn(String atinn) {
		this.atinn = atinn;
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
}