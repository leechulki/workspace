package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

@SuppressWarnings("serial")
public class Ses0031ParamVO extends ParameterVO {

	private String flag;
	private String mandt;
	private String qtnum;
	private String qtser;
	private String max_qtser;
	private String qtseq;
	private String shangh;
	private String zsarea;
	private String matnr;
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
    private String spart;

	private String qtdat;
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

	private String cuser;
	private String uuser;
	private String duser;

	private String mclass;
	private String atkla;

	private String prseq;
	private String prh;
	private String prd;
	private String etch;
	private String etcm;
	private String ztplgbn;
	private String zprdgbn;
	private String ztplno;
	private String spras;

	private String waerk;

	private String pspid;
	private String posid;
	private String seq;
	private String finl;
	private String atype;
	private String gubun;
	private String seria;
	private String blseq;
	private String blnum;
	private String blnam;
	private String zitem;
	private String zchar;
	private String zquty;
	private String zamnt;
	private String ztotl;
	private String bigo;
	private String tex1;
	private String tex2;
	private String state;

	private String addt01;
	private String messg;

	private String sorlt;
	
	private String zlzone;
	private String region;
	private String zagntflg;
	private String inco2;

	private String frqtdat;
	private String toqtdat;

	private String atinn;
	private String lang;

	private String zyear;
	private String zmonth;
	private String fa_byrgbn;
	private String zdeli;
	private String f_flag;
	private String sdate;
	private String jgbn;
	private String aqtnum;
	private String oppid;
	private String autolp;
	private String uch_duty;
	private String frcmfcdat;

	/* 상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청 */
	private String zuams;
	private String zurate;
	
	private String fpath;
	private String fname;

	//E-GIS 송수신 저장 20190329 BY LYK
	private String egisFlag="";
	private String egisEstNo="";
	private String egisEstSeq="0";
	private String egisEstSerno="0";
	private String egisSpecType="";
	private String pspidCo="";
	private String deptport="";
	private String destport="";
	private String ratio="";
	
	// 계약형태 추가 - 김은하 2020.07.10
	private String lifnrchk;
	
	// 2020 브랜드
	private String brndcd;		
	private String brndseq;
	
	// 건축허가일 - mj.Shin 2020.07.10
	private String achdt;
	
