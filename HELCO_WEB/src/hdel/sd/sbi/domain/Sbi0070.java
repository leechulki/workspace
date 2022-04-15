package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Sbi0070 extends CommonDomain {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String MANDT;  
	private String LIFNR; /* 사업자번호 */ 
	private String NAME1; /* 대리점명 */
	private String DEALER;  /* 딜러사번 */
	private String DEALERNM;   /* 딜러이름 */
	private String DEALERMAIL;  /* 딜러 EMAIL */

	private String MANAGER;    /* 담당자사번1 */
	private String MANAGER1;
	private String MANAGERNM1;
	private String MANAGERTELE1;
	private String MANAGER2;
	private String MANAGERNM2;
	private String MANAGERTELE2;
	private String MANAGER3;
	private String MANAGERNM3;
	private String MANAGERTELE3;
	private String ETC;
	private String SDFIELD;
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getLIFNR() {
		return LIFNR;
	}
	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}
	public String getNAME1() {
		return NAME1;
	}
	public void setNAME1(String nAME1) {
		NAME1 = nAME1;
	}
	public String getDEALER() {
		return DEALER;
	}
	public void setDEALER(String dEALER) {
		DEALER = dEALER;
	}
	public String getDEALERNM() {
		return DEALERNM;
	}
	public void setDEALERNM(String dEALERNM) {
		DEALERNM = dEALERNM;
	}
	public String getDEALERMAIL() {
		return DEALERMAIL;
	}
	public void setDEALERMAIL(String dEALERMAIL) {
		DEALERMAIL = dEALERMAIL;
	}
	public String getMANAGER() {
		return MANAGER;
	}
	public void setMANAGER(String mANAGER) {
		MANAGER = mANAGER;
	}
	public String getMANAGER1() {
		return MANAGER1;
	}
	public void setMANAGER1(String mANAGER1) {
		MANAGER1 = mANAGER1;
	}
	public String getMANAGERNM1() {
		return MANAGERNM1;
	}
	public void setMANAGERNM1(String mANAGERNM1) {
		MANAGERNM1 = mANAGERNM1;
	}
	public String getMANAGERTELE1() {
		return MANAGERTELE1;
	}
	public void setMANAGERTELE1(String mANAGERTELE1) {
		MANAGERTELE1 = mANAGERTELE1;
	}
	public String getMANAGER2() {
		return MANAGER2;
	}
	public void setMANAGER2(String mANAGER2) {
		MANAGER2 = mANAGER2;
	}
	public String getMANAGERNM2() {
		return MANAGERNM2;
	}
	public void setMANAGERNM2(String mANAGERNM2) {
		MANAGERNM2 = mANAGERNM2;
	}
	public String getMANAGERTELE2() {
		return MANAGERTELE2;
	}
	public void setMANAGERTELE2(String mANAGERTELE2) {
		MANAGERTELE2 = mANAGERTELE2;
	}
	public String getMANAGER3() {
		return MANAGER3;
	}
	public void setMANAGER3(String mANAGER3) {
		MANAGER3 = mANAGER3;
	}
	public String getMANAGERNM3() {
		return MANAGERNM3;
	}
	public void setMANAGERNM3(String mANAGERNM3) {
		MANAGERNM3 = mANAGERNM3;
	}
	public String getMANAGERTELE3() {
		return MANAGERTELE3;
	}
	public void setMANAGERTELE3(String mANAGERTELE3) {
		MANAGERTELE3 = mANAGERTELE3;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getETC() {
		return ETC;
	}
	public void setETC(String eTC) {
		ETC = eTC;
	}
	public String getSDFIELD() {
		return SDFIELD;
	}
	public void setSDFIELD(String sDFIELD) {
		SDFIELD = sDFIELD;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
	
	

}