package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0400ParamVO extends ParameterVO {

	private String mandt;
	private String frqtdat;
	private String toqtdat;
	private String frzrqdat;
	private String tozrqdat;
	private String vkbur;
	private String vkgrp;
	private String zkunnr;
	private String zrqstat;
	private String gubun;
	private String qtso_no;
	private String pjt;
	private String query_yn;
	private String qtser;
	private String qtseq;
	private String hogi;

	private String frvkbur;
	private String frvkgrp;
	private String tovkbur;
	private String tovkgrp;
	private String kunnr;
	private String zagnt;
	private String zkunnr_z3;
	private String zrqflg;
	private String spdocu;
    
	//2017.03 기술검토요청 업체별 조회 기능 추가
	private String outgbn;
	
	public String getOutgbn() {
		return outgbn;
	}
	public void setOutgbn(String outgbn) {
		this.outgbn = outgbn;
	}

	
	
	/**
	 * @return the mandt
	 */
	public String getMandt() {
		return mandt;
	}
	/**
	 * @param mandt the mandt to set
	 */
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	/**
	 * @return the frqtdat
	 */
	public String getFrqtdat() {
		return frqtdat;
	}
	/**
	 * @param frqtdat the frqtdat to set
	 */
	public void setFrqtdat(String frqtdat) {
		this.frqtdat = frqtdat;
	}
	/**
	 * @return the toqtdat
	 */
	public String getToqtdat() {
		return toqtdat;
	}
	/**
	 * @param toqtdat the toqtdat to set
	 */
	public void setToqtdat(String toqtdat) {
		this.toqtdat = toqtdat;
	}
	/**
	 * @return the frzrqdat
	 */
	public String getFrzrqdat() {
		return frzrqdat;
	}
	/**
	 * @param frzrqdat the frzrqdat to set
	 */
	public void setFrzrqdat(String frzrqdat) {
		this.frzrqdat = frzrqdat;
	}
	/**
	 * @return the tozrqdat
	 */
	public String getTozrqdat() {
		return tozrqdat;
	}
	/**
	 * @param tozrqdat the tozrqdat to set
	 */
	public void setTozrqdat(String tozrqdat) {
		this.tozrqdat = tozrqdat;
	}
	/**
	 * @return the vkbur
	 */
	public String getVkbur() {
		return vkbur;
	}
	/**
	 * @param vkbur the vkbur to set
	 */
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}
	/**
	 * @return the vkgrp
	 */
	public String getVkgrp() {
		return vkgrp;
	}
	/**
	 * @param vkgrp the vkgrp to set
	 */
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}
	/**
	 * @return the zkunnr
	 */
	public String getZkunnr() {
		return zkunnr;
	}
	/**
	 * @param zkunnr the zkunnr to set
	 */
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
	/**
	 * @return the zrqstat
	 */
	public String getZrqstat() {
		return zrqstat;
	}
	/**
	 * @param zrqstat the zrqstat to set
	 */
	public void setZrqstat(String zrqstat) {
		this.zrqstat = zrqstat;
	}
	/**
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}
	/**
	 * @param gubun the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	/**
	 * @return the qtso_no
	 */
	public String getQtso_no() {
		return qtso_no;
	}
	/**
	 * @param qtso_no the qtso_no to set
	 */
	public void setQtso_no(String qtso_no) {
		this.qtso_no = qtso_no;
	}
	/**
	 * @return the pjt
	 */
	public String getPjt() {
		return pjt;
	}
	/**
	 * @param pjt the pjt to set
	 */
	public void setPjt(String pjt) {
		this.pjt = pjt;
	}
	/**
	 * @return the query_yn
	 */
	public String getQuery_yn() {
		return query_yn;
	}
	/**
	 * @param query_yn the query_yn to set
	 */
	public void setQuery_yn(String query_yn) {
		this.query_yn = query_yn;
	}
	/**
	 * @return the qtser
	 */
	public String getQtser() {
		return qtser;
	}
	/**
	 * @param qtser the qtser to set
	 */
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}
	/**
	 * @return the qtseq
	 */
	public String getQtseq() {
		return qtseq;
	}
	/**
	 * @param qtseq the qtseq to set
	 */
	public void setQtseq(String qtseq) {
		this.qtseq = qtseq;
	}
	/**
	 * @return the hogi
	 */
	public String getHogi() {
		return hogi;
	}
	/**
	 * @param hogi the hogi to set
	 */
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}

	/**
	 * @return the frvkbur
	 */
	public String getFrvkbur() {
		return frvkbur;
	}
	/**
	 * @param frvkbur the frvkbur to set
	 */
	public void setFrvkbur(String frvkbur) {
		this.frvkbur = frvkbur;
	}
	/**
	 * @return the frvkgrp
	 */
	public String getFrvkgrp() {
		return frvkgrp;
	}
	/**
	 * @param frvkgrp the frvkgrp to set
	 */
	public void setFrvkgrp(String frvkgrp) {
		this.frvkgrp = frvkgrp;
	}
	/**
	 * @return the tovkbur
	 */
	public String getTovkbur() {
		return tovkbur;
	}
	/**
	 * @param tovkbur the tovkbur to set
	 */
	public void setTovkbur(String tovkbur) {
		this.tovkbur = tovkbur;
	}
	/**
	 * @return the tovkgrp
	 */
	public String getTovkgrp() {
		return tovkgrp;
	}
	/**
	 * @param tovkgrp the tovkgrp to set
	 */
	public void setTovkgrp(String tovkgrp) {
		this.tovkgrp = tovkgrp;
	}
	/**
	 * @return the zkunnr_z3
	 */
	public String getZkunnr_z3() {
		return zkunnr_z3;
	}
	/**
	 * @param zkunnr_z3 the zkunnr_z3 to set
	 */
	public void setZkunnr_z3(String zkunnr_z3) {
		this.zkunnr_z3 = zkunnr_z3;
	}
	/**
	 * @return the zrqflg
	 */
	public String getZrqflg() {
		return zrqflg;
	}
	/**
	 * @param zrqflg the zrqflg to set
	 */
	public void setZrqflg(String zrqflg) {
		this.zrqflg = zrqflg;
	}
	/**
	 * @return the kunnr
	 */
	public String getKunnr() {
		return kunnr;
	}
	/**
	 * @param kunnr the kunnr to set
	 */
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	/**
	 * @return the zagnt
	 */
	public String getZagnt() {
		return zagnt;
	}
	/**
	 * @param zagnt the zagnt to set
	 */
	public void setZagnt(String zagnt) {
		this.zagnt = zagnt;
	}
	/**
	 * @return the spdocu
	 */
	public String getSpdocu() {
		return spdocu;
	}
	/**
	 * @param spdocu the spdocu to set
	 */
	public void setSpdocu(String spdocu) {
		this.spdocu = spdocu;
	}
}
