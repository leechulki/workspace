package hdel.sd.sbp.domain;

import hdel.lib.domain.ParameterVO;

/**
 * �����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public class Sbp0100VO extends ParameterVO {

	/**
	 *  �빮�ڷ� ǥ���Ұ�.
	 */
	
	// pk
	private String MANDT;			// Ŭ���̾�Ʈ
	private String ZBPNNR;			// �����ȹ��ȣ
	
	private String ZPYEAR;			// ���⵵
	private String ZPYM;			// ��ȹ���
	private String SPART;			// ��ǰ��
	private String AUART;			// �ǸŹ�������
	private String MATNR;			// �����ȣ
	private String VKBUR;			// �����
	private String VKGRP;			// �����׷�
	private String ZKUNNR;			// �������
	private String RTYPE;			// �Ǳ���
	private String GTYPE;			// ����
	private String ZNUMBER;			// ���
	private String KUNNR;			// ����ȣ
	private String GSNAM;			// �����
	private String REGION;			// ��ġ����
	private String ZDELI;			// �ܳ��ⱸ��
	private String SOFOCA;			// ���ֿ����
	private String ZFORE;			// ���������
	private String WAERK;			// ��ȭ
	private String PSPIDSV; 		// ����������Ʈ
	private String ZHOGISV; 		// ����ȣ���ȣ
	private String ZRMK;			// ���
	private String VBELN;			// �ǸŹ���
	private String PSPID;			// ������Ʈ
	private String CDATE;			// �Է���
	private String CTIME;			// �Է½ð�
	private String CUSER;			// �Է���
	private String UDATE;			// ������
	private String UTIME;			// ����ð�
	private String UUSER;			// ������
	private String DDATE;			// ������
	private String DTIME;			// �����ð�
	private String DUSER;			// ������
	private String ZAGNT;			// �븮��
	

	public String getRTYPE() {
		return RTYPE;
	}
	public void setRTYPE(String rTYPE) {
		RTYPE = rTYPE;
	}
	public String getZAGNT() {
		return ZAGNT;
	}
	public void setZAGNT(String zAGNT) {
		ZAGNT = zAGNT;
	}
	
	
	
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getZBPNNR() {
		return ZBPNNR;
	}
	public void setZBPNNR(String zBPNNR) {
		ZBPNNR = zBPNNR;
	}
	public String getZPYEAR() {
		return ZPYEAR;
	}
	public void setZPYEAR(String zPYEAR) {
		ZPYEAR = zPYEAR;
	}
	public String getZPYM() {
		return ZPYM;
	}
	public void setZPYM(String zPYM) {
		ZPYM = zPYM;
	}
	public String getSPART() {
		return SPART;
	}
	public void setSPART(String sPART) {
		SPART = sPART;
	}
	public String getAUART() {
		return AUART;
	}
	public void setAUART(String aUART) {
		AUART = aUART;
	}
	public String getMATNR() {
		return MATNR;
	}
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}
	public String getVKBUR() {
		return VKBUR;
	}
	public void setVKBUR(String vKBUR) {
		VKBUR = vKBUR;
	}
	public String getVKGRP() {
		return VKGRP;
	}
	public void setVKGRP(String vKGRP) {
		VKGRP = vKGRP;
	}
	public String getZKUNNR() {
		return ZKUNNR;
	}
	public void setZKUNNR(String zKUNNR) {
		ZKUNNR = zKUNNR;
	}
	public String getGTYPE() {
		return GTYPE;
	}
	public void setGTYPE(String gTYPE) {
		GTYPE = gTYPE;
	}
	public String getZNUMBER() {
		return ZNUMBER;
	}
	public void setZNUMBER(String zNUMBER) {
		ZNUMBER = zNUMBER;
	}
	public String getKUNNR() {
		return KUNNR;
	}
	public void setKUNNR(String kUNNR) {
		KUNNR = kUNNR;
	}
	public String getGSNAM() {
		return GSNAM;
	}
	public void setGSNAM(String gSNAM) {
		GSNAM = gSNAM;
	}
	public String getREGION() {
		return REGION;
	}
	public void setREGION(String rEGION) {
		REGION = rEGION;
	}
	public String getZDELI() {
		return ZDELI;
	}
	public void setZDELI(String zDELI) {
		ZDELI = zDELI;
	}
	public String getSOFOCA() {
		return SOFOCA;
	}
	public void setSOFOCA(String sOFOCA) {
		SOFOCA = sOFOCA;
	}
	public String getZFORE() {
		return ZFORE;
	}
	public void setZFORE(String zFORE) {
		ZFORE = zFORE;
	}
	public String getWAERK() {
		return WAERK;
	}
	public void setWAERK(String wAERK) {
		WAERK = wAERK;
	}
	public String getPSPIDSV() {
		return PSPIDSV;
	}
	public void setPSPIDSV(String PSPIDSV) {
		PSPIDSV = PSPIDSV;
	}
	public String getZHOGISV() {
		return ZHOGISV;
	}
	public void setZHOGISV(String zHOGISV) {
		ZHOGISV = zHOGISV;
	}
	public String getZRMK() {
		return ZRMK;
	}
	public void setZRMK(String zRMK) {
		ZRMK = zRMK;
	}
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}
	public String getPSPID() {
		return PSPID;
	}
	public void setPSPID(String pSPID) {
		PSPID = pSPID;
	}
	public String getCDATE() {
		return CDATE;
	}
	public void setCDATE(String cDATE) {
		CDATE = cDATE;
	}
	public String getCTIME() {
		return CTIME;
	}
	public void setCTIME(String cTIME) {
		CTIME = cTIME;
	}
	public String getCUSER() {
		return CUSER;
	}
	public void setCUSER(String cUSER) {
		CUSER = cUSER;
	}
	public String getUDATE() {
		return UDATE;
	}
	public void setUDATE(String uDATE) {
		UDATE = uDATE;
	}
	public String getUTIME() {
		return UTIME;
	}
	public void setUTIME(String uTIME) {
		UTIME = uTIME;
	}
	public String getUUSER() {
		return UUSER;
	}
	public void setUUSER(String uUSER) {
		UUSER = uUSER;
	}
	public String getDDATE() {
		return DDATE;
	}
	public void setDDATE(String dDATE) {
		DDATE = dDATE;
	}
	public String getDTIME() {
		return DTIME;
	}
	public void setDTIME(String dTIME) {
		DTIME = dTIME;
	}
	public String getDUSER() {
		return DUSER;
	}
	public void setDUSER(String dUSER) {
		DUSER = dUSER;
	}
	
	
	
	
	
}
