package hdel.sd.smp.domain;

import hdel.lib.domain.ParameterVO;

/**
 * �̵���ȹ ����/����
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public class Smp0070ParamVO extends SmpComParameterVO {

	// �빮�ڷ� ǥ���Ұ�.
	private String MANDT;		// Ŭ���̾�Ʈ
	private String ZMPINC;		// �̵���ȹ����
	private String PLAN_YEAR;	// ��ȹ�⵵
	private String ZPYM; 		// �����
	private String ZPYM_TO;		// ����� TO
	private String ZPMONTHS;	// ��������
	private String ZOPFLG; 		// ���¿���
	private String ZCLFLG; 	 	// ��������
	private String ZRMK; 		// ���
	private String ROW_TYPE;	// rowŸ��

	
	public String getROW_TYPE() {
		return ROW_TYPE;
	}
	public void setROW_TYPE(String rOW_TYPE) {
		ROW_TYPE = rOW_TYPE;
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
	public String getZPYM_TO() {
		return ZPYM_TO;
	}
	public void setZPYM_TO(String zPYM_TO) {
		ZPYM_TO = zPYM_TO;
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
	public String getPLAN_YEAR() {
		return PLAN_YEAR;
	}
	public void setPLAN_YEAR(String pLAN_YEAR) {
		PLAN_YEAR = pLAN_YEAR;
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
	

	
	
}
