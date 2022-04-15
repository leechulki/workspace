package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS024P   
		implements java.io.Serializable {

	public ZPPS024P() {
	}
	public java.lang.String ILDAT;
	public java.lang.String DAYTXT;
	public java.lang.String ABLOCK;
	public java.lang.String BBLOCK;
	public java.lang.String EBLOCK;
	public java.lang.String CBLOCK;
	public java.lang.String DBLOCK;
	public java.lang.String FBLOCK;
	
	public java.lang.String getILDAT(){
		return ILDAT;
	}
	public java.lang.String getDAYTXT(){
		return DAYTXT;
	}
	public java.lang.String getABLOCK(){
		return ABLOCK;
	}
	public java.lang.String getBBLOCK(){
		return BBLOCK;
	}
	public java.lang.String getEBLOCK(){
		return EBLOCK;
	}
	public java.lang.String getCBLOCK(){
		return CBLOCK;
	}
	public java.lang.String getDBLOCK(){
		return DBLOCK;
	}
	public java.lang.String getFBLOCK(){
		return FBLOCK;
	}
	public void setILDAT(java.lang.String aValue) {
		ILDAT=aValue;
	}
	public void setDAYTXT(java.lang.String aValue) {
		DAYTXT=aValue;
	}
	public void setABLOCK(java.lang.String aValue) {
		ABLOCK=aValue;
	}
	public void setBBLOCK(java.lang.String aValue) {
		BBLOCK=aValue;
	}
	public void setEBLOCK(java.lang.String aValue) {
		EBLOCK=aValue;
	}
	public void setCBLOCK(java.lang.String aValue) {
		CBLOCK=aValue;
	}
	public void setDBLOCK(java.lang.String aValue) {
		DBLOCK=aValue;
	}
	public void setFBLOCK(java.lang.String aValue) {
		FBLOCK=aValue;
	}
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS024P.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS024P"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ILDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ILDAT"));
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
		elemField.setFieldName("ABLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ABLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BBLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BBLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CBLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CBLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DBLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DBLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EBLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EBLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FBLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FBLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ILDAT",(short)1,8);
		ds.addColumn("DAYTXT",(short)1,15);
		ds.addColumn("ABLOCK",(short)1,6);
		ds.addColumn("BBLOCK",(short)1,6);
		ds.addColumn("EBLOCK",(short)1,6);
		ds.addColumn("CBLOCK",(short)1,6);
		ds.addColumn("DBLOCK",(short)1,6);
		ds.addColumn("FBLOCK",(short)1,6);
		return ds;
	}

}