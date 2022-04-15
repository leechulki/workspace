package hdel.lib.domain;

public class ZSDT0167 extends CommonDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6788595853751521893L;

	private String mandt = "";
	private String qtnum = "";
	private String tquot = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	/**
	 * @return the mandt
	 */
	public String getMandt() {
		return mandt;
	}
	/**
	 * @param mandt the mandt to set
	 */
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	/**
	 * @return the qtnum
	 */
	public String getQtnum() {
		return qtnum;
	}
	/**
	 * @param qtnum the qtnum to set
	 */
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	/**
	 * @return the tquot
	 */
	public String getTquot() {
		return tquot;
	}
	/**
	 * @param tquot the tquot to set
	 */
	public void setTquot(String tquot) {
		this.tquot = tquot == null ? "0" : tquot;	// null casting
		this.tquot = this.tquot.equals("1") ? "X" : "";	//converting as SAP Boolean
	}
	/**
	 * @return the tstmp
	 */
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	/**
	 * @param tstmp the tstmp to set
	 */
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}
