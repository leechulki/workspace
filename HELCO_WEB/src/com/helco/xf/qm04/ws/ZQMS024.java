package com.helco.xf.qm04.ws;
import com.tobesoft.platform.data.Dataset;
public class ZQMS024   
		implements java.io.Serializable {

	public ZQMS024() {
	}
	public java.lang.String OBJNR;
	public java.lang.String OBJNRTX;
	public java.lang.String QMNUM;
	public java.lang.String FENUM;
	public java.lang.String PNLKN;
	public java.lang.String VORNR;
	public java.lang.String LTXA1;
	public java.lang.String MERKNR;
	public java.lang.String KURZTEXT;
	public java.lang.String FEKAT;
	public java.lang.String FEGRP;
	public java.lang.String FECOD;
	public java.lang.String FEKURZTEXT;
	public java.lang.String FETXT;
	public java.lang.String OTKAT;
	public java.lang.String OTGRP;
	public java.lang.String OTEIL;
	public java.lang.String OTKURZTEXT;
	public java.lang.String LNTXT;
	public java.lang.String getOBJNR(){
		return OBJNR;
	}
	public java.lang.String getOBJNRTX(){
		return OBJNRTX;
	}
	public java.lang.String getQMNUM(){
		return QMNUM;
	}
	public java.lang.String getFENUM(){
		return FENUM;
	}
	public java.lang.String getPNLKN(){
		return PNLKN;
	}
	public java.lang.String getVORNR(){
		return VORNR;
	}
	public java.lang.String getLTXA1(){
		return LTXA1;
	}
	public java.lang.String getMERKNR(){
		return MERKNR;
	}
	public java.lang.String getKURZTEXT(){
		return KURZTEXT;
	}
	public java.lang.String getFEKAT(){
		return FEKAT;
	}
	public java.lang.String getFEGRP(){
		return FEGRP;
	}
	public java.lang.String getFECOD(){
		return FECOD;
	}
	public java.lang.String getFEKURZTEXT(){
		return FEKURZTEXT;
	}
	public java.lang.String getFETXT(){
		return FETXT;
	}
	public java.lang.String getOTKAT(){
		return OTKAT;
	}
	public java.lang.String getOTGRP(){
		return OTGRP;
	}
	public java.lang.String getOTEIL(){
		return OTEIL;
	}
	public java.lang.String getOTKURZTEXT(){
		return OTKURZTEXT;
	}
	public java.lang.String getLNTXT(){
		return LNTXT;
	}
	public void setOBJNR(java.lang.String aValue) {
		OBJNR=aValue;
	}
	public void setOBJNRTX(java.lang.String aValue) {
		OBJNRTX=aValue;
	}
	public void setQMNUM(java.lang.String aValue) {
		QMNUM=aValue;
	}
	public void setFENUM(java.lang.String aValue) {
		FENUM=aValue;
	}
	public void setPNLKN(java.lang.String aValue) {
		PNLKN=aValue;
	}
	public void setVORNR(java.lang.String aValue) {
		VORNR=aValue;
	}
	public void setLTXA1(java.lang.String aValue) {
		LTXA1=aValue;
	}
	public void setMERKNR(java.lang.String aValue) {
		MERKNR=aValue;
	}
	public void setKURZTEXT(java.lang.String aValue) {
		KURZTEXT=aValue;
	}
	public void setFEKAT(java.lang.String aValue) {
		FEKAT=aValue;
	}
	public void setFEGRP(java.lang.String aValue) {
		FEGRP=aValue;
	}
	public void setFECOD(java.lang.String aValue) {
		FECOD=aValue;
	}
	public void setFEKURZTEXT(java.lang.String aValue) {
		FEKURZTEXT=aValue;
	}
	public void setFETXT(java.lang.String aValue) {
		FETXT=aValue;
	}
	public void setOTKAT(java.lang.String aValue) {
		OTKAT=aValue;
	}
	public void setOTGRP(java.lang.String aValue) {
		OTGRP=aValue;
	}
	public void setOTEIL(java.lang.String aValue) {
		OTEIL=aValue;
	}
	public void setOTKURZTEXT(java.lang.String aValue) {
		OTKURZTEXT=aValue;
	}
	public void setLNTXT(java.lang.String aValue) {
		LNTXT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZQMS024.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMS024"));
		org.apache.axis.description.ElementDesc elemField =null;
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
		elemField.setFieldName("PNLKN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PNLKN"));
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
		elemField.setFieldName("MERKNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MERKNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KURZTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KURZTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FEKAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FEKAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FEGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FEGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FECOD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FECOD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FEKURZTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FEKURZTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FETXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FETXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OTKAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OTKAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OTGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OTGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OTEIL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OTEIL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OTKURZTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OTKURZTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LNTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LNTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	} 
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("OBJNR",(short)1,22);
		ds.addColumn("OBJNRTX",(short)1,30);
		ds.addColumn("QMNUM",(short)1,12);
		ds.addColumn("FENUM",(short)1,4);
		ds.addColumn("PNLKN",(short)1,8);
		ds.addColumn("VORNR",(short)1,4);
		ds.addColumn("LTXA1",(short)1,40);
		ds.addColumn("MERKNR",(short)1,4);
		ds.addColumn("KURZTEXT",(short)1,40);
		ds.addColumn("FEKAT",(short)1,1);
		ds.addColumn("FEGRP",(short)1,8);
		ds.addColumn("FECOD",(short)1,4);
		ds.addColumn("FEKURZTEXT",(short)1,40);
		ds.addColumn("FETXT",(short)1,40);
		ds.addColumn("OTKAT",(short)1,1);
		ds.addColumn("OTGRP",(short)1,8);
		ds.addColumn("OTEIL",(short)1,4);
		ds.addColumn("OTKURZTEXT",(short)1,40);
		ds.addColumn("LNTXT",(short)1,1000);
		return ds;
	}

}