package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;

public class Com0230 extends CommonDomain {
 
	private String sman_cd;			// 담당자코드
	private String sman_nm; 		// 담당자명
	private String sman_info; 		// 담당자정보
	 
	public String getSman_cd() {
		return sman_cd;
	}
	public void setSman_cd(String sman_cd) {
		this.sman_cd = sman_cd;
	} 	
	public String getSman_nm() {
		return sman_nm;
	}
	public void setSman_nm(String sman_nm) {
		this.sman_nm = sman_nm;
	}
	public String getSman_info() {
		return sman_info;
	}
	public void setSman_info(String sman_info) {
		this.sman_info = sman_info;
	}
}
