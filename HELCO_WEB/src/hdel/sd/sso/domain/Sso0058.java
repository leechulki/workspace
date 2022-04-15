package hdel.sd.sso.domain;

import org.apache.commons.lang.math.NumberUtils;

import hdel.lib.domain.CommonDomain;
import hdel.lib.domain.ZBCS_TIMESTAMP;

public class Sso0058 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private String vbeln = "";
	private String recad_da = "";
	private Integer seq = 0;
	private String finl = "";

	private String posnr = "";
	private String matnr = "";
	private String hogi = "";
	private String arktx = "";

	private String posnrNew = "";
	private String matnrNew = "";
	private String hogiNew = "";
	private String arktxNew = "";

	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public Sso0058() {
		
	}
	public Sso0058(String mandt, String vbeln, Integer seq) {
		this.mandt = mandt;
		this.vbeln = vbeln;
		this.seq = seq;
	}

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getVbeln() {
		return NumberUtils.isNumber(vbeln) ? String.format("%010d", NumberUtils.toInt(vbeln, 0)) : vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = NumberUtils.isNumber(vbeln) ? String.format("%010d", NumberUtils.toInt(vbeln, 0)) : vbeln;
	}
	public String getRecad_da() {
		return recad_da;
	}
	public void setRecad_da(String recad_da) {
		this.recad_da = recad_da;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getFinl() {
		return finl;
	}
	public void setFinl(String finl) {
		this.finl = finl;
	}
	public String getPosnr() {
		return posnr;
	}
	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getHogi() {
		return hogi;
	}
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}
	public String getArktx() {
		return arktx;
	}
	public void setArktx(String arktx) {
		this.arktx = arktx;
	}
	public String getPosnrNew() {
		return posnrNew;
	}
	public void setPosnrNew(String posnrNew) {
		this.posnrNew = posnrNew;
	}
	public String getMatnrNew() {
		return matnrNew;
	}
	public void setMatnrNew(String matnrNew) {
		this.matnrNew = matnrNew;
	}
	public String getHogiNew() {
		return hogiNew;
	}
	public void setHogiNew(String hogiNew) {
		this.hogiNew = hogiNew;
	}
	public String getArktxNew() {
		return arktxNew;
	}
	public void setArktxNew(String arktxNew) {
		this.arktxNew = arktxNew;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}