package hdel.sd.com.domain;

public class SuvFloorVO {

	private String APPLYFLOOR;
	private String SUVPRD;
	
	public String getAPPLYFLOOR() {
		return APPLYFLOOR;
	}
	public void setAPPLYFLOOR(String aPPLYFLOOR) {
		APPLYFLOOR = aPPLYFLOOR;
	}
	public String getSUVPRD() {
		return SUVPRD;
	}
	public void setSUVPRD(String sUVPRD) {
		SUVPRD = sUVPRD;
	}
	
	@Override
	public String toString() {
		return "SuvFloorVO [APPLYFLOOR=" + APPLYFLOOR + ", SUVPRD=" + SUVPRD + "]";
	}
	
}
