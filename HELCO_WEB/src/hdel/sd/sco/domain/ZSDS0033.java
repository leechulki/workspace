package hdel.sd.sco.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZSDS0033 implements java.io.Serializable {

	public ZSDS0033() {
	}

	private String VBELN;
	private String HOGI;
	private String BSTNK;
	private String NAME1;
	private String KUNNR_PYT;
	private String VKBUR;
	private String VKBURT;
	private String VKGRP;
	private String VKGRPT;
	private String CUNNR;
	private String CNAME1;
	private String CONTR_DA;
	private String BSTDK;
	private BigDecimal NETWR;
	private BigDecimal WAVWR;
	private String ARKTX;
	private String ZSPEC1;
	private int ZSPEC7;
	private String ZSPEC3;
	private String ZSPEC5;
	private String ZSPEC4;
	private String ZSPEC8;
	private String TMTYPE;
	private String ZZPRO01;
	private String KUNNR;
	private String KUNNR_PY;
	private String RECAD_DA;
	private String WAERK;

	public String getVBELN(){
		return VBELN;
	}

	public String getHOGI(){
		return HOGI;
	}

	public String getBSTNK(){
		return BSTNK;
	}

	public String getNAME1(){
		return NAME1;
	}

	public String getKUNNR_PYT(){
		return KUNNR_PYT;
	}

	public String getVKBUR(){
		return VKBUR;
	}

	public String getVKBURT(){
		return VKBURT;
	}

	public String getVKGRP(){
		return VKGRP;
	}

	public String getVKGRPT(){
		return VKGRPT;
	}

	public String getCUNNR(){
		return CUNNR;
	}

	public String getCNAME1(){
		return CNAME1;
	}

	public String getCONTR_DA(){
		return CONTR_DA;
	}

	public String getBSTDK(){
		return BSTDK;
	}

	public BigDecimal getNETWR(){
		return NETWR;
	}

	public BigDecimal getWAVWR(){
		return WAVWR;
	}

	public String getARKTX(){
		return ARKTX;
	}

	public String getZSPEC1(){
		return ZSPEC1;
	}

	public int getZSPEC7(){
		return ZSPEC7;
	}

	public String getZSPEC3(){
		return ZSPEC3;
	}

	public String getZSPEC5(){
		return ZSPEC5;
	}

	public String getZSPEC4(){
		return ZSPEC4;
	}

	public String getZSPEC8(){
		return ZSPEC8;
	}

	public String getTMTYPE(){
		return TMTYPE;
	}

	public String getZZPRO01(){
		return ZZPRO01;
	}

	public String getKUNNR(){
		return KUNNR;
	}

	public String getKUNNR_PY(){
		return KUNNR_PY;
	}

	public String getRECAD_DA(){
		return RECAD_DA;
	}

	public String getWAERK(){
		return WAERK;
	}

	public void setVBELN(String aValue) {
		VBELN=aValue;
	}

	public void setHOGI(String aValue) {
		HOGI=aValue;
	}

	public void setBSTNK(String aValue) {
		BSTNK=aValue;
	}

	public void setNAME1(String aValue) {
		NAME1=aValue;
	}

	public void setKUNNR_PYT(String aValue) {
		KUNNR_PYT=aValue;
	}

	public void setVKBUR(String aValue) {
		VKBUR=aValue;
	}

	public void setVKBURT(String aValue) {
		VKBURT=aValue;
	}

	public void setVKGRP(String aValue) {
		VKGRP=aValue;
	}

	public void setVKGRPT(String aValue) {
		VKGRPT=aValue;
	}

	public void setCUNNR(String aValue) {
		CUNNR=aValue;
	}

	public void setCNAME1(String aValue) {
		CNAME1=aValue;
	}

	public void setCONTR_DA(String aValue) {
		CONTR_DA=aValue;
	}

	public void setBSTDK(String aValue) {
		BSTDK=aValue;
	}

	public void setNETWR(BigDecimal aValue) {
		NETWR=aValue;
	}

	public void setWAVWR(BigDecimal aValue) {
		WAVWR=aValue;
	}

	public void setARKTX(String aValue) {
		ARKTX=aValue;
	}

	public void setZSPEC1(String aValue) {
		ZSPEC1=aValue;
	}

	public void setZSPEC7(int aValue) {
		ZSPEC7=aValue;
	}

	public void setZSPEC3(String aValue) {
		ZSPEC3=aValue;
	}

	public void setZSPEC5(String aValue) {
		ZSPEC5=aValue;
	}

	public void setZSPEC4(String aValue) {
		ZSPEC4=aValue;
	}

	public void setZSPEC8(String aValue) {
		ZSPEC8=aValue;
	}

	public void setTMTYPE(String aValue) {
		TMTYPE=aValue;
	}

	public void setZZPRO01(String aValue) {
		ZZPRO01=aValue;
	}

	public void setKUNNR(String aValue) {
		KUNNR=aValue;
	}

	public void setKUNNR_PY(String aValue) {
		KUNNR_PY=aValue;
	}

	public void setRECAD_DA(String aValue) {
		RECAD_DA=aValue;
	}

	public void setWAERK(String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0033.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0033"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HOGI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTNK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTNK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR_PYT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR_PYT"));
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
		elemField.setFieldName("VKBURT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBURT"));
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
		elemField.setFieldName("VKGRPT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRPT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CNAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CNAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONTR_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONTR_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAVWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAVWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ARKTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARKTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC7");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC7"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSPEC8");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSPEC8"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TMTYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TMTYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZPRO01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPRO01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR_PY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR_PY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RECAD_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RECAD_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
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

		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("BSTNK",(short)1,20);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("KUNNR_PYT",(short)1,35);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKBURT",(short)1,20);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("VKGRPT",(short)1,20);
		ds.addColumn("CUNNR",(short)1,10);
		ds.addColumn("CNAME1",(short)1,35);
		ds.addColumn("CONTR_DA",(short)1,8);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("NETWR",(short)4,15);
		ds.addColumn("WAVWR",(short)4,15);
		ds.addColumn("ARKTX",(short)1,40);
		ds.addColumn("ZSPEC1",(short)1,5);
		ds.addColumn("ZSPEC7",(short)2,3);
		ds.addColumn("ZSPEC3",(short)1,4);
		ds.addColumn("ZSPEC5",(short)1,5);
		ds.addColumn("ZSPEC4",(short)1,2);
		ds.addColumn("ZSPEC8",(short)1,4);
		ds.addColumn("TMTYPE",(short)1,30);
		ds.addColumn("ZZPRO01",(short)1,8);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("KUNNR_PY",(short)1,10);
		ds.addColumn("RECAD_DA",(short)1,8);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}