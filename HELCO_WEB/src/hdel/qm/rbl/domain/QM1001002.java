package hdel.qm.rbl.domain;

import hdel.lib.domain.CommonDomain;

public class QM1001002 extends CommonDomain {

	// -- Master 시작
	private String nreqseq;
	private String reqseq;
	
	private String mandt;

	private String title;

	private String formgbn;
	private String statcd;

	private String goodsgbn;
	private String reqdt;
	private String reqgbn;
	private String reqnm;
	private String reqid;
	private String reqvendnm;
	private String reqcell;
	private String bfmatnr;
	private String ftreportcd;
	private String reqattdcd;
	private String techattdcd;
	private String testgbn;
	private String testgbn_etctxt;
	private String smpcnt;
	private String cpncd;
	private String cpnnm;
	private String smpqlt;
	private String lawrq_code;
	private String lawrq_chklstyn;
	private String lawrq_contyn;
	private String lawrq_nayn;
	private String att_cnfrmyn;
	private String att_specyn;
	private String att_bpyn;
	private String att_chklstyn;
	private String att_paxyn;
	private String att_kcsyn;
	private String att_ftflowyn;
	private String att_strptyn;
	private String att_imprptyn;
	private String att_nayn;
	private String hogi;
	private String spec;
	private String ctestymd;
	private String bosucontact;
	private String etc;
	private String thogi;
	private String atype;
	private String ause;
	private String pcnt;
	private String capa;
	private String stcnt;
	private String dometer;
	private String abyn;
	private String tmspec;
	private String console;
	private String inverter;
	private String rope_mg;
	private String roping;
	private String compenchain;
	private String rail_cc;
	private String guideshoe_car;
	private String guideshoe_cwt;
	private String brakespec;
	private String regulator;
	private String stopeqp;

	private String velocity;

	private String testernm;
	private String testerid;
	private String testercell;
	private String rst;
	private String rctdt;
	private String testsymd;
	private String testpymd;
	private String testdymd;
	private String jigcost;
	private String trvcost;
	private String trscost;
	private String testcost;
	private String totalcost;
	private String chnsrsn;
	private String nfllwrsn;
	private String rtnrsn;
	private String bigo;

	private String testrev;
	private String otestrev;

	private String rst_filenm;
	private String rst_filepath;
	private String rst_filenm_app;
	
	private String imp_filenm;
	private String imp_filepath;
	private String imp_filenm_app;
	
	private String blockcd;
	
	private String app_pyn;
	private String end_docu_no;
	private String positionnm;

	// -- Master 끝

	private String useyn;
	private String creid;
	private String credt;
	private String modid;
	private String moddt;

	// -- 신뢰성시험의뢰시험항목 시작
	// private String reqseq;
	private String testseq;
	private String testnm;
	private String testcont1;
	private String testcont2;
	// -- 신뢰성시험의뢰시험항목 끝

	// -- 개발항목 시작
	private String rndseq;
	private String goodsnm;
	private String modelnm;
	private String rndcont;

	// -- 개발항목 끝

	// -- File 시작
	private String flag;

	// private String reqseq;
	private String fileseq;
	private String filenm;
	private String filepath;
	private String filenm_app;
	private String filesize;
	// -- File 끝


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
	 * @return the nreqseq
	 */
	public String getNreqseq() {
		return nreqseq;
	}

	/**
	 * @param nreqseq
	 *            the nreqseq to set
	 */
	public void setNreqseq(String nreqseq) {
		this.nreqseq = nreqseq;
	}

	/**
	 * @return the reqseq
	 */
	public String getReqseq() {
		return reqseq;
	}

