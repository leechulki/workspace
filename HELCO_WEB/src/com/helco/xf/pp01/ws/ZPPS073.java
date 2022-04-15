package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS073   
		implements java.io.Serializable {

	public ZPPS073() {
	}
	public java.lang.String POSID;
	public java.lang.String POST1;
	public java.lang.String ATYPE;
	public java.lang.String SPEC;
	public java.math.BigDecimal KWMENG;
	public java.lang.String VRKME;
	public java.lang.String EDATU;
	public java.lang.String JMENGE;
	public java.math.BigDecimal MMENGE;
	public java.math.BigDecimal SMENGE;
	public java.math.BigDecimal CMENGE;
	public java.math.BigDecimal GMENGE;
	public java.lang.String MEINS;
	public java.lang.String MSPEC;
	public java.lang.String MZSIZE;
	public java.lang.String DZEINR;
	public java.lang.String PPLDAT;
	public java.lang.String SSCDAT;
	public java.lang.String ZSPEC4;
	public java.lang.String ZSPEC5;
	public java.lang.String ZSPEC6;
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.math.BigDecimal getKWMENG(){
		return KWMENG;
	}
	public java.lang.String getVRKME(){
		return VRKME;
	}
	public java.lang.String getEDATU(){
		return EDATU;
	}
	public java.lang.String getJMENGE(){
		return JMENGE;
	}
	public java.math.BigDecimal getMMENGE(){
		return MMENGE;
	}
	public java.math.BigDecimal getSMENGE(){
		return SMENGE;
	}
	public java.math.BigDecimal getCMENGE(){
		return CMENGE;
	}
	public java.math.BigDecimal getGMENGE(){
		return GMENGE;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.lang.String getMSPEC(){
		return MSPEC;
	}
	public java.lang.String getMZSIZE(){
		return MZSIZE;
	}
	public java.lang.String getDZEINR(){
		return DZEINR;
	}
	public java.lang.String getPPLDAT(){
		return PPLDAT;
	}
	public java.lang.String getSSCDAT(){
		return SSCDAT;
	}
	public java.lang.String getZSPEC4(){
		return ZSPEC4;
	}
	public java.lang.String getZSPEC5(){
		return ZSPEC5;
	}
	public java.lang.String getZSPEC6(){
		return ZSPEC6;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setKWMENG(java.math.BigDecimal aValue) {
		KWMENG=aValue;
	}
	public void setVRKME(java.lang.String aValue) {
		VRKME=aValue;
	}
	public void setEDATU(java.lang.String aValue) {
		EDATU=aValue;
	}
	public void setJMENGE(java.lang.String aValue) {
		JMENGE=aValue;
	}
	public void setMMENGE(java.math.BigDecimal aValue) {
		MMENGE=aValue;
	}
	public void setSMENGE(java.math.BigDecimal aValue) {
		SMENGE=aValue;
	}
	public void setCMENGE(java.math.BigDecimal aValue) {
		CMENGE=aValue;
	}
	public void setGMENGE(java.math.BigDecimal aValue) {
		GMENGE=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setMSPEC(java.lang.String aValue) {
		MSPEC=aValue;
	}
	public void setMZSIZE(java.lang.String aValue) {
		MZSIZE=aValue;
	}
	public void setDZEINR(java.lang.String aValue) {
		DZEINR=aValue;
	}
	public void setPPLDAT(java.lang.String aValue) {
		PPLDAT=aValue;
	}
	public void setSSCDAT(java.lang.String aValue) {
		SSCDAT=aValue;
	}
	public void setZSPEC4(java.lang.String aValue) {
		ZSPEC4=aValue;
	}
	public void setZSPEC5(java.lang.String aValue) {
		ZSPEC5=aValue;
	}
	public void setZSPEC6(java.lang.String aValue) {
		ZSPEC6=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS073.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS073"));
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
		elemField.setFieldName("ATYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWMENG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWMENG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VRKME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VRKME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EDATU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EDATU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GMENGE"));
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
		elemField.setFieldName("MSPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MZSIZE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MZSIZE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DZEINR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DZEINR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PPLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PPLDAT"));
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
		elemField.setFieldName("ZSPEC4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC6");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC6"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("ATYPE",(short)1,5);
		ds.addColumn("SPEC",(short)1,30);
		ds.addColumn("KWMENG",(short)4,13);
		ds.addColumn("VRKME",(short)1,3);
		ds.addColumn("EDATU",(short)1,8);
		ds.addColumn("JMENGE",(short)1,10);
		ds.addColumn("MMENGE",(short)4,13);
		ds.addColumn("SMENGE",(short)4,13);
		ds.addColumn("CMENGE",(short)4,13);
		ds.addColumn("GMENGE",(short)4,13);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("MSPEC",(short)1,60);
		ds.addColumn("MZSIZE",(short)1,60);
		ds.addColumn("DZEINR",(short)1,40);
		ds.addColumn("PPLDAT",(short)1,8);
		ds.addColumn("SSCDAT",(short)1,8);
		ds.addColumn("ZSPEC4",(short)1,2);
		ds.addColumn("ZSPEC5",(short)1,5);
		ds.addColumn("ZSPEC6",(short)1,4);
		return ds;
	}

}