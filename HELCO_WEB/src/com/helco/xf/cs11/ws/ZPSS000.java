package com.helco.xf.cs11.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPSS000   
		implements java.io.Serializable {

	public ZPSS000() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String ZLIFNR;
	public java.lang.String GUBUN;
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getZLIFNR(){
		return ZLIFNR;
	}
	public java.lang.String getGUBUN(){
		return GUBUN;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setZLIFNR(java.lang.String aValue) {
		ZLIFNR=aValue;
	}
	public void setGUBUN(java.lang.String aValue) {
		GUBUN=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS000.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS000"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZLIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZLIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ZLIFNR",(short)1,10);
		ds.addColumn("GUBUN",(short)1,1);
		return ds;
	}

}