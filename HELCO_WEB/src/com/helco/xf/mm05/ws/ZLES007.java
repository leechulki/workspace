package com.helco.xf.mm05.ws;
import com.tobesoft.platform.data.Dataset;
public class ZLES007   
		implements java.io.Serializable {

	public ZLES007() {
	}
	public java.lang.String ZBELN;
	public java.lang.String ZOSNR;
	public java.lang.String PSPID;
	public java.lang.String PSPNR;
	public java.lang.String POSID3;
	public java.lang.String BUDAT;
	public java.lang.String RSNUM;
	public java.lang.String RSPOS;
	public java.lang.String MATNR;
	public java.math.BigDecimal LFIMG;
	public java.lang.String ERFME;
	public java.lang.String MBLNR;
	public java.lang.String AENAM;
	public java.lang.String ZRTNVAL;
	public java.lang.String ZRTNMSG;
	public java.lang.String getZBELN(){
		return ZBELN;
	}
	public java.lang.String getZOSNR(){
		return ZOSNR;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPSPNR(){
		return PSPNR;
	}
	public java.lang.String getPOSID3(){
		return POSID3;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.lang.String getRSNUM(){
		return RSNUM;
	}
	public java.lang.String getRSPOS(){
		return RSPOS;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.math.BigDecimal getLFIMG(){
		return LFIMG;
	}
	public java.lang.String getERFME(){
		return ERFME;
	}
	public java.lang.String getMBLNR(){
		return MBLNR;
	}
	public java.lang.String getAENAM(){
		return AENAM;
	}
	public java.lang.String getZRTNVAL(){
		return ZRTNVAL;
	}
	public java.lang.String getZRTNMSG(){
		return ZRTNMSG;
	}
	public void setZBELN(java.lang.String aValue) {
		ZBELN=aValue;
	}
	public void setZOSNR(java.lang.String aValue) {
		ZOSNR=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPSPNR(java.lang.String aValue) {
		PSPNR=aValue;
	}
	public void setPOSID3(java.lang.String aValue) {
		POSID3=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setRSNUM(java.lang.String aValue) {
		RSNUM=aValue;
	}
	public void setRSPOS(java.lang.String aValue) {
		RSPOS=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setLFIMG(java.math.BigDecimal aValue) {
		LFIMG=aValue;
	}
	public void setERFME(java.lang.String aValue) {
		ERFME=aValue;
	}
	public void setMBLNR(java.lang.String aValue) {
		MBLNR=aValue;
	}
	public void setAENAM(java.lang.String aValue) {
		AENAM=aValue;
	}
	public void setZRTNVAL(java.lang.String aValue) {
		ZRTNVAL=aValue;
	}
	public void setZRTNMSG(java.lang.String aValue) {
		ZRTNMSG=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZLES007.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZLES007"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZOSNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZOSNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RSNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RSNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RSPOS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RSPOS"));
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
		elemField.setFieldName("LFIMG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LFIMG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERFME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERFME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MBLNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MBLNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AENAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AENAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZRTNVAL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZRTNVAL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZRTNMSG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZRTNMSG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ZBELN",(short)1,10);
		ds.addColumn("ZOSNR",(short)1,6);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("PSPNR",(short)1,8);
		ds.addColumn("POSID3",(short)1,24);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("RSNUM",(short)1,10);
		ds.addColumn("RSPOS",(short)1,4);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("LFIMG",(short)4,13);
		ds.addColumn("ERFME",(short)1,3);
		ds.addColumn("MBLNR",(short)1,10);
		ds.addColumn("AENAM",(short)1,12);
		ds.addColumn("ZRTNVAL",(short)1,1);
		ds.addColumn("ZRTNMSG",(short)1,64);
		return ds;
	}

}