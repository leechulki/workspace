package hdel.sd.com.domain;

import com.sap.domain.Kunnr;

import hdel.lib.domain.CommonDomain;

public class Com0280 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt;
	private String spras;
	private String vkbur;
	private String vkburnm;
	private String vkgrp;
	private String vkgrpnm;
	private Kunnr kunnr;
	private String kunnrnm;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getSpras() {
		return spras;
	}
	public void setSpras(String spras) {
		this.spras = spras;
	}
	public String getVkbur() {
		return vkbur;
	}
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}
	public String getVkburnm() {
		return vkburnm;
	}
	public void setVkburnm(String vkburnm) {
		this.vkburnm = vkburnm;
	}
	public String getVkgrp() {
		return vkgrp;
	}
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}
	public String getVkgrpnm() {
		return vkgrpnm;
	}
	public void setVkgrpnm(String vkgrpnm) {
		this.vkgrpnm = vkgrpnm;
	}
	public Kunnr getKunnr() {
		return kunnr;
	}
	public void setKunnr(Kunnr kunnr) {
		this.kunnr = kunnr;
	}
	public String getKunnrnm() {
		return kunnrnm;
	}
	public void setKunnrnm(String kunnrnm) {
		this.kunnrnm = kunnrnm;
	}
}