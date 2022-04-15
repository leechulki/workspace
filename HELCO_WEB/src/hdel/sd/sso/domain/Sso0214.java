package hdel.sd.sso.domain;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Sso0214 extends CommonDomain {
	
	private String mandt;
	private String zzpjt_id;
	private String hogi;
	private String bstnk;
	private String name1;
	private String chakd;
	private String dpcpd;
	private String chkcol;
	private String chktpdate2;
	private String chktpdate3;
	private String pocdt;
	private String ptpdt;
	private String tpdate2;
	private String tpdate3;
	private String dealer;
	private String manager;
	private String sdfield;
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZzpjt_id() {
		return zzpjt_id;
	}
	public void setZzpjt_id(String zzpjt_id) {
		this.zzpjt_id = zzpjt_id;
	}
	public String getHogi() {
		return hogi;
	}
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}
	public String getBstnk() {
		return bstnk;
	}
	public void setBstnk(String bstnk) {
		this.bstnk = bstnk;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getChakd() {
		return chakd;
	}
	public void setChakd(String chakd) {
		this.chakd = chakd;
	}
	public String getDpcpd() {
		return dpcpd;
	}
	public void setDpcpd(String dpcpd) {
		this.dpcpd = dpcpd;
	}
	public String getChkcol() {
		return chkcol;
	}
	public void setChkcol(String chkcol) {
		this.chkcol = chkcol;
	}
	public String getChktpdate2() {
		return chktpdate2;
	}
	public void setChktpdate2(String chktpdate2) {
		this.chktpdate2 = chktpdate2;
	}
	public String getChktpdate3() {
		return chktpdate3;
	}
	public void setChktpdate3(String chktpdate3) {
		this.chktpdate3 = chktpdate3;
	}
	public String getPocdt() {
		return pocdt;
	}
	public void setPocdt(String pocdt) {
		this.pocdt = pocdt == "____.__.__" ? null : pocdt.replace(".", "");
	}
	public String getPtpdt() {
		return ptpdt;
	}
	public void setPtpdt(String ptpdt) {
		this.ptpdt = ptpdt == "____.__.__" ? null : ptpdt.replace(".", "");
	}
	public String getTpdate2() {
		return tpdate2;
	}
	public void setTpdate2(String tpdate2) {
		this.tpdate2 = tpdate2;
	}
	public String getTpdate3() {
		return tpdate3;
	}
	public void setTpdate3(String tpdate3) {
		this.tpdate3 = tpdate3;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getSdfield() {
		return sdfield;
	}
	public void setSdfield(String sdfield) {
		this.sdfield = sdfield;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
	
}
