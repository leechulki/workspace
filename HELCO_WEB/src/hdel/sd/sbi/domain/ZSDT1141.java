package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;


/**
 * ZSDT1141(브랜드별 특성코드) Value Of Class
 * 
 * @author  박수근
 * @since 2020.08.20
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.08.20         박수근          최초 생성
 * 
 * </pre>
 */

public class ZSDT1141 extends CommonDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String MANDT = "";
	private  String BRNDSEQ = "";
	private  String ZPRDGBN = "";
	private  String BRNDCD = "";
	private  String PRH = "";
	private  String DISPTP = "";
	private  String MODITP = "";
	private  String EGISSND = "";
	private  String DIPSDAT = "99991231";
	private  String ERDAT = "";
	private  String ERZET = "";
	private  String ERNAM = "";
	private  String AEDAT = "";
	private  String AEZET = "";
	private  String AENAM = "";
	private  String FLAG = "";

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
	public String getDISPTP() {
		return DISPTP;
	}
	public void setDISPTP(String dISPTP) {
		DISPTP = dISPTP;
	}
	public String getMODITP() {
		return MODITP;
	}
	public void setMODITP(String mODITP) {
		MODITP = mODITP;
	}
	public String getEGISSND() {
		return EGISSND;
	}
	public void setEGISSND(String eGISSND) {
		EGISSND = eGISSND;
	}
	public String getDIPSDAT() {
		return DIPSDAT;
	}
	public void setDIPSDAT(String dIPSDAT) {
		if(dIPSDAT != null) {
			if(!"".equals(dIPSDAT)) {
				DIPSDAT = dIPSDAT;
			}
		}
	}
	public String getERDAT() {
		return ERDAT;
	}
	public void setERDAT(String eRDAT) {
		ERDAT = eRDAT;
	}
	public String getERZET() {
		return ERZET;
	}
	public void setERZET(String eRZET) {
		ERZET = eRZET;
	}
	public String getERNAM() {
		return ERNAM;
	}
	public void setERNAM(String eRNAM) {
		ERNAM = eRNAM;
	}
	public String getAEDAT() {
		return AEDAT;
	}
	public void setAEDAT(String aEDAT) {
		AEDAT = aEDAT;
	}
	public String getAEZET() {
		return AEZET;
	}
	public void setAEZET(String aEZET) {
		AEZET = aEZET;
	}
	public String getAENAM() {
		return AENAM;
	}
	public void setAENAM(String aENAM) {
		AENAM = aENAM;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}

	@Override
	public String toString() {
		return "ZSDT1141 [MANDT=" + MANDT + ", BRNDSEQ=" + BRNDSEQ + ", ZPRDGBN=" + ZPRDGBN + ", BRNDCD=" + BRNDCD
				+ ", PRH=" + PRH + ", DISPTP=" + DISPTP + ", MODITP=" + MODITP + ", EGISSND=" + EGISSND + ", DIPSDAT="
				+ DIPSDAT + ", ERDAT=" + ERDAT + ", ERZET=" + ERZET + ", ERNAM=" + ERNAM + ", AEDAT=" + AEDAT
				+ ", AEZET=" + AEZET + ", AENAM=" + AENAM + ", FLAG=" + FLAG + "]";
	}
}
