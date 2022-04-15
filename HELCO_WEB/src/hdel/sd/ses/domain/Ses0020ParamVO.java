package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0020ParamVO extends ParameterVO {	
	
	private String flag;
	
	private String mclass;
	private String atkla; 	
	private String prh; 	
	private String prd;
	
	private String mandt;
	private String ztplno;
	private int ztplseq;
	private String zrmk;

	
	private String cdate;
	private String ctime;
	private String cuser;
	
	private String udate;
	private String utime;
	private String uuser;
	
	private String zprdgbn;	
	private String ztplgbn;
	private String ztplnm;
	
	private String prhname;
	
	private String cnt;
	
	private String spras;	
	
	// 2020 브랜드
	private String brndcd;
	private String brndseq;
	private String pcncd;
	private String disptp;
	private String moditp;
	
	public String getSpras() {
		return spras;
	}

	public void setSpras(String spras) {
		this.spras = spras;
	}
	
	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getPrhname() {
		return prhname;
	}

	public void setPrhname(String prhname) {
		this.prhname = prhname;
	}
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getMclass() {
		return mclass;
	}

	public void setMclass(String mclass) {
		this.mclass = mclass;
	}

	public String getAtkla() {
		return atkla;
	}

	public void setAtkla(String atkla) {
		this.atkla = atkla;
	}

	public String getPrh() {
		return prh;
	}

	public void setPrh(String prh) {
		this.prh = prh;
	}

	public String getPrd() {
		return prd;
	}

	public void setPrd(String prd) {
		this.prd = prd;
	}

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

	public int getZtplseq() {
		return ztplseq;
	}

	public void setZtplseq(int ztplseq) {
		this.ztplseq = ztplseq;
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

	public String getZprdgbn() {
		return zprdgbn;
	}

	public void setZprdgbn(String zprdgbn) {
		this.zprdgbn = zprdgbn;
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

	
	// 2020 브랜드
	public String getBrndcd() {
		return brndcd;
	}

	public void setBrndcd(String brndcd) {
		this.brndcd = brndcd;
	}

	public String getBrndseq() {
		return brndseq;
	}

	public void setBrndseq(String brndseq) {
		this.brndseq = brndseq;
	}

	public String getPcncd() {
		return pcncd;
	}

	public void setPcncd(String pcncd) {
		this.pcncd = pcncd;
	}

	public String getDisptp() {
		return disptp;
	}

	public void setDisptp(String disptp) {
		this.disptp = disptp;
	}

	public String getModitp() {
		return moditp;
	}

	public void setModitp(String moditp) {
		this.moditp = moditp;
	}

	@Override
	public String toString() {
		return "Ses0020ParamVO [flag=" + flag + ", mclass=" + mclass + ", atkla=" + atkla + ", prh=" + prh + ", prd="
				+ prd + ", mandt=" + mandt + ", ztplno=" + ztplno + ", ztplseq=" + ztplseq + ", zrmk=" + zrmk
				+ ", cdate=" + cdate + ", ctime=" + ctime + ", cuser=" + cuser + ", udate=" + udate + ", utime=" + utime
				+ ", uuser=" + uuser + ", zprdgbn=" + zprdgbn + ", ztplgbn=" + ztplgbn + ", ztplnm=" + ztplnm
				+ ", prhname=" + prhname + ", cnt=" + cnt + ", spras=" + spras + ", brndcd=" + brndcd + ", brndseq="
				+ brndseq + ", pcncd=" + pcncd + ", disptp=" + disptp + ", moditp=" + moditp + "]";
	}
	
}
