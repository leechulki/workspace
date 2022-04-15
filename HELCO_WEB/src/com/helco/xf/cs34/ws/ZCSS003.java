package com.helco.xf.cs34.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS003   
		implements java.io.Serializable {

	public ZCSS003() {
	}
	public java.lang.String ARA_NM;
	public java.lang.String BSU_NM;
	public java.lang.String VBELN;
	public java.lang.String POSID;
	public java.lang.String KUNNR_NM;
	public java.lang.String BSTKD;
	public java.math.BigDecimal NETWR;
	public java.lang.String BSTDK;
	public java.lang.String CONTR_DA;
	public java.lang.String OFKDAT;
	public java.lang.String RKFKDAT;
	public java.math.BigDecimal RKNETWR;
	public java.math.BigDecimal SUJU;
	public java.lang.String WAERS;
	public java.lang.String PM_NAME;
	public java.lang.String PARVW_PY;
	public java.lang.String getARA_NM(){
		return ARA_NM;
	}
	public java.lang.String getBSU_NM(){
		return BSU_NM;
	}
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getKUNNR_NM(){
		return KUNNR_NM;
	}
	public java.lang.String getBSTKD(){
		return BSTKD;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.lang.String getBSTDK(){
		return BSTDK;
	}
	public java.lang.String getCONTR_DA(){
		return CONTR_DA;
	}
	public java.lang.String getOFKDAT(){
		return OFKDAT;
	}
	public java.lang.String getRKFKDAT(){
		return RKFKDAT;
	}
	public java.math.BigDecimal getRKNETWR(){
		return RKNETWR;
	}
	public java.math.BigDecimal getSUJU(){
		return SUJU;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getPM_NAME(){
		return PM_NAME;
	}
	public java.lang.String getPARVW_PY(){
		return PARVW_PY;
	}
	public void setARA_NM(java.lang.String aValue) {
		ARA_NM=aValue;
	}
	public void setBSU_NM(java.lang.String aValue) {
		BSU_NM=aValue;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setKUNNR_NM(java.lang.String aValue) {
		KUNNR_NM=aValue;
	}
	public void setBSTKD(java.lang.String aValue) {
		BSTKD=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setBSTDK(java.lang.String aValue) {
		BSTDK=aValue;
	}
	public void setCONTR_DA(java.lang.String aValue) {
		CONTR_DA=aValue;
	}
	public void setOFKDAT(java.lang.String aValue) {
		OFKDAT=aValue;
	}
	public void setRKFKDAT(java.lang.String aValue) {
		RKFKDAT=aValue;
	}
	public void setRKNETWR(java.math.BigDecimal aValue) {
		RKNETWR=aValue;
	}
	public void setSUJU(java.math.BigDecimal aValue) {
		SUJU=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setPM_NAME(java.lang.String aValue) {
		PM_NAME=aValue;
	}
	public void setPARVW_PY(java.lang.String aValue) {
		PARVW_PY=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS003.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS003"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ARA_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARA_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSU_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSU_NM"));
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
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTKD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTKD"));
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
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
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
		elemField.setFieldName("OFKDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OFKDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RKFKDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RKFKDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RKNETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RKNETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SUJU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SUJU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PM_NAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PM_NAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PARVW_PY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PARVW_PY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ARA_NM",(short)1,35);
		ds.addColumn("BSU_NM",(short)1,35);
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("KUNNR_NM",(short)1,35);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("CONTR_DA",(short)1,8);
		ds.addColumn("OFKDAT",(short)1,8);
		ds.addColumn("RKFKDAT",(short)1,8);
		ds.addColumn("RKNETWR",(short)4,13);
		ds.addColumn("SUJU",(short)4,13);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("PM_NAME",(short)1,12);
		ds.addColumn("PARVW_PY",(short)1,35);
		return ds;
	}

}