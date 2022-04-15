package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0471ParamVO extends ParameterVO {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String ZRQSEQ;
	private String QTSO_NO;
	private String QTSER;
	private String ZATGBN;
	
	private String GUBUN;	// 견적, 프로젝트
	private String UUSER;	// 처리자
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getZRQSEQ() {
		return ZRQSEQ;
	}
	public void setZRQSEQ(String zRQSEQ) {
		ZRQSEQ = zRQSEQ;
	}
	public String getQTSO_NO() {
		return QTSO_NO;
	}
	public void setQTSO_NO(String qTSO_NO) {
		QTSO_NO = qTSO_NO;
	}
	public String getQTSER() {
		return QTSER;
	}
	public void setQTSER(String qTSER) {
		QTSER = qTSER;
	}
	public String getZATGBN() {
		return ZATGBN;
	}
	public void setZATGBN(String zATGBN) {
		ZATGBN = zATGBN;
	}
	/**
	 * @return the gUBUN
	 */
	public String getGUBUN() {
		return GUBUN;
	}
	/**
	 * @param gUBUN the gUBUN to set
	 */
	public void setGUBUN(String gUBUN) {
		GUBUN = gUBUN;
	}
	/**
	 * @return the uUSER
	 */
	public String getUUSER() {
		return UUSER;
	}
	/**
	 * @param uUSER the uUSER to set
	 */
	public void setUUSER(String uUSER) {
		UUSER = uUSER;
	}
}
