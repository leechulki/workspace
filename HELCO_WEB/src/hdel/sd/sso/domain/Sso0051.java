package hdel.sd.sso.domain;

import hdel.lib.domain.CommonDomain; 

import java.math.*;

/**
 * 사양복사(Sso0051) CommonDomain
 * @Comment 
 *     	- Sso0051C(사양복사) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2013.03.11  김선호  :  initial version 
 * @version 1.0
 */ 


public class Sso0051 extends CommonDomain {
 
	private String NAM_CHAR;		// 특성코드 
	private String ATBEZ;			// 특성내역
	private String VALUE1;			// 특성값코드
	private String ATWTB1;			// 특성값내역
	private String MATNR; 
	private String KUNRG_NM;
	
	
	// 2020 브랜드
	private String DISPTP;
	private String MODITP;
	private String CNT;
	
	public String getNAM_CHAR() {
		return NAM_CHAR;
	}
	public void setNAM_CHAR(String nAM_CHAR) {
		NAM_CHAR = nAM_CHAR;
	}
	public String getATBEZ() {
		return ATBEZ;
	}
	public void setATBEZ(String aTBEZ) {
		ATBEZ = aTBEZ;
	}
	public String getVALUE1() {
		return VALUE1;
	}
	public void setVALUE1(String vALUE1) {
		VALUE1 = vALUE1;
	}
	public String getATWTB1() {
		return ATWTB1;
	}
	public void setATWTB1(String aTWTB1) {
		ATWTB1 = aTWTB1;
	}
	public String getMATNR() {
		return MATNR;
	}
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}
	public String getKUNRG_NM() {
		return KUNRG_NM;
	}
	public void setKUNRG_NM(String kUNRG_NM) {
		KUNRG_NM = kUNRG_NM;
	}

	// 2020 브랜드
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
	public String getCNT() {
		return CNT;
	}
	public void setCNT(String cNT) {
		CNT = cNT;
	}
}
