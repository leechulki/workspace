package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS034   
		implements java.io.Serializable {

	public ZPPS034() {
	}
	public java.lang.String AUFNR;
	public java.lang.String HOGI;
	public java.lang.String CONSNAM;
	public java.lang.String ITEM_NO;
	public java.lang.String ITEM_NAM;
	public java.math.BigDecimal PSMNG;
	public java.lang.String AMEIN;
	public java.lang.String CRDAT;
	public java.lang.String INPDAT1;
	public java.lang.String INPDAT2;
	public java.lang.String INPDAT3;
	public java.lang.String INPDAT4;
	public java.lang.String INPDAT5;
	public java.lang.String CONSNO;
	public java.lang.String VORNR;
	public java.lang.String LTXA1;
	public java.lang.String ARBPL;
	public java.lang.String KTEXT;
	public java.lang.String BUDAT;
	public java.lang.String EPDAT;
	public java.lang.String ILDAT;
	public java.lang.String STATUS;
	public java.lang.String getAUFNR(){
		return AUFNR;
	}
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getCONSNAM(){
		return CONSNAM;
	}
	public java.lang.String getITEM_NO(){
		return ITEM_NO;
	}
	public java.lang.String getITEM_NAM(){
		return ITEM_NAM;
	}
	public java.math.BigDecimal getPSMNG(){
		return PSMNG;
	}
	public java.lang.String getAMEIN(){
		return AMEIN;
	}
	public java.lang.String getCRDAT(){
		return CRDAT;
	}
	public java.lang.String getINPDAT1(){
		return INPDAT1;
	}
	public java.lang.String getINPDAT2(){
		return INPDAT2;
	}
	public java.lang.String getINPDAT3(){
		return INPDAT3;
	}
	public java.lang.String getINPDAT4(){
		return INPDAT4;
	}
	public java.lang.String getINPDAT5(){
		return INPDAT5;
	}
	public java.lang.String getCONSNO(){
		return CONSNO;
	}
	public java.lang.String getVORNR(){
		return VORNR;
	}
	public java.lang.String getLTXA1(){
		return LTXA1;
	}
	public java.lang.String getARBPL(){
		return ARBPL;
	}
	public java.lang.String getKTEXT(){
		return KTEXT;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.lang.String getEPDAT(){
		return EPDAT;
	}
	public java.lang.String getILDAT(){
		return ILDAT;
	}
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public void setAUFNR(java.lang.String aValue) {
		AUFNR=aValue;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setCONSNAM(java.lang.String aValue) {
		CONSNAM=aValue;
	}
	public void setITEM_NO(java.lang.String aValue) {
		ITEM_NO=aValue;
	}
	public void setITEM_NAM(java.lang.String aValue) {
		ITEM_NAM=aValue;
	}
	public void setPSMNG(java.math.BigDecimal aValue) {
		PSMNG=aValue;
	}
	public void setAMEIN(java.lang.String aValue) {
		AMEIN=aValue;
	}
	public void setCRDAT(java.lang.String aValue) {
		CRDAT=aValue;
	}
	public void setINPDAT1(java.lang.String aValue) {
		INPDAT1=aValue;
	}
	public void setINPDAT2(java.lang.String aValue) {
		INPDAT2=aValue;
	}
	public void setINPDAT3(java.lang.String aValue) {
		INPDAT3=aValue;
	}
	public void setINPDAT4(java.lang.String aValue) {
		INPDAT4=aValue;
	}
	public void setINPDAT5(java.lang.String aValue) {
		INPDAT5=aValue;
	}
	public void setCONSNO(java.lang.String aValue) {
		CONSNO=aValue;
	}
	public void setVORNR(java.lang.String aValue) {
		VORNR=aValue;
	}
	public void setLTXA1(java.lang.String aValue) {
		LTXA1=aValue;
	}
	public void setARBPL(java.lang.String aValue) {
		ARBPL=aValue;
	}
	public void setKTEXT(java.lang.String aValue) {
		KTEXT=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setEPDAT(java.lang.String aValue) {
		EPDAT=aValue;
	}
	public void setILDAT(java.lang.String aValue) {
		ILDAT=aValue;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS034.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS034"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HOGI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONSNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONSNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM_NO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM_NO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM_NAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM_NAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSMNG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSMNG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMEIN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMEIN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INPDAT1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INPDAT1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INPDAT2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INPDAT2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INPDAT3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INPDAT3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INPDAT4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INPDAT4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INPDAT5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INPDAT5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONSNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONSNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VORNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VORNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LTXA1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LTXA1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ARBPL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARBPL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KTEXT"));
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
		elemField.setFieldName("EPDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EPDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ILDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ILDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("AUFNR",(short)1,12);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("CONSNAM",(short)1,30);
		ds.addColumn("ITEM_NO",(short)1,9);
		ds.addColumn("ITEM_NAM",(short)1,30);
		ds.addColumn("PSMNG",(short)1,13);
		ds.addColumn("AMEIN",(short)1,3);
		ds.addColumn("CRDAT",(short)1,8);
		ds.addColumn("INPDAT1",(short)1,8);
		ds.addColumn("INPDAT2",(short)1,8);
		ds.addColumn("INPDAT3",(short)1,8);
		ds.addColumn("INPDAT4",(short)1,8);
		ds.addColumn("INPDAT5",(short)1,8);
		ds.addColumn("CONSNO",(short)1,24);
		ds.addColumn("VORNR",(short)1,4);
		ds.addColumn("LTXA1",(short)1,40);
		ds.addColumn("ARBPL",(short)1,8);
		ds.addColumn("KTEXT",(short)1,40);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("EPDAT",(short)1,8);
		ds.addColumn("ILDAT",(short)1,8);
		ds.addColumn("STATUS",(short)1,4);
		return ds;
	}

}