	/**
	 * @param reqseq
	 *            the reqseq to set
	 */
	public void setReqseq(String reqseq) {
		this.reqseq = reqseq;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the formgbn
	 */
	public String getFormgbn() {
		return formgbn;
	}

	/**
	 * @param formgbn
	 *            the formgbn to set
	 */
	public void setFormgbn(String formgbn) {
		this.formgbn = formgbn;
	}

	/**
	 * @return the goodsgbn
	 */
	public String getGoodsgbn() {
		return goodsgbn;
	}

	/**
	 * @param goodsgbn
	 *            the goodsgbn to set
	 */
	public void setGoodsgbn(String goodsgbn) {
		this.goodsgbn = goodsgbn;
	}

	/**
	 * @return the reqdt
	 */
	public String getReqdt() {
		return reqdt;
	}

	/**
	 * @param reqdt
	 *            the reqdt to set
	 */
	public void setReqdt(String reqdt) {
		this.reqdt = reqdt;
	}

	/**
	 * @return the reqgbn
	 */
	public String getReqgbn() {
		return reqgbn;
	}

	/**
	 * @param reqgbn
	 *            the reqgbn to set
	 */
	public void setReqgbn(String reqgbn) {
		this.reqgbn = reqgbn;
	}

	/**
	 * @return the reqvendnm
	 */
	public String getReqvendnm() {
		return reqvendnm;
	}

	/**
	 * @param reqvendnm
	 *            the reqvendnm to set
	 */
	public void setReqvendnm(String reqvendnm) {
		this.reqvendnm = reqvendnm;
	}

	/**
	 * @return the reqcell
	 */
	public String getReqcell() {
		return reqcell;
	}

	/**
	 * @param reqcell
	 *            the reqcell to set
	 */
	public void setReqcell(String reqcell) {
		this.reqcell = reqcell;
	}

	/**
	 * @return the bfmatnr
	 */
	public String getBfmatnr() {
		return bfmatnr;
	}

	/**
	 * @param bfmatnr
	 *            the bfmatnr to set
	 */
	public void setBfmatnr(String bfmatnr) {
		this.bfmatnr = bfmatnr;
	}

	/**
	 * @return the ftreportcd
	 */
	public String getFtreportcd() {
		return ftreportcd;
	}

	/**
	 * @param ftreportcd
	 *            the ftreportcd to set
	 */
	public void setFtreportcd(String ftreportcd) {
		this.ftreportcd = ftreportcd;
	}

	/**
	 * @return the reqattdcd
	 */
	public String getReqattdcd() {
		return reqattdcd;
	}

	/**
	 * @param reqattdcd
	 *            the reqattdcd to set
	 */
	public void setReqattdcd(String reqattdcd) {
		this.reqattdcd = reqattdcd;
	}

	/**
	 * @return the techattdcd
	 */
	public String getTechattdcd() {
		return techattdcd;
	}

	/**
	 * @param techattdcd
	 *            the techattdcd to set
	 */
	public void setTechattdcd(String techattdcd) {
		this.techattdcd = techattdcd;
	}

	/**
	 * @return the testgbn
	 */
	public String getTestgbn() {
		return testgbn;
	}

	/**
	 * @param testgbn
	 *            the testgbn to set
	 */
	public void setTestgbn(String testgbn) {
		this.testgbn = testgbn;
	}

	/**
	 * @return the testgbn_etctxt
	 */
	public String getTestgbn_etctxt() {
		return testgbn_etctxt;
	}

	/**
	 * @param testgbn_etctxt
	 *            the testgbn_etctxt to set
	 */
	public void setTestgbn_etctxt(String testgbn_etctxt) {
		this.testgbn_etctxt = testgbn_etctxt;
	}

	/**
	 * @return the smpcnt
	 */
	public String getSmpcnt() {
		return smpcnt;
	}

	/**
	 * @param smpcnt
	 *            the smpcnt to set
	 */
	public void setSmpcnt(String smpcnt) {
		this.smpcnt = smpcnt;
	}

	/**
	 * @return the cpncd
	 */
	public String getCpncd() {
		return cpncd;
	}

	/**
	 * @param cpncd
	 *            the cpncd to set
	 */
	public void setCpncd(String cpncd) {
		this.cpncd = cpncd;
	}

	/**
	 * @return the cpnnm
	 */
	public String getCpnnm() {
		return cpnnm;
	}

	/**
	 * @param cpnnm
	 *            the cpnnm to set
	 */
	public void setCpnnm(String cpnnm) {
		this.cpnnm = cpnnm;
	}

	/**
	 * @return the smpqlt
	 */
	public String getSmpqlt() {
		return smpqlt;
	}

	/**
	 * @param smpqlt
	 *            the smpqlt to set
	 */
	public void setSmpqlt(String smpqlt) {
		this.smpqlt = smpqlt;
	}

	/**
	 * @return the lawrq_code
	 */
	public String getLawrq_code() {
		return lawrq_code;
	}

	/**
	 * @param lawrq_code
	 *            the lawrq_code to set
	 */
	public void setLawrq_code(String lawrq_code) {
		this.lawrq_code = lawrq_code;
	}

	/**
	 * @return the lawrq_chklstyn
	 */
	public String getLawrq_chklstyn() {
		return lawrq_chklstyn;
	}

	/**
	 * @param lawrq_chklstyn
	 *            the lawrq_chklstyn to set
	 */
	public void setLawrq_chklstyn(String lawrq_chklstyn) {
		this.lawrq_chklstyn = lawrq_chklstyn;
	}

	/**
	 * @return the lawrq_contyn
	 */
	public String getLawrq_contyn() {
		return lawrq_contyn;
	}

	/**
	 * @param lawrq_contyn
	 *            the lawrq_contyn to set
	 */
	public void setLawrq_contyn(String lawrq_contyn) {
		this.lawrq_contyn = lawrq_contyn;
	}

	/**
	 * @return the lawrq_nayn
	 */
	public String getLawrq_nayn() {
		return lawrq_nayn;
	}

	/**
	 * @param lawrq_nayn
	 *            the lawrq_nayn to set
	 */
	public void setLawrq_nayn(String lawrq_nayn) {
		this.lawrq_nayn = lawrq_nayn;
	}

	/**
	 * @return the att_cnfrmyn
	 */
	public String getAtt_cnfrmyn() {
		return att_cnfrmyn;
	}

	/**
	 * @param att_cnfrmyn
	 *            the att_cnfrmyn to set
	 */
	public void setAtt_cnfrmyn(String att_cnfrmyn) {
		this.att_cnfrmyn = att_cnfrmyn;
	}

	/**
	 * @return the att_specyn
	 */
	public String getAtt_specyn() {
		return att_specyn;
	}

	/**
	 * @param att_specyn
	 *            the att_specyn to set
	 */
	public void setAtt_specyn(String att_specyn) {
		this.att_specyn = att_specyn;
	}

	/**
	 * @return the att_bpyn
	 */
	public String getAtt_bpyn() {
		return att_bpyn;
	}

	/**
	 * @param att_bpyn
	 *            the att_bpyn to set
	 */
	public void setAtt_bpyn(String att_bpyn) {
		this.att_bpyn = att_bpyn;
	}

	/**
	 * @return the att_chklstyn
	 */
	public String getAtt_chklstyn() {
		return att_chklstyn;
	}

	/**
	 * @param att_chklstyn
	 *            the att_chklstyn to set
	 */
	public void setAtt_chklstyn(String att_chklstyn) {
		this.att_chklstyn = att_chklstyn;
	}

	/**
	 * @return the att_paxyn
	 */
	public String getAtt_paxyn() {
		return att_paxyn;
	}

	/**
	 * @param att_paxyn
	 *            the att_paxyn to set
	 */
	public void setAtt_paxyn(String att_paxyn) {
		this.att_paxyn = att_paxyn;
	}

	/**
	 * @return the att_kcsyn
	 */
	public String getAtt_kcsyn() {
		return att_kcsyn;
	}

	/**
	 * @param att_kcsyn
	 *            the att_kcsyn to set
	 */
	public void setAtt_kcsyn(String att_kcsyn) {
		this.att_kcsyn = att_kcsyn;
	}

	/**
	 * @return the att_ftflowyn
	 */
	public String getAtt_ftflowyn() {
		return att_ftflowyn;
	}

	/**
	 * @param att_ftflowyn
	 *            the att_ftflowyn to set
	 */
	public void setAtt_ftflowyn(String att_ftflowyn) {
		this.att_ftflowyn = att_ftflowyn;
	}

	/**
	 * @return the att_strptyn
	 */
	public String getAtt_strptyn() {
		return att_strptyn;
	}

	/**
	 * @param att_strptyn
	 *            the att_strptyn to set
	 */
	public void setAtt_strptyn(String att_strptyn) {
		this.att_strptyn = att_strptyn;
	}

	/**
	 * @return the att_imprptyn
	 */
	public String getAtt_imprptyn() {
		return att_imprptyn;
	}

	/**
	 * @param att_imprptyn
	 *            the att_imprptyn to set
	 */
	public void setAtt_imprptyn(String att_imprptyn) {
		this.att_imprptyn = att_imprptyn;
	}

	/**
	 * @return the att_nayn
	 */
	public String getAtt_nayn() {
		return att_nayn;
	}

	/**
	 * @param att_nayn
	 *            the att_nayn to set
	 */
	public void setAtt_nayn(String att_nayn) {
		this.att_nayn = att_nayn;
	}

	/**
	 * @return the hogi
	 */
	public String getHogi() {
		return hogi;
	}

	/**
	 * @param hogi
	 *            the hogi to set
	 */
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}

	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * @param spec
	 *            the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * @return the ctestymd
	 */
	public String getCtestymd() {
		return ctestymd;
	}

