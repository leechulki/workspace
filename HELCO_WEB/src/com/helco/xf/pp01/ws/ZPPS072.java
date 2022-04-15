package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS072   
		implements java.io.Serializable {

	public ZPPS072() {
	}
	public java.lang.String POSID;
	public java.lang.String MAT_PSPNR;
	public java.lang.String POST1;
	public java.lang.String SPEC2;
	public java.lang.String ATYPE;
	public java.lang.String MSPEC;
	public java.lang.String MZSIZE;
	public java.lang.String MEINS;
	public java.lang.String PMENGE;
	public java.lang.String TMENGE;
	public java.lang.String EMENGE;
	public java.lang.String CHECK;
	public java.lang.String PPEDAT;
	public java.lang.String PPMDAT;
	public java.lang.String ILDAT;
	public java.lang.String PPIDAT;
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getMAT_PSPNR(){
		return MAT_PSPNR;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getSPEC2(){
		return SPEC2;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getMSPEC(){
		return MSPEC;
	}
	public java.lang.String getMZSIZE(){
		return MZSIZE;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.lang.String getPMENGE(){
		return PMENGE;
	}
	public java.lang.String getTMENGE(){
		return TMENGE;
	}
	public java.lang.String getEMENGE(){
		return EMENGE;
	}
	public java.lang.String getCHECK(){
		return CHECK;
	}
	public java.lang.String getPPEDAT(){
		return PPEDAT;
	}
	public java.lang.String getPPMDAT(){
		return PPMDAT;
	}
	public java.lang.String getILDAT(){
		return ILDAT;
	}
	public java.lang.String getPPIDAT(){
		return PPIDAT;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setMAT_PSPNR(java.lang.String aValue) {
		MAT_PSPNR=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setSPEC2(java.lang.String aValue) {
		SPEC2=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setMSPEC(java.lang.String aValue) {
		MSPEC=aValue;
	}
	public void setMZSIZE(java.lang.String aValue) {
		MZSIZE=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setPMENGE(java.lang.String aValue) {
		PMENGE=aValue;
	}
	public void setTMENGE(java.lang.String aValue) {
		TMENGE=aValue;
	}
	public void setEMENGE(java.lang.String aValue) {
		EMENGE=aValue;
	}
	public void setCHECK(java.lang.String aValue) {
		CHECK=aValue;
	}
	public void setPPEDAT(java.lang.String aValue) {
		PPEDAT=aValue;
	}
	public void setPPMDAT(java.lang.String aValue) {
		PPMDAT=aValue;
	}
	public void setILDAT(java.lang.String aValue) {
		ILDAT=aValue;
	}
	public void setPPIDAT(java.lang.String aValue) {
		PPIDAT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS072.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS072"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAT_PSPNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAT_PSPNR"));
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
		elemField.setFieldName("SPEC2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC2"));
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
		elemField.setFieldName("MSPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MZSIZE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MZSIZE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEINS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEINS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHECK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHECK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PPEDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PPEDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PPMDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PPMDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ILDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ILDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PPIDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PPIDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("MAT_PSPNR",(short)1,8);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("SPEC2",(short)1,30);
		ds.addColumn("ATYPE",(short)1,5);
		ds.addColumn("MSPEC",(short)1,60);
		ds.addColumn("MZSIZE",(short)1,60);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("PMENGE",(short)1,15);
		ds.addColumn("TMENGE",(short)1,15);
		ds.addColumn("EMENGE",(short)1,15);
		ds.addColumn("CHECK",(short)1,1);
		ds.addColumn("PPEDAT",(short)1,8);
		ds.addColumn("PPMDAT",(short)1,8);
		ds.addColumn("ILDAT",(short)1,8);
		ds.addColumn("PPIDAT",(short)1,8);
		return ds;
	}

}