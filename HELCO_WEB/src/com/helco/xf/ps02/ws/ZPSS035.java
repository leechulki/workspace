package com.helco.xf.ps02.ws;
import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;
public class ZPSS035   
		implements java.io.Serializable {

	public ZPSS035() {
	}
	public java.lang.String POSID;
	public java.lang.String POSID_1;
	public java.lang.String ZZACTSS;
	public java.lang.String ZSITE_NM;
	public java.lang.String TEXT30;
	public java.lang.String CDATE;
	public java.lang.String NAMET;
	public java.math.BigDecimal TERM;
	public java.lang.String MONEY;
	public java.lang.String SPEC;
	public java.lang.String LAYOUT;
	public java.lang.String ZZSHIP1;
	public java.lang.String BEST_DT1;
	public java.lang.String BEST_DT2;
	public java.lang.String BEST_DT3;
	public java.lang.String BEST_DT4;
	public java.lang.String BEST_DT5;
	public java.lang.String ZSITE_TEL;
	public java.lang.String MAX_EXPLO;
	
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPOSID_1(){
		return POSID_1;
	}
	public java.lang.String getZZACTSS(){
		return ZZACTSS;
	}
	public java.lang.String getZSITE_NM(){
		return ZSITE_NM;
	}
	public java.lang.String getTEXT30(){
		return TEXT30;
	}
	public java.lang.String getCDATE(){
		return CDATE;
	}
	public java.lang.String getNAMET(){
		return NAMET;
	}
	public java.math.BigDecimal getTERM(){
		return TERM;
	}
	public java.lang.String getMONEY(){
		return MONEY;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.lang.String getLAYOUT(){
		return LAYOUT;
	}
	public java.lang.String getZZSHIP1(){
		return ZZSHIP1;
	}
	public java.lang.String getBEST_DT1(){
		return BEST_DT1;
	}
	public java.lang.String getBEST_DT2(){
		return BEST_DT2;
	}
	public java.lang.String getBEST_DT3(){
		return BEST_DT3;
	}
	public java.lang.String getBEST_DT4(){
		return BEST_DT4;
	}
	public java.lang.String getBEST_DT5(){
		return BEST_DT5;
	}
	public java.lang.String getZSITE_TEL(){
		return ZSITE_TEL;
	}
	public java.lang.String getMAX_EXPLO(){
		return MAX_EXPLO;
	}

	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setPOSID_1(java.lang.String aValue) {
		POSID_1=aValue;
	}
	public void setZZACTSS(java.lang.String aValue) {
		ZZACTSS=aValue;
	}
	public void setZSITE_NM(java.lang.String aValue) {
		ZSITE_NM=aValue;
	}
	public void setTEXT30(java.lang.String aValue) {
		TEXT30=aValue;
	}
	public void setCDATE(java.lang.String aValue) {
		CDATE=CallSAP.getFormatDate(aValue);
	}
	public void setNAMET(java.lang.String aValue) {
		NAMET=aValue;
	}
	public void setTERM(java.math.BigDecimal aValue) {
		TERM=aValue;
	}
	public void setMONEY(java.lang.String aValue) {
		MONEY=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setLAYOUT(java.lang.String aValue) {
		LAYOUT=aValue;
	}
	public void setZZSHIP1(java.lang.String aValue) {
		ZZSHIP1=CallSAP.getFormatDate(aValue);
	}
	public void setBEST_DT1(java.lang.String aValue) {
		BEST_DT1=CallSAP.getFormatDate(aValue);
	}
	public void setBEST_DT2(java.lang.String aValue) {
		BEST_DT2=CallSAP.getFormatDate(aValue);
	}
	public void setBEST_DT3(java.lang.String aValue) {
		BEST_DT3=CallSAP.getFormatDate(aValue);
	}
	public void setBEST_DT4(java.lang.String aValue) {
		BEST_DT4=CallSAP.getFormatDate(aValue);
	}
	public void setBEST_DT5(java.lang.String aValue) {
		BEST_DT5=CallSAP.getFormatDate(aValue);
	}
	public void setZSITE_TEL(java.lang.String aValue) {
		ZSITE_TEL=aValue;
	}
	public void setMAX_EXPLO(java.lang.String aValue) {
		MAX_EXPLO=CallSAP.getFormatDate(aValue);
	}
	

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS035.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS035"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID_1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID_1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZACTSS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZACTSS"));
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
		elemField.setFieldName("TEXT30");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT30"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAMET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAMET"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TERM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TERM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MONEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MONEY"));
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
		elemField.setFieldName("LAYOUT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAYOUT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BEST_DT1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BEST_DT1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BEST_DT2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BEST_DT2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BEST_DT3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BEST_DT3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BEST_DT4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BEST_DT4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BEST_DT5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BEST_DT5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSITE_TEL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSITE_TEL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAX_EXPLO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAX_EXPLO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POSID_1",(short)1,24);
		ds.addColumn("ZZACTSS",(short)1,3);
		ds.addColumn("ZSITE_NM",(short)1,35);
		ds.addColumn("TEXT30",(short)1,30);
		ds.addColumn("CDATE",(short)1,8);
		ds.addColumn("NAMET",(short)1,20);
		ds.addColumn("TERM",(short)1,10);
		ds.addColumn("MONEY",(short)1,3);
		ds.addColumn("SPEC",(short)1,1);
		ds.addColumn("LAYOUT",(short)1,1);
		ds.addColumn("ZZSHIP1",(short)1,8);
		ds.addColumn("BEST_DT1",(short)1,8);
		ds.addColumn("BEST_DT2",(short)1,8);
		ds.addColumn("BEST_DT3",(short)1,8);
		ds.addColumn("BEST_DT4",(short)1,8);
		ds.addColumn("BEST_DT5",(short)1,8);
		ds.addColumn("ZSITE_TEL",(short)1,20);
		ds.addColumn("MAX_EXPLO",(short)1,8);
		
		return ds;
	}

}