	/**
	 * @param ctestymd
	 *            the ctestymd to set
	 */
	public void setCtestymd(String ctestymd) {
		this.ctestymd = ctestymd;
	}

	/**
	 * @return the bosucontact
	 */
	public String getBosucontact() {
		return bosucontact;
	}

	/**
	 * @param bosucontact
	 *            the bosucontact to set
	 */
	public void setBosucontact(String bosucontact) {
		this.bosucontact = bosucontact;
	}

	/**
	 * @return the etc
	 */
	public String getEtc() {
		return etc;
	}

	/**
	 * @param etc
	 *            the etc to set
	 */
	public void setEtc(String etc) {
		this.etc = etc;
	}

	/**
	 * @return the thogi
	 */
	public String getThogi() {
		return thogi;
	}

	/**
	 * @param thogi
	 *            the thogi to set
	 */
	public void setThogi(String thogi) {
		this.thogi = thogi;
	}

	/**
	 * @return the atype
	 */
	public String getAtype() {
		return atype;
	}

	/**
	 * @param atype
	 *            the atype to set
	 */
	public void setAtype(String atype) {
		this.atype = atype;
	}

	/**
	 * @return the ause
	 */
	public String getAuse() {
		return ause;
	}

	/**
	 * @param ause
	 *            the ause to set
	 */
	public void setAuse(String ause) {
		this.ause = ause;
	}

