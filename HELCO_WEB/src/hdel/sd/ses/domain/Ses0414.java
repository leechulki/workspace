package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Ses0414 extends CommonDomain {
	private static final long serialVersionUID = 1L;
	
	private String MANDT = "";
	private String TEMNO = "";
	private String TEMNONM = "";
	private String ZZACTSS = "";
	private String ZZACTSSNM = "";
	private String ZLEVE = "";
	private String CELLP = "";
	private String ZTEXT = "";
	private String RETIF = "";
	private String VKGRP = "";
	private String VKGRPNM = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
	/**
	 * @return the mANDT
	 */
	public String getMANDT() {
		return MANDT;
	}
	/**
	 * @param mANDT the mANDT to set
	 */
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	/**
	 * @return the tEMNO
	 */
	public String getTEMNO() {
		return TEMNO;
	}
	/**
	 * @param tEMNO the tEMNO to set
	 */
	public void setTEMNO(String tEMNO) {
		TEMNO = tEMNO;
	}
	/**
	 * @return the tEMNONM
	 */
	public String getTEMNONM() {
		return TEMNONM;
	}
	/**
	 * @param tEMNONM the tEMNONM to set
	 */
	public void setTEMNONM(String tEMNONM) {
		TEMNONM = tEMNONM;
	}
	/**
	 * @return the zZACTSS
	 */
	public String getZZACTSS() {
		return ZZACTSS;
	}
	/**
	 * @param zZACTSS the zZACTSS to set
	 */
	public void setZZACTSS(String zZACTSS) {
		ZZACTSS = zZACTSS;
	}
	/**
	 * @return the zZACTSSNM
	 */
	public String getZZACTSSNM() {
		return ZZACTSSNM;
	}
	/**
	 * @param zZACTSSNM the zZACTSSNM to set
	 */
	public void setZZACTSSNM(String zZACTSSNM) {
		ZZACTSSNM = zZACTSSNM;
	}
	/**
	 * @return the zLEVE
	 */
	public String getZLEVE() {
		return ZLEVE;
	}
	/**
	 * @param zLEVE the zLEVE to set
	 */
	public void setZLEVE(String zLEVE) {
		ZLEVE = zLEVE;
	}
	/**
	 * @return the cELLP
	 */
	public String getCELLP() {
		return CELLP;
	}
	/**
	 * @param cELLP the cELLP to set
	 */
	public void setCELLP(String cELLP) {
		CELLP = cELLP;
	}
	/**
	 * @return the zTEXT
	 */
	public String getZTEXT() {
		return ZTEXT;
	}
	/**
	 * @param zTEXT the zTEXT to set
	 */
	public void setZTEXT(String zTEXT) {
		ZTEXT = zTEXT;
	}
	/**
	 * @return the rETIF
	 */
	public String getRETIF() {
		return RETIF;
	}
	/**
	 * @param rETIF the rETIF to set
	 */
	public void setRETIF(String rETIF) {
		RETIF = rETIF.trim();
	}
	/**
	 * @return the vKGRP
	 */
	public String getVKGRP() {
		return VKGRP;
	}
	/**
	 * @param vKGRP the vKGRP to set
	 */
	public void setVKGRP(String vKGRP) {
		VKGRP = vKGRP;
	}
	/**
	 * @return the vKGRPNM
	 */
	public String getVKGRPNM() {
		return VKGRPNM;
	}
	/**
	 * @param vKGRPNM the vKGRPNM to set
	 */
	public void setVKGRPNM(String vKGRPNM) {
		VKGRPNM = vKGRPNM;
	}
	/**
	 * @return the tstmp
	 */
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	/**
	 * @param tstmp the tstmp to set
	 */
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}