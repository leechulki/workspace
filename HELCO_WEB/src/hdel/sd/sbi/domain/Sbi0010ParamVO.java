package hdel.sd.sbi.domain;

import hdel.lib.domain.ParameterVO;

public class Sbi0010ParamVO extends ParameterVO {	
	
	/**
	 * 
	 */
	
	private String MANDT;
	private String LAND;
	
	public String getLAND() {
		return LAND;
	}
	public void setLAND(String lAND) {
		LAND = lAND;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	
}
