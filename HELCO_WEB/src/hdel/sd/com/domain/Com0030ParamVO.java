package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0030ParamVO extends ParameterVO {

	private String mandt;	  	// 
	private String sman_cd;	  	// ������ڵ�
	private String sman_nm; 	// ����ڸ�
	private String sman_filter; // ��������ͱ��� ('':��ü, A:�븮��, H:����)
	private String lang;
	private String pgmid;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	} 
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
	public String getSman_filter() {
		return sman_filter;
	}
	public void setSman_filter(String sman_filter) {
		this.sman_filter = sman_filter;
	} 
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getPgmid() {
		return pgmid;
	}
	public void setPgmid(String pgmid) {
		this.pgmid = pgmid;
	}
}
