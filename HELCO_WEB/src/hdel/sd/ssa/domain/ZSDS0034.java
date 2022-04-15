package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;
 

/**
 * 매출채권 명세 및 채권현황(청구별) 미수/부도 조회 (ZSDS0034) Serializable
 * @Comment 
 *     	- Ssa0020C(매출채권 명세 및 채권현황(청구별)) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZSDS0034   
		implements java.io.Serializable {

	public ZSDS0034() {
	}
	public java.lang.String BOSU;
	public java.lang.String BUKRS;
	public java.lang.String GJAHR;
	public java.lang.String BELNR;
	public java.lang.String KUNNR;
	public java.lang.String KIDNO;
	public java.lang.String LAND1;
	public java.lang.String LANDX;
	public java.lang.String XREF1;
	public java.lang.String XREF2;
	public java.lang.String XREF3;
	public java.lang.String VKBUT;
	public java.lang.String VKGRT;
	public java.lang.String KUNZ2;
	public java.lang.String KUNZT;
	public java.lang.String NAME;
	public java.lang.String LIFNR;
	public java.lang.String PNAME1;
	public java.lang.String KIDNO_T;
	public java.lang.String NAME1;
	public java.lang.String BRAN1;
	public java.lang.String ZFBDT;
	public java.lang.String GUBUN;
	public java.lang.String BDATE;
	public java.lang.String YDAY;
	public java.lang.String ZBEPJEO;
	public java.lang.String ZQEGEMSA;
	public java.lang.String BUDAT;
	public java.lang.String RATE;
	public java.lang.String WAERS;
	public java.math.BigDecimal NETWR;
	public java.math.BigDecimal GETWR;
	public java.math.BigDecimal LONG_AMT;
	public java.math.BigDecimal RETN_AMT;
	public java.math.BigDecimal CHMON;
	public java.math.BigDecimal CHCAN;
	public java.math.BigDecimal BAMT;
	public java.math.BigDecimal PYAMT;
	public java.math.BigDecimal REFWR;
	public java.lang.String PLDAT;
	public java.math.BigDecimal PLAN_AMT;
	public java.math.BigDecimal PLAN_A01;
	public java.math.BigDecimal PLAN_A02;
	public java.math.BigDecimal PLAN_A03;
	public java.math.BigDecimal PTOT_AMT;
	public java.math.BigDecimal PTOT_AMT1;
	public java.math.BigDecimal NOFIX_AMT;
	public java.lang.String INGTEXT;
	public java.lang.String REASON;
	public java.lang.String REASONT;
	public java.lang.String MEDAT01;
	public java.lang.String MEDAT02;
	public java.lang.String MEDAT03;
	public java.lang.String MEDAT04;
	public java.lang.String MEDAT05;
	public java.lang.String MEDAT06;
	public java.lang.String MEDAT07;
	public java.lang.String MEDAT08;
	public java.lang.String DEBTRT01;
	public java.lang.String DEBTRT02;
	public java.lang.String DEBTRT03;
	public java.lang.String DEBTRT04;
	public java.lang.String DEBTRT05;
	public java.lang.String DEBTRT06;
	public java.lang.String DEBTRT07;
	public java.lang.String DEBTRT08;
	public java.lang.String PY_VICNT;
	public java.lang.String MM_VIDAT01;
	public java.lang.String MM_VIDAT02;
	public java.lang.String MM_VIDAT03;
	public java.lang.String MM_VIDAT04;
	public java.lang.String MM_VIDAT05;
	public java.lang.String MM_VIDAT06;
	public java.lang.String MM_VIDAT07;
	public java.lang.String MM_VIDAT08;
	public java.lang.String MM_VIDAT09;
	public java.lang.String MM_VIDAT10;
	public java.lang.String MM_VIDAT11;
	public java.lang.String MM_VIDAT12;
	public java.math.BigDecimal PLAN_A04;
	public java.math.BigDecimal PLAN_A05;
	public java.math.BigDecimal PLAN_A06;
	public java.math.BigDecimal PLAN_A07;
	public java.math.BigDecimal PLAN_A08;
	public java.math.BigDecimal PLAN_A09;
	public java.math.BigDecimal PLAN_A10;
	public java.math.BigDecimal PLAN_A11;
	public java.math.BigDecimal PLAN_A12;
	public java.math.BigDecimal PLAN_A13;
	public java.lang.String STATUS;
	public int QQ_VIDAT01;
	public int QQ_VIDAT02;
	public int QQ_VIDAT03;
	public int QQ_VIDAT04;
	public int QQ_VIDAT05;
	public int QQ_VIDAT06;
	public int QQ_VIDAT07;
	public int QQ_VIDAT08;
	public int QQ_VIDAT09;
	public int QQ_VIDAT10;
	public int QQ_VIDAT11;
	public int QQ_VIDAT12;
	public int QQ_CNT01;
	public int QQ_CNT02;
	public int QQ_CNT03;
	public int QQ_CNT04;
	public int QQ_CNT05;
	public int QQ_CNT06;
	public int QQ_CNT07;
	public int QQ_CNT08;
	public int QQ_PROJ;
	public java.math.BigDecimal LAWAMT;
	public java.math.BigDecimal ETCAMT;
	public java.lang.String NPLDAT;
	public java.lang.String AMTGBN;
	public java.lang.String PSORT;
	public java.lang.String AUART;
	public java.lang.String SPART;
	public java.lang.String AGING;
	public java.math.BigDecimal SHORT_AMT;
	public java.math.BigDecimal MON_AMT6;
	public java.lang.String getBOSU(){
		return BOSU;
	}
	public java.lang.String getBUKRS(){
		return BUKRS;
	}
	public java.lang.String getGJAHR(){
		return GJAHR;
	}
	public java.lang.String getBELNR(){
		return BELNR;
	}
	public java.lang.String getKUNNR(){
		return KUNNR;
	}
	public java.lang.String getKIDNO(){
		return KIDNO;
	}
	public java.lang.String getLAND1(){
		return LAND1;
	}
	public java.lang.String getLANDX(){
		return LANDX;
	}
	public java.lang.String getXREF1(){
		return XREF1;
	}
	public java.lang.String getXREF2(){
		return XREF2;
	}
	public java.lang.String getXREF3(){
		return XREF3;
	}
	public java.lang.String getVKBUT(){
		return VKBUT;
	}
	public java.lang.String getVKGRT(){
		return VKGRT;
	}
	public java.lang.String getKUNZ2(){
		return KUNZ2;
	}
	public java.lang.String getKUNZT(){
		return KUNZT;
	}
	public java.lang.String getNAME(){
		return NAME;
	}
	public java.lang.String getLIFNR(){
		return LIFNR;
	}
	public java.lang.String getPNAME1(){
		return PNAME1;
	}
	public java.lang.String getKIDNO_T(){
		return KIDNO_T;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.lang.String getBRAN1(){
		return BRAN1;
	}
	public java.lang.String getZFBDT(){
		return ZFBDT;
	}
	public java.lang.String getGUBUN(){
		return GUBUN;
	}
	public java.lang.String getBDATE(){
		return BDATE;
	}
	public java.lang.String getYDAY(){
		return YDAY;
	}
	public java.lang.String getZBEPJEO(){
		return ZBEPJEO;
	}
	public java.lang.String getZQEGEMSA(){
		return ZQEGEMSA;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.lang.String getRATE(){
		return RATE;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.math.BigDecimal getGETWR(){
		return GETWR;
	}
	public java.math.BigDecimal getLONG_AMT(){
		return LONG_AMT;
	}
	public java.math.BigDecimal getRETN_AMT(){
		return RETN_AMT;
	}
	public java.math.BigDecimal getCHMON(){
		return CHMON;
	}
	public java.math.BigDecimal getCHCAN(){
		return CHCAN;
	}
	public java.math.BigDecimal getBAMT(){
		return BAMT;
	}
	public java.math.BigDecimal getPYAMT(){
		return PYAMT;
	}
	public java.math.BigDecimal getREFWR(){
		return REFWR;
	}
	public java.lang.String getPLDAT(){
		return PLDAT;
	}
	public java.math.BigDecimal getPLAN_AMT(){
		return PLAN_AMT;
	}
	public java.math.BigDecimal getPLAN_A01(){
		return PLAN_A01;
	}
	public java.math.BigDecimal getPLAN_A02(){
		return PLAN_A02;
	}
	public java.math.BigDecimal getPLAN_A03(){
		return PLAN_A03;
	}
	public java.math.BigDecimal getPTOT_AMT(){
		return PTOT_AMT;
	}
	public java.math.BigDecimal getPTOT_AMT1(){
		return PTOT_AMT1;
	}
	public java.math.BigDecimal getNOFIX_AMT(){
		return NOFIX_AMT;
	}
	public java.lang.String getINGTEXT(){
		return INGTEXT;
	}
	public java.lang.String getREASON(){
		return REASON;
	}
	public java.lang.String getREASONT(){
		return REASONT;
	}
	public java.lang.String getMEDAT01(){
		return MEDAT01;
	}
	public java.lang.String getMEDAT02(){
		return MEDAT02;
	}
	public java.lang.String getMEDAT03(){
		return MEDAT03;
	}
	public java.lang.String getMEDAT04(){
		return MEDAT04;
	}
	public java.lang.String getMEDAT05(){
		return MEDAT05;
	}
	public java.lang.String getMEDAT06(){
		return MEDAT06;
	}
	public java.lang.String getMEDAT07(){
		return MEDAT07;
	}
	public java.lang.String getMEDAT08(){
		return MEDAT08;
	}
	public java.lang.String getDEBTRT01(){
		return DEBTRT01;
	}
	public java.lang.String getDEBTRT02(){
		return DEBTRT02;
	}
	public java.lang.String getDEBTRT03(){
		return DEBTRT03;
	}
	public java.lang.String getDEBTRT04(){
		return DEBTRT04;
	}
	public java.lang.String getDEBTRT05(){
		return DEBTRT05;
	}
	public java.lang.String getDEBTRT06(){
		return DEBTRT06;
	}
	public java.lang.String getDEBTRT07(){
		return DEBTRT07;
	}
	public java.lang.String getDEBTRT08(){
		return DEBTRT08;
	}
	public java.lang.String getPY_VICNT(){
		return PY_VICNT;
	}
	public java.lang.String getMM_VIDAT01(){
		return MM_VIDAT01;
	}
	public java.lang.String getMM_VIDAT02(){
		return MM_VIDAT02;
	}
	public java.lang.String getMM_VIDAT03(){
		return MM_VIDAT03;
	}
	public java.lang.String getMM_VIDAT04(){
		return MM_VIDAT04;
	}
	public java.lang.String getMM_VIDAT05(){
		return MM_VIDAT05;
	}
	public java.lang.String getMM_VIDAT06(){
		return MM_VIDAT06;
	}
	public java.lang.String getMM_VIDAT07(){
		return MM_VIDAT07;
	}
	public java.lang.String getMM_VIDAT08(){
		return MM_VIDAT08;
	}
	public java.lang.String getMM_VIDAT09(){
		return MM_VIDAT09;
	}
	public java.lang.String getMM_VIDAT10(){
		return MM_VIDAT10;
	}
	public java.lang.String getMM_VIDAT11(){
		return MM_VIDAT11;
	}
	public java.lang.String getMM_VIDAT12(){
		return MM_VIDAT12;
	}
	public java.math.BigDecimal getPLAN_A04(){
		return PLAN_A04;
	}
	public java.math.BigDecimal getPLAN_A05(){
		return PLAN_A05;
	}
	public java.math.BigDecimal getPLAN_A06(){
		return PLAN_A06;
	}
	public java.math.BigDecimal getPLAN_A07(){
		return PLAN_A07;
	}
	public java.math.BigDecimal getPLAN_A08(){
		return PLAN_A08;
	}
	public java.math.BigDecimal getPLAN_A09(){
		return PLAN_A09;
	}
	public java.math.BigDecimal getPLAN_A10(){
		return PLAN_A10;
	}
	public java.math.BigDecimal getPLAN_A11(){
		return PLAN_A11;
	}
	public java.math.BigDecimal getPLAN_A12(){
		return PLAN_A12;
	}
	public java.math.BigDecimal getPLAN_A13(){
		return PLAN_A13;
	}
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public int getQQ_VIDAT01(){
		return QQ_VIDAT01;
	}
	public int getQQ_VIDAT02(){
		return QQ_VIDAT02;
	}
	public int getQQ_VIDAT03(){
		return QQ_VIDAT03;
	}
	public int getQQ_VIDAT04(){
		return QQ_VIDAT04;
	}
	public int getQQ_VIDAT05(){
		return QQ_VIDAT05;
	}
	public int getQQ_VIDAT06(){
		return QQ_VIDAT06;
	}
	public int getQQ_VIDAT07(){
		return QQ_VIDAT07;
	}
	public int getQQ_VIDAT08(){
		return QQ_VIDAT08;
	}
	public int getQQ_VIDAT09(){
		return QQ_VIDAT09;
	}
	public int getQQ_VIDAT10(){
		return QQ_VIDAT10;
	}
	public int getQQ_VIDAT11(){
		return QQ_VIDAT11;
	}
	public int getQQ_VIDAT12(){
		return QQ_VIDAT12;
	}
	public int getQQ_CNT01(){
		return QQ_CNT01;
	}
	public int getQQ_CNT02(){
		return QQ_CNT02;
	}
	public int getQQ_CNT03(){
		return QQ_CNT03;
	}
	public int getQQ_CNT04(){
		return QQ_CNT04;
	}
	public int getQQ_CNT05(){
		return QQ_CNT05;
	}
	public int getQQ_CNT06(){
		return QQ_CNT06;
	}
	public int getQQ_CNT07(){
		return QQ_CNT07;
	}
	public int getQQ_CNT08(){
		return QQ_CNT08;
	}
	public int getQQ_PROJ(){
		return QQ_PROJ;
	}
	public java.math.BigDecimal getLAWAMT(){
		return LAWAMT;
	}
	public java.math.BigDecimal getETCAMT(){
		return ETCAMT;
	}
	public java.lang.String getNPLDAT(){
		return NPLDAT;
	}
	public java.lang.String getAMTGBN(){
		return AMTGBN;
	}
	public java.lang.String getPSORT(){
		return PSORT;
	}
	public java.lang.String getAUART(){
		return AUART;
	}
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getAGING(){
		return AGING;
	}
	public java.math.BigDecimal getSHORT_AMT(){
		return SHORT_AMT;
	}
	public java.math.BigDecimal getMON_AMT6(){
		return MON_AMT6;
	}
	public void setBOSU(java.lang.String aValue) {
		BOSU=aValue;
	}
	public void setBUKRS(java.lang.String aValue) {
		BUKRS=aValue;
	}
	public void setGJAHR(java.lang.String aValue) {
		GJAHR=aValue;
	}
	public void setBELNR(java.lang.String aValue) {
		BELNR=aValue;
	}
	public void setKUNNR(java.lang.String aValue) {
		KUNNR=aValue;
	}
	public void setKIDNO(java.lang.String aValue) {
		KIDNO=aValue;
	}
	public void setLAND1(java.lang.String aValue) {
		LAND1=aValue;
	}
	public void setLANDX(java.lang.String aValue) {
		LANDX=aValue;
	}
	public void setXREF1(java.lang.String aValue) {
		XREF1=aValue;
	}
	public void setXREF2(java.lang.String aValue) {
		XREF2=aValue;
	}
	public void setXREF3(java.lang.String aValue) {
		XREF3=aValue;
	}
	public void setVKBUT(java.lang.String aValue) {
		VKBUT=aValue;
	}
	public void setVKGRT(java.lang.String aValue) {
		VKGRT=aValue;
	}
	public void setKUNZ2(java.lang.String aValue) {
		KUNZ2=aValue;
	}
	public void setKUNZT(java.lang.String aValue) {
		KUNZT=aValue;
	}
	public void setNAME(java.lang.String aValue) {
		NAME=aValue;
	}
	public void setLIFNR(java.lang.String aValue) {
		LIFNR=aValue;
	}
	public void setPNAME1(java.lang.String aValue) {
		PNAME1=aValue;
	}
	public void setKIDNO_T(java.lang.String aValue) {
		KIDNO_T=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setBRAN1(java.lang.String aValue) {
		BRAN1=aValue;
	}
	public void setZFBDT(java.lang.String aValue) {
		ZFBDT=aValue;
	}
	public void setGUBUN(java.lang.String aValue) {
		GUBUN=aValue;
	}
	public void setBDATE(java.lang.String aValue) {
		BDATE=aValue;
	}
	public void setYDAY(java.lang.String aValue) {
		YDAY=aValue;
	}
	public void setZBEPJEO(java.lang.String aValue) {
		ZBEPJEO=aValue;
	}
	public void setZQEGEMSA(java.lang.String aValue) {
		ZQEGEMSA=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setRATE(java.lang.String aValue) {
		RATE=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setGETWR(java.math.BigDecimal aValue) {
		GETWR=aValue;
	}
	public void setLONG_AMT(java.math.BigDecimal aValue) {
		LONG_AMT=aValue;
	}
	public void setRETN_AMT(java.math.BigDecimal aValue) {
		RETN_AMT=aValue;
	}
	public void setCHMON(java.math.BigDecimal aValue) {
		CHMON=aValue;
	}
	public void setCHCAN(java.math.BigDecimal aValue) {
		CHCAN=aValue;
	}
	public void setBAMT(java.math.BigDecimal aValue) {
		BAMT=aValue;
	}
	public void setPYAMT(java.math.BigDecimal aValue) {
		PYAMT=aValue;
	}
	public void setREFWR(java.math.BigDecimal aValue) {
		REFWR=aValue;
	}
	public void setPLDAT(java.lang.String aValue) {
		PLDAT=aValue;
	}
	public void setPLAN_AMT(java.math.BigDecimal aValue) {
		PLAN_AMT=aValue;
	}
	public void setPLAN_A01(java.math.BigDecimal aValue) {
		PLAN_A01=aValue;
	}
	public void setPLAN_A02(java.math.BigDecimal aValue) {
		PLAN_A02=aValue;
	}
	public void setPLAN_A03(java.math.BigDecimal aValue) {
		PLAN_A03=aValue;
	}
	public void setPTOT_AMT(java.math.BigDecimal aValue) {
		PTOT_AMT=aValue;
	}
	public void setPTOT_AMT1(java.math.BigDecimal aValue) {
		PTOT_AMT1=aValue;
	}
	public void setNOFIX_AMT(java.math.BigDecimal aValue) {
		NOFIX_AMT=aValue;
	}
	public void setINGTEXT(java.lang.String aValue) {
		INGTEXT=aValue;
	}
	public void setREASON(java.lang.String aValue) {
		REASON=aValue;
	}
	public void setREASONT(java.lang.String aValue) {
		REASONT=aValue;
	}
	public void setMEDAT01(java.lang.String aValue) {
		MEDAT01=aValue;
	}
	public void setMEDAT02(java.lang.String aValue) {
		MEDAT02=aValue;
	}
	public void setMEDAT03(java.lang.String aValue) {
		MEDAT03=aValue;
	}
	public void setMEDAT04(java.lang.String aValue) {
		MEDAT04=aValue;
	}
	public void setMEDAT05(java.lang.String aValue) {
		MEDAT05=aValue;
	}
	public void setMEDAT06(java.lang.String aValue) {
		MEDAT06=aValue;
	}
	public void setMEDAT07(java.lang.String aValue) {
		MEDAT07=aValue;
	}
	public void setMEDAT08(java.lang.String aValue) {
		MEDAT08=aValue;
	}
	public void setDEBTRT01(java.lang.String aValue) {
		DEBTRT01=aValue;
	}
	public void setDEBTRT02(java.lang.String aValue) {
		DEBTRT02=aValue;
	}
	public void setDEBTRT03(java.lang.String aValue) {
		DEBTRT03=aValue;
	}
	public void setDEBTRT04(java.lang.String aValue) {
		DEBTRT04=aValue;
	}
	public void setDEBTRT05(java.lang.String aValue) {
		DEBTRT05=aValue;
	}
	public void setDEBTRT06(java.lang.String aValue) {
		DEBTRT06=aValue;
	}
	public void setDEBTRT07(java.lang.String aValue) {
		DEBTRT07=aValue;
	}
	public void setDEBTRT08(java.lang.String aValue) {
		DEBTRT08=aValue;
	}
	public void setPY_VICNT(java.lang.String aValue) {
		PY_VICNT=aValue;
	}
	public void setMM_VIDAT01(java.lang.String aValue) {
		MM_VIDAT01=aValue;
	}
	public void setMM_VIDAT02(java.lang.String aValue) {
		MM_VIDAT02=aValue;
	}
	public void setMM_VIDAT03(java.lang.String aValue) {
		MM_VIDAT03=aValue;
	}
	public void setMM_VIDAT04(java.lang.String aValue) {
		MM_VIDAT04=aValue;
	}
	public void setMM_VIDAT05(java.lang.String aValue) {
		MM_VIDAT05=aValue;
	}
	public void setMM_VIDAT06(java.lang.String aValue) {
		MM_VIDAT06=aValue;
	}
	public void setMM_VIDAT07(java.lang.String aValue) {
		MM_VIDAT07=aValue;
	}
	public void setMM_VIDAT08(java.lang.String aValue) {
		MM_VIDAT08=aValue;
	}
	public void setMM_VIDAT09(java.lang.String aValue) {
		MM_VIDAT09=aValue;
	}
	public void setMM_VIDAT10(java.lang.String aValue) {
		MM_VIDAT10=aValue;
	}
	public void setMM_VIDAT11(java.lang.String aValue) {
		MM_VIDAT11=aValue;
	}
	public void setMM_VIDAT12(java.lang.String aValue) {
		MM_VIDAT12=aValue;
	}
	public void setPLAN_A04(java.math.BigDecimal aValue) {
		PLAN_A04=aValue;
	}
	public void setPLAN_A05(java.math.BigDecimal aValue) {
		PLAN_A05=aValue;
	}
	public void setPLAN_A06(java.math.BigDecimal aValue) {
		PLAN_A06=aValue;
	}
	public void setPLAN_A07(java.math.BigDecimal aValue) {
		PLAN_A07=aValue;
	}
	public void setPLAN_A08(java.math.BigDecimal aValue) {
		PLAN_A08=aValue;
	}
	public void setPLAN_A09(java.math.BigDecimal aValue) {
		PLAN_A09=aValue;
	}
	public void setPLAN_A10(java.math.BigDecimal aValue) {
		PLAN_A10=aValue;
	}
	public void setPLAN_A11(java.math.BigDecimal aValue) {
		PLAN_A11=aValue;
	}
	public void setPLAN_A12(java.math.BigDecimal aValue) {
		PLAN_A12=aValue;
	}
	public void setPLAN_A13(java.math.BigDecimal aValue) {
		PLAN_A13=aValue;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	public void setQQ_VIDAT01(int aValue) {
		QQ_VIDAT01=aValue;
	}
	public void setQQ_VIDAT02(int aValue) {
		QQ_VIDAT02=aValue;
	}
	public void setQQ_VIDAT03(int aValue) {
		QQ_VIDAT03=aValue;
	}
	public void setQQ_VIDAT04(int aValue) {
		QQ_VIDAT04=aValue;
	}
	public void setQQ_VIDAT05(int aValue) {
		QQ_VIDAT05=aValue;
	}
	public void setQQ_VIDAT06(int aValue) {
		QQ_VIDAT06=aValue;
	}
	public void setQQ_VIDAT07(int aValue) {
		QQ_VIDAT07=aValue;
	}
	public void setQQ_VIDAT08(int aValue) {
		QQ_VIDAT08=aValue;
	}
	public void setQQ_VIDAT09(int aValue) {
		QQ_VIDAT09=aValue;
	}
	public void setQQ_VIDAT10(int aValue) {
		QQ_VIDAT10=aValue;
	}
	public void setQQ_VIDAT11(int aValue) {
		QQ_VIDAT11=aValue;
	}
	public void setQQ_VIDAT12(int aValue) {
		QQ_VIDAT12=aValue;
	}
	public void setQQ_CNT01(int aValue) {
		QQ_CNT01=aValue;
	}
	public void setQQ_CNT02(int aValue) {
		QQ_CNT02=aValue;
	}
	public void setQQ_CNT03(int aValue) {
		QQ_CNT03=aValue;
	}
	public void setQQ_CNT04(int aValue) {
		QQ_CNT04=aValue;
	}
	public void setQQ_CNT05(int aValue) {
		QQ_CNT05=aValue;
	}
	public void setQQ_CNT06(int aValue) {
		QQ_CNT06=aValue;
	}
	public void setQQ_CNT07(int aValue) {
		QQ_CNT07=aValue;
	}
	public void setQQ_CNT08(int aValue) {
		QQ_CNT08=aValue;
	}
	public void setQQ_PROJ(int aValue) {
		QQ_PROJ=aValue;
	}
	public void setLAWAMT(java.math.BigDecimal aValue) {
		LAWAMT=aValue;
	}
	public void setETCAMT(java.math.BigDecimal aValue) {
		ETCAMT=aValue;
	}
	public void setNPLDAT(java.lang.String aValue) {
		NPLDAT=aValue;
	}
	public void setAMTGBN(java.lang.String aValue) {
		AMTGBN=aValue;
	}
	public void setPSORT(java.lang.String aValue) {
		PSORT=aValue;
	}
	public void setAUART(java.lang.String aValue) {
		AUART=aValue;
	}
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setAGING(java.lang.String aValue) {
		AGING=aValue;
	}
	public void setSHORT_AMT(java.math.BigDecimal aValue) {
		SHORT_AMT=aValue;
	}
	public void setMON_AMT6(java.math.BigDecimal aValue) {
		MON_AMT6=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0034.class, true);

	/**
 	* Return type metadata object
 	*/
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
    		return typeDesc;
	}

	/**
	 * Get Custom Serializer
 	*/
	public static org.apache.axis.encoding.Serializer getSerializer(
      	 java.lang.String mechType, 
      	 java.lang.Class _javaType,  
     	  javax.xml.namespace.QName _xmlType) {
    		return 
      		new  org.apache.axis.encoding.ser.BeanSerializer(
       		 _javaType, _xmlType, typeDesc);
	}

	/**
 	* Get Custom Deserializer
 	*/
	public static org.apache.axis.encoding.Deserializer getDeserializer(
     	  java.lang.String mechType, 
     	  java.lang.Class _javaType,  
     	  javax.xml.namespace.QName _xmlType) {
    		return 
      		new  org.apache.axis.encoding.ser.BeanDeserializer(
     	 	  _javaType, _xmlType, typeDesc);

	} static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0034"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BOSU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BOSU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUKRS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUKRS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GJAHR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GJAHR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BELNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BELNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KIDNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KIDNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LAND1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAND1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LANDX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LANDX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("XREF1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "XREF1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("XREF2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "XREF2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("XREF3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "XREF3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNZ2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNZ2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNZT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNZT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PNAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PNAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KIDNO_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KIDNO_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BRAN1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BRAN1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZFBDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZFBDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("YDAY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "YDAY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZBEPJEO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZBEPJEO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZQEGEMSA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQEGEMSA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LONG_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LONG_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RETN_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RETN_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHMON");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHMON"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHCAN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHCAN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PYAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PYAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REFWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REFWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PTOT_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PTOT_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PTOT_AMT1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PTOT_AMT1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NOFIX_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NOFIX_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INGTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INGTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REASON");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REASON"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REASONT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REASONT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PY_VICNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PY_VICNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT09");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT09"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT10");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT10"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT11");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT11"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MM_VIDAT12");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MM_VIDAT12"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A09");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A09"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A10");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A10"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A11");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A11"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A12");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A12"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A13");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A13"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT09");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT09"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT10");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT10"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT11");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT11"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_VIDAT12");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_VIDAT12"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_PROJ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_PROJ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LAWAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAWAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ETCAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ETCAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NPLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NPLDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMTGBN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMTGBN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSORT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSORT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AGING");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AGING"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SHORT_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SHORT_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MON_AMT6");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MON_AMT6"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("BOSU",(short)1,1);
		ds.addColumn("BUKRS",(short)1,4);
		ds.addColumn("GJAHR",(short)1,4);
		ds.addColumn("BELNR",(short)1,10);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("KIDNO",(short)1,30);
		ds.addColumn("LAND1",(short)1,3);
		ds.addColumn("LANDX",(short)1,15);
		ds.addColumn("XREF1",(short)1,12);
		ds.addColumn("XREF2",(short)1,12);
		ds.addColumn("XREF3",(short)1,20);
		ds.addColumn("VKBUT",(short)1,20);
		ds.addColumn("VKGRT",(short)1,20);
		ds.addColumn("KUNZ2",(short)1,10);
		ds.addColumn("KUNZT",(short)1,35);
		ds.addColumn("NAME",(short)1,35);
		ds.addColumn("LIFNR",(short)1,10);
		ds.addColumn("PNAME1",(short)1,35);
		ds.addColumn("KIDNO_T",(short)1,35);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("BRAN1",(short)1,10);
		ds.addColumn("ZFBDT",(short)1,8);
		ds.addColumn("GUBUN",(short)1,1);
		ds.addColumn("BDATE",(short)1,8);
		ds.addColumn("YDAY",(short)1,5);
		ds.addColumn("ZBEPJEO",(short)1,8);
		ds.addColumn("ZQEGEMSA",(short)1,8);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("RATE",(short)1,2);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("GETWR",(short)4,13);
		ds.addColumn("LONG_AMT",(short)4,13);
		ds.addColumn("RETN_AMT",(short)4,13);
		ds.addColumn("CHMON",(short)4,13);
		ds.addColumn("CHCAN",(short)4,13);
		ds.addColumn("BAMT",(short)4,13);
		ds.addColumn("PYAMT",(short)4,13);
		ds.addColumn("REFWR",(short)4,13);
		ds.addColumn("PLDAT",(short)1,8);
		ds.addColumn("PLAN_AMT",(short)4,13);
		ds.addColumn("PLAN_A01",(short)4,13);
		ds.addColumn("PLAN_A02",(short)4,13);
		ds.addColumn("PLAN_A03",(short)4,13);
		ds.addColumn("PTOT_AMT",(short)4,13);
		ds.addColumn("PTOT_AMT1",(short)4,13);
		ds.addColumn("NOFIX_AMT",(short)4,13);
		ds.addColumn("INGTEXT",(short)1,1024);
		ds.addColumn("REASON",(short)1,2);
		ds.addColumn("REASONT",(short)1,30);
		ds.addColumn("MEDAT01",(short)1,8);
		ds.addColumn("MEDAT02",(short)1,8);
		ds.addColumn("MEDAT03",(short)1,8);
		ds.addColumn("MEDAT04",(short)1,8);
		ds.addColumn("MEDAT05",(short)1,8);
		ds.addColumn("MEDAT06",(short)1,8);
		ds.addColumn("MEDAT07",(short)1,8);
		ds.addColumn("MEDAT08",(short)1,8);
		ds.addColumn("DEBTRT01",(short)1,30);
		ds.addColumn("DEBTRT02",(short)1,30);
		ds.addColumn("DEBTRT03",(short)1,30);
		ds.addColumn("DEBTRT04",(short)1,30);
		ds.addColumn("DEBTRT05",(short)1,30);
		ds.addColumn("DEBTRT06",(short)1,30);
		ds.addColumn("DEBTRT07",(short)1,30);
		ds.addColumn("DEBTRT08",(short)1,30);
		ds.addColumn("PY_VICNT",(short)1,5);
		ds.addColumn("MM_VIDAT01",(short)1,8);
		ds.addColumn("MM_VIDAT02",(short)1,8);
		ds.addColumn("MM_VIDAT03",(short)1,8);
		ds.addColumn("MM_VIDAT04",(short)1,8);
		ds.addColumn("MM_VIDAT05",(short)1,8);
		ds.addColumn("MM_VIDAT06",(short)1,8);
		ds.addColumn("MM_VIDAT07",(short)1,8);
		ds.addColumn("MM_VIDAT08",(short)1,8);
		ds.addColumn("MM_VIDAT09",(short)1,8);
		ds.addColumn("MM_VIDAT10",(short)1,8);
		ds.addColumn("MM_VIDAT11",(short)1,8);
		ds.addColumn("MM_VIDAT12",(short)1,8);
		ds.addColumn("PLAN_A04",(short)4,13);
		ds.addColumn("PLAN_A05",(short)4,13);
		ds.addColumn("PLAN_A06",(short)4,13);
		ds.addColumn("PLAN_A07",(short)4,13);
		ds.addColumn("PLAN_A08",(short)4,13);
		ds.addColumn("PLAN_A09",(short)4,13);
		ds.addColumn("PLAN_A10",(short)4,13);
		ds.addColumn("PLAN_A11",(short)4,13);
		ds.addColumn("PLAN_A12",(short)4,13);
		ds.addColumn("PLAN_A13",(short)4,13);
		ds.addColumn("STATUS",(short)1,4);
		ds.addColumn("QQ_VIDAT01",(short)2,10);
		ds.addColumn("QQ_VIDAT02",(short)2,10);
		ds.addColumn("QQ_VIDAT03",(short)2,10);
		ds.addColumn("QQ_VIDAT04",(short)2,10);
		ds.addColumn("QQ_VIDAT05",(short)2,10);
		ds.addColumn("QQ_VIDAT06",(short)2,10);
		ds.addColumn("QQ_VIDAT07",(short)2,10);
		ds.addColumn("QQ_VIDAT08",(short)2,10);
		ds.addColumn("QQ_VIDAT09",(short)2,10);
		ds.addColumn("QQ_VIDAT10",(short)2,10);
		ds.addColumn("QQ_VIDAT11",(short)2,10);
		ds.addColumn("QQ_VIDAT12",(short)2,10);
		ds.addColumn("QQ_CNT01",(short)2,10);
		ds.addColumn("QQ_CNT02",(short)2,10);
		ds.addColumn("QQ_CNT03",(short)2,10);
		ds.addColumn("QQ_CNT04",(short)2,10);
		ds.addColumn("QQ_CNT05",(short)2,10);
		ds.addColumn("QQ_CNT06",(short)2,10);
		ds.addColumn("QQ_CNT07",(short)2,10);
		ds.addColumn("QQ_CNT08",(short)2,10);
		ds.addColumn("QQ_PROJ",(short)2,10);
		ds.addColumn("LAWAMT",(short)4,13);
		ds.addColumn("ETCAMT",(short)4,13);
		ds.addColumn("NPLDAT",(short)1,5);
		ds.addColumn("AMTGBN",(short)1,1);
		ds.addColumn("PSORT",(short)1,1);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("AGING",(short)1,5);
		ds.addColumn("SHORT_AMT",(short)4,13);
		ds.addColumn("MON_AMT6",(short)4,13);
		return ds;
	}

}