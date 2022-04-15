package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0010 extends CommonDomain {
 
	private String mandt;
	private String ztplno;
	private String ztplgbn;
	private String ztplnm;
	private String zprdgbn;
	private String zwriter;
	private String zrmk;
	private String cdate;
	private String ctime;
	private String cuser;
	private String udate;
	private String utime;
	private String uuser;
	// 2020 브랜드 입력
	private String brndcd;
	private String brndnm;

	public String USER_ID;  // 작업자ID

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZtplno() {
		return ztplno;
	}
	public void setZtplno(String ztplno) {
		this.ztplno = ztplno;
	}
	public String getZtplgbn() {
		return ztplgbn;
	}
	public void setZtplgbn(String ztplgbn) {
		this.ztplgbn = ztplgbn;
	}
	public String getZtplnm() {
		return ztplnm;
	}
	public void setZtplnm(String ztplnm) {
		this.ztplnm = ztplnm;
	}
	public String getZprdgbn() {
		return zprdgbn;
	}
	public void setZprdgbn(String zprdgbn) {
		this.zprdgbn = zprdgbn;
	}
	public String getZwriter() {
		return zwriter;
	}
	public void setZwriter(String zwriter) {
		this.zwriter = zwriter;
	}
	public String getZrmk() {
		return zrmk;
	}
	public void setZrmk(String zrmk) {
		this.zrmk = zrmk;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getCuser() {
		return cuser;
	}
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	public String getUdate() {
		return udate;
	}
	public void setUdate(String udate) {
		this.udate = udate;
	}
	public String getUtime() {
		return utime;
	}
	public void setUtime(String utime) {
		this.utime = utime;
	}
	public String getUuser() {
		return uuser;
	}
	public void setUuser(String uuser) {
		this.uuser = uuser;
	}
	public String getUSER_ID(){
		return USER_ID;
	}
	public void setUSER_ID(String USER_ID) {
		this.USER_ID=USER_ID;
	}
	// 2020 브랜드 입력
	public String getBrndcd() {
		return brndcd;
	}
	public void setBrndcd(String brndcd) {
		this.brndcd = brndcd;
	}
	public String getBrndnm() {
		return brndnm;
	}
	public void setBrndnm(String brndnm) {
		this.brndnm = brndnm;
	}
}
