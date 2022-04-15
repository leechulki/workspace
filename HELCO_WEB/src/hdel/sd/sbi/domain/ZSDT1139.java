package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;


/**
 * ZSDT1139(브랜드적용) Value Of Class
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

public class ZSDT1139 extends CommonDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String MANDT ="";
	private  String BRNDSEQ ="";
	private  String NOTE ="";
	private  String ERDAT ="";
	private  String ERZET ="";
	private  String ERNAM ="";
	private  String AEDAT ="";
	private  String AEZET ="";
	private  String AENAM ="";
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		if(mANDT != null)
		    MANDT = mANDT;
	}
	public String getBRNDSEQ() {
		return BRNDSEQ;
	}
	public void setBRNDSEQ(String bRNDSEQ) {
		BRNDSEQ = bRNDSEQ;
	}
	public String getNOTE() {
		return NOTE;
	}
	public void setNOTE(String nOTE) {
		if(nOTE != null)
			NOTE = nOTE;
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
	
	@Override
	public String toString() {
		return "ZSDT1139 [MANDT=" + MANDT + ", BRNDSEQ=" + BRNDSEQ + ", NOTE=" + NOTE + ", ERDAT=" + ERDAT + ", ERZET="
				+ ERZET + ", ERNAM=" + ERNAM + ", AEDAT=" + AEDAT + ", AEZET=" + AEZET + ", AENAM=" + AENAM + "]";
	}
	
}
