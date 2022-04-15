package com.helco.xf.mm07.ws;
import com.tobesoft.platform.data.Dataset;
public class ZMMS021   
		implements java.io.Serializable {

	public ZMMS021() {
	}
	public java.lang.String MATNR;
	public java.math.BigDecimal NETPR;
	public java.lang.String DZEINR;
	public java.lang.String MATNR_NM;
	public java.lang.String SPEC;
	public java.lang.String ZSIZE;
	public java.lang.String BLOCK_NO;
	public java.lang.String MEINS;

	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.math.BigDecimal getNETPR(){
		return NETPR;
	}
	public java.lang.String getDZEINR(){
		return DZEINR;
	}
	public java.lang.String getMATNR_NM(){
		return MATNR_NM;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.lang.String getZSIZE(){
		return ZSIZE;
	}
	public java.lang.String getBLOCK_NO(){
		return BLOCK_NO;
	}	
	public java.lang.String getMEINS(){
		return MEINS;
	}	
	
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setNETPR(java.math.BigDecimal aValue) {
		NETPR=aValue;
	}
	public void setDZEINR(java.lang.String aValue) {
		DZEINR=aValue;
	}
	public void setMATNR_NM(java.lang.String aValue) {
		MATNR_NM=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setZSIZE(java.lang.String aValue) {
		ZSIZE=aValue;
	}
	public void setBLOCK_NO(java.lang.String aValue) {
		BLOCK_NO=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZMMS021.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZMMS021"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETPR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETPR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR_NM"));
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
		elemField.setFieldName("ZSIZE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSIZE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLOCK_NO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLOCK_NO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEINS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEINS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("NETPR",(short)4,13);
		ds.addColumn("DZEINR",(short)1,40);
		ds.addColumn("MATNR_NM",(short)1,40);
		ds.addColumn("SPEC",(short)1,60);
		ds.addColumn("ZSIZE",(short)1,60);
		ds.addColumn("BLOCK_NO",(short)1,9);
		ds.addColumn("MEINS",(short)1,3);

		return ds;
	}
	/*
	public static void setTypeDesc(org.apache.axis.description.TypeDesc typeDesc) {
		ZMMS021.typeDesc = typeDesc;
	}
	*/

}