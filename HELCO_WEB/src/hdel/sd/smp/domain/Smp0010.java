package hdel.sd.smp.domain;

import hdel.lib.domain.CommonDomain;

public class Smp0010 extends CommonDomain {
 
	private String lifnr;		// 협력업체코드
	
	private String name1; 		// 협력업체명

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
