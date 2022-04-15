package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;

/**
 * ���ֺ���
 * 
 * �ۼ�  ����   : 2015.07
 * HISTORY  : �ű԰���
 */
@SuppressWarnings("serial")
public class Sso0020VO extends ParameterVO {

	/**
	 *  �빮�ڷ� ǥ���Ұ�.
	 */
	 
	// pk
	private String MANDT;
	private String AUTOLP;
	private String AUTOLP_P;
	private String QTNUM;
	private String KUNNR_Z3;
	private String SPEC_E;
	private String LP_CHN;
	
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getAUTOLP() {
		return AUTOLP;
	}
	public void setAUTOLP(String aUTOLP) {
		AUTOLP = aUTOLP;
	}
	public String getAUTOLP_P() {
		return AUTOLP_P;
	}
	public void setAUTOLP_P(String aUTOLP_P) {
		AUTOLP_P = aUTOLP_P;
	}
	public String getQTNUM() {
		return QTNUM;
	}
	public void setQTNUM(String qTNUM) {
		QTNUM = qTNUM;
	}
	public String getKUNNR_Z3() {
		return KUNNR_Z3;
	}
	public void setKUNNR_Z3(String kUNNR_Z3) {
		KUNNR_Z3 = kUNNR_Z3;
	}
	public String getSPEC_E() {
		return SPEC_E;
	}
	public void setSPEC_E(String sPEC_E) {
		SPEC_E = sPEC_E;
	}
	public String getLP_CHN() {
		return LP_CHN;
	}
	public void setLP_CHN(String lP_CHN) {
		LP_CHN = lP_CHN;
	}
	
}
