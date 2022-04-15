package com.helco.xf.cs01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZMMS006   
		implements java.io.Serializable {

	public ZMMS006() {
	}
	public java.lang.String MAC_N1;
	public java.lang.String SERNO;
	public java.lang.String SERNO2;
	public java.lang.String getMAC_N1(){
		return MAC_N1;
	}
	public java.lang.String getSERNO(){
		return SERNO;
	}
	public java.lang.String getSERNO2(){
		return SERNO2;
	}
	public void setMAC_N1(java.lang.String aValue) {
		MAC_N1=aValue;
	}
	public void setSERNO(java.lang.String aValue) {
		SERNO=aValue.toUpperCase();
	}
	public void setSERNO2(java.lang.String aValue) {
		SERNO2=aValue.toUpperCase();
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZMMS006.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZMMS006"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAC_N1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAC_N1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SERNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SERNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SERNO2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SERNO2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MAC_N1",(short)1,0);
		ds.addColumn("SERNO",(short)1,40);
		ds.addColumn("SERNO2",(short)1,40);
		return ds;
	}

}