package com.helco.xf.cs34.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS010   
		implements java.io.Serializable {

	public ZCSS010() {
	}
	public java.lang.String ARA_NM;
	public java.lang.String BSU_NM;
	public java.lang.String VBELN;
	public java.lang.String POSID;
	public java.lang.String KUNNR_NM;
	public java.lang.String BSTKD;
	public java.math.BigDecimal NETWR;
	public java.lang.String BSTDK;
	public java.lang.String WAERS;
	public java.lang.String PM_NAME;
	public java.lang.String PARVW_PY;
	public java.lang.String STATUS;	
	public java.lang.String JOBEND;
	public java.lang.String BILDAT;
	public java.math.BigDecimal BILAMT1;
	public java.math.BigDecimal JANGO;
	public java.lang.String CHD_TXT;
	public java.math.BigDecimal SILJANGO;
	public java.lang.String QTNAM;
	public java.lang.String SPART;
	public java.lang.String SPART_NM;
	
	public java.lang.String CRNAME;
	public java.lang.String CRNAM;
	
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
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getPM_NAME(){
		return PM_NAME;
	}
	public java.lang.String getPARVW_PY(){
		return PARVW_PY;
	}
	public java.lang.String getSTATUS(){
		return STATUS;
	}	
	public java.lang.String getJOBEND(){
		return JOBEND;
	}
	public java.lang.String getBILDAT(){
		return BILDAT;
	}
	public java.math.BigDecimal getBILAMT1(){
		return BILAMT1;
	}
	public java.math.BigDecimal getJANGO(){
		return JANGO;
	}
	public java.lang.String getCHD_TXT(){
		return CHD_TXT;
	}
	public java.math.BigDecimal getSILJANGO(){
		return SILJANGO;
	}
	public java.lang.String getQTNAM(){
		return QTNAM;
	}
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getSPART_NM(){
		return SPART_NM;
	}
	
	public java.lang.String getCRNAME(){
		return CRNAME;
	}
	public java.lang.String getCRNAM(){
		return CRNAM;
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
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setPM_NAME(java.lang.String aValue) {
		PM_NAME=aValue;
	}
	public void setPARVW_PY(java.lang.String aValue) {
		PARVW_PY=aValue;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	public void setJOBEND(java.lang.String aValue) {
		JOBEND=aValue;
	}
	public void setBILDAT(java.lang.String aValue) {
		BILDAT=aValue;
	}
	public void setBILAMT1(java.math.BigDecimal aValue) {
		BILAMT1=aValue;
	}
	public void setJANGO(java.math.BigDecimal aValue) {
		JANGO=aValue;
	}
	public void setCHD_TXT(java.lang.String aValue) {
		CHD_TXT=aValue;
	}
	public void setSILJANGO(java.math.BigDecimal aValue) {
		SILJANGO=aValue;
	}
	public void setQTNAM(java.lang.String aValue) {
		QTNAM=aValue;
	}
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setSPART_NM(java.lang.String aValue) {
		SPART_NM=aValue;
	}
	
	public void setCRNAME(java.lang.String aValue) {
		CRNAME=aValue;
	}
	public void setCRNAM(java.lang.String aValue) {
		CRNAM=aValue;
	}	
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS010.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS010"));
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
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JOBEND");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JOBEND"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BILDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BILDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BILAMT1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BILAMT1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JANGO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JANGO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHD_TXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHD_TXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SILJANGO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SILJANGO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTNAM"));
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
		elemField.setFieldName("SPART_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPART_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRNAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRNAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRNAM"));
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
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("PM_NAME",(short)1,12);
		ds.addColumn("PARVW_PY",(short)1,35);
		ds.addColumn("STATUS",(short)1,1);
		ds.addColumn("JOBEND",(short)1,1);
		ds.addColumn("BILDAT",(short)1,8);
		ds.addColumn("BILAMT1",(short)4,13);
		ds.addColumn("JANGO",(short)4,13);
		ds.addColumn("CHD_TXT",(short)1,100);
		ds.addColumn("SILJANGO",(short)4,13);
		ds.addColumn("QTNAM",(short)1,100);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("SPART_NM",(short)1,20);
		
		ds.addColumn("CRNAME",(short)1,20);
		ds.addColumn("CRNAM",(short)1,12);
		return ds;
	}

}