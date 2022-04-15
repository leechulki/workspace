package hdel.sd.sch.domain;

import hdel.lib.domain.ParameterVO;

/**
 * On-Hand û����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public class Sch0040VO extends ParameterVO {

	/**
	 *  �빮�ڷ� ǥ���Ұ�.
	 */
	
	// pk
	private String MANDT;		// Ŭ���̾�Ʈ
	private String ZMPNNR;		// �̵���ȹ��ȣ
	private String ZMPINC;		// �̵���ȹ����
	 
	private String ZPYM;		// ��ȹ���
	private String SPART;		// ��ǰ��
	private String AUART;		// �ǸŹ�������
	private String MATNR;		// �����ȣ
	private String VKBUR;		// �����
	private String VKGRP;		// �����׷�
	private String ZKUNNR;		// �������
	private String GTYPE;		// ����
	private String TYPE1;		// Ÿ��1
	private String TYPE2;		// Ÿ��2
	private String TYPE3;		// Ÿ��3
	private String ZNUMBER;		// ���
	private String KUNNR;		// ����ȣ
	private String GSNAM;		// �����
	private String REGION;		// ��ġ����
	private String ZDELI;		// �ܳ��ⱸ��
	private String SHANGH;		// ����&���ر���
	private String ZINTER;		// �߰蹫������
	private String SOFOCA;		// ���ֿ����
	private String ZFORE;		// ���������
	private String WAERK;		// ��ȭ
	private String DELDAT;		// ���⿹����
	private String ZRMK;		// ���
	private String SOABLE;		// ���ְ��ɼ�
	private String SORLT;		// ���ְ��
	private String SOPRICE;		// ���ֱݾ�
	private String SHANG;		// ������
	private String SONNR;		// ���ְ�ȹ��ȣ
	private String VBELN;		// �ǸŹ���
	private String POSID;		// ������Ʈ
	private String CDATE;		// �Է���
	private String CTIME;		// �Է½ð�
	private String CUSER;		// �Է���
	private String UDATE;		// ������
	private String UTIME;		// ����ð�
	private String UUSER;		// ������
	private String DDATE;		// ������
	private String DTIME;		// �����ð�
	private String DUSER;		// ������
	private String ZSITE_NM;	// �����
	
	
	
	public String getZSITE_NM() {
		return ZSITE_NM;
	}
	public void setZSITE_NM(String zSITE_NM) {
		ZSITE_NM = zSITE_NM;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getZMPNNR() {
		return ZMPNNR;
	}
	public void setZMPNNR(String zMPNNR) {
		ZMPNNR = zMPNNR;
	}
	public String getZMPINC() {
		return ZMPINC;
	}
	public void setZMPINC(String zMPINC) {
		ZMPINC = zMPINC;
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
