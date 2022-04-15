package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;


/**
 * [SD]매출채권 AGING SCHEDULE (ZSDS0032) Serializable
 * @Comment 
 *     	- Ssa0030C([SD]매출채권 AGING SCHEDULE) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */
 

public class ZSDS0032   
		implements java.io.Serializable {

	public ZSDS0032() {
	}
	public java.lang.String HKONT;
	public java.lang.String HKONTT;
	public java.lang.String LANDX;
	public java.lang.String KUNZT;
	public java.lang.String VKBUR;
	public java.lang.String VKBUT;
	public java.lang.String VKGRP;
	public java.lang.String VKGRT;
	public java.lang.String KIDNO;
	public java.lang.String POST1;
	public java.lang.String SPART;
	public java.lang.String KUNNR;
	public java.lang.String KUNNT;
	public java.lang.String WDATE;
	public java.lang.String ZFBDT;
	public java.lang.String AGING;
	public java.lang.String WAERS;
	public java.math.BigDecimal WRBTR;
	public java.lang.String WAERS2;
	public java.math.BigDecimal WRBTR2;
	public java.lang.String getHKONT(){
		return HKONT;
	}
	public java.lang.String getHKONTT(){
		return HKONTT;
	}
	public java.lang.String getLANDX(){
		return LANDX;
	}
	public java.lang.String getKUNZT(){
		return KUNZT;
	}
	public java.lang.String getVKBUR(){
		return VKBUR;
	}
	public java.lang.String getVKBUT(){
		return VKBUT;
	}
	public java.lang.String getVKGRP(){
		return VKGRP;
	}
	public java.lang.String getVKGRT(){
		return VKGRT;
	}
	public java.lang.String getKIDNO(){
		return KIDNO;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getKUNNR(){
		return KUNNR;
	}
	public java.lang.String getKUNNT(){
		return KUNNT;
	}
	public java.lang.String getWDATE(){
		return WDATE;
	}
	public java.lang.String getZFBDT(){
		return ZFBDT;
	}
	public java.lang.String getAGING(){
		return AGING;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.math.BigDecimal getWRBTR(){
		return WRBTR;
	}
	public java.lang.String getWAERS2(){
		return WAERS2;
	}
	public java.math.BigDecimal getWRBTR2(){
		return WRBTR2;
	}
	public void setHKONT(java.lang.String aValue) {
		HKONT=aValue;
	}
	public void setHKONTT(java.lang.String aValue) {
		HKONTT=aValue;
	}
	public void setLANDX(java.lang.String aValue) {
		LANDX=aValue;
	}
	public void setKUNZT(java.lang.String aValue) {
		KUNZT=aValue;
	}
	public void setVKBUR(java.lang.String aValue) {
		VKBUR=aValue;
	}
	public void setVKBUT(java.lang.String aValue) {
		VKBUT=aValue;
	}
	public void setVKGRP(java.lang.String aValue) {
		VKGRP=aValue;
	}
	public void setVKGRT(java.lang.String aValue) {
		VKGRT=aValue;
	}
	public void setKIDNO(java.lang.String aValue) {
		KIDNO=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setKUNNR(java.lang.String aValue) {
		KUNNR=aValue;
	}
	public void setKUNNT(java.lang.String aValue) {
		KUNNT=aValue;
	}
	public void setWDATE(java.lang.String aValue) {
		WDATE=aValue;
	}
	public void setZFBDT(java.lang.String aValue) {
		ZFBDT=aValue;
	}
	public void setAGING(java.lang.String aValue) {
		AGING=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setWRBTR(java.math.BigDecimal aValue) {
		WRBTR=aValue;
	}
	public void setWAERS2(java.lang.String aValue) {
		WAERS2=aValue;
	}
	public void setWRBTR2(java.math.BigDecimal aValue) {
		WRBTR2=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0032.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0032"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HKONT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HKONT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HKONTT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HKONTT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LANDX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LANDX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNZT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNZT"));
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
		elemField.setFieldName("VKBUT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUT"));
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
		elemField.setFieldName("VKGRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KIDNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KIDNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POST1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POST1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPART"));
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
		elemField.setFieldName("KUNNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZFBDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZFBDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AGING");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AGING"));
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
		elemField.setFieldName("WRBTR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WRBTR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WRBTR2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WRBTR2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("HKONT",(short)1,10);
		ds.addColumn("HKONTT",(short)1,20);
		ds.addColumn("LANDX",(short)1,15);
		ds.addColumn("KUNZT",(short)1,35);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKBUT",(short)1,20);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("VKGRT",(short)1,20);
		ds.addColumn("KIDNO",(short)1,30);
		ds.addColumn("POST1",(short)1,35);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("KUNNT",(short)1,35);
		ds.addColumn("WDATE",(short)1,8);
		ds.addColumn("ZFBDT",(short)1,8);
		ds.addColumn("AGING",(short)1,20);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("WRBTR",(short)4,13);
		ds.addColumn("WAERS2",(short)1,5);
		ds.addColumn("WRBTR2",(short)4,13);
		return ds;
	}

}