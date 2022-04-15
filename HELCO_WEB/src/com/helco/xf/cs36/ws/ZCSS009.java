package com.helco.xf.cs36.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS009   
		implements java.io.Serializable {

	public ZCSS009() {
	}
	public java.lang.String FKYYM;
	public java.lang.String ARA_NM;
	public java.lang.String BSU_NM;
	public java.lang.String KUNNR_NM;
	public java.lang.String ZZPJT_ID;
	public java.lang.String BSTKD;
	public java.lang.String SPARTT;
	public java.lang.String AUARTT;
	public java.lang.String ZTERM;
	public java.lang.String FKDAT;
	public java.math.BigDecimal FAKWR;
	public java.lang.String WAERS;
	public java.lang.String VBELN;
	public java.math.BigDecimal BCNT;
	public java.lang.String TAXDATE;
	public java.lang.String TAXNO;
	public java.math.BigDecimal TCNT;
	public java.lang.String DGNO;
	public java.lang.String getFKYYM(){
		return FKYYM;
	}
	public java.lang.String getARA_NM(){
		return ARA_NM;
	}
	public java.lang.String getBSU_NM(){
		return BSU_NM;
	}
	public java.lang.String getKUNNR_NM(){
		return KUNNR_NM;
	}
	public java.lang.String getZZPJT_ID(){
		return ZZPJT_ID;
	}
	public java.lang.String getBSTKD(){
		return BSTKD;
	}
	public java.lang.String getSPARTT(){
		return SPARTT;
	}
	public java.lang.String getAUARTT(){
		return AUARTT;
	}
	public java.lang.String getZTERM(){
		return ZTERM;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.math.BigDecimal getFAKWR(){
		return FAKWR;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.math.BigDecimal getBCNT(){
		return BCNT;
	}
	public java.lang.String getTAXDATE(){
		return TAXDATE;
	}
	public java.lang.String getTAXNO(){
		return TAXNO;
	}
	public java.math.BigDecimal getTCNT(){
		return TCNT;
	}
	public java.lang.String getDGNO(){
		return DGNO;
	}
	public void setFKYYM(java.lang.String aValue) {
		FKYYM=aValue;
	}
	public void setARA_NM(java.lang.String aValue) {
		ARA_NM=aValue;
	}
	public void setBSU_NM(java.lang.String aValue) {
		BSU_NM=aValue;
	}
	public void setKUNNR_NM(java.lang.String aValue) {
		KUNNR_NM=aValue;
	}
	public void setZZPJT_ID(java.lang.String aValue) {
		ZZPJT_ID=aValue;
	}
	public void setBSTKD(java.lang.String aValue) {
		BSTKD=aValue;
	}
	public void setSPARTT(java.lang.String aValue) {
		SPARTT=aValue;
	}
	public void setAUARTT(java.lang.String aValue) {
		AUARTT=aValue;
	}
	public void setZTERM(java.lang.String aValue) {
		ZTERM=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setFAKWR(java.math.BigDecimal aValue) {
		FAKWR=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setBCNT(java.math.BigDecimal aValue) {
		BCNT=aValue;
	}
	public void setTAXDATE(java.lang.String aValue) {
		TAXDATE=aValue;
	}
	public void setTAXNO(java.lang.String aValue) {
		TAXNO=aValue;
	}
	public void setTCNT(java.math.BigDecimal aValue) {
		TCNT=aValue;
	}
	public void setDGNO(java.lang.String aValue) {
		DGNO=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS009.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS009"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKYYM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKYYM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ARA_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARA_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSU_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSU_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR_NM"));
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
		elemField.setFieldName("SPARTT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPARTT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUARTT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUARTT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZTERM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTERM"));
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
		elemField.setFieldName("FAKWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FAKWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BCNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BCNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TAXDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TAXDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TAXNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TAXNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TCNT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TCNT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DGNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DGNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("FKYYM",(short)1,6);
		ds.addColumn("ARA_NM",(short)1,20);
		ds.addColumn("BSU_NM",(short)1,20);
		ds.addColumn("KUNNR_NM",(short)1,35);
		ds.addColumn("ZZPJT_ID",(short)1,24);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("SPARTT",(short)1,20);
		ds.addColumn("AUARTT",(short)1,20);
		ds.addColumn("ZTERM",(short)1,4);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("FAKWR",(short)4,13);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("BCNT",(short)4,13);
		ds.addColumn("TAXDATE",(short)1,8);
		ds.addColumn("TAXNO",(short)1,10);
		ds.addColumn("TCNT",(short)4,13);
		ds.addColumn("DGNO",(short)1,13);
		return ds;
	}

}