package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0200 extends CommonDomain {
 
	private String mandt;
	private String qtnum;
	private String qtser;
	private String qtseq;
	private String shangh;
	private String matnr;
	private String spec;
	private String gtype;
	private String type1;
	private String zacapa;
	private String type2;
	private String type3;
	private String znumber;
	private String zuse;
	private String zopn;
	private String zlen;
	private String zuam;
	private String zcost;
	private String zeam;
	private String shang;
	private String zrmk;
	private String waerk;
	private String cdate;
	private String ctime;
	private String cuser;
	private String udate;
	private String utime;
	private String uuser;
	private String ddate;
	private String dtime;
	private String duser;
	private String zestdat;
	private String name1;
	private String gsnam;
//----< 2017.01.16 List field 추가 by mj.Shin Start 	
	private String qtdat;
	private String vbeln;
	private String type4;
//----> 2017.01.16 List field 추가 by mj.Shin End
	private String qtgbn;
	private String zprgflg;
	private String zdev;
	private String zgnm;
	private String region; // 2020.07.27 List field 추가 by eunha
	private String dsetup;

	// 승강기번호, 최초 견적일자 추가 21.08.03
	private String eldelvno;
	private String fcdate;
	
	// 부서, 팀 필드 추가 2021.08.24.
	private String vkbur;
	private String vkgrp;
	
	public String getQtgbn() {
		return qtgbn;
	}
	public void setQtgbn(String qtgbn) {
		this.qtgbn = qtgbn;
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
	public String getQtseq() {
		return qtseq;
	}
	public void setQtseq(String qtseq) {
		this.qtseq = qtseq;
	}
	public String getShangh() {
		return shangh;
	}
	public void setShangh(String shangh) {
		this.shangh = shangh;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getGtype() {
		return gtype;
	}
	public void setGtype(String gtype) {
		this.gtype = gtype;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getZacapa() {
		return zacapa;
	}
	public void setZacapa(String zacapa) {
		this.zacapa = zacapa;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getType3() {
		return type3;
	}
	public void setType3(String type3) {
		this.type3 = type3;
	}
	public String getZnumber() {
		return znumber;
	}
	public void setZnumber(String znumber) {
		this.znumber = znumber;
	}
	public String getZuse() {
		return zuse;
	}
	public void setZuse(String zuse) {
		this.zuse = zuse;
	}
	public String getZopn() {
		return zopn;
	}
	public void setZopn(String zopn) {
		this.zopn = zopn;
	}
	public String getZlen() {
		return zlen;
	}
	public void setZlen(String zlen) {
		this.zlen = zlen;
	}
	public String getZuam() {
		return zuam;
	}
	public void setZuam(String zuam) {
		this.zuam = zuam;
	}
	public String getZcost() {
		return zcost;
	}
	public void setZcost(String zcost) {
		this.zcost = zcost;
	}
	public String getZeam() {
		return zeam;
	}
	public void setZeam(String zeam) {
		this.zeam = zeam;
	}
	public String getShang() {
		return shang;
	}
	public void setShang(String shang) {
		this.shang = shang;
	}
	public String getZrmk() {
		return zrmk;
	}
	public void setZrmk(String zrmk) {
		this.zrmk = zrmk;
	}
	public String getWaerk() {
		return waerk;
	}
	public void setWaerk(String waerk) {
		this.waerk = waerk;
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
	public String getDdate() {
		return ddate;
	}
	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	public String getDtime() {
		return dtime;
	}
	public void setDtime(String dtime) {
		this.dtime = dtime;
	}
	public String getDuser() {
		return duser;
	}
	public void setDuser(String duser) {
		this.duser = duser;
	}
	public String getZestdat() {
		return zestdat;
	}
	public void setZestdat(String zestdat) {
		this.zestdat = zestdat;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	
	/**
	 * @return the qtdat
	 */
	public String getQtdat() {
		return qtdat;
	}
	/**
	 * @param qtdat the qtdat to set
	 */
	public void setQtdat(String qtdat) {
		this.qtdat = qtdat;
	}
	/**
	 * @return the vbeln
	 */
	public String getVbeln() {
		return vbeln;
	}
	/**
	 * @param vbeln the vbeln to set
	 */
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	/**
	 * @return the type4
	 */
	public String getType4() {
		return type4;
	}
	/**
	 * @param type4 the type4 to set
	 */
	public void setType4(String type4) {
		this.type4 = type4;
	}
	/**
	 * @return the zprgflg
	 */
	public String getZprgflg() {
		return zprgflg;
	}
	/**
	 * @param zprgflg the zprgflg to set
	 */
	public void setZprgflg(String zprgflg) {
		this.zprgflg = zprgflg;
	}
	public String getZdev() {
		return zdev;
	}
	public void setZdev(String zdev) {
		this.zdev = zdev;
	}
	public String getZgnm() {
		return zgnm;
	}
	public void setZgnm(String zgnm) {
		this.zgnm = zgnm;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDsetup() {
		return dsetup;
	}
	public void setDsetup(String dsetup) {
		this.dsetup = dsetup;
	}

	// 승강기번호, 최초 견적일자 추가 21.08.03
	public String getEldelvno() {
		return eldelvno;
	}
	public void setEldelvno(String eldelvno) {
		this.eldelvno = eldelvno;
	}
	public String getFcdate() {
		return fcdate;
	}
	public void setFcdate(String fcdate) {
		this.fcdate = fcdate;
	}
	
	// 부서, 팀 필드 추가 2021.08.24.
	public String getVkbur() {
		return vkbur;
	}
	
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}
	
	public String getVkgrp() {
		return vkgrp;
	}
	
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}
	
}
