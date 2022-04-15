package com.helco.xf.qm04.ws;
import com.tobesoft.platform.data.Dataset;
public class ZQMS027T   
		implements java.io.Serializable {

	public ZQMS027T() {
	}
	public java.lang.String QMNUM;
	public java.lang.String FENUM;
	public java.lang.String MANUM;
	public java.lang.String TEXT;
	public java.lang.String getQMNUM(){
		return QMNUM;
	}
	public java.lang.String getFENUM(){
		return FENUM;
	}
	public java.lang.String getMANUM(){
		return MANUM;
	}
	public java.lang.String getTEXT(){
		return TEXT;
	}
	public void setQMNUM(java.lang.String aValue) {
		QMNUM=aValue;
	}
	public void setFENUM(java.lang.String aValue) {
		FENUM=aValue;
	}
	public void setMANUM(java.lang.String aValue) {
		MANUM=aValue;
	}
	public void setTEXT(java.lang.String aValue) {
		TEXT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZQMS027T.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMS027T"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QMNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QMNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FENUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FENUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("QMNUM",(short)1,12);
		ds.addColumn("FENUM",(short)1,4);
		ds.addColumn("MANUM",(short)1,4);
		ds.addColumn("TEXT",(short)1,72);
		return ds;
	}

}