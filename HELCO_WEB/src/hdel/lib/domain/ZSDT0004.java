package hdel.lib.domain;

import org.apache.commons.lang.math.NumberUtils;

public class ZSDT0004 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt = "";
	private String hogi = "";
	private String vbeln = "";
	private String posnr = "000000";
	private String matnr = "";
	private String mowbs = "";
	private String uepos = "";
	private String cuobj = "";
	private String zzpjt_id = "";
	private String zzpjt_nm = "";
	private String spart = "";
	private String vkbur = "";
	private String vkgrp = "";
	private String cr_date = "";
	private String cr_time = "";
	private Boolean cr_flag = false;
	private Boolean cr_flag2 = false;
	private Boolean cr_flag3 = false;
	private String tp_date1 = "";
	private String tp_date2 = "";
	private String tp_date3 = "";
	private String tp_date4 = "";
	private Boolean del_flag = false;

	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getHogi() {
		return hogi;
	}
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}
	public String getVbeln() {
		return NumberUtils.isNumber(vbeln) ? String.format("%010d", NumberUtils.toInt(vbeln, 0)) : vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = NumberUtils.isNumber(vbeln) ? String.format("%010d", NumberUtils.toInt(vbeln, 0)) : vbeln;
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
	public String getMowbs() {
		return mowbs;
	}
	public void setMowbs(String mowbs) {
		this.mowbs = mowbs;
	}
	public String getUepos() {
		return uepos;
	}
	public void setUepos(String uepos) {
		this.uepos = uepos;
	}
	public String getCuobj() {
		return cuobj;
	}
	public void setCuobj(String cuobj) {
		this.cuobj = cuobj;
	}
	public String getZzpjt_id() {
		return zzpjt_id;
	}
	public void setZzpjt_id(String zzpjt_id) {
		this.zzpjt_id = zzpjt_id;
	}
	public String getZzpjt_nm() {
		return zzpjt_nm;
	}
	public void setZzpjt_nm(String zzpjt_nm) {
		this.zzpjt_nm = zzpjt_nm;
	}
	public String getSpart() {
		return spart;
	}
	public void setSpart(String spart) {
		this.spart = spart;
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
	public String getCr_date() {
		return String.format("%08d", NumberUtils.toInt(cr_date, 0));
	}
	public void setCr_date(String cr_date) {
		this.cr_date = String.format("%08d", NumberUtils.toInt(cr_date, 0));
	}
	public String getCr_time() {
		return String.format("%06d", NumberUtils.toInt(cr_time, 0));
	}
	public void setCr_time(String cr_time) {
		this.cr_time = String.format("%06d", NumberUtils.toInt(cr_time, 0));
	}
	public Boolean getCr_flag() {
		return cr_flag;
	}
	public void setCr_flag(Boolean cr_flag) {
		this.cr_flag = cr_flag;
	}
	public Boolean getCr_flag2() {
		return cr_flag2;
	}
	public void setCr_flag2(Boolean cr_flag2) {
		this.cr_flag2 = cr_flag2;
	}
	public Boolean getCr_flag3() {
		return cr_flag3;
	}
	public void setCr_flag3(Boolean cr_flag3) {
		this.cr_flag3 = cr_flag3;
	}
	public String getTp_date1() {
		return String.format("%08d", NumberUtils.toInt(tp_date1, 0));
	}
	public void setTp_date1(String tp_date1) {
		this.tp_date1 = String.format("%08d", NumberUtils.toInt(tp_date1, 0));
	}
	public String getTp_date2() {
		return String.format("%08d", NumberUtils.toInt(tp_date2, 0));
	}
	public void setTp_date2(String tp_date2) {
		this.tp_date2 = String.format("%08d", NumberUtils.toInt(tp_date2, 0));
	}
	public String getTp_date3() {
		return String.format("%08d", NumberUtils.toInt(tp_date3, 0));
	}
	public void setTp_date3(String tp_date3) {
		this.tp_date3 = String.format("%08d", NumberUtils.toInt(tp_date3, 0));
	}
	public String getTp_date4() {
		return String.format("%08d", NumberUtils.toInt(tp_date4, 0));
	}
	public void setTp_date4(String tp_date4) {
		this.tp_date4 = String.format("%08d", NumberUtils.toInt(tp_date4, 0));
	}
	public Boolean getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Boolean del_flag) {
		this.del_flag = del_flag;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}