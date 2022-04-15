package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;

public class Sso0062ParamVO extends ParameterVO {
	private String MANDT;	// CLIENT
	private String QTNUM;	// 견적번호
	private String QTSER;	// 견적차수
	private String QTSEQ;	// 견적일련번호
	private String SONNR;	// 
	private String SPRAS;	// lang
	private String HOGI;
	private String MATNR;

	/**
	 * @return the mANDT
	 */
	public String getMANDT() {
		return MANDT;
	}
	/**
	 * @param mANDT the mANDT to set
	 */
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	/**
	 * @return the qTNUM
	 */
	public String getQTNUM() {
		return QTNUM;
	}
	/**
	 * @param qTNUM the qTNUM to set
	 */
	public void setQTNUM(String qTNUM) {
		QTNUM = qTNUM;
	}
	/**
	 * @return the qTSER
	 */
	public String getQTSER() {
		return QTSER;
	}
	/**
	 * @param qTSER the qTSER to set
	 */
	public void setQTSER(String qTSER) {
		QTSER = qTSER;
	}
	/**
	 * @return the qTSEQ
	 */
	public String getQTSEQ() {
		return QTSEQ;
	}
	/**
	 * @param qTSEQ the qTSEQ to set
	 */
	public void setQTSEQ(String qTSEQ) {
		QTSEQ = qTSEQ;
	}
	/**
	 * @return the sONNR
	 */
	public String getSONNR() {
		return SONNR;
	}
	/**
	 * @param sONNR the sONNR to set
	 */
	public void setSONNR(String sONNR) {
		SONNR = sONNR;
	}
	/**
	 * @return the sPRAS
	 */
	public String getSPRAS() {
		return SPRAS;
	}
	/**
	 * @param sPRAS the sPRAS to set
	 */
	public void setSPRAS(String sPRAS) {
		SPRAS = sPRAS;
	}
	/**
	 * @return the hOGI
	 */
	public String getHOGI() {
		return HOGI;
	}
	/**
	 * @param hOGI the hOGI to set
	 */
	public void setHOGI(String hOGI) {
		HOGI = hOGI;
	}
	/**
	 * @return the mATNR
	 */
	public String getMATNR() {
		return MATNR;
	}
	/**
	 * @param mATNR the mATNR to set
	 */
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}
}
