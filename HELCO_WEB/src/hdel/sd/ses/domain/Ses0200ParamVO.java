package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0200ParamVO extends ParameterVO {

	private String mandt;
	private String frqtdat;
	private String toqtdat;
	private String vkbur;
	private String vkgrp;
	private String zprgflg;
	private String zkunnr;
	private String frzestdat;
	private String tozestdat;
	
	private String qtnum;
	private String qtser;
	
	private String gsnam;
	
	// 승강기번호, 최초 견적일자 추가 21.08.03
	private String eldelvno;
	private String fcdate;
	
	// 최초 견적일자 기간 조회 조건 추가 2021.08.24.
	private String frfcdate;
	private String tofcdate;
		
	// 지역 조회조건 추가 by 김은하
	private String region;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getFrqtdat() {
		return frqtdat;
	}
	public void setFrqtdat(String frqtdat) {
		this.frqtdat = frqtdat;
	}
	public String getToqtdat() {
		return toqtdat;
	}
	public void setToqtdat(String toqtdat) {
		this.toqtdat = toqtdat;
	}
	public String getVkbur() {
		return vkbur;
	}
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}
	public String getVkgrp() {
		return vkgrp;
	}
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}
	public String getZprgflg() {
		return zprgflg;
	}
	public void setZprgflg(String zprgflg) {
		this.zprgflg = zprgflg;
	}
	public String getZkunnr() {
		return zkunnr;
	}
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
	public String getFrzestdat() {
		return frzestdat;
	}
	public void setFrzestdat(String frzestdat) {
		this.frzestdat = frzestdat;
	}
	public String getTozestdat() {
		return tozestdat;
	}
	public void setTozestdat(String tozestdat) {
		this.tozestdat = tozestdat;
	}
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public String getQtser() {
		return qtser;
	}
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}
	
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEldelvno() {
		return eldelvno;
	}
	public void setEldelvno(String eldelvno) {
		this.eldelvno = eldelvno;
	}
	
	public String getFcdate() {
		return fcdate;
	}
	public void setFcdate(String fcdate) {
		this.fcdate = fcdate;
	}
	
	public String getFrfcdate() {
		return frfcdate;
	}
	public void setFrfcdate(String frfcdate) {
		this.frfcdate = frfcdate;
	}
	
	public String getTofcdate() {
		return tofcdate;
	}
	public void setTofcdate(String tofcdate) {
		this.tofcdate = tofcdate;
	}
	
}
