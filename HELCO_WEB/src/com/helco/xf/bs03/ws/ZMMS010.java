package com.helco.xf.bs03.ws;
import com.tobesoft.platform.data.Dataset;
public class ZMMS010   
		implements java.io.Serializable {

	public ZMMS010() {
	}
	public java.lang.String POSID;
	public java.lang.String POST1;
	public java.lang.String MATNR;
	public java.lang.String MTART;
	public java.lang.String MAKTX;
	public java.lang.String MEINS;
	public java.lang.String LIFNR;
	public java.lang.String LNAME;
	public java.lang.String EKTEL;
	public java.lang.String LGOBE;
	public java.lang.String BADAT;
	public java.lang.String MENGE1;
	public java.lang.String BEDAT;
	public java.lang.String MENGE2;
	public java.lang.String BUDAT;
	public java.lang.String MENGE3;
	public java.lang.String BUDAT2;
	public java.lang.String MENGE4;
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getMTART(){
		return MTART;
	}
	public java.lang.String getMAKTX(){
		return MAKTX;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.lang.String getLIFNR() {
		return LIFNR;
	}
	public java.lang.String getLNAME() {
		return LNAME;
	}
	public java.lang.String getEKTEL(){
		return EKTEL;
	}
	public java.lang.String getLGOBE(){
		return LGOBE;
	}
	public java.lang.String getBADAT(){
		return BADAT;
	}
	public java.lang.String getMENGE1(){
		return MENGE1;
	}
	public java.lang.String getBEDAT(){
		return BEDAT;
	}
	public java.lang.String getMENGE2(){
		return MENGE2;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.lang.String getMENGE3(){
		return MENGE3;
	}
	public java.lang.String getBUDAT2(){
		return BUDAT2;
	}
	public java.lang.String getMENGE4(){
		return MENGE4;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setMTART(java.lang.String aValue) {
		MTART=aValue;
	}
	public void setMAKTX(java.lang.String aValue) {
		MAKTX=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setLIFNR(java.lang.String lifnr) {
		LIFNR = lifnr;
	}
	public void setLNAME(java.lang.String lname) {
		LNAME = lname;
	}
	public void setEKTEL(java.lang.String aValue) {
		EKTEL=aValue;
	}
	public void setLGOBE(java.lang.String aValue) {
		LGOBE=aValue;
	}
	public void setBADAT(java.lang.String aValue) {
		BADAT=aValue;
	}
	public void setMENGE1(java.lang.String aValue) {
		MENGE1=aValue;
	}
	public void setBEDAT(java.lang.String aValue) {
		BEDAT=aValue;
	}
	public void setMENGE2(java.lang.String aValue) {
		MENGE2=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setMENGE3(java.lang.String aValue) {
		MENGE3=aValue;
	}
	public void setBUDAT2(java.lang.String aValue) {
		BUDAT2=aValue;
	}
	public void setMENGE4(java.lang.String aValue) {
		MENGE4=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZMMS010.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZMMS010"));
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
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MTART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MTART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAKTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAKTX"));
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
		elemField.setFieldName("LIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LNAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LNAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EKTEL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EKTEL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LGOBE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LGOBE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BADAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BADAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MENGE1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BEDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BEDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MENGE2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE2"));
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
		elemField.setFieldName("MENGE3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE3"));
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
		elemField.setFieldName("MENGE4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("MTART",(short)1,4);
		ds.addColumn("MAKTX",(short)1,40);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("LIFNR",(short)1,10);
		ds.addColumn("LNAME",(short)1,30);
		ds.addColumn("EKTEL",(short)1,12);
		ds.addColumn("LGOBE",(short)1,16);
		ds.addColumn("BADAT",(short)1,8);
		ds.addColumn("MENGE1",(short)1,18);
		ds.addColumn("BEDAT",(short)1,8);
		ds.addColumn("MENGE2",(short)1,18);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("MENGE3",(short)1,18);
		ds.addColumn("BUDAT2",(short)1,8);
		ds.addColumn("MENGE4",(short)1,18);
		return ds;
	}


}