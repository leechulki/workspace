package com.helco.xf.qm04.ws;
import com.tobesoft.platform.data.Dataset;
public class ZQMS027   
		implements java.io.Serializable {

	public ZQMS027() {
	}
	public java.lang.String QMNUM;
	public java.lang.String FENUM;
	public java.lang.String MANUM;
	public java.lang.String MATXT;
	public java.lang.String ERLNAM;
	public java.lang.String getQMNUM(){
		return QMNUM;
	}
	public java.lang.String getFENUM(){
		return FENUM;
	}
	public java.lang.String getMANUM(){
		return MANUM;
	}
	public java.lang.String getMATXT(){
		return MATXT;
	}
	public java.lang.String getERLNAM(){
		return ERLNAM;
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
	public void setMATXT(java.lang.String aValue) {
		MATXT=aValue;
	}
	public void setERLNAM(java.lang.String aValue) {
		ERLNAM=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZQMS027.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMS027"));
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
		elemField.setFieldName("MATXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERLNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERLNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("QMNUM",(short)1,12);
		ds.addColumn("FENUM",(short)1,4);
		ds.addColumn("MANUM",(short)1,4);
		ds.addColumn("MATXT",(short)1,40);
		ds.addColumn("ERLNAM",(short)1,12);
		return ds;
	}

}