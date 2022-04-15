package com.helco.xf.qm06.ws;
import com.tobesoft.platform.data.Dataset;
public class ZMMS014   
		implements java.io.Serializable {

	public ZMMS014() {
	}
	public java.lang.String CRDAT;
	public java.lang.String POSID;
	public java.lang.String POST1;
	public java.lang.String MATNR;
	public java.lang.String MAKTX;
	public java.lang.String MEINS;
	public java.lang.String CGROUPTXT;
	public java.lang.String NAMET;
	public java.math.BigDecimal MENGE1;
	public java.math.BigDecimal MENGE2;
	public java.math.BigDecimal MENGE3;
	public java.math.BigDecimal MENGE4;
	public java.lang.String PRCTYP;
	public java.lang.String getCRDAT(){
		return CRDAT;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getMAKTX(){
		return MAKTX;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.lang.String getCGROUPTXT(){
		return CGROUPTXT;
	}
	public java.lang.String getNAMET(){
		return NAMET;
	}
	public java.math.BigDecimal getMENGE1(){
		return MENGE1;
	}
	public java.math.BigDecimal getMENGE2(){
		return MENGE2;
	}
	public java.math.BigDecimal getMENGE3(){
		return MENGE3;
	}
	public java.math.BigDecimal getMENGE4(){
		return MENGE4;
	}
	public java.lang.String getPRCTYP(){
		return PRCTYP;
	}
	public void setCRDAT(java.lang.String aValue) {
		CRDAT=aValue;
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
	public void setMAKTX(java.lang.String aValue) {
		MAKTX=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setCGROUPTXT(java.lang.String aValue) {
		CGROUPTXT=aValue;
	}
	public void setNAMET(java.lang.String aValue) {
		NAMET=aValue;
	}
	public void setMENGE1(java.math.BigDecimal aValue) {
		MENGE1=aValue;
	}
	public void setMENGE2(java.math.BigDecimal aValue) {
		MENGE2=aValue;
	}
	public void setMENGE3(java.math.BigDecimal aValue) {
		MENGE3=aValue;
	}
	public void setMENGE4(java.math.BigDecimal aValue) {
		MENGE4=aValue;
	}
	public void setPRCTYP(java.lang.String aValue) {
		PRCTYP=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZMMS014.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZMMS014"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRDAT"));
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
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
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
		elemField.setFieldName("CGROUPTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CGROUPTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAMET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAMET"));
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
		elemField.setFieldName("MENGE2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MENGE3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MENGE4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRCTYP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRCTYP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("CRDAT",(short)1,8);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("MAKTX",(short)1,40);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("CGROUPTXT",(short)1,12);
		ds.addColumn("NAMET",(short)1,20);
		ds.addColumn("MENGE1",(short)4,13);
		ds.addColumn("MENGE2",(short)4,13);
		ds.addColumn("MENGE3",(short)4,13);
		ds.addColumn("MENGE4",(short)4,13);
		ds.addColumn("PRCTYP",(short)1,1);
		return ds;
	}

}