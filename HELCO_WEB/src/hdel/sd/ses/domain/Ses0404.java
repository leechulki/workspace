package hdel.sd.ses.domain;

import org.apache.commons.lang.math.NumberUtils;

import com.helco.pbf.NumberFormat;

import hdel.lib.domain.CommonDomain;

public class Ses0404 extends CommonDomain {
    private String mandt;
	private String zrqseq;
    private String qtnum;
    private String qtser;
    private String pjt;
    private String gsnam;
	private String outgbn;
	private String znumber;
	private String outgrd;
	private String outloq;
	private String outprice;
	private String outamt;
	private String zrqdat;
	private String outsrcdt;
	private String outactdt;
	private String outretdt;
	private String outfindt;
	private String findat;
	private String delaydays;
	private String wrkdays;
	private String duedt;
	private String induedt;
	private String outman;
	private String ptnz3;
	private String ptnz3nm;
	private String ptnz2;
	private String ptnz2nm;
	private String vkbur;
	private String vkgrp;
	private String udate;
	private String utime;
	private String uuser;
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
	 * @return the pjt
	 */
	public String getPjt() {
		return pjt;
	}
	/**
	 * @param pjt the pjt to set
	 */
	public void setPjt(String pjt) {
		this.pjt = pjt;
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
	 * @return the outgbn
	 */
	public String getOutgbn() {
		return outgbn;
	}
	/**
	 * @param outgbn the outgbn to set
	 */
	public void setOutgbn(String outgbn) {
		this.outgbn = outgbn;
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
	 * @return the outgrd
	 */
	public String getOutgrd() {
		return outgrd;
	}
	/**
	 * @param outgrd the outgrd to set
	 */
	public void setOutgrd(String outgrd) {
		this.outgrd = outgrd;
	}
	/**
	 * @return the outloq
	 */
	public String getOutloq() {
		return outloq;
	}
	/**
	 * @param outloq the outloq to set
	 */
	public void setOutloq(String outloq) {
		this.outloq = outloq;
	}
	/**
	 * @return the outprice
	 */
	public String getOutprice() {
		return outprice;
	}
	/**
	 * @param outprice the outprice to set
	 */
	public void setOutprice(String outprice) {
		this.outprice = outprice;
	}
	/**
	 * @return the outamt
	 */
	public String getOutamt() {
		return outamt;
	}
	/**
	 * @param outamt the outamt to set
	 */
	public void setOutamt(String outamt) {
		this.outamt = outamt;
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
	 * @return the outsrcdt
	 */
	public String getOutsrcdt() {
		return outsrcdt;
	}
	/**
	 * @param outsrcdt the outsrcdt to set
	 */
	public void setOutsrcdt(String outsrcdt) {
		this.outsrcdt = outsrcdt;
	}
	/**
	 * @return the outactdt
	 */
	public String getOutactdt() {
		return outactdt;
	}
	/**
	 * @param outactdt the outactdt to set
	 */
	public void setOutactdt(String outactdt) {
		this.outactdt = outactdt;
	}
	/**
	 * @return the outretdt
	 */
	public String getOutretdt() {
		return outretdt;
	}
	/**
	 * @param outretdt the outretdt to set
	 */
	public void setOutretdt(String outretdt) {
		this.outretdt = outretdt;
	}
	/**
	 * @return the outfindt
	 */
	public String getOutfindt() {
		return outfindt;
	}
	/**
	 * @param outfindt the outfindt to set
	 */
	public void setOutfindt(String outfindt) {
		this.outfindt = outfindt;
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
	 * @return the delaydays
	 */
	public String getDelaydays() {
		return delaydays;
	}
	/**
	 * @param delaydays the delaydays to set
	 */
	public void setDelaydays(String delaydays) {
		this.delaydays = String.format("%03d", NumberUtils.toInt(delaydays, 0));
	}
	/**
	 * @return the wrkdays
	 */
	public String getWrkdays() {
		return wrkdays;
	}
	/**
	 * @param wrkdays the wrkdays to set
	 */
	public void setWrkdays(String wrkdays) {
		this.wrkdays = String.format("%02d", NumberUtils.toInt(wrkdays, 0));
	}
	/**
	 * @return the duedt
	 */
	public String getDuedt() {
		return duedt;
	}
	/**
	 * @param duedt the duedt to set
	 */
	public void setDuedt(String duedt) {
		this.duedt = duedt;
	}
	/**
	 * @return the induedt
	 */
	public String getInduedt() {
		return induedt;
	}
	/**
	 * @param induedt the induedt to set
	 */
	public void setInduedt(String induedt) {
		this.induedt = induedt;
	}
	/**
	 * @return the outman
	 */
	public String getOutman() {
		return outman;
	}
	/**
	 * @param outman the outman to set
	 */
	public void setOutman(String outman) {
		this.outman = outman;
	}
	/**
	 * @return the ptnz3
	 */
	public String getPtnz3() {
		return ptnz3;
	}
	/**
	 * @param ptnz3 the ptnz3 to set
	 */
	public void setPtnz3(String ptnz3) {
		this.ptnz3 = ptnz3;
	}
	/**
	 * @return the ptnz3nm
	 */
	public String getPtnz3nm() {
		return ptnz3nm;
	}
	/**
	 * @param ptnz3nm the ptnz3nm to set
	 */
	public void setPtnz3nm(String ptnz3nm) {
		this.ptnz3nm = ptnz3nm;
	}
	/**
	 * @return the ptnz2
	 */
	public String getPtnz2() {
		return ptnz2;
	}
	/**
	 * @param ptnz2 the ptnz2 to set
	 */
	public void setPtnz2(String ptnz2) {
		this.ptnz2 = ptnz2;
	}
	/**
	 * @return the ptnz2nm
	 */
	public String getPtnz2nm() {
		return ptnz2nm;
	}
	/**
	 * @param ptnz2nm the ptnz2nm to set
	 */
	public void setPtnz2nm(String ptnz2nm) {
		this.ptnz2nm = ptnz2nm;
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
}