package hdel.sd.sbp.domain;

import hdel.lib.domain.CommonDomain;
import java.math.*;


/**
 * �����ȹ����/���� (Sbp0070) CommonDomain
 * @Comment 
 *     	- Sbp0070C(�����ȹ����/����) ���� ��� 
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


public class Sbp0070 extends CommonDomain {
	 
	public String CHK;		// üũ(1,0)
	public String FLAG;     // ���汸��(I,U,D) 
	public String USER_ID;  // �۾���ID
	
	public String MANDT;	// Cleint
	public String ZBPINC;	// �����ȹ����
	public String ZPYEAR;	// ���⵵
	public String ZOPFLG;	// ���¿���(����:X)
	public String ZCLFLG;	// �������� (����:X)
	public String ZRMK; 	// ���
	public String CDATE;    // �Է�����
	public String CTIME;    // �Է½ð�
	public String CUSER;    // �Է���ID
	public String UDATE;    // ��������
	public String UTIME;    // ����ð�
	public String UUSER;    // ������ID
	public String DDATE;    // ��������
	public String DTIME;    // �����ð�
	public String DUSER;    // ������ID 
 

	public String getCHK(){
		return CHK;
	}
	public String getFLAG(){
		return FLAG;
	} 
	public String getUSER_ID(){
		return USER_ID;
	}
	public String getMANDT(){
		return MANDT;
	}
	public String getZBPINC(){
		return ZBPINC;
	}
	public String getZPYEAR(){
		return ZPYEAR;
	}
	public String getZOPFLG(){
		return ZOPFLG;
	}
	public String getZCLFLG(){
		return ZCLFLG;
	}
	public String getZRMK(){
		return ZRMK;
	}
	public String getCDATE(){
		return CDATE;
	}
	public String getCTIME(){
		return CTIME;
	}
	public String getCUSER(){
		return CUSER;
	}
	public String getUDATE(){
		return UDATE;
	}
	public String getUTIME(){
		return UTIME;
	}
	public String getUUSER(){
		return UUSER;
	}
	public String getDDATE(){
		return DDATE;
	}
	public String getDTIME(){
		return DTIME;
	}
	public String getDUSER(){
		return DUSER;
	}
	 
	 
	public void setCHK(String aValue) {
		CHK=aValue;
	}
	public void setFLAG(String aValue) {
		FLAG=aValue;
	} 
	public void setUSER_ID(String aValue) {
		USER_ID=aValue;
	}
	 
	public void setMANDT(String aValue) {
		MANDT=aValue;
	}
	public void setZBPINC(String aValue) {
		ZBPINC=aValue;
	}
	public void setZPYEAR(String aValue) {
		ZPYEAR=aValue;
	}
	public void setZOPFLG(String aValue) {
		ZOPFLG=aValue;
	}
	public void setZCLFLG(String aValue) {
		ZCLFLG=aValue;
	}
	public void setZRMK(String aValue) {
		ZRMK=aValue;
	}
	public void setCDATE(String aValue) {
		CDATE=aValue;
	}
	public void setCTIME(String aValue) {
		CTIME=aValue;
	}
	public void setCUSER(String aValue) {
		CUSER=aValue;
	}
	public void setUDATE(String aValue) {
		UDATE=aValue;
	}
	public void setUTIME(String aValue) {
		UTIME=aValue;
	}
	public void setUUSER(String aValue) {
		UUSER=aValue;
	}
	public void setDDATE(String aValue) {
		DDATE=aValue;
	}
	public void setDTIME(String aValue) {
		DTIME=aValue;
	}
	public void setDUSER(String aValue) {
		DUSER=aValue;
	}
}