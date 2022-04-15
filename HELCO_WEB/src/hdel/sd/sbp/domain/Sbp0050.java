package hdel.sd.sbp.domain;

import hdel.lib.domain.CommonDomain;
import java.math.*;


/**
 * �����ȹ����ä�ǰ���(���⺸��) (Sbp0050) CommonDomain
 * @Comment 
 *     	- Sbp0050C(�����ȹ����ä�ǰ���(���⺸��)) ���� ��� 
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


public class Sbp0050 extends CommonDomain {
	
	public String OPENDTM;  // �����Ͻ�
	public String ZBPINC;  	// �����ȹ����
	public String ZOPFLG;  	// ���¿���(Y,N)
	
	public String SAVE_YN;	// ���忩��(Y,N)
	public String CHK;		// üũ(1,0)
	public String FLAG;     // ���汸��(I,U,D)
	public String NEW_YN;   // �űԿ���
	public String USER_ID;  // �۾���ID
	
	public String MANDT;	// SAP CLIENT
	public String ZBPNNR;   // �����ȹ��ȣ
	public String ZPYEAR;   // ���⵵
	public String ZPYM;     // ��ȹ���
	public String SPART;    // ��ǰ��
	public String AUART;    // �ǸŹ�������
	public String ZSVCGBN;  // ��������
	public String MATNR;    // �����ȣ
	public String VKBUR;    // ������ڵ�
	public String VKGRP;    // �����׷��ڵ�
	public String ZKUNNR;   // ��������ڵ�
	public String ZKUNNR_NM;   // ���������
	public String GTYPE;    // ����
	public int 	  ZNUMBER;  // ���
	public String KUNNR;    // ����ȣ(�ŷ����ڵ�)
	public String KUNNR_NM; // ����(�ŷ�����)
	public String GSNAM;    // �����
	public String REGION;   // ��ġ�����ڵ�
	public String ZBDGBN;   // ��������
	public String ZBDTYP;   // �ǹ��뵵
	public String ZDELI;    // �ܳ��ⱸ��
	public BigDecimal SOFOCA;   // ���ֿ����
	public BigDecimal ZFORE;    // ���������
	public String WAERK;    // ��ȭ
	public String DELDAT;   // ���⿹����
	public String ZRMK;     // ���
	public String ZRMK1;    // ��� (replace(ZRMK, chr(13)||chr(10), ' ')
	public String VBELN;    // �ǸŹ���
	public String POSID;    // ������Ʈ
	public String CDATE;    // �Է�����
	public String CTIME;    // �Է½ð�
	public String CUSER;    // �Է���ID
	public String UDATE;    // ��������
	public String UTIME;    // ����ð�
	public String UUSER;    // ������ID
	public String DDATE;    // ��������
	public String DTIME;    // �����ð�
	public String DUSER;    // ������ID
	

	public String ZAGNT;    // �븮��
	public String ZAGNT_NM; // �븮����

	public String LAND1; 	// �����ڵ�
	public String LAND1_NM; // �����ڵ��
	public String ZAREA; 	// �ǿ��ڵ�
	public String ZAREA_NM; // �ǿ��ڵ��
	
	
	public String 	  GBN;		// ����/û��/���� ���� (1, 2, 3 : order by ����)
	public String 	  GBN_NM;	// ����/û��/���� ���� �� (1:����, 2:û��, 3:����)
	public BigDecimal AMT00;	// ���س��+00�� �ݾ�
	public BigDecimal AMT01;	// ���س��+01�� �ݾ�
	public BigDecimal AMT02;	// ���س��+02�� �ݾ�
	public BigDecimal AMT03;	// ���س��+03�� �ݾ�
	public BigDecimal AMT04;	// ���س��+04�� �ݾ�
	public BigDecimal AMT05;	// ���س��+05�� �ݾ�
	public BigDecimal AMT06;	// ���س��+06�� �ݾ�
	public BigDecimal AMT07;	// ���س��+07�� �ݾ�
	public BigDecimal AMT08;	// ���س��+08�� �ݾ�
	public BigDecimal AMT09;	// ���س��+09�� �ݾ�
	public BigDecimal AMT10;	// ���س��+10�� �ݾ�
	public BigDecimal AMT11;	// ���س��+11�� �ݾ�
	public BigDecimal AMT12;	// ���س��+12�� �ݾ�
	public BigDecimal AMT13;	// ���س��+13�� �ݾ�
	public BigDecimal AMT14;	// ���س��+14�� �ݾ�
	public BigDecimal AMT15;	// ���س��+15�� �ݾ�
	public BigDecimal AMT16;	// ���س��+16�� �ݾ�
	public BigDecimal AMT17;	// ���س��+17�� �ݾ�
	public BigDecimal AMT18;	// ���س��+18�� �ݾ�
	public BigDecimal AMT19;	// ���س��+19�� �ݾ�
	public BigDecimal AMT20;	// ���س��+20�� �ݾ�
	public BigDecimal AMT21;	// ���س��+21�� �ݾ�
	public BigDecimal AMT22;	// ���س��+22�� �ݾ�
	public BigDecimal AMT23;	// ���س��+23�� �ݾ�
	public BigDecimal AMT24;	// ���س��+24�� �ݾ�
	public BigDecimal AMT25;	// ���س��+25�� �ݾ�
	public BigDecimal AMT26;	// ���س��+26�� �ݾ�
	public BigDecimal AMT27;	// ���س��+27�� �ݾ� 
	public BigDecimal AMT28;	// ���س��+28�� �ݾ�
	public BigDecimal AMT29;	// ���س��+29�� �ݾ�  
	public String     PSPIDSV;	// ����������Ʈ��ȣ(�����ȹ��ȣ)
	public String     ZHOGISV;	// ����ȣ���ȣ
	public String     ZSAYM;	// ������
	public String     ZRQYM;	// û�����
	public String     ZYSYM;	// ���ݳ�� 
	public BigDecimal NETWR_SA;	// ���⿹��ݾ�
	public BigDecimal NETWR_RQ;	// û������ݾ�
	public BigDecimal COLBI;	// ���ݿ���ݾ�
 
	public String getOPENDTM(){
		return OPENDTM;
	} 
	public String getZBPINC(){
		return ZBPINC;
	}
	public String getZOPFLG(){
		return ZOPFLG;
	} 
	public String getSAVE_YN(){
		return SAVE_YN;
	}
	public String getCHK(){
		return CHK;
	}
	public String getFLAG(){
		return FLAG;
	}
	public String getNEW_YN(){
		return NEW_YN;
	}
	public String getUSER_ID(){
		return USER_ID;
	}
	public String getMANDT(){
		return MANDT;
	}
	public String getZBPNNR(){
		return ZBPNNR;
	}
	public String getZPYEAR(){
		return ZPYEAR;
	}
	public String getZPYM(){
		return ZPYM;
	}
	public String getSPART(){
		return SPART;
	}
	public String getAUART(){
		return AUART;
	}
	public String getZSVCGBN(){
		return ZSVCGBN;
	}
	public String getMATNR(){
		return MATNR;
	}
	public String getVKBUR(){
		return VKBUR;
	}
	public String getVKGRP(){
		return VKGRP;
	}
	public String getZKUNNR(){
		return ZKUNNR;
	}
	public String getZKUNNR_NM(){
		return ZKUNNR_NM;
	}
	public String getGTYPE(){
		return GTYPE;
	} 
	public int getZNUMBER(){
		return ZNUMBER;
	}
	public String getKUNNR(){
		return KUNNR;
	}
	public String getKUNNR_NM(){
		return KUNNR_NM;
	}
	public String getGSNAM(){
		return GSNAM;
	}
	public String getREGION(){
		return REGION;
	}
	public String getZBDGBN(){
		return ZBDGBN;
	}
	public String getZBDTYP(){
		return ZBDTYP;
	}
	public String getZDELI(){
		return ZDELI;
	} 
	public BigDecimal getSOFOCA(){
		return SOFOCA;
	}
	public BigDecimal getZFORE(){
		return ZFORE;
	}
	public String getWAERK(){
		return WAERK;
	}
	public String getDELDAT(){
		return DELDAT;
	}
	public String getZRMK(){
		return ZRMK;
	}
	public String getZRMK1(){
		return ZRMK1;
	}
	public String getVBELN(){
		return VBELN;
	}
	public String getPOSID(){
		return POSID;
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
	 

	public String getZAGNT(){
		return ZAGNT;
	}
	public String getZAGNT_NM(){
		return ZAGNT_NM;
	}
 
	public String getLAND1(){
		return LAND1;
	}  
	public String getLAND1_NM(){
		return LAND1_NM;
	}  
	public String getZAREA(){
		return ZAREA;
	}  
	public String getZAREA_NM(){
		return ZAREA_NM;
	}  
	
	public String getGBN(){
		return GBN;
	}
	public String getGBN_NM(){
		return GBN_NM;
	} 
	public BigDecimal getAMT00(){
		return AMT00;
	}
	public BigDecimal getAMT01(){
		return AMT01;
	}
	public BigDecimal getAMT02(){
		return AMT02;
	}
	public BigDecimal getAMT03(){
		return AMT03;
	}
	public BigDecimal getAMT04(){
		return AMT04;
	}
	public BigDecimal getAMT05(){
		return AMT05;
	}
	public BigDecimal getAMT06(){
		return AMT06;
	}
	public BigDecimal getAMT07(){
		return AMT07;
	}
	public BigDecimal getAMT08(){
		return AMT08;
	}
	public BigDecimal getAMT09(){
		return AMT09;
	}
	public BigDecimal getAMT10(){
		return AMT10;
	}
	public BigDecimal getAMT11(){
		return AMT11;
	}
	public BigDecimal getAMT12(){
		return AMT12;
	}
	public BigDecimal getAMT13(){
		return AMT13;
	}
	public BigDecimal getAMT14(){
		return AMT14;
	}
	public BigDecimal getAMT15(){
		return AMT15;
	}
	public BigDecimal getAMT16(){
		return AMT16;
	}
	public BigDecimal getAMT17(){
		return AMT17;
	} 

	public BigDecimal getAMT18(){
		return AMT18;
	}
	public BigDecimal getAMT19(){
		return AMT19;
	}
	public BigDecimal getAMT20(){
		return AMT20;
	}
	public BigDecimal getAMT21(){
		return AMT21;
	}
	public BigDecimal getAMT22(){
		return AMT22;
	}
	public BigDecimal getAMT23(){
		return AMT23;
	}
	public BigDecimal getAMT24(){
		return AMT24;
	}
	public BigDecimal getAMT25(){
		return AMT25;
	}
	public BigDecimal getAMT26(){
		return AMT26;
	}
	public BigDecimal getAMT27(){
		return AMT27;
	} 
	public BigDecimal getAMT28(){
		return AMT28;
	}
	public BigDecimal getAMT29(){
		return AMT29;
	}
	 

	public String getPSPIDSV(){
		return PSPIDSV;
	}
	public String getZHOGISV(){
		return ZHOGISV;
	}
	public String getZSAYM(){
		return ZSAYM;
	}
	public String getZRQYM(){
		return ZRQYM;
	}
	public String getZYSYM(){
		return ZYSYM;
	}
	public BigDecimal getNETWR_SA(){
		return NETWR_SA;
	} 
	public BigDecimal getNETWR_RQ(){
		return NETWR_RQ;
	} 
	public BigDecimal getCOLBI(){
		return COLBI;
	} 
	  
	
	public void setOPENDTM(String aValue) {
		OPENDTM=aValue;
	} 
	public void setZBPINC(String aValue) {
		ZBPINC=aValue;
	} 
	public void setZOPFLG(String aValue) {
		ZOPFLG=aValue;
	} 
	
	public void setSAVE_YN(String aValue) {
		SAVE_YN=aValue;
	}
	public void setCHK(String aValue) {
		CHK=aValue;
	}
	public void setFLAG(String aValue) {
		FLAG=aValue;
	}
	public void setNEW_YN(String aValue) {
		NEW_YN=aValue;
	}
	public void setUSER_ID(String aValue) {
		USER_ID=aValue;
	}
	 
	public void setMANDT(String aValue) {
		MANDT=aValue;
	}
	public void setZBPNNR(String aValue) {
		ZBPNNR=aValue;
	}
	public void setZPYEAR(String aValue) {
		ZPYEAR=aValue;
	}
	public void setZPYM(String aValue) {
		ZPYM=aValue;
	}
	public void setSPART(String aValue) {
		SPART=aValue;
	}
	public void setAUART(String aValue) {
		AUART=aValue;
	}
	public void setZSVCGBN(String aValue) {
		ZSVCGBN=aValue;
	}
	public void setMATNR(String aValue) {
		MATNR=aValue;
	}
	public void setVKBUR(String aValue) {
		VKBUR=aValue;
	}
	public void setVKGRP(String aValue) {
		VKGRP=aValue;
	}
	public void setZKUNNR(String aValue) {
		ZKUNNR=aValue;
	}
	public void setZKUNNR_NM(String aValue) {
		ZKUNNR_NM=aValue;
	}
	public void setGTYPE(String aValue) {
		GTYPE=aValue;
	} 
	public void setZNUMBER(int aValue) {
		ZNUMBER=aValue;
	}
	public void setKUNNR(String aValue) {
		KUNNR=aValue;
	}
	public void setKUNNR_NM(String aValue) {
		KUNNR_NM=aValue;
	}
	public void setGSNAM(String aValue) {
		GSNAM=aValue;
	}
	public void setREGION(String aValue) {
		REGION=aValue;
	}
	public void setZBDGBN(String aValue) {
		ZBDGBN=aValue;
	}
	public void setZBDTYP(String aValue) {
		ZBDTYP=aValue;
	}
	public void setZDELI(String aValue) {
		ZDELI=aValue;
	} 
	public void setSOFOCA(BigDecimal aValue) {
		SOFOCA=aValue;
	}
	public void setZFORE(BigDecimal aValue) {
		ZFORE=aValue;
	}
	public void setWAERK(String aValue) {
		WAERK=aValue;
	}
	public void setDELDAT(String aValue) {
		DELDAT=aValue;
	}
	public void setZRMK(String aValue) {
		ZRMK=aValue;
	}
	public void setZRMK1(String aValue) {
		ZRMK1=aValue;
	}
	public void setVBELN(String aValue) {
		VBELN=aValue;
	}
	public void setPOSID(String aValue) {
		POSID=aValue;
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
	


	public void setZAGNT(String aValue) {
		ZAGNT=aValue;
	}
	public void setZAGNT_NM(String aValue) {
		ZAGNT_NM=aValue;
	} 
	public void setLAND1(String aValue) {
		LAND1=aValue;
	} 
	public void setLAND1_NM(String aValue) {
		LAND1_NM=aValue;
	} 
	public void setZAREA(String aValue) {
		ZAREA=aValue;
	} 
	public void setZAREA_NM(String aValue) {
		ZAREA_NM=aValue;
	}  
	
	public void setGBN(String aValue) {
		GBN=aValue;
	}
	public void setGBN_NM(String aValue) {
		GBN_NM=aValue;
	} 
	public void setAMT00(BigDecimal aValue) {
		AMT00=aValue;
	}
	public void setAMT01(BigDecimal aValue) {
		AMT01=aValue;
	}
	public void setAMT02(BigDecimal aValue) {
		AMT02=aValue;
	}
	public void setAMT03(BigDecimal aValue) {
		AMT03=aValue;
	}
	public void setAMT04(BigDecimal aValue) {
		AMT04=aValue;
	}
	public void setAMT05(BigDecimal aValue) {
		AMT05=aValue;
	}
	public void setAMT06(BigDecimal aValue) {
		AMT06=aValue;
	}
	public void setAMT07(BigDecimal aValue) {
		AMT07=aValue;
	}
	public void setAMT08(BigDecimal aValue) {
		AMT08=aValue;
	}
	public void setAMT09(BigDecimal aValue) {
		AMT09=aValue;
	}
	public void setAMT10(BigDecimal aValue) {
		AMT10=aValue;
	}
	public void setAMT11(BigDecimal aValue) {
		AMT11=aValue;
	}
	public void setAMT12(BigDecimal aValue) {
		AMT12=aValue;
	}
	public void setAMT13(BigDecimal aValue) {
		AMT13=aValue;
	}
	public void setAMT14(BigDecimal aValue) {
		AMT14=aValue;
	}
	public void setAMT15(BigDecimal aValue) {
		AMT15=aValue;
	}
	public void setAMT16(BigDecimal aValue) {
		AMT16=aValue;
	}
	public void setAMT17(BigDecimal aValue) {
		AMT17=aValue;
	} 
	public void setAMT18(BigDecimal aValue) {
		AMT18=aValue;
	}
	public void setAMT19(BigDecimal aValue) {
		AMT19=aValue;
	}
	public void setAMT20(BigDecimal aValue) {
		AMT20=aValue;
	}
	public void setAMT21(BigDecimal aValue) {
		AMT21=aValue;
	}
	public void setAMT22(BigDecimal aValue) {
		AMT22=aValue;
	}
	public void setAMT23(BigDecimal aValue) {
		AMT23=aValue;
	}
	public void setAMT24(BigDecimal aValue) {
		AMT24=aValue;
	}
	public void setAMT25(BigDecimal aValue) {
		AMT25=aValue;
	}
	public void setAMT26(BigDecimal aValue) {
		AMT26=aValue;
	}
	public void setAMT27(BigDecimal aValue) {
		AMT27=aValue;
	}
	public void setAMT28(BigDecimal aValue) {
		AMT28=aValue;
	}
	public void setAMT29(BigDecimal aValue) {
		AMT29=aValue;
	}
	
	

	public void setPSPIDSV(String aValue) {
		PSPIDSV=aValue;
	}
	public void setZHOGISV(String aValue) {
		ZHOGISV=aValue;
	}
	public void setZSAYM(String aValue) {
		ZSAYM=aValue;
	}
	public void setZRQYM(String aValue) {
		ZRQYM=aValue;
	}
	public void setZYSYM(String aValue) {
		ZYSYM=aValue;
	}
	public void setNETWR_SA(BigDecimal aValue) {
		NETWR_SA=aValue;
	}
	public void setNETWR_RQ(BigDecimal aValue) {
		NETWR_RQ=aValue;
	}
	public void setCOLBI(BigDecimal aValue) {
		COLBI=aValue;
	} 
	  
	
}