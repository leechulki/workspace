package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0030 extends CommonDomain {
 
	private String mandt;
	private String qtnum;
	private String qtser;
	private String qtdat;
	private String spart;
	private String qtgbn;
	private String zbdtyp;
	private String zprgflg;
	private String vkbur;
	private String vkgrp;
	private String zkunnr;
	private String zagnt;
	private String kunnr;
	private String zcst;
	private String zgnm;
	private String zdev;
	private String gsnam;
	private String zregn;
	private String zpid;
	private String ztel;
	private String zaddr_zpost;
	private String zaddr_adr1;
	private String zaddr_adr2;
	private String soable;
	private String zestdat;
	private String deldat;
	private String zdvmts;
	private String zsum_znumber;
	private String zsum_zcost;
	private String zsum_zeam;
	private String zsum_shang;
	private String inco;
	private String zicif_pprt;
	private String zicif_ppct;
	private String zicif_ppcd;
	private String zicif_1strt;
	private String zicif_1stct;
	private String zicif_1stcd;
	private String zicif_2strt;
	private String zicif_2stct;
	private String zicif_2stcd;
	private String zicif_rmrt;
	private String zicif_rmct;
	private String zicif_rmcd;
	private String sprmk1;
	private String sprmk2;
	private String sprmk3;
	private String sprmk4;
	private String sprmk5;
	private String sprmk6;
	private String sprmk7;
	private String sprmk8;
	private String sprmk9;
	private String sprmk10;
	private String zsoflg;
	private String sonnr;
	private String vbeln;
	private String qtseq;
	private String shangh;
	private String zsarea;
	private String matnr;
	private String zprdgbn;
	private String spec;
	private String gtype;
	private String type1;
	private String type2;
	private String type3;
	private String znumber;
	private String zacapa;
	private String zuse;
	private String zopn;
	private String zlen;
	private String zoutc;
	private String zuam;
	private String zcost;
	private String zcostm;
	private String zeam;
	private String shang;
	private String zrmk;
	private String waerk;
	private String auart;
	private String co_ddvrtq;
	private String co_dephtq;
	private String co_dssq;
	private String co_bclcdtq;
	private String co_chpitq;
	private String co_aspc;
	private String co_dsv1;
	private String co_dsv1tq;
	private String co_dsv2;
	private String co_dsv2tq;
	private String cdate;
	private String ctime;
	private String cuser;
	private String udate;
	private String utime;
	private String uuser;
	private String ddate;
	private String dtime;
	private String duser;
	
	private String zlzone;
	private String region;
	private String buyr_nm;
	private String zkunnr_nm;
	private String zagnt_nm;
	private String zagntflg;
	private String inco2;
	private String part_cnt;
	private String fa_byrgbn;
	private String zdeli;
	private String zuse_nm;
	private String zrtrsn;
	private String lifnrchk;

	private String zattpath;
	private String zattfn;
	private String zattfn_or;
	
	private String tel;		//2012.12.14
	private String fax;		//2012.12.17
	private String cel;		//2012.12.17
	private String zeam_org;   //2012.12.20
	private String mail;
	
	private String land1;		//2013.07.23 
	private String landx;
	private String jgbn;
	private String aqtnum;
	private String oppid;
		
	private String zuams;		//2014.05.21
	private String zurate;
	private String autolp;
	private String uch_duty;
	private String frcmfcdat;

	private String conqty20;
	private String conqty40;

	private String egis_flag;
	private String egis_est_no;
	private String egis_est_seq;
	private String egis_est_serno;
	private String egis_spec_type;
	private String pspid_co;
	private String opendt;
	private String destport;
	private String deptport;
	private String destname;
	
	private String incrtf_tp;

    // 2020 브랜드입력화
	private String brndcd;
	private String brndnm;
	private String brndseq;
	private String sndSys;
	
	private String sid;
	private String mflifnrt;
	private String dmstat;
	private String cslifnrt;	
	private String achdt;
	
	private String qtvadt;
	private String fa_chk1;
	private String fa_chk2;
	
	// 마진율  원가 추가 20.07.01
	private String margin;

	// 프로젝트 원가 추가 20.07.01
	private String zuamp;
	private String zcostp;
	
	//모의견적서 속성 추가 21.006.04
	private String smlqtnum;
	private String smlqtser;
	
	private String delvno;
	
	
	public String getConqty20() {
		return conqty20;
	}
	public void setConqty20(String conqty20) {
		this.conqty20 = conqty20;
	}
	public String getConqty40() {
		return conqty40;
	}
	public void setConqty40(String conqty40) {
		this.conqty40 = conqty40;
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
	public String getQtdat() {
		return qtdat;
	}
	public void setQtdat(String qtdat) {
		this.qtdat = qtdat;
	}
	public String getSpart() {
		return spart;
	}
	public void setSpart(String spart) {
		this.spart = spart;
	}
	public String getQtgbn() {
		return qtgbn;
	}
	public void setQtgbn(String qtgbn) {
		this.qtgbn = qtgbn;
	}
	public String getZbdtyp() {
		return zbdtyp;
	}
	public void setZbdtyp(String zbdtyp) {
		this.zbdtyp = zbdtyp;
	}
	public String getZprgflg() {
		return zprgflg;
	}
	public void setZprgflg(String zprgflg) {
		this.zprgflg = zprgflg;
	}
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
	public String getZkunnr() {
		return zkunnr;
	}
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
	public String getZagnt() {
		return zagnt;
	}
	public void setZagnt(String zagnt) {
		this.zagnt = zagnt;
	}
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	public String getZcst() {
		return zcst;
	}
	public void setZcst(String zcst) {
		this.zcst = zcst;
	}
	public String getZgnm() {
		return zgnm;
	}
	public void setZgnm(String zgnm) {
		this.zgnm = zgnm;
	}
	public String getZdev() {
		return zdev;
	}
	public void setZdev(String zdev) {
		this.zdev = zdev;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	public String getZregn() {
		return zregn;
	}
	public void setZregn(String zregn) {
		this.zregn = zregn;
	}
	public String getZpid() {
		return zpid;
	}
	public void setZpid(String zpid) {
		this.zpid = zpid;
	}
	public String getZtel() {
		return ztel;
	}
	public void setZtel(String ztel) {
		this.ztel = ztel;
	}
	public String getZaddr_zpost() {
		return zaddr_zpost;
	}
	public void setZaddr_zpost(String zaddr_zpost) {
		this.zaddr_zpost = zaddr_zpost;
	}
	public String getZaddr_adr1() {
		return zaddr_adr1;
	}
	public void setZaddr_adr1(String zaddr_adr1) {
		this.zaddr_adr1 = zaddr_adr1;
	}
	public String getZaddr_adr2() {
		return zaddr_adr2;
	}
	public void setZaddr_adr2(String zaddr_adr2) {
		this.zaddr_adr2 = zaddr_adr2;
	}
	public String getSoable() {
		return soable;
	}
	public void setSoable(String soable) {
		this.soable = soable;
	}
	public String getZestdat() {
		return zestdat;
	}
	public void setZestdat(String zestdat) {
		this.zestdat = zestdat;
	}
	public String getDeldat() {
		return deldat;
	}
	public void setDeldat(String deldat) {
		this.deldat = deldat;
	}
	public String getZdvmts() {
		return zdvmts;
	}
	public void setZdvmts(String zdvmts) {
		this.zdvmts = zdvmts;
	}
	public String getZsum_znumber() {
		return zsum_znumber;
	}
	public void setZsum_znumber(String zsum_znumber) {
		this.zsum_znumber = zsum_znumber;
	}
	public String getZsum_zcost() {
		return zsum_zcost;
	}
	public void setZsum_zcost(String zsum_zcost) {
		this.zsum_zcost = zsum_zcost;
	}
	public String getZcostm() {
		return zcostm;
	}
	public void setZcostm(String zcostm) {
		this.zcostm = zcostm;
	}
	public String getZsum_zeam() {
		return zsum_zeam;
	}
	public void setZsum_zeam(String zsum_zeam) {
		this.zsum_zeam = zsum_zeam;
	}
	public String getZsum_shang() {
		return zsum_shang;
	}
	public void setZsum_shang(String zsum_shang) {
		this.zsum_shang = zsum_shang;
	}
	public String getInco() {
		return inco;
	}
	public void setInco(String inco) {
		this.inco = inco;
	}
	public String getZicif_pprt() {
		return zicif_pprt;
	}
	public void setZicif_pprt(String zicif_pprt) {
		this.zicif_pprt = zicif_pprt;
	}
	public String getZicif_ppct() {
		return zicif_ppct;
	}
	public void setZicif_ppct(String zicif_ppct) {
		this.zicif_ppct = zicif_ppct;
	}
	public String getZicif_ppcd() {
		return zicif_ppcd;
	}
	public void setZicif_ppcd(String zicif_ppcd) {
		this.zicif_ppcd = zicif_ppcd;
	}
	public String getZicif_1strt() {
		return zicif_1strt;
	}
	public void setZicif_1strt(String zicif_1strt) {
		this.zicif_1strt = zicif_1strt;
	}
	public String getZicif_1stct() {
		return zicif_1stct;
	}
	public void setZicif_1stct(String zicif_1stct) {
		this.zicif_1stct = zicif_1stct;
	}
	public String getZicif_1stcd() {
		return zicif_1stcd;
	}
	public void setZicif_1stcd(String zicif_1stcd) {
		this.zicif_1stcd = zicif_1stcd;
	}
	public String getZicif_2strt() {
		return zicif_2strt;
	}
	public void setZicif_2strt(String zicif_2strt) {
		this.zicif_2strt = zicif_2strt;
	}
	public String getZicif_2stct() {
		return zicif_2stct;
	}
	public void setZicif_2stct(String zicif_2stct) {
		this.zicif_2stct = zicif_2stct;
	}
	public String getZicif_2stcd() {
		return zicif_2stcd;
	}
	public void setZicif_2stcd(String zicif_2stcd) {
		this.zicif_2stcd = zicif_2stcd;
	}
	public String getZicif_rmrt() {
		return zicif_rmrt;
	}
	public void setZicif_rmrt(String zicif_rmrt) {
		this.zicif_rmrt = zicif_rmrt;
	}
	public String getZicif_rmct() {
		return zicif_rmct;
	}
	public void setZicif_rmct(String zicif_rmct) {
		this.zicif_rmct = zicif_rmct;
	}
	public String getZicif_rmcd() {
		return zicif_rmcd;
	}
	public void setZicif_rmcd(String zicif_rmcd) {
		this.zicif_rmcd = zicif_rmcd;
	}
	public String getSprmk1() {
		return sprmk1;
	}
	public void setSprmk1(String sprmk1) {
		this.sprmk1 = sprmk1;
	}
	public String getSprmk2() {
		return sprmk2;
	}
	public void setSprmk2(String sprmk2) {
		this.sprmk2 = sprmk2;
	}
	public String getSprmk3() {
		return sprmk3;
	}
	public void setSprmk3(String sprmk3) {
		this.sprmk3 = sprmk3;
	}
	public String getSprmk4() {
		return sprmk4;
	}
	public void setSprmk4(String sprmk4) {
		this.sprmk4 = sprmk4;
	}
	public String getSprmk5() {
		return sprmk5;
	}
	public void setSprmk5(String sprmk5) {
		this.sprmk5 = sprmk5;
	}
	public String getSprmk6() {
		return sprmk6;
	}
	public void setSprmk6(String sprmk6) {
		this.sprmk6 = sprmk6;
	}
	public String getSprmk7() {
		return sprmk7;
	}
	public void setSprmk7(String sprmk7) {
		this.sprmk7 = sprmk7;
	}
	public String getSprmk8() {
		return sprmk8;
	}
	public void setSprmk8(String sprmk8) {
		this.sprmk8 = sprmk8;
	}
	public String getSprmk9() {
		return sprmk9;
	}
	public void setSprmk9(String sprmk9) {
		this.sprmk9 = sprmk9;
	}
	public String getSprmk10() {
		return sprmk10;
	}
	public void setSprmk10(String sprmk10) {
		this.sprmk10 = sprmk10;
	}
	public String getZsoflg() {
		return zsoflg;
	}
	public void setZsoflg(String zsoflg) {
		this.zsoflg = zsoflg;
	}
	public String getSonnr() {
		return sonnr;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public void setSonnr(String sonnr) {
		this.sonnr = sonnr;
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
	public String getZsarea() {
		return zsarea;
	}
	public void setZsarea(String zsarea) {
		this.zsarea = zsarea;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getZprdgbn() {
		return zprdgbn;
	}
	public void setZprdgbn(String zprdgbn) {
		this.zprdgbn = zprdgbn;
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
	public String getZacapa() {
		return zacapa;
	}
	public void setZacapa(String zacapa) {
		this.zacapa = zacapa;
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
	public String getZoutc() {
		return zoutc;
	}
	public void setZoutc(String zoutc) {
		this.zoutc = zoutc;
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
	public String getAuart() {
		return auart;
	}
	public void setAuart(String auart) {
		this.auart = auart;
	}
	
	public String getCo_ddvrtq() {
		return co_ddvrtq;
	}
	public void setCo_ddvrtq(String co_ddvrtq) {
		this.co_ddvrtq = co_ddvrtq;
	}
	public String getCo_dephtq() {
		return co_dephtq;
	}
	public void setCo_dephtq(String co_dephtq) {
		this.co_dephtq = co_dephtq;
	}
	public String getCo_dssq() {
		return co_dssq;
	}
	public void setCo_dssq(String co_dssq) {
		this.co_dssq = co_dssq;
	}
	public String getCo_bclcdtq() {
		return co_bclcdtq;
	}
	public void setCo_bclcdtq(String co_bclcdtq) {
		this.co_bclcdtq = co_bclcdtq;
	}
	public String getCo_chpitq() {
		return co_chpitq;
	}
	public void setCo_chpitq(String co_chpitq) {
		this.co_chpitq = co_chpitq;
	}
	public String getCo_aspc() {
		return co_aspc;
	}
	public void setCo_aspc(String co_aspc) {
		this.co_aspc = co_aspc;
	}
	public String getCo_dsv1() {
		return co_dsv1;
	}
	public void setCo_dsv1(String co_dsv1) {
		this.co_dsv1 = co_dsv1;
	}
	public String getCo_dsv1tq() {
		return co_dsv1tq;
	}
	public void setCo_dsv1tq(String co_dsv1tq) {
		this.co_dsv1tq = co_dsv1tq;
	}
	public String getCo_dsv2() {
		return co_dsv2;
	}
	public void setCo_dsv2(String co_dsv2) {
		this.co_dsv2 = co_dsv2;
	}
	public String getCo_dsv2tq() {
		return co_dsv2tq;
	}
	public void setCo_dsv2tq(String co_dsv2tq) {
		this.co_dsv2tq = co_dsv2tq;
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
	public String getZlzone() {
		return zlzone;
	}
	public void setZlzone(String zlzone) {
		this.zlzone = zlzone;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBuyr_nm() {
		return buyr_nm;
	}
	public void setBuyr_nm(String buyr_nm) {
		this.buyr_nm = buyr_nm;
	}
	public String getZkunnr_nm() {
		return zkunnr_nm;
	}
	public void setZkunnr_nm(String zkunnr_nm) {
		this.zkunnr_nm = zkunnr_nm;
	}
	public String getZagnt_nm() {
		return zagnt_nm;
	}
	public void setZagnt_nm(String zagnt_nm) {
		this.zagnt_nm = zagnt_nm;
	}
	public String getZagntflg() {
		return zagntflg;
	}
	public void setZagntflg(String zagntflg) {
		this.zagntflg = zagntflg;
	}
	public String getInco2() {
		return inco2;
	}
	public void setInco2(String inco2) {
		this.inco2 = inco2;
	}
	public String getPart_cnt() {
		return part_cnt;
	}
	public void setPart_cnt(String part_cnt) {
		this.part_cnt = part_cnt;
	}
	public String getZdeli() {
		return zdeli;
	}
	public void setZdeli(String zdeli) {
		this.zdeli = zdeli;
	}
	/**
	 * @return the zattpath
	 */
	public String getZattpath() {
		return zattpath;
	}
	/**
	 * @param zattpath the zattpath to set
	 */
	public void setZattpath(String zattpath) {
		this.zattpath = zattpath;
	}
	/**
	 * @return the zattfn
	 */
	public String getZattfn() {
		return zattfn;
	}
	/**
	 * @param zattfn the zattfn to set
	 */
	public void setZattfn(String zattfn) {
		this.zattfn = zattfn;
	}
	/**
	 * @return the zattfn_or
	 */
	public String getZattfn_or() {
		return zattfn_or;
	}
	/**
	 * @param zattfn_or the zattfn_or to set
	 */
	public void setZattfn_or(String zattfn_or) {
		this.zattfn_or = zattfn_or;
	}
	public String getFa_byrgbn() {
		return fa_byrgbn;
	}
	public void setFa_byrgbn(String fa_byrgbn) {
		this.fa_byrgbn = fa_byrgbn;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	public String getZeam_org() {
		return zeam_org;
	}
	public void setZeam_org(String zeam_org) {
		this.zeam_org = zeam_org;
	}
	public String getZuse_nm() {
		return zuse_nm;
	}
	public void setZuse_nm(String zuse_nm) {
		this.zuse_nm = zuse_nm;
	}
	public String getZrtrsn() {
		return zrtrsn;
	}
	public void setZrtrsn(String zrtrsn) {
		this.zrtrsn = zrtrsn;
	}
	public String getLifnrchk() {
		return lifnrchk;
	}
	public void setLifnrchk(String lifnrchk) {
		this.lifnrchk = lifnrchk;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the land1
	 */
	public String getLand1() {
		return land1;
	}
	/**
	 * @param land1 the land1 to set
	 */
	public void setLand1(String land1) {
		this.land1 = land1;
	}
	/**
	 * @return the landx
	 */
	public String getLandx() {
		return landx;
	}
	/**
	 * @param landx the landx to set
	 */
	public void setLandx(String landx) {
		this.landx = landx;
	}	
	/**
	 * @return the jgbn
	 */
	public String getJgbn() {
		return jgbn;
	}
	/**
	 * @param jgbn the jgbn to set
	 */
	public void setJgbn(String jgbn) {
		this.jgbn = jgbn;
	}
	/**
	 * @return the aqtnum
	 */
	public String getAqtnum() {
		return aqtnum;
	}
	/**
	 * @param aqtnum the aqtnum to set
	 */
	public void setAqtnum(String aqtnum) {
		this.aqtnum = aqtnum;
	}
	/**
	 * @return the oppid
	 */
	public String getOppid() {
		return oppid;
	}
	/**
	 * @param oppid the oppid to set
	 */
	public void setOppid(String oppid) {
		this.oppid = oppid;
	}
	
	/**
	 * @return the zuams
	 */
	public String getZuams() {
		return zuams;
	}
	/**
	 * @param zuams the zuams to set
	 */
	public void setZuams(String zuams) {
		this.zuams = zuams;
	}
	
	/**
	 * @return the zurate
	 */
	public String getZurate() {
		return zurate;
	}
	/**
	 * @param zurate the zurate to set
	 */
	public void setZurate(String zurate) {
		this.zurate = zurate;
	}

	/**
	 * @return the autolp
	 */

	public String getAutolp() {
		return autolp;
	}
	/**
	 * @param autolp the autolp to set
	 */

	public void setAutolp(String autolp) {
		this.autolp = autolp;
	}

	/**
	 * @return the autolp
	 */

	public String getUch_duty() {
		return uch_duty;
	}
	/**
	 * @param autolp the autolp to set
	 */

	public void setUch_duty(String uch_duty) {
		this.uch_duty = uch_duty;
	}
	/**
	 * @return the frcmfcdat
	 */
	public String getFrcmfcdat() {
		return frcmfcdat;
	}
	/**
	 * @param frcmfcdat the frcmfcdat to set
	 */
	public void setFrcmfcdat(String frcmfcdat) {
		this.frcmfcdat = frcmfcdat;
	}
	public String getEgis_flag() {
		return egis_flag;
	}
	public void setEgis_flag(String egis_flag) {
		this.egis_flag = egis_flag;
	}

	public String getEgis_est_no() {
		return egis_est_no;
	}
	public void setEgis_est_no(String egis_est_no) {
		this.egis_est_no = egis_est_no;
	}
	public String getEgis_est_seq() {
		return egis_est_seq;
	}
	public void setEgis_est_seq(String egis_est_seq) {
		this.egis_est_seq = egis_est_seq;
	}
	public String getEgis_est_serno() {
		return egis_est_serno;
	}
	public void setEgis_est_serno(String egis_est_serno) {
		this.egis_est_serno = egis_est_serno;
	}
	public String getPspid_co() {
		return pspid_co;
	}
	public void setPspid_co(String pspid_co) {
		this.pspid_co = pspid_co;
	}
	public String getOpendt() {
		return opendt;
	}
	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}
	public String getDestport() {
		return destport;
	}
	public void setDestport(String destport) {
		this.destport = destport;
	}
	public String getDeptport() {
		return deptport;
	}
	public void setDeptport(String deptport) {
		this.deptport = deptport;
	}
	public String getDestname() {
		return destname;
	}
	public void setDestname(String destname) {
		this.destname = destname;
	}
	public String getEgis_spec_type() {
		return egis_spec_type;
	}
	public void setEgis_spec_type(String egis_spec_type) {
		this.egis_spec_type = egis_spec_type;
	}
	public String getIncrtf_tp() {
		return incrtf_tp;
	}
	public void setIncrtf_tp(String incrtf_tp) {
		this.incrtf_tp = incrtf_tp;
	}
	
	//2020 브랜드입력화
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
	public String getBrndseq() {
		return brndseq;
	}
	public void setBrndseq(String brndseq) {
		this.brndseq = brndseq;
	}
	public String getSndSys() {
		return sndSys;
	}
	public void setSndSys(String sndSys) {
		this.sndSys = sndSys;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMflifnrt() {
		return mflifnrt;
	}
	public void setMflifnrt(String mflifnrt) {
		this.mflifnrt = mflifnrt;
	}
	public String getDmstat() {
		return dmstat;
	}
	public void setDmstat(String dmstat) {
		this.dmstat = dmstat;
	}

	public String getCslifnrt() {
		return cslifnrt;
	}
	public void setCslifnrt(String cslifnrt) {
		this.cslifnrt = cslifnrt;
	}

	/**
	 * @return the achdt
	 */
	public String getAchdt() {
		return achdt;
	}
	/**
	 * @param achdt the achdt to set
	 */
	public void setAchdt(String achdt) {
		this.achdt = achdt;
	}
	
	public String getQtvadt() {
		return qtvadt;
	}
	public void setQtvadt(String qtvadt) {
		this.qtvadt = qtvadt;
	}
	public String getFa_chk1() {
		return fa_chk1;
	}
	public void setFa_chk1(String fa_chk1) {
		this.fa_chk1 = fa_chk1;
	}
	public String getFa_chk2() {
		return fa_chk2;
	}
	public void setFa_chk2(String fa_chk2) {
		this.fa_chk2 = fa_chk2;
	}

	// 마진율  21.07.01 추가
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}

	// 프로젝트 원가 21.07.01 추가
	public String getZuamp() {
		return zuamp;
	}
	public void setZuamp(String zuamp) {
		this.zuamp = zuamp;
	}

	public String getZcostp() {
		return zcostp;
	}
	public void setZcostp(String zcostp) {
		this.zcostp = zcostp;
	}
	
	//모의견적서 속성 추가 21.006.04
	public String getSmlqtnum() {
		return smlqtnum;
	}
	public void setSmlqtnum(String smlqtnum) {
		this.smlqtnum = smlqtnum;
	}
	public String getSmlqtser() {
		return smlqtser;
	}
	public void setSmlqtser(String smlqtser) {
		this.smlqtser = smlqtser;
	}
	
	public String getDelvno() {
		return delvno;
	}
	public void setDelvno(String delvno) {
		this.delvno = delvno;
	}
}