	// 프로젝트 원가 추가 20.07.01
	private String zuamp;
	private String zcostp;
	
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the mandt
	 */
	public String getMandt() {
		return mandt;
	}
	/**
	 * @param mandt the mandt to set
	 */
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	/**
	 * @return the qtnum
	 */
	public String getQtnum() {
		return qtnum;
	}
	/**
	 * @param qtnum the qtnum to set
	 */
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	/**
	 * @return the qtser
	 */
	public String getQtser() {
		return qtser;
	}
	/**
	 * @param qtser the qtser to set
	 */
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}
	/**
	 * @return the qtseq
	 */
	public String getQtseq() {
		return qtseq;
	}
	/**
	 * @param qtseq the qtseq to set
	 */
	public void setQtseq(String qtseq) {
		this.qtseq = qtseq;
	}
	/**
	 * @return the shangh
	 */
	public String getShangh() {
		return shangh;
	}
	/**
	 * @param shangh the shangh to set
	 */
	public void setShangh(String shangh) {
		this.shangh = shangh;
	}
	/**
	 * @return the zsarea
	 */
	public String getZsarea() {
		return zsarea;
	}
	/**
	 * @param zsarea the zsarea to set
	 */
	public void setZsarea(String zsarea) {
		this.zsarea = zsarea;
	}
	/**
	 * @return the matnr
	 */
	public String getMatnr() {
		return matnr;
	}
	/**
	 * @param matnr the matnr to set
	 */
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * @return the gtype
	 */
	public String getGtype() {
		return gtype;
	}
	/**
	 * @param gtype the gtype to set
	 */
	public void setGtype(String gtype) {
		this.gtype = gtype;
	}
	/**
	 * @return the type1
	 */
	public String getType1() {
		return type1;
	}
	/**
	 * @param type1 the type1 to set
	 */
	public void setType1(String type1) {
		this.type1 = type1;
	}
	/**
	 * @return the type2
	 */
	public String getType2() {
		return type2;
	}
	/**
	 * @param type2 the type2 to set
	 */
	public void setType2(String type2) {
		this.type2 = type2;
	}
	/**
	 * @return the type3
	 */
	public String getType3() {
		return type3;
	}
	/**
	 * @param type3 the type3 to set
	 */
	public void setType3(String type3) {
		this.type3 = type3;
	}
	/**
	 * @return the znumber
	 */
	public String getZnumber() {
		return znumber;
	}
	/**
	 * @param znumber the znumber to set
	 */
	public void setZnumber(String znumber) {
		this.znumber = znumber;
	}
	/**
	 * @return the zacapa
	 */
	public String getZacapa() {
		return zacapa;
	}
	/**
	 * @param zacapa the zacapa to set
	 */
	public void setZacapa(String zacapa) {
		this.zacapa = zacapa;
	}
	/**
	 * @return the zuse
	 */
	public String getZuse() {
		return zuse;
	}
	/**
	 * @param zuse the zuse to set
	 */
	public void setZuse(String zuse) {
		this.zuse = zuse;
	}
	/**
	 * @return the zopn
	 */
	public String getZopn() {
		return zopn;
	}
	/**
	 * @param zopn the zopn to set
	 */
	public void setZopn(String zopn) {
		this.zopn = zopn;
	}
	/**
	 * @return the zlen
	 */
	public String getZlen() {
		return zlen;
	}
	/**
	 * @param zlen the zlen to set
	 */
	public void setZlen(String zlen) {
		this.zlen = zlen;
	}
	/**
	 * @return the zoutc
	 */
	public String getZoutc() {
		return zoutc;
	}
	/**
	 * @param zoutc the zoutc to set
	 */
	public void setZoutc(String zoutc) {
		this.zoutc = zoutc;
	}
	/**
	 * @return the zuam
	 */
	public String getZuam() {
		return zuam;
	}
	/**
	 * @param zuam the zuam to set
	 */
	public void setZuam(String zuam) {
		this.zuam = zuam;
	}
	/**
	 * @return the zcost
	 */
	public String getZcost() {
		return zcost;
	}
	/**
	 * @param zcost the zcost to set
	 */
	public void setZcost(String zcost) {
		this.zcost = zcost;
	}
	/**
	 * @return the zcostm
	 */
	public String getZcostm() {
		return zcostm;
	}
	/**
	 * @param zcostm the zcostm to set
	 */
	public void setZcostm(String zcostm) {
		this.zcostm = zcostm;
	}
	/**
	 * @return the zeam
	 */
	public String getZeam() {
		return zeam;
	}
	/**
	 * @param zeam the zeam to set
	 */
	public void setZeam(String zeam) {
		this.zeam = zeam;
	}
	/**
	 * @return the shang
	 */
	public String getShang() {
		return shang;
	}
	/**
	 * @param shang the shang to set
	 */
	public void setShang(String shang) {
		this.shang = shang;
	}
	/**
	 * @return the zrmk
	 */
	public String getZrmk() {
		return zrmk;
	}
	/**
	 * @param zrmk the zrmk to set
	 */
	public void setZrmk(String zrmk) {
		this.zrmk = zrmk;
	}
	/**
	 * @return the spart
	 */
	public String getSpart() {
		return spart;
	}
	/**
	 * @param spart the spart to set
	 */
	public void setSpart(String spart) {
		this.spart = spart;
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
	 * @return the qtgbn
	 */
	public String getQtgbn() {
		return qtgbn;
	}
	/**
	 * @param qtgbn the qtgbn to set
	 */
	public void setQtgbn(String qtgbn) {
		this.qtgbn = qtgbn;
	}
	/**
	 * @return the zbdtyp
	 */
	public String getZbdtyp() {
		return zbdtyp;
	}
	/**
	 * @param zbdtyp the zbdtyp to set
	 */
	public void setZbdtyp(String zbdtyp) {
		this.zbdtyp = zbdtyp;
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
	/**
	 * @return the vkbur
	 */
	public String getVkbur() {
		return vkbur;
	}
	/**
	 * @param vkbur the vkbur to set
	 */
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}
	/**
	 * @return the vkgrp
	 */
	public String getVkgrp() {
		return vkgrp;
	}
	/**
	 * @param vkgrp the vkgrp to set
	 */
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}
	/**
	 * @return the zkunnr
	 */
	public String getZkunnr() {
		return zkunnr;
	}
	/**
	 * @param zkunnr the zkunnr to set
	 */
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
	/**
	 * @return the zagnt
	 */
	public String getZagnt() {
		return zagnt;
	}
	/**
	 * @param zagnt the zagnt to set
	 */
	public void setZagnt(String zagnt) {
		this.zagnt = zagnt;
	}
	/**
	 * @return the kunnr
	 */
	public String getKunnr() {
		return kunnr;
	}
	/**
	 * @param kunnr the kunnr to set
	 */
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	/**
	 * @return the zcst
	 */
	public String getZcst() {
		return zcst;
	}
	/**
	 * @param zcst the zcst to set
	 */
	public void setZcst(String zcst) {
		this.zcst = zcst;
	}
	/**
	 * @return the zgnm
	 */
	public String getZgnm() {
		return zgnm;
	}
	/**
	 * @param zgnm the zgnm to set
	 */
	public void setZgnm(String zgnm) {
		this.zgnm = zgnm;
	}
	/**
	 * @return the zdev
	 */
	public String getZdev() {
		return zdev;
	}
	/**
	 * @param zdev the zdev to set
	 */
	public void setZdev(String zdev) {
		this.zdev = zdev;
	}
	/**
	 * @return the gsnam
	 */
	public String getGsnam() {
		return gsnam;
	}
	/**
	 * @param gsnam the gsnam to set
	 */
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	/**
	 * @return the zregn
	 */
	public String getZregn() {
		return zregn;
	}
	/**
	 * @param zregn the zregn to set
	 */
	public void setZregn(String zregn) {
		this.zregn = zregn;
	}
	/**
	 * @return the zpid
	 */
	public String getZpid() {
		return zpid;
	}
	/**
	 * @param zpid the zpid to set
	 */
	public void setZpid(String zpid) {
		this.zpid = zpid;
	}
	/**
	 * @return the ztel
	 */
	public String getZtel() {
		return ztel;
	}
	/**
	 * @param ztel the ztel to set
	 */
	public void setZtel(String ztel) {
		this.ztel = ztel;
	}
	/**
	 * @return the zaddr_zpost
	 */
	public String getZaddr_zpost() {
		return zaddr_zpost;
	}
	/**
	 * @param zaddr_zpost the zaddr_zpost to set
	 */
	public void setZaddr_zpost(String zaddr_zpost) {
		this.zaddr_zpost = zaddr_zpost;
	}
	/**
	 * @return the zaddr_adr1
	 */
	public String getZaddr_adr1() {
		return zaddr_adr1;
	}
	/**
	 * @param zaddr_adr1 the zaddr_adr1 to set
	 */
	public void setZaddr_adr1(String zaddr_adr1) {
		this.zaddr_adr1 = zaddr_adr1;
	}
	/**
	 * @return the zaddr_adr2
	 */
	public String getZaddr_adr2() {
		return zaddr_adr2;
	}
	/**
	 * @param zaddr_adr2 the zaddr_adr2 to set
	 */
	public void setZaddr_adr2(String zaddr_adr2) {
		this.zaddr_adr2 = zaddr_adr2;
	}
	/**
	 * @return the soable
	 */
	public String getSoable() {
		return soable;
	}
	/**
	 * @param soable the soable to set
	 */
	public void setSoable(String soable) {
		this.soable = soable;
	}
	/**
	 * @return the zestdat
	 */
	public String getZestdat() {
		return zestdat;
	}
	/**
	 * @param zestdat the zestdat to set
	 */
	public void setZestdat(String zestdat) {
		this.zestdat = zestdat;
	}
	/**
	 * @return the deldat
	 */
	public String getDeldat() {
		return deldat;
	}
	/**
	 * @param deldat the deldat to set
	 */
	public void setDeldat(String deldat) {
		this.deldat = deldat;
	}
	/**
	 * @return the zdvmts
	 */
	public String getZdvmts() {
		return zdvmts;
	}
	/**
	 * @param zdvmts the zdvmts to set
	 */
	public void setZdvmts(String zdvmts) {
		this.zdvmts = zdvmts;
	}
	/**
	 * @return the zsum_znumber
	 */
	public String getZsum_znumber() {
		return zsum_znumber;
	}
	/**
	 * @param zsum_znumber the zsum_znumber to set
	 */
	public void setZsum_znumber(String zsum_znumber) {
		this.zsum_znumber = zsum_znumber;
	}
	/**
	 * @return the zsum_zcost
	 */
	public String getZsum_zcost() {
		return zsum_zcost;
	}
	/**
	 * @param zsum_zcost the zsum_zcost to set
	 */
	public void setZsum_zcost(String zsum_zcost) {
		this.zsum_zcost = zsum_zcost;
	}
	/**
	 * @return the zsum_zeam
	 */
	public String getZsum_zeam() {
		return zsum_zeam;
	}
	/**
	 * @param zsum_zeam the zsum_zeam to set
	 */
	public void setZsum_zeam(String zsum_zeam) {
		this.zsum_zeam = zsum_zeam;
	}
	/**
	 * @return the zsum_shang
	 */
	public String getZsum_shang() {
		return zsum_shang;
	}
	/**
	 * @param zsum_shang the zsum_shang to set
	 */
	public void setZsum_shang(String zsum_shang) {
		this.zsum_shang = zsum_shang;
	}
	/**
	 * @return the inco
	 */
	public String getInco() {
		return inco;
	}
	/**
	 * @param inco the inco to set
	 */
	public void setInco(String inco) {
		this.inco = inco;
	}
	/**
	 * @return the zicif_pprt
	 */
	public String getZicif_pprt() {
		return zicif_pprt;
	}
	/**
	 * @param zicif_pprt the zicif_pprt to set
	 */
	public void setZicif_pprt(String zicif_pprt) {
		this.zicif_pprt = zicif_pprt;
	}
	/**
	 * @return the zicif_ppct
	 */
	public String getZicif_ppct() {
		return zicif_ppct;
	}
	/**
	 * @param zicif_ppct the zicif_ppct to set
	 */
	public void setZicif_ppct(String zicif_ppct) {
		this.zicif_ppct = zicif_ppct;
	}
	/**
	 * @return the zicif_ppcd
	 */
	public String getZicif_ppcd() {
		return zicif_ppcd;
	}
	/**
	 * @param zicif_ppcd the zicif_ppcd to set
	 */
	public void setZicif_ppcd(String zicif_ppcd) {
		this.zicif_ppcd = zicif_ppcd;
	}
	/**
	 * @return the zicif_1strt
	 */
	public String getZicif_1strt() {
		return zicif_1strt;
	}
	/**
	 * @param zicif_1strt the zicif_1strt to set
	 */
	public void setZicif_1strt(String zicif_1strt) {
		this.zicif_1strt = zicif_1strt;
	}
	/**
	 * @return the zicif_1stct
	 */
	public String getZicif_1stct() {
		return zicif_1stct;
	}
	/**
	 * @param zicif_1stct the zicif_1stct to set
	 */
	public void setZicif_1stct(String zicif_1stct) {
		this.zicif_1stct = zicif_1stct;
	}
	/**
	 * @return the zicif_1stcd
	 */
	public String getZicif_1stcd() {
		return zicif_1stcd;
	}
	/**
	 * @param zicif_1stcd the zicif_1stcd to set
	 */
	public void setZicif_1stcd(String zicif_1stcd) {
		this.zicif_1stcd = zicif_1stcd;
	}
	/**
	 * @return the zicif_2strt
	 */
	public String getZicif_2strt() {
		return zicif_2strt;
	}
	/**
	 * @param zicif_2strt the zicif_2strt to set
	 */
	public void setZicif_2strt(String zicif_2strt) {
		this.zicif_2strt = zicif_2strt;
	}
	/**
	 * @return the zicif_2stct
	 */
	public String getZicif_2stct() {
		return zicif_2stct;
	}
	/**
	 * @param zicif_2stct the zicif_2stct to set
	 */
	public void setZicif_2stct(String zicif_2stct) {
		this.zicif_2stct = zicif_2stct;
	}
	/**
	 * @return the zicif_2stcd
	 */
	public String getZicif_2stcd() {
		return zicif_2stcd;
	}
	/**
	 * @param zicif_2stcd the zicif_2stcd to set
	 */
	public void setZicif_2stcd(String zicif_2stcd) {
		this.zicif_2stcd = zicif_2stcd;
	}
	/**
	 * @return the zicif_rmrt
	 */
	public String getZicif_rmrt() {
		return zicif_rmrt;
	}
	/**
	 * @param zicif_rmrt the zicif_rmrt to set
	 */
	public void setZicif_rmrt(String zicif_rmrt) {
		this.zicif_rmrt = zicif_rmrt;
	}
	/**
	 * @return the zicif_rmct
	 */
	public String getZicif_rmct() {
		return zicif_rmct;
	}
	/**
	 * @param zicif_rmct the zicif_rmct to set
	 */
	public void setZicif_rmct(String zicif_rmct) {
		this.zicif_rmct = zicif_rmct;
	}
	/**
	 * @return the zicif_rmcd
	 */
	public String getZicif_rmcd() {
		return zicif_rmcd;
	}
	/**
	 * @param zicif_rmcd the zicif_rmcd to set
	 */
	public void setZicif_rmcd(String zicif_rmcd) {
		this.zicif_rmcd = zicif_rmcd;
	}
	/**
	 * @return the sprmk1
	 */
	public String getSprmk1() {
		return sprmk1;
	}
	/**
	 * @param sprmk1 the sprmk1 to set
	 */
	public void setSprmk1(String sprmk1) {
		this.sprmk1 = sprmk1;
	}
	/**
	 * @return the sprmk2
	 */
	public String getSprmk2() {
		return sprmk2;
	}
	/**
	 * @param sprmk2 the sprmk2 to set
	 */
	public void setSprmk2(String sprmk2) {
		this.sprmk2 = sprmk2;
	}
	/**
	 * @return the sprmk3
	 */
	public String getSprmk3() {
		return sprmk3;
	}
	/**
	 * @param sprmk3 the sprmk3 to set
	 */
	public void setSprmk3(String sprmk3) {
		this.sprmk3 = sprmk3;
	}
	/**
	 * @return the sprmk4
	 */
	public String getSprmk4() {
		return sprmk4;
	}
	/**
	 * @param sprmk4 the sprmk4 to set
	 */
	public void setSprmk4(String sprmk4) {
		this.sprmk4 = sprmk4;
	}
	/**
	 * @return the sprmk5
	 */
	public String getSprmk5() {
		return sprmk5;
	}
	/**
	 * @param sprmk5 the sprmk5 to set
	 */
	public void setSprmk5(String sprmk5) {
		this.sprmk5 = sprmk5;
	}
	/**
	 * @return the sprmk6
	 */
	public String getSprmk6() {
		return sprmk6;
	}
	/**
	 * @param sprmk6 the sprmk6 to set
	 */
	public void setSprmk6(String sprmk6) {
		this.sprmk6 = sprmk6;
	}
	/**
	 * @return the sprmk7
	 */
	public String getSprmk7() {
		return sprmk7;
	}
	/**
	 * @param sprmk7 the sprmk7 to set
	 */
	public void setSprmk7(String sprmk7) {
		this.sprmk7 = sprmk7;
	}
	/**
	 * @return the sprmk8
	 */
	public String getSprmk8() {
		return sprmk8;
	}
	/**
	 * @param sprmk8 the sprmk8 to set
	 */
	public void setSprmk8(String sprmk8) {
		this.sprmk8 = sprmk8;
	}
	/**
	 * @return the sprmk9
	 */
	public String getSprmk9() {
		return sprmk9;
	}
	/**
	 * @param sprmk9 the sprmk9 to set
	 */
	public void setSprmk9(String sprmk9) {
		this.sprmk9 = sprmk9;
	}
	/**
	 * @return the sprmk10
	 */
	public String getSprmk10() {
		return sprmk10;
	}
	/**
	 * @param sprmk10 the sprmk10 to set
	 */
	public void setSprmk10(String sprmk10) {
		this.sprmk10 = sprmk10;
	}
	/**
	 * @return the zsoflg
	 */
	public String getZsoflg() {
		return zsoflg;
	}
	/**
	 * @param zsoflg the zsoflg to set
	 */
	public void setZsoflg(String zsoflg) {
		this.zsoflg = zsoflg;
	}
	/**
	 * @return the sonnr
	 */
	public String getSonnr() {
		return sonnr;
	}
	/**
	 * @param sonnr the sonnr to set
	 */
	public void setSonnr(String sonnr) {
		this.sonnr = sonnr;
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
	 * @return the auart
	 */
	public String getAuart() {
		return auart;
	}
	/**
	 * @param auart the auart to set
	 */
	public void setAuart(String auart) {
		this.auart = auart;
	}
	/**
	 * @return the co_ddvrtq
	 */
	public String getCo_ddvrtq() {
		return co_ddvrtq;
	}
	/**
	 * @param co_ddvrtq the co_ddvrtq to set
	 */
	public void setCo_ddvrtq(String co_ddvrtq) {
		this.co_ddvrtq = co_ddvrtq;
	}
	/**
	 * @return the co_dephtq
	 */
	public String getCo_dephtq() {
		return co_dephtq;
	}
	/**
	 * @param co_dephtq the co_dephtq to set
	 */
	public void setCo_dephtq(String co_dephtq) {
		this.co_dephtq = co_dephtq;
	}
	/**
	 * @return the co_dssq
	 */
	public String getCo_dssq() {
		return co_dssq;
	}
	/**
	 * @param co_dssq the co_dssq to set
	 */
	public void setCo_dssq(String co_dssq) {
		this.co_dssq = co_dssq;
	}
	/**
	 * @return the co_bclcdtq
	 */
	public String getCo_bclcdtq() {
		return co_bclcdtq;
	}
	/**
	 * @param co_bclcdtq the co_bclcdtq to set
	 */
	public void setCo_bclcdtq(String co_bclcdtq) {
		this.co_bclcdtq = co_bclcdtq;
	}
	/**
	 * @return the co_chpitq
	 */
	public String getCo_chpitq() {
		return co_chpitq;
	}
	/**
	 * @param co_chpitq the co_chpitq to set
	 */
	public void setCo_chpitq(String co_chpitq) {
		this.co_chpitq = co_chpitq;
	}
	/**
	 * @return the co_aspc
	 */
	public String getCo_aspc() {
		return co_aspc;
	}
	/**
	 * @param co_aspc the co_aspc to set
	 */
	public void setCo_aspc(String co_aspc) {
		this.co_aspc = co_aspc;
	}
	/**
	 * @return the co_dsv1
	 */
	public String getCo_dsv1() {
		return co_dsv1;
	}
	/**
	 * @param co_dsv1 the co_dsv1 to set
	 */
	public void setCo_dsv1(String co_dsv1) {
		this.co_dsv1 = co_dsv1;
	}
	/**
	 * @return the co_dsv1tq
	 */
	public String getCo_dsv1tq() {
		return co_dsv1tq;
	}
	/**
	 * @param co_dsv1tq the co_dsv1tq to set
	 */
	public void setCo_dsv1tq(String co_dsv1tq) {
		this.co_dsv1tq = co_dsv1tq;
	}
	/**
	 * @return the co_dsv2
	 */
	public String getCo_dsv2() {
		return co_dsv2;
	}
	/**
	 * @param co_dsv2 the co_dsv2 to set
	 */
	public void setCo_dsv2(String co_dsv2) {
		this.co_dsv2 = co_dsv2;
	}
	/**
	 * @return the co_dsv2tq
	 */
	public String getCo_dsv2tq() {
		return co_dsv2tq;
	}
	/**
	 * @param co_dsv2tq the co_dsv2tq to set
	 */
	public void setCo_dsv2tq(String co_dsv2tq) {
		this.co_dsv2tq = co_dsv2tq;
	}
	/**
	 * @return the cuser
	 */
	public String getCuser() {
		return cuser;
	}
	/**
	 * @param cuser the cuser to set
	 */
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	/**
	 * @return the uuser
	 */
	public String getUuser() {
		return uuser;
	}
	/**
	 * @param uuser the uuser to set
	 */
	public void setUuser(String uuser) {
		this.uuser = uuser;
	}
	/**
	 * @return the duser
	 */
	public String getDuser() {
		return duser;
	}
	/**
	 * @param duser the duser to set
	 */
	public void setDuser(String duser) {
		this.duser = duser;
	}
	/**
	 * @return the mclass
	 */
	public String getMclass() {
		return mclass;
	}
	/**
	 * @param mclass the mclass to set
	 */
	public void setMclass(String mclass) {
		this.mclass = mclass;
	}
	/**
	 * @return the atkla
	 */
	public String getAtkla() {
		return atkla;
	}
	/**
	 * @param atkla the atkla to set
	 */
	public void setAtkla(String atkla) {
		this.atkla = atkla;
	}
	/**
	 * @return the prseq
	 */
	public String getPrseq() {
		return prseq;
	}
	/**
	 * @param prseq the prseq to set
	 */
	public void setPrseq(String prseq) {
		this.prseq = prseq;
	}
	/**
	 * @return the prh
	 */
	public String getPrh() {
		return prh;
	}
	/**
	 * @param prh the prh to set
	 */
	public void setPrh(String prh) {
		this.prh = prh;
	}
	/**
	 * @return the prd
	 */
	public String getPrd() {
		return prd;
	}
	/**
	 * @param prd the prd to set
	 */
	public void setPrd(String prd) {
		this.prd = prd;
	}
	/**
	 * @return the etch
	 */
	public String getEtch() {
		return etch;
	}
	/**
	 * @param etch the etch to set
	 */
	public void setEtch(String etch) {
		this.etch = etch;
	}
	/**
	 * @return the etcm
	 */
	public String getEtcm() {
		return etcm;
	}
	/**
	 * @param etcm the etcm to set
	 */
	public void setEtcm(String etcm) {
		this.etcm = etcm;
	}
	/**
	 * @return the ztplgbn
	 */
	public String getZtplgbn() {
		return ztplgbn;
	}
	/**
	 * @param ztplgbn the ztplgbn to set
	 */
	public void setZtplgbn(String ztplgbn) {
		this.ztplgbn = ztplgbn;
	}
	/**
	 * @return the zprdgbn
	 */
	public String getZprdgbn() {
		return zprdgbn;
	}
	/**
	 * @param zprdgbn the zprdgbn to set
	 */
	public void setZprdgbn(String zprdgbn) {
		this.zprdgbn = zprdgbn;
	}
	/**
	 * @return the ztplno
	 */
	public String getZtplno() {
		return ztplno;
	}
	/**
	 * @param ztplno the ztplno to set
	 */
	public void setZtplno(String ztplno) {
		this.ztplno = ztplno;
	}
	/**
	 * @return the spras
	 */
	public String getSpras() {
		return spras;
	}
	/**
	 * @param spras the spras to set
	 */
	public void setSpras(String spras) {
		this.spras = spras;
	}
	/**
	 * @return the waerk
	 */
	public String getWaerk() {
		return waerk;
	}
	/**
	 * @param waerk the waerk to set
	 */
	public void setWaerk(String waerk) {
		this.waerk = waerk;
	}
	/**
	 * @return the pspid
	 */
	public String getPspid() {
		return pspid;
	}
	/**
	 * @param pspid the pspid to set
	 */
	public void setPspid(String pspid) {
		this.pspid = pspid;
	}
	/**
	 * @return the posid
	 */
	public String getPosid() {
		return posid;
	}
	/**
	 * @param posid the posid to set
	 */
	public void setPosid(String posid) {
		this.posid = posid;
	}
	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	/**
	 * @return the finl
	 */
	public String getFinl() {
		return finl;
	}
	/**
	 * @param finl the finl to set
	 */
	public void setFinl(String finl) {
		this.finl = finl;
	}
	/**
	 * @return the atype
	 */
	public String getAtype() {
		return atype;
	}
	/**
	 * @param atype the atype to set
	 */
	public void setAtype(String atype) {
		this.atype = atype;
	}
	/**
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}
	/**
	 * @param gubun the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	/**
	 * @return the seria
	 */
	public String getSeria() {
		return seria;
	}
	/**
	 * @param seria the seria to set
	 */
	public void setSeria(String seria) {
		this.seria = seria;
	}
	/**
	 * @return the blseq
	 */
	public String getBlseq() {
		return blseq;
	}
	/**
	 * @param blseq the blseq to set
	 */
	public void setBlseq(String blseq) {
		this.blseq = blseq;
	}
	/**
	 * @return the blnum
	 */
	public String getBlnum() {
		return blnum;
	}
	/**
	 * @param blnum the blnum to set
	 */
	public void setBlnum(String blnum) {
		this.blnum = blnum;
	}
	/**
	 * @return the blnam
	 */
	public String getBlnam() {
		return blnam;
	}
	/**
	 * @param blnam the blnam to set
	 */
	public void setBlnam(String blnam) {
		this.blnam = blnam;
	}
	/**
	 * @return the zitem
	 */
	public String getZitem() {
		return zitem;
	}
	/**
	 * @param zitem the zitem to set
	 */
	public void setZitem(String zitem) {
		this.zitem = zitem;
	}
	/**
	 * @return the zchar
	 */
	public String getZchar() {
		return zchar;
	}
	/**
	 * @param zchar the zchar to set
	 */
	public void setZchar(String zchar) {
		this.zchar = zchar;
	}
	/**
	 * @return the zquty
	 */
	public String getZquty() {
		return zquty;
	}
	/**
	 * @param zquty the zquty to set
	 */
	public void setZquty(String zquty) {
		this.zquty = zquty;
	}
	/**
	 * @return the zamnt
	 */
	public String getZamnt() {
		return zamnt;
	}
	/**
	 * @param zamnt the zamnt to set
	 */
	public void setZamnt(String zamnt) {
		this.zamnt = zamnt;
	}
	/**
	 * @return the ztotl
	 */
	public String getZtotl() {
		return ztotl;
	}
	/**
	 * @param ztotl the ztotl to set
	 */
	public void setZtotl(String ztotl) {
		this.ztotl = ztotl;
	}
	/**
	 * @return the bigo
	 */
	public String getBigo() {
		return bigo;
	}
	/**
	 * @param bigo the bigo to set
	 */
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	/**
	 * @return the tex1
	 */
	public String getTex1() {
		return tex1;
	}
	/**
	 * @param tex1 the tex1 to set
	 */
	public void setTex1(String tex1) {
		this.tex1 = tex1;
	}
	/**
	 * @return the tex2
	 */
	public String getTex2() {
		return tex2;
	}
	/**
	 * @param tex2 the tex2 to set
	 */
	public void setTex2(String tex2) {
		this.tex2 = tex2;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the addt01
	 */
	public String getAddt01() {
		return addt01;
	}
	/**
	 * @param addt01 the addt01 to set
	 */
	public void setAddt01(String addt01) {
		this.addt01 = addt01;
	}
	/**
	 * @return the messg
	 */
	public String getMessg() {
		return messg;
	}
	/**
	 * @param messg the messg to set
	 */
	public void setMessg(String messg) {
		this.messg = messg;
	}
	/**
	 * @return the sorlt
	 */
	public String getSorlt() {
		return sorlt;
	}
	/**
	 * @param sorlt the sorlt to set
	 */
	public void setSorlt(String sorlt) {
		this.sorlt = sorlt;
	}
	/**
	 * @return the zlzone
	 */
	public String getZlzone() {
		return zlzone;
	}
	/**
	 * @param zlzone the zlzone to set
	 */
	public void setZlzone(String zlzone) {
		this.zlzone = zlzone;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the zagntflg
	 */
	public String getZagntflg() {
		return zagntflg;
	}
	/**
	 * @param zagntflg the zagntflg to set
	 */
	public void setZagntflg(String zagntflg) {
		this.zagntflg = zagntflg;
	}
	/**
	 * @return the inco2
	 */
	public String getInco2() {
		return inco2;
	}
	/**
	 * @param inco2 the inco2 to set
	 */
	public void setInco2(String inco2) {
		this.inco2 = inco2;
	}
	/**
	 * @return the frqtdat
	 */
	public String getFrqtdat() {
		return frqtdat;
	}
	/**
	 * @param frqtdat the frqtdat to set
	 */
	public void setFrqtdat(String frqtdat) {
		this.frqtdat = frqtdat;
	}
	/**
	 * @return the toqtdat
	 */
	public String getToqtdat() {
		return toqtdat;
	}
	/**
	 * @param toqtdat the toqtdat to set
	 */
	public void setToqtdat(String toqtdat) {
		this.toqtdat = toqtdat;
	}
	/**
	 * @return the atinn
	 */
	public String getAtinn() {
		return atinn;
	}
	/**
	 * @param atinn the atinn to set
	 */
	public void setAtinn(String atinn) {
		this.atinn = atinn;
	}
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * @return the zyear
	 */
	public String getZyear() {
		return zyear;
	}
	/**
	 * @param zyear the zyear to set
	 */
	public void setZyear(String zyear) {
		this.zyear = zyear;
	}
	/**
	 * @return the zmonth
	 */
	public String getZmonth() {
		return zmonth;
	}
	/**
	 * @param zmonth the zmonth to set
	 */
	public void setZmonth(String zmonth) {
		this.zmonth = zmonth;
	}
	/**
	 * @return the fa_byrgbn
	 */
	public String getFa_byrgbn() {
		return fa_byrgbn;
	}
	/**
	 * @param fa_byrgbn the fa_byrgbn to set
	 */
	public void setFa_byrgbn(String fa_byrgbn) {
		this.fa_byrgbn = fa_byrgbn;
	}
	/**
	 * @return the zdeli
	 */
	public String getZdeli() {
		return zdeli;
	}
	/**
	 * @param zdeli the zdeli to set
	 */
	public void setZdeli(String zdeli) {
		this.zdeli = zdeli;
	}
	/**
	 * @return the max_qtser
	 */
	public String getMax_qtser() {
		return max_qtser;
	}
	/**
	 * @param max_qtser the max_qtser to set
	 */
	public void setMax_qtser(String max_qtser) {
		this.max_qtser = max_qtser;
	}
	/**
	 * @return the f_flag
	 */
	public String getF_flag() {
		return f_flag;
	}
	/**
	 * @param f_flag the f_flag to set
	 */
	public void setF_flag(String f_flag) {
		this.f_flag = f_flag;
	}
	/**
	 * @return the sdate
	 */
	public String getSdate() {
		return sdate;
	}
	/**
	 * @param sdate the sdate to set
	 */
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	/**
	 * @return the sdate
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
	
	/* 상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청 */	
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
	/**
	 * @return the fpath
	 */
	public String getFpath() {
		return fpath;
	}
	/**
	 * @param fpath the fpath to set
	 */
	public void setFpath(String fpath) {
		this.fpath = fpath;
	}
	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getEgisFlag() {
		return egisFlag;
	}
	public void setEgisFlag(String egisFlag) {
		this.egisFlag = egisFlag;
	}
	public String getEgisEstNo() {
		return egisEstNo;
	}
	public void setEgisEstNo(String egisEstNo) {
		this.egisEstNo = egisEstNo;
	}
	public String getEgisEstSeq() {
		return egisEstSeq;
	}
	public void setEgisEstSeq(String egisEstSeq) {
		this.egisEstSeq = egisEstSeq;
	}
	public String getEgisEstSerno() {
		return egisEstSerno;
	}
	public void setEgisEstSerno(String egisEstSerno) {
		this.egisEstSerno = egisEstSerno;
	}
	public String getDeptport() {
		return deptport;
	}
	public void setDeptport(String deptport) {
		this.deptport = deptport;
	}
	public String getPspidCo() {
		return pspidCo;
	}
	public void setPspidCo(String pspidCo) {
		this.pspidCo = pspidCo;
	}
	public String getDestport() {
		return destport;
	}
	public void setDestport(String destport) {
		this.destport = destport;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getEgisSpecType() {
		return egisSpecType;
	}
	public void setEgisSpecType(String egisSpecType) {
		this.egisSpecType = egisSpecType;
	}
	public String getLifnrchk() {
		return lifnrchk;
	}
	public void setLifnrchk(String lifnrchk) {
		this.lifnrchk = lifnrchk;
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

	// 프로젝트 원가 21.07.01 추가
	public void setZuamp(String zuamp) {
		this.zuamp = zuamp;
	}
	public void setZcostp(String zcostp) {
		this.zcostp = zcostp;
	}
	
	public String getZcostp() {
		return zcostp;
	}
	public String getZuamp() {
		return zuamp;
	}
}
