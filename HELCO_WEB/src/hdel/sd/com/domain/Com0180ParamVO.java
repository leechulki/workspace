package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 공사번호 조회 (Com0180ParamVO) ParameterVO
 * @Comment 
 *     	- Com0040C(공사번호 조회) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.08.01  김재영  :  initial version 
 * @version 1.0
 */

public class Com0180ParamVO extends ParameterVO {

	private String mandt;	// 
	private String zzpjt_id;	
	private String bstnk;
	private String erdat_fr;
	private String erdat_to;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZzpjt_id() {
		return zzpjt_id;
	}
	public void setZzpjt_id(String zzpjt_id) {
		this.zzpjt_id = zzpjt_id;
	}
	public String getBstnk() {
		return bstnk;
	}
	public void setBstnk(String bstnk) {
		this.bstnk = bstnk;
	}
	public String getErdat_fr() {
		return erdat_fr;
	}
	public void setErdat_fr(String erdat_fr) {
		this.erdat_fr = erdat_fr;
	}
	public String getErdat_to() {
		return erdat_to;
	}
	public void setErdat_to(String erdat_to) {
		this.erdat_to = erdat_to;
	}
}
