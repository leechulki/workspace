package hdel.sd.sbp.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 사업계획 현황(보수)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public class Sbp0100VO extends ParameterVO {

	/**
	 *  대문자로 표시할것.
	 */
	
	// pk
	private String MANDT;			// 클라이언트
	private String ZBPNNR;			// 사업계획번호
	
	private String ZPYEAR;			// 편성년도
	private String ZPYM;			// 계획년월
	private String SPART;			// 제품군
	private String AUART;			// 판매문서유형
	private String MATNR;			// 자재번호
	private String VKBUR;			// 사업장
	private String VKGRP;			// 영업그룹
	private String ZKUNNR;			// 영업사원
	private String RTYPE;			// 실기종
	private String GTYPE;			// 기종
	private String ZNUMBER;			// 대수
	private String KUNNR;			// 고객번호
	private String GSNAM;			// 공사명
	private String REGION;			// 설치지역
	private String ZDELI;			// 단납기구분
	private String SOFOCA;			// 수주예상액
	private String ZFORE;			// 예상시행율
	private String WAERK;			// 통화
	private String PSPIDSV; 		// 보수프로젝트
	private String ZHOGISV; 		// 보수호기번호
	private String ZRMK;			// 비고
	private String VBELN;			// 판매문서
	private String PSPID;			// 프로젝트
	private String CDATE;			// 입력일
	private String CTIME;			// 입력시간
	private String CUSER;			// 입력자
	private String UDATE;			// 변경일
	private String UTIME;			// 변경시간
	private String UUSER;			// 변경자
	private String DDATE;			// 삭제일
	private String DTIME;			// 삭제시간
	private String DUSER;			// 삭제자
	private String ZAGNT;			// 대리점
	

	public String getRTYPE() {
		return RTYPE;
	}
	public void setRTYPE(String rTYPE) {
		RTYPE = rTYPE;
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
	public String getZBPNNR() {
		return ZBPNNR;
	}
	public void setZBPNNR(String zBPNNR) {
		ZBPNNR = zBPNNR;
	}
	public String getZPYEAR() {
		return ZPYEAR;
	}
	public void setZPYEAR(String zPYEAR) {
		ZPYEAR = zPYEAR;
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
	public void setPSPIDSV(String PSPIDSV) {
		PSPIDSV = PSPIDSV;
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
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}
	public String getPSPID() {
		return PSPID;
	}
	public void setPSPID(String pSPID) {
		PSPID = pSPID;
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
