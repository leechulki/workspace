package com.helco.xf.cs36.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS015   
		implements java.io.Serializable {

	public ZCSS015() {
	}
	public java.lang.String INCGBNT;
	public java.lang.String LAND1;
	public java.lang.String LAND1T;
	public java.lang.String BSTDK;
	public java.lang.String VDATU;
	public java.lang.String VKBURT;
	public java.lang.String VKGRPT;
	public java.lang.String ZKUNNRT;
	public java.lang.String SPART;
	public java.lang.String AUART;
	public java.lang.String QTNUM;
	public java.lang.String QTNAM;
	public java.lang.String ZZPJT_ID;
	public java.lang.String BSTKD;
	public java.lang.String KUNNRT;
	public java.math.BigDecimal NETWR;
	public java.math.BigDecimal BNETWR;
	public java.math.BigDecimal EXTOKRW;
	public java.math.BigDecimal EXTOUSD;
	public java.lang.String WAERK;
	public java.math.BigDecimal JANGO;
	
	public java.lang.String getINCGBNT(){
		return INCGBNT;
	}
	public java.lang.String getLAND1(){
		return LAND1;
	}
	public java.lang.String getLAND1T(){
		return LAND1T;
	}

	public java.lang.String getBSTDK(){
		return BSTDK;
	}
	public java.lang.String getVDATU(){
		return VDATU;
	}
	public java.lang.String getVKBURT(){
		return VKBURT;
	}
	public java.lang.String getVKGRPT(){
		return VKGRPT;
	}
	public java.lang.String getZKUNNRT(){
		return ZKUNNRT;
	}
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getAUART(){
		return AUART;
	}	
	public java.lang.String getQTNUM(){
		return QTNUM;
	}
	public java.lang.String getQTNAM(){
		return QTNAM;
	}
	public java.lang.String getZZPJT_ID(){
		return ZZPJT_ID;
	}
	public java.lang.String getBSTKD(){
		return BSTKD;
	}
	public java.lang.String getKUNNRT(){
		return KUNNRT;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.math.BigDecimal getBNETWR(){
		return BNETWR;
	}
	public java.math.BigDecimal getEXTOKRW(){
		return EXTOKRW;
	}
	public java.math.BigDecimal getEXTOUSD(){
		return EXTOUSD;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.math.BigDecimal getJANGO(){
		return JANGO;
	}
	public void setINCGBNT(java.lang.String aValue) {
		INCGBNT=aValue;
	}
	public void setLAND1(java.lang.String aValue) {
		LAND1=aValue;
	}
	public void setLAND1T(java.lang.String aValue) {
		LAND1T=aValue;
	}
	public void setBSTDK(java.lang.String aValue) {
		BSTDK=aValue;
	}
	public void setVDATU(java.lang.String aValue) {
		VDATU=aValue;
	}
	public void setVKBURT(java.lang.String aValue) {
		VKBURT=aValue;
	}
	public void setVKGRPT(java.lang.String aValue) {
		VKGRPT=aValue;
	}
	public void setZKUNNRT(java.lang.String aValue) {
		ZKUNNRT=aValue;
	}
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setAUART(java.lang.String aValue) {
		AUART=aValue;
	}
	public void setQTNUM(java.lang.String aValue) {
		QTNUM=aValue;
	}
	public void setQTNAM(java.lang.String aValue) {
		QTNAM=aValue;
	}
	public void setZZPJT_ID(java.lang.String aValue) {
		ZZPJT_ID=aValue;
	}
	public void setBSTKD(java.lang.String aValue) {
		BSTKD=aValue;
	}
	public void setKUNNRT(java.lang.String aValue) {
		KUNNRT=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setBNETWR(java.math.BigDecimal aValue) {
		BNETWR=aValue;
	}
	public void setEXTOKRW(java.math.BigDecimal aValue) {
		EXTOKRW=aValue;
	}
	public void setEXTOUSD(java.math.BigDecimal aValue) {
		EXTOUSD=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setJANGO(java.math.BigDecimal aValue) {
		JANGO=aValue;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS015.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS015"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INCGBNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INCGBNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LAND1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAND1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LAND1T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAND1T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
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
		elemField.setFieldName("VKBURT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBURT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRPT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRPT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKUNNRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNRZ1T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNRZ1T"));
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
		elemField.setFieldName("AUART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZPJT_ID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPJT_ID"));
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
		elemField.setFieldName("KUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNRT"));
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
		elemField.setFieldName("BNETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BNETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EXTOKRW");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EXTOKRW"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EXTOUSD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EXTOUSD"));
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
		elemField.setFieldName("JANGO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JANGO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("INCGBNT",(short)1,50);
		ds.addColumn("LAND1",(short)1,50);
		ds.addColumn("LAND1T",(short)1,50);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("VDATU",(short)1,8);
		ds.addColumn("VKBURT",(short)1,25);
		ds.addColumn("VKGRPT",(short)1,25);
		ds.addColumn("ZKUNNRT",(short)1,10);
		ds.addColumn("KUNRZ1T",(short)1,25);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("QTNUM",(short)1,24);
		ds.addColumn("QTNAM",(short)1,24);
		ds.addColumn("ZZPJT_ID",(short)1,24);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("KUNNRT",(short)1,25);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("BNETWR",(short)4,13);
		ds.addColumn("EXTOKRW",(short)4,13);
		ds.addColumn("EXTOUSD",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("JANGO",(short)4,13);
		return ds;
	}
}