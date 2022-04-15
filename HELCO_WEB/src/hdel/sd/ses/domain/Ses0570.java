package hdel.sd.ses.domain;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.sap.domain.Datum;

import hdel.lib.domain.CommonDomain;

public class Ses0570 extends CommonDomain {
 
	private String mandt;
	private String frzrqdat;
	private String tozrqdat;
    private String qtso_no;
    private String qtser;
    private String qtseq;
	private String gsnam;
    private String zrqseq;
    private String Zrqdat;
    private String findat;
    private String kunnr_z3;
    private String kunnr_z3_nm;
    private String vbeln;
	private int totalzrqdat;
	private int daysno;
	private int range;
	private String part_code;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getFrzrqdat() {
		return frzrqdat;
	}
	public void setFrzrqdat(String frzrqdat) {
		this.frzrqdat = frzrqdat;
	}
	public String getTozrqdat() {
		return tozrqdat;
	}
	public void setTozrqdat(String tozrqdat) {
		this.tozrqdat = tozrqdat;
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
		return Zrqdat;
	}
	/**
	 * @param zrqdat the zrqdat to set
	 */
	public void setZrqdat(String zrqdat) {
		Zrqdat = zrqdat;
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
	 * @return the kunnr_z3
	 */
	public String getKunnr_z3() {
		return kunnr_z3;
	}
	/**
	 * @param kunnr_z3 the kunnr_z3 to set
	 */
	public void setKunnr_z3(String kunnr_z3) {
		this.kunnr_z3 = kunnr_z3;
	}
	/**
	 * @return the kunnr_z3_nm
	 */
	public String getKunnr_z3_nm() {
		return kunnr_z3_nm;
	}
	/**
	 * @param kunnr_z3_nm the kunnr_z3_nm to set
	 */
	public void setKunnr_z3_nm(String kunnr_z3_nm) {
		this.kunnr_z3_nm = kunnr_z3_nm;
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
	public int getTotalzrqdat() {
		return totalzrqdat;
	}
	public void setTotalzrqdat(int totalzrqdat) {
		this.totalzrqdat = totalzrqdat;
	}
	public int getDaysno() {
		return daysno;
	}
	public void setDaysno(int daysno) {
		this.daysno = daysno;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public String getPart_code() {
		return part_code;
	}
	public void setPart_code(String part_code) {
		this.part_code = part_code;
	}
	
}