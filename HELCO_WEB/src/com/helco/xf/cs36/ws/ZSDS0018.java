package com.helco.xf.cs36.ws;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0018   
		implements java.io.Serializable {

	public ZSDS0018() {
	}
	public java.lang.String YEARM;
	public java.lang.String VKBURT;
	public java.lang.String VKGRPT;
	public java.lang.String ZKUNNRT;
	public java.lang.String KUNRZ1T;
	public java.lang.String SPART;
	public java.lang.String AUART;
	public java.lang.String ZZPJT_ID;
	public java.lang.String BSTKD;
	public java.lang.String KUNNRT;
	public java.lang.String ISNEW;
	public java.lang.String CHMENG;
	public java.math.BigDecimal CHNETWR;
	public java.math.BigDecimal EXTOKRW;
	public java.lang.String BSTDK;
	public java.lang.String ERDAT;
	public java.lang.String WAERK;
	public java.lang.String ABGT;
	public java.lang.String CRS_TXT;
	public java.math.BigDecimal SRAT;
	public java.lang.String MODGBN;
	public java.lang.String MODGBN_NM;
	public java.lang.String ILSIC_YN;
	public java.lang.String getYEARM(){
		return YEARM;
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
	public java.lang.String getKUNNRT(){
		return KUNNRT;
	}
	public java.lang.String getISNEW(){
		return ISNEW;
	}
	public java.lang.String getCHMENG(){
		return CHMENG;
	}
	public java.math.BigDecimal getCHNETWR(){
		return CHNETWR;
	}
	public java.math.BigDecimal getEXTOKRW(){
		return EXTOKRW;
	}
	public java.lang.String getBSTDK(){
		return BSTDK;
	}
	public java.lang.String getERDAT(){
		return ERDAT;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getABGT(){
		return ABGT;
	}
	public java.lang.String getCRS_TXT(){
		return CRS_TXT;
	}
	public java.math.BigDecimal getSRAT(){
		return SRAT;
	}
	public java.lang.String getMODGBN(){
		return MODGBN;
	}	
	public java.lang.String getMODGBN_NM(){
		return MODGBN_NM;
	}
	public java.lang.String getILSIC_YN(){
		return ILSIC_YN;
	}	
	
	public void setYEARM(java.lang.String aValue) {
		YEARM=aValue;
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
	public void setKUNNRT(java.lang.String aValue) {
		KUNNRT=aValue;
	}
	public void setISNEW(java.lang.String aValue) {
		ISNEW=aValue;
	}
	public void setCHMENG(java.lang.String aValue) {
		CHMENG=aValue;
	}
	public void setCHNETWR(java.math.BigDecimal aValue) {
		CHNETWR=aValue;
	}
	public void setEXTOKRW(java.math.BigDecimal aValue) {
		EXTOKRW=aValue;
	}
	public void setBSTDK(java.lang.String aValue) {
		BSTDK=aValue;
	}
	public void setERDAT(java.lang.String aValue) {
		ERDAT=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setABGT(java.lang.String aValue) {
		ABGT=aValue;
	}
	public void setCRS_TXT(java.lang.String aValue) {
		CRS_TXT=aValue;
	}
	public void setSRAT(java.math.BigDecimal aValue) {
		SRAT=aValue;
	}
	public void setMODGBN(java.lang.String aValue) {
		MODGBN=aValue;
	}	
	public void setMODGBN_NM(java.lang.String aValue) {
		MODGBN_NM=aValue;
	}
	public void setILSIC_YN(java.lang.String aValue) {
		ILSIC_YN=aValue;
	}	
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0018.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0018"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("YEARM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "YEARM"));
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
		elemField.setFieldName("KUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ISNEW");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ISNEW"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHMENG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHMENG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHNETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHNETWR"));
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
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
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
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CRS_TXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRS_TXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SRAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SRAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MODGBN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MODGBN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MODGBN_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MODGBN_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ILSIC_YN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ILSIC_YN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);			
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("YEARM",(short)1,6);
		ds.addColumn("VKBURT",(short)1,25);
		ds.addColumn("VKGRPT",(short)1,25);
		ds.addColumn("ZKUNNRT",(short)1,10);
		ds.addColumn("KUNRZ1T",(short)1,25);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("ZZPJT_ID",(short)1,24);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("KUNNRT",(short)1,25);
		ds.addColumn("ISNEW",(short)1,6);
		ds.addColumn("CHMENG",(short)1,5);
		ds.addColumn("CHNETWR",(short)4,13);
		ds.addColumn("EXTOKRW",(short)4,13);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("ABGT",(short)1,10);
		ds.addColumn("CRS_TXT",(short)1,50);
		ds.addColumn("SRAT",(short)4,13);
		ds.addColumn("MODGBN",(short)1,2);
		ds.addColumn("MODGBN_NM",(short)1,60);
		ds.addColumn("ILSIC_YN",(short)1,1);
		return ds;
	}

}