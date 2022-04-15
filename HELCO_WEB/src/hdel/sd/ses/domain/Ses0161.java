package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0161 extends CommonDomain {
 
	private String check;
	private String mandt;
	private String qtnum;
	private String qtser;
	private String zrqseq;
	private String zrqcn;
	private String zrsrlt;

	public String USER_ID;  // ÀÛ¾÷ÀÚID
	
	private String sonnr;
	private String cuser;
	
	private String zattseq;
	private String zattpath;
	private String zattfn;
	
	private String uuser;

	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public String getQtser() {
		return qtser;
	}
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}
	public String getZrqseq() {
		return zrqseq;
	}
	public void setZrqseq(String zrqseq) {
		this.zrqseq = zrqseq;
	}
	public String getZrqcn() {
		return zrqcn;
	}
	public void setZrsrlt(String zrsrlt) {
		this.zrsrlt = zrsrlt;
	}
	public String getZrsrlt() {
		return zrsrlt;
	}
	public void setZrqcn(String zrsrlt) {
		this.zrsrlt = zrsrlt;
	}
	
	public void setSonnr(String sonnr) {
		this.sonnr = sonnr;
	}
	public String getSonnr() {
		return sonnr;
	}	
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	public String getCuser() {
		return cuser;
	}		
	
	public void setZattseq(String zattseq) {
		this.zattseq = zattseq;
	}
	public String getZattseq() {
		return zattseq;
	}	
	
	public void setZattpath(String zattpath) {
		this.zattpath = zattpath;
	}
	public String getZattpath() {
		return zattpath;
	}		
	
	public void setZattfn(String zattfn) {
		this.zattfn = zattfn;
	}
	public String getZattfn() {
		return zattfn;
	}	
	
	public void setUuser(String uuser) {
		this.uuser = uuser;
	}
	public String getUuser() {
		return uuser;
	}		
}
