package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS041   
		implements java.io.Serializable {

	public ZPPS041() {
	}
	public java.lang.String POSID;
	public java.lang.String POST1;
	public java.lang.String ATYPE;
	public java.lang.String SPEC;
	public java.math.BigDecimal MENGE;
	public java.lang.String MEINS;
	public java.lang.String GUBUN;
	public java.lang.String SPEC1;
	public java.lang.String ERPW;
	public java.lang.String PNDAT;
	public java.lang.String SHDAT;
	public java.lang.String SDDAT;
	public java.lang.String PLDAT;
	public java.lang.String PPDAT;
	public java.lang.String TEXT;
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.math.BigDecimal getMENGE(){
		return MENGE;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.lang.String getGUBUN(){
		return GUBUN;
	}
	public java.lang.String getSPEC1(){
		return SPEC1;
	}
	public java.lang.String getERPW(){
		return ERPW;
	}
	public java.lang.String getPNDAT(){
		return PNDAT;
	}
	public java.lang.String getSHDAT(){
		return SHDAT;
	}
	public java.lang.String getSDDAT(){
		return SDDAT;
	}
	public java.lang.String getPLDAT(){
		return PLDAT;
	}
	public java.lang.String getPPDAT(){
		return PPDAT;
	}
	public java.lang.String getTEXT(){
		return TEXT;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setMENGE(java.math.BigDecimal aValue) {
		MENGE=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setGUBUN(java.lang.String aValue) {
		GUBUN=aValue;
	}
	public void setSPEC1(java.lang.String aValue) {
		SPEC1=aValue;
	}
	public void setERPW(java.lang.String aValue) {
		ERPW=aValue;
	}
	public void setPNDAT(java.lang.String aValue) {
		PNDAT=aValue;
	}
	public void setSHDAT(java.lang.String aValue) {
		SHDAT=aValue;
	}
	public void setSDDAT(java.lang.String aValue) {
		SDDAT=aValue;
	}
	public void setPLDAT(java.lang.String aValue) {
		PLDAT=aValue;
	}
	public void setPPDAT(java.lang.String aValue) {
		PPDAT=aValue;
	}
	public void setTEXT(java.lang.String aValue) {
		TEXT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS041.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS041"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POST1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POST1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATYPE"));
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
		elemField.setFieldName("MENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEINS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEINS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPEC1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERPW");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERPW"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PNDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PNDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SHDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SHDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SDDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SDDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PPDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PPDAT"));
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
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("ATYPE",(short)1,5);
		ds.addColumn("SPEC",(short)1,30);
		ds.addColumn("MENGE",(short)4,13);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("GUBUN",(short)1,1);
		ds.addColumn("SPEC1",(short)1,60);
		ds.addColumn("ERPW",(short)1,30);
		ds.addColumn("PNDAT",(short)1,8);
		ds.addColumn("SHDAT",(short)1,8);
		ds.addColumn("SDDAT",(short)1,8);
		ds.addColumn("PLDAT",(short)1,8);
		ds.addColumn("PPDAT",(short)1,8);
		ds.addColumn("TEXT",(short)1,100);
		return ds;
	}

}