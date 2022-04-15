package hdel.sd.ssa.domain;

import hdel.lib.domain.CommonDomain;


/**
 * ���ݰ�꼭Cû����Ȳ(Ssa0040) CommonDomain
 * @Comment 
 *     	- Ssa0040C(���ݰ�꼭Cû����Ȳ) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.13  khs  :  initial version 
 * @version 1.0
 */ 


public class Ssa0040 extends CommonDomain {
 
	private String mandt;
	private String acode;			// �������� 
	private String acode_name;	// ����������
	private String scode;			// �����ڵ� 
	private String scode_t;			// ������
	 
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getAcode() {
		return acode;
	}
	public void setAcode(String acode) {
		this.acode = acode;
	}
	public String getAcode_name() {
		return acode_name;
	}
	public void setAcode_name(String acode_name) {
		this.acode_name = acode_name;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	} 	 
	public String getScode_t() {
		return scode_t;
	}
	public void setScode_t(String scode_t) {
		this.scode_t = scode_t;
	} 	
}
