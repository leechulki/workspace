package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0441ParamVO extends ParameterVO {	
	
	private String MANDT;
	private String PSPID;
	private String SEQ;
	private String RQSER;

	private String ZATGBN;
	private String ZATTPATH;
	private String ZATTFN;
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getPSPID() {
		return PSPID;
	}
	public void setPSPID(String pSPID) {
		PSPID = pSPID;
	}
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}
	public String getRQSER() {
		return RQSER;
	}
	public void setRQSER(String rQSER) {
		RQSER = rQSER;
	}

	public String getZATGBN() {
		return ZATGBN;
	}
	public void setZATGBN(String zATGBN) {
		ZATGBN = zATGBN;
	}
	public String getZATTPATH() {
		return ZATTPATH;
	}
	public void setZATTPATH(String zATTPATH) {
		ZATTPATH = zATTPATH;
	}
	public String getZATTFN() {
		return ZATTFN;
	}
	public void setZATTFN(String zATTFN) {
		ZATTFN = zATTFN;
	}
	
}
