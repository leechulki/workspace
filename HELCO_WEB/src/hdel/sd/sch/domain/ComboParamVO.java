package hdel.sd.sch.domain;

public class ComboParamVO {
	private String MANDT; // 클라이언트
	private String ATNAM; // 기종, 용도, 인승, 속도
	private String CDATE_YY;
	private String CDATE_MM;
	private String LANG; // 언어
	private String CODE1; // 구분2, 구분3, 설치1
	private String SORTL; // 수주협력업체, 설치협력업체
	private String LIFNR; // 수주협력업체, 설치협력업체
	private String NAME1; // 수주협력업체, 설치협력업체
	private String DOMNAME; // 구분1, 수주유형

	public String getMANDT() {
		return MANDT;
	}

	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}

	public String getATNAM() {
		return ATNAM;
	}

	public void setATNAM(String aTNAM) {
		ATNAM = aTNAM;
	}

	public String getCDATE_YY() {
		return CDATE_YY;
	}

	public void setCDATE_YY(String cDATE_YY) {
		CDATE_YY = cDATE_YY;
	}

	public String getCDATE_MM() {
		return CDATE_MM;
	}

	public void setCDATE_MM(String cDATE_MM) {
		CDATE_MM = cDATE_MM;
	}

	public String getLANG() {
		return LANG;
	}

	public void setLANG(String lANG) {
		LANG = lANG;
	}

	public String getCODE1() {
		return CODE1;
	}

	public void setCODE1(String cODE1) {
		CODE1 = cODE1;
	}

	public String getSORTL() {
		return SORTL;
	}

	public void setSORTL(String sORTL) {
		SORTL = sORTL;
	}

	public String getLIFNR() {
		return LIFNR;
	}

	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}

	public String getNAME1() {
		return NAME1;
	}

	public void setNAME1(String nAME1) {
		NAME1 = nAME1;
	}

	public String getDOMNAME() {
		return DOMNAME;
	}

	public void setDOMNAME(String dOMNAME) {
		DOMNAME = dOMNAME;
	}

}
