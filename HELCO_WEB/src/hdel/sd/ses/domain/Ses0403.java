package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Ses0403 extends CommonDomain {
 
	private String mandt;
	private String vkbur;
    private String burNm;
    private String vkgrp;
    private String grpNm;
    private String lifnr;
    private String lifnrNm;
    private String delfg;
    private String qtnum;
    private String cvabr;
    private String cvabrt;
    private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
    private String sprtfg;
    private String ssprtdat;
    private String esprtdat;
    private String sprtcnt;
	private String sprtLifnr;
    private String sprtLifnrNm;
    private String chn_sno;
    private String chn_snm;

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
	 * @return the burNm
	 */
	public String getBurNm() {
		return burNm;
	}

	/**
	 * @param burNm the burNm to set
	 */
	public void setBurNm(String burNm) {
		this.burNm = burNm;
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
	 * @return the grpNm
	 */
	public String getGrpNm() {
		return grpNm;
	}

	/**
	 * @param grpNm the grpNm to set
	 */
	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
	}

    /**
	 * @return the lifnr
	 */
	public String getLifnr() {
		return lifnr;
	}

	/**
	 * @param lifnr the lifnr to set
	 */
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

    /**
	 * @return the lifnrNm
	 */
	public String getLifnrNm() {
		return lifnrNm;
	}

	/**
	 * @param lifnrNm the lifnrNm to set
	 */
	public void setLifnrNm(String lifnrNm) {
		this.lifnrNm = lifnrNm;
	}

	public String getDelfg() {
		return delfg;
	}

	public void setDelfg(String delfg) {
		this.delfg = delfg;
	}

	public String getQtnum() {
		return qtnum;
	}

	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}

	public String getCvabr() {
		return cvabr;
	}

	public void setCvabr(String cvabr) {
		this.cvabr = cvabr;
	}

	public String getCvabrt() {
		return cvabrt;
	}

	public void setCvabrt(String cvabrt) {
		this.cvabrt = cvabrt;
	}

	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}

	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
	
    public String getSprtfg() {
		return sprtfg;
	}

	public void setSprtfg(String sprtfg) {
		this.sprtfg = sprtfg;
	}

	public String getSsprtdat() {
		return ssprtdat;
	}

	public void setSsprtdat(String ssprtdat) {
		this.ssprtdat = ssprtdat;
	}

	public String getEsprtdat() {
		return esprtdat;
	}

	public void setEsprtdat(String esprtdat) {
		this.esprtdat = esprtdat;
	}

	public String getSprtcnt() {
		return sprtcnt;
	}

	public void setSprtcnt(String sprtcnt) {
		this.sprtcnt = sprtcnt;
	}

	public String getSprtLifnr() {
		return sprtLifnr;
	}

	public void setSprtLifnr(String sprtLifnr) {
		this.sprtLifnr = sprtLifnr;
	}

	public String getSprtLifnrNm() {
		return sprtLifnrNm;
	}

	public void setSprtLifnrNm(String sprtLifnrNm) {
		this.sprtLifnrNm = sprtLifnrNm;
	}

	public String getChn_sno() {
		return chn_sno;
	}

	public void setChn_sno(String chn_sno) {
		this.chn_sno = chn_sno;
	}

	public String getChn_snm() {
		return chn_snm;
	}

	public void setChn_snm(String chn_snm) {
		this.chn_snm = chn_snm;
	}
}