	/**
	 * @return the pcnt
	 */
	public String getPcnt() {
		return pcnt;
	}

	/**
	 * @param pcnt
	 *            the pcnt to set
	 */
	public void setPcnt(String pcnt) {
		this.pcnt = pcnt;
	}

	/**
	 * @return the capa
	 */
	public String getCapa() {
		return capa;
	}

	/**
	 * @param capa
	 *            the capa to set
	 */
	public void setCapa(String capa) {
		this.capa = capa;
	}

	/**
	 * @return the stcnt
	 */
	public String getStcnt() {
		return stcnt;
	}

	/**
	 * @param stcnt
	 *            the stcnt to set
	 */
	public void setStcnt(String stcnt) {
		this.stcnt = stcnt;
	}

	/**
	 * @return the dometer
	 */
	public String getDometer() {
		return dometer;
	}

	/**
	 * @param dometer
	 *            the dometer to set
	 */
	public void setDometer(String dometer) {
		this.dometer = dometer;
	}

	/**
	 * @return the abyn
	 */
	public String getAbyn() {
		return abyn;
	}

	/**
	 * @param abyn
	 *            the abyn to set
	 */
	public void setAbyn(String abyn) {
		this.abyn = abyn;
	}

	/**
	 * @return the tmspec
	 */
	public String getTmspec() {
		return tmspec;
	}

