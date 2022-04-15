package com.helco.xf.cs01.ws;

public class MatchDutyD   
		implements java.io.Serializable {

	/**
	 * 정합성 체크 데이터 변수
	 * @param ctx
	 * @throws Exception
	 */
	private static final long serialVersionUID = 1L;
	public MatchDutyD() {
	}
	
	private String MANDT;
	private String CTYPE;
	private String TYPE;
	private String BLOCKNO;
	private String BLOCKNOSEQ;
	private String ZORDER;
	private String SPEC;
	private String CALCULAT;
	private String CALCULATCK;
	private String DFLAG;
	
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
	public String getBLOCKNOSEQ() {
		return BLOCKNOSEQ;
	}
	public void setBLOCKNOSEQ(String bLOCKNOSEQ) {
		BLOCKNOSEQ = bLOCKNOSEQ;
	}
	public String getZORDER() {
		return ZORDER;
	}
	public void setZORDER(String zORDER) {
		ZORDER = zORDER;
	}
	public String getSPEC() {
		return SPEC;
	}
	public void setSPEC(String sPEC) {
		SPEC = sPEC;
	}
	public String getCALCULAT() {
		return CALCULAT;
	}
	public void setCALCULAT(String cALCULAT) {
		CALCULAT = cALCULAT;
	}
	public String getCALCULATCK() {
		return CALCULATCK;
	}
	public void setCALCULATCK(String cALCULATCK) {
		CALCULATCK = cALCULATCK;
	}
	public String getDFLAG() {
		return DFLAG;
	}
	public void setDFLAG(String dFLAG) {
		DFLAG = dFLAG;
	}
	@Override
	public String toString() {
		return "MatchDutyD [MANDT=" + MANDT + ", CTYPE=" + CTYPE + ", TYPE="
				+ TYPE + ", BLOCKNO=" + BLOCKNO + ", BLOCKNOSEQ=" + BLOCKNOSEQ
				+ ", ZORDER=" + ZORDER + ", SPEC=" + SPEC + ", CALCULAT="
				+ CALCULAT + ", CALCULATCK=" + CALCULATCK + ", DFLAG=" + DFLAG
				+ "]";
	}

}