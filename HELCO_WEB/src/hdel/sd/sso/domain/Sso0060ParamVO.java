package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;


/**
 * 수주생성(Sso0060ParamVO) ParameterVO
 * @Comment 
 *     	- Sso0060C(수주생성) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class Sso0060ParamVO extends ParameterVO {

	private String MANDT;	// CLIENT
	private String QTNUM;	// 견적번호
	private String QTSER;	// 견적차수
	private String VBELN;	// 프로젝트번호
	private String CMD;		// 처리구분 (조회:DISP, 생성:SAVE, 변경:UPDT)
	private String CODE;	// 파트너코드 

	private String VKBUR;	// 부서코드
	private String VKGRP;	// 팀코드
	
	private String AUART;	// 문서유형
	
	private String SDFIELD;	// 주차, 리모델링, 신규 구분
	private String ZKUNNR;	// 담당자 코드

	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String value) {
		this.MANDT = value;
	} 
	public String getQTNUM() {
		return QTNUM;
	}
	public void setQTNUM(String value) {
		this.QTNUM = value;
	}
	public String getQTSER() {
		return QTSER;
	}
	public void setQTSER(String value) {
		this.QTSER = value;
	}
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String value) {
		this.VBELN = value;
	}
	public String getCMD() {
		return CMD;
	}
	public void setCMD(String value) {
		this.CMD = value;
	}
	public String geCODE() {
		return CODE;
	}
	public void setCODE(String value) {
		this.CODE = value;
	} 
	public String getVKBUR() {
		return VKBUR;
	}
	public void setVKBUR(String value) {
		this.VKBUR = value;
	} 
	public String getVKGRP() {
		return VKGRP;
	}
	public void setVKGRP(String value) {
		this.VKGRP = value;
	} 
	public String getAUART() {
		return AUART;
	}
	public void setAUART(String value) {
		this.AUART = value;
	}
	public String getSDFIELD() {
		return SDFIELD;
	}
	public void setSDFIELD(String sDFIELD) {
		SDFIELD = sDFIELD;
	}
	public String getZKUNNR() {
		return ZKUNNR;
	}
	public void setZKUNNR(String zKUNNR) {
		ZKUNNR = zKUNNR;
	}
	
}