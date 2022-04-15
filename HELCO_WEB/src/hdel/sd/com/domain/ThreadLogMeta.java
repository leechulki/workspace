package hdel.sd.com.domain;

import java.io.Serializable;

public class ThreadLogMeta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String qtnum = "test";
	private String eqtnum = "test";
	private String qtser;
	private StringBuffer logMsg = new StringBuffer();
	private long startLongTime = 0L;
	private long endLongTime = 0L;
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public String getQtser() {
		return qtser;
	}
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}
	public StringBuffer getLogMsg() {
		return logMsg;
	}
	public void setLogMsg(String msg) {
		this.logMsg.append(msg);
	}
	public long getStartLongTime() {
		return startLongTime;
	}
	public void setStartLongTime(long startLongTime) {
		this.startLongTime = startLongTime;
	}
	public long getEndLongTime() {
		return endLongTime;
	}
	public void setEndLongTime(long endLongTime) {
		this.endLongTime = endLongTime;
	}
	public String getEqtnum() {
		return eqtnum;
	}
	public void setEqtnum(String eqtnum) {
		this.eqtnum = eqtnum;
	}

	@Override
	public String toString() {
		return "LogMeta [qtnum=" + qtnum + ", eqtnum=" + eqtnum + ", qtser=" + qtser + ", logMsg=" + logMsg.toString()
				+ ", startLongTime=" + startLongTime + ", endLongTime=" + endLongTime + "]";
	}
}