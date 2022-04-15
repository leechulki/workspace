package com.helco.xf.cs36.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS004   
		implements java.io.Serializable {

	public ZCSS004() {
	}
	public java.lang.String FKYYM;
	public java.lang.String VKBURT;
	public java.lang.String VKGRPT;
	public java.lang.String ZKUNNRT;
	public java.lang.String KUNRZ1T;
	public java.lang.String SPART;
	public java.lang.String AUART;
	public java.lang.String ZZPJT_ID;
	public java.lang.String BSTKD;
	public java.lang.String KUNRPYT;
	public java.math.BigDecimal RKNETWR;
	public java.math.BigDecimal EXTOKRW;
	public java.lang.String RKFKDAT;
	public java.lang.String ERDAT;
	public java.lang.String BSTDK;
	public java.lang.String WAERK;
	public java.lang.String ABGT;
	public java.lang.String getFKYYM(){
		return FKYYM;
	}
	public java.lang.String getVKBURT(){
		return VKBURT;
	}
	public java.lang.String getVKGRPT(){
		return VKGRPT;
	}
	public java.lang.String getZKUNNRT(){
		return ZKUNNRT;
	}
	public java.lang.String getKUNRZ1T(){
		return KUNRZ1T;
	}
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getAUART(){
		return AUART;
	}
	public java.lang.String getZZPJT_ID(){
		return ZZPJT_ID;
	}
	public java.lang.String getBSTKD(){
		return BSTKD;
	}
	public java.lang.String getKUNRPYT(){
		return KUNRPYT;
	}
	public java.math.BigDecimal getRKNETWR(){
		return RKNETWR;
	}
	public java.math.BigDecimal getEXTOKRW(){
		return EXTOKRW;
	}
	public java.lang.String getRKFKDAT(){
		return RKFKDAT;
	}
	public java.lang.String getERDAT(){
		return ERDAT;
	}
	public java.lang.String getBSTDK(){
		return BSTDK;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getABGT(){
		return ABGT;
	}
	public void setFKYYM(java.lang.String aValue) {
		FKYYM=aValue;
	}
	public void setVKBURT(java.lang.String aValue) {
		VKBURT=aValue;
	}
	public void setVKGRPT(java.lang.String aValue) {
		VKGRPT=aValue;
	}
	public void setZKUNNRT(java.lang.String aValue) {
		ZKUNNRT=aValue;
	}
	public void setKUNRZ1T(java.lang.String aValue) {
		KUNRZ1T=aValue;
	}
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setAUART(java.lang.String aValue) {
		AUART=aValue;
	}
	public void setZZPJT_ID(java.lang.String aValue) {
		ZZPJT_ID=aValue;
	}
	public void setBSTKD(java.lang.String aValue) {
		BSTKD=aValue;
	}
	public void setKUNRPYT(java.lang.String aValue) {
		KUNRPYT=aValue;
	}
	public void setRKNETWR(java.math.BigDecimal aValue) {
		RKNETWR=aValue;
	}
	public void setEXTOKRW(java.math.BigDecimal aValue) {
		EXTOKRW=aValue;
	}
	public void setRKFKDAT(java.lang.String aValue) {
		RKFKDAT=aValue;
	}
	public void setERDAT(java.lang.String aValue) {
		ERDAT=aValue;
	}
	public void setBSTDK(java.lang.String aValue) {
		BSTDK=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setABGT(java.lang.String aValue) {
		ABGT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS004.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS004"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKYYM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKYYM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBURT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBURT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRPT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRPT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKUNNRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNRZ1T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNRZ1T"));
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
		elemField.setFieldName("AUART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZPJT_ID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPJT_ID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTKD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTKD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNRPYT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNRPYT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RKNETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RKNETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EXTOKRW");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EXTOKRW"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RKFKDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RKFKDAT"));
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
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ABGT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ABGT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("FKYYM",(short)1,6);
		ds.addColumn("VKBURT",(short)1,20);
		ds.addColumn("VKGRPT",(short)1,20);
		ds.addColumn("ZKUNNRT",(short)1,35);
		ds.addColumn("KUNRZ1T",(short)1,35);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("ZZPJT_ID",(short)1,24);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("KUNRPYT",(short)1,35);
		ds.addColumn("RKNETWR",(short)4,13);
		ds.addColumn("EXTOKRW",(short)4,13);
		ds.addColumn("RKFKDAT",(short)1,8);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("ABGT",(short)1,10);
		return ds;
	}

}