package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0027   
		implements java.io.Serializable {

	public ZSDS0027() {
	}
	public java.lang.String KTOKD;
	public java.lang.String KUNNR;
	public java.lang.String BRAN1;
	public java.lang.String VTWEG;
	public java.lang.String SPART;
	
	public java.lang.String VKBUR;
	public java.lang.String VKGRP;
	public java.lang.String NAME1;
	public java.lang.String SORTL;
	public java.lang.String JUDGE;
	
	public java.lang.String MNGTEXT;
	public java.lang.String CLOS_DAT;
	public java.lang.String STCD2;
	public java.lang.String STCD1;
	public java.lang.String KFTBUS;
	
	public java.lang.String KFTIND;
	public java.lang.String KFREPRE;
	public java.lang.String PSTLZ;
	public java.lang.String ORT01;
	public java.lang.String STRAS;
	
	public java.lang.String LAND1;
	public java.lang.String LANDX;
	public java.lang.String REGIO;
	public java.lang.String WAERS;
	public java.lang.String TAXKD;
	public java.lang.String getKTOKD(){
		return KTOKD;
	}
	public java.lang.String getKUNNR(){
		return KUNNR;
	}
	public java.lang.String getBRAN1(){
		return BRAN1;
	}
	public java.lang.String getVTWEG(){
		return VTWEG;
	}
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getVKBUR(){
		return VKBUR;
	}
	public java.lang.String getVKGRP(){
		return VKGRP;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.lang.String getSORTL(){
		return SORTL;
	}
	public java.lang.String getJUDGE(){
		return JUDGE;
	}
	public java.lang.String getMNGTEXT(){
		return MNGTEXT;
	}
	public java.lang.String getCLOS_DAT(){
		return CLOS_DAT;
	}
	public java.lang.String getSTCD2(){
		return STCD2;
	}
	public java.lang.String getSTCD1(){
		return STCD1;
	}
	public java.lang.String getKFTBUS(){
		return KFTBUS;
	}
	public java.lang.String getKFTIND(){
		return KFTIND;
	}
	public java.lang.String getKFREPRE(){
		return KFREPRE;
	}
	public java.lang.String getPSTLZ(){
		return PSTLZ;
	}
	public java.lang.String getORT01(){
		return ORT01;
	}
	public java.lang.String getSTRAS(){
		return STRAS;
	}
	public java.lang.String getLAND1(){
		return LAND1;
	}
	public java.lang.String getLANDX(){
		return LANDX;
	}
	public java.lang.String getREGIO(){
		return REGIO;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getTAXKD(){
		return TAXKD;
	}
	public void setKTOKD(java.lang.String aValue) {
		KTOKD=aValue;
	}
	public void setKUNNR(java.lang.String aValue) {
		KUNNR=aValue;
	}
	public void setBRAN1(java.lang.String aValue) {
		BRAN1=aValue;
	}
	public void setVTWEG(java.lang.String aValue) {
		VTWEG=aValue;
	}
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setVKBUR(java.lang.String aValue) {
		VKBUR=aValue;
	}
	public void setVKGRP(java.lang.String aValue) {
		VKGRP=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setSORTL(java.lang.String aValue) {
		SORTL=aValue;
	}
	public void setJUDGE(java.lang.String aValue) {
		JUDGE=aValue;
	}
	public void setMNGTEXT(java.lang.String aValue) {
		MNGTEXT=aValue;
	}
	public void setCLOS_DAT(java.lang.String aValue) {
		CLOS_DAT=aValue;
	}
	public void setSTCD2(java.lang.String aValue) {
		STCD2=aValue;
	}
	public void setSTCD1(java.lang.String aValue) {
		STCD1=aValue;
	}
	public void setKFTBUS(java.lang.String aValue) {
		KFTBUS=aValue;
	}
	public void setKFTIND(java.lang.String aValue) {
		KFTIND=aValue;
	}
	public void setKFREPRE(java.lang.String aValue) {
		KFREPRE=aValue;
	}
	public void setPSTLZ(java.lang.String aValue) {
		PSTLZ=aValue;
	}
	public void setORT01(java.lang.String aValue) {
		ORT01=aValue;
	}
	public void setSTRAS(java.lang.String aValue) {
		STRAS=aValue;
	}
	public void setLAND1(java.lang.String aValue) {
		LAND1=aValue;
	}
	public void setLANDX(java.lang.String aValue) {
		LANDX=aValue;
	}
	public void setREGIO(java.lang.String aValue) {
		REGIO=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setTAXKD(java.lang.String aValue) {
		TAXKD=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0027.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0027"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KTOKD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KTOKD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BRAN1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BRAN1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VTWEG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VTWEG"));
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
		elemField.setFieldName("VKBUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRP"));
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
		elemField.setFieldName("SORTL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SORTL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JUDGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JUDGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MNGTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MNGTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CLOS_DAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CLOS_DAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STCD2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STCD2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STCD1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STCD1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KFTBUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KFTBUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KFTIND");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KFTIND"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KFREPRE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KFREPRE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSTLZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSTLZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ORT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ORT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STRAS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STRAS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LAND1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAND1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LANDX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LANDX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REGIO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REGIO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TAXKD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TAXKD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("KTOKD",(short)1,4);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("BRAN1",(short)1,10);
		ds.addColumn("VTWEG",(short)1,2);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("SORTL",(short)1,10);
		ds.addColumn("JUDGE",(short)1,2);
		ds.addColumn("MNGTEXT",(short)1,20);
		ds.addColumn("CLOS_DAT",(short)1,8);
		ds.addColumn("STCD2",(short)1,11);
		ds.addColumn("STCD1",(short)1,16);
		ds.addColumn("KFTBUS",(short)1,30);
		ds.addColumn("KFTIND",(short)1,30);
		ds.addColumn("KFREPRE",(short)1,10);
		ds.addColumn("PSTLZ",(short)1,10);
		ds.addColumn("ORT01",(short)1,25);
		ds.addColumn("STRAS",(short)1,30);
		ds.addColumn("LAND1",(short)1,3);
		ds.addColumn("LANDX",(short)1,15);
		ds.addColumn("REGIO",(short)1,3);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("TAXKD",(short)1,1);
		return ds;
	}

}