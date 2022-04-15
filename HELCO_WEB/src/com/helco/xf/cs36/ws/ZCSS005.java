package com.helco.xf.cs36.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS005   
		implements java.io.Serializable {

	public ZCSS005() {
	}
	public java.lang.String YYMM;
	public java.lang.String VKBURT;
	public java.lang.String VKGRPT;
	public java.lang.String ZKUNNRT;
	public java.lang.String KUNRZ1T;
	public java.lang.String SPART;
	public java.lang.String AUART;
	public java.lang.String KIDNO;
	public java.lang.String BSTKD;
	public java.lang.String KUNRPYT;
	public java.math.BigDecimal HWBAS;
	public java.math.BigDecimal KRWTR;
	public java.lang.String BUDAT;
	public java.lang.String FKDAT;
	public java.lang.String CPROGN;
	public java.lang.String WAERK;
	public java.lang.String ABGT;
	public java.lang.String getYYMM(){
		return YYMM;
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
	public java.lang.String getKIDNO(){
		return KIDNO;
	}
	public java.lang.String getBSTKD(){
		return BSTKD;
	}
	public java.lang.String getKUNRPYT(){
		return KUNRPYT;
	}
	public java.math.BigDecimal getHWBAS(){
		return HWBAS;
	}
	public java.math.BigDecimal getKRWTR(){
		return KRWTR;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.lang.String getCPROGN(){
		return CPROGN;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getABGT(){
		return ABGT;
	}
	public void setYYMM(java.lang.String aValue) {
		YYMM=aValue;
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
	public void setKIDNO(java.lang.String aValue) {
		KIDNO=aValue;
	}
	public void setBSTKD(java.lang.String aValue) {
		BSTKD=aValue;
	}
	public void setKUNRPYT(java.lang.String aValue) {
		KUNRPYT=aValue;
	}
	public void setHWBAS(java.math.BigDecimal aValue) {
		HWBAS=aValue;
	}
	public void setKRWTR(java.math.BigDecimal aValue) {
		KRWTR=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setCPROGN(java.lang.String aValue) {
		CPROGN=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setABGT(java.lang.String aValue) {
		ABGT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS005.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS005"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("YYMM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "YYMM"));
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
		elemField.setFieldName("KIDNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KIDNO"));
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
		elemField.setFieldName("HWBAS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HWBAS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KRWTR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KRWTR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CPROGN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CPROGN"));
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
		ds.addColumn("YYMM",(short)1,6);
		ds.addColumn("VKBURT",(short)1,20);
		ds.addColumn("VKGRPT",(short)1,20);
		ds.addColumn("ZKUNNRT",(short)1,35);
		ds.addColumn("KUNRZ1T",(short)1,35);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("KIDNO",(short)1,30);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("KUNRPYT",(short)1,35);
		ds.addColumn("HWBAS",(short)4,13);
		ds.addColumn("KRWTR",(short)4,13);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("CPROGN",(short)1,10);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("ABGT",(short)1,10);
		return ds;
	}

}