package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 수주계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public class Sso0120VO extends ParameterVO {

	/**
	 *  대문자로 표시할것.
	 */
	
	// pk
	private String MANDT;			// 클라이언트
	private String SONNR;			// 수주계획번호 
	private String ZPYM; 			// 계획년월
	private String SPART; 			// 제품군
	private String AUART; 			// 판매문서유형
	private String MATNR; 			// 자재번호
	private String VKBUR;			// 사업장
	private String VKGRP; 			// 영업그룹
	private String ZKUNNR; 			// 영업사원
	private String RTYPE;			// 실기종
	private String GTYPE; 			// 기종
	private String TYPE1; 			// 타입1
	private String TYPE2; 			// 타입2
	private String TYPE3; 			// 타입3
	private String ZNUMBER; 		// 대수
	private String KUNNR; 			// 고객번호
	private String GSNAM; 			// 공사명
	private String LAND1;			// 국가
	private String REGION; 			// 설치지역
	private String ZDELI; 			// 단납기구분
	private String SHANGH; 			// 국내상해구분
	private String ZINTER; 			// 중계무역구분
	private String SOFOCA; 			// 수주예상액
	private String ZFORE;			// 예상시행율
	private String WAERK; 			// 통화
	private String DELDAT;			// 납기예정일
	private String ZRMK; 			// 비고
	private String SOABLE; 			// 수주가능성
	private String SORLT; 			// 수주결과
	private String SOPRICE; 		// 수주금액
	private String SHANG; 			// 시행율
	private String ZMPFLG; 			// 이동계획반영여부
	private String VBELN; 			// 판매문서
	private String ZBPNNR; 			// 사업계획번호
	private String SONNRB;			// 이전수주계획번호
	private String CDATE; 			// 입력일
	private String CTIME; 			// 입력시간
	private String CUSER; 			// 입력자
	private String UDATE; 			// 변경일
	private String UTIME; 			// 변경시간
	private String UUSER; 			// 변경자
	private String DDATE; 			// 삭제일
	private String DTIME; 			// 삭제시간
	private String DUSER;			// 삭제자
	private String ZAGNT;			// 대리점
	private String ZBDTYP;          //건물용도 jss
	
	public String getRTYPE() {
		return RTYPE;
	}
	public void setRTYPE(String rTYPE) {
		RTYPE = rTYPE;
	}
	public String getLAND1() {
		return LAND1;
	}
	public void setLAND1(String lAND1) {
		LAND1 = lAND1;
	}
	public String getSONNRB() {
		return SONNRB;
	}
	public void setSONNRB(String sONNRB) {
		SONNRB = sONNRB;
	}
	public String getZAGNT() {
		return ZAGNT;
	}
	public void setZAGNT(String zAGNT) {
		ZAGNT = zAGNT;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getSONNR() {
		return SONNR;
	}
	public void setSONNR(String sONNR) {
		SONNR = sONNR;
	}
	public String getZPYM() {
		return ZPYM;
	}
	public void setZPYM(String zPYM) {
		ZPYM = zPYM;
	}
	public String getSPART() {
		return SPART;
	}
	public void setSPART(String sPART) {
		SPART = sPART;
	}
	public String getAUART() {
		return AUART;
	}
	public void setAUART(String aUART) {
		AUART = aUART;
	}
	public String getMATNR() {
		return MATNR;
	}
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}
	public String getVKBUR() {
		return VKBUR;
	}
	public void setVKBUR(String vKBUR) {
		VKBUR = vKBUR;
	}
	public String getVKGRP() {
		return VKGRP;
	}
	public void setVKGRP(String vKGRP) {
		VKGRP = vKGRP;
	}
	public String getZKUNNR() {
		return ZKUNNR;
	}
	public void setZKUNNR(String zKUNNR) {
		ZKUNNR = zKUNNR;
	}
	public String getGTYPE() {
		return GTYPE;
	}
	public void setGTYPE(String gTYPE) {
		GTYPE = gTYPE;
	}
	public String getTYPE1() {
		return TYPE1;
	}
	public void setTYPE1(String tYPE1) {
		TYPE1 = tYPE1;
	}
	public String getTYPE2() {
		return TYPE2;
	}
	public void setTYPE2(String tYPE2) {
		TYPE2 = tYPE2;
	}
	public String getTYPE3() {
		return TYPE3;
	}
	public void setTYPE3(String tYPE3) {
		TYPE3 = tYPE3;
	}
	public String getZNUMBER() {
		return ZNUMBER;
	}
	public void setZNUMBER(String zNUMBER) {
		ZNUMBER = zNUMBER;
	}
	public String getKUNNR() {
		return KUNNR;
	}
	public void setKUNNR(String kUNNR) {
		KUNNR = kUNNR;
	}
	public String getGSNAM() {
		return GSNAM;
	}
	public void setGSNAM(String gSNAM) {
		GSNAM = gSNAM;
	}
	public String getREGION() {
		return REGION;
	}
	public void setREGION(String rEGION) {
		REGION = rEGION;
	}
	public String getZDELI() {
		return ZDELI;
	}
	public void setZDELI(String zDELI) {
		ZDELI = zDELI;
	}
	public String getSHANGH() {
		return SHANGH;
	}
	public void setSHANGH(String sHANGH) {
		SHANGH = sHANGH;
	}
	public String getZINTER() {
		return ZINTER;
	}
	public void setZINTER(String zINTER) {
		ZINTER = zINTER;
	}
	public String getSOFOCA() {
		return SOFOCA;
	}
	public void setSOFOCA(String sOFOCA) {
		SOFOCA = sOFOCA;
	}
	public String getZFORE() {
		return ZFORE;
	}
	public void setZFORE(String zFORE) {
		ZFORE = zFORE;
	}
	public String getWAERK() {
		return WAERK;
	}
	public void setWAERK(String wAERK) {
		WAERK = wAERK;
	}
	public String getDELDAT() {
		return DELDAT;
	}
	public void setDELDAT(String dELDAT) {
		DELDAT = dELDAT;
	}
	public String getZRMK() {
		return ZRMK;
	}
	public void setZRMK(String zRMK) {
		ZRMK = zRMK;
	}
	public String getSOABLE() {
		return SOABLE;
	}
	public void setSOABLE(String sOABLE) {
		SOABLE = sOABLE;
	}
	public String getSORLT() {
		return SORLT;
	}
	public void setSORLT(String sORLT) {
		SORLT = sORLT;
	}
	public String getSOPRICE() {
		return SOPRICE;
	}
	public void setSOPRICE(String sOPRICE) {
		SOPRICE = sOPRICE;
	}
	public String getSHANG() {
		return SHANG;
	}
	public void setSHANG(String sHANG) {
		SHANG = sHANG;
	}
	public String getZMPFLG() {
		return ZMPFLG;
	}
	public void setZMPFLG(String zMPFLG) {
		ZMPFLG = zMPFLG;
	}
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}
	public String getZBPNNR() {
		return ZBPNNR;
	}
	public void setZBPNNR(String zBPNNR) {
		ZBPNNR = zBPNNR;
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
	public String getZBDTYP() {
		return ZBDTYP;
	}
	public void setZBDTYP(String zBDTYP) {
		ZBDTYP = zBDTYP;
	}
	
	
}
