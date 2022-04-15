package com.helco.xf.bs01.ws;

import com.tobesoft.platform.data.Dataset;

public class ZSDS0091 implements java.io.Serializable{

	public ZSDS0091() {}
	
	public java.lang.String PSPID;
	public java.lang.String SOPTP;
	public java.lang.String SOPDT;
	public java.lang.String SOP_Y_REGDT;

	public java.lang.String getPSPID() {
		return PSPID;
	}
	public java.lang.String getSOPTP() {
		return SOPTP;
	}
	public java.lang.String getSOPDT() {
		return SOPDT;
	}
	public java.lang.String getSOP_Y_REGDT() {
		return SOP_Y_REGDT;
	}
	
	public void setPSPID(java.lang.String aValue) {
		PSPID = aValue;
	}
	public void setSOPTP(java.lang.String aValue) {
		SOPTP = aValue;
	}
	public void setSOPDT(java.lang.String aValue) {
		SOPDT = aValue;
	}
	public void setSOP_Y_REGDT(java.lang.String aValue) {
		SOP_Y_REGDT = aValue;
	}
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0091.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0091"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SOPTP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SOPTP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SOPDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SOPDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SOP_Y_REGDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SOP_Y_REGDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,72);
		ds.addColumn("SOPTP",(short)1,10);
		ds.addColumn("SOPDT",(short)1,24);
		ds.addColumn("SOP_Y_REGDT",(short)1,24);
		return ds;
	}
}
