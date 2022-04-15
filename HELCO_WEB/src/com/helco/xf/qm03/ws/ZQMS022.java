package com.helco.xf.qm03.ws;
import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
public class ZQMS022   
		implements java.io.Serializable {

	public ZQMS022() {
	}
	public java.lang.String STATUS;
	public java.lang.String TMODE;
	public java.lang.String WERKS;
	public java.lang.String EBELN;
	public java.lang.String EBELP;
	public java.lang.String HOGI;
	public java.lang.String SEQ;
	public java.lang.String PARQDT;
	public java.lang.String PSPID;
	public java.lang.String LIFNR;
	public java.lang.String MATNR;
	public java.lang.String ATYPE;
	public java.lang.String SPEC;
	public int PACOUNT;
	public java.lang.String PS_PSP_PNR;
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public java.lang.String getTMODE(){
		return TMODE;
	}
	public java.lang.String getWERKS(){
		return WERKS;
	}
	public java.lang.String getEBELN(){
		return EBELN;
	}
	public java.lang.String getEBELP(){
		return EBELP;
	}
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getSEQ(){
		return SEQ;
	}
	public java.lang.String getPARQDT(){
		return PARQDT;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	} 
	public java.lang.String getLIFNR(){
		return LIFNR;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public int getPACOUNT(){
		return PACOUNT;
	}
	public java.lang.String getPS_PSP_PNR(){
		return PS_PSP_PNR;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	public void setTMODE(java.lang.String aValue) {
		TMODE=aValue;
	}
	public void setWERKS(java.lang.String aValue) {
		WERKS=aValue;
	}
	public void setEBELN(java.lang.String aValue) {
		EBELN=aValue;
	}
	public void setEBELP(java.lang.String aValue) {
		EBELP=aValue;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setSEQ(java.lang.String aValue) {
		SEQ=aValue;
	}
	public void setPARQDT(java.lang.String aValue) {
		PARQDT=CallSAP.getFormatDate(aValue);
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setLIFNR(java.lang.String aValue) {
		LIFNR=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setPACOUNT(int aValue) {
		PACOUNT=aValue;
	}
	public void setPS_PSP_PNR(java.lang.String aValue) {
		PS_PSP_PNR=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZQMS022.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMS022"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TMODE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TMODE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WERKS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WERKS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EBELP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EBELP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HOGI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PARQDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PARQDT"));
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
		elemField.setFieldName("LIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LIFNR"));
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
		elemField.setFieldName("ATYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATYPE"));
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
		elemField.setFieldName("PACOUNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PACOUNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PS_PSP_PNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PS_PSP_PNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("STATUS",(short)1,1);
		ds.addColumn("TMODE",(short)1,1);
		ds.addColumn("WERKS",(short)1,4);
		ds.addColumn("EBELN",(short)1,10);
		ds.addColumn("EBELP",(short)1,5);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("SEQ",(short)1,2);
		ds.addColumn("PARQDT",(short)1,8);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("LIFNR",(short)1,10);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("ATYPE",(short)1,4);
		ds.addColumn("SPEC",(short)1,30);
		ds.addColumn("PACOUNT",(short)2,10);
		ds.addColumn("PS_PSP_PNR",(short)1,8);
		return ds;
	}

}