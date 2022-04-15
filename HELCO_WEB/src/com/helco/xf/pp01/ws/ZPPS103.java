package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS103   
		implements java.io.Serializable {

	public ZPPS103() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String POST1;
	public java.lang.String CHASU;
	public java.lang.String ITEMNO;
	public java.lang.String MATNR;
	public java.lang.String ITEMNAM;
	public java.lang.String MATNAM;
	public java.math.BigDecimal MENGE1;
	public java.lang.String MEINS;
	public java.lang.String DGDATE;
	public java.lang.String IGDATE;
	public java.lang.String SSCDAT;
	public java.lang.String IBGO1;
	public java.lang.String IBGO2;
	public java.lang.String INPDAT4;
	public java.lang.String CRDAT;
	public java.lang.String CRCOD;
	public java.lang.String CRNAM;
	public java.lang.String STATUS;
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getCHASU(){
		return CHASU;
	}
	public java.lang.String getITEMNO(){
		return ITEMNO;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getITEMNAM(){
		return ITEMNAM;
	}
	public java.lang.String getMATNAM(){
		return MATNAM;
	}
	public java.math.BigDecimal getMENGE1(){
		return MENGE1;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.lang.String getDGDATE(){
		return DGDATE;
	}
	public java.lang.String getIGDATE(){
		return IGDATE;
	}
	public java.lang.String getSSCDAT(){
		return SSCDAT;
	}
	public java.lang.String getIBGO1(){
		return IBGO1;
	}
	public java.lang.String getIBGO2(){
		return IBGO2;
	}
	public java.lang.String getINPDAT4(){
		return INPDAT4;
	}
	public java.lang.String getCRDAT(){
		return CRDAT;
	}
	public java.lang.String getCRCOD(){
		return CRCOD;
	}
	public java.lang.String getCRNAM(){
		return CRNAM;
	}
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setCHASU(java.lang.String aValue) {
		CHASU=aValue;
	}
	public void setITEMNO(java.lang.String aValue) {
		ITEMNO=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setITEMNAM(java.lang.String aValue) {
		ITEMNAM=aValue;
	}
	public void setMATNAM(java.lang.String aValue) {
		MATNAM=aValue;
	}
	public void setMENGE1(java.math.BigDecimal aValue) {
		MENGE1=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setDGDATE(java.lang.String aValue) {
		DGDATE=aValue;
	}
	public void setIGDATE(java.lang.String aValue) {
		IGDATE=aValue;
	}
	public void setSSCDAT(java.lang.String aValue) {
		SSCDAT=aValue;
	}
	public void setIBGO1(java.lang.String aValue) {
		IBGO1=aValue;
	}
	public void setIBGO2(java.lang.String aValue) {
		IBGO2=aValue;
	}
	public void setINPDAT4(java.lang.String aValue) {
		INPDAT4=aValue;
	}
	public void setCRDAT(java.lang.String aValue) {
		CRDAT=aValue;
	}
	public void setCRCOD(java.lang.String aValue) {
		CRCOD=aValue;
	}
	public void setCRNAM(java.lang.String aValue) {
		CRNAM=aValue;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS103.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS103"));
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
		elemField.setFieldName("POST1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POST1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHASU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHASU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEMNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEMNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEMNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEMNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MENGE1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE1"));
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
		elemField.setFieldName("DGDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DGDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("IGDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IGDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SSCDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SSCDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("IBGO1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IBGO1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("IBGO2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IBGO2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INPDAT4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INPDAT4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRCOD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRCOD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("CHASU",(short)1,2);
		ds.addColumn("ITEMNO",(short)1,9);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("ITEMNAM",(short)1,30);
		ds.addColumn("MATNAM",(short)1,40);
		ds.addColumn("MENGE1",(short)4,13);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("DGDATE",(short)1,8);
		ds.addColumn("IGDATE",(short)1,8);
		ds.addColumn("SSCDAT",(short)1,8);
		ds.addColumn("IBGO1",(short)1,8);
		ds.addColumn("IBGO2",(short)1,8);
		ds.addColumn("INPDAT4",(short)1,8);
		ds.addColumn("CRDAT",(short)1,8);
		ds.addColumn("CRCOD",(short)1,10);
		ds.addColumn("CRNAM",(short)1,35);
		ds.addColumn("STATUS",(short)1,10);
		return ds;
	}

}