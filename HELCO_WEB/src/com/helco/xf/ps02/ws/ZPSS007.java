package com.helco.xf.ps02.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPSS007   
		implements java.io.Serializable {

	public ZPSS007() {
	}
	public java.lang.String POSID;
	public java.lang.String ZSITE_NM;
	public java.lang.String ZZACTSS;
	public java.lang.String TEXT30;
	public java.lang.String ZQNTY;
	public java.lang.String CONTR_DA;
	public java.lang.String VDATU;
	public java.lang.String ADDR1;
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getZSITE_NM(){
		return ZSITE_NM;
	}
	public java.lang.String getZZACTSS(){
		return ZZACTSS;
	}
	public java.lang.String getTEXT30(){
		return TEXT30;
	}
	public java.lang.String getZQNTY(){
		return ZQNTY;
	}
	public java.lang.String getCONTR_DA(){
		return CONTR_DA;
	}
	public java.lang.String getVDATU(){
		return VDATU;
	}
	public java.lang.String getADDR1(){
		return ADDR1;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setZSITE_NM(java.lang.String aValue) {
		ZSITE_NM=aValue;
	}
	public void setZZACTSS(java.lang.String aValue) {
		ZZACTSS=aValue;
	}
	public void setTEXT30(java.lang.String aValue) {
		TEXT30=aValue;
	}
	public void setZQNTY(java.lang.String aValue) {
		ZQNTY=aValue;
	}
	public void setCONTR_DA(java.lang.String aValue) {
		CONTR_DA=aValue;
	}
	public void setVDATU(java.lang.String aValue) {
		VDATU=aValue;
	}
	public void setADDR1(java.lang.String aValue) {
		ADDR1=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS007.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS007"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSITE_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSITE_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZACTSS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZACTSS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT30");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT30"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQNTY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONTR_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONTR_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VDATU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VDATU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ADDR1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ADDR1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ZSITE_NM",(short)1,35);
		ds.addColumn("ZZACTSS",(short)1,3);
		ds.addColumn("TEXT30",(short)1,30);
		ds.addColumn("ZQNTY",(short)1,3);
		ds.addColumn("CONTR_DA",(short)1,8);
		ds.addColumn("VDATU",(short)1,8);
		ds.addColumn("ADDR1",(short)1,70);
		return ds;
	}

}