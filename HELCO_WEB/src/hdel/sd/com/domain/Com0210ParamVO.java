package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 수주계획번호 조회 (Com0210ParamVO) ParameterVO
 * @Comment 
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.08.29  김재영  :  initial version 
 * @version 1.0
 */

public class Com0210ParamVO extends ParameterVO {

	private String mandt;	// 
	private String zpym;
	private String sonnr;
	private String gsnam;
	private String zkunnr;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
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
	public String getZkunnr() {
		return zkunnr;
	}
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
}
