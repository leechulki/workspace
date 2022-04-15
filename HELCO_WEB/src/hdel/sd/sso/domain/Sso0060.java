package hdel.sd.sso.domain;

import hdel.lib.domain.CommonDomain; 
import java.math.*;

/**
 * ���ֻ���(Sso0060) CommonDomain
 * @Comment 
 *     	- Sso0060C(���ֻ���) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */ 


public class Sso0060 extends CommonDomain {
 
	private String CODE;			// ��Ʈ���ڵ� 
	private String NAME;			// ��Ʈ���ڵ��
	private String INFO;			// ��Ʈ���ڵ�ΰ�����
	private String VKBUR_NM;		// ����� �μ���
	private String VKGRP_NM;		// ����� ����
	private String QTNUM;			// ������ȣ
	private String QTSER;			// ��������
	private String QTSEQ;			// �����Ϸù�ȣ
	private String VBELN;			// ������Ʈ��ȣ   
	private String AUART;      		// �Ǹ� ���� ����
	private String SPART;    		// ��ǰ��
	private String VKBUR;   		// �����
	private String VKGRP;    		// ���� �׷�
	private String BSTKD;    		// �����
	private String WAERK;    		// SD ���� ��ȭ
	private String VDATU;    		// ��ǰ��û��
	private String KUNNR_RG; 		// �Ǹ�ó
	private String KUNNR_Z1; 		// ���¾�ü-�븮��
	private String KUNNR_Z2; 		// �����
	private String WWBLD;    		// �ǹ� �뵵
	private String MATNR;    		// ���� ��ȣ
	private BigDecimal NETWR;		// �ݾ�
	private BigDecimal WAVWR;		// ����
	private String ARKTX;     		// SPEC
	private Integer ZNUMBER;		// ���
	private String SONNR;		    // ���ְ�ȹ��ȣ
	private String ZPRGFLG;			// �����������
	private String UDATE;
	private String UTIME;
	private String UUSER;
	private String MANDT;
	private String INCO;
	private String INCO1;
	private String INCO2;
	
	private String RECAD_DA;		// ���ֽ�����
	private String QTDAT;
	
	private String KUNNR_Z3; 		// ������� �����
	private String KVGR1;           // ��꼭 ���� 
	private BigDecimal EXRATE;         // ȯ��
	private String CNT;
	private String LHCHK;
	private String GUBUN;
	
	private String SOPIND;			// ���尳������ ���� ������
	
	// 2020 �귣��
	private String BRNDCD;
	private String BRNDSEQ;
	
	private String MANAGER; 		// ���� �����
	private String LIFNRCHK;		// ������� 
	
	/**
	 * @return the cODE
	 */
	public String getCODE() {
		return CODE;
	}

	/**
	 * @param cODE the cODE to set
	 */
	public void setCODE(String cODE) {
		CODE = cODE;
	}

	/**
	 * @return the nAME
	 */
	public String getNAME() {
		return NAME;
	}

	/**
	 * @param nAME the nAME to set
	 */
	public void setNAME(String nAME) {
		NAME = nAME;
	}

	/**
	 * @return the iNFO
	 */
	public String getINFO() {
		return INFO;
	}

	/**
	 * @param iNFO the iNFO to set
	 */
	public void setINFO(String iNFO) {
		INFO = iNFO;
	}

	/**
	 * @return the vKBUR_NM
	 */
	public String getVKBUR_NM() {
		return VKBUR_NM;
	}

	/**
	 * @param vKBUR_NM the vKBUR_NM to set
	 */
	public void setVKBUR_NM(String vKBUR_NM) {
		VKBUR_NM = vKBUR_NM;
	}

	/**
	 * @return the vKGRP_NM
	 */
	public String getVKGRP_NM() {
		return VKGRP_NM;
	}

	/**
	 * @param vKGRP_NM the vKGRP_NM to set
	 */
	public void setVKGRP_NM(String vKGRP_NM) {
		VKGRP_NM = vKGRP_NM;
	}

	/**
	 * @return the qTNUM
	 */
	public String getQTNUM() {
		return QTNUM;
	}

	/**
	 * @param qTNUM the qTNUM to set
	 */
	public void setQTNUM(String qTNUM) {
		QTNUM = qTNUM;
	}

	/**
	 * @return the qTSER
	 */
	public String getQTSER() {
		return QTSER;
	}

	/**
	 * @param qTSER the qTSER to set
	 */
	public void setQTSER(String qTSER) {
		QTSER = qTSER;
	}

	/**
	 * @return the qTSEQ
	 */
	public String getQTSEQ() {
		return QTSEQ;
	}

	/**
	 * @param qTSEQ the qTSEQ to set
	 */
	public void setQTSEQ(String qTSEQ) {
		QTSEQ = qTSEQ;
	}

