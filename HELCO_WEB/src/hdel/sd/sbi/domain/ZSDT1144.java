package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;


/**
 * ZSDT1144(브랜드기종) Value Of Class
 * 
 * @author  박수근
 * @since 2020.09.25
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

public class ZSDT1144 extends CommonDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String MANDT = "";
	private  String SEQ = "";
	private  String BRNDCD = "";
	private  String GTYPE = "";
	private  String ERDAT;
	private  String ERNAM = "";
	private  String AEDAT;
	private  String AENAM = "";

	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		if(sEQ != null)
	        SEQ = sEQ;
	}
	public String getBRNDCD() {
		return BRNDCD;
	}
	public void setBRNDCD(String bRNDCD) {
		BRNDCD = bRNDCD;
	}
	public String getGTYPE() {
		return GTYPE;
	}
	public void setGTYPE(String gTYPE) {
		GTYPE = gTYPE;
	}
	public String getERDAT() {
		return ERDAT;
	}
	public void setERDAT(String eRDAT) {
		ERDAT = eRDAT;
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
	public String getAENAM() {
		return AENAM;
	}
	public void setAENAM(String aENAM) {
		AENAM = aENAM;
	}
	@Override
	public String toString() {
		return "ZSDT1144 [MANDT=" + MANDT + ", SEQ=" + SEQ + ", BRNDCD=" + BRNDCD + ", GTYPE=" + GTYPE + ", ERDAT="
				+ ERDAT + ", ERNAM=" + ERNAM + ", AEDAT=" + AEDAT + ", AENAM=" + AENAM + "]";
	}

}
