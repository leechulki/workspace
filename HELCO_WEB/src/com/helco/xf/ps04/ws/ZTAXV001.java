package com.helco.xf.ps04.ws;
import com.tobesoft.platform.data.Dataset;
public class ZTAXV001   
		implements java.io.Serializable {

	public ZTAXV001() {
	}
	public java.lang.String MANDT;
	public java.lang.String WRKLFN;
	public java.lang.String BELNR;
	public java.lang.String BLDAT;
	public java.lang.String PSPID;
	public java.lang.String ZSITE_NM;
	public java.math.BigDecimal TWBTR;
	public java.lang.String getMANDT(){
		return MANDT;
	}
	public java.lang.String getWRKLFN(){
		return WRKLFN;
	}
	public java.lang.String getBELNR(){
		return BELNR;
	}
	public java.lang.String getBLDAT(){
		return BLDAT;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getZSITE_NM(){
		return ZSITE_NM;
	}
	public java.math.BigDecimal getTWBTR(){
		return TWBTR;
	}
	public void setMANDT(java.lang.String aValue) {
		MANDT=aValue;
	}
	public void setWRKLFN(java.lang.String aValue) {
		WRKLFN=aValue;
	}
	public void setBELNR(java.lang.String aValue) {
		BELNR=aValue;
	}
	public void setBLDAT(java.lang.String aValue) {
		BLDAT=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setZSITE_NM(java.lang.String aValue) {
		ZSITE_NM=aValue;
	}
	public void setTWBTR(java.math.BigDecimal aValue) {
		TWBTR=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZTAXV001.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZTAXV001"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WRKLFN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WRKLFN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BELNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BELNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSITE_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSITE_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TWBTR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TWBTR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("WRKLFN",(short)1,10);
		ds.addColumn("BELNR",(short)1,10);
		ds.addColumn("BLDAT",(short)1,8);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("ZSITE_NM",(short)1,35);
		ds.addColumn("TWBTR",(short)4,13);
		return ds;
	}

}