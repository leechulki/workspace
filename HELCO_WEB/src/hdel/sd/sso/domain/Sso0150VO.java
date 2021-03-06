package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 수주계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public class Sso0150VO extends ParameterVO {

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
	private String GTYPE; 			// 기종
	private String ZNUMBER; 		// 대수
	private String KUNNR; 			// 고객번호
	private String GSNAM; 			// 공사명
	private String REGION; 			// 설치지역
	private String ZDELI; 			// 단납기구분
	private String SOFOCA; 			// 수주예상액
	private String ZFORE;			// 예상시행율
	private String WAERK; 			// 통화
	private String PSPIDSV; 		// 보수프로젝트
	private String ZHOGISV; 		// 보수호기번호
	private String ZRMK; 			// 비고
	private String SOABLE; 			// 수주가능성
	private String SORLT; 			// 수주결과
	private String SOPRICE; 		// 수주금액
	private String SHANG; 			// 시행율
	private String ZMPFLG; 			// 이동계획반영여부
	private String ZBPNNR; 			// 사업계획번호
	private String CDATE; 			// 입력일
	private String CTIME; 			// 입력시간
	private String CUSER; 			// 입력자
	private String UDATE; 			// 변경일
	private String UTIME; 			// 변경시간
	private String UUSER; 			// 변경자
	private String DDATE; 			// 삭제일
	private String DTIME; 			// 삭제시간
	private String DUSER;			// 삭제자
	private String ZAGNT;			// 협력사
	private String KRWUSD;			// 환율
	
	

	public String getKRWUSD() {
		return KRWUSD;
	}
	public void setKRWUSD(String kRWUSD) {
		KRWUSD = kRWUSD;
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
	public String getPSPIDSV() {
		return PSPIDSV;
	}
	public void setPSPIDSV(String pSPIDSV) {
		PSPIDSV = pSPIDSV;
	}
	public String getZHOGISV() {
		return ZHOGISV;
	}
	public void setZHOGISV(String zHOGISV) {
		ZHOGISV = zHOGISV;
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
	
	
	

	
	
	
}
