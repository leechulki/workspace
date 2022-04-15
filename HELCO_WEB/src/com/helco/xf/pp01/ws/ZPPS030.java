package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS030   
		implements java.io.Serializable {

	public ZPPS030() {
	}
	public java.lang.String POSID;
	public java.lang.String POST1;
	public java.lang.String BLOCK_NO;
	public java.lang.String MATNR_NM;
	public java.lang.String IBDMNG;
	public java.lang.String OBDMNG;
	public java.lang.String MEINS;
	public java.lang.String PRLAB1;
	public java.lang.String WODAT;
	public java.lang.String BUDAT;
	public java.lang.String PRLAB2;
	public java.lang.String BUDAT2;
	public java.lang.String PRLAB3;
	public java.lang.String WADAT_IST;
	public java.lang.String PRLAB4;
	public java.lang.String ILDAT;
	public java.lang.String BIGO;
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getBLOCK_NO(){
		return BLOCK_NO;
	}
	public java.lang.String getMATNR_NM(){
		return MATNR_NM;
	}
	public java.lang.String getIBDMNG(){
		return IBDMNG;
	}
	public java.lang.String getOBDMNG(){
		return OBDMNG;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.lang.String getPRLAB1(){
		return PRLAB1;
	}
	public java.lang.String getWODAT(){
		return WODAT;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.lang.String getPRLAB2(){
		return PRLAB2;
	}
	public java.lang.String getBUDAT2(){
		return BUDAT2;
	}
	public java.lang.String getPRLAB3(){
		return PRLAB3;
	}
	public java.lang.String getWADAT_IST(){
		return WADAT_IST;
	}
	public java.lang.String getPRLAB4(){
		return PRLAB4;
	}
	public java.lang.String getILDAT(){
		return ILDAT;
	}
	public java.lang.String getBIGO(){
		return BIGO;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setBLOCK_NO(java.lang.String aValue) {
		BLOCK_NO=aValue;
	}
	public void setMATNR_NM(java.lang.String aValue) {
		MATNR_NM=aValue;
	}
	public void setIBDMNG(java.lang.String aValue) {
		IBDMNG=aValue;
	}
	public void setOBDMNG(java.lang.String aValue) {
		OBDMNG=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setPRLAB1(java.lang.String aValue) {
		PRLAB1=aValue;
	}
	public void setWODAT(java.lang.String aValue) {
		WODAT=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setPRLAB2(java.lang.String aValue) {
		PRLAB2=aValue;
	}
	public void setBUDAT2(java.lang.String aValue) {
		BUDAT2=aValue;
	}
	public void setPRLAB3(java.lang.String aValue) {
		PRLAB3=aValue;
	}
	public void setWADAT_IST(java.lang.String aValue) {
		WADAT_IST=aValue;
	}
	public void setPRLAB4(java.lang.String aValue) {
		PRLAB4=aValue;
	}
	public void setILDAT(java.lang.String aValue) {
		ILDAT=aValue;
	}
	public void setBIGO(java.lang.String aValue) {
		BIGO=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS030.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS030"));
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
		elemField.setFieldName("BLOCK_NO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLOCK_NO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("IBDMNG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IBDMNG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OBDMNG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OBDMNG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEINS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEINS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRLAB1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRLAB1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WODAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WODAT"));
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
		elemField.setFieldName("PRLAB2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRLAB2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUDAT2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUDAT2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRLAB3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRLAB3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WADAT_IST");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WADAT_IST"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRLAB4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRLAB4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ILDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ILDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BIGO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BIGO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("BLOCK_NO",(short)1,9);
		ds.addColumn("MATNR_NM",(short)1,40);
		ds.addColumn("IBDMNG",(short)1,3);
		ds.addColumn("OBDMNG",(short)1,3);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("PRLAB1",(short)1,3);
		ds.addColumn("WODAT",(short)1,8);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("PRLAB2",(short)1,3);
		ds.addColumn("BUDAT2",(short)1,8);
		ds.addColumn("PRLAB3",(short)1,3);
		ds.addColumn("WADAT_IST",(short)1,8);
		ds.addColumn("PRLAB4",(short)1,3);
		ds.addColumn("ILDAT",(short)1,8);
		ds.addColumn("BIGO",(short)1,10);
		return ds;
	}

}