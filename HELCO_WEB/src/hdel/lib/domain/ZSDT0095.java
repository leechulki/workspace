package hdel.lib.domain;

import org.apache.commons.lang.math.NumberUtils;

public class ZSDT0095 extends CommonDomain {
	private static final long serialVersionUID = -3960318633530828556L;

	private String mandt = "";
	private String vbeln = "";
	private String lockflag = "";
	private String username = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public ZSDT0095() {
	}
	public ZSDT0095(String mandt, String vbeln, Boolean lock) {
		setMandt(mandt);
		setVbeln(vbeln);
		setLock(lock);
	}

	public Boolean getLock() {
//		return lockflag == null ? false : lockflag.equals("X") ? true : false;
		return lockflag.equals("X") ? true : false;
	}
	public void setLock(Boolean lock) {
		if(lock)
			setLockflag("X");
		else
			setLockflag("");
	}

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getVbeln() {
		return NumberUtils.isNumber(vbeln) ? String.format("%010d", NumberUtils.toInt(vbeln, 0)) : vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = NumberUtils.isNumber(vbeln) ? String.format("%010d", NumberUtils.toInt(vbeln, 0)) : vbeln;
	}
	public String getLockflag() {
		return lockflag;
	}
	public void setLockflag(String lockflag) {
		this.lockflag = lockflag == null ? "" : lockflag;	// null casting
		this.lockflag = this.lockflag.equals("X") ? "X" : "";	//converting as SAP Boolean TRUE only if "X"
	}
	public String getUsername() {
		return username == null ? "" : username;
	}
	public void setUsername(String username) {
		this.username = username == null ? "" : username;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}