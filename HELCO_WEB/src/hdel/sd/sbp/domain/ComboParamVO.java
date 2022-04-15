package hdel.sd.sbp.domain;

public class ComboParamVO {
	private String MANDT; 	// 클라이언트
	private String DOMNAME; // 수주가능성
	private String LANG; // 설치지역

	public String getMANDT() {
		return MANDT;
	}

	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}

	public String getDOMNAME() {
		return DOMNAME;
	}

	public void setDOMNAME(String dOMNAME) {
		DOMNAME = dOMNAME;
	}

	public String getLANG() {
		return LANG;
	}

	public void setLANG(String lANG) {
		LANG = lANG;
	}

}
