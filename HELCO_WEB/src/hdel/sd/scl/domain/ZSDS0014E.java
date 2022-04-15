package hdel.sd.scl.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0014E   
		implements java.io.Serializable {

	public ZSDS0014E() {
	}
	public java.lang.String VBELN;
	public java.lang.String POSNR;
	public java.lang.String KUNNR;
	public java.lang.String KUNNRT;
	public java.lang.String BSTNK;
	public java.lang.String LAND1;
	public java.lang.String BSTDK;
	public java.lang.String CONTR_DA;
	public java.lang.String WAERK;
	public java.lang.String VKBUR;
	public java.lang.String VKBURT;
	public java.lang.String VKGRP;
	public java.lang.String VKGRPT;
	public java.lang.String ZKUNNR;
	public java.lang.String ZKUNNRT;
	public java.math.BigDecimal NETWR;
	public java.lang.String ZZPJT_ID;
	public java.lang.String POSID;
	public java.lang.String ZQNTY;
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPOSNR(){
		return POSNR;
	}
	public java.lang.String getKUNNR(){
		return KUNNR;
	}
	public java.lang.String getKUNNRT(){
		return KUNNRT;
	}
	public java.lang.String getBSTNK(){
		return BSTNK;
	}
	public java.lang.String getLAND1(){
		return LAND1;
	}
	public java.lang.String getBSTDK(){
		return BSTDK;
	}
	public java.lang.String getCONTR_DA(){
		return CONTR_DA;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getVKBUR(){
		return VKBUR;
	}
	public java.lang.String getVKBURT(){
		return VKBURT;
	}
	public java.lang.String getVKGRP(){
		return VKGRP;
	}
	public java.lang.String getVKGRPT(){
		return VKGRPT;
	}
	public java.lang.String getZKUNNR(){
		return ZKUNNR;
	}
	public java.lang.String getZKUNNRT(){
		return ZKUNNRT;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.lang.String getZZPJT_ID(){
		return ZZPJT_ID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getZQNTY(){
		return ZQNTY;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPOSNR(java.lang.String aValue) {
		POSNR=aValue;
	}
	public void setKUNNR(java.lang.String aValue) {
		KUNNR=aValue;
	}
	public void setKUNNRT(java.lang.String aValue) {
		KUNNRT=aValue;
	}
	public void setBSTNK(java.lang.String aValue) {
		BSTNK=aValue;
	}
	public void setLAND1(java.lang.String aValue) {
		LAND1=aValue;
	}
	public void setBSTDK(java.lang.String aValue) {
		BSTDK=aValue;
	}
	public void setCONTR_DA(java.lang.String aValue) {
		CONTR_DA=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setVKBUR(java.lang.String aValue) {
		VKBUR=aValue;
	}
	public void setVKBURT(java.lang.String aValue) {
		VKBURT=aValue;
	}
	public void setVKGRP(java.lang.String aValue) {
		VKGRP=aValue;
	}
	public void setVKGRPT(java.lang.String aValue) {
		VKGRPT=aValue;
	}
	public void setZKUNNR(java.lang.String aValue) {
		ZKUNNR=aValue;
	}
	public void setZKUNNRT(java.lang.String aValue) {
		ZKUNNRT=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setZZPJT_ID(java.lang.String aValue) {
		ZZPJT_ID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setZQNTY(java.lang.String aValue) {
		ZQNTY=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0014E.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0014E"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNRT"));
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
		elemField.setFieldName("LAND1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAND1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONTR_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONTR_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBURT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBURT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRPT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRPT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKUNNRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZPJT_ID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPJT_ID"));
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
		elemField.setFieldName("ZQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQNTY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("POSNR",(short)1,6);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("KUNNRT",(short)1,35);
		ds.addColumn("BSTNK",(short)1,20);
		ds.addColumn("LAND1",(short)1,3);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("CONTR_DA",(short)1,8);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKBURT",(short)1,20);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("VKGRPT",(short)1,20);
		ds.addColumn("ZKUNNR",(short)1,10);
		ds.addColumn("ZKUNNRT",(short)1,35);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("ZZPJT_ID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ZQNTY",(short)1,3);
		return ds;
	}

}