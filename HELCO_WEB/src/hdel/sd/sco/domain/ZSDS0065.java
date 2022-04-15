package hdel.sd.sco.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZSDS0065 implements java.io.Serializable {

	public ZSDS0065() {
	}

	private String GUBUNA;
	private String VKBUR;
	private String VKGRP;
	private BigDecimal YAMT_T;
	private BigDecimal JMAMT_T;
	private BigDecimal DAMT_T;
	private BigDecimal NAMT_T;
	private BigDecimal JYAMT_S;
	private String WAERK;

	public String getGUBUNA(){
		return GUBUNA;
	}

	public String getVKBUR(){
		return VKBUR;
	}

	public String getVKGRP(){
		return VKGRP;
	}

	public BigDecimal getYAMT_T(){
		return YAMT_T;
	}

	public BigDecimal getJMAMT_T(){
		return JMAMT_T;
	}

	public BigDecimal getDAMT_T(){
		return DAMT_T;
	}

	public BigDecimal getNAMT_T(){
		return NAMT_T;
	}

	public BigDecimal getJYAMT_S(){
		return JYAMT_S;
	}

	public String getWAERK(){
		return WAERK;
	}

	public void setGUBUNA(String aValue) {
		GUBUNA=aValue;
	}

	public void setVKBUR(String aValue) {
		VKBUR=aValue;
	}

	public void setVKGRP(String aValue) {
		VKGRP=aValue;
	}

	public void setYAMT_T(BigDecimal aValue) {
		YAMT_T=aValue;
	}

	public void setJMAMT_T(BigDecimal aValue) {
		JMAMT_T=aValue;
	}

	public void setDAMT_T(BigDecimal aValue) {
		DAMT_T=aValue;
	}

	public void setNAMT_T(BigDecimal aValue) {
		NAMT_T=aValue;
	}

	public void setJYAMT_S(BigDecimal aValue) {
		JYAMT_S=aValue;
	}

	public void setWAERK(String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0065.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0065"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUNA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUNA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("YAMT_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "YAMT_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JMAMT_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JMAMT_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DAMT_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DAMT_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAMT_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAMT_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JYAMT_S");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JYAMT_S"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();

		ds.addColumn("GUBUNA",(short)1,1);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("YAMT_T",(short)4,15);
		ds.addColumn("JMAMT_T",(short)4,15);
		ds.addColumn("DAMT_T",(short)4,15);
		ds.addColumn("NAMT_T",(short)4,15);
		ds.addColumn("JYAMT_S",(short)4,15);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}