	/**
	 * @return the vBELN
	 */
	public String getVBELN() {
		return VBELN;
	}

	/**
	 * @param vBELN the vBELN to set
	 */
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}

	/**
	 * @return the aUART
	 */
	public String getAUART() {
		return AUART;
	}

	/**
	 * @param aUART the aUART to set
	 */
	public void setAUART(String aUART) {
		AUART = aUART;
	}

	/**
	 * @return the sPART
	 */
	public String getSPART() {
		return SPART;
	}

	/**
	 * @param sPART the sPART to set
	 */
	public void setSPART(String sPART) {
		SPART = sPART;
	}

	/**
	 * @return the vKBUR
	 */
	public String getVKBUR() {
		return VKBUR;
	}

	/**
	 * @param vKBUR the vKBUR to set
	 */
	public void setVKBUR(String vKBUR) {
		VKBUR = vKBUR;
	}

	/**
	 * @return the vKGRP
	 */
	public String getVKGRP() {
		return VKGRP;
	}

	/**
	 * @param vKGRP the vKGRP to set
	 */
	public void setVKGRP(String vKGRP) {
		VKGRP = vKGRP;
	}

	/**
	 * @return the bSTKD
	 */
	public String getBSTKD() {
		return BSTKD;
	}

	/**
	 * @param bSTKD the bSTKD to set
	 */
	public void setBSTKD(String bSTKD) {
		BSTKD = bSTKD;
	}

	/**
	 * @return the wAERK
	 */
	public String getWAERK() {
		return WAERK;
	}

	/**
	 * @param wAERK the wAERK to set
	 */
	public void setWAERK(String wAERK) {
		WAERK = wAERK;
	}

	/**
	 * @return the vDATU
	 */
	public String getVDATU() {
		return VDATU;
	}

	/**
	 * @param vDATU the vDATU to set
	 */
	public void setVDATU(String vDATU) {
		VDATU = vDATU;
	}

	/**
	 * @return the kUNNR_RG
	 */
	public String getKUNNR_RG() {
		return KUNNR_RG;
	}

	/**
	 * @param kUNNR_RG the kUNNR_RG to set
	 */
	public void setKUNNR_RG(String kUNNR_RG) {
		KUNNR_RG = kUNNR_RG;
	}

	/**
	 * @return the kUNNR_Z1
	 */
	public String getKUNNR_Z1() {
		return KUNNR_Z1;
	}

	/**
	 * @param kUNNR_Z1 the kUNNR_Z1 to set
	 */
	public void setKUNNR_Z1(String kUNNR_Z1) {
		KUNNR_Z1 = kUNNR_Z1;
	}

	/**
	 * @return the kUNNR_Z2
	 */
	public String getKUNNR_Z2() {
		return KUNNR_Z2;
	}

	/**
	 * @param kUNNR_Z2 the kUNNR_Z2 to set
	 */
	public void setKUNNR_Z2(String kUNNR_Z2) {
		KUNNR_Z2 = kUNNR_Z2;
	}

	/**
	 * @return the wWBLD
	 */
	public String getWWBLD() {
		return WWBLD;
	}

	/**
	 * @param wWBLD the wWBLD to set
	 */
	public void setWWBLD(String wWBLD) {
		WWBLD = wWBLD;
	}

	/**
	 * @return the mATNR
	 */
	public String getMATNR() {
		return MATNR;
	}

	/**
	 * @param mATNR the mATNR to set
	 */
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	/**
	 * @return the nETWR
	 */
	public BigDecimal getNETWR() {
		return NETWR;
	}

	/**
	 * @param nETWR the nETWR to set
	 */
	public void setNETWR(BigDecimal nETWR) {
		NETWR = nETWR;
	}

	/**
	 * @return the wAVWR
	 */
	public BigDecimal getWAVWR() {
		return WAVWR;
	}

	/**
	 * @param wAVWR the wAVWR to set
	 */
	public void setWAVWR(BigDecimal wAVWR) {
		WAVWR = wAVWR;
	}

	/**
	 * @return the aRKTX
	 */
	public String getARKTX() {
		return ARKTX;
	}

	/**
	 * @param aRKTX the aRKTX to set
	 */
	public void setARKTX(String aRKTX) {
		ARKTX = aRKTX;
	}

	/**
	 * @return the zNUMBER
	 */
	public Integer getZNUMBER() {
		return ZNUMBER;
	}

	/**
	 * @param zNUMBER the zNUMBER to set
	 */
	public void setZNUMBER(Integer zNUMBER) {
		ZNUMBER = zNUMBER;
	}

	/**
	 * @return the sONNR
	 */
	public String getSONNR() {
		return SONNR;
	}

	/**
	 * @param sONNR the sONNR to set
	 */
	public void setSONNR(String sONNR) {
		SONNR = sONNR;
	}

	/**
	 * @return the zPRGFLG
	 */
	public String getZPRGFLG() {
		return ZPRGFLG;
	}

	/**
	 * @param zPRGFLG the zPRGFLG to set
	 */
	public void setZPRGFLG(String zPRGFLG) {
		ZPRGFLG = zPRGFLG;
	}

	/**
	 * @return the uDATE
	 */
	public String getUDATE() {
		return UDATE;
	}

	/**
	 * @param uDATE the uDATE to set
	 */
	public void setUDATE(String uDATE) {
		UDATE = uDATE;
	}

	/**
	 * @return the uTIME
	 */
	public String getUTIME() {
		return UTIME;
	}

	/**
	 * @param uTIME the uTIME to set
	 */
	public void setUTIME(String uTIME) {
		UTIME = uTIME;
	}

	/**
	 * @return the uUSER
	 */
	public String getUUSER() {
		return UUSER;
	}

	/**
	 * @param uUSER the uUSER to set
	 */
	public void setUUSER(String uUSER) {
		UUSER = uUSER;
	}

	/**
	 * @return the mANDT
	 */
	public String getMANDT() {
		return MANDT;
	}

	/**
	 * @param mANDT the mANDT to set
	 */
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}

	/**
	 * @return the iNCO
	 */
	public String getINCO() {
		return INCO;
	}

	/**
	 * @param iNCO the iNCO to set
	 */
	public void setINCO(String iNCO) {
		INCO = iNCO;
	}

	/**
	 * @return the iNCO1
	 */
	public String getINCO1() {
		return INCO1;
	}

	/**
	 * @param iNCO1 the iNCO1 to set
	 */
	public void setINCO1(String iNCO1) {
		INCO1 = iNCO1;
	}

	/**
	 * @return the iNCO2
	 */
	public String getINCO2() {
		return INCO2;
	}

	/**
	 * @param iNCO2 the iNCO2 to set
	 */
	public void setINCO2(String iNCO2) {
		INCO2 = iNCO2;
	}

	/**
	 * @return the rECAD_DA
	 */
	public String getRECAD_DA() {
		return RECAD_DA;
	}

	/**
	 * @param rECAD_DA the rECAD_DA to set
	 */
	public void setRECAD_DA(String rECAD_DA) {
		RECAD_DA = rECAD_DA;
	}

	public String getQTDAT() {
		return QTDAT;
	}

	public void setQTDAT(String qTDAT) {
		QTDAT = qTDAT;
	}
	
	/**
	 * @return the kUNNR_Z3
	 */
	public String getKUNNR_Z3() {
		return KUNNR_Z3;
	}

	/**
	 * @param kUNNR_Z3 the kUNNR_Z3 to set
	 */
	public void setKUNNR_Z3(String kUNNR_Z3) {
		KUNNR_Z3 = kUNNR_Z3;
	}
	
	/**
	 * @return the kVGR1
	 */
	public String getKVGR1() {
		return KVGR1;
	}

	/**
	 * @param kVGR1 the kVGR1 to set
	 */
	public void setKVGR1(String kVGR1) {
		KVGR1 = kVGR1;
	}
	
	/**
	 * @return the eXRATE
	 */
	public BigDecimal getEXRATE() {
		return EXRATE;
	}

	/**
	 * @param eXRATE the eXRATE to set
	 */
	public void setEXRATE(BigDecimal eXRATE) {
		EXRATE = eXRATE;
	}
	public String getCNT() {
		return CNT;
	}
	public void setCNT(String cNT) {
		CNT = cNT;
	}

	public String getLHCHK() {
		return LHCHK;
	}

	public void setLHCHK(String lHCHK) {
		LHCHK = lHCHK;
	}

	public String getGUBUN() {
		return GUBUN;
	}

	public void setGUBUN(String gUBUN) {
		GUBUN = gUBUN;
	}

	public String getSOPIND() {
		return SOPIND;
	}

	public void setSOPIND(String sOPIND) {
		SOPIND = sOPIND;
	}
	
	// 2020 �귣��
	public String getBRNDCD() {
		return BRNDCD;
	}

	public void setBRNDCD(String bRNDCD) {
		BRNDCD = bRNDCD;
	}

	public String getBRNDSEQ() {
		return BRNDSEQ;
	}

	public void setBRNDSEQ(String bRNDSEQ) {
		BRNDSEQ = bRNDSEQ;
	}

	public String getMANAGER() {
		return MANAGER;
	}

	public void setMANAGER(String mANAGER) {
		MANAGER = mANAGER;
	}

	/**
	 * @return the lIFNRCHK
	 */
	public String getLIFNRCHK() {
		return LIFNRCHK;
	}

	/**
	 * @param lIFNRCHK the lIFNRCHK to set
	 */
	public void setLIFNRCHK(String lIFNRCHK) {
		LIFNRCHK = lIFNRCHK;
	}
	
}
