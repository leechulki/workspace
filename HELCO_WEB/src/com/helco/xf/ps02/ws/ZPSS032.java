package com.helco.xf.ps02.ws;
import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;
public class ZPSS032   
		implements java.io.Serializable {

	public ZPSS032() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String ENABLE;
	public java.lang.String GTCNT;
	public java.lang.String ZZINTER;

	public java.lang.String ZZSHIP1;
	public java.lang.String ZZSHIP2;
	public java.lang.String ZZSHIP3;
	public java.lang.String ZZSHIP4;
	public java.lang.String ZZSHIP5;
	public java.lang.String ZZSHIP6;
	public java.lang.String AUFNR1;
	public java.lang.String AUFNR2;
	public java.lang.String AUFNR3;
	public java.lang.String AUFNR4;
	public java.lang.String AUFNR5;
	public java.lang.String AUFNR6;
	
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getENABLE(){
		return ENABLE;
	}
	public java.lang.String getGTCNT(){
		return GTCNT;
	}
	public java.lang.String getZZINTER(){
		return ZZINTER;
	}
	public java.lang.String getZZSHIP1(){
		return ZZSHIP1;
	}
	public java.lang.String getZZSHIP2(){
		return ZZSHIP2;
	}
	public java.lang.String getZZSHIP3(){
		return ZZSHIP3;
	}
	public java.lang.String getZZSHIP4(){
		return ZZSHIP4;
	}
	public java.lang.String getZZSHIP5(){
		return ZZSHIP5;
	}
	public java.lang.String getZZSHIP6(){
		return ZZSHIP6;
	}
	public java.lang.String getAUFNR1(){
		return AUFNR1;
	}
	public java.lang.String getAUFNR2(){
		return AUFNR2;
	}
	public java.lang.String getAUFNR3(){
		return AUFNR3;
	}
	public java.lang.String getAUFNR4(){
		return AUFNR4;
	}
	public java.lang.String getAUFNR5(){
		return AUFNR5;
	}
	public java.lang.String getAUFNR6(){
		return AUFNR6;
	}
	
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setENABLE(java.lang.String aValue) {
		ENABLE=aValue;
	}
	public void setGTCNT(java.lang.String aValue) {
		GTCNT=aValue;
	}
	public void setZZINTER(java.lang.String aValue) {
		ZZINTER=aValue;
	}
	public void setZZSHIP1(java.lang.String aValue) {
		ZZSHIP1=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP2(java.lang.String aValue) {
		ZZSHIP2=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP3(java.lang.String aValue) {
		ZZSHIP3=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP4(java.lang.String aValue) {
		ZZSHIP4=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP5(java.lang.String aValue) {
		ZZSHIP5=CallSAP.getFormatDate(aValue);
	}
	public void setZZSHIP6(java.lang.String aValue) {
		ZZSHIP6=CallSAP.getFormatDate(aValue);
	}
	public void setAUFNR1(java.lang.String aValue) {
		AUFNR1=aValue;
	}
	public void setAUFNR2(java.lang.String aValue) {
		AUFNR2=aValue;
	}
	public void setAUFNR3(java.lang.String aValue) {
		AUFNR3=aValue;
	}
	public void setAUFNR4(java.lang.String aValue) {
		AUFNR4=aValue;
	}
	public void setAUFNR5(java.lang.String aValue) {
		AUFNR5=aValue;
	}
	public void setAUFNR6(java.lang.String aValue) {
		AUFNR6=aValue;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS032.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS032"));
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
		elemField.setFieldName("ENABLE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ENABLE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GTCNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GTCNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZINTER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZINTER"));
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
		elemField.setFieldName("ZZSHIP2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSHIP6");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSHIP6"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR5");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR5"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR6");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR6"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ENABLE",(short)1,1);
		ds.addColumn("GTCNT",(short)1,10);
		ds.addColumn("ZZINTER",(short)1,1);
		ds.addColumn("ZZSHIP1",(short)1,8);
		ds.addColumn("ZZSHIP2",(short)1,8);
		ds.addColumn("ZZSHIP3",(short)1,8);
		ds.addColumn("ZZSHIP4",(short)1,8);
		ds.addColumn("ZZSHIP5",(short)1,8);
		ds.addColumn("ZZSHIP6",(short)1,8);
		ds.addColumn("AUFNR1",(short)1,12);
		ds.addColumn("AUFNR2",(short)1,12);
		ds.addColumn("AUFNR3",(short)1,12);
		ds.addColumn("AUFNR4",(short)1,12);
		ds.addColumn("AUFNR5",(short)1,12);
		ds.addColumn("AUFNR6",(short)1,12);
		
		return ds;
	}

}