package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0451 extends CommonDomain {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mandt;
    private String qtso_no;
    private String qtser;
    private String qtseq;
    private String prd;
	private String prh;
	private String mclass;
	private String atkla;
	private String prhname;
	
	private String zrqseq;
	private String zrqdat;
	private String dsngbn;
	private String zrqid;
	private String zrqnm;
	private String zrq_deptnm;
	private String zrqtel;
	private String zrqcel;
	private String zrqcn;
	private String zrqstat;
	private String kunnr_ds;
	private String kunnr_dsnm;
	private String hogi;
	private String dlvdat;
	private String findat;

	private String ceil_2nd;
	private String wall_2nd;
	private String flor_2nd;
	private String hdrl_2nd;
	private String oper_2nd;
	private String door_2nd;
	private String poid_2nd;
	private String hatc_2nd;
	private String jamb_2nd;
	private String hbtn_2nd;
	private String hltn_2nd;
	private String expo_2nd;
	private String ceil_3nd;
	private String wall_3nd;
	private String flor_3nd;
	private String hdrl_3nd;
	private String oper_3nd;
	private String door_3nd;
	private String poid_3nd;
	private String hatc_3nd;
	private String jamb_3nd;
	private String hbtn_3nd;
	private String hltn_3nd;
	private String expo_3nd;
	private String norest;
	private String rest01;
	private String rest02;
	private String rest03;
	private String delv01;
	private String delv02;
	private String delv03;
	private String addtuse01; //2013.06.03 수정[추가용도 장애인적용,비상용 추가]
	private String addtuse02;	
	private String ex_kunnr;
	private String ex_kunnrnm;
	private String inter_amt;
	private String rq_sa;
	private String rq_sa_nm;
	private String rq_tb;
	private String rq_tb_nm;
	private String rq_db;
	private String rq_db_nm;
	private String ds_tb;
	private String ds_tb_nm;
	private String ds_db;
	private String ds_db_nm;
	private String cdate;
	private String ctime;
	private String cuser;
	private String udate;
	private String utime;
	private String uuser;
	private String gvgcd;
	private String vbeln;
	private String gsnam;
	private String matnr;
	private String posnr;
	private String zrsrlt;
	
	private String gtype;
	private String znumber;
	private String zacapa;
	private String zuse;
	private String zopn;
	private String zlen;
	private String type1;
	private String type2;
	private String type3;
	
	private String metr_2nd;
	private String atyp_2nd;
	private String aman_2nd;
	private String aqty_2nd;
	private String aspd_2nd;
	private String aflr_2nd;
	private String ause_2nd;
	private String adoor_2nd;
	private String metr_3nd;
	private String atyp_3nd;
	private String aman_3nd;
	private String aqty_3nd;
	private String aspd_3nd;
	private String aflr_3nd;
	private String ause_3nd;
	private String adoor_3nd;

	
	//2013.06.03 첨부파일 추가
	private String flag;
	private String zattseq;
	private String zatgbn;
	private String zattpath;
	private String zattfn;
	
	private String scolor;
	
	private String vkbur;
	private String Dfindat;
	private String email;
	
	private String zrqseq_del;
	
	private String email_dept;
	
	// 20201015 외주업체 추가
	private String lifnr;
	private String lifnrnm;
	
	public String getVkbur() {
		return vkbur;
	}
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
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
	 * @return the qtso_no
	 */
	public String getQtso_no() {
		return qtso_no;
	}
	/**
	 * @param qtso_no the qtso_no to set
	 */
	public void setQtso_no(String qtso_no) {
		this.qtso_no = qtso_no;
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
	 * @return the prhname
	 */
	public String getPrhname() {
		return prhname;
	}
	/**
	 * @param prhname the prhname to set
	 */
	public void setPrhname(String prhname) {
		this.prhname = prhname;
	}
	/**
	 * @return the zrqseq
	 */
	public String getZrqseq() {
		return zrqseq;
	}
	/**
	 * @param zrqseq the zrqseq to set
	 */
	public void setZrqseq(String zrqseq) {
		this.zrqseq = zrqseq;
	}
	/**
	 * @return the zrqdat
	 */
	public String getZrqdat() {
		return zrqdat;
	}
	/**
	 * @param zrqdat the zrqdat to set
	 */
	public void setZrqdat(String zrqdat) {
		this.zrqdat = zrqdat;
	}
	/**
	 * @return the dsngbn
	 */
	public String getDsngbn() {
		return dsngbn;
	}
	/**
	 * @param dsngbn the dsngbn to set
	 */
	public void setDsngbn(String dsngbn) {
		this.dsngbn = dsngbn;
	}
	/**
	 * @return the zrqid
	 */
	public String getZrqid() {
		return zrqid;
	}
	/**
	 * @param zrqid the zrqid to set
	 */
	public void setZrqid(String zrqid) {
		this.zrqid = zrqid;
	}
	/**
	 * @return the zrqnm
	 */
	public String getZrqnm() {
		return zrqnm;
	}
	/**
	 * @param zrqnm the zrqnm to set
	 */
	public void setZrqnm(String zrqnm) {
		this.zrqnm = zrqnm;
	}
	/**
	 * @return the zrq_deptnm
	 */
	public String getZrq_deptnm() {
		return zrq_deptnm;
	}
	/**
	 * @param zrq_deptnm the zrq_deptnm to set
	 */
	public void setZrq_deptnm(String zrq_deptnm) {
		this.zrq_deptnm = zrq_deptnm;
	}
	/**
	 * @return the zrqtel
	 */
	public String getZrqtel() {
		return zrqtel;
	}
	/**
	 * @param zrqtel the zrqtel to set
	 */
	public void setZrqtel(String zrqtel) {
		this.zrqtel = zrqtel;
	}
	/**
	 * @return the zrqcel
	 */
	public String getZrqcel() {
		return zrqcel;
	}
	/**
	 * @param zrqcel the zrqcel to set
	 */
	public void setZrqcel(String zrqcel) {
		this.zrqcel = zrqcel;
	}
	/**
	 * @return the zrqcn
	 */
	public String getZrqcn() {
		return zrqcn;
	}
	/**
	 * @param zrqcn the zrqcn to set
	 */
	public void setZrqcn(String zrqcn) {
		this.zrqcn = zrqcn;
	}
	/**
	 * @return the zrqstat
	 */
	public String getZrqstat() {
		return zrqstat;
	}
	/**
	 * @param zrqstat the zrqstat to set
	 */
	public void setZrqstat(String zrqstat) {
		this.zrqstat = zrqstat;
	}
	/**
	 * @return the kunnr_ds
	 */
	public String getKunnr_ds() {
		return kunnr_ds;
	}
	/**
	 * @param kunnr_ds the kunnr_ds to set
	 */
	public void setKunnr_ds(String kunnr_ds) {
		this.kunnr_ds = kunnr_ds;
	}
	/**
	 * @return the kunnr_dsnm
	 */
	public String getKunnr_dsnm() {
		return kunnr_dsnm;
	}
	/**
	 * @param kunnr_dsnm the kunnr_dsnm to set
	 */
	public void setKunnr_dsnm(String kunnr_dsnm) {
		this.kunnr_dsnm = kunnr_dsnm;
	}
	/**
	 * @return the hogi
	 */
	public String getHogi() {
		return hogi;
	}
	/**
	 * @param hogi the hogi to set
	 */
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}
	/**
	 * @return the dlvdat
	 */
	public String getDlvdat() {
		return dlvdat;
	}
	/**
	 * @param dlvdat the dlvdat to set
	 */
	public void setDlvdat(String dlvdat) {
		this.dlvdat = dlvdat;
	}
	/**
	 * @return the findat
	 */
	public String getFindat() {
		return findat;
	}
	/**
	 * @param findat the findat to set
	 */
	public void setFindat(String findat) {
		this.findat = findat;
	}
	/**
	 * @return the ceil_2nd
	 */
	public String getCeil_2nd() {
		return ceil_2nd;
	}
	/**
	 * @param ceil_2nd the ceil_2nd to set
	 */
	public void setCeil_2nd(String ceil_2nd) {
		this.ceil_2nd = ceil_2nd;
	}
	/**
	 * @return the wall_2nd
	 */
	public String getWall_2nd() {
		return wall_2nd;
	}
	/**
	 * @param wall_2nd the wall_2nd to set
	 */
	public void setWall_2nd(String wall_2nd) {
		this.wall_2nd = wall_2nd;
	}
	/**
	 * @return the flor_2nd
	 */
	public String getFlor_2nd() {
		return flor_2nd;
	}
	/**
	 * @param flor_2nd the flor_2nd to set
	 */
	public void setFlor_2nd(String flor_2nd) {
		this.flor_2nd = flor_2nd;
	}
	/**
	 * @return the hdrl_2nd
	 */
	public String getHdrl_2nd() {
		return hdrl_2nd;
	}
	/**
	 * @param hdrl_2nd the hdrl_2nd to set
	 */
	public void setHdrl_2nd(String hdrl_2nd) {
		this.hdrl_2nd = hdrl_2nd;
	}
	/**
	 * @return the oper_2nd
	 */
	public String getOper_2nd() {
		return oper_2nd;
	}
	/**
	 * @param oper_2nd the oper_2nd to set
	 */
	public void setOper_2nd(String oper_2nd) {
		this.oper_2nd = oper_2nd;
	}
	/**
	 * @return the door_2nd
	 */
	public String getDoor_2nd() {
		return door_2nd;
	}
	/**
	 * @param door_2nd the door_2nd to set
	 */
	public void setDoor_2nd(String door_2nd) {
		this.door_2nd = door_2nd;
	}
	/**
	 * @return the poid_2nd
	 */
	public String getPoid_2nd() {
		return poid_2nd;
	}
	/**
	 * @param poid_2nd the poid_2nd to set
	 */
	public void setPoid_2nd(String poid_2nd) {
		this.poid_2nd = poid_2nd;
	}
	/**
	 * @return the hatc_2nd
	 */
	public String getHatc_2nd() {
		return hatc_2nd;
	}
	/**
	 * @param hatc_2nd the hatc_2nd to set
	 */
	public void setHatc_2nd(String hatc_2nd) {
		this.hatc_2nd = hatc_2nd;
	}
	/**
	 * @return the jamb_2nd
	 */
	public String getJamb_2nd() {
		return jamb_2nd;
	}
	/**
	 * @param jamb_2nd the jamb_2nd to set
	 */
	public void setJamb_2nd(String jamb_2nd) {
		this.jamb_2nd = jamb_2nd;
	}
	/**
	 * @return the hbtn_2nd
	 */
	public String getHbtn_2nd() {
		return hbtn_2nd;
	}
	/**
	 * @param hbtn_2nd the hbtn_2nd to set
	 */
	public void setHbtn_2nd(String hbtn_2nd) {
		this.hbtn_2nd = hbtn_2nd;
	}
	/**
	 * @return the hltn_2nd
	 */
	public String getHltn_2nd() {
		return hltn_2nd;
	}
	/**
	 * @param hltn_2nd the hltn_2nd to set
	 */
	public void setHltn_2nd(String hltn_2nd) {
		this.hltn_2nd = hltn_2nd;
	}
	/**
	 * @return the expo_2nd
	 */
	public String getExpo_2nd() {
		return expo_2nd;
	}
	/**
	 * @param expo_2nd the expo_2nd to set
	 */
	public void setExpo_2nd(String expo_2nd) {
		this.expo_2nd = expo_2nd;
	}
	/**
	 * @return the ceil_3nd
	 */
	public String getCeil_3nd() {
		return ceil_3nd;
	}
	/**
	 * @param ceil_3nd the ceil_3nd to set
	 */
	public void setCeil_3nd(String ceil_3nd) {
		this.ceil_3nd = ceil_3nd;
	}
	/**
	 * @return the wall_3nd
	 */
	public String getWall_3nd() {
		return wall_3nd;
	}
	/**
	 * @param wall_3nd the wall_3nd to set
	 */
	public void setWall_3nd(String wall_3nd) {
		this.wall_3nd = wall_3nd;
	}
	/**
	 * @return the flor_3nd
	 */
	public String getFlor_3nd() {
		return flor_3nd;
	}
	/**
	 * @param flor_3nd the flor_3nd to set
	 */
	public void setFlor_3nd(String flor_3nd) {
		this.flor_3nd = flor_3nd;
	}
	/**
	 * @return the hdrl_3nd
	 */
	public String getHdrl_3nd() {
		return hdrl_3nd;
	}
	/**
	 * @param hdrl_3nd the hdrl_3nd to set
	 */
	public void setHdrl_3nd(String hdrl_3nd) {
		this.hdrl_3nd = hdrl_3nd;
	}
	/**
	 * @return the oper_3nd
	 */
	public String getOper_3nd() {
		return oper_3nd;
	}
	/**
	 * @param oper_3nd the oper_3nd to set
	 */
	public void setOper_3nd(String oper_3nd) {
		this.oper_3nd = oper_3nd;
	}
	/**
	 * @return the door_3nd
	 */
	public String getDoor_3nd() {
		return door_3nd;
	}
	/**
	 * @param door_3nd the door_3nd to set
	 */
	public void setDoor_3nd(String door_3nd) {
		this.door_3nd = door_3nd;
	}
	/**
	 * @return the poid_3nd
	 */
	public String getPoid_3nd() {
		return poid_3nd;
	}
	/**
	 * @param poid_3nd the poid_3nd to set
	 */
	public void setPoid_3nd(String poid_3nd) {
		this.poid_3nd = poid_3nd;
	}
	/**
	 * @return the hatc_3nd
	 */
	public String getHatc_3nd() {
		return hatc_3nd;
	}
	/**
	 * @param hatc_3nd the hatc_3nd to set
	 */
	public void setHatc_3nd(String hatc_3nd) {
		this.hatc_3nd = hatc_3nd;
	}
	/**
	 * @return the jamb_3nd
	 */
	public String getJamb_3nd() {
		return jamb_3nd;
	}
	/**
	 * @param jamb_3nd the jamb_3nd to set
	 */
	public void setJamb_3nd(String jamb_3nd) {
		this.jamb_3nd = jamb_3nd;
	}
	/**
	 * @return the hbtn_3nd
	 */
	public String getHbtn_3nd() {
		return hbtn_3nd;
	}
	/**
	 * @param hbtn_3nd the hbtn_3nd to set
	 */
	public void setHbtn_3nd(String hbtn_3nd) {
		this.hbtn_3nd = hbtn_3nd;
	}
	/**
	 * @return the hltn_3nd
	 */
	public String getHltn_3nd() {
		return hltn_3nd;
	}
	/**
	 * @param hltn_3nd the hltn_3nd to set
	 */
	public void setHltn_3nd(String hltn_3nd) {
		this.hltn_3nd = hltn_3nd;
	}
	/**
	 * @return the expo_3nd
	 */
	public String getExpo_3nd() {
		return expo_3nd;
	}
	/**
	 * @param expo_3nd the expo_3nd to set
	 */
	public void setExpo_3nd(String expo_3nd) {
		this.expo_3nd = expo_3nd;
	}
	/**
	 * @return the norest
	 */
	public String getNorest() {
		return norest;
	}
	/**
	 * @param norest the norest to set
	 */
	public void setNorest(String norest) {
		this.norest = norest;
	}
	/**
	 * @return the rest01
	 */
	public String getRest01() {
		return rest01;
	}
	/**
	 * @param rest01 the rest01 to set
	 */
	public void setRest01(String rest01) {
		this.rest01 = rest01;
	}
	/**
	 * @return the rest02
	 */
	public String getRest02() {
		return rest02;
	}
	/**
	 * @param rest02 the rest02 to set
	 */
	public void setRest02(String rest02) {
		this.rest02 = rest02;
	}
	/**
	 * @return the rest03
	 */
	public String getRest03() {
		return rest03;
	}
	/**
	 * @param rest03 the rest03 to set
	 */
	public void setRest03(String rest03) {
		this.rest03 = rest03;
	}
	/**
	 * @return the delv01
	 */
	public String getDelv01() {
		return delv01;
	}
	/**
	 * @param delv01 the delv01 to set
	 */
	public void setDelv01(String delv01) {
		this.delv01 = delv01;
	}
	/**
	 * @return the delv02
	 */
	public String getDelv02() {
		return delv02;
	}
	/**
	 * @param delv02 the delv02 to set
	 */
	public void setDelv02(String delv02) {
		this.delv02 = delv02;
	}
	/**
	 * @return the delv03
	 */
	public String getDelv03() {
		return delv03;
	}
	/**
	 * @param delv03 the delv03 to set
	 */
	public void setDelv03(String delv03) {
		this.delv03 = delv03;
	}
		
	/**
	 * @return the addtuse01
	 */
	public String getAddtuse01() {
		return addtuse01;
	}
	/**
	 * @param addtuse01 the addtuse01 to set
	 */
	public void setAddtuse01(String addtuse01) {
		this.addtuse01 = addtuse01;
	}
	/**
	 * @return the addtuse02
	 */
	public String getAddtuse02() {
		return addtuse02;
	}
	/**
	 * @param addtuse02 the addtuse02 to set
	 */
	public void setAddtuse02(String addtuse02) {
		this.addtuse02 = addtuse02;
	}
	/**
	 * @return the ex_kunnr
	 */
	public String getEx_kunnr() {
		return ex_kunnr;
	}
	/**
	 * @param ex_kunnr the ex_kunnr to set
	 */
	public void setEx_kunnr(String ex_kunnr) {
		this.ex_kunnr = ex_kunnr;
	}
	/**
	 * @return the ex_kunnrnm
	 */
	public String getEx_kunnrnm() {
		return ex_kunnrnm;
	}
	/**
	 * @param ex_kunnrnm the ex_kunnrnm to set
	 */
	public void setEx_kunnrnm(String ex_kunnrnm) {
		this.ex_kunnrnm = ex_kunnrnm;
	}
	/**
	 * @return the inter_amt
	 */
	public String getInter_amt() {
		return inter_amt;
	}
	/**
	 * @param inter_amt the inter_amt to set
	 */
	public void setInter_amt(String inter_amt) {
		this.inter_amt = inter_amt;
	}
	/**
	 * @return the rq_sa
	 */
	public String getRq_sa() {
		return rq_sa;
	}
	/**
	 * @param rq_sa the rq_sa to set
	 */
	public void setRq_sa(String rq_sa) {
		this.rq_sa = rq_sa;
	}
	/**
	 * @return the rq_sa_nm
	 */
	public String getRq_sa_nm() {
		return rq_sa_nm;
	}
	/**
	 * @param rq_sa_nm the rq_sa_nm to set
	 */
	public void setRq_sa_nm(String rq_sa_nm) {
		this.rq_sa_nm = rq_sa_nm;
	}
	/**
	 * @return the rq_tb
	 */
	public String getRq_tb() {
		return rq_tb;
	}
	/**
	 * @param rq_tb the rq_tb to set
	 */
	public void setRq_tb(String rq_tb) {
		this.rq_tb = rq_tb;
	}
	/**
	 * @return the rq_tb_nm
	 */
	public String getRq_tb_nm() {
		return rq_tb_nm;
	}
	/**
	 * @param rq_tb_nm the rq_tb_nm to set
	 */
	public void setRq_tb_nm(String rq_tb_nm) {
		this.rq_tb_nm = rq_tb_nm;
	}
	/**
	 * @return the rq_db
	 */
	public String getRq_db() {
		return rq_db;
	}
	/**
	 * @param rq_db the rq_db to set
	 */
	public void setRq_db(String rq_db) {
		this.rq_db = rq_db;
	}
	/**
	 * @return the rq_db_nm
	 */
	public String getRq_db_nm() {
		return rq_db_nm;
	}
	/**
	 * @param rq_db_nm the rq_db_nm to set
	 */
	public void setRq_db_nm(String rq_db_nm) {
		this.rq_db_nm = rq_db_nm;
	}
	/**
	 * @return the ds_tb
	 */
	public String getDs_tb() {
		return ds_tb;
	}
	/**
	 * @param ds_tb the ds_tb to set
	 */
	public void setDs_tb(String ds_tb) {
		this.ds_tb = ds_tb;
	}
	/**
	 * @return the ds_tb_nm
	 */
	public String getDs_tb_nm() {
		return ds_tb_nm;
	}
	/**
	 * @param ds_tb_nm the ds_tb_nm to set
	 */
	public void setDs_tb_nm(String ds_tb_nm) {
		this.ds_tb_nm = ds_tb_nm;
	}
	/**
	 * @return the ds_db
	 */
	public String getDs_db() {
		return ds_db;
	}
	/**
	 * @param ds_db the ds_db to set
	 */
	public void setDs_db(String ds_db) {
		this.ds_db = ds_db;
	}
	/**
	 * @return the ds_db_nm
	 */
	public String getDs_db_nm() {
		return ds_db_nm;
	}
	/**
	 * @param ds_db_nm the ds_db_nm to set
	 */
	public void setDs_db_nm(String ds_db_nm) {
		this.ds_db_nm = ds_db_nm;
	}
	/**
	 * @return the cdate
	 */
	public String getCdate() {
		return cdate;
	}
	/**
	 * @param cdate the cdate to set
	 */
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	/**
	 * @return the ctime
	 */
	public String getCtime() {
		return ctime;
	}
	/**
	 * @param ctime the ctime to set
	 */
	public void setCtime(String ctime) {
		this.ctime = ctime;
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
	 * @return the udate
	 */
	public String getUdate() {
		return udate;
	}
	/**
	 * @param udate the udate to set
	 */
	public void setUdate(String udate) {
		this.udate = udate;
	}
	/**
	 * @return the utime
	 */
	public String getUtime() {
		return utime;
	}
	/**
	 * @param utime the utime to set
	 */
	public void setUtime(String utime) {
		this.utime = utime;
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
	 * @return the gvgcd
	 */
	public String getGvgcd() {
		return gvgcd;
	}
	/**
	 * @param gvgcd the gvgcd to set
	 */
	public void setGvgcd(String gvgcd) {
		this.gvgcd = gvgcd;
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
	 * @return the posnr
	 */
	public String getPosnr() {
		return posnr;
	}
	/**
	 * @param posnr the posnr to set
	 */
	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}
	/**
	 * @return the zrsrlt
	 */
	public String getZrsrlt() {
		return zrsrlt;
	}
	/**
	 * @param zrsrlt the zrsrlt to set
	 */
	public void setZrsrlt(String zrsrlt) {
		this.zrsrlt = zrsrlt;
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
	 * @return the metr_2nd
	 */
	public String getMetr_2nd() {
		return metr_2nd;
	}
	/**
	 * @param metr_2nd the metr_2nd to set
	 */
	public void setMetr_2nd(String metr_2nd) {
		this.metr_2nd = metr_2nd;
	}
	/**
	 * @return the atyp_2nd
	 */
	public String getAtyp_2nd() {
		return atyp_2nd;
	}
	/**
	 * @param atyp_2nd the atyp_2nd to set
	 */
	public void setAtyp_2nd(String atyp_2nd) {
		this.atyp_2nd = atyp_2nd;
	}
	/**
	 * @return the aman_2nd
	 */
	public String getAman_2nd() {
		return aman_2nd;
	}
	/**
	 * @param aman_2nd the aman_2nd to set
	 */
	public void setAman_2nd(String aman_2nd) {
		this.aman_2nd = aman_2nd;
	}
	/**
	 * @return the aqty_2nd
	 */
	public String getAqty_2nd() {
		return aqty_2nd;
	}
	/**
	 * @param aqty_2nd the aqty_2nd to set
	 */
	public void setAqty_2nd(String aqty_2nd) {
		this.aqty_2nd = aqty_2nd;
	}
	/**
	 * @return the aspd_2nd
	 */
	public String getAspd_2nd() {
		return aspd_2nd;
	}
	/**
	 * @param aspd_2nd the aspd_2nd to set
	 */
	public void setAspd_2nd(String aspd_2nd) {
		this.aspd_2nd = aspd_2nd;
	}
	/**
	 * @return the aflr_2nd
	 */
	public String getAflr_2nd() {
		return aflr_2nd;
	}
	/**
	 * @param aflr_2nd the aflr_2nd to set
	 */
	public void setAflr_2nd(String aflr_2nd) {
		this.aflr_2nd = aflr_2nd;
	}
	/**
	 * @return the ause_2nd
	 */
	public String getAuse_2nd() {
		return ause_2nd;
	}
	/**
	 * @param ause_2nd the ause_2nd to set
	 */
	public void setAuse_2nd(String ause_2nd) {
		this.ause_2nd = ause_2nd;
	}
	/**
	 * @return the adoor_2nd
	 */
	public String getAdoor_2nd() {
		return adoor_2nd;
	}
	/**
	 * @param adoor_2nd the adoor_2nd to set
	 */
	public void setAdoor_2nd(String adoor_2nd) {
		this.adoor_2nd = adoor_2nd;
	}
	/**
	 * @return the metr_3nd
	 */
	public String getMetr_3nd() {
		return metr_3nd;
	}
	/**
	 * @param metr_3nd the metr_3nd to set
	 */
	public void setMetr_3nd(String metr_3nd) {
		this.metr_3nd = metr_3nd;
	}
	/**
	 * @return the atyp_3nd
	 */
	public String getAtyp_3nd() {
		return atyp_3nd;
	}
	/**
	 * @param atyp_3nd the atyp_3nd to set
	 */
	public void setAtyp_3nd(String atyp_3nd) {
		this.atyp_3nd = atyp_3nd;
	}
	/**
	 * @return the aman_3nd
	 */
	public String getAman_3nd() {
		return aman_3nd;
	}
	/**
	 * @param aman_3nd the aman_3nd to set
	 */
	public void setAman_3nd(String aman_3nd) {
		this.aman_3nd = aman_3nd;
	}
	/**
	 * @return the aqty_3nd
	 */
	public String getAqty_3nd() {
		return aqty_3nd;
	}
	/**
	 * @param aqty_3nd the aqty_3nd to set
	 */
	public void setAqty_3nd(String aqty_3nd) {
		this.aqty_3nd = aqty_3nd;
	}
	/**
	 * @return the aspd_3nd
	 */
	public String getAspd_3nd() {
		return aspd_3nd;
	}
	/**
	 * @param aspd_3nd the aspd_3nd to set
	 */
	public void setAspd_3nd(String aspd_3nd) {
		this.aspd_3nd = aspd_3nd;
	}
	/**
	 * @return the aflr_3nd
	 */
	public String getAflr_3nd() {
		return aflr_3nd;
	}
	/**
	 * @param aflr_3nd the aflr_3nd to set
	 */
	public void setAflr_3nd(String aflr_3nd) {
		this.aflr_3nd = aflr_3nd;
	}
	/**
	 * @return the ause_3nd
	 */
	public String getAuse_3nd() {
		return ause_3nd;
	}
	/**
	 * @param ause_3nd the ause_3nd to set
	 */
	public void setAuse_3nd(String ause_3nd) {
		this.ause_3nd = ause_3nd;
	}
	/**
	 * @return the adoor_3nd
	 */
	public String getAdoor_3nd() {
		return adoor_3nd;
	}
	/**
	 * @param adoor_3nd the adoor_3nd to set
	 */
	public void setAdoor_3nd(String adoor_3nd) {
		this.adoor_3nd = adoor_3nd;
	}
	
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
	 * @return the zattseq
	 */
	public String getZattseq() {
		return zattseq;
	}
	/**
	 * @param zattseq the zattseq to set
	 */
	public void setZattseq(String zattseq) {
		this.zattseq = zattseq;
	}
	/**
	 * @return the zatgbn
	 */
	public String getZatgbn() {
		return zatgbn;
	}
	/**
	 * @param zatgbn the zatgbn to set
	 */
	public void setZatgbn(String zatgbn) {
		this.zatgbn = zatgbn;
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
	 * @return the scolor
	 */
	public String getScolor() {
		return scolor;
	}
	/**
	 * @param scolor the scolor to set
	 */
	public void setScolor(String scolor) {
		this.scolor = scolor;
	}
	public String getDfindat() {
		return Dfindat;
	}
	public void setDfindat(String dfindat) {
		Dfindat = dfindat;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZrqseq_del() {
		return zrqseq_del;
	}
	public void setZrqseq_del(String zrqseq_del) {
		this.zrqseq_del = zrqseq_del;
	}
	public String getEmail_dept() {
		return email_dept;
	}
	public void setEmail_dept(String email_dept) {
		this.email_dept = email_dept;
	}
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public String getLifnrnm() {
		return lifnrnm;
	}
	public void setLifnrnm(String lifnrnm) {
		this.lifnrnm = lifnrnm;
	}
	
}
