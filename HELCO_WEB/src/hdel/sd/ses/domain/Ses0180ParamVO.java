package hdel.sd.ses.domain;

import com.helco.xf.cs12.evladm.dbutil.DateTime;

import tit.util.DatasetUtility;

public class Ses0180ParamVO {
	private String MANDT;
	private String QTDATFR;
	private String QTDATTO;
	private String ZESTDATFR;
	private String ZESTDATTO;
	private String VKBUR;
	private String VKGRP;
	private String ZPRGFLG;
	private String ZKUNNR;
	private String ZPSTAT;
	private String BDDATFR;
	private String BDDATTO;

	public String getMandt() {
		return MANDT;
	}

	public void setMandt(String mandt) {
		this.MANDT = mandt;
	}

	public String getQtdatfr() {
		return QTDATFR;
	}

	public void setQtdatfr(String qtdatFr) {
		this.QTDATFR = qtdatFr;
	}

	public String getQtdatto() {
		return QTDATFR;
	}

	public void setQtdatto(String qtdatTo) {
		if (qtdatTo.equals("")) {
			this.QTDATTO = DateTime.getDateString();
		} else {
			this.QTDATTO = qtdatTo;
		}
	}

	public String getZestdatfr() {
		return ZESTDATFR;
	}

	public void setZestdatfr(String zestdatFr) {
		this.ZESTDATFR = zestdatFr;
	}

	public String getZestdatto() {
		return ZESTDATTO;
	}

	public void setZestdatto(String zestdatTo) {
		if (zestdatTo.equals("")) {
			this.ZESTDATTO = DateTime.getDateString();
		} else {
			this.ZESTDATTO = zestdatTo;
		}
	}

	public String getVkbur() {
		return VKBUR;
	}

	public void setVkbur(String vkbur) {
		this.VKBUR = vkbur;
	}

	public String getVkgrp() {
		return VKGRP;
	}

	public void setVkgrp(String vkgrp) {
		this.VKGRP = vkgrp;
	}

	public String getZprgflg() {
		return ZPRGFLG;
	}

	public void setZprgflg(String zprgflg) {
		this.ZPRGFLG = zprgflg;
	}

	public String getZkunnr() {
		return ZKUNNR;
	}

	public void setZkunnr(String zkunnr) {
		this.ZKUNNR = zkunnr;
	}

	public String getZpstat() {
		return ZPSTAT;
	}

	public void setZpstat(String zpstat) {
		this.ZPSTAT = zpstat;
	}

	public String getBDDATFR() {
		return BDDATFR;
	}

	public void setBDDATFR(String bDDATFR) {
		BDDATFR = bDDATFR;
	}

	public String getBDDATTO() {
		return BDDATTO;
	}

	public void setBDDATTO(String bDDATTO) {
		BDDATTO = bDDATTO;
	}
}
