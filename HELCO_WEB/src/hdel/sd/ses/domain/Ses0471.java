package hdel.sd.ses.domain;

import org.apache.commons.lang.math.NumberUtils;

import hdel.lib.domain.CommonDomain;

public class Ses0471 extends CommonDomain {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String ZRQSEQ;
	private String ZRQFLG;
	private String ZRQDAT;
	private String ZRQSTAT;
	private String ZRSRLT;
	private String KUNNR_Z3;
	private String KUNNR_Z3_NM;
	private String QTSO_NO;
	private String QTSER;
	private String HOGI;
	private String FWRD;
	private String FINDAT;
	private String OUTSRCDT;
	private String OUTFINDT;
	private String OUTCANDT;
	private String F_BRPT   ;
	private String F_SRPT   ;
	private String J_LOPL   ;
	private String J_BYRPH  ;
	private String J_STDYN;
	private String J_SUBDAT ;
	private String J_USE    ;
	private String J_TYPE   ;
	private String J_MODL   ;
	private String J_GRDQ   ;
	private String J_EQSQ   ;
	private String J_SUVQ   ;
	private String J_TTBL   ;
	private String J_EXMT   ;
	private String J_EXMTDP ;
	private String J_PLAL   ;
	private String J_EFST   ;
	private String J_DWG    ;	
	private String ZRQCN01;	
	private String ZRQCN02;	
	private String ZRQCN03;	
	private String ZRQCN04;	
	private String ZRQCN05;	
	private String ZRQCN06;	
	private String ZRQCN07;	
	private String ZRQCN08;	
	private String ZRQCN09;	
	private String ZRQCN10;
	private String CDATE;
	private String CTIME;
	private String CUSER;
	private String UDATE;
	private String UTIME;
	private String UUSER;
	private String UUSER_TEL;
	private String KUN_EMAIL;	
	private String GVGCD;
	
	private String FLAG;
	private String ZATTSEQ;
	private String ZATGBN;
	private String ZATTPATH;
	private String ZATTFN;
	
	// Ãâ·Â¿ë
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
	
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getZRQSEQ() {
		return ZRQSEQ;
	}
	public void setZRQSEQ(String zRQSEQ) {
		ZRQSEQ = zRQSEQ;
	}
	public String getZRQFLG() {
		return ZRQFLG;
	}
	public void setZRQFLG(String zRQFLG) {
		ZRQFLG = zRQFLG;
	}
	public String getZRQDAT() {
		return ZRQDAT;
	}
	public void setZRQDAT(String zRQDAT) {
		ZRQDAT = String.format("%08d", NumberUtils.toInt(zRQDAT, 0));
	}
	public String getZRQSTAT() {
		return ZRQSTAT;
	}
	public void setZRQSTAT(String zRQSTAT) {
		ZRQSTAT = zRQSTAT;
	}
	public String getZRSRLT() {
		return ZRSRLT;
	}
	public void setZRSRLT(String zRSRLT) {
		ZRSRLT = zRSRLT;
	}
	public String getKUNNR_Z3() {
		return KUNNR_Z3;
	}
	public void setKUNNR_Z3(String kUNNR_Z3) {
		KUNNR_Z3 = kUNNR_Z3;
	}
	public String getQTSO_NO() {
		return QTSO_NO;
	}
	public void setQTSO_NO(String qTSO_NO) {
		QTSO_NO = qTSO_NO;
	}
	public String getQTSER() {
		return QTSER;
	}
	public void setQTSER(String qTSER) {
		QTSER = qTSER;
	}
	public String getHOGI() {
		return HOGI;
	}
	public void setHOGI(String hOGI) {
		HOGI = hOGI;
	}	
	public String getFWRD() {
		return FWRD;
	}
	public void setFWRD(String fWRD) {
		FWRD = fWRD;
	}
	
	public String getFINDAT() {
		return FINDAT;
	}
	public void setFINDAT(String fINDAT) {
		FINDAT = String.format("%08d", NumberUtils.toInt(fINDAT, 0));
	}
	public String getOUTSRCDT() {
		return OUTSRCDT;
	}
	public void setOUTSRCDT(String oUTSRCDT) {
		OUTSRCDT = String.format("%08d", NumberUtils.toInt(oUTSRCDT, 0));
	}
	public String getOUTFINDT() {
		return OUTFINDT;
	}
	public void setOUTFINDT(String oUTFINDT) {
		OUTFINDT = String.format("%08d", NumberUtils.toInt(oUTFINDT, 0));
	}	
	/**
	 * @return the oUTCANDT
	 */
	public String getOUTCANDT() {
		return OUTCANDT;
	}
	/**
	 * @param oUTCANDT the oUTCANDT to set
	 */
	public void setOUTCANDT(String oUTCANDT) {
		OUTCANDT = String.format("%08d", NumberUtils.toInt(oUTCANDT, 0));
	}
	public String getF_BRPT() {
		return F_BRPT;
	}
	public void setF_BRPT(String f_BRPT) {
		F_BRPT = f_BRPT;
	}
	public String getF_SRPT() {
		return F_SRPT;
	}
	public void setF_SRPT(String f_SRPT) {
		F_SRPT = f_SRPT;
	}
	public String getJ_LOPL() {
		return J_LOPL;
	}
	public void setJ_LOPL(String j_LOPL) {
		J_LOPL = j_LOPL;
	}
	public String getJ_BYRPH() {
		return J_BYRPH;
	}
	public void setJ_BYRPH(String j_BYRPH) {
		J_BYRPH = j_BYRPH;
	}
	/**
	 * @return the j_STDYN
	 */
	public String getJ_STDYN() {
		return J_STDYN;
	}
	/**
	 * @param j_STDYN the j_STDYN to set
	 */
	public void setJ_STDYN(String j_STDYN) {
		J_STDYN = j_STDYN;
	}
	public String getJ_SUBDAT() {
		return J_SUBDAT;
	}
	public void setJ_SUBDAT(String j_SUBDAT) {
		J_SUBDAT = j_SUBDAT;
	}
	public String getJ_USE() {
		return J_USE;
	}
	public void setJ_USE(String j_USE) {
		J_USE = j_USE;
	}
	public String getJ_TYPE() {
		return J_TYPE;
	}
	public void setJ_TYPE(String j_TYPE) {
		J_TYPE = j_TYPE;
	}
	public String getJ_MODL() {
		return J_MODL;
	}
	public void setJ_MODL(String j_MODL) {
		J_MODL = j_MODL;
	}

