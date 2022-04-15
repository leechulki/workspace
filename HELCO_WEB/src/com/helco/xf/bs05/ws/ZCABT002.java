package com.helco.xf.bs05.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCABT002   
		implements java.io.Serializable {

	public ZCABT002() {
	}
	public java.lang.String MANDT;
	public java.lang.String CD_TYPE;
	public java.lang.String CD_KEY;
	public java.lang.String VL_KEY;
	public java.lang.String OPT_CD1;
	public java.lang.String OPT_CD2;
	public java.lang.String OPT_CD3;
	public java.lang.String OPT_CD4;
	public java.lang.String OPT_CD5;
	public java.lang.String OPT_CD6;
	public java.lang.String OPT_CD7;
	public java.lang.String OPT_CD8;
	public java.lang.String OPT_CD9;
	public java.lang.String CD_NAME;
	public java.lang.String ANDAT;
	public java.lang.String AEDAT;
	public java.lang.String USNAM;
	public java.lang.String FORMULA;
	public java.lang.String CALC;
	public java.lang.String getMANDT(){
		return MANDT;
	}
	public java.lang.String getCD_TYPE(){
		return CD_TYPE;
	}
	public java.lang.String getCD_KEY(){
		return CD_KEY;
	}
	public java.lang.String getVL_KEY(){
		return VL_KEY;
	}
	public java.lang.String getOPT_CD1(){
		return OPT_CD1;
	}
	public java.lang.String getOPT_CD2(){
		return OPT_CD2;
	}
	public java.lang.String getOPT_CD3(){
		return OPT_CD3;
	}
	public java.lang.String getOPT_CD4(){
		return OPT_CD4;
	}
	public java.lang.String getOPT_CD5(){
		return OPT_CD5;
	}
	public java.lang.String getOPT_CD6(){
		return OPT_CD6;
	}
	public java.lang.String getOPT_CD7(){
		return OPT_CD7;
	}
	public java.lang.String getOPT_CD8(){
		return OPT_CD8;
	}
	public java.lang.String getOPT_CD9(){
		return OPT_CD9;
	}
	public java.lang.String getCD_NAME(){
		return CD_NAME;
	}
	public java.lang.String getANDAT(){
		return ANDAT;
	}
	public java.lang.String getAEDAT(){
		return AEDAT;
	}
	public java.lang.String getUSNAM(){
		return USNAM;
	}
	public java.lang.String getFORMULA(){
		return FORMULA;
	}
	public java.lang.String getCALC(){
		return CALC;
	}
	public void setMANDT(java.lang.String aValue) {
		MANDT=aValue;
	}
	public void setCD_TYPE(java.lang.String aValue) {
		CD_TYPE=aValue;
	}
	public void setCD_KEY(java.lang.String aValue) {
		CD_KEY=aValue;
	}
	public void setVL_KEY(java.lang.String aValue) {
		VL_KEY=aValue;
	}
	public void setOPT_CD1(java.lang.String aValue) {
		OPT_CD1=aValue;
	}
	public void setOPT_CD2(java.lang.String aValue) {
		OPT_CD2=aValue;
	}
	public void setOPT_CD3(java.lang.String aValue) {
		OPT_CD3=aValue;
	}
	public void setOPT_CD4(java.lang.String aValue) {
		OPT_CD4=aValue;
	}
	public void setOPT_CD5(java.lang.String aValue) {
		OPT_CD5=aValue;
	}
	public void setOPT_CD6(java.lang.String aValue) {
		OPT_CD6=aValue;
	}
	public void setOPT_CD7(java.lang.String aValue) {
		OPT_CD7=aValue;
	}
	public void setOPT_CD8(java.lang.String aValue) {
		OPT_CD8=aValue;
	}
	public void setOPT_CD9(java.lang.String aValue) {
		OPT_CD9=aValue;
	}
	public void setCD_NAME(java.lang.String aValue) {
		CD_NAME=aValue;
	}
	public void setANDAT(java.lang.String aValue) {
		ANDAT=aValue;
	}
	public void setAEDAT(java.lang.String aValue) {
		AEDAT=aValue;
	}
	public void setUSNAM(java.lang.String aValue) {
		USNAM=aValue;
	}
	public void setFORMULA(java.lang.String aValue) {
		FORMULA=aValue;
	}
	public void setCALC(java.lang.String aValue) {
		CALC=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCABT002.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCABT002"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CD_TYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CD_TYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CD_KEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CD_KEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VL_KEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VL_KEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD6");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD6"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD7");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD7"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD8");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD8"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPT_CD9");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPT_CD9"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CD_NAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CD_NAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ANDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ANDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AEDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AEDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("USNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "USNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FORMULA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FORMULA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CALC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CALC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("CD_TYPE",(short)1,4);
		ds.addColumn("CD_KEY",(short)1,20);
		ds.addColumn("VL_KEY",(short)1,50);
		ds.addColumn("OPT_CD1",(short)1,20);
		ds.addColumn("OPT_CD2",(short)1,20);
		ds.addColumn("OPT_CD3",(short)1,20);
		ds.addColumn("OPT_CD4",(short)1,20);
		ds.addColumn("OPT_CD5",(short)1,20);
		ds.addColumn("OPT_CD6",(short)1,20);
		ds.addColumn("OPT_CD7",(short)1,20);
		ds.addColumn("OPT_CD8",(short)1,20);
		ds.addColumn("OPT_CD9",(short)1,20);
		ds.addColumn("CD_NAME",(short)1,50);
		ds.addColumn("ANDAT",(short)1,8);
		ds.addColumn("AEDAT",(short)1,8);
		ds.addColumn("USNAM",(short)1,12);
		ds.addColumn("FORMULA",(short)1,100);
		ds.addColumn("CALC",(short)1,1);
		return ds;
	}

}