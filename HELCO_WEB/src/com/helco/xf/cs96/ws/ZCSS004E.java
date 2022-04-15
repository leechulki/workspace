package com.helco.xf.cs96.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS004E   
		implements java.io.Serializable {

	public ZCSS004E() {
	}
	public java.lang.String TXE;
	public java.lang.String getTXE(){
		return TXE;
	}
	public void setTXE(java.lang.String aValue) {
		TXE=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS004E.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS004E"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TXE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TXE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("TXE",(short)1,132);
		return ds;
	}

}