package hdel.sd.ses.domain;

import com.sap.domain.Datum;

import hdel.lib.domain.CommonDomain;

public class Ses0405 extends CommonDomain {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -2791467608200793923L;

	private String mandt;
	private String bsdat;
	private String qtnum;
	private String qtser;
	private String qtseq;
	private String del90;
	private Integer del90Days;
	private Datum frcmfcdat = new Datum();
	private Datum ildat = new Datum();
	private Datum findat = new Datum();
	private Datum dcsdat = new Datum();
	private String fr_bsdat;
	private String to_bsdat;
	private String fr_vkbur;
	private String to_vkbur;
	private String fr_vkgrp;
	private String to_vkgrp;
	private String dept;
	private String dept_n;
	private String vkbur;
	private String vkburnm;
    private String vkgrp;
    private String vkgrpnm;
    private String gsnam;
    private String pjtid;
    private String spec;
    private String kunnr;
    private String kunnrnm;
    private String parz2;
    private String parz2nm;
    private String parz3;
    private String parz3nm;
    private Integer daysno;
    private Integer monthno;

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
	 * @return the bsdat
	 */
	public String getBsdat() {
		return bsdat;
	}
	/**
	 * @param bsdat the bsdat to set
	 */
	public void setBsdat(String bsdat) {
		this.bsdat = bsdat;
	}
	/**
	 * @return the qtnum
	 */
	public String getQtnum() {
		return qtnum == null ? "" : qtnum;
	}
	/**
	 * @param qtnum the qtnum to set
	 */
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum == null ? "" : qtnum;
	}
	/**
	 * @return the qtser
	 */
	public String getQtser() {
		return qtser == null ? "" : qtser;
	}
	/**
	 * @param qtser the qtser to set
	 */
	public void setQtser(String qtser) {
		this.qtser = qtser == null ? "" : qtser;
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
	 * @return the del90
	 */
	public String getDel90() {
		return del90;
	}
	/**
	 * @param del90 the del90 to set
	 */
	public void setDel90(String del90) {
		this.del90 = del90;
	}
	/**
	 * @return the del90dDys
	 */
	public Integer getDel90Days() {
		return del90Days;
	}
	/**
	 * @param del90dDys the del90Days to set
	 */
	public void setDel90Days(Integer del90Days) {
		this.del90Days = del90Days;
	}
	public Datum getFrcmfcdat() {
		return frcmfcdat;
	}
	public void setFrcmfcdat(Datum frcmfcdat) {
		this.frcmfcdat = frcmfcdat;
	}
	public Datum getIldat() {
		return ildat;
	}
	public void setIldat(Datum ildat) {
		this.ildat = ildat;
	}
	public Datum getFindat() {
		return findat;
	}
	public void setFindat(Datum findat) {
		this.findat = findat;
	}
	public Datum getDcsdat() {
		return dcsdat.isInitial() ? ( ildat.isInitial() ? frcmfcdat : ildat ) : dcsdat;
	}
	public void setDcsdat() {
		this.dcsdat = getDcsdat();
	}
	public void setDcsdat(Datum dcsdat) {
		this.dcsdat = dcsdat;
	}
	/**
	 * @return the fr_bsdat
	 */
	public String getFr_bsdat() {
		return fr_bsdat==null ? "00000000" : fr_bsdat;
	}
	/**
	 * @param fr_bsdat the fr_bsdat to set
	 */
	public void setFr_bsdat(String fr_bsdat) {
		this.fr_bsdat = fr_bsdat==null ? "00000000" : fr_bsdat;
	}
	/**
	 * @return the to_bsdat
	 */
	public String getTo_bsdat() {
		return to_bsdat==null ? "00000000" : to_bsdat;
	}
	/**
	 * @param to_bsdat the to_bsdat to set
	 */
	public void setTo_bsdat(String to_bsdat) {
		this.to_bsdat = to_bsdat==null ? "00000000" : to_bsdat;
	}
	/**
	 * @return the fr_vkbur
	 */
	public String getFr_vkbur() {
		return fr_vkbur==null ? "" : fr_vkbur;
	}
	/**
	 * @param fr_vkbur the fr_vkbur to set
	 */
	public void setFr_vkbur(String fr_vkbur) {
		this.fr_vkbur = fr_vkbur==null ? "" : fr_vkbur;
	}
	/**
	 * @return the to_vkbur
	 */
	public String getTo_vkbur() {
		return to_vkbur==null ? "" : to_vkbur;
	}
	/**
	 * @param to_vkbur the to_vkbur to set
	 */
	public void setTo_vkbur(String to_vkbur) {
		this.to_vkbur = to_vkbur==null ? "" : to_vkbur;
	}
	/**
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}
	/**
	 * @param dept the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	/**
	 * @return the dept_n
	 */
	public String getDept_n() {
		return dept_n;
	}
	/**
	 * @param dept_n the dept_n to set
	 */
	public void setDept_n(String dept_n) {
		this.dept_n = dept_n;
	}
	/**
	 * @return the fr_vkgrp
	 */
	public String getFr_vkgrp() {
		return fr_vkgrp==null ? "" : fr_vkgrp;
	}
	/**
	 * @param fr_vkgrp the fr_vkgrp to set
	 */
	public void setFr_vkgrp(String fr_vkgrp) {
		this.fr_vkgrp = fr_vkgrp==null ? "" : fr_vkgrp;
	}
	/**
	 * @return the to_vkgrp
	 */
	public String getTo_vkgrp() {
		return to_vkgrp==null ? "" : to_vkgrp;
	}
	/**
	 * @param to_vkgrp the to_vkgrp to set
	 */
	public void setTo_vkgrp(String to_vkgrp) {
		this.to_vkgrp = to_vkgrp==null ? "" : to_vkgrp;
	}
	/**
	 * @return the vkbur
	 */
	public String getVkbur() {
		return vkbur==null ? "" : vkbur;
	}
	/**
	 * @param vkbur the vkbur to set
	 */
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur==null ? "" : vkbur;
	}
	/**
	 * @return the vkburnm
	 */
	public String getVkburnm() {
		return vkburnm==null ? "" : vkburnm;
	}
	/**
	 * @param vkburnm the vkburnm to set
	 */
	public void setVkburnm(String vkburnm) {
		this.vkburnm = vkburnm==null ? "" : vkburnm;
	}
	/**
	 * @return the vkgrp
	 */
	public String getVkgrp() {
		return vkgrp==null ? "" : vkgrp;
	}
	/**
	 * @param vkgrp the vkgrp to set
	 */
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp==null ? "" : vkgrp;
	}
	/**
	 * @return the vkgrpnm
	 */
	public String getVkgrpnm() {
		return vkgrpnm==null ? "" : vkgrpnm;
	}
	/**
	 * @param vkgrpnm the vkgrpnm to set
	 */
	public void setVkgrpnm(String vkgrpnm) {
		this.vkgrpnm = vkgrpnm==null ? "" : vkgrpnm;
	}
	/**
	 * @return the gsnam
	 */
	public String getGsnam() {
		return gsnam;
	}
	/**
	 * @param gsnam the gsnam to set
	 */
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	/**
	 * @return the pjtid
	 */
	public String getPjtid() {
		return  pjtid;
	}
	/**
	 * @param pjtid the pjtid to set
	 */
	public void setPjtid(String pjtid) {
		this.pjtid = pjtid;
	}
	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
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
	 * @return the kunnrnm
	 */
	public String getKunnrnm() {
		return kunnrnm;
	}
	/**
	 * @param kunnrnm the kunnrnm to set
	 */
	public void setKunnrnm(String kunnrnm) {
		this.kunnrnm = kunnrnm;
	}
	/**
	 * @return the parz2
	 */
	public String getParz2() {
		return parz2;
	}
	/**
	 * @param parz2 the parz2 to set
	 */
	public void setParz2(String parz2) {
		this.parz2 = parz2;
	}
	/**
	 * @return the parz2nm
	 */
	public String getParz2nm() {
		return parz2nm;
	}
	/**
	 * @param parz2nm the parz2nm to set
	 */
	public void setParz2nm(String parz2nm) {
		this.parz2nm = parz2nm;
	}
	/**
	 * @return the parz3
	 */
	public String getParz3() {
		return parz3==null ? "" : parz3;
	}
	/**
	 * @param parz3 the parz3 to set
	 */
	public void setParz3(String parz3) {
		this.parz3 = parz3==null ? "" : parz3;
	}
	/**
	 * @return the parz3nm
	 */
	public String getParz3nm() {
		return parz3nm==null ? "" : parz3nm;
	}
	/**
	 * @param parz3nm the parz3nm to set
	 */
	public void setParz3nm(String parz3nm) {
		this.parz3nm = parz3nm==null ? "" : parz3nm;
	}
	/**
	 * @return the daysno
	 */
	public Integer getDaysno() {
		return daysno;
	}
	/**
	 * @param daysno the daysno to set
	 */
	public void setDaysno(Integer daysno) {
		this.daysno = daysno;
	}
	/**
	 * @return the monthno
	 */
	public Integer getMonthno() {
		return monthno;
	}
	/**
	 * @param monthno the monthno to set
	 */
	public void setMonthno(Integer monthno) {
		this.monthno = monthno;
	}
}