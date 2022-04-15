package hdel.sd.sbp.domain;

import hdel.lib.domain.ParameterVO;


/**
 * �����ȹ����/���� (Sbp0070ParamVO) ParameterVO
 * @Comment 
 *     	- Sbp0070C(�����ȹ����/����) ���� ��� 
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


public class Sbp0070ParamVO extends ParameterVO {
	
	public String MANDT;    // CLIENT
	public String LANG;    	// ���
	public String USER_ID;	// WEB USER_ID 
	
	public String ZPYEAR;   // ���⵵  
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