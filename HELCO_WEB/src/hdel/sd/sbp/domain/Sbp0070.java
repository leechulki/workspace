package hdel.sd.sbp.domain;

import hdel.lib.domain.CommonDomain;
import java.math.*;


/**
 * 사업계획오픈/마감 (Sbp0070) CommonDomain
 * @Comment 
 *     	- Sbp0070C(사업계획오픈/마감) 에서 사용 
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public class Sbp0070 extends CommonDomain {
	 
	public String CHK;		// 체크(1,0)
	public String FLAG;     // 변경구분(I,U,D) 
	public String USER_ID;  // 작업자ID
	
	public String MANDT;	// Cleint
	public String ZBPINC;	// 사업계획차수
	public String ZPYEAR;	// 편성년도
	public String ZOPFLG;	// 오픈여부(오픈:X)
	public String ZCLFLG;	// 마감여부 (마감:X)
	public String ZRMK; 	// 비고
	public String CDATE;    // 입력일자
	public String CTIME;    // 입력시간
	public String CUSER;    // 입력자ID
	public String UDATE;    // 변경일자
	public String UTIME;    // 변경시간
	public String UUSER;    // 변경자ID
	public String DDATE;    // 삭제일자
	public String DTIME;    // 삭제시간
	public String DUSER;    // 삭제자ID 
 

	public String getCHK(){
		return CHK;
	}
	public String getFLAG(){
		return FLAG;
	} 
	public String getUSER_ID(){
		return USER_ID;
	}
	public String getMANDT(){
		return MANDT;
	}
	public String getZBPINC(){
		return ZBPINC;
	}
	public String getZPYEAR(){
		return ZPYEAR;
	}
	public String getZOPFLG(){
		return ZOPFLG;
	}
	public String getZCLFLG(){
		return ZCLFLG;
	}
	public String getZRMK(){
		return ZRMK;
	}
	public String getCDATE(){
		return CDATE;
	}
	public String getCTIME(){
		return CTIME;
	}
	public String getCUSER(){
		return CUSER;
	}
	public String getUDATE(){
		return UDATE;
	}
	public String getUTIME(){
		return UTIME;
	}
	public String getUUSER(){
		return UUSER;
	}
	public String getDDATE(){
		return DDATE;
	}
	public String getDTIME(){
		return DTIME;
	}
	public String getDUSER(){
		return DUSER;
	}
	 
	 
	public void setCHK(String aValue) {
		CHK=aValue;
	}
	public void setFLAG(String aValue) {
		FLAG=aValue;
	} 
	public void setUSER_ID(String aValue) {
		USER_ID=aValue;
	}
	 
	public void setMANDT(String aValue) {
		MANDT=aValue;
	}
	public void setZBPINC(String aValue) {
		ZBPINC=aValue;
	}
	public void setZPYEAR(String aValue) {
		ZPYEAR=aValue;
	}
	public void setZOPFLG(String aValue) {
		ZOPFLG=aValue;
	}
	public void setZCLFLG(String aValue) {
		ZCLFLG=aValue;
	}
	public void setZRMK(String aValue) {
		ZRMK=aValue;
	}
	public void setCDATE(String aValue) {
		CDATE=aValue;
	}
	public void setCTIME(String aValue) {
		CTIME=aValue;
	}
	public void setCUSER(String aValue) {
		CUSER=aValue;
	}
	public void setUDATE(String aValue) {
		UDATE=aValue;
	}
	public void setUTIME(String aValue) {
		UTIME=aValue;
	}
	public void setUUSER(String aValue) {
		UUSER=aValue;
	}
	public void setDDATE(String aValue) {
		DDATE=aValue;
	}
	public void setDTIME(String aValue) {
		DTIME=aValue;
	}
	public void setDUSER(String aValue) {
		DUSER=aValue;
	}
}