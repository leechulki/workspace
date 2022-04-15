package com.helco.xf.cs96.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS004   
		implements java.io.Serializable {

	public ZCSS004() {
	}
	public java.lang.String SEQ;
	public java.lang.String UPN;
	public java.lang.String DAT;
	public java.lang.String MUR;
	public java.lang.String getSEQ(){
		return SEQ;
	}
	public java.lang.String getUPN(){
		return UPN;
	}
	public java.lang.String getDAT(){
		return DAT;
	}
	public java.lang.String getMUR(){
		return MUR;
	}
	public void setSEQ(java.lang.String aValue) {
		SEQ=aValue;
	}
	public void setUPN(java.lang.String aValue) {
		UPN=aValue;
	}
	public void setDAT(java.lang.String aValue) {
		DAT=aValue;
	}
	public void setMUR(java.lang.String aValue) {
		MUR=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS004.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS004"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UPN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UPN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("SEQ",(short)1,2);
		ds.addColumn("UPN",(short)1,6);
		ds.addColumn("DAT",(short)1,8);
		ds.addColumn("MUR",(short)1,8);
		return ds;
	}

}