	/**
	 * @param tmspec
	 *            the tmspec to set
	 */
	public void setTmspec(String tmspec) {
		this.tmspec = tmspec;
	}

	/**
	 * @return the console
	 */
	public String getConsole() {
		return console;
	}

	/**
	 * @param console
	 *            the console to set
	 */
	public void setConsole(String console) {
		this.console = console;
	}

	/**
	 * @return the inverter
	 */
	public String getInverter() {
		return inverter;
	}

	/**
	 * @param inverter
	 *            the inverter to set
	 */
	public void setInverter(String inverter) {
		this.inverter = inverter;
	}

	/**
	 * @return the rope_mg
	 */
	public String getRope_mg() {
		return rope_mg;
	}

	/**
	 * @param rope_mg
	 *            the rope_mg to set
	 */
	public void setRope_mg(String rope_mg) {
		this.rope_mg = rope_mg;
	}

	/**
	 * @return the roping
	 */
	public String getRoping() {
		return roping;
	}

	/**
	 * @param roping
	 *            the roping to set
	 */
	public void setRoping(String roping) {
		this.roping = roping;
	}

	/**
	 * @return the compenchain
	 */
	public String getCompenchain() {
		return compenchain;
	}

	/**
	 * @param compenchain
	 *            the compenchain to set
	 */
	public void setCompenchain(String compenchain) {
		this.compenchain = compenchain;
	}

	/**
	 * @return the rail_cc
	 */
	public String getRail_cc() {
		return rail_cc;
	}

	/**
	 * @param rail_cc
	 *            the rail_cc to set
	 */
	public void setRail_cc(String rail_cc) {
		this.rail_cc = rail_cc;
	}

	/**
	 * @return the guideshoe_car
	 */
	public String getGuideshoe_car() {
		return guideshoe_car;
	}

	/**
	 * @param guideshoe_car
	 *            the guideshoe_car to set
	 */
	public void setGuideshoe_car(String guideshoe_car) {
		this.guideshoe_car = guideshoe_car;
	}

	/**
	 * @return the guideshoe_cwt
	 */
	public String getGuideshoe_cwt() {
		return guideshoe_cwt;
	}

	/**
	 * @param guideshoe_cwt
	 *            the guideshoe_cwt to set
	 */
	public void setGuideshoe_cwt(String guideshoe_cwt) {
		this.guideshoe_cwt = guideshoe_cwt;
	}

	/**
	 * @return the brakespec
	 */
	public String getBrakespec() {
		return brakespec;
	}

	/**
	 * @param brakespec
	 *            the brakespec to set
	 */
	public void setBrakespec(String brakespec) {
		this.brakespec = brakespec;
	}

	/**
	 * @return the regulator
	 */
	public String getRegulator() {
		return regulator;
	}

	/**
	 * @param regulator
	 *            the regulator to set
	 */
	public void setRegulator(String regulator) {
		this.regulator = regulator;
	}

	/**
	 * @return the stopeqp
	 */
	public String getStopeqp() {
		return stopeqp;
	}

	/**
	 * @param stopeqp
	 *            the stopeqp to set
	 */
	public void setStopeqp(String stopeqp) {
		this.stopeqp = stopeqp;
	}

	/**
	 * @return the testernm
	 */
	public String getTesternm() {
		return testernm;
	}

	/**
	 * @param testernm
	 *            the testernm to set
	 */
	public void setTesternm(String testernm) {
		this.testernm = testernm;
	}

	/**
	 * @return the testercell
	 */
	public String getTestercell() {
		return testercell;
	}

	/**
	 * @param testercell
	 *            the testercell to set
	 */
	public void setTestercell(String testercell) {
		this.testercell = testercell;
	}

	/**
	 * @return the rst
	 */
	public String getRst() {
		return rst;
	}

	/**
	 * @param rst
	 *            the rst to set
	 */
	public void setRst(String rst) {
		this.rst = rst;
	}

	/**
	 * @return the testsymd
	 */
	public String getTestsymd() {
		return testsymd;
	}

