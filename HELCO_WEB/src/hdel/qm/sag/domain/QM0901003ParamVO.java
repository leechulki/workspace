package hdel.qm.sag.domain;

import hdel.lib.domain.ParameterVO;

public class QM0901003ParamVO extends ParameterVO {

	private String mandt;	  	// 
	private String sman_cd;	  	// 담당자코드
	private String sman_nm; 	// 담당자명
	
	public String getMandt() {
		return mandt;
	}
	public String getSman_cd() {
		return sman_cd;
	}
	public String getSman_nm() {
		return sman_nm;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public void setSman_cd(String sman_cd) {
		this.sman_cd = sman_cd;
	}
	public void setSman_nm(String sman_nm) {
		this.sman_nm = sman_nm;
	}
	
}
