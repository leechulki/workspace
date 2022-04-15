package com.helco.xf.cs01.ws;

public class MatchDuty   
		implements java.io.Serializable {

	/**
	 * 정합성 마스터 데이터 변수
	 * @param ctx
	 * @throws Exception
	 */
	private static final long serialVersionUID = 1L;

	public MatchDuty() {
	}
	
	private String MANDT;
	private String CTYPE;
	private String TYPE;
	private String BLOCKNO;
	private String MESSAGE;
	private String TRANS;
	private String DFLAG;
	private String BLOCKNOSEQ;

	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getCTYPE() {
		return CTYPE;
	}
	public void setCTYPE(String cTYPE) {
		CTYPE = cTYPE;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getBLOCKNO() {
		return BLOCKNO;
	}
	public void setBLOCKNO(String bLOCKNO) {
		BLOCKNO = bLOCKNO;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public String getTRANS() {
		return TRANS;
	}
	public void setTRANS(String tRANS) {
		TRANS = tRANS;
	}
	public String getDFLAG() {
		return DFLAG;
	}
	public void setDFLAG(String dFLAG) {
		DFLAG = dFLAG;
	}
	
	public String getBLOCKNOSEQ() {
		return BLOCKNOSEQ;
	}
	public void setBLOCKNOSEQ(String bLOCKNOSEQ) {
		BLOCKNOSEQ = bLOCKNOSEQ;
	}
	@Override
	public String toString() {
		return "MatchDuty [MANDT=" + MANDT + ", CTYPE=" + CTYPE + ", TYPE="
				+ TYPE + ", BLOCKNO=" + BLOCKNO + ", MESSAGE=" + MESSAGE
				+ ", TRANS=" + TRANS + ", DFLAG=" + DFLAG + ", BLOCKNOSEQ="
				+ BLOCKNOSEQ + "]";
	}
}