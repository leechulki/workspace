package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0030ParamVO extends ParameterVO {

	private String lang;
	private String mandt;
	private String frqtdat;
	private String toqtdat;
	private String vkbur;
	private String vkgrp;
	private String zprgflg;
	private String zkunnr;
	private String frzestdat;
	private String tozestdat;
	private String kunnr;
	private String auart;
	private String qtnum;
	private String qtser;

	private String zregn;
	private String gno;
	private String darijumzkunnr;	
	
	private String max_qtser;		
	private String tquot;
	private String qtgbn; //견적구분 조회조건 추가 2021.04.16.
	private String vbeln; //프로젝트 번호 중복 확인 2021.05.06.
	
	// 21.006.04 모의견적서 속성 추가
	private String smlysno = "N";
	private String smlqtnum;
	private String smlqtser;
	private String cuser;
	
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
	 * @return the zprgflg
	 */
	public String getZprgflg() {
		return zprgflg;
	}
	/**
	 * @param zprgflg the zprgflg to set
	 */
	public void setZprgflg(String zprgflg) {
		this.zprgflg = zprgflg;
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
	 * @return the frzestdat
	 */
	public String getFrzestdat() {
		return frzestdat;
	}
	/**
	 * @param frzestdat the frzestdat to set
	 */
	public void setFrzestdat(String frzestdat) {
		this.frzestdat = frzestdat;
	}
	/**
	 * @return the tozestdat
	 */
	public String getTozestdat() {
		return tozestdat;
	}
	/**
	 * @param tozestdat the tozestdat to set
	 */
	public void setTozestdat(String tozestdat) {
		this.tozestdat = tozestdat;
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

	public String getAuart() {
		return auart;
	}
	public void setAuart(String auart) {
		this.auart = auart;
	}
	
	public String getQtgbn() { //견적구분 조회조건 추가 2021.04.16.
		return qtgbn;
	}
	public void setQtgbn(String qtgbn) {
		this.qtgbn = qtgbn;
	}
		
	
	public String getVbeln() { //프로젝트 번호 중복 확인 2021.05.06.
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	
	
	/**
	 * @return the qtnum
	 */
	public String getQtnum() {
		return qtnum;
	}
	/**
	 * @param qtnum the qtnum to set
	 */
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
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
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	/**
	 * @return the zregn
	 */
	public String getZregn() {
		return zregn;
	}
	/**
	 * @param zregn the zregn to set
	 */
	public void setZregn(String zregn) {
		this.zregn = zregn;
	}
	/**
	 * @return the gno
	 */
	public String getGno() {
		return gno;
	}
	/**
	 * @param gno the gno to set
	 */
	public void setGno(String gno) {
		this.gno = gno;
	}
	/**
	 * @return the darijumzkunnr
	 */
	public String getDarijumzkunnr() {
		return darijumzkunnr;
	}
	/**
	 * @param darijumzkunnr the darijumzkunnr to set
	 */
	public void setDarijumzkunnr(String darijumzkunnr) {
		this.darijumzkunnr = darijumzkunnr;
	}
	/**
	 * @return the max_qtser
	 */
	public String getMax_qtser() {
		return max_qtser;
	}
	/**
	 * @param max_qtser the max_qtser to set
	 */
	public void setMax_qtser(String max_qtser) {
		this.max_qtser = max_qtser;
	}
	/**
	 * @return the tquot
	 */
	public String getTquot() {
		return tquot;
	}
	/**
	 * @param tquot the tquot to set
	 */
	public void setTquot(String tquot) {
		this.tquot = tquot == null ? "0" : tquot;	// null casting
		this.tquot = this.tquot.equals("1") ? "X" : "";	//converting as SAP Boolean
	}

	// 모의견적여부 추가 2021.06.08
	public String getSmlysno() {
		return smlysno;
	}
	public void setSmlysno(String smlysno) {
		if(smlysno == null) {
			smlysno = "N";
		} else {
			if("".equals(smlysno)) {
				smlysno = "N";
			}
		}
		this.smlysno = smlysno;
	}

    public String getSmlqtnum() {
		return smlqtnum;
	}
	public void setSmlqtnum(String smlqtnum) {
		this.smlqtnum = smlqtnum;
	}
	public String getSmlqtser() {
		return smlqtser;
	}
	public void setSmlqtser(String smlqtser) {
		this.smlqtser = smlqtser;
	}
	public String getCuser() {
		return cuser;
	}
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}	
}
