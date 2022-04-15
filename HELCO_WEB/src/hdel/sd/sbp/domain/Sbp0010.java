package hdel.sd.sbp.domain;

import hdel.lib.domain.CommonDomain;
import java.math.*;
  

/**
 * 사업계획관리(Sbp0010) CommonDomain
 * @Comment 
 *     	- Sbp0010C(사업계획관리) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class Sbp0010 extends CommonDomain {
	
	public String OPENDTM;  // 오픈일시
	public String ZBPINC;  	// 사업계획차수
	public String ZOPFLG;  	// 오픈여부(Y,N)
	
	public String SAVE_YN;	// 저장여부(Y,N)
	public String CHK;		// 체크(1,0)
	public String FLAG;     // 변경구분(I,U,D)
	public String NEW_YN;   // 신규여부
	public String USER_ID;  // 작업자ID
	
	public String MANDT;	// SAP CLIENT
	public String ZBPNNR;   // 사업계획번호
	public String ZPYEAR;   // 편성년도
	public String ZPYM;     // 계획년월
	public String SPART;    // 제품군
	public String AUART;    // 판매문서유형
	public String MATNR;    // 자재번호
	public String VKBUR;    // 사업장코드
	public String VKGRP;    // 영업그룹코드
	public String ZKUNNR;   // 영업사원코드
	public String ZKUNNR_NM;   // 영업사원명
	public String GTYPE;    // 기종
	public String TYPE1;    // TYPE1
	public String TYPE2;    // TYPE2
	public String TYPE3;    // TYPE3
	public int ZNUMBER;  	// 대수
	public String KUNNR;    // 고객번호(거래선코드)
	public String KUNNR_NM;    // 고객명(거래선명)
	public String GSNAM;    // 공사명
	public String REGION;   // 설치지역코드
	public String ZDELI;    // 단납기구분
	public String SHANGH;   // 국내/상해구분
	public String ZINTER;   // 중계무역구분
	public BigDecimal SOFOCA;   // 수주예상액
	public BigDecimal ZFORE;    // 예상시행율
	public String WAERK;    // 통화
	public String PSPIDSV;  // 보수프로젝트번호
	public String ZHOGISV;  // 보수호기번호
	public String DELDAT;   // 납기예정일
	public String ZRMK;     // 비고
	public String ZRMK1;    // 비고 (replace(ZRMK, chr(13)||chr(10), ' ')
	public String VBELN;    // 판매문서
	public String PSPID;    // 프로젝트
	public String CDATE;    // 입력일자
	public String CTIME;    // 입력시간
	public String CUSER;    // 입력자ID
	public String UDATE;    // 변경일자
	public String UTIME;    // 변경시간
	public String UUSER;    // 변경자ID
	public String DDATE;    // 삭제일자
	public String DTIME;    // 삭제시간
	public String DUSER;    // 삭제자ID
	
	public String ZAGNT;    // 대리점
	public String ZAGNT_NM; // 대리점명
	
	public String LAND1; 	// 국가코드
	public String LAND1_NM; // 국가코드명
	public String ZAREA; 	// 권역코드
	public String ZAREA_NM; // 권역코드명
	
	// 저장상태(A:사업계획저장완료, E/F/G:SAP용매출청구수금저장완료, O:매출/청구/수금/원가저장완료, Z:성공)
	public String SAVE_STAT; 
	
	public String 	  GBN;		// 매출/청구/수금 구분 (1, 2, 3 : order by 기준)
	public String 	  GBN_NM;	// 매출/청구/수금 구분 명 (1:매출, 2:청구, 3:수금)
	public BigDecimal AMT00;	// 기준년월+00월 금액
	public BigDecimal AMT01;	// 기준년월+01월 금액
	public BigDecimal AMT02;	// 기준년월+02월 금액
	public BigDecimal AMT03;	// 기준년월+03월 금액
	public BigDecimal AMT04;	// 기준년월+04월 금액
	public BigDecimal AMT05;	// 기준년월+05월 금액
	public BigDecimal AMT06;	// 기준년월+06월 금액
	public BigDecimal AMT07;	// 기준년월+07월 금액
	public BigDecimal AMT08;	// 기준년월+08월 금액
	public BigDecimal AMT09;	// 기준년월+09월 금액
	public BigDecimal AMT10;	// 기준년월+10월 금액
	public BigDecimal AMT11;	// 기준년월+11월 금액
	public BigDecimal AMT12;	// 기준년월+12월 금액
	public BigDecimal AMT13;	// 기준년월+13월 금액
	public BigDecimal AMT14;	// 기준년월+14월 금액
	public BigDecimal AMT15;	// 기준년월+15월 금액
	public BigDecimal AMT16;	// 기준년월+16월 금액
	public BigDecimal AMT17;	// 기준년월+17월 금액 
	public BigDecimal AMT18;	// 기준년월+18월 금액
	public BigDecimal AMT19;	// 기준년월+19월 금액
	public BigDecimal AMT20;	// 기준년월+20월 금액
	public BigDecimal AMT21;	// 기준년월+21월 금액
	public BigDecimal AMT22;	// 기준년월+22월 금액
	public BigDecimal AMT23;	// 기준년월+23월 금액
	public BigDecimal AMT24;	// 기준년월+24월 금액
	public BigDecimal AMT25;	// 기준년월+25월 금액
	public BigDecimal AMT26;	// 기준년월+26월 금액
	public BigDecimal AMT27;	// 기준년월+27월 금액 
	public BigDecimal AMT28;	// 기준년월+28월 금액
	public BigDecimal AMT29;	// 기준년월+29월 금액  
	
	
	public String     ZSAYM;	// 매출년월
	public String     ZRQYM;	// 청구년월
	public String     ZYSYM;	// 수금년월 
	public BigDecimal NETWR_SA;	// 매출예상금액
	public BigDecimal NETWR_RQ;	// 청구예상금액
	public BigDecimal COLBI;	// 수금예상금액
	
	// ZSDT0014S 테이블 저장용
	public String     POSID;	// 호기
	public String     BIDDAT;	// ?????
 
	// 원가산출용 
	public String     ATKLA ; 	// 특성그룹 ==> 확인필요
	public String     ATNAM ; 	// 특성코드 saphee.zsdt1061.prh 
	public String     VALUE ; 	// 특성값  saphee.zsdt1061.prd  
	public BigDecimal ZCOST;	// 원가
	public BigDecimal ZPCOST;	// 단가	
	
	
	public String UFLAG;	// 기종, 인승 , 속도, 층수, 수주예상액  변경시
	public String RTYPE;	// 기종 	
	
	public String CODE;   	// 실기종 코드  	
	public String CODENAME;	// 실기종 코등 이름.
	
	public String ZBDTYP;	   // 건물용도  jss
	public String ZBDTYP_LNM;  // 건물용도 대분류 jss
	
	
	public String getOPENDTM(){
		return OPENDTM;
	} 
	public String getZBPINC(){
		return ZBPINC;
	}
	public String getZOPFLG(){
		return ZOPFLG;
	} 
	public String getSAVE_YN(){
		return SAVE_YN;
	}
	public String getCHK(){
		return CHK;
	}
	public String getFLAG(){
		return FLAG;
	}
	public String getNEW_YN(){
		return NEW_YN;
	}
	public String getUSER_ID(){
		return USER_ID;
	}
	public String getMANDT(){
		return MANDT;
	}
	public String getZBPNNR(){
		return ZBPNNR;
	}
	public String getZPYEAR(){
		return ZPYEAR;
	}
	public String getZPYM(){
		return ZPYM;
	}
	public String getSPART(){
		return SPART;
	}
	public String getAUART(){
		return AUART;
	}
	public String getMATNR(){
		return MATNR;
	}
	public String getVKBUR(){
		return VKBUR;
	}
	public String getVKGRP(){
		return VKGRP;
	}
	public String getZKUNNR(){
		return ZKUNNR;
	}
	public String getZKUNNR_NM(){
		return ZKUNNR_NM;
	}
	public String getGTYPE(){
		return GTYPE;
	}
	public String getTYPE1(){
		return TYPE1;
	}
	public String getTYPE2(){
		return TYPE2;
	}
	public String getTYPE3(){
		return TYPE3;
	}
	public int getZNUMBER(){
		return ZNUMBER;
	}
	public String getKUNNR(){
		return KUNNR;
	}
	public String getKUNNR_NM(){
		return KUNNR_NM;
	}
	public String getGSNAM(){
		return GSNAM;
	}
	public String getREGION(){
		return REGION;
	}
	public String getZDELI(){
		return ZDELI;
	}
	public String getSHANGH(){
		return SHANGH;
	}
	public String getZINTER(){
		return ZINTER;
	}
	public BigDecimal getSOFOCA(){
		return SOFOCA;
	}
	public BigDecimal getZFORE(){
		return ZFORE;
	}
	public String getWAERK(){
		return WAERK;
	}
	public String getPSPIDSV(){
		return PSPIDSV;
	}
	public String getZHOGISV(){
		return ZHOGISV;
	}
	public String getDELDAT(){
		return DELDAT;
	}
	public String getZRMK(){
		return ZRMK;
	}
	public String getZRMK1(){
		return ZRMK1;
	}
	public String getVBELN(){
		return VBELN;
	}
	public String getPSPID(){
		return PSPID;
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
	public String getZAGNT(){
		return ZAGNT;
	}
	public String getZAGNT_NM(){
		return ZAGNT_NM;
	}
	public String getSAVE_STAT(){
		return SAVE_STAT;
	} 
	public String getLAND1(){
		return LAND1;
	}  
	public String getLAND1_NM(){
		return LAND1_NM;
	}  
	public String getZAREA(){
		return ZAREA;
	}  
	public String getZAREA_NM(){
		return ZAREA_NM;
	}  
	
	public String getGBN(){
		return GBN;
	}
	public String getGBN_NM(){
		return GBN_NM;
	} 
	public BigDecimal getAMT00(){
		return AMT00;
	}
	public BigDecimal getAMT01(){
		return AMT01;
	}
	public BigDecimal getAMT02(){
		return AMT02;
	}
	public BigDecimal getAMT03(){
		return AMT03;
	}
	public BigDecimal getAMT04(){
		return AMT04;
	}
	public BigDecimal getAMT05(){
		return AMT05;
	}
	public BigDecimal getAMT06(){
		return AMT06;
	}
	public BigDecimal getAMT07(){
		return AMT07;
	}
	public BigDecimal getAMT08(){
		return AMT08;
	}
	public BigDecimal getAMT09(){
		return AMT09;
	}
	public BigDecimal getAMT10(){
		return AMT10;
	}
	public BigDecimal getAMT11(){
		return AMT11;
	}
	public BigDecimal getAMT12(){
		return AMT12;
	}
	public BigDecimal getAMT13(){
		return AMT13;
	}
	public BigDecimal getAMT14(){
		return AMT14;
	}
	public BigDecimal getAMT15(){
		return AMT15;
	}
	public BigDecimal getAMT16(){
		return AMT16;
	}
	public BigDecimal getAMT17(){
		return AMT17;
	} 
	 
	

	public BigDecimal getAMT18(){
		return AMT18;
	}
	public BigDecimal getAMT19(){
		return AMT19;
	}
	public BigDecimal getAMT20(){
		return AMT20;
	}
	public BigDecimal getAMT21(){
		return AMT21;
	}
	public BigDecimal getAMT22(){
		return AMT22;
	}
	public BigDecimal getAMT23(){
		return AMT23;
	}
	public BigDecimal getAMT24(){
		return AMT24;
	}
	public BigDecimal getAMT25(){
		return AMT25;
	}
	public BigDecimal getAMT26(){
		return AMT26;
	}
	public BigDecimal getAMT27(){
		return AMT27;
	} 
	public BigDecimal getAMT28(){
		return AMT28;
	}
	public BigDecimal getAMT29(){
		return AMT29;
	}
	 
	

	public String getZSAYM(){
		return ZSAYM;
	}
	public String getZRQYM(){
		return ZRQYM;
	}
	public String getZYSYM(){
		return ZYSYM;
	}
	public BigDecimal getNETWR_SA(){
		return NETWR_SA;
	} 
	public BigDecimal getNETWR_RQ(){
		return NETWR_RQ;
	} 
	public BigDecimal getCOLBI(){
		return COLBI;
	}  
	public String getPOSID(){
		return POSID;
	} 
	public String getBIDDAT(){
		return BIDDAT;
	}   
	public String getATKLA(){
		return ATKLA;
	} 
	public String getATNAM(){
		return ATNAM;
	} 
	public String getVALUE(){
		return VALUE;
	}    
	public BigDecimal getZCOST(){
		return ZCOST;
	} 
	public BigDecimal getZPCOST(){
		return ZPCOST;
	}  
	
	public void setOPENDTM(String aValue) {
		OPENDTM=aValue;
	} 
	public void setZBPINC(String aValue) {
		ZBPINC=aValue;
	} 
	public void setZOPFLG(String aValue) {
		ZOPFLG=aValue;
	} 
	
	public void setSAVE_YN(String aValue) {
		SAVE_YN=aValue;
	}
	public void setCHK(String aValue) {
		CHK=aValue;
	}
	public void setFLAG(String aValue) {
		FLAG=aValue;
	}
	public void setNEW_YN(String aValue) {
		NEW_YN=aValue;
	}
	public void setUSER_ID(String aValue) {
		USER_ID=aValue;
	}
	 
	public void setMANDT(String aValue) {
		MANDT=aValue;
	}
	public void setZBPNNR(String aValue) {
		ZBPNNR=aValue;
	}
	public void setZPYEAR(String aValue) {
		ZPYEAR=aValue;
	}
	public void setZPYM(String aValue) {
		ZPYM=aValue;
	}
	public void setSPART(String aValue) {
		SPART=aValue;
	}
	public void setAUART(String aValue) {
		AUART=aValue;
	}
	public void setMATNR(String aValue) {
		MATNR=aValue;
	}
	public void setVKBUR(String aValue) {
		VKBUR=aValue;
	}
	public void setVKGRP(String aValue) {
		VKGRP=aValue;
	}
	public void setZKUNNR(String aValue) {
		ZKUNNR=aValue;
	}
	public void setZKUNNR_NM(String aValue) {
		ZKUNNR_NM=aValue;
	}
	public void setGTYPE(String aValue) {
		GTYPE=aValue;
	}
	public void setTYPE1(String aValue) {
		TYPE1=aValue;
	}
	public void setTYPE2(String aValue) {
		TYPE2=aValue;
	}
	public void setTYPE3(String aValue) {
		TYPE3=aValue;
	}
	public void setZNUMBER(int aValue) {
		ZNUMBER=aValue;
	}
	public void setKUNNR(String aValue) {
		KUNNR=aValue;
	}
	public void setKUNNR_NM(String aValue) {
		KUNNR_NM=aValue;
	}
	public void setGSNAM(String aValue) {
		GSNAM=aValue;
	}
	public void setREGION(String aValue) {
		REGION=aValue;
	}
	public void setZDELI(String aValue) {
		ZDELI=aValue;
	}
	public void setSHANGH(String aValue) {
		SHANGH=aValue;
	}
	public void setZINTER(String aValue) {
		ZINTER=aValue;
	}
	public void setSOFOCA(BigDecimal aValue) {
		SOFOCA=aValue;
	}
	public void setZFORE(BigDecimal aValue) {
		ZFORE=aValue;
	}
	public void setWAERK(String aValue) {
		WAERK=aValue;
	}
	public void setPSPIDSV(String aValue) {
		PSPIDSV=aValue;
	}
	public void setZHOGISV(String aValue) {
		ZHOGISV=aValue;
	}
	public void setDELDAT(String aValue) {
		DELDAT=aValue;
	}
	public void setZRMK(String aValue) {
		ZRMK=aValue;
	}
	public void setZRMK1(String aValue) {
		ZRMK1=aValue;
	}
	public void setVBELN(String aValue) {
		VBELN=aValue;
	}
	public void setPSPID(String aValue) {
		PSPID=aValue;
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
	public void setZAGNT(String aValue) {
		ZAGNT=aValue;
	}
	public void setZAGNT_NM(String aValue) {
		ZAGNT_NM=aValue;
	}
	public void setSAVE_STAT(String aValue) {
		SAVE_STAT=aValue;
	} 
	public void setLAND1(String aValue) {
		LAND1=aValue;
	} 
	public void setLAND1_NM(String aValue) {
		LAND1_NM=aValue;
	} 
	public void setZAREA(String aValue) {
		ZAREA=aValue;
	} 
	public void setZAREA_NM(String aValue) {
		ZAREA_NM=aValue;
	} 

	public void setGBN(String aValue) {
		GBN=aValue;
	}
	public void setGBN_NM(String aValue) {
		GBN_NM=aValue;
	} 
	public void setAMT00(BigDecimal aValue) {
		AMT00=aValue;
	}
	public void setAMT01(BigDecimal aValue) {
		AMT01=aValue;
	}
	public void setAMT02(BigDecimal aValue) {
		AMT02=aValue;
	}
	public void setAMT03(BigDecimal aValue) {
		AMT03=aValue;
	}
	public void setAMT04(BigDecimal aValue) {
		AMT04=aValue;
	}
	public void setAMT05(BigDecimal aValue) {
		AMT05=aValue;
	}
	public void setAMT06(BigDecimal aValue) {
		AMT06=aValue;
	}
	public void setAMT07(BigDecimal aValue) {
		AMT07=aValue;
	}
	public void setAMT08(BigDecimal aValue) {
		AMT08=aValue;
	}
	public void setAMT09(BigDecimal aValue) {
		AMT09=aValue;
	}
	public void setAMT10(BigDecimal aValue) {
		AMT10=aValue;
	}
	public void setAMT11(BigDecimal aValue) {
		AMT11=aValue;
	}
	public void setAMT12(BigDecimal aValue) {
		AMT12=aValue;
	}
	public void setAMT13(BigDecimal aValue) {
		AMT13=aValue;
	}
	public void setAMT14(BigDecimal aValue) {
		AMT14=aValue;
	}
	public void setAMT15(BigDecimal aValue) {
		AMT15=aValue;
	}
	public void setAMT16(BigDecimal aValue) {
		AMT16=aValue;
	}
	public void setAMT17(BigDecimal aValue) {
		AMT17=aValue;
	}
	
	
	

	public void setAMT18(BigDecimal aValue) {
		AMT18=aValue;
	}
	public void setAMT19(BigDecimal aValue) {
		AMT19=aValue;
	}
	public void setAMT20(BigDecimal aValue) {
		AMT20=aValue;
	}
	public void setAMT21(BigDecimal aValue) {
		AMT21=aValue;
	}
	public void setAMT22(BigDecimal aValue) {
		AMT22=aValue;
	}
	public void setAMT23(BigDecimal aValue) {
		AMT23=aValue;
	}
	public void setAMT24(BigDecimal aValue) {
		AMT24=aValue;
	}
	public void setAMT25(BigDecimal aValue) {
		AMT25=aValue;
	}
	public void setAMT26(BigDecimal aValue) {
		AMT26=aValue;
	}
	public void setAMT27(BigDecimal aValue) {
		AMT27=aValue;
	}
	public void setAMT28(BigDecimal aValue) {
		AMT28=aValue;
	}
	public void setAMT29(BigDecimal aValue) {
		AMT29=aValue;
	}
	
	

	public void setZSAYM(String aValue) {
		ZSAYM=aValue;
	}
	public void setZRQYM(String aValue) {
		ZRQYM=aValue;
	}
	public void setZYSYM(String aValue) {
		ZYSYM=aValue;
	}
	public void setNETWR_SA(BigDecimal aValue) {
		NETWR_SA=aValue;
	}
	public void setNETWR_RQ(BigDecimal aValue) {
		NETWR_RQ=aValue;
	}
	public void setCOLBI(BigDecimal aValue) {
		COLBI=aValue;
	}  
	public void setPOSID(String aValue) {
		POSID=aValue;
	} 
	public void setBIDDAT(String aValue) {
		BIDDAT=aValue;
	} 

	public void setATKLA(String aValue) {
		ATKLA=aValue;
	}   
	public void setATNAM(String aValue) {
		ATNAM=aValue;
	}   
	public void setVALUE(String aValue) {
		VALUE=aValue;
	}     
	public void setZCOST(BigDecimal aValue) {
		ZCOST=aValue;
	}
	public void setZPCOST(BigDecimal aValue) {
		ZPCOST=aValue;
	}
	
	
	
	public String getUFLAG(){
		return UFLAG;
	}  
	public void setUFLAG(String aValue) {
		UFLAG=aValue;
	}	
	public String getRTYPE(){
		return RTYPE;
	}  
	public void setRTYPE(String aValue) {
		RTYPE=aValue;
	}	
	
	public String getCODE(){
		return CODE;
	}  
	public void setCODE(String aValue) {
		CODE=aValue;
	}	
	
	public String getCODENAME(){
		return CODENAME;
	}  
	public void setCODENAME(String aValue) {
		CODENAME=aValue;
	}
	public String getZBDTYP() {
		return ZBDTYP;
	}
	public void setZBDTYP(String zBDTYP) {
		ZBDTYP = zBDTYP;
	}
	public String getZBDTYP_LNM() {
		return ZBDTYP_LNM;
	}
	public void setZBDTYP_LNM(String zBDTYP_LNM) {
		ZBDTYP_LNM = zBDTYP_LNM;
	}
	
	
	
}