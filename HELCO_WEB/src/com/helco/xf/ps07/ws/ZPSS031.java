package com.helco.xf.ps07.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPSS031   
		implements java.io.Serializable {

	public ZPSS031() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String CHK_KIND;
	public java.lang.String CHASU;
	public java.lang.String ZZJUNGD;
	public java.lang.String MODE1;
	public java.lang.String INSP_BIGO;
	
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getCHK_KIND(){
		return CHK_KIND;
	}
	public java.lang.String getCHASU(){
		return CHASU;
	}
	public java.lang.String getZZJUNGD(){
		return ZZJUNGD;
	}
	public java.lang.String getMODE1(){
		return MODE1;
	}
	public java.lang.String getINSP_BIGO(){
		return INSP_BIGO;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setCHK_KIND(java.lang.String aValue) {
		CHK_KIND=aValue;
	}
	public void setCHASU(java.lang.String aValue) {
		CHASU=aValue;
	}
	public void setZZJUNGD(java.lang.String aValue) {
		ZZJUNGD=aValue;
	}
	public void setMODE1(java.lang.String aValue) {
		MODE1=aValue;
	}
	public void setINSP_BIGO(java.lang.String aValue) {
		INSP_BIGO=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS031.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS031"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
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
		elemField.setFieldName("CHK_KIND");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHK_KIND"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHASU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHASU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZJUNGD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZJUNGD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MODE1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MODE1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INSP_BIGO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INSP_BIGO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("CHK_KIND",(short)1,1);
		ds.addColumn("CHASU",(short)1,3);
		ds.addColumn("ZZJUNGD",(short)1,8);
		ds.addColumn("MODE1",(short)1,1);
		ds.addColumn("INSP_BIGO",(short)1,40);
		return ds;
	}

}