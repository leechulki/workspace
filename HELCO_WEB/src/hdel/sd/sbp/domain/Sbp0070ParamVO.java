package hdel.sd.sbp.domain;

import hdel.lib.domain.ParameterVO;


/**
 * 사업계획오픈/마감 (Sbp0070ParamVO) ParameterVO
 * @Comment 
 *     	- Sbp0070C(사업계획오픈/마감) 에서 사용 
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public class Sbp0070ParamVO extends ParameterVO {
	
	public String MANDT;    // CLIENT
	public String LANG;    	// 언어
	public String USER_ID;	// WEB USER_ID 
	
	public String ZPYEAR;   // 편성년도  
	public String getMANDT(){
		return MANDT;
	}
	public String getLANG(){
		return LANG;
	}
	public String getUSER_ID(){
		return USER_ID;
	} 
	public String getZPYEAR(){
		return ZPYEAR;
	} 
	
	public void setMANDT(String aValue) {
		MANDT=aValue;
	}
	public void setLANG(String aValue) {
		LANG=aValue;
	}
	public void setUSER_ID(String aValue) {
		USER_ID=aValue;
	}  
	public void setZPYEAR(String aValue) {
		ZPYEAR=aValue;
	} 
}