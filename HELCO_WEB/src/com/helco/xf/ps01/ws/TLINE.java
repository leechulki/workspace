package com.helco.xf.ps01.ws;
import com.tobesoft.platform.data.Dataset;
public class TLINE   
		implements java.io.Serializable {

	public TLINE() {
	}
	public java.lang.String TDFORMAT;
	public java.lang.String TDLINE;
	public java.lang.String getTDFORMAT(){
		return TDFORMAT;
	}
	public java.lang.String getTDLINE(){
		return TDLINE;
	}
	public void setTDFORMAT(java.lang.String aValue) {
		TDFORMAT=aValue;
	}
	public void setTDLINE(java.lang.String aValue) {
		TDLINE=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(TLINE.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "TLINE"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TDFORMAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TDFORMAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TDLINE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TDLINE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("TDFORMAT",(short)1,2);
		ds.addColumn("TDLINE",(short)1,132);
		return ds;
	}

}