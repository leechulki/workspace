package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;

public class Sso0214ParamVO extends ParameterVO {
	
	private String mandt;
	private String zzpjt_id;
	private String fr_chakd;
	private String to_chakd;
	private String vkbur_f;
	private String vkbur_t;
	private String vkgrp_f;
	private String vkgrp_t;
	private String dealer;
	private String manager;
	private String chkcol;
	private String chktpdate2;
	private String chktpdate3;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZzpjt_id() {
		return zzpjt_id;
	}
	public void setZzpjt_id(String zzpjt_id) {
		this.zzpjt_id = zzpjt_id;
	}
	public String getFr_chakd() {
		return fr_chakd;
	}
	public void setFr_chakd(String fr_chakd) {
		this.fr_chakd = fr_chakd == "____.__.__" ? null : fr_chakd.replace(".", "");
	}
	public String getTo_chakd() {
		return to_chakd;
	}
	public void setTo_chakd(String to_chakd) {
		this.to_chakd = to_chakd == "____.__.__" ? null : to_chakd.replace(".", "");
	}
	public String getVkbur_f() {
		return vkbur_f;
	}
	public void setVkbur_f(String vkbur_f) {
		this.vkbur_f = vkbur_f;
	}
	public String getVkbur_t() {
		return vkbur_t;
	}
	public void setVkbur_t(String vkbur_t) {
		this.vkbur_t = vkbur_t;
	}
	public String getVkgrp_f() {
		return vkgrp_f;
	}
	public void setVkgrp_f(String vkgrp_f) {
		this.vkgrp_f = vkgrp_f;
	}
	public String getVkgrp_t() {
		return vkgrp_t;
	}
	public void setVkgrp_t(String vkgrp_t) {
		this.vkgrp_t = vkgrp_t;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getChkcol() {
		return chkcol;
	}
	public void setChkcol(String chkcol) {
		this.chkcol = chkcol;
	}
	public String getChktpdate2() {
		return chktpdate2;
	}
	public void setChktpdate2(String chktpdate2) {
		this.chktpdate2 = chktpdate2;
	}
	public String getChktpdate3() {
		return chktpdate3;
	}
	public void setChktpdate3(String chktpdate3) {
		this.chktpdate3 = chktpdate3;
	}
	
}
