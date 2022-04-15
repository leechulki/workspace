package com.helco.xf.bs05.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCABS006   
		implements java.io.Serializable {

	public ZCABS006() {
	}
	public java.lang.String EL;
	public java.lang.String ES;
	public java.lang.String FDATE;
	public java.lang.String TDATE;
	public java.lang.String VERSION;
	public java.lang.String TYPE;
	public java.math.BigDecimal BAAMT;
	public java.math.BigDecimal OPAM1;
	public java.math.BigDecimal OPAM2;
	public java.math.BigDecimal BUAMT;
	public java.math.BigDecimal REAMT;
	public java.math.BigDecimal TOTAL;
	public java.lang.String WAERS;
	public java.lang.String getEL(){
		return EL;
	}
	public java.lang.String getES(){
		return ES;
	}
	public java.lang.String getFDATE(){
		return FDATE;
	}
	public java.lang.String getTDATE(){
		return TDATE;
	}
	public java.lang.String getVERSION(){
		return VERSION;
	}
	public java.lang.String getTYPE(){
		return TYPE;
	}
	public java.math.BigDecimal getBAAMT(){
		return BAAMT;
	}
	public java.math.BigDecimal getOPAM1(){
		return OPAM1;
	}
	public java.math.BigDecimal getOPAM2(){
		return OPAM2;
	}
	public java.math.BigDecimal getBUAMT(){
		return BUAMT;
	}
	public java.math.BigDecimal getREAMT(){
		return REAMT;
	}
	public java.math.BigDecimal getTOTAL(){
		return TOTAL;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public void setEL(java.lang.String aValue) {
		EL=aValue;
	}
	public void setES(java.lang.String aValue) {
		ES=aValue;
	}
	public void setFDATE(java.lang.String aValue) {
		FDATE=aValue;
	}
	public void setTDATE(java.lang.String aValue) {
		TDATE=aValue;
	}
	public void setVERSION(java.lang.String aValue) {
		VERSION=aValue;
	}
	public void setTYPE(java.lang.String aValue) {
		TYPE=aValue;
	}
	public void setBAAMT(java.math.BigDecimal aValue) {
		BAAMT=aValue;
	}
	public void setOPAM1(java.math.BigDecimal aValue) {
		OPAM1=aValue;
	}
	public void setOPAM2(java.math.BigDecimal aValue) {
		OPAM2=aValue;
	}
	public void setBUAMT(java.math.BigDecimal aValue) {
		BUAMT=aValue;
	}
	public void setREAMT(java.math.BigDecimal aValue) {
		REAMT=aValue;
	}
	public void setTOTAL(java.math.BigDecimal aValue) {
		TOTAL=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCABS006.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCABS006"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ES");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ES"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VERSION");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VERSION"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BAAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BAAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPAM1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPAM1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPAM2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPAM2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TOTAL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TOTAL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("EL",(short)1,1);
		ds.addColumn("ES",(short)1,1);
		ds.addColumn("FDATE",(short)1,8);
		ds.addColumn("TDATE",(short)1,8);
		ds.addColumn("VERSION",(short)1,4);
		ds.addColumn("TYPE",(short)1,45);
		ds.addColumn("BAAMT",(short)4,11);
		ds.addColumn("OPAM1",(short)4,11);
		ds.addColumn("OPAM2",(short)4,11);
		ds.addColumn("BUAMT",(short)4,11);
		ds.addColumn("REAMT",(short)4,11);
		ds.addColumn("TOTAL",(short)4,11);
		ds.addColumn("WAERS",(short)1,5);
		return ds;
	}

}