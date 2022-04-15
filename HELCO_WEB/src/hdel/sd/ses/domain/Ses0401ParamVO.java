package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0401ParamVO extends ParameterVO {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String ZRQSEQ;
	private String QTSO_NO;
	private String QTSER;
	private String QTSEQ;
	private String HOGI;
	private String ZATGBN;
	
	private String GUBUN;	// ����, ������Ʈ
	private String UUSER;	// ó����
	
	private String SMANCD;
	
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
	public String getQTSEQ() {
		return QTSEQ;
	}
	public void setQTSEQ(String qTSEQ) {
		QTSEQ = qTSEQ;
	}
	public String getHOGI() {
		return HOGI;
	}
	public void setHOGI(String hOGI) {
		HOGI = hOGI;
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
	public String getSMANCD() {
		return SMANCD;
	}
	public void setSMANCD(String sMANCD) {
		SMANCD = sMANCD;
	}	
}
