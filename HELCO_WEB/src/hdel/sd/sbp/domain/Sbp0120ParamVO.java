package hdel.sd.sbp.domain;

import hdel.sd.smp.domain.SmpComParameterVO;

/**
 * 사업계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public class Sbp0120ParamVO extends SmpComParameterVO {

	// 대문자로 표시할것.
	private String MANDT;			// 클라이언트
	private String ZPYEAR;			// 계획년도
	private String WHERE;			// 조회구분
	private String SPART;			// 제품군
	private String AUART;			// 오더유형(판매유형문서)
	 
	private String VKBUR_F;			// 사업장 from
	private String VKGRP_F;			// 영업그룹 from
	private String VKBUR_T;			// 사업장 to
	private String VKGRP_T;			// 영업그룹 to
	private String ZKUNNR;			// 영업사원
	
	
	

	public String getAUART() {
		return AUART;
	}
	public void setAUART(String aUART) {
		AUART = aUART;
	}
	public String getSPART() {
		return SPART;
	}
	public void setSPART(String sPART) {
		SPART = sPART;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getZPYEAR() {
		return ZPYEAR;
	}
	public void setZPYEAR(String zPYEAR) {
		ZPYEAR = zPYEAR;
	}
	public String getWHERE() {
		return WHERE;
	}
	public void setWHERE(String wHERE) {
		WHERE = wHERE;
	}
	public String getVKBUR_F() {
		return VKBUR_F;
	}
	public void setVKBUR_F(String vKBUR_F) {
		VKBUR_F = vKBUR_F;
	}
	public String getVKGRP_F() {
		return VKGRP_F;
	}
	public void setVKGRP_F(String vKGRP_F) {
		VKGRP_F = vKGRP_F;
	}
	public String getVKBUR_T() {
		return VKBUR_T;
	}
	public void setVKBUR_T(String vKBUR_T) {
		VKBUR_T = vKBUR_T;
	}
	public String getVKGRP_T() {
		return VKGRP_T;
	}
	public void setVKGRP_T(String vKGRP_T) {
		VKGRP_T = vKGRP_T;
	}
	public String getZKUNNR() {
		return ZKUNNR;
	}
	public void setZKUNNR(String zKUNNR) {
		ZKUNNR = zKUNNR;
	}
	
	
}
