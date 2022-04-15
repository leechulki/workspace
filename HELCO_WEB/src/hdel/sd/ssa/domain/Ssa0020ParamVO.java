package hdel.sd.ssa.domain;

import hdel.lib.domain.ParameterVO;


/**
 * 매출채권 명세 및 채권현황(청구별)(Ssa0020ParamVO) ParameterVO
 * @Comment 
 *     	- Ssa0020C(매출채권 명세 및 채권현황(청구별)) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class Ssa0020ParamVO extends ParameterVO {

	private String mandt;	// 
	private String zyear;	// 기준년도
	private String zmonth;	// 기준월도
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	} 
	public String getZyear() {
		return zyear;
	}
	public void setZyear(String zyear) {
		this.zyear = zyear;
	}
	public String getZmonth() {
		return zmonth;
	}
	public void setZmonth(String zmonth) {
		this.zmonth = zmonth;
	}
	
}
