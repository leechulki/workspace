package hdel.sd.sso.domain;

import hdel.sd.smp.domain.SmpComParameterVO;

/**
 * ���ֺ���
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
@SuppressWarnings("serial")
public class Sso0050ParamVO extends SmpComParameterVO {

	// �빮�ڷ� ǥ���Ұ�.
	private String MANDT;			// Ŭ���̾�Ʈ
	private String PSPID;			// ������Ʈ��ȣ
	private String POSNR;			// �Ǹ�ǰ��
	private String MATNR;			// �����ȣ 
	private String CLASS;			// Ŭ��������
	private String VBELN;
	private String SEQ;
	private String SEQPRE;
	private String FINL;
	private String POSID;
	private String TCURRDATE;		// ȯ�� �������� 2012.02.20
	
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
	public String getPOSNR() {
		return POSNR;
	}
	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}
	public String getMATNR() {
		return MATNR;
	}
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}
	public String getCLASS() {
		return CLASS;
	}
	public void setCLASS(String cLASS) {
		CLASS = cLASS;
	}
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}
	public String getSEQPRE() {
		return SEQPRE;
	}
	public void setSEQPRE(String sEQPRE) {
		SEQPRE = sEQPRE;
	}
	public String getFINL() {
		return FINL;
	}
	public void setFINL(String fINL) {
		FINL = fINL;
	}
	public String getPOSID() {
		return POSID;
	}
	public void setPOSID(String pOSID) {
		POSID = pOSID;
	}
	public String getTCURRDATE() {
		return TCURRDATE;
	}
	public void setTCURRDATE(String tCURRDATE) {
		TCURRDATE = tCURRDATE;
	}

}
