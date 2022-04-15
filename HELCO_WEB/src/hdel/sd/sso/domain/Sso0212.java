package hdel.sd.sso.domain;

import java.math.BigDecimal;

import org.apache.commons.lang.math.NumberUtils;

import com.sap.domain.Datum;
import com.sap.domain.Kunnr;
import com.sap.domain.Vbeln;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Sso0212 extends CommonDomain {
 
	private static final long serialVersionUID = 1L;

	private String mandt;
	private Datum frRecadda = new Datum();
	private Datum toRecadda = new Datum();
	private String frVkbur;
	private String toVkbur;
	private String frVkgrp;
	private String toVkgrp;
	private String bstdk;
	private String contr_da;
	private String vdatu;
	private String bstnk;
	private Kunnr knrZ2 = new Kunnr();
	private String knrZ2Nm;
	private Kunnr knrAg = new Kunnr();
	private String knrAgNm;
	private BigDecimal netwrSO;
	private BigDecimal mwsbp;
	private BigDecimal hwbas29;
	private BigDecimal dmbtr29;
	private Integer zqnty;
	private Float cpb;
	private Float kbetr;
	private BigDecimal kzwi4;
	private BigDecimal tampa;
	private BigDecimal balance;
	private BigDecimal stake;
	private String descr;
	private BigDecimal kwert;
	private BigDecimal adjKwert;
	private Vbeln vbeln = new Vbeln();
	private String abstk;
	private Boolean posted = false;
	private Datum aedat = new Datum();
	private String aenam;
	private String username;
	private Boolean exists128;

	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public Datum getFrRecadda() {
		return frRecadda;
	}
	public void setFrRecadda(Datum frRecadda) {
		this.frRecadda = frRecadda;
	}
	public Datum getToRecadda() {
		return toRecadda;
	}
	public void setToRecadda(Datum toRecadda) {
		this.toRecadda = toRecadda;
	}
	public String getFrVkbur() {
		return frVkbur;
	}
	public void setFrVkbur(String frVkbur) {
		this.frVkbur = frVkbur;
	}
	public String getToVkbur() {
		return toVkbur;
	}
	public void setToVkbur(String toVkbur) {
		this.toVkbur = toVkbur;
	}
	public String getFrVkgrp() {
		return frVkgrp;
	}
	public void setFrVkgrp(String frVkgrp) {
		this.frVkgrp = frVkgrp;
	}
	public String getToVkgrp() {
		return toVkgrp;
	}
	public void setToVkgrp(String toVkgrp) {
		this.toVkgrp = toVkgrp;
	}
	public String getBstnk() {
		return bstnk;
	}
	public void setBstnk(String bstnk) {
		this.bstnk = bstnk;
	}
	public Kunnr getKnrZ2() {
		return knrZ2;
	}
	public void setKnrZ2(Kunnr knrZ2) {
		this.knrZ2 = knrZ2;
	}
	private String convKnrZ3Input(String external) {
		String internal;
		internal = external == null ? "0" : external;	//null to integer
		internal = "".equals(internal) ? "0" : internal;	//empty to integer
		return NumberUtils.isNumber(internal) ? String.format("%010d", NumberUtils.toInt(internal, 0)) : internal;
	}
	public String getKnrZ2Nm() {
		return knrZ2Nm;
	}
	public void setKnrZ2Nm(String knrZ2Nm) {
		this.knrZ2Nm = knrZ2Nm;
	}
	public BigDecimal getNetwrSO() {
		return netwrSO;
	}
	public void setNetwrSO(BigDecimal netwrSO) {
		this.netwrSO = netwrSO;
	}
	public BigDecimal getMwsbp() {
		return mwsbp;
	}
	public void setMwsbp(BigDecimal mwsbp) {
		this.mwsbp = mwsbp;
	}
	public BigDecimal getHwbas29() {
		return hwbas29;
	}
	public void setHwbas29(BigDecimal hwbas29) {
		this.hwbas29 = hwbas29;
	}
	public BigDecimal getDmbtr29() {
		return dmbtr29;
	}
	public void setDmbtr29(BigDecimal dmbtr29) {
		this.dmbtr29 = dmbtr29;
	}
	public Float getCpb() {
		return cpb;
	}
	public void setCpb(Float cpb) {
		this.cpb = cpb;
	}
	public Integer getZqnty() {
		return zqnty;
	}
	public void setZqnty(Integer zqnty) {
		this.zqnty = zqnty;
	}
	public Float getKbetr() {
		return kbetr;
	}
	public void setKbetr(Float kbetr) {
		this.kbetr = kbetr;
	}
	public BigDecimal getKzwi4() {
		return kzwi4;
	}
	public void setKzwi4(BigDecimal kzwi4) {
		this.kzwi4 = kzwi4;
	}
	public BigDecimal getTampa() {
		return tampa;
	}
	public void setTampa(BigDecimal tampa) {
		this.tampa = tampa;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getStake() {
		return stake;
	}
	public void setStake(BigDecimal stake) {
		if (stake == null) {
			this.stake = BigDecimal.ZERO;
		} else {
			this.stake = stake;
		}
	}
	public BigDecimal getKwert() {
		return kwert;
	}
	public void setKwert(BigDecimal kwert) {
		this.kwert = kwert;
	}
	public BigDecimal getAdjKwert() {
		return adjKwert;
	}
	public void setAdjKwert(BigDecimal adjKwert) {
		this.adjKwert = adjKwert;
	}
	public Vbeln getVbeln() {
		return vbeln;
	}
	public void setVbeln(Vbeln vbeln) {
		this.vbeln = vbeln;
	}
	public Datum getAedat() {
		return aedat;
	}
	public void setAedat(Datum aedat) {
		this.aedat = aedat;
	}
	public String getAenam() {
		return aenam;
	}
	public void setAenam(String aenam) {
		this.aenam = aenam;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getExists128() {
		return exists128;
	}
	public void setExists128(Boolean exists128) {
		this.exists128 = exists128;
	}
	public String getBstdk() {
		return bstdk;
	}
	public void setBstdk(String bstdk) {
		this.bstdk = bstdk;
	}
	public String getContr_da() {
		return contr_da;
	}
	public void setContr_da(String contr_da) {
		this.contr_da = contr_da;
	}
	public String getVdatu() {
		return vdatu;
	}
	public void setVdatu(String vdatu) {
		this.vdatu = vdatu;
	}
	public Kunnr getKnrAg() {
		return knrAg;
	}
	public void setKnrAg(Kunnr knrAg) {
		this.knrAg = knrAg;
	}
	public String getKnrAgNm() {
		return knrAgNm;
	}
	public void setKnrAgNm(String knrAgNm) {
		this.knrAgNm = knrAgNm;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAbstk() {
		return abstk;
	}
	public void setAbstk(String abstk) {
		this.abstk = abstk;
	}
	public Boolean getPosted() {
		return posted;
	}
	public void setPosted(Boolean posted) {
		this.posted = posted;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}