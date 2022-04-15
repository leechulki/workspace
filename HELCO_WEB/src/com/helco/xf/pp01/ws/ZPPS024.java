package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS024   
		implements java.io.Serializable {

	public ZPPS024() {
	}
	public java.lang.String DATE;
	public java.lang.String DAYTXT;
	public java.lang.String COUNT;
	public java.lang.String ITEM01;
	public java.lang.String ITEM02;
	public java.lang.String ITEM05;
	public java.lang.String ITEM03;
	public java.lang.String ITEM04;
	public java.lang.String ITEM06;
	public java.lang.String ITEM07;
	public java.lang.String ITEM08;
	public java.lang.String ITEM09;
	public java.lang.String ITEM10;
	public java.lang.String ITEM11;
	public java.lang.String ITEM12;
	public java.lang.String getDATE(){
		return DATE;
	}
	public java.lang.String getDAYTXT(){
		return DAYTXT;
	}
	public java.lang.String getCOUNT(){
		return COUNT;
	}
	public java.lang.String getITEM01(){
		return ITEM01;
	}
	public java.lang.String getITEM02(){
		return ITEM02;
	}
	public java.lang.String getITEM05(){
		return ITEM05;
	}
	public java.lang.String getITEM03(){
		return ITEM03;
	}
	public java.lang.String getITEM04(){
		return ITEM04;
	}
	public java.lang.String getITEM06(){
		return ITEM06;
	}
	public java.lang.String getITEM07(){
		return ITEM07;
	}
	public java.lang.String getITEM08(){
		return ITEM08;
	}
	public java.lang.String getITEM09(){
		return ITEM09;
	}
	public java.lang.String getITEM10(){
		return ITEM10;
	}
	public java.lang.String getITEM11(){
		return ITEM11;
	}
	public java.lang.String getITEM12(){
		return ITEM12;
	}
	public void setDATE(java.lang.String aValue) {
		DATE=aValue;
	}
	public void setDAYTXT(java.lang.String aValue) {
		DAYTXT=aValue;
	}
	public void setCOUNT(java.lang.String aValue) {
		COUNT=aValue;
	}
	public void setITEM01(java.lang.String aValue) {
		ITEM01=aValue;
	}
	public void setITEM02(java.lang.String aValue) {
		ITEM02=aValue;
	}
	public void setITEM05(java.lang.String aValue) {
		ITEM05=aValue;
	}
	public void setITEM03(java.lang.String aValue) {
		ITEM03=aValue;
	}
	public void setITEM04(java.lang.String aValue) {
		ITEM04=aValue;
	}
	public void setITEM06(java.lang.String aValue) {
		ITEM06=aValue;
	}
	public void setITEM07(java.lang.String aValue) {
		ITEM07=aValue;
	}
	public void setITEM08(java.lang.String aValue) {
		ITEM08=aValue;
	}
	public void setITEM09(java.lang.String aValue) {
		ITEM09=aValue;
	}
	public void setITEM10(java.lang.String aValue) {
		ITEM10=aValue;
	}
	public void setITEM11(java.lang.String aValue) {
		ITEM11=aValue;
	}
	public void setITEM12(java.lang.String aValue) {
		ITEM12=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS024.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS024"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DAYTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DAYTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("COUNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "COUNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM09");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM09"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM10");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM10"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM11");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM11"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM12");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM12"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("DATE",(short)1,8);
		ds.addColumn("DAYTXT",(short)1,15);
		ds.addColumn("COUNT",(short)1,6);
		ds.addColumn("ITEM01",(short)1,6);
		ds.addColumn("ITEM02",(short)1,6);
		ds.addColumn("ITEM05",(short)1,6);
		ds.addColumn("ITEM03",(short)1,6);
		ds.addColumn("ITEM04",(short)1,6);
		ds.addColumn("ITEM06",(short)1,6);
		ds.addColumn("ITEM07",(short)1,6);
		ds.addColumn("ITEM08",(short)1,6);
		ds.addColumn("ITEM09",(short)1,6);
		ds.addColumn("ITEM10",(short)1,6);
		ds.addColumn("ITEM11",(short)1,6);
		ds.addColumn("ITEM12",(short)1,6);
		return ds;
	}

}