package hdel.sd.sbi.domain;

import org.apache.commons.lang.math.NumberUtils;

import hdel.lib.domain.CommonDomain;

public class Sbi0040 extends CommonDomain {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mandt;
	private String vkbur;
	private String vkburnm;
	private String vkgrp;
	private String vkgrpnm;
	private String kunnr;
	private String kunnrnm;
	private String lifnr;
	private String entdt;
	private String firstErdat;
	private String fr_vkbur;
	private String to_vkbur;
	private String fr_vkgrp;
	private String to_vkgrp;
	private String fr_entdt;
	private String to_entdt;
	private boolean editable;

	public String getEntdt() {
		return String.format("%08d", NumberUtils.toInt(entdt, 0));
	}
	public String getFirstErdat() {
		return String.format("%08d", NumberUtils.toInt(firstErdat, 0));
	}
	public String getKunnr() {
		return (kunnr==null || kunnr.equals("")) ? "0000000000" : kunnr;
	}
	public String getKunnrnm() {
		return kunnrnm == null ? "" : kunnrnm;
	}
	public String getLifnr() {
		return String.format("%010d", NumberUtils.toInt(lifnr, 0));
	}
	public String getMandt() {
		return mandt;
	}
	public String getVkbur() {
		return vkbur == null ? "" : vkbur;
	}
	public String getVkburnm() {
		return vkburnm == null ? "" : vkburnm;
	}
	public String getVkgrp() {
		return vkgrp == null ? "" : vkgrp;
	}
	public String getVkgrpnm() {
		return vkgrpnm == null ? "" : vkgrpnm;
	}
	public void setEntdt(String entdt) {
		this.entdt = String.format("%08d", NumberUtils.toInt(entdt, 0));
	}
	public void setFirstErdat(String firstErdat) {
		this.firstErdat = String.format("%08d", NumberUtils.toInt(firstErdat, 0));
	}
	public void setKunnr(String kunnr) {
		this.kunnr = (kunnr==null || kunnr.equals("")) ? "0000000000" : kunnr;
	}
	public void setKunnrnm(String kunnrnm) {
		this.kunnrnm = kunnrnm == null ? "" : kunnrnm;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = String.format("%010d", NumberUtils.toInt(lifnr, 0));
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur == null ? "" : vkbur;
	}
	public void setVkburnm(String vkburnm) {
		this.vkburnm = vkburnm == null ? "" : vkburnm;
	}
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp == null ? "" : vkgrp;
	}
	public void setVkgrpnm(String vkgrpnm) {
		this.vkgrpnm = vkgrpnm == null ? "" : vkgrpnm;
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

	public String getFr_entdt() {
		return String.format("%08d", NumberUtils.toInt(fr_entdt, 0));
	}
	public void setFr_entdt(String fr_entdt) {
		this.fr_entdt = String.format("%08d", NumberUtils.toInt(fr_entdt, 0));
	}
	public String getTo_entdt() {
		return String.format("%08d", NumberUtils.toInt(to_entdt, 0));
	}
	public void setTo_entdt(String to_entdt) {
		this.to_entdt = String.format("%08d", NumberUtils.toInt(to_entdt, 0));
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}