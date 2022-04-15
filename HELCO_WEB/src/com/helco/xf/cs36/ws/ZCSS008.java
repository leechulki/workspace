package com.helco.xf.cs36.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS008   
		implements java.io.Serializable {

	public ZCSS008() {
	}
	public java.lang.String ARA;
	public java.lang.String ARA_NM;
	public java.lang.String BSU;
	public java.lang.String BSU_NM;
	public java.math.BigDecimal TMAMT_U;
	public java.math.BigDecimal SMAMT_U;
	public java.math.BigDecimal TMAMT_S;
	public java.math.BigDecimal SMAMT_S;
	public java.math.BigDecimal TMAMT_B;
	public java.math.BigDecimal SMAMT_B;
	public java.math.BigDecimal TYAMT_U;
	public java.math.BigDecimal SYAMT_U;
	public java.math.BigDecimal TYAMT_S;
	public java.math.BigDecimal SYAMT_S;
	public java.math.BigDecimal TYAMT_B;
	public java.math.BigDecimal SYAMT_B;
	public java.lang.String getARA(){
		return ARA;
	}
	public java.lang.String getARA_NM(){
		return ARA_NM;
	}
	public java.lang.String getBSU(){
		return BSU;
	}
	public java.lang.String getBSU_NM(){
		return BSU_NM;
	}
	public java.math.BigDecimal getTMAMT_U(){
		return TMAMT_U;
	}
	public java.math.BigDecimal getSMAMT_U(){
		return SMAMT_U;
	}
	public java.math.BigDecimal getTMAMT_S(){
		return TMAMT_S;
	}
	public java.math.BigDecimal getSMAMT_S(){
		return SMAMT_S;
	}
	public java.math.BigDecimal getTMAMT_B(){
		return TMAMT_B;
	}
	public java.math.BigDecimal getSMAMT_B(){
		return SMAMT_B;
	}
	public java.math.BigDecimal getTYAMT_U(){
		return TYAMT_U;
	}
	public java.math.BigDecimal getSYAMT_U(){
		return SYAMT_U;
	}
	public java.math.BigDecimal getTYAMT_S(){
		return TYAMT_S;
	}
	public java.math.BigDecimal getSYAMT_S(){
		return SYAMT_S;
	}
	public java.math.BigDecimal getTYAMT_B(){
		return TYAMT_B;
	}
	public java.math.BigDecimal getSYAMT_B(){
		return SYAMT_B;
	}
	public void setARA(java.lang.String aValue) {
		ARA=aValue;
	}
	public void setARA_NM(java.lang.String aValue) {
		ARA_NM=aValue;
	}
	public void setBSU(java.lang.String aValue) {
		BSU=aValue;
	}
	public void setBSU_NM(java.lang.String aValue) {
		BSU_NM=aValue;
	}
	public void setTMAMT_U(java.math.BigDecimal aValue) {
		TMAMT_U=aValue;
	}
	public void setSMAMT_U(java.math.BigDecimal aValue) {
		SMAMT_U=aValue;
	}
	public void setTMAMT_S(java.math.BigDecimal aValue) {
		TMAMT_S=aValue;
	}
	public void setSMAMT_S(java.math.BigDecimal aValue) {
		SMAMT_S=aValue;
	}
	public void setTMAMT_B(java.math.BigDecimal aValue) {
		TMAMT_B=aValue;
	}
	public void setSMAMT_B(java.math.BigDecimal aValue) {
		SMAMT_B=aValue;
	}
	public void setTYAMT_U(java.math.BigDecimal aValue) {
		TYAMT_U=aValue;
	}
	public void setSYAMT_U(java.math.BigDecimal aValue) {
		SYAMT_U=aValue;
	}
	public void setTYAMT_S(java.math.BigDecimal aValue) {
		TYAMT_S=aValue;
	}
	public void setSYAMT_S(java.math.BigDecimal aValue) {
		SYAMT_S=aValue;
	}
	public void setTYAMT_B(java.math.BigDecimal aValue) {
		TYAMT_B=aValue;
	}
	public void setSYAMT_B(java.math.BigDecimal aValue) {
		SYAMT_B=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS008.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS008"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ARA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ARA_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARA_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSU_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSU_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TMAMT_U");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TMAMT_U"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SMAMT_U");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SMAMT_U"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TMAMT_S");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TMAMT_S"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SMAMT_S");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SMAMT_S"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TMAMT_B");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TMAMT_B"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SMAMT_B");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SMAMT_B"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TYAMT_U");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TYAMT_U"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SYAMT_U");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SYAMT_U"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TYAMT_S");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TYAMT_S"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SYAMT_S");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SYAMT_S"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TYAMT_B");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TYAMT_B"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SYAMT_B");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SYAMT_B"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ARA",(short)1,2);
		ds.addColumn("ARA_NM",(short)1,10);
		ds.addColumn("BSU",(short)1,4);
		ds.addColumn("BSU_NM",(short)1,25);
		ds.addColumn("TMAMT_U",(short)4,13);
		ds.addColumn("SMAMT_U",(short)4,13);
		ds.addColumn("TMAMT_S",(short)4,13);
		ds.addColumn("SMAMT_S",(short)4,13);
		ds.addColumn("TMAMT_B",(short)4,13);
		ds.addColumn("SMAMT_B",(short)4,13);
		ds.addColumn("TYAMT_U",(short)4,13);
		ds.addColumn("SYAMT_U",(short)4,13);
		ds.addColumn("TYAMT_S",(short)4,13);
		ds.addColumn("SYAMT_S",(short)4,13);
		ds.addColumn("TYAMT_B",(short)4,13);
		ds.addColumn("SYAMT_B",(short)4,13);
		return ds;
	}

}