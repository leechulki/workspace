package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;

/**
 * �����ȣ ��ȸ (Com0180) CommonDomain
 * @Comment 
 *     	- Com0180C(�����ȣ ��ȸ) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.08.01  ���翵  :  initial version 
 * @version 1.0
 */

public class Com0180 extends CommonDomain {
 
	private String zzpjt_id;
	private String bstnk;

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
}
