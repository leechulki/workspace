package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0290ParamVO extends ParameterVO {
	
	private String mandt;
	private String vkbur;
	private String vkgrp;
	private String kunz2;
	private String fr_perdt;
	private String to_perdt;
	private String perno;
	private String manag;
	private String postn;
	private String fr_chakd;
	private String to_chakd;
	
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
	public String getKunz2() {
		return kunz2;
	}
	public void setKunz2(String kunz2) {
		this.kunz2 = kunz2;
	}
	public String getFr_perdt() {
		return fr_perdt;
	}
	public void setFr_perdt(String fr_perdt) {
		this.fr_perdt = fr_perdt == "____.__.__" ? null : fr_perdt.replace(".", "");
	}
	public String getTo_perdt() {
		return to_perdt;
	}
	public void setTo_perdt(String to_perdt) {
		this.to_perdt = to_perdt == "____.__.__" ? null : to_perdt.replace(".", "");
	}
	public String getPerno() {
		return perno;
	}
	public void setPerno(String perno) {
		this.perno = perno;
	}
	public String getManag() {
		return manag;
	}
	public void setManag(String manag) {
		this.manag = manag;
	}
	public String getPostn() {
		return postn;
	}
	public void setPostn(String postn) {
		this.postn = postn;
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

}
