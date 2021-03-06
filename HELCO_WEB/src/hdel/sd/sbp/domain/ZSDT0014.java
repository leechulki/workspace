package hdel.sd.sbp.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZSDT0014 implements java.io.Serializable {

	public ZSDT0014() {
	}

	private String MANDT;			// 클라이언트                   
	private String SONNR;           // 수주계획번호                 
	private String PSPID;           // 프로젝트 정의                
	private String POSID;           // 작업 분석 구조 요소(WBS 요소)
	private int BIDYM;              // 입찰연월                     
	private String AUART;           // 판매 문서 유형               
	private String SPART;           // 제품군                       
	private String MATNR;           // 자재 번호                    
	private String VKBUR;           // 사업장                       
	private String VKGRP;           // 영업 그룹                    
	private String ZKUNNR;          // 영업사원                     
	private String GUBUN;           // 구분                         
	private String BIDDAT;          // 입찰예정일                   
	private String KUNNR;           // 고객 번호 1                  
	private String NAME1;           // 이름 1                       
	private String GSNAM;           // 공사명                       
	private String REGION;          // 설치지역                     
	private String GTYPE;           // 기종                         
	private String GTYPE_OLD;       // 기종                         
	private String GSPEC1;          // TYPE 1                       
	private String GSPEC2;          // TYPE 2                       
	private String GSPEC3;          // TYPE 3                       
	private int ZNUMBER;            // 대수                         
	private String ZDELI;           // 단납기 구분                  
	private String ZDELIC;          // 단납기 구분전산    
	private String SHANGH;          // 국내 상해 구분     
	private String ZINTER;          // 중계무역구분(Y)    
	private BigDecimal SOFOCA;      // 수주예상액         
	private BigDecimal ZFORE;       // 예상 시행율        
	private String WAERK;           // SD 문서 통화       
	private String SOABLE;          // 수주가능성         
	private String INPRO;           // 진행사항           
	private String DELDAT;          // 납기예정일         
	private String SORLT;           // 수주결과           
	private String VBELN;           // 판매 관리 문서 번호
	private BigDecimal SOPRICE;     // 수주금액           
	private BigDecimal SHANG;       // 당사 시행율        
	private String BIDENTP;         // 낙찰사             
	private BigDecimal BIDPRICE;    // 낙찰가             
	private String ENTP2;           // 업체2              
	private BigDecimal PRICE2;      // 금액2              
	private String ENTP3;           // 업체3              
	private BigDecimal PRICE3;      // 금액3              
	private int PLANDAT;            // 당초 계획연월      
	private BigDecimal DIAMT;       // 직접비             
	private BigDecimal INAMT;       // 간접비             
	private BigDecimal DIAMT_K;     // 직접비(시행예산)   
	private BigDecimal INAMT_K;     // 간접비(시행예산)   
	private String WAERK_K;         // SD 문서 통화  
	private BigDecimal MRATE;       // 매출 집계 비율
	private String CDATE;           // 전산입력일    
	private String CTIME;           // 입력시간      
	private String CUSER;           // 생성자        
	private String UDATE;           // 변경일        
	private String UTIME;           // 변경시간      
	private String UUSER;           // 변경한 사람   
	private String DDATE;           // 삭제일        
	private String DTIME;           // 삭제시간      
	private String DUSER;           // 삭제한 사람
	private String ZBDTYP;          //건물용도 jss	
	private String ZPYEAR;          //jss
	private String SID;             //승강기번호
	
	
	
	public String getZPYEAR() {
		return ZPYEAR;
	}

	public void setZPYEAR(String zPYEAR) {
		ZPYEAR = zPYEAR;
	}

	public String getZBDTYP() {
		return ZBDTYP;
	}

	public void setZBDTYP(String zBDTYP) {
		ZBDTYP = zBDTYP;
	}
	public String getMANDT(){
		return MANDT;
	}

	public String getSONNR(){
		return SONNR;
	}

	public String getPSPID(){
		return PSPID;
	}

	public String getPOSID(){
		return POSID;
	}

	public int getBIDYM(){
		return BIDYM;
	}

	public String getAUART(){
		return AUART;
	}

	public String getSPART(){
		return SPART;
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

	public String getGUBUN(){
		return GUBUN;
	}

	public String getBIDDAT(){
		return BIDDAT;
	}

	public String getKUNNR(){
		return KUNNR;
	}

	public String getNAME1(){
		return NAME1;
	}

	public String getGSNAM(){
		return GSNAM;
	}

	public String getREGION(){
		return REGION;
	}

	public String getGTYPE(){
		return GTYPE;
	}

	public String getGTYPE_OLD(){
		return GTYPE_OLD;
	}

	public String getGSPEC1(){
		return GSPEC1;
	}

	public String getGSPEC2(){
		return GSPEC2;
	}

	public String getGSPEC3(){
		return GSPEC3;
	}

	public int getZNUMBER(){
		return ZNUMBER;
	}

	public String getZDELI(){
		return ZDELI;
	}

	public String getZDELIC(){
		return ZDELIC;
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

	public String getSOABLE(){
		return SOABLE;
	}

	public String getINPRO(){
		return INPRO;
	}

	public String getDELDAT(){
		return DELDAT;
	}

	public String getSORLT(){
		return SORLT;
	}

	public String getVBELN(){
		return VBELN;
	}

	public BigDecimal getSOPRICE(){
		return SOPRICE;
	}

	public BigDecimal getSHANG(){
		return SHANG;
	}

	public String getBIDENTP(){
		return BIDENTP;
	}

	public BigDecimal getBIDPRICE(){
		return BIDPRICE;
	}

	public String getENTP2(){
		return ENTP2;
	}

	public BigDecimal getPRICE2(){
		return PRICE2;
	}

	public String getENTP3(){
		return ENTP3;
	}

	public BigDecimal getPRICE3(){
		return PRICE3;
	}

	public int getPLANDAT(){
		return PLANDAT;
	}

	public BigDecimal getDIAMT(){
		return DIAMT;
	}

	public BigDecimal getINAMT(){
		return INAMT;
	}

	public BigDecimal getDIAMT_K(){
		return DIAMT_K;
	}

	public BigDecimal getINAMT_K(){
		return INAMT_K;
	}

	public String getWAERK_K(){
		return WAERK_K;
	}

	public BigDecimal getMRATE(){
		return MRATE;
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

	public void setMANDT(String aValue) {
		MANDT=aValue;
	}

	public void setSONNR(String aValue) {
		SONNR=aValue;
	}

	public void setPSPID(String aValue) {
		PSPID=aValue;
	}

	public void setPOSID(String aValue) {
		POSID=aValue;
	}

	public void setBIDYM(int aValue) {
		BIDYM=aValue;
	}

	public void setAUART(String aValue) {
		AUART=aValue;
	}

	public void setSPART(String aValue) {
		SPART=aValue;
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

	public void setGUBUN(String aValue) {
		GUBUN=aValue;
	}

	public void setBIDDAT(String aValue) {
		BIDDAT=aValue;
	}

	public void setKUNNR(String aValue) {
		KUNNR=aValue;
	}

	public void setNAME1(String aValue) {
		NAME1=aValue;
	}

	public void setGSNAM(String aValue) {
		GSNAM=aValue;
	}

	public void setREGION(String aValue) {
		REGION=aValue;
	}

	public void setGTYPE(String aValue) {
		GTYPE=aValue;
	}

	public void setGTYPE_OLD(String aValue) {
		GTYPE_OLD=aValue;
	}

	public void setGSPEC1(String aValue) {
		GSPEC1=aValue;
	}

	public void setGSPEC2(String aValue) {
		GSPEC2=aValue;
	}

	public void setGSPEC3(String aValue) {
		GSPEC3=aValue;
	}

	public void setZNUMBER(int aValue) {
		ZNUMBER=aValue;
	}

	public void setZDELI(String aValue) {
		ZDELI=aValue;
	}

	public void setZDELIC(String aValue) {
		ZDELIC=aValue;
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

	public void setSOABLE(String aValue) {
		SOABLE=aValue;
	}

	public void setINPRO(String aValue) {
		INPRO=aValue;
	}

	public void setDELDAT(String aValue) {
		DELDAT=aValue;
	}

	public void setSORLT(String aValue) {
		SORLT=aValue;
	}

	public void setVBELN(String aValue) {
		VBELN=aValue;
	}

	public void setSOPRICE(BigDecimal aValue) {
		SOPRICE=aValue;
	}

	public void setSHANG(BigDecimal aValue) {
		SHANG=aValue;
	}

	public void setBIDENTP(String aValue) {
		BIDENTP=aValue;
	}

	public void setBIDPRICE(BigDecimal aValue) {
		BIDPRICE=aValue;
	}

	public void setENTP2(String aValue) {
		ENTP2=aValue;
	}

	public void setPRICE2(BigDecimal aValue) {
		PRICE2=aValue;
	}

	public void setENTP3(String aValue) {
		ENTP3=aValue;
	}

	public void setPRICE3(BigDecimal aValue) {
		PRICE3=aValue;
	}

	public void setPLANDAT(int aValue) {
		PLANDAT=aValue;
	}

	public void setDIAMT(BigDecimal aValue) {
		DIAMT=aValue;
	}

	public void setINAMT(BigDecimal aValue) {
		INAMT=aValue;
	}

	public void setDIAMT_K(BigDecimal aValue) {
		DIAMT_K=aValue;
	}

	public void setINAMT_K(BigDecimal aValue) {
		INAMT_K=aValue;
	}

	public void setWAERK_K(String aValue) {
		WAERK_K=aValue;
	}

	public void setMRATE(BigDecimal aValue) {
		MRATE=aValue;
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
	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDT0014.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDT0014"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SONNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SONNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BIDYM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BIDYM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BIDDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BIDDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GSNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GSNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REGION");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REGION"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GTYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GTYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GTYPE_OLD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GTYPE_OLD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GSPEC1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GSPEC1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GSPEC2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GSPEC2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GSPEC3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GSPEC3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZNUMBER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZNUMBER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZDELI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZDELI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZDELIC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZDELIC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SHANGH");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SHANGH"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZINTER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZINTER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SOFOCA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SOFOCA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZFORE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZFORE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SOABLE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SOABLE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INPRO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INPRO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DELDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DELDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SORLT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SORLT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SOPRICE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SOPRICE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SHANG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SHANG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BIDENTP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BIDENTP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BIDPRICE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BIDPRICE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ENTP2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ENTP2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRICE2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRICE2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ENTP3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ENTP3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRICE3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRICE3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLANDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLANDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DIAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DIAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DIAMT_K");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DIAMT_K"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INAMT_K");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INAMT_K"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK_K");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK_K"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MRATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MRATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CTIME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CTIME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CUSER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CUSER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UTIME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UTIME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UUSER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UUSER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DTIME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DTIME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DUSER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DUSER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZBDTYP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZBDTYP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZPYEAR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZPYEAR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();

		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("SONNR",(short)1,10);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("BIDYM",(short)2,6);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("ZKUNNR",(short)1,10);
		ds.addColumn("GUBUN",(short)1,1);
		ds.addColumn("BIDDAT",(short)1,8);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("GSNAM",(short)1,50);
		ds.addColumn("REGION",(short)1,2);
		ds.addColumn("GTYPE",(short)1,20);
		ds.addColumn("GTYPE_OLD",(short)1,20);
		ds.addColumn("GSPEC1",(short)1,5);
		ds.addColumn("GSPEC2",(short)1,5);
		ds.addColumn("GSPEC3",(short)1,5);
		ds.addColumn("ZNUMBER",(short)2,5);
		ds.addColumn("ZDELI",(short)1,1);
		ds.addColumn("ZDELIC",(short)1,1);
		ds.addColumn("SHANGH",(short)1,4);
		ds.addColumn("ZINTER",(short)1,1);
		ds.addColumn("SOFOCA",(short)1,15);
		ds.addColumn("ZFORE",(short)1,4);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("SOABLE",(short)1,1);
		ds.addColumn("INPRO",(short)1,50);
		ds.addColumn("DELDAT",(short)1,8);
		ds.addColumn("SORLT",(short)1,4);
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("SOPRICE",(short)1,15);
		ds.addColumn("SHANG",(short)1,4);
		ds.addColumn("BIDENTP",(short)1,6);
		ds.addColumn("BIDPRICE",(short)1,15);
		ds.addColumn("ENTP2",(short)1,6);
		ds.addColumn("PRICE2",(short)1,15);
		ds.addColumn("ENTP3",(short)1,6);
		ds.addColumn("PRICE3",(short)1,15);
		ds.addColumn("PLANDAT",(short)2,6);
		ds.addColumn("DIAMT",(short)1,13);
		ds.addColumn("INAMT",(short)1,13);
		ds.addColumn("DIAMT_K",(short)1,13);
		ds.addColumn("INAMT_K",(short)1,13);
		ds.addColumn("WAERK_K",(short)1,5);
		ds.addColumn("MRATE",(short)1,7);
		ds.addColumn("CDATE",(short)1,8);
		ds.addColumn("CTIME",(short)1,6);
		ds.addColumn("CUSER",(short)1,10);
		ds.addColumn("UDATE",(short)1,8);
		ds.addColumn("UTIME",(short)1,6);
		ds.addColumn("UUSER",(short)1,10);
		ds.addColumn("DDATE",(short)1,8);
		ds.addColumn("DTIME",(short)1,6);
		ds.addColumn("DUSER",(short)1,10);
		ds.addColumn("ZBDTYP",(short)1,2);
		ds.addColumn("ZPYEAR",(short)1,4);
		ds.addColumn("SID",(short)1,7);
		return ds;
	}

}