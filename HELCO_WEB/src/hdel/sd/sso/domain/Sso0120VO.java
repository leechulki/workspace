package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;

/**
 * ���ְ�ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public class Sso0120VO extends ParameterVO {

	/**
	 *  �빮�ڷ� ǥ���Ұ�.
	 */
	
	// pk
	private String MANDT;			// Ŭ���̾�Ʈ
	private String SONNR;			// ���ְ�ȹ��ȣ 
	private String ZPYM; 			// ��ȹ���
	private String SPART; 			// ��ǰ��
	private String AUART; 			// �ǸŹ�������
	private String MATNR; 			// �����ȣ
	private String VKBUR;			// �����
	private String VKGRP; 			// �����׷�
	private String ZKUNNR; 			// �������
	private String RTYPE;			// �Ǳ���
	private String GTYPE; 			// ����
	private String TYPE1; 			// Ÿ��1
	private String TYPE2; 			// Ÿ��2
	private String TYPE3; 			// Ÿ��3
	private String ZNUMBER; 		// ���
	private String KUNNR; 			// ������ȣ
	private String GSNAM; 			// �����
	private String LAND1;			// ����
	private String REGION; 			// ��ġ����
	private String ZDELI; 			// �ܳ��ⱸ��
	private String SHANGH; 			// �������ر���
	private String ZINTER; 			// �߰蹫������
	private String SOFOCA; 			// ���ֿ����
	private String ZFORE;			// ���������
	private String WAERK; 			// ��ȭ
	private String DELDAT;			// ���⿹����
	private String ZRMK; 			// ���
	private String SOABLE; 			// ���ְ��ɼ�
	private String SORLT; 			// ���ְ��
	private String SOPRICE; 		// ���ֱݾ�
	private String SHANG; 			// ������
	private String ZMPFLG; 			// �̵���ȹ�ݿ�����
	private String VBELN; 			// �ǸŹ���
	private String ZBPNNR; 			// �����ȹ��ȣ
	private String SONNRB;			// �������ְ�ȹ��ȣ
	private String CDATE; 			// �Է���
	private String CTIME; 			// �Է½ð�
	private String CUSER; 			// �Է���
	private String UDATE; 			// ������
	private String UTIME; 			// ����ð�
	private String UUSER; 			// ������
	private String DDATE; 			// ������
	private String DTIME; 			// �����ð�
	private String DUSER;			// ������
	private String ZAGNT;			// �븮��
	private String ZBDTYP;          //�ǹ��뵵 jss
	
	public String getRTYPE() {
		return RTYPE;
	}
	public void setRTYPE(String rTYPE) {
		RTYPE = rTYPE;
	}
	public String getLAND1() {
		return LAND1;
	}
	public void setLAND1(String lAND1) {
		LAND1 = lAND1;
	}
	public String getSONNRB() {
		return SONNRB;
	}
	public void setSONNRB(String sONNRB) {
		SONNRB = sONNRB;
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
	public String getSONNR() {
		return SONNR;
	}
	public void setSONNR(String sONNR) {
		SONNR = sONNR;
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
	public String getTYPE1() {
		return TYPE1;
	}
	public void setTYPE1(String tYPE1) {
		TYPE1 = tYPE1;
	}
	public String getTYPE2() {
		return TYPE2;
	}
	public void setTYPE2(String tYPE2) {
		TYPE2 = tYPE2;
	}
	public String getTYPE3() {
		return TYPE3;
	}
	public void setTYPE3(String tYPE3) {
		TYPE3 = tYPE3;
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
	public String getSHANGH() {
		return SHANGH;
	}
	public void setSHANGH(String sHANGH) {
		SHANGH = sHANGH;
	}
	public String getZINTER() {
		return ZINTER;
	}
	public void setZINTER(String zINTER) {
		ZINTER = zINTER;
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
	public String getDELDAT() {
		return DELDAT;
	}
	public void setDELDAT(String dELDAT) {
		DELDAT = dELDAT;
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
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}
	public String getZBPNNR() {
		return ZBPNNR;
	}
	public void setZBPNNR(String zBPNNR) {
		ZBPNNR = zBPNNR;
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
	public String getZBDTYP() {
		return ZBDTYP;
	}
	public void setZBDTYP(String zBDTYP) {
		ZBDTYP = zBDTYP;
	}
	
	
}