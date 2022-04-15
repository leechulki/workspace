package hdel.qm.sag.domain;

import hdel.lib.domain.CommonDomain;

public class QM0901001 extends CommonDomain {
 
	private String sman_cd;			// 담당자코드
	private String sman_nm; 		// 담당자명
	private String sman_mail; 		// 담당자메일
	
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
	public String getSman_mail() {
		return sman_mail;
	}
	public void setSman_mail(String sman_mail) {
		this.sman_mail = sman_mail;
	}
	
}
