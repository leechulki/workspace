package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0250ParamVO extends ParameterVO {

	private String mandt;		//클라이언트
	private String qtnum;		// 견적번호
	private String qtser; 		// 견적차수
	private String kunnr; 		// 고객번호
	private String gsnam; 		// 공사명
	private String fr_qtdat; 		// 견적일자
	private String to_qtdat; 		// 견적일자
	
	private String gubun;
		
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public String getQtser() {
		return qtser;
	}
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}	
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getFr_qtdat() {
		return fr_qtdat;
	}
	public void setFr_qtdat(String fr_qtdat) {
		this.fr_qtdat = fr_qtdat;
	}
	public String getTo_qtdat() {
		return to_qtdat;
	}
	public void setTo_qtdat(String to_qtdat) {
		this.to_qtdat = to_qtdat;
	}
}
