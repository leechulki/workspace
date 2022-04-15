package hdel.sd.ssa.domain;

import hdel.lib.domain.CommonDomain;


/**
 * 매출채권 명세 및 채권현황(청구별)(Sbp0010) CommonDomain
 * @Comment 
 *     	- Ssa0020C(매출채권 명세 및 채권현황(청구별)) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class Ssa0020 extends CommonDomain {
 
	private String zclose;			// 마감일자 
	 
	public String getZclose() {
		return zclose;
	}
	public void setZclose(String zclose) {
		this.zclose = zclose;
	} 	 
}
