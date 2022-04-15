package hdel.sd.com.domain;

public class BrndSayang {
	
	private String BRNDSEQ;
	private String BRNDCD;
	private String PRH;
	private String PRD;
	private String KEYIN;

	public String getBRNDSEQ() {
		return BRNDSEQ;
	}
	public void setBRNDSEQ(String bRNDSEQ) {
		BRNDSEQ = bRNDSEQ;
	}
	public String getBRNDCD() {
		return BRNDCD;
	}
	public void setBRNDCD(String bRNDCD) {
		BRNDCD = bRNDCD;
	}
	public String getPRH() {
		return PRH;
	}
	public void setPRH(String pRH) {
		PRH = pRH;
	}
	public String getPRD() {
		return PRD;
	}
	public void setPRD(String pRD) {
		PRD = pRD;
	}
	public String getKEYIN() {
		return KEYIN;
	}
	public void setKEYIN(String kEYIN) {
		KEYIN = kEYIN;
	}
	@Override
	public String toString() {
		return "BrndSayang [BRNDSEQ=" + BRNDSEQ + ", BRNDCD=" + BRNDCD + ", PRH=" + PRH + ", PRD=" + PRD + ", KEYIN=" + KEYIN + "]";
	}
	
}
