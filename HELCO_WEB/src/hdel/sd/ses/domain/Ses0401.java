package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0401 extends CommonDomain {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String ZRQSEQ;
	private String ZRQFLG;
	private String ZRQDAT;
	private String ZRQCN;
	private String ZRQSTAT;
	private String ZRSRLT;
	private String KUNNR_Z3;
	private String KUNNR_Z3_NM;
	private String QTSO_NO;
	private String QTSER;
	private String QTSEQ;
	private String HOGI;
	private String FWRD;
	private String FINDAT;
	private String CONFDAT;
	private String CONFDOCU;
	private String OUTGBN;
	private String OUTSRCDT;
	private String OUTFINDT;
	private String OUTCANDT;
	private String OUTACTDT;
	private String OUTRETDT;
	private String OUTGRD;
	private String OUTLOQ;
	private String OUTMAN;
	private String LODAT;
	private String CODAT;
	private String SPDOCU;
	private String LP_IS;
	private String LP_CHN;
	private String LP_OLD;
	private String NONSTD;
	private String APR_IS;
	private String DRW_IS;
	private String CDATE;
	private String CTIME;
	private String CUSER;
	private String UDATE;
	private String UTIME;
	private String UUSER;
	private String UUSER_TEL;
	private String KUN_EMAIL;	
	private String GVGCD;
	
	//파일첨부
	private String FLAG;
	private String ZATTSEQ;
	private String ZATGBN;
	private String ZATTPATH;
	private String ZATTFN;
	private String FILEGBN;
	
	
	// 출력물
	private String VKBUR;
	private String VKBUR_NM;
	private String VKGRP;
	private String VKGRP_NM;
	private String ZKUNNR;
	private String ZAGNT;
	private String KUNNR;
	private String GSNAM;
	private String DELDAT;
	private String ZPID;
	private String ZTEL;
	private String ZAGNT_NM;
	private String ZKUNNR_NM;
	private String ZKUNNR_TEL;
	private String KUNNR_NM;
	private String POSNR;
	
	private String PRH;
	private String PRD;
	private String ATKLA;
	private String PRHNAME;
	
	private String ZKUN_EMAIL;
	private String ZKUNNR_Z3;

	private String CADREQTYPE;
	private String CISTAT;
	private String ERROR_TEXT;
	
	private String USERCODE;

	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG == null ? "" : fLAG;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT == null ? "" : mANDT;
	}
	public String getZRQSEQ() {
		return ZRQSEQ;
	}
	public void setZRQSEQ(String zRQSEQ) {
		ZRQSEQ = zRQSEQ == null ? "" :  zRQSEQ;
	}
	public String getZRQFLG() {
		return ZRQFLG;
	}
	public void setZRQFLG(String zRQFLG) {
		ZRQFLG = zRQFLG == null ? "" : zRQFLG;
	}
	public String getZRQDAT() {
		return ZRQDAT;
	}
	public void setZRQDAT(String zRQDAT) {
		ZRQDAT = zRQDAT == null ? "" : zRQDAT;
	}
	public String getZRQCN() {
		return ZRQCN;
	}
	public void setZRQCN(String zRQCN) {
		ZRQCN = zRQCN == null ? "" : zRQCN;
	}
	public String getZRQSTAT() {
		return ZRQSTAT;
	}
	public void setZRQSTAT(String zRQSTAT) {
		ZRQSTAT = zRQSTAT == null ? "" : zRQSTAT;
	}
	public String getZRSRLT() {
		return ZRSRLT;
	}
	public void setZRSRLT(String zRSRLT) {
		ZRSRLT = zRSRLT == null ? "" : zRSRLT;
	}
	public String getKUNNR_Z3() {
		return KUNNR_Z3;
	}
	public void setKUNNR_Z3(String kUNNR_Z3) {
		KUNNR_Z3 = kUNNR_Z3 == null ? "" : kUNNR_Z3;
	}
	public String getQTSO_NO() {
		return QTSO_NO;
	}
	public void setQTSO_NO(String qTSO_NO) {
		QTSO_NO = qTSO_NO == null ? "" : qTSO_NO;
	}
	public String getQTSER() {
		return QTSER;
	}
	public void setQTSER(String qTSER) {
		QTSER = qTSER == null ? "" : qTSER;
	}
	public String getQTSEQ() {
		return QTSEQ;
	}
	public void setQTSEQ(String qTSEQ) {
		QTSEQ = qTSEQ == null ? "" : qTSEQ;
	}
	public String getHOGI() {
		return HOGI;
	}
	public void setHOGI(String hOGI) {
		HOGI = hOGI == null ? "" : hOGI;
	}	
	public String getFWRD() {
		return FWRD;
	}
	public void setFWRD(String fWRD) {
		FWRD = fWRD == null ? "" : fWRD;
	}
	public String getFINDAT() {
		return FINDAT;
	}
	public void setFINDAT(String fINDAT) {
		FINDAT = fINDAT == null ? "" : fINDAT;
	}

	public String getCONFDAT() {
		return CONFDAT;
	}
	public void setCONFDAT(String cONFDAT) {
		CONFDAT = cONFDAT == null ? "" : cONFDAT;
	}
	
	public String getCONFDOCU() {
		return CONFDOCU;
	}
	public void setCONFDOCU(String cONFDOCU) {
		CONFDOCU = cONFDOCU == null ? "" : cONFDOCU;
	}

	public String getOUTGBN() {
		return OUTGBN;
	}
	public void setOUTGBN(String oUTGBN) {
		OUTGBN = oUTGBN == null ? "" : oUTGBN;
	}
	public String getOUTSRCDT() {
		return OUTSRCDT;
	}
	public void setOUTSRCDT(String oUTSRCDT) {
		OUTSRCDT = oUTSRCDT == null ? "" : oUTSRCDT;
	}
	public String getOUTFINDT() {
		return OUTFINDT;
	}
	public void setOUTFINDT(String oUTFINDT) {
		OUTFINDT = oUTFINDT == null ? "" : oUTFINDT;
	}	
	public String getLODAT() {
		return LODAT;
	}
	public void setLODAT(String lODAT) {
		LODAT = lODAT == null ? "" : lODAT;
	}
	public String getCODAT() {
		return CODAT;
	}
	public void setCODAT(String cODAT) {
		CODAT = cODAT == null ? "" : cODAT;
	}
	public String getSPDOCU() {
		return SPDOCU;
	}
	public void setSPDOCU(String sPDOCU) {
		SPDOCU = sPDOCU == null ? "" : sPDOCU;
	}

	public String getLP_IS() {
		return LP_IS;
	}
	public void setLP_IS(String lP_IS) {
		LP_IS = lP_IS == null ? "" : lP_IS;
	}
	public String getLP_CHN() {
		return LP_CHN;
	}
	public void setLP_CHN(String lP_CHN) {
		LP_CHN = lP_CHN == null ? "" : lP_CHN;
	}
	
	public String getLP_OLD() {
		return LP_OLD;
	}
	public void setLP_OLD(String lP_OLD) {
		LP_OLD = lP_OLD == null ? "" : lP_OLD;
	}
	public String getNONSTD() {
		return NONSTD;
	}
	public void setNONSTD(String nONSTD) {
		NONSTD = nONSTD == null ? "" : nONSTD;
	}
	public String getAPR_IS() {
		return APR_IS;
	}
	public void setAPR_IS(String aPR_IS) {
		APR_IS = aPR_IS == null ? "" : aPR_IS;
	}
	public String getDRW_IS() {
		return DRW_IS;
	}
	public void setDRW_IS(String dRW_IS) {
		DRW_IS = dRW_IS == null ? "" : dRW_IS;
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
		UUSER = uUSER == null ? "" : uUSER;
	}
	public String getZATTSEQ() {
		return ZATTSEQ;
	}
	public void setZATTSEQ(String zATTSEQ) {
		ZATTSEQ = zATTSEQ == null ? "" : zATTSEQ;
	}
	public String getZATTPATH() {
		return ZATTPATH;
	}
	public void setZATTPATH(String zATTPATH) {
		ZATTPATH = zATTPATH == null ? "" : zATTPATH;
	}
	public String getZATTFN() {
		return ZATTFN;
	}
	public void setZATTFN(String zATTFN) {
		ZATTFN = zATTFN == null ? "" : zATTFN;
	}
	public String getZATGBN() {
		return ZATGBN;
	}
	public void setZATGBN(String zATGBN) {
		ZATGBN = zATGBN == null ? "" : zATGBN;
	}
	public String getGVGCD() {
		return GVGCD;
	}
	public void setGVGCD(String gVGCD) {
		GVGCD = gVGCD;
	}
	/**
	 * @return the kUNNR_Z3_NM
	 */
	public String getKUNNR_Z3_NM() {
		return KUNNR_Z3_NM;
	}
	/**
	 * @param kUNNR_Z3_NM the kUNNR_Z3_NM to set
	 */
	public void setKUNNR_Z3_NM(String kUNNR_Z3_NM) {
		KUNNR_Z3_NM = kUNNR_Z3_NM;
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
	 * @return the zKUNNR
	 */
	public String getZKUNNR() {
		return ZKUNNR;
	}
	/**
	 * @param zKUNNR the zKUNNR to set
	 */
	public void setZKUNNR(String zKUNNR) {
		ZKUNNR = zKUNNR;
	}
	/**
	 * @return the zAGNT
	 */
	public String getZAGNT() {
		return ZAGNT;
	}
	/**
	 * @param zAGNT the zAGNT to set
	 */
	public void setZAGNT(String zAGNT) {
		ZAGNT = zAGNT;
	}
	/**
	 * @return the kUNNR
	 */
	public String getKUNNR() {
		return KUNNR;
	}
	/**
	 * @param kUNNR the kUNNR to set
	 */
	public void setKUNNR(String kUNNR) {
		KUNNR = kUNNR;
	}
	/**
	 * @return the gSNAM
	 */
	public String getGSNAM() {
		return GSNAM;
	}
	/**
	 * @param gSNAM the gSNAM to set
	 */
	public void setGSNAM(String gSNAM) {
		GSNAM = gSNAM == null ? "" : gSNAM;
	}
	/**
	 * @return the dELDAT
	 */
	public String getDELDAT() {
		return DELDAT;
	}
	/**
	 * @param dELDAT the dELDAT to set
	 */
	public void setDELDAT(String dELDAT) {
		DELDAT = dELDAT;
	}
	/**
	 * @return the zPID
	 */
	public String getZPID() {
		return ZPID;
	}
	/**
	 * @param zPID the zPID to set
	 */
	public void setZPID(String zPID) {
		ZPID = zPID;
	}
	/**
	 * @return the zTEL
	 */
	public String getZTEL() {
		return ZTEL;
	}
	/**
	 * @param zTEL the zTEL to set
	 */
	public void setZTEL(String zTEL) {
		ZTEL = zTEL;
	}
	/**
	 * @return the zAGNT_NM
	 */
	public String getZAGNT_NM() {
		return ZAGNT_NM;
	}
	/**
	 * @param zAGNT_NM the zAGNT_NM to set
	 */
	public void setZAGNT_NM(String zAGNT_NM) {
		ZAGNT_NM = zAGNT_NM;
	}
	/**
	 * @return the zKUNNR_NM
	 */
	public String getZKUNNR_NM() {
		return ZKUNNR_NM;
	}
	/**
	 * @param zKUNNR_NM the zKUNNR_NM to set
	 */
	public void setZKUNNR_NM(String zKUNNR_NM) {
		ZKUNNR_NM = zKUNNR_NM;
	}
	/**
	 * @return the zKUNNR_TEL
	 */
	public String getZKUNNR_TEL() {
		return ZKUNNR_TEL;
	}
	/**
	 * @param zKUNNR_TEL the zKUNNR_TEL to set
	 */
	public void setZKUNNR_TEL(String zKUNNR_TEL) {
		ZKUNNR_TEL = zKUNNR_TEL;
	}
	/**
	 * @return the kUNNR_NM
	 */
	public String getKUNNR_NM() {
		return KUNNR_NM;
	}
	/**
	 * @param kUNNR_NM the kUNNR_NM to set
	 */
	public void setKUNNR_NM(String kUNNR_NM) {
		KUNNR_NM = kUNNR_NM;
	}
	/**
	 * @return the pRH
	 */
	public String getPRH() {
		return PRH;
	}
	/**
	 * @param pRH the pRH to set
	 */
	public void setPRH(String pRH) {
		PRH = pRH;
	}
	/**
	 * @return the pRD
	 */
	public String getPRD() {
		return PRD;
	}
	/**
	 * @param pRD the pRD to set
	 */
	public void setPRD(String pRD) {
		PRD = pRD;
	}
	/**
	 * @return the aTKLA
	 */
	public String getATKLA() {
		return ATKLA;
	}
	/**
	 * @param aTKLA the aTKLA to set
	 */
	public void setATKLA(String aTKLA) {
		ATKLA = aTKLA;
	}
	/**
	 * @return the pRHNAME
	 */
	public String getPRHNAME() {
		return PRHNAME;
	}
	/**
	 * @param pRHNAME the pRHNAME to set
	 */
	public void setPRHNAME(String pRHNAME) {
		PRHNAME = pRHNAME;
	}
	/**
	 * @return the pOSNR
	 */
	public String getPOSNR() {
		return POSNR;
	}
	/**
	 * @param pOSNR the pOSNR to set
	 */
	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}
	/**
	 * @return the uUSER_TEL
	 */
	public String getUUSER_TEL() {
		return UUSER_TEL;
	}
	/**
	 * @param uUSER_TEL the uUSER_TEL to set
	 */
	public void setUUSER_TEL(String uUSER_TEL) {
		UUSER_TEL = uUSER_TEL;
	}

	/**
	 * @return the kUN_EMAIL
	 */
	public String getKUN_EMAIL() {
		return KUN_EMAIL;
	}
	/**
	 * @param kUN_EMAIL the kUN_EMAIL to set
	 */
	public void setKUN_EMAIL(String kUN_EMAIL) {
		KUN_EMAIL = kUN_EMAIL;
	}
	public String getOUTCANDT() {
		return OUTCANDT;
	}
	public void setOUTCANDT(String oUTCANDT) {
		OUTCANDT = oUTCANDT == null ? "" : oUTCANDT;
	}
	public String getOUTACTDT() {
		return OUTACTDT;
	}
	public void setOUTACTDT(String oUTACTDT) {
		OUTACTDT = oUTACTDT == null ? "" : oUTACTDT;
	}
	public String getOUTRETDT() {
		return OUTRETDT;
	}
	public void setOUTRETDT(String oUTRETDT) {
		OUTRETDT = oUTRETDT == null ? "" : oUTRETDT;
	}
	public String getOUTGRD() {
		return OUTGRD;
	}
	public void setOUTGRD(String oUTGRD) {
		OUTGRD = oUTGRD == null ? "" : oUTGRD;
	}
	public String getOUTLOQ() {
		return OUTLOQ;
	}
	public void setOUTLOQ(String oUTLOQ) {
		OUTLOQ = oUTLOQ == null ? "0" : oUTLOQ;
	}
	public String getOUTMAN() {
		return OUTMAN;
	}
	public void setOUTMAN(String oUTMAN) {
		OUTMAN = oUTMAN == null ? "" : oUTMAN;
	}
	public String getZKUN_EMAIL() {
		return ZKUN_EMAIL;
	}
	public void setZKUN_EMAIL(String zKUN_EMAIL) {
		ZKUN_EMAIL = zKUN_EMAIL;
	}
	public String getZKUNNR_Z3() {
		return ZKUNNR_Z3;
	}
	public void setZKUNNR_Z3(String zKUNNR_Z3) {
		ZKUNNR_Z3 = zKUNNR_Z3;
	}
	/**
	 * @return the cADREQTYPE
	 */
	public String getCADREQTYPE() {
		return CADREQTYPE == null ? "" : CADREQTYPE;
	}
	/**
	 * @param cADREQTYPE the cADREQTYPE to set
	 */
	public void setCADREQTYPE(String cADREQTYPE) {
		CADREQTYPE = cADREQTYPE == null ? "" : cADREQTYPE;
	}
	/**
	 * @return the cISTAT
	 */
	public String getCISTAT() {
		return CISTAT;
	}
	/**
	 * @param cISTAT the cISTAT to set
	 */
	public void setCISTAT(String cISTAT) {
		CISTAT = cISTAT;
	}
	public String getERROR_TEXT() {
		return ERROR_TEXT;
	}
	public void setERROR_TEXT(String eRROR_TEXT) {
		ERROR_TEXT = eRROR_TEXT;
	}
	public String getFILEGBN() {
		return FILEGBN;
	}
	public void setFILEGBN(String fILEGBN) {
		FILEGBN = fILEGBN;
	}
	public String getUSERCODE() {
		return USERCODE;
	}
	public void setUSERCODE(String uSERCODE) {
		USERCODE = uSERCODE;
	}
}