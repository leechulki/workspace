package hdel.sd.ssa.domain;

import hdel.lib.domain.ParameterVO;


/**
 * ����ä�� �� �� ä����Ȳ(û����)(Ssa0020ParamVO) ParameterVO
 * @Comment 
 *     	- Ssa0020C(����ä�� �� �� ä����Ȳ(û����)) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */ 


public class Ssa0020ParamVO extends ParameterVO {

	private String mandt;	// 
	private String zyear;	// ���س⵵
	private String zmonth;	// ���ؿ���
	
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
