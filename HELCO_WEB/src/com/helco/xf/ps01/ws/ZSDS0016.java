package com.helco.xf.ps01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0016   
		implements java.io.Serializable {

	public ZSDS0016() {
	}
	public java.lang.String NAM_CHAR;
	public java.lang.String ATBEZ;
	public java.lang.String VALUE1;
	public java.lang.String ATWTB1;
	public java.lang.String VALUE2;
	public java.lang.String ATWTB2;
	public java.lang.String getNAM_CHAR(){
		return NAM_CHAR;
	}
	public java.lang.String getATBEZ(){
		return ATBEZ;
	}
	public java.lang.String getVALUE1(){
		return VALUE1;
	}
	public java.lang.String getATWTB1(){
		return ATWTB1;
	}
	public java.lang.String getVALUE2(){
		return VALUE2;
	}
	public java.lang.String getATWTB2(){
		return ATWTB2;
	}
	public void setNAM_CHAR(java.lang.String aValue) {
		NAM_CHAR=aValue;
	}
	public void setATBEZ(java.lang.String aValue) {
		ATBEZ=aValue;
	}
	public void setVALUE1(java.lang.String aValue) {
		VALUE1=aValue;
	}
	public void setATWTB1(java.lang.String aValue) {
		ATWTB1=aValue;
	}
	public void setVALUE2(java.lang.String aValue) {
		VALUE2=aValue;
	}
	public void setATWTB2(java.lang.String aValue) {
		ATWTB2=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0016.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0016"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAM_CHAR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAM_CHAR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATBEZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATBEZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VALUE1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VALUE1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATWTB1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWTB1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VALUE2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VALUE2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATWTB2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWTB2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("NAM_CHAR",(short)1,30);
		ds.addColumn("ATBEZ",(short)1,30);
		ds.addColumn("VALUE1",(short)1,60);
		ds.addColumn("ATWTB1",(short)1,30);
		ds.addColumn("VALUE2",(short)1,60);
		ds.addColumn("ATWTB2",(short)1,30);
		return ds;
	}

}