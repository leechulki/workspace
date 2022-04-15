package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0020ParamVO extends ParameterVO {

	private String mandt;	// 
	private String lifnr;		// 협력업체코드
	private String name1;	// 협력업체명

	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	
	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	
	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}
}
