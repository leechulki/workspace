package hdel.sd.smp.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 이동계획 오픈/마감
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public class Smp0070VO extends ParameterVO {

	/**
	 *  대문자로 표시할것.
	 */
	
	// pk
	private String MANDT;		// 클라이언트
	private String ZMPINC;		// 이동계획차수
	
	private String ZPYM; 		// 편성년월
	private String ZPMONTHS;	// 편성개월수
	private String ZOPFLG; 		// 오픈여부
	private String ZCLFLG;  	// 마감여부
	private String ZRMK; 		// 비고
	private String CDATE;		// 입력일
	private String CTIME;		// 입력시간
	private String CUSER;		// 입력자
	private String UDATE;		// 변경일
	private String UTIME;		// 변경시간
	private String UUSER;		// 변경자
	private String DDATE;		// 삭제일
	private String DTIME;		// 삭제시간
	private String DUSER;		// 삭제자
	
	private String BTN_EVENT;	// 마감여부 스크립트용 플래그
	
	public String getBTN_EVENT() {
		return BTN_EVENT;
	}
	public void setBTN_EVENT(String bTN_EVENT) {
		BTN_EVENT = bTN_EVENT;
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
	public String getCDATE() {
		return CDATE;
	}
	public void setCDATE(String cDATE) {
		CDATE = cDATE;
	}
	public String getCTIME() {
		return CTIME;
	}
	public void setCTIME(String cTIME) {
		CTIME = cTIME;
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
		UDATE = uDATE;
	}
	public String getUTIME() {
		return UTIME;
	}
	public void setUTIME(String uTIME) {
		UTIME = uTIME;
	}
	public String getUUSER() {
		return UUSER;
	}
	public void setUUSER(String uUSER) {
		UUSER = uUSER;
	}
	public String getDDATE() {
		return DDATE;
	}
	public void setDDATE(String dDATE) {
		DDATE = dDATE;
	}
	public String getDTIME() {
		return DTIME;
	}
	public void setDTIME(String dTIME) {
		DTIME = dTIME;
	}
	public String getDUSER() {
		return DUSER;
	}
	public void setDUSER(String dUSER) {
		DUSER = dUSER;
	}

	
	
}
