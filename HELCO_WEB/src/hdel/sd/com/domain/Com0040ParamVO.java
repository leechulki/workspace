package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;


/**
 * 거래선목록 조회 (Com0040ParamVO) ParameterVO
 * @Comment 
 *     	- Com0040C(거래선목록 조회) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public class Com0040ParamVO extends ParameterVO {

	private String mandt;	// 
	private String buyr_cd;	// 거래처코드
	private String buyr_nm;	// 거래처명
	private String lang;	// 언어

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
	 * @return the buyr_cd
	 */
	public String getBuyr_cd() {
		return buyr_cd;
	}
	/**
	 * @param buyr_cd the buyr_cd to set
	 */
	public void setBuyr_cd(String buyr_cd) {
		this.buyr_cd = buyr_cd;
	}
	/**
	 * @return the buyr_nm
	 */
	public String getBuyr_nm() {
		return buyr_nm;
	}
	/**
	 * @param buyr_nm the buyr_nm to set
	 */
	public void setBuyr_nm(String buyr_nm) {
		this.buyr_nm = buyr_nm;
	}
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
}
