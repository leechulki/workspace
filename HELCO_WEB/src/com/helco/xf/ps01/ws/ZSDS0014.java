package com.helco.xf.ps01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0014   
		implements java.io.Serializable {

	public ZSDS0014() {
	}
	public java.lang.String VBELN;
	public java.lang.String PSPID;
	public java.lang.String VKBUR;
	public java.lang.String VKGRP;
	public java.lang.String BSTDK;
	public java.lang.String KNUMV;
	public java.lang.String NAME1;
	public java.lang.String BSTNK;
	public java.lang.String ZQNTY;
	public java.math.BigDecimal NETWR;
	public java.lang.String CONTR_DA;
	public java.lang.String VDATU;
	public java.lang.String IRATE;
	public java.lang.String ZMAN_NM;
	public java.lang.String ZBEPJEO;
	public java.lang.String WAERK;
	/**
	 * @return the mEKINT
	 */
	public java.lang.String getMEKINT() {
		return MEKINT;
	}
	/**
	 * @param mEKINT the mEKINT to set
	 */
	public void setMEKINT(java.lang.String mEKINT) {
		MEKINT = mEKINT;
	}
	public java.lang.String MEKINT;
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getVKBUR(){
		return VKBUR;
	}
	public java.lang.String getVKGRP(){
		return VKGRP;
	}
	public java.lang.String getBSTDK(){
		return BSTDK;
	}
	public java.lang.String getKNUMV(){
		return KNUMV;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.lang.String getBSTNK(){
		return BSTNK;
	}
	public java.lang.String getZQNTY(){
		return ZQNTY;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.lang.String getCONTR_DA(){
		return CONTR_DA;
	}
	public java.lang.String getVDATU(){
		return VDATU;
	}
	public java.lang.String getIRATE(){
		return IRATE;
	}
	public java.lang.String getZMAN_NM(){
		return ZMAN_NM;
	}
	public java.lang.String getZBEPJEO(){
		return ZBEPJEO;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setVKBUR(java.lang.String aValue) {
		VKBUR=aValue;
	}
	public void setVKGRP(java.lang.String aValue) {
		VKGRP=aValue;
	}
	public void setBSTDK(java.lang.String aValue) {
		BSTDK=aValue;
	}
	public void setKNUMV(java.lang.String aValue) {
		KNUMV=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setBSTNK(java.lang.String aValue) {
		BSTNK=aValue;
	}
	public void setZQNTY(java.lang.String aValue) {
		ZQNTY=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setCONTR_DA(java.lang.String aValue) {
		CONTR_DA=aValue;
	}
	public void setVDATU(java.lang.String aValue) {
		VDATU=aValue;
	}
	public void setIRATE(java.lang.String aValue) {
		IRATE=aValue;
	}
	public void setZMAN_NM(java.lang.String aValue) {
		ZMAN_NM=aValue;
	}
	public void setZBEPJEO(java.lang.String aValue) {
		ZBEPJEO=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0014.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0014"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KNUMV");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KNUMV"));
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
		elemField.setFieldName("BSTNK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTNK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQNTY"));
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
		elemField.setFieldName("CONTR_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONTR_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VDATU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VDATU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("IRATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IRATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZMAN_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZMAN_NM"));
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
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEKINT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEKINT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("KNUMV",(short)1,10);
		ds.addColumn("NAME1",(short)1,20);
		ds.addColumn("BSTNK",(short)1,30);
		ds.addColumn("ZQNTY",(short)1,3);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("CONTR_DA",(short)1,8);
		ds.addColumn("VDATU",(short)1,8);
		ds.addColumn("IRATE",(short)1,1);
		ds.addColumn("ZMAN_NM",(short)1,20);
		ds.addColumn("ZBEPJEO",(short)1,8);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("MEKINT",(short)1,60);
		return ds;
	}

}