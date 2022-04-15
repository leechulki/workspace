package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0088 extends CommonDomain {
 
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String QTNUM;
	private String QTSER;
	private String ATTSEQ;
	private String FILEGBN;
	private String ATTPATH;
	private String ATTFN;
	private String FLAG;
	private String UUSER;
	
	
	@Override
	public String toString() {
		return "Ses0088 [MANDT=" + MANDT + ", QTNUM=" + QTNUM + ", QTSER="
				+ QTSER + ", ATTSEQ=" + ATTSEQ + ", FILEGBN=" + FILEGBN
				+ ", ATTPATH=" + ATTPATH + ", ATTFN=" + ATTFN + ", FLAG="
				+ FLAG + ", UUSER=" + UUSER + "]";
	}
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getQTNUM() {
		return QTNUM;
	}
	public void setQTNUM(String qTNUM) {
		QTNUM = qTNUM;
	}
	public String getQTSER() {
		return QTSER;
	}
	public void setQTSER(String qTSER) {
		QTSER = qTSER;
	}
	public String getATTSEQ() {
		return ATTSEQ;
	}
	public void setATTSEQ(String aTTSEQ) {
		ATTSEQ = aTTSEQ;
	}
	public String getFILEGBN() {
		return FILEGBN;
	}
	public void setFILEGBN(String fILEGBN) {
		FILEGBN = fILEGBN;
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
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getUUSER() {
		return UUSER;
	}
	public void setUUSER(String uUSER) {
		UUSER = uUSER;
	}
	

}