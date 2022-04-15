package com.helco.xf.ps01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0017   
		implements java.io.Serializable {

	public ZSDS0017() {
	}
	public java.lang.String HOGI;
	public java.lang.String ARKTX;
	public java.lang.String NAME1;
	public java.lang.String BSTNK;
	public java.lang.String VDATU;
	public java.lang.String SAL_MAN;
	public java.lang.String TEC_MAN;
	public java.lang.String TELNO;
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getARKTX(){
		return ARKTX;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.lang.String getBSTNK(){
		return BSTNK;
	}
	public java.lang.String getVDATU(){
		return VDATU;
	}
	public java.lang.String getSAL_MAN(){
		return SAL_MAN;
	}
	public java.lang.String getTEC_MAN(){
		return TEC_MAN;
	}
	public java.lang.String getTELNO(){
		return TELNO;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setARKTX(java.lang.String aValue) {
		ARKTX=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setBSTNK(java.lang.String aValue) {
		BSTNK=aValue;
	}
	public void setVDATU(java.lang.String aValue) {
		VDATU=aValue;
	}
	public void setSAL_MAN(java.lang.String aValue) {
		SAL_MAN=aValue;
	}
	public void setTEC_MAN(java.lang.String aValue) {
		TEC_MAN=aValue;
	}
	public void setTELNO(java.lang.String aValue) {
		TELNO=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0017.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0017"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HOGI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ARKTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARKTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTNK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTNK"));
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
		elemField.setFieldName("SAL_MAN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SAL_MAN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEC_MAN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEC_MAN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TELNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TELNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("ARKTX",(short)1,40);
		ds.addColumn("NAME1",(short)1,20);
		ds.addColumn("BSTNK",(short)1,30);
		ds.addColumn("VDATU",(short)1,8);
		ds.addColumn("SAL_MAN",(short)1,20);
		ds.addColumn("TEC_MAN",(short)1,20);
		ds.addColumn("TELNO",(short)1,20);
		return ds;
	}

}