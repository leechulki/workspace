package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

@SuppressWarnings("serial")
public class Ses0330ParamVO extends ParameterVO {

	private String  mandt;				//mandt
	private String  vbeln; 				//영업오더번호
	private String  zseq;  				//회차
	private String  bstkd;       //현장명
	private String  region;      //지역
	private String  inco1;       //인도조건:파트1
	private String  inco2;       //인도조건:파트2
	private String  ztermt;      //지급조건내역
	private String  specdwg;		 //스펙
	private String  edatu;       //납기일자
	private String  edatufr;       //납기일자fr
	private String  edatuto;       //납기일자to
	private String  zfwding;		 //포워딩
	private String  sedat;       //메일전송시일자
	private String  sezzt;       //메일전송시간
	private String  erdat;       //생성일자
	private String  erzzt;       //생성시간
	private String  ernam;       //생성자
	
	private String  posnr;       //품목번호
	private String  posid;       //호기번호
	private String  wwmod;    		//기종
	private String  arktx;       //사양
	private String  kwmeng;      //판매대수
	private String  vrkme;       //판매단위
	private String  netwr;       //판매금액
	private String  waerk;       //통화단위

	private String  characteristic;		//특성이름
	private String  cuobj;       //구성
	private String  value;       //특성값
	
	
	private String  flag;       //flag
	private String  recad_da;       //승인일자	
	
	private String spras;			// G_LANG
	private String zzpjt_id;		// 공사번호 proj.no.
	private String land1;			// 국가코드
	private String vkbur;			// 부서
	private String vkgrp;			// 팀
	private String zkunnr;			// 담당자
	private String remark1;			// 비고1
	private String remark2;			// 비고2
	private String remark3;			// 비고3
	private String remark4;			// 비고4
	private String remark5;			// 비고5
	private String shnetwr;			// 상해금액
	private String trade;			// 중계무역여부
	private String acdat;			// 접수일자
	private String lang;
	
	
	public String getAcdat() {
		return acdat;
	}
	public void setAcdat(String acdat) {
		this.acdat = acdat;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getShnetwr() {
		return shnetwr;
	}
	public void setShnetwr(String shnetwr) {
		this.shnetwr = shnetwr;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getRemark5() {
		return remark5;
	}
	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}
	public String getSpras() {
		return spras;
	}
	public void setSpras(String spras) {
		this.spras = spras;
	}
	public String getLand1() {
		return land1;
	}
	public void setLand1(String land1) {
		this.land1 = land1;
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
	public String getZzpjt_id() {
		return zzpjt_id;
	}
	public void setZzpjt_id(String zzpjt_id) {
		this.zzpjt_id = zzpjt_id;
	}
	public String getRecad_da() {
		return recad_da;
	}
	public void setRecad_da(String recad_da) {
		this.recad_da = recad_da;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getZseq() {
		return zseq;
	}
	public void setZseq(String zseq) {
		this.zseq = zseq;
	}
	public String getBstkd() {
		return bstkd;
	}
	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getInco1() {
		return inco1;
	}
	public void setInco1(String inco1) {
		this.inco1 = inco1;
	}
	public String getInco2() {
		return inco2;
	}
	public void setInco2(String inco2) {
		this.inco2 = inco2;
	}
	public String getZtermt() {
		return ztermt;
	}
	public void setZtermt(String ztermt) {
		this.ztermt = ztermt;
	}
	public String getSpecdwg() {
		return specdwg;
	}
	public void setSpecdwg(String specdwg) {
		this.specdwg = specdwg;
	}
	public String getEdatu() {
		return edatu;
	}
	public void setEdatu(String edatu) {
		this.edatu = edatu;
	}
	public String getZfwding() {
		return zfwding;
	}
	public void setZfwding(String zfwding) {
		this.zfwding = zfwding;
	}
	public String getSedat() {
		return sedat;
	}
	public void setSedat(String sedat) {
		this.sedat = sedat;
	}
	public String getSezzt() {
		return sezzt;
	}
	public void setSezzt(String sezzt) {
		this.sezzt = sezzt;
	}
	public String getErdat() {
		return erdat;
	}
	public void setErdat(String erdat) {
		this.erdat = erdat;
	}
	public String getErzzt() {
		return erzzt;
	}
	public void setErzzt(String erzzt) {
		this.erzzt = erzzt;
	}
	public String getErnam() {
		return ernam;
	}
	public void setErnam(String ernam) {
		this.ernam = ernam;
	}
	public String getPosnr() {
		return posnr;
	}
	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}
	public String getPosid() {
		return posid;
	}
	public void setPosid(String posid) {
		this.posid = posid;
	}
	public String getWwmod() {
		return wwmod;
	}
	public void setWwmod(String wwmod) {
		this.wwmod = wwmod;
	}
	public String getArktx() {
		return arktx;
	}
	public void setArktx(String arktx) {
		this.arktx = arktx;
	}
	public String getKwmeng() {
		return kwmeng;
	}
	public void setKwmeng(String kwmeng) {
		this.kwmeng = kwmeng;
	}
	public String getVrkme() {
		return vrkme;
	}
	public void setVrkme(String vrkme) {
		this.vrkme = vrkme;
	}
	public String getNetwr() {
		return netwr;
	}
	public void setNetwr(String netwr) {
		this.netwr = netwr;
	}
	public String getWaerk() {
		return waerk;
	}
	public void setWaerk(String waerk) {
		this.waerk = waerk;
	}
	public String getCharacteristic() {
		return characteristic;
	}
	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}
	public String getCuobj() {
		return cuobj;
	}
	public void setCuobj(String cuobj) {
		this.cuobj = cuobj;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getEdatufr() {
		return edatufr;
	}
	public void setEdatufr(String edatufr) {
		this.edatufr = edatufr;
	}
	public String getEdatuto() {
		return edatuto;
	}
	public void setEdatuto(String edatuto) {
		this.edatuto = edatuto;
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
}
