package com.helco.xf.qm04.ws;
import com.tobesoft.platform.data.Dataset;
public class ZQMS025   
		implements java.io.Serializable {

	public ZQMS025() {
	}
	public java.lang.String QMNUM;
	public java.lang.String FENUM;
	public java.lang.String MANUM;
	public java.lang.String MNKAT;
	public java.lang.String MNGRP;
	public java.lang.String MNCOD;
	public java.lang.String MNKURZTEXT;
	public java.lang.String MATXT;
	public java.lang.String OBJNR;
	public java.lang.String OBJNRTX;
	public java.lang.String RELEASED;
	public java.lang.String LNTXT;
	public java.lang.String TEXT;
	public java.lang.String ERLNAM;
	public java.lang.String getQMNUM(){
		return QMNUM;
	}
	public java.lang.String getFENUM(){
		return FENUM;
	}
	public java.lang.String getMANUM(){
		return MANUM;
	}
	public java.lang.String getMNKAT(){
		return MNKAT;
	}
	public java.lang.String getMNGRP(){
		return MNGRP;
	}
	public java.lang.String getMNCOD(){
		return MNCOD;
	}
	public java.lang.String getMNKURZTEXT(){
		return MNKURZTEXT;
	}
	public java.lang.String getMATXT(){
		return MATXT;
	}
	public java.lang.String getOBJNR(){
		return OBJNR;
	}
	public java.lang.String getOBJNRTX(){
		return OBJNRTX;
	}
	public java.lang.String getRELEASED(){
		return RELEASED;
	}
	public java.lang.String getLNTXT(){
		return LNTXT;
	}
	public java.lang.String getTEXT(){
		return TEXT;
	}
	public java.lang.String getERLNAM(){
		return ERLNAM;
	}
	public void setQMNUM(java.lang.String aValue) {
		QMNUM=aValue;
	}
	public void setFENUM(java.lang.String aValue) {
		FENUM=aValue;
	}
	public void setMANUM(java.lang.String aValue) {
		MANUM=aValue;
	}
	public void setMNKAT(java.lang.String aValue) {
		MNKAT=aValue;
	}
	public void setMNGRP(java.lang.String aValue) {
		MNGRP=aValue;
	}
	public void setMNCOD(java.lang.String aValue) {
		MNCOD=aValue;
	}
	public void setMNKURZTEXT(java.lang.String aValue) {
		MNKURZTEXT=aValue;
	}
	public void setMATXT(java.lang.String aValue) {
		MATXT=aValue;
	}
	public void setOBJNR(java.lang.String aValue) {
		OBJNR=aValue;
	}
	public void setOBJNRTX(java.lang.String aValue) {
		OBJNRTX=aValue;
	}
	public void setRELEASED(java.lang.String aValue) {
		RELEASED=aValue;
	}
	public void setLNTXT(java.lang.String aValue) {
		LNTXT=aValue;
	}
	public void setTEXT(java.lang.String aValue) {
		TEXT=aValue;
	}
	public void setERLNAM(java.lang.String aValue) {
		ERLNAM=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZQMS025.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMS025"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QMNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QMNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FENUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FENUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MNKAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MNKAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MNGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MNGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MNCOD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MNCOD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MNKURZTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MNKURZTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OBJNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OBJNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OBJNRTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OBJNRTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RELEASED");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RELEASED"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LNTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LNTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERLNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERLNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("QMNUM",(short)1,12);
		ds.addColumn("FENUM",(short)1,4);
		ds.addColumn("MANUM",(short)1,4);
		ds.addColumn("MNKAT",(short)1,1);
		ds.addColumn("MNGRP",(short)1,8);
		ds.addColumn("MNCOD",(short)1,4);
		ds.addColumn("MNKURZTEXT",(short)1,40);
		ds.addColumn("MATXT",(short)1,40);
		ds.addColumn("OBJNR",(short)1,22);
		ds.addColumn("OBJNRTX",(short)1,30);
		ds.addColumn("RELEASED",(short)1,1);
		ds.addColumn("LNTXT",(short)1,1000);
		ds.addColumn("TEXT",(short)1,1000);
		ds.addColumn("ERLNAM",(short)1,12);
		return ds;
	}

}