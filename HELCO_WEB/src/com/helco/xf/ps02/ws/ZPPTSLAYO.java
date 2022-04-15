package com.helco.xf.ps02.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPTSLAYO   
		implements java.io.Serializable {

	public ZPPTSLAYO() {
	}
	public java.lang.String POSID;
	public java.lang.String BLOCK;
	public java.lang.String SPEC;
	public java.lang.String LAYOUT;
	public java.lang.String MONEY;
	
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getBLOCK(){
		return BLOCK;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.lang.String getLAYOUT(){
		return LAYOUT;
	}
	public java.lang.String getMONEY(){
		return MONEY;
	}
	
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setBLOCK(java.lang.String aValue) {
		BLOCK=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setLAYOUT(java.lang.String aValue) {
		LAYOUT=aValue;
	}
	public void setMONEY(java.lang.String aValue) {
		MONEY=aValue;
	}

	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPTSLAYO.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPSSLAYO"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLOCK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLOCK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LAYOUT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAYOUT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MONEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MONEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("BLOCK",(short)1,1);
		ds.addColumn("SPEC",(short)1,1);
		ds.addColumn("LAYOUT",(short)1,1);
		ds.addColumn("MONEY",(short)1,3);
		
		return ds;
	}

}