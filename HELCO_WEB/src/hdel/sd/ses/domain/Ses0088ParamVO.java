package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0088ParamVO extends ParameterVO {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String QTNUM;
	private String QTSER;
	private String ATTSEQ;
	private String FILEGBN;
	
	
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
	
}
