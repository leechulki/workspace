package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;

public class Com0250 extends CommonDomain {
 
	private String mandt;		//클라이언트
	private String qtnum;		// 견적번호
	private String qtser; 		// 견적차수
	private String qtdat; 		// 견적일자
	private String kunnr; 		// 고객번호
	private String gsnam; 		// 공사명
	
	
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
	public String getQtdat() {
		return qtdat;
	}
	public void setQtdat(String qtdat) {
		this.qtdat = qtdat;
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
	 
	
}
