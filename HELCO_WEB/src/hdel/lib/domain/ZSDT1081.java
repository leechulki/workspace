package hdel.lib.domain;

public class ZSDT1081 extends CommonDomain {
	private static final long serialVersionUID = 1L;
	
	private String MANDT = "";
	private String TEMNO = "";
	private String VKGRP = "";
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