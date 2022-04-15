package hdel.sd.com.domain;

import hdel.lib.domain.CommonDomain;


/**
 * 거래선목록 조회 (Com0040) CommonDomain
 * @Comment 
 *     	- Com0040C(거래선목록 조회) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public class Com0040 extends CommonDomain {
 
	private String buyr_cd;			// 거래처코드
	private String buyr_nm; 		// 거래처명
	private String buyr_nm_sort;	// 거래처명 정렬용
	private String buyr_addr;		// 거래처주소
	private String buyr_cd_db;		// 거래처코드DB정보
	private String land1;			// 국가코드
	private String land1_nm;		// 국가코드명 
	private String zarea;			// 권역코드
	private String zarea_nm;		// 권역코드명 

	private String opendt;		// E-GIS 오픈여부 
	
	 

	public String getBuyr_cd() {
		return buyr_cd;
	}
	public void setBuyr_cd(String buyr_cd) {
		this.buyr_cd = buyr_cd;
	} 	
	public String getBuyr_nm() {
		return buyr_nm;
	}
	public void setBuyr_nm(String buyr_nm) {
		this.buyr_nm = buyr_nm;
	}
	public String getBuyr_nm_sort() {
		return buyr_nm_sort;
	}
	public void setBuyr_nm_sort(String buyr_nm_sort) {
		this.buyr_nm_sort = buyr_nm_sort;
	}
	public String getBuyr_addr() {
		return buyr_addr;
	}
	public void setBuyr_addr(String buyr_addr) {
		this.buyr_addr = buyr_addr;
	}
	public String getBuyr_cd_db() {
		return buyr_cd_db;
	}
	public void setBuyr_cd_db(String buyr_cd_db) {
		this.buyr_cd_db = buyr_cd_db;
	} 
	
	public String getLand1() {
		return land1;
	}
	public void setLand1(String land1) {
		this.land1 = land1;
	} 
	public String getLand1_nm() {
		return land1_nm;
	}
	public void setLand1_nm(String land1_nm) {
		this.land1_nm = land1_nm;
	}    
	public String getZarea() {
		return zarea;
	}
	public void setZarea(String zarea) {
		this.zarea = zarea;
	} 
	public String getZarea_nm() {
		return zarea_nm;
	}
	public void setZarea_nm(String zarea_nm) {
		this.zarea_nm = zarea_nm;
	}
	public String getOpendt() {
		return opendt;
	}
	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}  
	
	
}
