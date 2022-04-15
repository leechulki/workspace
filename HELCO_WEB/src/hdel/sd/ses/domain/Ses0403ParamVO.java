package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0403ParamVO extends ParameterVO {
	private String mandt;
	private String frvkbur;
	private String frvkgrp;
	private String tovkbur;
	private String tovkgrp;
	private String lifnr;
	private String delfg;

	
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
	 * @return the frvkbur
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

	public String getDelfg() {
		return delfg;
	}
	public void setDelfg(String delfg) {
		this.delfg = delfg;
	}
}