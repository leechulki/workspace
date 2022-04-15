package com.helco.xf.ps02.ws;
import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;
public class ZPSS030   
		implements java.io.Serializable {

	public ZPSS030() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String ZZHOGIT;
	public java.lang.String ZZSHIP1;
	public java.lang.String ZZSHIP2;
	public java.lang.String ZZSHIP3;
	public java.lang.String ZZSHIP4;
	public java.lang.String ZZSHIP5;
	public java.lang.String ZZSHIP6;
	public java.lang.String AUFNR1;
	public java.lang.String AUFNR2;
	public java.lang.String AUFNR3;
	public java.lang.String AUFNR4;
	public java.lang.String AUFNR5;
	public java.lang.String AUFNR6;
	public java.lang.String ZZFKEYD;
	public java.lang.String ZZCOMP2;
	public java.lang.String ZZSTAN1;
	public java.lang.String ZZACTSS;
	public java.lang.String ZZPMNUM;
	public java.lang.String CLOSI;
	public java.lang.String BLOCK;
	public java.lang.String CHASU;
	public java.lang.String QUART;
	public java.lang.String REMOV;
	public java.lang.String CHANG;
	public java.lang.String REASO;
	public java.lang.String TXT01;
	public java.lang.String REAQU;
	public java.lang.String CNAME;
	public java.lang.String CDATE;
	public java.lang.String CHASU1;
	public java.lang.String REMOV1;
	public java.lang.String CHANG1;
	public java.lang.String REASO1;
	public java.lang.String TXT011;
	public java.lang.String REAQU1;
	public java.lang.String CNAME1;
	public java.lang.String CDATE1;
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getZZHOGIT(){
		return ZZHOGIT;
	}
	public java.lang.String getZZSHIP1(){
		return ZZSHIP1;
	}
	public java.lang.String getZZSHIP2(){
		return ZZSHIP2;
	}
	public java.lang.String getZZSHIP3(){
		return ZZSHIP3;
	}
	public java.lang.String getZZSHIP4(){
		return ZZSHIP4;
	}
	public java.lang.String getZZSHIP5(){
		return ZZSHIP5;
	}
	public java.lang.String getZZSHIP6(){
		return ZZSHIP6;
	}
	public java.lang.String getAUFNR1(){
		return AUFNR1;
	}
	public java.lang.String getAUFNR2(){
		return AUFNR2;
	}
	public java.lang.String getAUFNR3(){
		return AUFNR3;
	}
	public java.lang.String getAUFNR4(){
		return AUFNR4;
	}
	public java.lang.String getAUFNR5(){
		return AUFNR5;
	}
	public java.lang.String getAUFNR6(){
		return AUFNR6;
	}
	public java.lang.String getZZFKEYD(){
		return ZZFKEYD;
	}
	public java.lang.String getZZCOMP2(){
		return ZZCOMP2;
	}
	public java.lang.String getZZSTAN1(){
		return ZZSTAN1;
	}
	public java.lang.String getZZACTSS(){
		return ZZACTSS;
	}
	public java.lang.String getZZPMNUM(){
		return ZZPMNUM;
	}
	public java.lang.String getCLOSI(){
		return CLOSI;
	}
	public java.lang.String getBLOCK(){
		return BLOCK;
	}
	public java.lang.String getCHASU(){
		return CHASU;
	}
	public java.lang.String getQUART(){
		return QUART;
	}
	public java.lang.String getREMOV(){
		return REMOV;
	}
	public java.lang.String getCHANG(){
		return CHANG;
	}
	public java.lang.String getREASO(){
		return REASO;
	}
	public java.lang.String getTXT01(){
		return TXT01;
	}
	public java.lang.String getREAQU(){
		return REAQU;
	}
	public java.lang.String getCNAME(){
		return CNAME;
	}
	public java.lang.String getCDATE(){
		return CDATE;
	}
	public java.lang.String getCHASU1(){
		return CHASU1;
	}
	public java.lang.String getREMOV1(){
		return REMOV1;
	}
	public java.lang.String getCHANG1(){
		return CHANG1;
	}
	public java.lang.String getREASO1(){
		return REASO1;
	}
	public java.lang.String getTXT011(){
		return TXT011;
	}
	public java.lang.String getREAQU1(){
		return REAQU1;
	}
	public java.lang.String getCNAME1(){
		return CNAME1;
	}
	public java.lang.String getCDATE1(){
		return CDATE1;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setZZHOGIT(java.lang.String aValue) {
		ZZHOGIT=aValue;
	}
	public void setZZSHIP1(java.lang.String aValue) {
		ZZSHIP1=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP2(java.lang.String aValue) {
		ZZSHIP2=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP3(java.lang.String aValue) {
		ZZSHIP3=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP4(java.lang.String aValue) {
		ZZSHIP4=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP5(java.lang.String aValue) {
		ZZSHIP5=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP6(java.lang.String aValue) {
		ZZSHIP6=CallSAP.getFormatDate(aValue);
	}
	public void setAUFNR1(java.lang.String aValue) {
		AUFNR1=aValue;
	}
	public void setAUFNR2(java.lang.String aValue) {
		AUFNR2=aValue;
	}
	public void setAUFNR3(java.lang.String aValue) {
		AUFNR3=aValue;
	}
	public void setAUFNR4(java.lang.String aValue) {
		AUFNR4=aValue;
	}
	public void setAUFNR5(java.lang.String aValue) {
		AUFNR5=aValue;
	}
	public void setAUFNR6(java.lang.String aValue) {
		AUFNR6=aValue;
	}
	public void setZZFKEYD(java.lang.String aValue) {
		ZZFKEYD=aValue;
	}
	public void setZZCOMP2(java.lang.String aValue) {
		ZZCOMP2=CallSAP.getFormatDate(aValue);
	}
	public void setZZSTAN1(java.lang.String aValue) {
		ZZSTAN1=aValue;
	}
	public void setZZACTSS(java.lang.String aValue) {
		ZZACTSS=aValue;
	}
	public void setZZPMNUM(java.lang.String aValue) {
		ZZPMNUM=aValue;
	}
	public void setCLOSI(java.lang.String aValue) {
		CLOSI=aValue;
	}
	public void setBLOCK(java.lang.String aValue) {
		BLOCK=aValue;
	}
	public void setCHASU(java.lang.String aValue) {
		CHASU=aValue;
	}
	public void setQUART(java.lang.String aValue) {
		QUART=aValue;
	}
	public void setREMOV(java.lang.String aValue) {
		REMOV=CallSAP.getFormatDate(aValue);
	}
	public void setCHANG(java.lang.String aValue) {
		CHANG=CallSAP.getFormatDate(aValue);
	}
	public void setREASO(java.lang.String aValue) {
		REASO=aValue;
	}
	public void setTXT01(java.lang.String aValue) {
		TXT01=aValue;
	}
	public void setREAQU(java.lang.String aValue) {
		REAQU=aValue;
	}
	public void setCNAME(java.lang.String aValue) {
		CNAME=aValue;
	}
	public void setCDATE(java.lang.String aValue) {
		CDATE=CallSAP.getFormatDate(aValue);
	}
	public void setCHASU1(java.lang.String aValue) {
		CHASU1=aValue;
	}
	public void setREMOV1(java.lang.String aValue) {
		REMOV1=CallSAP.getFormatDate(aValue);
	}
	public void setCHANG1(java.lang.String aValue) {
		CHANG1=CallSAP.getFormatDate(aValue);
	}
	public void setREASO1(java.lang.String aValue) {
		REASO1=aValue;
	}
	public void setTXT011(java.lang.String aValue) {
		TXT011=aValue;
	}
	public void setREAQU1(java.lang.String aValue) {
		REAQU1=aValue;
	}
	public void setCNAME1(java.lang.String aValue) {
		CNAME1=aValue;
	}
	public void setCDATE1(java.lang.String aValue) {
		CDATE1=CallSAP.getFormatDate(aValue);
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS030.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS030"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZHOGIT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZHOGIT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP6");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP6"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR6");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR6"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZFKEYD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZFKEYD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZCOMP2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZCOMP2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSTAN1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSTAN1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZACTSS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZACTSS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZPMNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPMNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CLOSI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CLOSI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHASU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHASU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QUART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QUART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REMOV");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REMOV"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHANG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHANG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REASO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REASO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TXT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TXT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REAQU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REAQU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CNAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CNAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHASU1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHASU1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REMOV1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REMOV1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHANG1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHANG1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REASO1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REASO1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TXT011");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TXT011"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REAQU1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REAQU1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CNAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CNAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CDATE1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CDATE1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ZZHOGIT",(short)1,10);
		ds.addColumn("ZZSHIP1",(short)1,8);
		ds.addColumn("ZZSHIP2",(short)1,8);
		ds.addColumn("ZZSHIP3",(short)1,8);
		ds.addColumn("ZZSHIP4",(short)1,8);
		ds.addColumn("ZZSHIP5",(short)1,8);
		ds.addColumn("ZZSHIP6",(short)1,8);
		ds.addColumn("AUFNR1",(short)1,12);
		ds.addColumn("AUFNR2",(short)1,12);
		ds.addColumn("AUFNR3",(short)1,12);
		ds.addColumn("AUFNR4",(short)1,12);
		ds.addColumn("AUFNR5",(short)1,12);
		ds.addColumn("AUFNR6",(short)1,12);
		ds.addColumn("ZZFKEYD",(short)1,1);
		ds.addColumn("ZZCOMP2",(short)1,8);
		ds.addColumn("ZZSTAN1",(short)1,1);
		ds.addColumn("ZZACTSS",(short)1,3);
		ds.addColumn("ZZPMNUM",(short)1,6);
		ds.addColumn("CLOSI",(short)1,1);
		ds.addColumn("BLOCK",(short)1,1);
		ds.addColumn("CHASU",(short)1,2);
		ds.addColumn("QUART",(short)1,1);
		ds.addColumn("REMOV",(short)1,8);
		ds.addColumn("CHANG",(short)1,8);
		ds.addColumn("REASO",(short)1,3);
		ds.addColumn("TXT01",(short)1,60);
		ds.addColumn("REAQU",(short)1,2);
		ds.addColumn("CNAME",(short)1,12);
		ds.addColumn("CDATE",(short)1,8);
		ds.addColumn("CHASU1",(short)1,2);
		ds.addColumn("REMOV1",(short)1,8);
		ds.addColumn("CHANG1",(short)1,8);
		ds.addColumn("REASO1",(short)1,3);
		ds.addColumn("TXT011",(short)1,60);
		ds.addColumn("REAQU1",(short)1,2);
		ds.addColumn("CNAME1",(short)1,12);
		ds.addColumn("CDATE1",(short)1,8);
		return ds;
	}

}