package hdel.lib.domain;

public class ZSDT0161 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	public static enum cistat { 
		SG, //재요청
		SR, //요청
		TB,	//반려
		TA,	//접수
		TC,	//취소
		TF,	//완료
		TR,	//의뢰
		TS,	//자체작도
		TO, //영업기술완료
		TK, //외주완료(검토요청)반려
		EA,	//외주접수
		EB,	//외주반려
		EF,	//외주완료 
	};
	
	private String mandt = "";
	private String zrqseq = "";
	private String cistat = "";
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

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
	 * @return the zrqseq
	 */
	public String getZrqseq() {
		return zrqseq;
	}
	/**
	 * @param zrqseq the zrqseq to set
	 */
	public void setZrqseq(String zrqseq) {
		this.zrqseq = zrqseq;
	}
	/**
	 * @return the cistat
	 */
	public String getCistat() {
		return cistat;
	}
	/**
	 * @param cistat the cistat to set
	 */
	public void setCistat(String cistat) {
		this.cistat = cistat;
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