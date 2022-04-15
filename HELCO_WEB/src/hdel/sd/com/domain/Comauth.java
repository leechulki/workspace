package hdel.sd.com.domain;

import org.apache.commons.lang.math.NumberUtils;

import hdel.lib.domain.CommonDomain;

public class Comauth extends CommonDomain{

	private static final long serialVersionUID = 1L;
	
	// UI의 Dataset 정의 변수명과 동일하게 정의(대소문자 구분)
	private String mandt;
	private String userid;
	private String pgmid;
	private String pgmnm;
	private String groupid;
	private String userauth;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPgmid() {
		return pgmid;
	}
	public void setPgmid(String pgmid) {
		this.pgmid = pgmid;
	}
	public String getPgmnm() {
		return pgmnm;
	}
	public void setPgmnm(String pgmnm) {
		this.pgmnm = pgmnm;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getUserauth() {
		return userauth;
	}
	public void setUserauth(String userauth) {
		this.userauth = userauth;
	}	
}
