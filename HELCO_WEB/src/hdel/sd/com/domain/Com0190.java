package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;


/**
 * ��ǰó��� ��ȸ (Com0190) CommonDomain
 * @Comment 
 *     	- Com0190C(��ǰó��� ��ȸ) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


public class Com0190 extends CommonDomain {
 
	private String CODE;			// ��ǰó�ڵ�
	private String CODE_NAME; 		// ��ǰó�� 
	private String INFO;			// ��ǰó����  
	
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String CODE) {
		this.CODE = CODE;
	} 	
	public String getCODE_NAME() {
		return CODE_NAME;
	}
	public void setCODE_NAME(String CODE_NAME) {
		this.CODE_NAME = CODE_NAME;
	} 
	public String getINFO() {
		return INFO;
	}
	public void setINFO(String INFO) {
		this.INFO = INFO;
	} 
}
