package com.helco.xf.bs05.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCABS004   
		implements java.io.Serializable {

	public ZCABS004() {
	}
	public java.lang.String POSNR;
	public java.lang.String ATKLA;
	public java.lang.String CD_KEY;
	public java.lang.String VL_KEY;
	public java.lang.String ATNAM;
	public java.lang.String ATBEZ;
	public java.lang.String VALUE;
	public java.lang.String ATWTB;
//	public java.math.BigDecimal DMBTR;
	public java.lang.String DMBTR;
	public java.lang.String WAERS;
	public java.lang.String MESSG;
	public java.lang.String VAKEY;
	public java.lang.String NEWER;
	public java.lang.String DEPEN;
	public java.lang.String QUANT;
	public java.lang.String getPOSNR(){
		return POSNR;
	}
	public java.lang.String getATKLA(){
		return ATKLA;
	}
	public java.lang.String getCD_KEY(){
		return CD_KEY;
	}
	public java.lang.String getVL_KEY(){
		return VL_KEY;
	}
	public java.lang.String getATNAM(){
		return ATNAM;
	}
	public java.lang.String getATBEZ(){
		return ATBEZ;
	}
	public java.lang.String getVALUE(){
		return VALUE;
	}
	public java.lang.String getATWTB(){
		return ATWTB;
	}
//	public java.math.BigDecimal getDMBTR(){
//		return DMBTR;
//	}
	public java.lang.String getDMBTR(){
		return DMBTR;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getMESSG(){
		return MESSG;
	}
	public java.lang.String getVAKEY(){
		return VAKEY;
	}
	public java.lang.String getNEWER(){
		return NEWER;
	}
	public java.lang.String getDEPEN(){
		return DEPEN;
	}
	public java.lang.String getQUANT(){
		return QUANT;
	}
	public void setPOSNR(java.lang.String aValue) {
		POSNR=aValue;
	}
	public void setATKLA(java.lang.String aValue) {
		ATKLA=aValue;
	}
	public void setCD_KEY(java.lang.String aValue) {
		CD_KEY=aValue;
	}
	public void setVL_KEY(java.lang.String aValue) {
		VL_KEY=aValue;
	}
	public void setATNAM(java.lang.String aValue) {
		ATNAM=aValue;
	}
	public void setATBEZ(java.lang.String aValue) {
		ATBEZ=aValue;
	}
	public void setVALUE(java.lang.String aValue) {
		VALUE=aValue;
	}
	public void setATWTB(java.lang.String aValue) {
		ATWTB=aValue;
	}
//	public void setDMBTR(java.math.BigDecimal aValue) {
//		DMBTR=aValue;
//	}
	public void setDMBTR(java.lang.String aValue) {
		DMBTR=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setMESSG(java.lang.String aValue) {
		MESSG=aValue;
	}
	public void setVAKEY(java.lang.String aValue) {
		VAKEY=aValue;
	}
	public void setNEWER(java.lang.String aValue) {
		NEWER=aValue;
	}
	public void setDEPEN(java.lang.String aValue) {
		DEPEN=aValue;
	}
	public void setQUANT(java.lang.String aValue) {
		QUANT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCABS004.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCABS004"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATKLA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATKLA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CD_KEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CD_KEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VL_KEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VL_KEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATBEZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATBEZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VALUE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VALUE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATWTB");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWTB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DMBTR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DMBTR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MESSG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MESSG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAKEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAKEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NEWER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NEWER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEPEN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEPEN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QUANT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QUANT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSNR",(short)1,3);
		ds.addColumn("ATKLA",(short)1,10);
		ds.addColumn("CD_KEY",(short)1,20);
		ds.addColumn("VL_KEY",(short)1,50);
		ds.addColumn("ATNAM",(short)1,30);
		ds.addColumn("ATBEZ",(short)1,30);
		ds.addColumn("VALUE",(short)1,30);
		ds.addColumn("ATWTB",(short)1,30);
		ds.addColumn("DMBTR",(short)1,10);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("MESSG",(short)1,20);
		ds.addColumn("VAKEY",(short)1,50);
		ds.addColumn("NEWER",(short)1,20);
		ds.addColumn("DEPEN",(short)1,20);
		ds.addColumn("QUANT",(short)1,20);
		return ds;
	}

}