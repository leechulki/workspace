package com.helco.xf.bs03.ws;
import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;
public class ZPPS081A   
		implements java.io.Serializable {

	public ZPPS081A() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String PBTXT;
	public java.lang.String CREID;
	public java.lang.String CRDAT;
	public java.lang.String PDAT1;
	public java.lang.String PDAT2;
	public java.lang.String PDAT3;
	public java.lang.String CMDAT;
	public java.lang.String SDTXT;
	public java.lang.String ERNAM;
	public java.lang.String ERDAT;
	public java.lang.String ERZET;
	public java.lang.String GBN1;
	public java.lang.String GBN2;
	public java.lang.String GBN3;
	public java.lang.String STATUS;
	public java.lang.String CREIDNM;
	public java.lang.String POST1;
	public java.lang.String PBICON;
	public java.lang.String TDID;
	public java.lang.String LANGU;
	public java.lang.String TDNAME;
	public java.lang.String TDOBJECT;
	public java.lang.String UPDAT;
	public java.lang.String UPZET;
	public java.lang.String UPDID;
	public java.lang.String MIRUM;
	public java.lang.String EIRUM;
	public java.lang.String PSPMNAM;
	public java.lang.String TECHSDNAM;
	public java.lang.String SDNAM;
	public java.lang.String TECHSDTDID;	
	public java.lang.String TECHSDTXT;	
	public java.lang.String ZZDAT01;	
	public java.lang.String SALES_P;	

	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getPBTXT(){
		return PBTXT;
	}
	public java.lang.String getCREID(){
		return CREID;
	}
	public java.lang.String getCRDAT(){
		return CRDAT;
	}
	public java.lang.String getPDAT1(){
		return PDAT1;
	}
	public java.lang.String getPDAT2(){
		return PDAT2;
	}
	public java.lang.String getPDAT3(){
		return PDAT3;
	}
	public java.lang.String getCMDAT(){
		return CMDAT;
	}
	public java.lang.String getSDTXT(){
		return SDTXT;
	}
	public java.lang.String getERNAM(){
		return ERNAM;
	}
	public java.lang.String getERDAT(){
		return ERDAT;
	}
	public java.lang.String getERZET(){
		return ERZET;
	}
	public java.lang.String getGBN1(){
		return GBN1;
	}
	public java.lang.String getGBN2(){
		return GBN2;
	}
	public java.lang.String getGBN3(){
		return GBN3;
	}
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public java.lang.String getCREIDNM(){
		return CREIDNM;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getPBICON(){
		return PBICON;
	}
	public java.lang.String getTDID(){
		return TDID;
	}
	public java.lang.String getLANGU(){
		return LANGU;
	}
	public java.lang.String getTDNAME(){
		return TDNAME;
	}
	public java.lang.String getTDOBJECT(){
		return TDOBJECT;
	}
	public java.lang.String getUPDAT() {
		return UPDAT;
	}
	public java.lang.String getUPZET() {
		return UPZET;
	}
	public java.lang.String getUPDID() {
		return UPDID;
	}
	public java.lang.String getMIRUM() {
		return MIRUM;
	}
	public java.lang.String getEIRUM() {
		return EIRUM;
	}
	public java.lang.String getPSPMNAM() {
		return PSPMNAM;
	}
	public java.lang.String getTECHSDNAM() {
		return TECHSDNAM;
	}
	public java.lang.String getSDNAM() {
		return SDNAM;
	}
	public java.lang.String getTECHSDTDID() {
		return TECHSDTDID;
	}
	public java.lang.String getTECHSDTXT() {
		return TECHSDTXT;
	}
	public java.lang.String getZZDAT01() {
		return ZZDAT01;
	}
	public java.lang.String getSALES_P() {
		return SALES_P;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setPBTXT(java.lang.String aValue) {
		PBTXT=aValue;
	}
	public void setCREID(java.lang.String aValue) {
		CREID=aValue;
	}
	public void setCRDAT(java.lang.String aValue) {
		CRDAT=aValue;
	}
	public void setPDAT1(java.lang.String aValue) {
		PDAT1=CallSAP.getFormatDate(aValue);
	}
	public void setPDAT2(java.lang.String aValue) {
		PDAT2=CallSAP.getFormatDate(aValue);
	}
	public void setPDAT3(java.lang.String aValue) {
		PDAT3=CallSAP.getFormatDate(aValue);
	}
	public void setCMDAT(java.lang.String aValue) {
		CMDAT=CallSAP.getFormatDate(aValue);
	}
	public void setSDTXT(java.lang.String aValue) {
		SDTXT=aValue;
	}
	public void setERNAM(java.lang.String aValue) {
		ERNAM=aValue;
	}
	public void setERDAT(java.lang.String aValue) {
		ERDAT=aValue;
	}
	public void setERZET(java.lang.String aValue) {
		ERZET=aValue;
	}
	public void setGBN1(java.lang.String aValue) {
		GBN1=aValue;
	}
	public void setGBN2(java.lang.String aValue) {
		GBN2=aValue;
	}
	public void setGBN3(java.lang.String aValue) {
		GBN3=aValue;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	public void setCREIDNM(java.lang.String aValue) {
		CREIDNM=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setPBICON(java.lang.String aValue) {
		PBICON=aValue;
	}
	public void setTDID(java.lang.String aValue) {
		TDID=aValue;
	}
	public void setLANGU(java.lang.String aValue) {
		LANGU=aValue;
	}
	public void setTDNAME(java.lang.String aValue) {
		TDNAME=aValue;
	}
	public void setTDOBJECT(java.lang.String aValue) {
		TDOBJECT=aValue;
	}
	public void setUPDAT(java.lang.String updat) {
		UPDAT = updat;
	}
	public void setUPZET(java.lang.String upzet) {
		UPZET = upzet;
	}
	public void setUPDID(java.lang.String updid) {
		UPDID = updid;
	}
	public void setMIRUM(java.lang.String mirum) {
		MIRUM = mirum;
	}
	public void setEIRUM(java.lang.String eirum) {
		EIRUM = eirum;
	}
	public void setPSPMNAM(java.lang.String pspmnam) {
		PSPMNAM = pspmnam;
	}
	public void setTECHSDNAM(java.lang.String techsdnam) {
		TECHSDNAM = techsdnam;
	}
	public void setSDNAM(java.lang.String sdnam) {
		SDNAM = sdnam;
	}
	public void setTECHSDTDID(java.lang.String techsdtdid) {
		TECHSDTDID = techsdtdid;
	}
	public void setTECHSDTXT(java.lang.String techsdtxt) {
		TECHSDTXT = techsdtxt;
	}	
	public void setZZDAT01(java.lang.String zzdat01) {
		ZZDAT01 = zzdat01;
	}	
	public void setSALES_P(java.lang.String sales_p) {
		SALES_P = sales_p;
	}	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS081A.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS081A"));
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
		elemField.setFieldName("PBTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PBTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CREID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CREID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PDAT1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PDAT1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PDAT2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PDAT2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PDAT3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PDAT3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CMDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CMDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SDTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SDTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERZET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERZET"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GBN1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GBN1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GBN2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GBN2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GBN3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GBN3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CREIDNM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CREIDNM"));
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
		elemField.setFieldName("PBICON");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PBICON"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TDID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TDID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LANGU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LANGU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TDNAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TDNAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TDOBJECT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TDOBJECT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UPDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UPDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UPZET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UPZET"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UPDID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UPDID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MIRUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MIRUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EIRUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EIRUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPMNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPMNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TECHSDNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TECHSDNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SDNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SDNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TECHSDTDID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TECHSDTDID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TECHSDTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TECHSDTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZDAT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZDAT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SALES_P");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SALES_P"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("PBTXT",(short)1,40);
		ds.addColumn("CREID",(short)1,10);
		ds.addColumn("CRDAT",(short)1,8);
		ds.addColumn("PDAT1",(short)1,8);
		ds.addColumn("PDAT2",(short)1,8);
		ds.addColumn("PDAT3",(short)1,8);
		ds.addColumn("CMDAT",(short)1,8);
		ds.addColumn("SDTXT",(short)1,40);
		ds.addColumn("ERNAM",(short)1,12);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("ERZET",(short)1,10);
		ds.addColumn("GBN1",(short)1,1);
		ds.addColumn("GBN2",(short)1,1);
		ds.addColumn("GBN3",(short)1,1);
		ds.addColumn("STATUS",(short)1,20);
		ds.addColumn("CREIDNM",(short)1,35);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("PBICON",(short)1,4);
		ds.addColumn("TDID",(short)1,4);
		ds.addColumn("LANGU",(short)1,10);
		ds.addColumn("TDNAME",(short)1,70);
		ds.addColumn("TDOBJECT",(short)1,10);
		ds.addColumn("UPDAT",(short)1,8);
		ds.addColumn("UPZET",(short)1,6);
		ds.addColumn("UPDID",(short)1,12);
		ds.addColumn("MIRUM",(short)1,35);
		ds.addColumn("EIRUM",(short)1,35);
		ds.addColumn("PSPMNAM",(short)1,35);
		ds.addColumn("TECHSDNAM",(short)1,35);
		ds.addColumn("SDNAM",(short)1,35);
		ds.addColumn("TECHSDTDID",(short)1,4);
		ds.addColumn("TECHSDTXT",(short)1,40);
		ds.addColumn("ZZDAT01",(short)1,8);
		ds.addColumn("SALES_P",(short)1,1);
		return ds;
	}

	public static void setTypeDesc(org.apache.axis.description.TypeDesc typeDesc) {
		ZPPS081A.typeDesc = typeDesc;
	}


}