package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;

public class Com0260 extends CommonDomain {

	private String mandt;		//클라이언트
	private String zrqseq;		// 견적번호
	private String gsnam; 		// 공사명
	
	public String getZrqseq() {
		return zrqseq;
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public void setZrqseq(String zrqseq) {
		this.zrqseq = zrqseq;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	 
	
}
