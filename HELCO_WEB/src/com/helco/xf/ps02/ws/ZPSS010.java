package com.helco.xf.ps02.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPSS010   
		implements java.io.Serializable {

	public ZPSS010() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String ZZHOGIT;
	public java.lang.String ZSITE_NM;
	public java.lang.String ZZPMNUM;
	public java.lang.String NAMEP;
	public java.lang.String ZQNTY;
	public java.lang.String VDATU;
	public java.lang.String ADDR1;
	public java.lang.String ZZSHIP1;
	public java.lang.String ZZFKEYD;
	public java.lang.String ZZCOMP2;
	public java.lang.String ZZSTAN1;
	public java.lang.String ZZLIFNR;
	public java.lang.String NAME1;
	public java.lang.String TXT04;
	public java.lang.String TXT30;	
	public java.lang.String ZTEAM;
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getZZHOGIT(){
		return ZZHOGIT;
	}
	public java.lang.String getZSITE_NM(){
		return ZSITE_NM;
	}
	public java.lang.String getZZPMNUM(){
		return ZZPMNUM;
	}
	public java.lang.String getNAMEP(){
		return NAMEP;
	}
	public java.lang.String getZQNTY(){
		return ZQNTY;
	}
	public java.lang.String getVDATU(){
		return VDATU;
	}
	public java.lang.String getADDR1(){
		return ADDR1;
	}
	public java.lang.String getZZSHIP1(){
		return ZZSHIP1;
	}
	public java.lang.String getZZFKEYD(){
		return ZZFKEYD;
	}
	public java.lang.String getZZCOMP2(){
		return ZZCOMP2;
	}
	public java.lang.String getZZSTAN1(){
		return ZZSTAN1;
	}
	public java.lang.String getZZLIFNR(){
		return ZZLIFNR;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.lang.String getTXT04(){
		return TXT04;
	}
	public java.lang.String getTXT30(){
		return TXT30;
	}
	public java.lang.String getZTEAM(){
		return ZTEAM;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setZZHOGIT(java.lang.String aValue) {
		ZZHOGIT=aValue;
	}
	public void setZSITE_NM(java.lang.String aValue) {
		ZSITE_NM=aValue;
	}
	public void setZZPMNUM(java.lang.String aValue) {
		ZZPMNUM=aValue;
	}
	public void setNAMEP(java.lang.String aValue) {
		NAMEP=aValue;
	}
	public void setZQNTY(java.lang.String aValue) {
		ZQNTY=aValue;
	}
	public void setVDATU(java.lang.String aValue) {
		VDATU=aValue;
	}
	public void setADDR1(java.lang.String aValue) {
		ADDR1=aValue;
	}
	public void setZZSHIP1(java.lang.String aValue) {
		ZZSHIP1=aValue;
	}
	public void setZZFKEYD(java.lang.String aValue) {
		ZZFKEYD=aValue;
	}
	public void setZZCOMP2(java.lang.String aValue) {
		ZZCOMP2=aValue;
	}
	public void setZZSTAN1(java.lang.String aValue) {
		ZZSTAN1=aValue;
	}
	public void setZZLIFNR(java.lang.String aValue) {
		ZZLIFNR=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setTXT04(java.lang.String aValue) {
		TXT04=aValue;
	}
	public void setTXT30(java.lang.String aValue) {
		TXT30=aValue;
	}
	public void setZTEAM(java.lang.String aValue) {
		ZTEAM=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS010.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS010"));
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
		elemField.setFieldName("ZZHOGIT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZHOGIT"));
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
		elemField.setFieldName("ZZPMNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPMNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAMEP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAMEP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQNTY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VDATU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VDATU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ADDR1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ADDR1"));
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
		elemField.setFieldName("ZZFKEYD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZFKEYD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZCOMP2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZCOMP2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZSTAN1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZSTAN1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZLIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZLIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TXT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TXT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TXT30");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TXT30"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZTEAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTEAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ZZHOGIT",(short)1,10);
		ds.addColumn("ZSITE_NM",(short)1,35);
		ds.addColumn("ZZPMNUM",(short)1,6);
		ds.addColumn("NAMEP",(short)1,20);
		ds.addColumn("ZQNTY",(short)1,3);
		ds.addColumn("VDATU",(short)1,8);
		ds.addColumn("ADDR1",(short)1,35);
		ds.addColumn("ZZSHIP1",(short)1,8);
		ds.addColumn("ZZFKEYD",(short)1,1);
		ds.addColumn("ZZCOMP2",(short)1,8);
		ds.addColumn("ZZSTAN1",(short)1,1);
		ds.addColumn("ZZLIFNR",(short)1,10);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("TXT04",(short)1,4);
		ds.addColumn("TXT30",(short)1,30);
		ds.addColumn("ZTEAM",(short)1,1);
		return ds;
	}

}