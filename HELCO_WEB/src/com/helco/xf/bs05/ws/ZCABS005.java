package com.helco.xf.bs05.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCABS005   
		implements java.io.Serializable {

	public ZCABS005() {
	}
	public java.lang.String VAL01;
	public java.lang.String VAL02;
	public java.lang.String VAL03;
	public java.lang.String VAL04;
	public java.lang.String VAL05;
	public java.lang.String VAL06;
	public java.lang.String VAL07;
	public java.lang.String VAL08;
	public java.lang.String VAL09;
	public java.lang.String getVAL01(){
		return VAL01;
	}
	public java.lang.String getVAL02(){
		return VAL02;
	}
	public java.lang.String getVAL03(){
		return VAL03;
	}
	public java.lang.String getVAL04(){
		return VAL04;
	}
	public java.lang.String getVAL05(){
		return VAL05;
	}
	public java.lang.String getVAL06(){
		return VAL06;
	}
	public java.lang.String getVAL07(){
		return VAL07;
	}
	public java.lang.String getVAL08(){
		return VAL08;
	}
	public java.lang.String getVAL09(){
		return VAL09;
	}
	public void setVAL01(java.lang.String aValue) {
		VAL01=aValue;
	}
	public void setVAL02(java.lang.String aValue) {
		VAL02=aValue;
	}
	public void setVAL03(java.lang.String aValue) {
		VAL03=aValue;
	}
	public void setVAL04(java.lang.String aValue) {
		VAL04=aValue;
	}
	public void setVAL05(java.lang.String aValue) {
		VAL05=aValue;
	}
	public void setVAL06(java.lang.String aValue) {
		VAL06=aValue;
	}
	public void setVAL07(java.lang.String aValue) {
		VAL07=aValue;
	}
	public void setVAL08(java.lang.String aValue) {
		VAL08=aValue;
	}
	public void setVAL09(java.lang.String aValue) {
		VAL09=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCABS005.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCABS005"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL09");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL09"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("VAL01",(short)1,30);
		ds.addColumn("VAL02",(short)1,30);
		ds.addColumn("VAL03",(short)1,30);
		ds.addColumn("VAL04",(short)1,30);
		ds.addColumn("VAL05",(short)1,30);
		ds.addColumn("VAL06",(short)1,30);
		ds.addColumn("VAL07",(short)1,30);
		ds.addColumn("VAL08",(short)1,30);
		ds.addColumn("VAL09",(short)1,30);
		return ds;
	}

}