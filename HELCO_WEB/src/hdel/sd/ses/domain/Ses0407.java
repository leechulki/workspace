package hdel.sd.ses.domain;

import org.apache.commons.lang.math.NumberUtils;

import hdel.lib.domain.CommonDomain;

public class Ses0407 extends CommonDomain {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -2791467608200793923L;

	private String chgstate;
	private String mandt;
	private String kunnr;
	private String name1;
	private String pstlz;
	private String ort01;
	private String j_1kfrepre;
	private String j_1kftbus;
	private String j_1kftind;
	private String ycont;
	private String udate;
	private String uname;
	/**
	 * @return the chgstate
	 */
	public String getChgstate() {
		return chgstate;
	}
	/**
	 * @param chgstate the chgstate to set
	 */
	public void setChgstate(String chgstate) {
		this.chgstate = chgstate;
	}
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
	 * @return the kunnr
	 */
	public String getKunnr() {
		return String.format("%010d", NumberUtils.toInt(kunnr, 0));
	}
	/**
	 * @param kunnr the kunnr to set
	 */
	public void setKunnr(String kunnr) {
		this.kunnr = String.format("%010d", NumberUtils.toInt(kunnr, 0));
	}
	/**
	 * @return the name1
	 */
	public String getName1() {
		return name1==null ? "" : name1;
	}
	/**
	 * @param name1 the name1 to set
	 */
	public void setName1(String name1) {
		this.name1 = name1==null ? "" : name1;
	}
	/**
	 * @return the pstlz
	 */
	public String getPstlz() {
		return pstlz;
	}
	/**
	 * @param pstlz the pstlz to set
	 */
	public void setPstlz(String pstlz) {
		this.pstlz = pstlz;
	}
	/**
	 * @return the ort01
	 */
	public String getOrt01() {
		return ort01;
	}
	/**
	 * @param ort01 the ort01 to set
	 */
	public void setOrt01(String ort01) {
		this.ort01 = ort01;
	}
	/**
	 * @return the j_1kfrepre
	 */
	public String getJ_1kfrepre() {
		return j_1kfrepre;
	}
	/**
	 * @param j_1kfrepre the j_1kfrepre to set
	 */
	public void setJ_1kfrepre(String j_1kfrepre) {
		this.j_1kfrepre = j_1kfrepre;
	}
	/**
	 * @return the j_1kftbus
	 */
	public String getJ_1kftbus() {
		return j_1kftbus;
	}
	/**
	 * @param j_1kftbus the j_1kftbus to set
	 */
	public void setJ_1kftbus(String j_1kftbus) {
		this.j_1kftbus = j_1kftbus;
	}
	/**
	 * @return the j_1kftind
	 */
	public String getJ_1kftind() {
		return j_1kftind;
	}
	/**
	 * @param j_1kftind the j_1kftind to set
	 */
	public void setJ_1kftind(String j_1kftind) {
		this.j_1kftind = j_1kftind;
	}
	/**
	 * @return the ycont
	 */
	public String getYcont() {
		return ycont;
	}
	/**
	 * @param ycont the ycont to set
	 */
	public void setYcont(String ycont) {
		this.ycont = ycont;
	}
	/**
	 * @return the udate
	 */
	public String getUdate() {
		return String.format("%08d", NumberUtils.toInt(udate, 0));
	}
	/**
	 * @param udate the udate to set
	 */
	public void setUdate(String udate) {
		this.udate = String.format("%08d", NumberUtils.toInt(udate, 0));
	}
	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname==null ? "" : uname;
	}
	/**
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname==null ? "" : uname;
	}
	
}