package hdel.sd.smp.domain;

import hdel.lib.domain.ParameterVO;

/**
 * �̵���ȹ ��Ȳ(��������)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public class Smp0120VO extends ParameterVO {

	/**
	 *  �빮�ڷ� ǥ���Ұ�.
	 */
	 
	// pk
	private String MANDT; 		// Ŭ���̾�Ʈ
	private String ZMPINC; 		// �̵���ȹ����
	private String ZMPNNR; 		// �̵���ȹ��ȣ
	private String ZPYM; 		// ��ȹ���
	private String SPART; 		// ��ǰ��
	private String AUART; 		// �ǸŹ�������
	private String MATNR;		// �����ȣ
	private String VKBUR;		// �����
	private String VKGRP;		// �����׷�
	private String ZKUNNR;		// �������
	private String GTYPE;		// ����
	private String ZNUMBER;		// ���
	private String KUNNR;		// ����ȣ
	private String GSNAM;		// �����
	private String REGION; 		// ��ġ����
	private String ZDELI; 		// �ܳ��ⱸ��
	private String SOFOCA;		// ���ֿ����
	private String ZFORE;		// ���������
	private String WAERK;		// ��ȭ
	private String POSIDSV; 	// ����������Ʈ
	private String ZHOGISV; 	// ����ȣ���ȣ
	private String ZRMK; 		// ���
	private String SOABLE; 		// ���ְ��ɼ�
	private String SORLT; 		// ���ְ��
	private String SOPRICE; 	// ���ֱݾ�
	private String SHANG;		// ������
	private String ZMPFLG; 		// �̵���ȹ�ݿ�����
	private String ZBPNNR; 		// �����ȹ��ȣ
	private String SONNR; 		// ���ְ�ȹ��ȣ
	private String VBELN; 		// �ǸŹ���
	private String POSID; 		// ������Ʈ
	private String CDATE;		// �Է���
	private String CTIME;		// �Է½ð�
	private String CUSER; 		// �Է���
	private String UDATE; 		// ������
	private String UTIME; 		// ����ð�
	private String UUSER;		// ������
	private String DDATE;		// ������
	private String DTIME; 		// �����ð�
	private String DUSER; 		// ������
	private String ZAGNT;		// ���»�
	private String KRWUSD;		// ȯ��
	
	

	public String getKRWUSD() {
		return KRWUSD;
	}
	public void setKRWUSD(String kRWUSD) {
		KRWUSD = kRWUSD;
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
	public String getZMPINC() {
		return ZMPINC;
	}
	public void setZMPINC(String zMPINC) {
		ZMPINC = zMPINC;
	}
	public String getZMPNNR() {
		return ZMPNNR;
	}
	public void setZMPNNR(String zMPNNR) {
		ZMPNNR = zMPNNR;
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
	public String getPOSIDSV() {
		return POSIDSV;
	}
	public void setPOSIDSV(String pOSIDSV) {
		POSIDSV = pOSIDSV;
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
	public String getSOABLE() {
		return SOABLE;
	}
	public void setSOABLE(String sOABLE) {
		SOABLE = sOABLE;
	}
	public String getSORLT() {
		return SORLT;
	}
	public void setSORLT(String sORLT) {
		SORLT = sORLT;
	}
	public String getSOPRICE() {
		return SOPRICE;
	}
	public void setSOPRICE(String sOPRICE) {
		SOPRICE = sOPRICE;
	}
	public String getSHANG() {
		return SHANG;
	}
	public void setSHANG(String sHANG) {
		SHANG = sHANG;
	}
	public String getZMPFLG() {
		return ZMPFLG;
	}
	public void setZMPFLG(String zMPFLG) {
		ZMPFLG = zMPFLG;
	}
	public String getZBPNNR() {
		return ZBPNNR;
	}
	public void setZBPNNR(String zBPNNR) {
		ZBPNNR = zBPNNR;
	}
	public String getSONNR() {
		return SONNR;
	}
	public void setSONNR(String sONNR) {
		SONNR = sONNR;
	}
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}
	public String getPOSID() {
		return POSID;
	}
	public void setPOSID(String pOSID) {
		POSID = pOSID;
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
