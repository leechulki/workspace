package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

@SuppressWarnings("serial")
public class Ses0330 extends CommonDomain {
 
	private String  mandt;				//mandt
	private String  vbeln; 				//����������ȣ
	private String  zseq;  				//ȸ��
	private String  bstkd;       //�����
	private String  region;      //����
	private String  inco1;       //�ε�����:��Ʈ1
	private String  inco2;       //�ε�����:��Ʈ2
	private String  ztermt;      //�������ǳ���
	private String  specdwg;		 //����
	private String  edatu;       //��������
	private String  edatufr;       //��������fr
	private String  edatuto;       //��������to
	private String  zfwding;		 //������
	private String  sedat;       //�������۽�����
	private String  sezzt;       //�������۽ð�
	private String  erdat;       //��������
	private String  erzzt;       //�����ð�
	private String  ernam;       //������
	
	private String  posnr;       //ǰ���ȣ
	private String  posid;       //ȣ���ȣ
	private String  wwmod;    		//����
	private String  arktx;       //���
	private String  kwmeng;      //�ǸŴ��
	private String  vrkme;       //�ǸŴ���
	private String  netwr;       //�Ǹűݾ�
	private String  waerk;       //��ȭ����

	private String  characteristic;		//Ư���̸�
	private String  characteristicnm;		//Ư���̸�
	private String  cuobj;       //����
	private String  value;       //Ư����
	
	private String  flag;       //flag	
	private String  recad_da;       //��������	
	
	private String zzpjt_id;		// �����ȣ proj.no.
	private String bstnk;			// �����
	private String land1;			// �����ڵ�
	private String landx;			// ������
	private String vkbur;			// �μ��ڵ�
	private String burbezei;		// �μ���
	private String vkgrp;			// ���ڵ�
	private String grpbezei;		// ����
	private String zkunnr;			// ������ڵ�
	private String name1;			// ����ڸ�
	private String kzwi5;			// ��������
	private String shnetwr;			// ���ֱݾ�-����
	private String shwaerk;			// ��ȭ����-����
	private String mailaddr;		// ������ ����� ���� �ּ�
	private String acdat;			// ��������
	private String remark1;			// ���1
	private String remark2;			// ���2
	private String remark3;			// ���3
	private String remark4;			// ���4
	private String remark5;			// ���5
	private String atnam;			// Ư���ڵ�
	private String atbez;			// Ư������
	private String atwrt;			// Ư����
	private String atwtb;			// Ư��������
	
	
	public String getAtnam() {
		return atnam;
	}
	public void setAtnam(String atnam) {
		this.atnam = atnam;
	}
	public String getAtbez() {
		return atbez;
	}
	public void setAtbez(String atbez) {
		this.atbez = atbez;
	}
	public String getAtwrt() {
		return atwrt;
	}
	public void setAtwrt(String atwrt) {
		this.atwrt = atwrt;
	}
	public String getAtwtb() {
		return atwtb;
	}
	public void setAtwtb(String atwtb) {
		this.atwtb = atwtb;
	}
	public String getZzpjt_id() {
		return zzpjt_id;
	}
	public void setZzpjt_id(String zzpjt_id) {
		this.zzpjt_id = zzpjt_id;
	}
	public String getBstnk() {
		return bstnk;
	}
	public void setBstnk(String bstnk) {
		this.bstnk = bstnk;
	}
	public String getLand1() {
		return land1;
	}
	public void setLand1(String land1) {
		this.land1 = land1;
	}
	public String getLandx() {
		return landx;
	}
	public void setLandx(String landx) {
		this.landx = landx;
	}
	public String getVkbur() {
		return vkbur;
	}
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}
	public String getBurbezei() {
		return burbezei;
	}
	public void setBurbezei(String burbezei) {
		this.burbezei = burbezei;
	}
	public String getVkgrp() {
		return vkgrp;
	}
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}
	public String getGrpbezei() {
		return grpbezei;
	}
	public void setGrpbezei(String grpbezei) {
		this.grpbezei = grpbezei;
	}
	public String getZkunnr() {
		return zkunnr;
	}
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getKzwi5() {
		return kzwi5;
	}
	public void setKzwi5(String kzwi5) {
		this.kzwi5 = kzwi5;
	}
	public String getShnetwr() {
		return shnetwr;
	}
	public void setShnetwr(String shnetwr) {
		this.shnetwr = shnetwr;
	}
	public String getShwaerk() {
		return shwaerk;
	}
	public void setShwaerk(String shwaerk) {
		this.shwaerk = shwaerk;
	}
	public String getMailaddr() {
		return mailaddr;
	}
	public void setMailaddr(String mailaddr) {
		this.mailaddr = mailaddr;
	}
	public String getAcdat() {
		return acdat;
	}
	public void setAcdat(String acdat) {
		this.acdat = acdat;
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
	
	public String getCharacteristicnm() {
		return characteristicnm;
	}
	public void setCharacteristicnm(String characteristicnm) {
		this.characteristicnm = characteristicnm;
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

	
}
