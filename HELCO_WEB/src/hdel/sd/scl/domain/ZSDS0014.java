package hdel.sd.scl.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZSDS0014 implements java.io.Serializable {

	public ZSDS0014() {
	}

	private String VBELN;
	private String PSPID;
	private String VKBUR;
	private String VKGRP;
	private String BSTDK;
	private String KNUMV;
	private String NAME1;
	private String BSTNK;
	private int ZQNTY;
	private BigDecimal NETWR;
	private String CONTR_DA;
	private String VDATU;
	private BigDecimal IRATE;
	private String ZMAN_NM;
	private String ZBEPJEO;
	private String WAERK;
	private String MEKINT;

	public String getVBELN(){
		return VBELN;
	}

	public String getPSPID(){
		return PSPID;
	}

	public String getVKBUR(){
		return VKBUR;
	}

	public String getVKGRP(){
		return VKGRP;
	}

	public String getBSTDK(){
		return BSTDK;
	}

	public String getKNUMV(){
		return KNUMV;
	}

	public String getNAME1(){
		return NAME1;
	}

	public String getBSTNK(){
		return BSTNK;
	}

	public int getZQNTY(){
		return ZQNTY;
	}

	public BigDecimal getNETWR(){
		return NETWR;
	}

	public String getCONTR_DA(){
		return CONTR_DA;
	}

	public String getVDATU(){
		return VDATU;
	}

	public BigDecimal getIRATE(){
		return IRATE;
	}

	public String getZMAN_NM(){
		return ZMAN_NM;
	}

	public String getZBEPJEO(){
		return ZBEPJEO;
	}

	public String getWAERK(){
		return WAERK;
	}

	public String getMEKINT(){
		return MEKINT;
	}

	public void setVBELN(String aValue) {
		VBELN=aValue;
	}

	public void setPSPID(String aValue) {
		PSPID=aValue;
	}

	public void setVKBUR(String aValue) {
		VKBUR=aValue;
	}

	public void setVKGRP(String aValue) {
		VKGRP=aValue;
	}

	public void setBSTDK(String aValue) {
		BSTDK=aValue;
	}

	public void setKNUMV(String aValue) {
		KNUMV=aValue;
	}

	public void setNAME1(String aValue) {
		NAME1=aValue;
	}

	public void setBSTNK(String aValue) {
		BSTNK=aValue;
	}

	public void setZQNTY(int aValue) {
		ZQNTY=aValue;
	}

	public void setNETWR(BigDecimal aValue) {
		NETWR=aValue;
	}

	public void setCONTR_DA(String aValue) {
		CONTR_DA=aValue;
	}

	public void setVDATU(String aValue) {
		VDATU=aValue;
	}

	public void setIRATE(BigDecimal aValue) {
		IRATE=aValue;
	}

	public void setZMAN_NM(String aValue) {
		ZMAN_NM=aValue;
	}

	public void setZBEPJEO(String aValue) {
		ZBEPJEO=aValue;
	}

	public void setWAERK(String aValue) {
		WAERK=aValue;
	}

	public void setMEKINT(String aValue) {
		MEKINT=aValue;
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
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KNUMV");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KNUMV"));
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
		elemField.setFieldName("BSTNK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTNK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQNTY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONTR_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONTR_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VDATU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VDATU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("IRATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IRATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZMAN_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZMAN_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZBEPJEO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZBEPJEO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEKINT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEKINT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
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
		ds.addColumn("ZQNTY",(short)2,3);
		ds.addColumn("NETWR",(short)4,15);
		ds.addColumn("CONTR_DA",(short)1,8);
		ds.addColumn("VDATU",(short)1,8);
		ds.addColumn("IRATE",(short)4,5);
		ds.addColumn("ZMAN_NM",(short)1,30);
		ds.addColumn("ZBEPJEO",(short)1,8);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("MEKINT",(short)1,60);
		return ds;
	}

}