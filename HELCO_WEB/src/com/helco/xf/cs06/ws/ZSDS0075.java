package com.helco.xf.cs06.ws;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0075   
		implements java.io.Serializable {

	public ZSDS0075() {
	}
	public java.lang.String ZPJT;
	public java.lang.String ZHNO;
	public java.lang.String ZFDT;
	public java.lang.String getZPJT(){
		return ZPJT;
	}
	public java.lang.String getZHNO(){
		return ZHNO;
	}
	public java.lang.String getZFDT(){
		return ZFDT;
	}
	public void setZPJT(java.lang.String aValue) {
		ZPJT=aValue;
	}
	public void setZHNO(java.lang.String aValue) {
		ZHNO=aValue;
	}
	public void setZFDT(java.lang.String aValue) {
		ZFDT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0075.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0075"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZPJT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZPJT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZHNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZHNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZFDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZFDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ZPJT",(short)1,6);
		ds.addColumn("ZHNO",(short)1,3);
		ds.addColumn("ZFDT",(short)1,10);
		return ds;
	}

}