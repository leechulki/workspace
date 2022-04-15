package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;


/**
 * ZSDT1140(브랜드적용일자) Value Of Class
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

public class ZSDT1140 extends CommonDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String MANDT ="";
	private  String BRNDSEQ ="";
	private  String ZPRDGBN ="";
	private  String BRNDCD ="";
	private  String FRDAT ="";
	private  String TODAT ="";
	private  String SNDSYS ="";
	private  String ERDAT ="";
	private  String ERZET ="";
	private  String ERNAM ="";
	private  String AEDAT ="";
	private  String AEZET ="";
	private  String AENAM ="";
	private  String FLAG ="";
	private  String SPRAS="";
	
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
	public String getFRDAT() {
		return FRDAT;
	}
	public void setFRDAT(String fRDAT) {
		FRDAT = fRDAT;
	}
	public String getTODAT() {
		return TODAT;
	}
	public void setTODAT(String tODAT) {
		TODAT = tODAT;
	}
	public String getSNDSYS() {
		return SNDSYS;
	}
	public void setSNDSYS(String sNDSYS) {
		SNDSYS = sNDSYS;
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
		if(eRNAM != null)
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
		if(aENAM != null)
		    AENAM = aENAM;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getSPRAS() {
		return SPRAS;
	}
	public void setSPRAS(String sPRAS) {
		SPRAS = sPRAS;
	}
	@Override
	public String toString() {
		return "ZSDT1140 [MANDT=" + MANDT + ", BRNDSEQ=" + BRNDSEQ + ", ZPRDGBN=" + ZPRDGBN + ", BRNDCD=" + BRNDCD
				+ ", FRDAT=" + FRDAT + ", TODAT=" + TODAT + ", SNDSYS=" + SNDSYS + ", ERDAT=" + ERDAT + ", ERZET="
				+ ERZET + ", ERNAM=" + ERNAM + ", AEDAT=" + AEDAT + ", AEZET=" + AEZET + ", AENAM=" + AENAM + ", FLAG="
				+ FLAG + ", SPRAS" + "]";
	}
	
	
}
