package com.helco.xf.cs01.ws;

public class MatchDutyCalcu   
		implements java.io.Serializable {

	/**
	 * 정합성 연산수식 데이터 변수
	 * @param ctx
	 * @throws Exception
	 */
	private static final long serialVersionUID = 1L;
	public MatchDutyCalcu() {
	}
	
	private String MANDT;
	private String SPEC;
	private String CALCULAT;
	private String FOMAT;
	private String BIGO;
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getSPEC() {
		return SPEC;
	}
	public void setSPEC(String sPEC) {
		SPEC = sPEC;
	}
	public String getCALCULAT() {
		return CALCULAT;
	}
	public void setCALCULAT(String cALCULAT) {
		CALCULAT = cALCULAT;
	}
	public String getFOMAT() {
		return FOMAT;
	}
	public void setFOMAT(String fOMAT) {
		FOMAT = fOMAT;
	}
	public String getBIGO() {
		return BIGO;
	}
	public void setBIGO(String bIGO) {
		BIGO = bIGO;
	}
	@Override
	public String toString() {
		return "MatchDutyCalcu [MANDT=" + MANDT + ", SPEC=" + SPEC
				+ ", CALCULAT=" + CALCULAT + ", FOMAT=" + FOMAT + ", BIGO="
				+ BIGO + "]";
	}
	
}