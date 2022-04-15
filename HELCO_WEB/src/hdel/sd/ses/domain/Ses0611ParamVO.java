package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0611ParamVO extends ParameterVO {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String SID;
	private String ATTSEQ;
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
}
