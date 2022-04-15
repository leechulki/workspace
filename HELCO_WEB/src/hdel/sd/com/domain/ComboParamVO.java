package hdel.sd.com.domain;

public class ComboParamVO {
	private String mandt = "";

	private String vkbur = "";
	private String vkgrp = "";
	private String zkunnr = "";

	private String lang = "";

	private String sCode = "";
	
	// DD07T의 도메인ID : 2012.05.30 : GRY : 추가
	private String domname = "";

	// DD07T의 코드 : 2012.05.30 : GRY : 추가
	private String domvalue_l = "";
	
	// DD07T의 코드명에 코드 같이 조회 여부 : 2012.05.30 : GRY : 추가
	private String concat_yn = "";
	
	private String mclass = "";

	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
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

	public String getZkunnr() {
		return zkunnr;
	}

	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getDomname() {
		return domname;
	}

	public void setDomname(String domname) {
		this.domname = domname;
	}

	public String getDomvalue_l() {
		return domvalue_l;
	}

	public void setDomvalue_l(String domvalue_l) {
		this.domvalue_l = domvalue_l;
	}

	public String getConcat_yn() {
		return concat_yn;
	}

	public void setConcat_yn(String concat_yn) {
		this.concat_yn = concat_yn;
	}

	public String getMclass() {
		return mclass;
	}

	public void setMclass(String mclass) {
		this.mclass = mclass;
	}
}
