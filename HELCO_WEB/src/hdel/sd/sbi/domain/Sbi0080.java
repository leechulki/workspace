package hdel.sd.sbi.domain;

import org.apache.commons.lang.math.NumberUtils;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Sbi0080 extends CommonDomain {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String MANDT;
	private String RATIOTP;
	private String LAND1;
	private String LANDX;
	private String FRDAT;
	private String TODAT;
	private String RATIO;
	private String LANG;

	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();


	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getRATIOTP() {
		return RATIOTP;
	}
	public void setRATIOTP(String rATIOTP) {
		RATIOTP = rATIOTP;
	}
	public String getLAND1() {
		return LAND1;
	}
	public void setLAND1(String lAND1) {
		LAND1 = lAND1;
	}
	public String getLANDX() {
		return LANDX;
	}
	public void setLANDX(String lANDX) {
		LANDX = lANDX;
	}
	public String getFRDAT() {
		return FRDAT;
	}
	public void setFRDAT(String fRDAT) {
		FRDAT = fRDAT;
	}
	public String getTODAT() {
		return TODAT;
	}
	public void setTODAT(String tODAT) {
		TODAT = tODAT;
	}
	public String getRATIO() {
		return RATIO;
	}
	public void setRATIO(String rATIO) {
		RATIO = rATIO;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	
	
	public String getLANG() {
		return LANG;
	}
	public void setLANG(String lANG) {
		LANG = lANG;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}