package com.helco.xf.ps11.ws;
import com.tobesoft.platform.data.Dataset;
public class BAPI_WBS_LIST_TAB   
		implements java.io.Serializable {

	public BAPI_WBS_LIST_TAB() {
	}
	public java.lang.String WBS_ELEMENT;


	public java.lang.String getWBS_ELEMENT(){
		return WBS_ELEMENT;
	}
	public void setWBS_ELEMENT(java.lang.String aValue) {
		WBS_ELEMENT=aValue;
	}
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(BAPI_WBS_LIST_TAB.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "IT_POSID"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WBS_ELEMENT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WBS_ELEMENT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("WBS_ELEMENT",(short)1,24);

		
		
		return ds;
	}

}