	/**
	 * @param testsymd
	 *            the testsymd to set
	 */
	public void setTestsymd(String testsymd) {
		this.testsymd = testsymd;
	}

	/**
	 * @return the testpymd
	 */
	public String getTestpymd() {
		return testpymd;
	}

	/**
	 * @param testpymd
	 *            the testpymd to set
	 */
	public void setTestpymd(String testpymd) {
		this.testpymd = testpymd;
	}

	/**
	 * @return the testdymd
	 */
	public String getTestdymd() {
		return testdymd;
	}

	/**
	 * @param testdymd
	 *            the testdymd to set
	 */
	public void setTestdymd(String testdymd) {
		this.testdymd = testdymd;
	}

	/**
	 * @return the jigcost
	 */
	public String getJigcost() {
		return jigcost;
	}

	/**
	 * @param jigcost
	 *            the jigcost to set
	 */
	public void setJigcost(String jigcost) {
		this.jigcost = jigcost;
	}

	/**
	 * @return the trvcost
	 */
	public String getTrvcost() {
		return trvcost;
	}

	/**
	 * @param trvcost
	 *            the trvcost to set
	 */
	public void setTrvcost(String trvcost) {
		this.trvcost = trvcost;
	}

	/**
	 * @return the trscost
	 */
	public String getTrscost() {
		return trscost;
	}

	/**
	 * @param trscost
	 *            the trscost to set
	 */
	public void setTrscost(String trscost) {
		this.trscost = trscost;
	}

	/**
	 * @return the testcost
	 */
	public String getTestcost() {
		return testcost;
	}

	/**
	 * @param testcost
	 *            the testcost to set
	 */
	public void setTestcost(String testcost) {
		this.testcost = testcost;
	}

	/**
	 * @return the totalcost
	 */
	public String getTotalcost() {
		return totalcost;
	}

	/**
	 * @param totalcost
	 *            the totalcost to set
	 */
	public void setTotalcost(String totalcost) {
		this.totalcost = totalcost;
	}

	/**
	 * @return the chnsrsn
	 */
	public String getChnsrsn() {
		return chnsrsn;
	}

	/**
	 * @param chnsrsn
	 *            the chnsrsn to set
	 */
	public void setChnsrsn(String chnsrsn) {
		this.chnsrsn = chnsrsn;
	}

	/**
	 * @return the nfllwrsn
	 */
	public String getNfllwrsn() {
		return nfllwrsn;
	}

	/**
	 * @param nfllwrsn
	 *            the nfllwrsn to set
	 */
	public void setNfllwrsn(String nfllwrsn) {
		this.nfllwrsn = nfllwrsn;
	}

	/**
	 * @return the rtnrsn
	 */
	public String getRtnrsn() {
		return rtnrsn;
	}

	/**
	 * @param rtnrsn
	 *            the rtnrsn to set
	 */
	public void setRtnrsn(String rtnrsn) {
		this.rtnrsn = rtnrsn;
	}

	/**
	 * @return the bigo
	 */
	public String getBigo() {
		return bigo;
	}

