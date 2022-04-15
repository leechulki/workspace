package com.helco.xf.qm03.ws;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
public class ZQMS021   
		implements java.io.Serializable {
 
	public ZQMS021() {
	}
	public java.lang.String STATUS;
	public java.lang.String STATUSTX;
	public java.lang.String EBELN;
	public java.lang.String EBELP;
	public java.lang.String PSPID;
	public java.lang.String POST1;
	public java.lang.String HOGI;
	public java.lang.String SPEC;
	public java.lang.String ATYPE;
	public java.lang.String MATNR;
	public java.lang.String MAKTX;
	public int PACOUNT;
	public java.lang.String PARQDT;
	public java.lang.String PACFDT;
	public java.lang.String PAPRDT;
	public java.lang.String QMCHECK;
	public java.lang.String QMNUM;
	public java.lang.String PRUEFLOS;
	public java.lang.String PAPRID;
	public java.lang.String PAPRIDTX;
	public java.lang.String DSKURZTEXT;
	public java.lang.String VDATUM;
	public java.lang.String WERKS;
	public java.lang.String LIFNR;
	public java.lang.String NAME1;
	public java.math.BigDecimal BRTWR;
	public java.lang.String WAERS;
	public java.lang.String SEQ;
	
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public java.lang.String getEBELN(){
		return EBELN;
	}
	public java.lang.String getEBELP(){
		return EBELP;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getMAKTX(){
		return MAKTX;
	}
	public int getPACOUNT(){
		return PACOUNT;
	}
	public java.lang.String getPARQDT(){
		return PARQDT;
	}
	public java.lang.String getPACFDT(){
		return PACFDT;
	}
	public java.lang.String getPAPRDT(){
		return PAPRDT;
	}
	public java.lang.String getQMCHECK(){
		return QMCHECK;
	}
	public java.lang.String getQMNUM(){
		return QMNUM;
	}
	public java.lang.String getPRUEFLOS(){
		return PRUEFLOS;
	}
	public java.lang.String getPAPRID(){
		return PAPRID;
	}
	public java.lang.String getPAPRIDTX(){
		return PAPRIDTX;
	}
	public java.lang.String getDSKURZTEXT(){
		return DSKURZTEXT;
	}
	public java.lang.String getVDATUM(){
		return VDATUM;
	}
	public java.lang.String getWERKS(){
		return WERKS;
	}
	public java.lang.String getLIFNR(){
		return LIFNR;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.math.BigDecimal getBRTWR(){
		return BRTWR;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	public void setEBELN(java.lang.String aValue) {
		EBELN=aValue;
	}
	public void setEBELP(java.lang.String aValue) {
		EBELP=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setMAKTX(java.lang.String aValue) {
		MAKTX=aValue;
	}
	public void setPACOUNT(int aValue) {
		PACOUNT=aValue;
	}
	public void setPARQDT(java.lang.String aValue) {
		PARQDT=aValue;
	}
	public void setPACFDT(java.lang.String aValue) {
		PACFDT=aValue;
	}
	public void setPAPRDT(java.lang.String aValue) {
		PAPRDT=aValue;
	}
	public void setQMCHECK(java.lang.String aValue) {
		QMCHECK=aValue;
	}
	public void setQMNUM(java.lang.String aValue) {
		QMNUM=aValue;
	}
	public void setPRUEFLOS(java.lang.String aValue) {
		PRUEFLOS=aValue;
	}
	public void setPAPRID(java.lang.String aValue) {
		PAPRID=aValue;
	}
	public void setPAPRIDTX(java.lang.String aValue) {
		PAPRIDTX=aValue;
	}
	public void setDSKURZTEXT(java.lang.String aValue) {
		DSKURZTEXT=aValue;
	}
	public void setVDATUM(java.lang.String aValue) {
		VDATUM=aValue;
	}
	public void setWERKS(java.lang.String aValue) {
		WERKS=aValue;
	}
	public void setLIFNR(java.lang.String aValue) {
		LIFNR=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setBRTWR(java.math.BigDecimal aValue) {
		BRTWR=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZQMS021.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMS021"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUSTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUSTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EBELP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EBELP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POST1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POST1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HOGI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAKTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAKTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PACOUNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PACOUNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PARQDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PARQDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PACFDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PACFDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PAPRDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PAPRDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QMCHECK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QMCHECK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QMNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QMNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRUEFLOS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRUEFLOS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PAPRID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PAPRID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PAPRIDTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PAPRIDTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DSKURZTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DSKURZTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VDATUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VDATUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WERKS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WERKS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BRTWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BRTWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("STATUS",(short)1,1);
		ds.addColumn("STATUSTX",(short)1,100);
		ds.addColumn("EBELN",(short)1,10);
		ds.addColumn("EBELP",(short)1,5);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("SPEC",(short)1,30);
		ds.addColumn("ATYPE",(short)1,4);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("MAKTX",(short)1,40);
		ds.addColumn("PACOUNT",(short)2,10);
		ds.addColumn("PARQDT",(short)1,8);
		ds.addColumn("PACFDT",(short)1,8);
		ds.addColumn("PAPRDT",(short)1,8);
		ds.addColumn("QMCHECK",(short)1,1);
		ds.addColumn("QMNUM",(short)1,12);
		ds.addColumn("PRUEFLOS",(short)1,12);
		ds.addColumn("PAPRID",(short)1,10);
		ds.addColumn("PAPRIDTX",(short)1,35);
		ds.addColumn("DSKURZTEXT",(short)1,40);
		ds.addColumn("VDATUM",(short)1,8);
		ds.addColumn("WERKS",(short)1,4);
		ds.addColumn("LIFNR",(short)1,10);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("BRTWR",(short)4,13);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("SEQ",(short)1,2);
		return ds;
	}
	public java.lang.String getSTATUSTX() {
		return STATUSTX;
	}
	public void setSTATUSTX(java.lang.String statustx) {
		STATUSTX = statustx;
	}
	public java.lang.String getSEQ() {
		return SEQ;
	}
	public void setSEQ(java.lang.String seq) {
		SEQ = seq;
	}

}