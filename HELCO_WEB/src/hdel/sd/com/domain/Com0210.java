package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;

/**
 * 수주계획번호 조회 (Com0210) CommonDomain
 * @Comment 
 *     	- Com0210C(수주계획번호 조회) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.08.29  김재영  :  initial version 
 * @version 1.0
 */

public class Com0210 extends CommonDomain {
 
	private String zpym;
	private String sonnr;
	private String gsnam;

	public String getZpym() {
		return zpym;
	}
	public void setZpym(String zpym) {
		this.zpym = zpym;
	}
	public String getSonnr() {
		return sonnr;
	}
	public void setSonnr(String sonnr) {
		this.sonnr = sonnr;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
}