	/**
	 * @param bigo
	 *            the bigo to set
	 */
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}

	/**
	 * @return the useyn
	 */
	public String getUseyn() {
		return useyn;
	}

	/**
	 * @param useyn
	 *            the useyn to set
	 */
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	/**
	 * @return the credt
	 */
	public String getCredt() {
		return credt;
	}

	/**
	 * @param credt
	 *            the credt to set
	 */
	public void setCredt(String credt) {
		this.credt = credt;
	}

	/**
	 * @return the moddt
	 */
	public String getModdt() {
		return moddt;
	}

	/**
	 * @param moddt
	 *            the moddt to set
	 */
	public void setModdt(String moddt) {
		this.moddt = moddt;
	}

	/**
	 * @return the testseq
	 */
	public String getTestseq() {
		return testseq;
	}

	/**
	 * @param testseq
	 *            the testseq to set
	 */
	public void setTestseq(String testseq) {
		this.testseq = testseq;
	}

	/**
	 * @return the testnm
	 */
	public String getTestnm() {
		return testnm;
	}

	/**
	 * @param testnm
	 *            the testnm to set
	 */
	public void setTestnm(String testnm) {
		this.testnm = testnm;
	}

	/**
	 * @return the testcont1
	 */
	public String getTestcont1() {
		return testcont1;
	}

	/**
	 * @param testcont1
	 *            the testcont1 to set
	 */
	public void setTestcont1(String testcont1) {
		this.testcont1 = testcont1;
	}

	/**
	 * @return the testcont2
	 */
	public String getTestcont2() {
		return testcont2;
	}

	/**
	 * @param testcont2
	 *            the testcont2 to set
	 */
	public void setTestcont2(String testcont2) {
		this.testcont2 = testcont2;
	}

	/**
	 * @return the rndseq
	 */
	public String getRndseq() {
		return rndseq;
	}

	/**
	 * @param rndseq
	 *            the rndseq to set
	 */
	public void setRndseq(String rndseq) {
		this.rndseq = rndseq;
	}

	/**
	 * @return the goodsnm
	 */
	public String getGoodsnm() {
		return goodsnm;
	}

	/**
	 * @param goodsnm
	 *            the goodsnm to set
	 */
	public void setGoodsnm(String goodsnm) {
		this.goodsnm = goodsnm;
	}

	/**
	 * @return the modelnm
	 */
	public String getModelnm() {
		return modelnm;
	}

	/**
	 * @param modelnm
	 *            the modelnm to set
	 */
	public void setModelnm(String modelnm) {
		this.modelnm = modelnm;
	}

	/**
	 * @return the rndcont
	 */
	public String getRndcont() {
		return rndcont;
	}

	/**
	 * @param rndcont
	 *            the rndcont to set
	 */
	public void setRndcont(String rndcont) {
		this.rndcont = rndcont;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the fileseq
	 */
	public String getFileseq() {
		return fileseq;
	}

	/**
	 * @param fileseq
	 *            the fileseq to set
	 */
	public void setFileseq(String fileseq) {
		this.fileseq = fileseq;
	}

	/**
	 * @return the filenm
	 */
	public String getFilenm() {
		return filenm;
	}

	/**
	 * @param filenm
	 *            the filenm to set
	 */
	public void setFilenm(String filenm) {
		this.filenm = filenm;
	}

	/**
	 * @return the filepath
	 */
	public String getFilepath() {
		return filepath;
	}

	/**
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	/**
	 * @return the filenm_app
	 */
	public String getFilenm_app() {
		return filenm_app;
	}

	/**
	 * @param filenm_app
	 *            the filenm_app to set
	 */
	public void setFilenm_app(String filenm_app) {
		this.filenm_app = filenm_app;
	}

	/**
	 * @return the filesize
	 */
	public String getFilesize() {
		return filesize;
	}

	/**
	 * @param filesize
	 *            the filesize to set
	 */
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	/**
	 * @return the testrev
	 */
	public String getTestrev() {
		return testrev;
	}

	/**
	 * @param testrev
	 *            the testrev to set
	 */
	public void setTestrev(String testrev) {
		this.testrev = testrev;
	}

	/**
	 * @return the otestrev
	 */
	public String getOtestrev() {
		return otestrev;
	}

	/**
	 * @param otestrev
	 *            the otestrev to set
	 */
	public void setOtestrev(String otestrev) {
		this.otestrev = otestrev;
	}

	/**
	 * @return the rst_filenm
	 */
	public String getRst_filenm() {
		return rst_filenm;
	}

	/**
	 * @param rst_filenm
	 *            the rst_filenm to set
	 */
	public void setRst_filenm(String rst_filenm) {
		this.rst_filenm = rst_filenm;
	}

	/**
	 * @return the rst_filepath
	 */
	public String getRst_filepath() {
		return rst_filepath;
	}

	/**
	 * @param rst_filepath
	 *            the rst_filepath to set
	 */
	public void setRst_filepath(String rst_filepath) {
		this.rst_filepath = rst_filepath;
	}

	/**
	 * @return the rst_filenm_app
	 */
	public String getRst_filenm_app() {
		return rst_filenm_app;
	}

	/**
	 * @param rst_filenm_app
	 *            the rst_filenm_app to set
	 */
	public void setRst_filenm_app(String rst_filenm_app) {
		this.rst_filenm_app = rst_filenm_app;
	}

	/**
	 * @return the imp_filenm
	 */
	public String getImp_filenm() {
		return imp_filenm;
	}

	/**
	 * @param imp_filenm the imp_filenm to set
	 */
	public void setImp_filenm(String imp_filenm) {
		this.imp_filenm = imp_filenm;
	}

	/**
	 * @return the imp_filepath
	 */
	public String getImp_filepath() {
		return imp_filepath;
	}

	/**
	 * @param imp_filepath the imp_filepath to set
	 */
	public void setImp_filepath(String imp_filepath) {
		this.imp_filepath = imp_filepath;
	}

	/**
	 * @return the imp_filenm_app
	 */
	public String getImp_filenm_app() {
		return imp_filenm_app;
	}

	/**
	 * @param imp_filenm_app the imp_filenm_app to set
	 */
	public void setImp_filenm_app(String imp_filenm_app) {
		this.imp_filenm_app = imp_filenm_app;
	}

	/**
	 * @return the reqnm
	 */
	public String getReqnm() {
		return reqnm;
	}

	/**
	 * @param reqnm
	 *            the reqnm to set
	 */
	public void setReqnm(String reqnm) {
		this.reqnm = reqnm;
	}

	/**
	 * @return the reqid
	 */
	public String getReqid() {
		return reqid;
	}

	/**
	 * @param reqid
	 *            the reqid to set
	 */
	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	/**
	 * @return the testerid
	 */
	public String getTesterid() {
		return testerid;
	}

	/**
	 * @param testerid
	 *            the testerid to set
	 */
	public void setTesterid(String testerid) {
		this.testerid = testerid;
	}

	/**
	 * @return the creid
	 */
	public String getCreid() {
		return creid;
	}

	/**
	 * @param creid
	 *            the creid to set
	 */
	public void setCreid(String creid) {
		this.creid = creid;
	}

	/**
	 * @return the modid
	 */
	public String getModid() {
		return modid;
	}

	/**
	 * @param modid
	 *            the modid to set
	 */
	public void setModid(String modid) {
		this.modid = modid;
	}

	/**
	 * @return the statcd
	 */
	public String getStatcd() {
		return statcd;
	}

	/**
	 * @param statcd
	 *            the statcd to set
	 */
	public void setStatcd(String statcd) {
		this.statcd = statcd;
	}

	/**
	 * @return the rctdt
	 */
	public String getRctdt() {
		return rctdt;
	}

	/**
	 * @param rctdt
	 *            the rctdt to set
	 */
	public void setRctdt(String rctdt) {
		this.rctdt = rctdt;
	}

	/**
	 * @return the velocity
	 */
	public String getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(String velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the blockcd
	 */
	public String getBlockcd() {
		return blockcd;
	}

	/**
	 * @param blockcd the blockcd to set
	 */
	public void setBlockcd(String blockcd) {
		this.blockcd = blockcd;
	}

	/**
	 * @return the app_pyn
	 */
	public String getApp_pyn() {
		return app_pyn;
	}

	/**
	 * @param app_pyn the app_pyn to set
	 */
	public void setApp_pyn(String app_pyn) {
		this.app_pyn = app_pyn;
	}

	/**
	 * @return the end_docu_no
	 */
	public String getEnd_docu_no() {
		return end_docu_no;
	}

	/**
	 * @param end_docu_no the end_docu_no to set
	 */
	public void setEnd_docu_no(String end_docu_no) {
		this.end_docu_no = end_docu_no;
	}

	/**
	 * @return the positionnm
	 */
	public String getPositionnm() {
		return positionnm;
	}

	/**
	 * @param positionnm the positionnm to set
	 */
	public void setPositionnm(String positionnm) {
		this.positionnm = positionnm;
	}

}