	public String getJ_GRDQ() {
		return J_GRDQ;
	}
	public void setJ_GRDQ(String j_GRDQ) {
		J_GRDQ = j_GRDQ;
	}
	public String getJ_EQSQ() {
		return J_EQSQ;
	}
	public void setJ_EQSQ(String j_EQSQ) {
		J_EQSQ = j_EQSQ;
	}
	public String getJ_SUVQ() {
		return J_SUVQ;
	}
	public void setJ_SUVQ(String j_SUVQ) {
		J_SUVQ = j_SUVQ;
	}
	public String getJ_TTBL() {
		return J_TTBL;
	}
	public void setJ_TTBL(String j_TTBL) {
		J_TTBL = j_TTBL;
	}
	public String getJ_EXMT() {
		return J_EXMT;
	}
	public void setJ_EXMT(String j_EXMT) {
		J_EXMT = j_EXMT;
	}
	public String getJ_EXMTDP() {
		return J_EXMTDP;
	}
	public void setJ_EXMTDP(String j_EXMTDP) {
		J_EXMTDP = j_EXMTDP;
	}
	public String getJ_PLAL() {
		return J_PLAL;
	}
	public void setJ_PLAL(String j_PLAL) {
		J_PLAL = j_PLAL;
	}
	public String getJ_EFST() {
		return J_EFST;
	}
	public void setJ_EFST(String j_EFST) {
		J_EFST = j_EFST;
	}
	public String getJ_DWG() {
		return J_DWG;
	}
	public void setJ_DWG(String j_DWG) {
		J_DWG = j_DWG;
	}
	
	public String getZRQCN01() {
		return ZRQCN01;
	}
	public void setZRQCN01(String zRQCN01) {
		ZRQCN01 = zRQCN01;
	}
	public String getZRQCN02() {
		return ZRQCN02;
	}
	public void setZRQCN02(String zRQCN02) {
		ZRQCN02 = zRQCN02;
	}
	public String getZRQCN03() {
		return ZRQCN03;
	}
	public void setZRQCN03(String zRQCN03) {
		ZRQCN03 = zRQCN03;
	}
	public String getZRQCN04() {
		return ZRQCN04;
	}
	public void setZRQCN04(String zRQCN04) {
		ZRQCN04 = zRQCN04;
	}
	public String getZRQCN05() {
		return ZRQCN05;
	}
	public void setZRQCN05(String zRQCN05) {
		ZRQCN05 = zRQCN05;
	}
	public String getZRQCN06() {
		return ZRQCN06;
	}
	public void setZRQCN06(String zRQCN06) {
		ZRQCN06 = zRQCN06;
	}
	public String getZRQCN07() {
		return ZRQCN07;
	}
	public void setZRQCN07(String zRQCN07) {
		ZRQCN07 = zRQCN07;
	}
	public String getZRQCN08() {
		return ZRQCN08;
	}
	public void setZRQCN08(String zRQCN08) {
		ZRQCN08 = zRQCN08;
	}
	public String getZRQCN09() {
		return ZRQCN09;
	}
	public void setZRQCN09(String zRQCN09) {
		ZRQCN09 = zRQCN09;
	}
	public String getZRQCN10() {
		return ZRQCN10;
	}
	public void setZRQCN10(String zRQCN10) {
		ZRQCN10 = zRQCN10;
	}
	public String getCDATE() {
		return CDATE;
	}
	public void setCDATE(String cDATE) {
		CDATE = String.format("%08d", NumberUtils.toInt(cDATE, 0));
	}
	public String getCTIME() {
		return CTIME;
	}
	public void setCTIME(String cTIME) {
		CTIME = String.format("%06d", NumberUtils.toInt(cTIME, 0));
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
		UDATE = String.format("%08d", NumberUtils.toInt(uDATE, 0));
	}
	public String getUTIME() {
		return UTIME;
	}
	public void setUTIME(String uTIME) {
		UTIME = String.format("%06d", NumberUtils.toInt(uTIME, 0));
	}
	public String getUUSER() {
		return UUSER;
	}
	public void setUUSER(String uUSER) {
		UUSER = uUSER;
	}
	public String getZATTSEQ() {
		return ZATTSEQ;
	}
	public void setZATTSEQ(String zATTSEQ) {
		ZATTSEQ = zATTSEQ;
	}
	public String getZATTPATH() {
		return ZATTPATH;
	}
	public void setZATTPATH(String zATTPATH) {
		ZATTPATH = zATTPATH;
	}
	public String getZATTFN() {
		return ZATTFN;
	}
	public void setZATTFN(String zATTFN) {
		ZATTFN = zATTFN;
	}
	public String getZATGBN() {
		return ZATGBN;
	}
	public void setZATGBN(String zATGBN) {
		ZATGBN = zATGBN;
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
		GSNAM = gSNAM;
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
}
