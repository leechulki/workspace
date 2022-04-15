package com.helco.xf.ps11.ws;
import com.tobesoft.platform.data.Dataset;
public class ZWBS0004   
		implements java.io.Serializable {

	public ZWBS0004() {
	}
	public java.lang.String POSID;
	public java.lang.String ATNAM;
	public java.lang.String ATBEZ;
	public java.lang.String ATWRT;
	public java.lang.String ATFLV;
	public java.lang.String ATFLB;
	public java.lang.String ATWTB;

	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getATNAM(){
		return ATNAM;
	}
	public java.lang.String getATBEZ(){
		return ATBEZ;
	}
	public java.lang.String getATWRT(){
		return ATWRT;
	}
	public java.lang.String getATFLV(){
		return ATFLV;
	}
	public java.lang.String getATFLB(){
		return ATFLB;
	}
	public java.lang.String getATWTB(){
		return ATWTB;
	}
	
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setATNAM(java.lang.String aValue) {
		ATNAM=aValue;
	}
	public void setATBEZ(java.lang.String aValue) {
		ATBEZ=aValue;
	}
	public void setATWRT(java.lang.String aValue) {
		ATWRT=aValue;
	}
	public void setATFLV(java.lang.String aValue) {
		ATFLV=aValue;
	}
	public void setATFLB(java.lang.String aValue) {
		ATFLB=aValue;
	}
	public void setATWTB(java.lang.String aValue) {
		ATWTB=aValue;
	}
	
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZWBS0004.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZWBS0004"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATNAM"));
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
		elemField.setFieldName("ATWRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATFLV");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATFLV"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATFLB");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATFLB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATWTB");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWTB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ATNAM",(short)1,30);
		ds.addColumn("ATBEZ",(short)1,30);
		ds.addColumn("ATWRT",(short)1,30);
		ds.addColumn("ATFLV",(short)1,16);
		ds.addColumn("ATFLB",(short)1,16);
		ds.addColumn("ATWTB",(short)1,30);
		
		
		return ds;
	}

}