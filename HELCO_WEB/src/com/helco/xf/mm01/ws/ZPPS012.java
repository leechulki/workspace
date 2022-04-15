package com.helco.xf.mm01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS012   
		implements java.io.Serializable {

	public ZPPS012() {
	}
	public java.lang.String MANDT;
	public java.lang.String SEQ;
	public java.lang.String CRDAT;
	public java.lang.String SEQNO;
	public java.lang.String WOKNUM;
	public java.lang.String MATNR;
	public java.lang.String IDNRK;
	public java.lang.String MATKL;
	public java.lang.String ITEM_SEQ;
	public java.lang.String SPEC_TYP;
	public java.lang.String SERIAL;
	public java.lang.String TEXT;
	public java.lang.String getMANDT(){
		return MANDT;
	}
	public java.lang.String getCRDAT(){
		return CRDAT;
	}
	public java.lang.String getSEQNO(){
		return SEQNO;
	}
	public java.lang.String getWOKNUM(){
		return WOKNUM;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getIDNRK(){
		return IDNRK;
	}
	public java.lang.String getMATKL(){
		return MATKL;
	}
	public java.lang.String getITEM_SEQ(){
		return ITEM_SEQ;
	}
	public java.lang.String getSPEC_TYP(){
		return SPEC_TYP;
	}
	public java.lang.String getSERIAL(){
		return SERIAL;
	}
	public java.lang.String getTEXT(){
		return TEXT;
	}
	public void setMANDT(java.lang.String aValue) {
		MANDT=aValue;
	}
	public void setCRDAT(java.lang.String aValue) {
		CRDAT=aValue;
	}
	public void setSEQNO(java.lang.String aValue) {
		SEQNO=aValue;
	}
	public void setWOKNUM(java.lang.String aValue) {
		WOKNUM=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setIDNRK(java.lang.String aValue) {
		IDNRK=aValue;
	}
	public void setMATKL(java.lang.String aValue) {
		MATKL=aValue;
	}
	public void setITEM_SEQ(java.lang.String aValue) {
		ITEM_SEQ=aValue;
	}
	public void setSPEC_TYP(java.lang.String aValue) {
		SPEC_TYP=aValue;
	}
	public void setSERIAL(java.lang.String aValue) {
		SERIAL=aValue;
	}
	public void setTEXT(java.lang.String aValue) {
		TEXT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS012.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS012"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SEQNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SEQNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WOKNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WOKNUM"));
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
		elemField.setFieldName("IDNRK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IDNRK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATKL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATKL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEM_SEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEM_SEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPEC_TYP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC_TYP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SERIAL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SERIAL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("SEQ",(short)1,3);
		ds.addColumn("CRDAT",(short)1,8);
		ds.addColumn("SEQNO",(short)1,4);
		ds.addColumn("WOKNUM",(short)1,24);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("IDNRK",(short)1,18);
		ds.addColumn("MATKL",(short)1,9);
		ds.addColumn("ITEM_SEQ",(short)1,4);
		ds.addColumn("SPEC_TYP",(short)1,1);
		ds.addColumn("SERIAL",(short)1,4);
		ds.addColumn("TEXT",(short)1,100);
		return ds;
	}

}