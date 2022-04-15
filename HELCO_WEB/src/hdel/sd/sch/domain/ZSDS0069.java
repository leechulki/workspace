package hdel.sd.sch.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0069   
		implements java.io.Serializable {

	public ZSDS0069() {
	}
	public java.lang.String VKORG;
	public java.lang.String VBELN;
	public java.lang.String VTWEG;
	public java.lang.String SPART;
	public java.lang.String KUNNR;
	public java.lang.String NAME1;
	public java.lang.String ORT01;
	public java.lang.String FKDAT;
	public java.math.BigDecimal NETWR;
	public java.lang.String WAERK;
	public java.lang.String VKBUR;
	public java.lang.String VKGRP;
	public java.lang.String ZKUNNR;
	public java.lang.String BILLDAT;

	public java.lang.String getVKORG(){
		return VKORG;
	}
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getVTWEG(){
		return VTWEG;
	}
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getKUNNR(){
		return KUNNR;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.lang.String getORT01(){
		return ORT01;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getVKBUR(){
		return VKBUR;
	}
	public java.lang.String getVKGRP(){
		return VKGRP;
	}
	public java.lang.String getZKUNNR(){
		return ZKUNNR;
	}
	public void setVKORG(java.lang.String aValue) {
		VKORG=aValue;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setVTWEG(java.lang.String aValue) {
		VTWEG=aValue;
	}
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setKUNNR(java.lang.String aValue) {
		KUNNR=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setORT01(java.lang.String aValue) {
		ORT01=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setVKBUR(java.lang.String aValue) {
		VKBUR=aValue;
	}
	public void setVKGRP(java.lang.String aValue) {
		VKGRP=aValue;
	}
	public void setZKUNNR(java.lang.String aValue) {
		ZKUNNR=aValue;
	}
	public java.lang.String getBILLDAT() {
		return BILLDAT;
	}
	public void setBILLDAT(java.lang.String bILLDAT) {
		BILLDAT = bILLDAT;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0069.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0069"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKORG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKORG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VTWEG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VTWEG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ORT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ORT01"));
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
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BILLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BILLDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("VKORG",(short)1,4);
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("VTWEG",(short)1,2);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("ORT01",(short)1,35);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("ZKUNNR",(short)1,10);
		ds.addColumn("BILLDAT",(short)1,8);
		return ds;
	}

}