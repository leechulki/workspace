package hdel.sd.sso.domain;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Sso0032 extends CommonDomain {

	private static final long serialVersionUID = 1L;

    private String MANDT;  
    private String VBELN;		 
	private String PSPID; 		 
	private String POST1; 		
	private String DOCTP;  		
	private String SPDRFTFLAG;  
	private String SPDRFTDESC;  
	private String LIFNRCHK;	
	private String SSPLDT;		
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
	
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
	 * @return the pSPID
	 */
	public String getPSPID() {
		return PSPID;
	}
	/**
	 * @param pSPID the pSPID to set
	 */
	public void setPSPID(String pSPID) {
		PSPID = pSPID;
	}
	/**
	 * @return the pOST1
	 */
	public String getPOST1() {
		return POST1;
	}
	/**
	 * @param pOST1 the pOST1 to set
	 */
	public void setPOST1(String pOST1) {
		POST1 = pOST1;
	}
	/**
	 * @return the dOCTP
	 */
	public String getDOCTP() {
		return DOCTP;
	}
	/**
	 * @param dOCTP the dOCTP to set
	 */
	public void setDOCTP(String dOCTP) {
		DOCTP = dOCTP;
	}
	/**
	 * @return the sPDRFTFLAG
	 */
	public String getSPDRFTFLAG() {
		return SPDRFTFLAG;
	}
	/**
	 * @param sPDRFTFLAG the sPDRFTFLAG to set
	 */
	public void setSPDRFTFLAG(String sPDRFTFLAG) {
		SPDRFTFLAG = sPDRFTFLAG;
	}
	/**
	 * @return the sPDRFTDESC
	 */
	public String getSPDRFTDESC() {
		return SPDRFTDESC;
	}
	/**
	 * @param sPDRFTDESC the sPDRFTDESC to set
	 */
	public void setSPDRFTDESC(String sPDRFTDESC) {
		SPDRFTDESC = sPDRFTDESC;
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
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the tstmp
	 */
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	/**
	 * @param tstmp the tstmp to set
	 */
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
	/**
	 * @return the sSPLDT
	 */
	public String getSSPLDT() {
		return SSPLDT;
	}
	/**
	 * @param sSPLDT the sSPLDT to set
	 */
	public void setSSPLDT(String sSPLDT) {
		SSPLDT = sSPLDT;
	}

}
