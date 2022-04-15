package com.helco.xf.ps11.ws;
import com.tobesoft.platform.data.Dataset;
public class BAPIRET2   
		implements java.io.Serializable {

	public BAPIRET2() {
	}
	public java.lang.String TYPE;
	public java.lang.String MESSAGE;
	
	public java.lang.String getTYPE(){
		return TYPE;
	}
	public java.lang.String getMESSAGE(){
		return MESSAGE;
	}
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(BAPIRET2.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "BAPIRET2"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MESSAGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MESSAGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("TYPE",(short)1,20);
		ds.addColumn("MESSAGE",(short)1,220);
		
		return ds;
	}

}