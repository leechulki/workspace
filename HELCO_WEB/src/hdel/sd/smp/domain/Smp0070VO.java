package hdel.sd.smp.domain;

import hdel.lib.domain.ParameterVO;

/**
 * �̵���ȹ ����/����
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public class Smp0070VO extends ParameterVO {

	/**
	 *  �빮�ڷ� ǥ���Ұ�.
	 */
	
	// pk
	private String MANDT;		// Ŭ���̾�Ʈ
	private String ZMPINC;		// �̵���ȹ����
	
	private String ZPYM; 		// �����
	private String ZPMONTHS;	// ��������
	private String ZOPFLG; 		// ���¿���
	private String ZCLFLG;  	// ��������
	private String ZRMK; 		// ���
	private String CDATE;		// �Է���
	private String CTIME;		// �Է½ð�
	private String CUSER;		// �Է���
	private String UDATE;		// ������
	private String UTIME;		// ����ð�
	private String UUSER;		// ������
	private String DDATE;		// ������
	private String DTIME;		// �����ð�
	private String DUSER;		// ������
	
	private String BTN_EVENT;	// �������� ��ũ��Ʈ�� �÷���
	
	public String getBTN_EVENT() {
		return BTN_EVENT;
	}
	public void setBTN_EVENT(String bTN_EVENT) {
		BTN_EVENT = bTN_EVENT;
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
	public String getZPYM() {
		return ZPYM;
	}
	public void setZPYM(String zPYM) {
		ZPYM = zPYM;
	}
	public String getZPMONTHS() {
		return ZPMONTHS;
	}
	public void setZPMONTHS(String zPMONTHS) {
		ZPMONTHS = zPMONTHS;
	}
	public String getZOPFLG() {
		return ZOPFLG;
	}
	public void setZOPFLG(String zOPFLG) {
		ZOPFLG = zOPFLG;
	}
	public String getZCLFLG() {
		return ZCLFLG;
	}
	public void setZCLFLG(String zCLFLG) {
		ZCLFLG = zCLFLG;
	}
	public String getZRMK() {
		return ZRMK;
	}
	public void setZRMK(String zRMK) {
		ZRMK = zRMK;
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
