package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZCOBS001   
		implements java.io.Serializable {

	public ZCOBS001() {
	}
	public java.lang.String QTNUM;
	public java.lang.String QTSER;
	public java.lang.String QTSEQ;
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String GJAHR;
	public java.lang.String POPER;
	public java.lang.String MATNR;
	public java.lang.String VKBUR;
	public java.lang.String VKGRP;
	public java.lang.String AUART;
	public java.lang.String KTGRM;
	public java.math.BigDecimal NETWR;
	public java.lang.String REGIO;
	public java.lang.String LZONE;
	public java.lang.String ATKLA;
	public java.lang.String ATNAM;
	public java.lang.String ATBEZ;
	public java.lang.String VALUE;
	public java.lang.String ATWTB;
	public java.lang.String MESSG;
	public java.lang.String getQTNUM(){
		return QTNUM;
	}
	public java.lang.String getQTSER(){
		return QTSER;
	}
	public java.lang.String getQTSEQ(){
		return QTSEQ;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getGJAHR(){
		return GJAHR;
	}
	public java.lang.String getPOPER(){
		return POPER;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getVKBUR(){
		return VKBUR;
	}
	public java.lang.String getVKGRP(){
		return VKGRP;
	}
	public java.lang.String getAUART(){
		return AUART;
	}
	public java.lang.String getKTGRM(){
		return KTGRM;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.lang.String getREGIO(){
		return REGIO;
	}
	public java.lang.String getLZONE(){
		return LZONE;
	}
	public java.lang.String getATKLA(){
		return ATKLA;
	}
	public java.lang.String getATNAM(){
		return ATNAM;
	}
	public java.lang.String getATBEZ(){
		return ATBEZ;
	}
	public java.lang.String getVALUE(){
		return VALUE;
	}
	public java.lang.String getATWTB(){
		return ATWTB;
	}
	public java.lang.String getMESSG(){
		return MESSG;
	}
	public void setQTNUM(java.lang.String aValue) {
		QTNUM=aValue;
	}
	public void setQTSER(java.lang.String aValue) {
		QTSER=aValue;
	}
	public void setQTSEQ(java.lang.String aValue) {
		QTSEQ=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setGJAHR(java.lang.String aValue) {
		GJAHR=aValue;
	}
	public void setPOPER(java.lang.String aValue) {
		POPER=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setVKBUR(java.lang.String aValue) {
		VKBUR=aValue;
	}
	public void setVKGRP(java.lang.String aValue) {
		VKGRP=aValue;
	}
	public void setAUART(java.lang.String aValue) {
		AUART=aValue;
	}
	public void setKTGRM(java.lang.String aValue) {
		KTGRM=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setREGIO(java.lang.String aValue) {
		REGIO=aValue;
	}
	public void setLZONE(java.lang.String aValue) {
		LZONE=aValue;
	}
	public void setATKLA(java.lang.String aValue) {
		ATKLA=aValue;
	}
	public void setATNAM(java.lang.String aValue) {
		ATNAM=aValue;
	}
	public void setATBEZ(java.lang.String aValue) {
		ATBEZ=aValue;
	}
	public void setVALUE(java.lang.String aValue) {
		VALUE=aValue;
	}
	public void setATWTB(java.lang.String aValue) {
		ATWTB=aValue;
	}
	public void setMESSG(java.lang.String aValue) {
		MESSG=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCOBS001.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCOBS001"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTSER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSEQ"));
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
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GJAHR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GJAHR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POPER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POPER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
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
		elemField.setFieldName("AUART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KTGRM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KTGRM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REGIO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REGIO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LZONE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LZONE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATKLA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATKLA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATBEZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATBEZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VALUE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VALUE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATWTB");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWTB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MESSG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MESSG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("QTNUM",(short)1,10);
		ds.addColumn("QTSER",(short)1,3);
		ds.addColumn("QTSEQ",(short)1,3);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("GJAHR",(short)1,4);
		ds.addColumn("POPER",(short)1,3);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("KTGRM",(short)1,2);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("REGIO",(short)1,3);
		ds.addColumn("LZONE",(short)1,10);
		ds.addColumn("ATKLA",(short)1,10);
		ds.addColumn("ATNAM",(short)1,30);
		ds.addColumn("ATBEZ",(short)1,30);
		ds.addColumn("VALUE",(short)1,30);
		ds.addColumn("ATWTB",(short)1,30);
		ds.addColumn("MESSG",(short)1,20);
		return ds;
	}

}