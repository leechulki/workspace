package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0300ParamVO extends ParameterVO {

	private String mandt;
	private String dealer_cd; // 담당자코드
	private String dealer_name; // 담당자명
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getDealer_cd() {
		return dealer_cd;
	}
	public void setDealer_cd(String dealer_cd) {
		this.dealer_cd = dealer_cd;
	}
	public String getDealer_name() {
		return dealer_name;
	}
	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}
}
