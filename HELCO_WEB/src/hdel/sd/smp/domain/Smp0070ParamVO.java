package hdel.sd.smp.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 이동계획 오픈/마감
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public class Smp0070ParamVO extends SmpComParameterVO {

	// 대문자로 표시할것.
	private String MANDT;		// 클라이언트
	private String ZMPINC;		// 이동계획차수
	private String PLAN_YEAR;	// 계획년도
	private String ZPYM; 		// 편성년월
	private String ZPYM_TO;		// 편성년월 TO
	private String ZPMONTHS;	// 편성개월수
	private String ZOPFLG; 		// 오픈여부
	private String ZCLFLG; 	 	// 마감여부
	private String ZRMK; 		// 비고
	private String ROW_TYPE;	// row타입

	
	public String getROW_TYPE() {
		return ROW_TYPE;
	}
	public void setROW_TYPE(String rOW_TYPE) {
		ROW_TYPE = rOW_TYPE;
	}
	public String getZOPFLG() {
		return ZOPFLG;
	}
	public void setZOPFLG(String zOPFLG) {
		ZOPFLG = zOPFLG;
	}
	public String getZCLFLG() {
		return ZCLFLG;
	}
	public void setZCLFLG(String zCLFLG) {
		ZCLFLG = zCLFLG;
	}
	public String getZRMK() {
		return ZRMK;
	}
	public void setZRMK(String zRMK) {
		ZRMK = zRMK;
	}
	public String getZPYM_TO() {
		return ZPYM_TO;
	}
	public void setZPYM_TO(String zPYM_TO) {
		ZPYM_TO = zPYM_TO;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getZMPINC() {
		return ZMPINC;
	}
	public void setZMPINC(String zMPINC) {
		ZMPINC = zMPINC;
	}
	public String getPLAN_YEAR() {
		return PLAN_YEAR;
	}
	public void setPLAN_YEAR(String pLAN_YEAR) {
		PLAN_YEAR = pLAN_YEAR;
	}
	public String getZPYM() {
		return ZPYM;
	}
	public void setZPYM(String zPYM) {
		ZPYM = zPYM;
	}
	public String getZPMONTHS() {
		return ZPMONTHS;
	}
	public void setZPMONTHS(String zPMONTHS) {
		ZPMONTHS = zPMONTHS;
	}
	

	
	
}
