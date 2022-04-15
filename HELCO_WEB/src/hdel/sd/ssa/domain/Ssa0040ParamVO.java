package hdel.sd.ssa.domain;

import hdel.lib.domain.ParameterVO;


/**
 * 세금계산서C청구현황(Ssa0040ParamVO) ParameterVO
 * @Comment 
 *     	- Ssa0040C(세금계산서C청구현황) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.15  khs  :  initial version 
 * @version 1.0
 */ 


public class Ssa0040ParamVO extends ParameterVO {

	private String mandt;	// 

	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	
}
