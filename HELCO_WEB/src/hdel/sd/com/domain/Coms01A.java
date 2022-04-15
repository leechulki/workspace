package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;

public class Coms01A extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String MANDT;
	private String BRNDSEQ;
	private String ZPRDGBN;
	private String BRNDCD;
	private String BRNDNM;
	private String GTYPE;
	private String BRNDDAY;
	private String FRDAT;
	private String TODAT;
	private String SNDSYS;
	private String QTNUM;
	private String CDATE;
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getBRNDSEQ() {
		return BRNDSEQ;
	}
	public void setBRNDSEQ(String bRNDSEQ) {
		BRNDSEQ = bRNDSEQ;
	}
	public String getZPRDGBN() {
		return ZPRDGBN;
	}
	public void setZPRDGBN(String zPRDGBN) {
		ZPRDGBN = zPRDGBN;
	}
	public String getBRNDCD() {
		return BRNDCD;
	}
	public void setBRNDCD(String bRNDCD) {
		BRNDCD = bRNDCD;
	}
	public String getBRNDNM() {
		return BRNDNM;
	}
	public void setBRNDNM(String bRNDNM) {
		BRNDNM = bRNDNM;
	}
	public String getGTYPE() {
		return GTYPE;
	}
	public void setGTYPE(String gTYPE) {
		GTYPE = gTYPE;
	}
	public String getBRNDDAY() {
		return BRNDDAY;
	}
	public void setBRNDDAY(String bRNDDAY) {
		BRNDDAY = bRNDDAY;
	}
	public String getFRDAT() {
		return FRDAT;
	}
	public void setFRDAT(String fRDAT) {
		FRDAT = fRDAT;
	}
	public String getTODAT() {
		return TODAT;
	}
	public void setTODAT(String tODAT) {
		TODAT = tODAT;
	}
	public String getSNDSYS() {
		return SNDSYS;
	}
	public void setSNDSYS(String sNDSYS) {
		SNDSYS = sNDSYS;
	}
	public String getQTNUM() {
		return QTNUM;
	}
	public void setQTNUM(String qTNUM) {
		QTNUM = qTNUM;
	}
	
	public String getCDATE() {
		return CDATE;
	}
	
	public void setCDATE(String cDATE) {
		CDATE = cDATE;
	}
	@Override
	public String toString() {
		return "Coms01A [MANDT=" + MANDT + ", BRNDSEQ=" + BRNDSEQ + ", ZPRDGBN=" + ZPRDGBN + ", BRNDCD=" + BRNDCD
				+ ", BRNDNM=" + BRNDNM + ", GTYPE=" + GTYPE + ", BRNDDAY=" + BRNDDAY + ", FRDAT=" + FRDAT + ", TODAT="
				+ TODAT + ", SNDSYS=" + SNDSYS + ", QTNUM=" + QTNUM + ", CDATE=" + CDATE + "]";
	}
}
