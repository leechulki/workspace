package hdel.sd.sbp.domain;

import hdel.lib.domain.CommonDomain;
import java.math.*;
  

/**
 * 사업계획관리(Sbp00101) CommonDomain
 * @Comment 
 *     	- Sbp0010C(사업계획관리) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class Sbp00101 extends CommonDomain {
	
	public String MANDT;
	public String SONNR;
	public String QTNUM;
	public String QTSER;
	public String QTSEQ;
	public String BLGRP;
	public String BLSEQ;
	public String BLNUM;
	public String GRMAT;
	public String RQMAT;
	public String TWAER;
	public String MEINH_RQ;
	public String MEINH_MH;
	public String ADJ;
	public BigDecimal BDMNG;
	public BigDecimal ZCPMRAW;
	public BigDecimal ZCPMRAW_A;
	public BigDecimal ZCPMRAW_T;
	public BigDecimal ZCPMCOM;
	public BigDecimal ZCPDLAB;
	public BigDecimal ZCPDEXP;
	public BigDecimal ZCPDSCT;
	public BigDecimal ZCPILAB;
	public BigDecimal ZCPIMEN;
	public BigDecimal ZCBIMEN;
	public BigDecimal ZCPRICE;
	public BigDecimal ZCTOTAL;
	public BigDecimal ZACTLAB1;
	public BigDecimal ZACTMAC1;
	public String SALES;
	public String MODEL;
	public String CRDAT;
	public String CRNAM;
	public String getMANDT(){
		return MANDT;
	}
	public String getSONNR(){
		return SONNR;
	}
	public String getQTNUM(){
		return QTNUM;
	}
	public String getQTSER(){
		return QTSER;
	}
	public String getQTSEQ(){
		return QTSEQ;
	}
	public String getBLGRP(){
		return BLGRP;
	}
	public String getBLSEQ(){
		return BLSEQ;
	}
	public String getBLNUM(){
		return BLNUM;
	}
	public String getGRMAT(){
		return GRMAT;
	}
	public String getRQMAT(){
		return RQMAT;
	}
	public String getTWAER(){
		return TWAER;
	}
	public String getMEINH_RQ(){
		return MEINH_RQ;
	}
	public String getMEINH_MH(){
		return MEINH_MH;
	}
	public String getADJ(){
		return ADJ;
	}
	public BigDecimal getBDMNG(){
		return BDMNG;
	}
	public BigDecimal getZCPMRAW(){
		return ZCPMRAW;
	}
	public BigDecimal getZCPMRAW_A(){
		return ZCPMRAW_A;
	}
	public BigDecimal getZCPMRAW_T(){
		return ZCPMRAW_T;
	}
	public BigDecimal getZCPMCOM(){
		return ZCPMCOM;
	}
	public BigDecimal getZCPDLAB(){
		return ZCPDLAB;
	}
	public BigDecimal getZCPDEXP(){
		return ZCPDEXP;
	}
	public BigDecimal getZCPDSCT(){
		return ZCPDSCT;
	}
	public BigDecimal getZCPILAB(){
		return ZCPILAB;
	}
	public BigDecimal getZCPIMEN(){
		return ZCPIMEN;
	}
	public BigDecimal getZCBIMEN(){
		return ZCBIMEN;
	}
	public BigDecimal getZCPRICE(){
		return ZCPRICE;
	}
	public BigDecimal getZCTOTAL(){
		return ZCTOTAL;
	}
	public BigDecimal getZACTLAB1(){
		return ZACTLAB1;
	}
	public BigDecimal getZACTMAC1(){
		return ZACTMAC1;
	}
	public String getSALES(){
		return SALES;
	}
	public String getMODEL(){
		return MODEL;
	}
	public String getCRDAT(){
		return CRDAT;
	}
	public String getCRNAM(){
		return CRNAM;
	}
	public void setMANDT(String aValue) {
		MANDT=aValue;
	}
	public void setSONNR(String aValue) {
		SONNR=aValue;
	}
	public void setQTNUM(String aValue) {
		QTNUM=aValue;
	}
	public void setQTSER(String aValue) {
		QTSER=aValue;
	}
	public void setQTSEQ(String aValue) {
		QTSEQ=aValue;
	}
	public void setBLGRP(String aValue) {
		BLGRP=aValue;
	}
	public void setBLSEQ(String aValue) {
		BLSEQ=aValue;
	}
	public void setBLNUM(String aValue) {
		BLNUM=aValue;
	}
	public void setGRMAT(String aValue) {
		GRMAT=aValue;
	}
	public void setRQMAT(String aValue) {
		RQMAT=aValue;
	}
	public void setTWAER(String aValue) {
		TWAER=aValue;
	}
	public void setMEINH_RQ(String aValue) {
		MEINH_RQ=aValue;
	}
	public void setMEINH_MH(String aValue) {
		MEINH_MH=aValue;
	}
	public void setADJ(String aValue) {
		ADJ=aValue;
	}
	public void setBDMNG(BigDecimal aValue) {
		BDMNG=aValue;
	}
	public void setZCPMRAW(BigDecimal aValue) {
		ZCPMRAW=aValue;
	}
	public void setZCPMRAW_A(BigDecimal aValue) {
		ZCPMRAW_A=aValue;
	}
	public void setZCPMRAW_T(BigDecimal aValue) {
		ZCPMRAW_T=aValue;
	}
	public void setZCPMCOM(BigDecimal aValue) {
		ZCPMCOM=aValue;
	}
	public void setZCPDLAB(BigDecimal aValue) {
		ZCPDLAB=aValue;
	}
	public void setZCPDEXP(BigDecimal aValue) {
		ZCPDEXP=aValue;
	}
	public void setZCPDSCT(BigDecimal aValue) {
		ZCPDSCT=aValue;
	}
	public void setZCPILAB(BigDecimal aValue) {
		ZCPILAB=aValue;
	}
	public void setZCPIMEN(BigDecimal aValue) {
		ZCPIMEN=aValue;
	}
	public void setZCBIMEN(BigDecimal aValue) {
		ZCBIMEN=aValue;
	}
	public void setZCPRICE(BigDecimal aValue) {
		ZCPRICE=aValue;
	}
	public void setZCTOTAL(BigDecimal aValue) {
		ZCTOTAL=aValue;
	}
	public void setZACTLAB1(BigDecimal aValue) {
		ZACTLAB1=aValue;
	}
	public void setZACTMAC1(BigDecimal aValue) {
		ZACTMAC1=aValue;
	}
	public void setSALES(String aValue) {
		SALES=aValue;
	}
	public void setMODEL(String aValue) {
		MODEL=aValue;
	}
	public void setCRDAT(String aValue) {
		CRDAT=aValue;
	}
	public void setCRNAM(String aValue) {
		CRNAM=aValue;
	} 
}