package com.helco.xf.cs96.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS004A   
		implements java.io.Serializable {

	public ZCSS004A() {
	}
	public java.lang.String GBN;
	public java.lang.String TEXT;
	public java.lang.String getGBN(){
		return GBN;
	}
	public java.lang.String getTEXT(){
		return TEXT;
	}
	public void setGBN(java.lang.String aValue) {
		GBN=aValue;
	}
	public void setTEXT(java.lang.String aValue) {
		TEXT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS004A.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS004A"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GBN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GBN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("GBN",(short)1,1);
		ds.addColumn("TEXT",(short)1,132);
		return ds;
	}

}