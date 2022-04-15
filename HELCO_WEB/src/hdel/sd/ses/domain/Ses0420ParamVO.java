package hdel.sd.ses.domain;

import org.apache.commons.lang.math.NumberUtils;

import hdel.lib.domain.ParameterVO;

public class Ses0420ParamVO extends ParameterVO {

	private String mandt;
	private String frzrqdat;
	private String tozrqdat;
	private String frdeldat;
	private String todeldat;
	private String vkbur;
	private String vkgrp;
	private String zkunnr;
	private String gubun;
	private String qtso_no;
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
	private String ycont;
	
	private String frildat;		//2017.07.11 출하일자 조건 추가 by mj.Shin 
	private String toildat;
	
	private String frfrcmfcdat;
	private String tofrcmfcdat;
	
	private String part;
	
	private String spras;
	
	private int d_zqntytot;
	
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
	 * @return the frdeldat
	 */
	public String getFrdeldat() {
		return String.format("%08d", NumberUtils.toInt(frdeldat, 0));
	}
	/**
	 * @param frdeldat the frdeldat to set
	 */
	public void setFrdeldat(String frdeldat) {
		this.frdeldat = String.format("%08d", NumberUtils.toInt(frdeldat, 0));
	}
	/**
	 * @return the todeldat
	 */
	public String getTodeldat() {
		return String.format("%08d", NumberUtils.toInt(todeldat, 0));
	}
	/**
	 * @param todeldat the todeldat to set
	 */
	public void setTodeldat(String todeldat) {
		this.todeldat = String.format("%08d", NumberUtils.toInt(todeldat, 0));
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
	public String getYcont() {
		return ycont;
	}
	public void setYcont(String ycont) {
		this.ycont = ycont;
	}
	
	public String getFrildat() {		
		return String.format("%08d",  NumberUtils.toInt(frildat, 0));
	}
	
	public void setFrildat(String frildat) {
		this.frildat = String.format("%08d", NumberUtils.toInt(frildat, 0));
	}
	
	public String getToildat() {
		return String.format("%08d",  NumberUtils.toInt(toildat, 0));
	}
	
	public void setToildat(String toildat) {
		this.toildat = String.format("%08d", NumberUtils.toInt(toildat, 0));
		
	}
	public String getFrfrcmfcdat() {
		return String.format("%08d",  NumberUtils.toInt(frfrcmfcdat, 0));
		
	}
	public void setFrfrcmfcdat(String frfrcmfcdat) {
		this.frfrcmfcdat = String.format("%08d", NumberUtils.toInt(frfrcmfcdat, 0));
		
	}
	public String getTofrcmfcdat() {
		return String.format("%08d",  NumberUtils.toInt(tofrcmfcdat, 0));
		
	}
	public void setTofrcmfcdat(String tofrcmfcdat) {
		this.tofrcmfcdat = String.format("%08d", NumberUtils.toInt(tofrcmfcdat, 0));
		
	}
	/**
	 * @return the part
	 */
	public String getPart() {
		return part;
	}
	/**
	 * @param part the part to set
	 */
	public void setPart(String part) {
		this.part = part;
	}
	/**
	 * @return the spras
	 */
	public String getSpras() {
		return spras;
	}
	/**
	 * @param spras the spras to set
	 */
	public void setSpras(String spras) {
		this.spras = spras;
	}
	public int getD_zqntytot() {
		return d_zqntytot;
	}
	public void setD_zqntytot(int d_zqntytot) {
		this.d_zqntytot = d_zqntytot;
	}	
	
}
