package com.helco.xf.qm06.ws;
import com.tobesoft.platform.data.Dataset;
public class ZMMS013   
		implements java.io.Serializable {

	public ZMMS013() {
	}
	public java.lang.String JQPRNUM;
	public java.lang.String JQPRNO;
	public java.lang.String POST1;
	public java.lang.String SERNO;
	public java.lang.String MATNR;
	public java.lang.String MAKTX;
	public java.lang.String OCCDT;
	public java.lang.String NAMET;
	public java.lang.String NAMET2;
	public java.lang.String CREDT;
	public java.lang.String CRENM;
	public java.lang.String APPDT03;
	public java.lang.String MEINS;
	public java.math.BigDecimal MENGE1;
	public java.math.BigDecimal MENGE2;
	public java.math.BigDecimal MENGE3;
	public java.math.BigDecimal MENGE4;
	public java.lang.String ZSDATE;
	public java.lang.String ZTON;
	public java.lang.String ZCDATE;
	public java.lang.String ZCDATE2;
	public java.lang.String ZTEXT;
	public java.lang.String POSID_1;
	public java.lang.String getJQPRNUM(){
		return JQPRNUM;
	}
	public java.lang.String getJQPRNO(){
		return JQPRNO;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getSERNO(){
		return SERNO;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getMAKTX(){
		return MAKTX;
	}
	public java.lang.String getOCCDT(){
		return OCCDT;
	}
	public java.lang.String getNAMET(){
		return NAMET;
	}
	public java.lang.String getNAMET2(){
		return NAMET2;
	}
	public java.lang.String getCREDT(){
		return CREDT;
	}
	public java.lang.String getCRENM(){
		return CRENM;
	}
	public java.lang.String getAPPDT03(){
		return APPDT03;
	}
	public java.lang.String getMEINS(){
		return MEINS;
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
	public java.lang.String getZSDATE(){
		return ZSDATE;
	}
	public java.lang.String getZTON(){
		return ZTON;
	}
	public java.lang.String getZCDATE(){
		return ZCDATE;
	}
	public java.lang.String getZCDATE2(){
		return ZCDATE2;
	}
	public java.lang.String getZTEXT(){
		return ZTEXT;
	}
	public java.lang.String getPOSID_1() {
		return POSID_1;
	}
	public void setJQPRNUM(java.lang.String aValue) {
		JQPRNUM=aValue;
	}
	public void setJQPRNO(java.lang.String aValue) {
		JQPRNO=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setSERNO(java.lang.String aValue) {
		SERNO=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setMAKTX(java.lang.String aValue) {
		MAKTX=aValue;
	}
	public void setOCCDT(java.lang.String aValue) {
		OCCDT=aValue;
	}
	public void setNAMET(java.lang.String aValue) {
		NAMET=aValue;
	}
	public void setNAMET2(java.lang.String aValue) {
		NAMET2=aValue;
	}
	public void setCREDT(java.lang.String aValue) {
		CREDT=aValue;
	}
	public void setCRENM(java.lang.String aValue) {
		CRENM=aValue;
	}
	public void setAPPDT03(java.lang.String aValue) {
		APPDT03=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
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
	public void setZSDATE(java.lang.String aValue) {
		ZSDATE=aValue;
	}
	public void setZTON(java.lang.String aValue) {
		ZTON=aValue;
	}
	public void setZCDATE(java.lang.String aValue) {
		ZCDATE=aValue;
	}
	public void setZCDATE2(java.lang.String aValue) {
		ZCDATE2=aValue;
	}
	public void setZTEXT(java.lang.String aValue) {
		ZTEXT=aValue;
	}
	public void setPOSID_1(java.lang.String posid_1) {
		POSID_1 = posid_1;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZMMS013.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZMMS013"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JQPRNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JQPRNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JQPRNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JQPRNO"));
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
		elemField.setFieldName("SERNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SERNO"));
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
		elemField.setFieldName("OCCDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OCCDT"));
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
		elemField.setFieldName("NAMET2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAMET2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CREDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CREDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRENM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRENM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("APPDT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "APPDT03"));
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
		elemField.setFieldName("ZSDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZTON");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTON"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZCDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZCDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZCDATE2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZCDATE2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID_1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID_1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("JQPRNUM",(short)1,10);
		ds.addColumn("JQPRNO",(short)1,20);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("SERNO",(short)1,2);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("MAKTX",(short)1,40);
		ds.addColumn("OCCDT",(short)1,8);
		ds.addColumn("NAMET",(short)1,20);
		ds.addColumn("NAMET2",(short)1,20);
		ds.addColumn("CREDT",(short)1,8);
		ds.addColumn("CRENM",(short)1,20);
		ds.addColumn("APPDT03",(short)1,8);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("MENGE1",(short)4,13);
		ds.addColumn("MENGE2",(short)4,13);
		ds.addColumn("MENGE3",(short)4,13);
		ds.addColumn("MENGE4",(short)4,13);
		ds.addColumn("ZSDATE",(short)1,8);
		ds.addColumn("ZTON",(short)1,10);
		ds.addColumn("ZCDATE",(short)1,8);
		ds.addColumn("ZCDATE2",(short)1,8);
		ds.addColumn("ZTEXT",(short)1,40);
		ds.addColumn("ZPOSID_1",(short)1,24);
		return ds;
	}


}