package hdel.sd.sco.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZSDS0066 implements java.io.Serializable {

	public ZSDS0066() {
	}

	private String VKBUR;
	private String VKGRP;
	private BigDecimal SJ_MS;
	private BigDecimal SJ_NS;
	private BigDecimal MC_MS;
	private BigDecimal MC_NS;
	private BigDecimal SG_MS;
	private BigDecimal SG_NS;
	private BigDecimal SJ_WMS;
	private BigDecimal SJ_WNS;
	private String WAERK;

	public String getVKBUR(){
		return VKBUR;
	}

	public String getVKGRP(){
		return VKGRP;
	}

	public BigDecimal getSJ_MS(){
		return SJ_MS;
	}

	public BigDecimal getSJ_NS(){
		return SJ_NS;
	}

	public BigDecimal getMC_MS(){
		return MC_MS;
	}

	public BigDecimal getMC_NS(){
		return MC_NS;
	}

	public BigDecimal getSG_MS(){
		return SG_MS;
	}

	public BigDecimal getSG_NS(){
		return SG_NS;
	}

	public BigDecimal getSJ_WMS(){
		return SJ_WMS;
	}

	public BigDecimal getSJ_WNS(){
		return SJ_WNS;
	}

	public String getWAERK(){
		return WAERK;
	}

	public void setVKBUR(String aValue) {
		VKBUR=aValue;
	}

	public void setVKGRP(String aValue) {
		VKGRP=aValue;
	}

	public void setSJ_MS(BigDecimal aValue) {
		SJ_MS=aValue;
	}

	public void setSJ_NS(BigDecimal aValue) {
		SJ_NS=aValue;
	}

	public void setMC_MS(BigDecimal aValue) {
		MC_MS=aValue;
	}

	public void setMC_NS(BigDecimal aValue) {
		MC_NS=aValue;
	}

	public void setSG_MS(BigDecimal aValue) {
		SG_MS=aValue;
	}

	public void setSG_NS(BigDecimal aValue) {
		SG_NS=aValue;
	}

	public void setSJ_WMS(BigDecimal aValue) {
		SJ_WMS=aValue;
	}

	public void setSJ_WNS(BigDecimal aValue) {
		SJ_WNS=aValue;
	}

	public void setWAERK(String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0066.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0066"));
		org.apache.axis.description.ElementDesc elemField =null;
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
		elemField.setFieldName("SJ_MS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SJ_MS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SJ_NS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SJ_NS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MC_MS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MC_MS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MC_NS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MC_NS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SG_MS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SG_MS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SG_NS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SG_NS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SJ_WMS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SJ_WMS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SJ_WNS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SJ_WNS"));
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

		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("SJ_MS",(short)4,13);
		ds.addColumn("SJ_NS",(short)4,13);
		ds.addColumn("MC_MS",(short)4,13);
		ds.addColumn("MC_NS",(short)4,13);
		ds.addColumn("SG_MS",(short)4,13);
		ds.addColumn("SG_NS",(short)4,13);
		ds.addColumn("SJ_WMS",(short)4,13);
		ds.addColumn("SJ_WNS",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}