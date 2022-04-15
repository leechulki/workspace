package com.helco.xf.mm05.ws;
import com.tobesoft.platform.data.Dataset;
public class ZLES006   
		implements java.io.Serializable {

	public ZLES006() {
	}
	public java.lang.String ZSTAT4;
	public java.lang.String ZSTAT4_NM;
	public java.lang.String BDTER;
	public java.lang.String PSPID;
	public java.lang.String POSID2;
	public java.lang.String POST1;
	public java.lang.String EL_ATYP;
	public java.lang.String EL_ZAFQ;
	public java.lang.String EL_SPEC;
	public java.lang.String AUFNR;
	public java.lang.String KTEXT;
	public java.lang.String EXTWG;
	public java.lang.String EWBEZ;
	public java.lang.String ZBELN;
	public java.lang.String ZKNUM;
	public java.lang.String ZKNUM2;
	public java.lang.String VBELN;
	public java.lang.String VTEXT;
	public java.lang.String BOX_GUBN;	
	public java.lang.String getZSTAT4(){
		return ZSTAT4;
	}
	public java.lang.String getZSTAT4_NM(){
		return ZSTAT4_NM;
	}
	public java.lang.String getBDTER(){
		return BDTER;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID2(){
		return POSID2;
	}
	public java.lang.String getPOST1(){
		return POST1;
	}
	public java.lang.String getEL_ATYP(){
		return EL_ATYP;
	}
	public java.lang.String getEL_ZAFQ(){
		return EL_ZAFQ;
	}
	public java.lang.String getEL_SPEC(){
		return EL_SPEC;
	}
	public java.lang.String getAUFNR(){
		return AUFNR;
	}
	public java.lang.String getKTEXT(){
		return KTEXT;
	}
	public java.lang.String getEXTWG(){
		return EXTWG;
	}
	public java.lang.String getEWBEZ(){
		return EWBEZ;
	}
	public java.lang.String getZBELN(){
		return ZBELN;
	}
	public java.lang.String getZKNUM(){
		return ZKNUM;
	}
	public java.lang.String getZKNUM2(){
		return ZKNUM2;
	}
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getVTEXT(){
		return VTEXT;
	}
	public java.lang.String getBOX_GUBN() {
		return BOX_GUBN;
	}
	
	public void setZSTAT4(java.lang.String aValue) {
		ZSTAT4=aValue;
	}
	public void setZSTAT4_NM(java.lang.String aValue) {
		ZSTAT4_NM=aValue;
	}
	public void setBDTER(java.lang.String aValue) {
		BDTER=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID2(java.lang.String aValue) {
		POSID2=aValue;
	}
	public void setPOST1(java.lang.String aValue) {
		POST1=aValue;
	}
	public void setEL_ATYP(java.lang.String aValue) {
		EL_ATYP=aValue;
	}
	public void setEL_ZAFQ(java.lang.String aValue) {
		EL_ZAFQ=aValue;
	}
	public void setEL_SPEC(java.lang.String aValue) {
		EL_SPEC=aValue;
	}
	public void setAUFNR(java.lang.String aValue) {
		AUFNR=aValue;
	}
	public void setKTEXT(java.lang.String aValue) {
		KTEXT=aValue;
	}
	public void setEXTWG(java.lang.String aValue) {
		EXTWG=aValue;
	}
	public void setEWBEZ(java.lang.String aValue) {
		EWBEZ=aValue;
	}
	public void setZBELN(java.lang.String aValue) {
		ZBELN=aValue;
	}
	public void setZKNUM(java.lang.String aValue) {
		ZKNUM=aValue;
	}
	public void setZKNUM2(java.lang.String aValue) {
		ZKNUM2=aValue;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setVTEXT(java.lang.String aValue) {
		VTEXT=aValue;
	}
	public void setBOX_GUBN(java.lang.String box_gubn) {
		BOX_GUBN = box_gubn;
	}	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZLES006.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZLES006"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSTAT4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSTAT4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSTAT4_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSTAT4_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BDTER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BDTER"));
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
		elemField.setFieldName("POSID2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID2"));
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
		elemField.setFieldName("EL_ATYP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EL_ATYP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EL_ZAFQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EL_ZAFQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EL_SPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EL_SPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EXTWG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EXTWG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EWBEZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EWBEZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKNUM2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKNUM2"));
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
		elemField.setFieldName("VTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BOX_GUBN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BOX_GUBN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ZSTAT4",(short)1,1);
		ds.addColumn("ZSTAT4_NM",(short)1,20);
		ds.addColumn("BDTER",(short)1,8);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID2",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("EL_ATYP",(short)1,30);
		ds.addColumn("EL_ZAFQ",(short)1,30);
		ds.addColumn("EL_SPEC",(short)1,30);
		ds.addColumn("AUFNR",(short)1,12);
		ds.addColumn("KTEXT",(short)1,40);
		ds.addColumn("EXTWG",(short)1,18);
		ds.addColumn("EWBEZ",(short)1,20);
		ds.addColumn("ZBELN",(short)1,10);
		ds.addColumn("ZKNUM",(short)1,12);
		ds.addColumn("ZKNUM2",(short)1,2);
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("VTEXT",(short)1,20);
		ds.addColumn("BOX_GUBN",(short)1,20);		
		return ds;
	}


}