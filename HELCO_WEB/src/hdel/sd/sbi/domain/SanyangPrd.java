package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;


/**
 * SanyangPrd(영업사양값) Value Of Class
 * 
 * @author  박수근
 * @since 2020.09.01
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.09.01         박수근          최초 생성
 * 
 * </pre>
 */

public class SanyangPrd extends CommonDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String MANDT;
	private  String BRNDSEQ;
	private  String ZPRDGBN;
	private  String BRNDCD;
	private  String PRH;
	private  String PRHNAME;
	private  String MFLAG;
	private  String MSEQ;
	private  String MDATATYPE;
	private  String MDISPTP;
	private  String MMODITP;
	private  String MEGISSND;
	private  String MDIPSDAT;
	private  String DSEQ;
	private  String DCNT;
	private  String PRD;
	private  String PRDNAME;
	private  String DFLAG;
	private  String DDATATYPE;
	private  String DDISPTP;
	private  String DDIPSDAT;
	private  String ATKLA;
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getBRNDSEQ() {
		return BRNDSEQ;
	}
	public void setBRNDSEQ(String bRNDSEQ) {
		BRNDSEQ = bRNDSEQ;
	}
	public String getZPRDGBN() {
		return ZPRDGBN;
	}
	public void setZPRDGBN(String zPRDGBN) {
		ZPRDGBN = zPRDGBN;
	}
	public String getBRNDCD() {
		return BRNDCD;
	}
	public void setBRNDCD(String bRNDCD) {
		BRNDCD = bRNDCD;
	}
	public String getPRH() {
		return PRH;
	}
	public void setPRH(String pRH) {
		PRH = pRH;
	}
	public String getPRHNAME() {
		return PRHNAME;
	}
	public void setPRHNAME(String pRHNAME) {
		PRHNAME = pRHNAME;
	}
	public String getMFLAG() {
		return MFLAG;
	}
	public void setMFLAG(String mFLAG) {
		MFLAG = mFLAG;
	}
	public String getMSEQ() {
		return MSEQ;
	}
	public void setMSEQ(String mSEQ) {
		MSEQ = mSEQ;
	}
	public String getMDATATYPE() {
		return MDATATYPE;
	}
	public void setMDATATYPE(String mDATATYPE) {
		MDATATYPE = mDATATYPE;
	}
	public String getMDISPTP() {
		return MDISPTP;
	}
	public void setMDISPTP(String mDISPTP) {
		MDISPTP = mDISPTP;
	}
	public String getMMODITP() {
		return MMODITP;
	}
	public void setMMODITP(String mMODITP) {
		MMODITP = mMODITP;
	}
	public String getMEGISSND() {
		return MEGISSND;
	}
	public void setMEGISSND(String mEGISSND) {
		MEGISSND = mEGISSND;
	}
	public String getMDIPSDAT() {
		return MDIPSDAT;
	}
	public void setMDIPSDAT(String mDIPSDAT) {
		MDIPSDAT = mDIPSDAT;
	}
	public String getDSEQ() {
		return DSEQ;
	}
	public void setDSEQ(String dSEQ) {
		DSEQ = dSEQ;
	}
	public String getDCNT() {
		return DCNT;
	}
	public void setDCNT(String dCNT) {
		DCNT = dCNT;
	}
	public String getPRD() {
		return PRD;
	}
	public void setPRD(String pRD) {
		PRD = pRD;
	}
	public String getPRDNAME() {
		return PRDNAME;
	}
	public void setPRDNAME(String pRDNAME) {
		PRDNAME = pRDNAME;
	}
	public String getDFLAG() {
		return DFLAG;
	}
	public void setDFLAG(String dFLAG) {
		DFLAG = dFLAG;
	}
	public String getDDATATYPE() {
		return DDATATYPE;
	}
	public void setDDATATYPE(String dDATATYPE) {
		DDATATYPE = dDATATYPE;
	}
	public String getDDISPTP() {
		return DDISPTP;
	}
	public void setDDISPTP(String dDISPTP) {
		DDISPTP = dDISPTP;
	}
	public String getDDIPSDAT() {
		return DDIPSDAT;
	}
	public void setDDIPSDAT(String dDIPSDAT) {
		DDIPSDAT = dDIPSDAT;
	}
	public String getATKLA() {
		return ATKLA;
	}
	public void setATKLA(String aTKLA) {
		ATKLA = aTKLA;
	}

	@Override
	public String toString() {
		return "SanyangPrd [MANDT=" + MANDT + ", BRNDSEQ=" + BRNDSEQ + ", ZPRDGBN=" + ZPRDGBN + ", BRNDCD=" + BRNDCD
				+ ", PRH=" + PRH + ", PRHNAME=" + PRHNAME + ", MFLAG=" + MFLAG + ", MSEQ=" + MSEQ + ", MDATATYPE="
				+ MDATATYPE + ", MDISPTP=" + MDISPTP + ", MMODITP=" + MMODITP + ", MEGISSND=" + MEGISSND + ", MDIPSDAT="
				+ MDIPSDAT + ", DSEQ=" + DSEQ + ", DCNT=" + DCNT + ", PRD=" + PRD + ", PRDNAME=" + PRDNAME + ", DFLAG="
				+ DFLAG + ", DDATATYPE=" + DDATATYPE + ", DDISPTP=" + DDISPTP + ", DDIPSDAT=" + DDIPSDAT + ", ATKLA="
				+ ATKLA + "]";
	}
}
