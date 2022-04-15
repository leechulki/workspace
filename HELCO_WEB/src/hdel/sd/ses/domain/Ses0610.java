package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Ses0610 extends CommonDomain {
	
	private String mandt;
	private String sid;
	private String bstdk_e;
	private String wwfq;
	private String zregn;
	private String ort01_e;
	private String stras;
	private String inspdt1;
	private String inspdt2;
	private String inspdt3;
	private String inspdt4;
	private String inspdtn;
	private String kunz2;
	private String kunz2Name;
	private String bdadat1;
	private String bdadat2;
	private String bdddat;
	private String mgnm;
	private String mgtel;
	private String sfprtco;
	private String sbdcm;
	private String dmstat;
	private int filecnt;
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getBstdk_e() {
		return bstdk_e;
	}
	public void setBstdk_e(String bstdk_e) {
		this.bstdk_e = bstdk_e;
	}
	public String getWwfq() {
		return wwfq;
	}
	public void setWwfq(String wwfq) {
		this.wwfq = wwfq;
	}
	public String getZregn() {
		return zregn;
	}
	public void setZregn(String zregn) {
		this.zregn = zregn;
	}
	public String getOrt01_e() {
		return ort01_e;
	}
	public void setOrt01_e(String ort01_e) {
		this.ort01_e = ort01_e;
	}
	public String getStras() {
		return stras;
	}
	public void setStras(String stras) {
		this.stras = stras;
	}
	public String getInspdt1() {
		return inspdt1;
	}
	public void setInspdt1(String inspdt1) {
		this.inspdt1 = inspdt1;
	}
	public String getInspdt2() {
		return inspdt2;
	}
	public void setInspdt2(String inspdt2) {
		this.inspdt2 = inspdt2;
	}
	public String getInspdt3() {
		return inspdt3;
	}
	public void setInspdt3(String inspdt3) {
		this.inspdt3 = inspdt3;
	}
	public String getInspdt4() {
		return inspdt4;
	}
	public void setInspdt4(String inspdt4) {
		this.inspdt4 = inspdt4;
	}
	public String getInspdtn() {
		return inspdtn;
	}
	public void setInspdtn(String inspdtn) {
		this.inspdtn = inspdtn;
	}
	public String getKunz2() {
		return kunz2;
	}
	public void setKunz2(String kunz2) {
		this.kunz2 = kunz2;
	}
	public String getKunz2Name() {
		return kunz2Name;
	}
	public void setKunz2Name(String kunz2Name) {
		this.kunz2Name = kunz2Name;
	}
	
	public String getBdadat1() {
		return bdadat1;
	}
	public void setBdadat1(String bdadat1) {
		if (bdadat1 == null) {
			this.bdadat1 = "00000000";
		}
		else {
			this.bdadat1 = bdadat1;
		}
	}
	public String getBdadat2() {
		return bdadat2;
	}
	public void setBdadat2(String bdadat2) {
		if (bdadat2 == null) {
			this.bdadat2 = "00000000";
		}
		else {
			this.bdadat2 = bdadat2;
		}
	}
	public String getBdddat() {
		return bdddat;
	}
	public void setBdddat(String bdddat) {
		if (bdddat == null) {
			this.bdddat = "00000000";
		}
		else {
			this.bdddat = bdddat;
		}
	}
	public String getMgnm() {
		return mgnm;
	}
	public void setMgnm(String mgnm) {
		if (mgnm == null) {
			this.mgnm = "";
		}
		else {
			this.mgnm = mgnm;
		}
	}
	public String getMgtel() {
		return mgtel;
	}
	public void setMgtel(String mgtel) {
		if (mgtel == null) {
			this.mgtel = "";
		}
		else {
			this.mgtel = mgtel;
		}
	}
	public String getSfprtco() {
		return sfprtco;
	}
	public void setSfprtco(String sfprtco) {
		if (sfprtco == null) {
			this.sfprtco = "";
		}
		else {
			this.sfprtco = sfprtco;
		}
	}
	public String getSbdcm() {
		return sbdcm;
	}
	public void setSbdcm(String sbdcm) {
		if (sbdcm == null) {
			this.sbdcm = "";
		}
		else {
			this.sbdcm = sbdcm;
		}
	}
	public String getDmstat() {
		return dmstat;
	}
	public void setDmstat(String dmstat) {
		this.dmstat = dmstat;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
	public int getFilecnt() {
		return filecnt;
	}
	public void setFilecnt(int filecnt) {
		this.filecnt = filecnt;
	}
}
