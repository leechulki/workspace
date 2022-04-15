package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0611 extends CommonDomain {
 
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String SID;
	private String ATTSEQ;
	private String ATTPATH;
	private String ATTFN;
	private String UUSER;
	private String FLAG;
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getATTSEQ() {
		return ATTSEQ;
	}
	public void setATTSEQ(String aTTSEQ) {
		ATTSEQ = aTTSEQ;
	}
	public String getATTPATH() {
		return ATTPATH;
	}
	public void setATTPATH(String aTTPATH) {
		ATTPATH = aTTPATH;
	}
	public String getATTFN() {
		return ATTFN;
	}
	public void setATTFN(String aTTFN) {
		ATTFN = aTTFN;
	}
	public String getUUSER() {
		return UUSER;
	}
	public void setUUSER(String uUSER) {
		UUSER = uUSER;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
}