package com.helco.xf.cs34.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS011   
		implements java.io.Serializable {

	public ZCSS011() {
	}
	public java.lang.String SO_NO;
	public java.lang.String FKDAT;
	public java.math.BigDecimal FAKWR;
	public java.lang.String FKSAF;
	public java.lang.String WAERK;
	public java.lang.String getSO_NO(){
		return SO_NO;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.math.BigDecimal getFAKWR(){
		return FAKWR;
	}
	public java.lang.String getFKSAF(){
		return FKSAF;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setSO_NO(java.lang.String aValue) {
		SO_NO=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setFAKWR(java.math.BigDecimal aValue) {
		FAKWR=aValue;
	}
	public void setFKSAF(java.lang.String aValue) {
		FKSAF=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS011.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS011"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SO_NO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SO_NO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FAKWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FAKWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKSAF");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKSAF"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("SO_NO",(short)1,10);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("FAKWR",(short)4,13);
		ds.addColumn("FKSAF",(short)1,1);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}