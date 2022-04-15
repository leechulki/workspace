package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0052   
		implements java.io.Serializable {

	public ZSDS0052() {
	}
	public java.lang.String HOGI;
	public java.lang.String CHARACTERISTIC;
	public java.lang.String SEQ;
	public java.lang.String VBELN;
	public java.lang.String POSNR;
	public java.lang.String MOWBS;
	public java.lang.String CUOBJ;
	public java.lang.String VALUE;
	public java.lang.String ERNAM;
	public java.lang.String CR_DATE;
	public java.lang.String CR_TIME;
	public java.lang.String TP_STATUS;
	public java.lang.String TP_DATE;
	public java.lang.String TP_TIME;
	public java.lang.String TP_NAME;
	public java.lang.String ATBEZ;
	public java.lang.String ATWTB;
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getCHARACTERISTIC(){
		return CHARACTERISTIC;
	}
	public java.lang.String getSEQ(){
		return SEQ;
	}
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPOSNR(){
		return POSNR;
	}
	public java.lang.String getMOWBS(){
		return MOWBS;
	}
	public java.lang.String getCUOBJ(){
		return CUOBJ;
	}
	public java.lang.String getVALUE(){
		return VALUE;
	}
	public java.lang.String getERNAM(){
		return ERNAM;
	}
	public java.lang.String getCR_DATE(){
		return CR_DATE;
	}
	public java.lang.String getCR_TIME(){
		return CR_TIME;
	}
	public java.lang.String getTP_STATUS(){
		return TP_STATUS;
	}
	public java.lang.String getTP_DATE(){
		return TP_DATE;
	}
	public java.lang.String getTP_TIME(){
		return TP_TIME;
	}
	public java.lang.String getTP_NAME(){
		return TP_NAME;
	}
	public java.lang.String getATBEZ(){
		return ATBEZ;
	}
	public java.lang.String getATWTB(){
		return ATWTB;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setCHARACTERISTIC(java.lang.String aValue) {
		CHARACTERISTIC=aValue;
	}
	public void setSEQ(java.lang.String aValue) {
		SEQ=aValue;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPOSNR(java.lang.String aValue) {
		POSNR=aValue;
	}
	public void setMOWBS(java.lang.String aValue) {
		MOWBS=aValue;
	}
	public void setCUOBJ(java.lang.String aValue) {
		CUOBJ=aValue;
	}
	public void setVALUE(java.lang.String aValue) {
		VALUE=aValue;
	}
	public void setERNAM(java.lang.String aValue) {
		ERNAM=aValue;
	}
	public void setCR_DATE(java.lang.String aValue) {
		CR_DATE=aValue;
	}
	public void setCR_TIME(java.lang.String aValue) {
		CR_TIME=aValue;
	}
	public void setTP_STATUS(java.lang.String aValue) {
		TP_STATUS=aValue;
	}
	public void setTP_DATE(java.lang.String aValue) {
		TP_DATE=aValue;
	}
	public void setTP_TIME(java.lang.String aValue) {
		TP_TIME=aValue;
	}
	public void setTP_NAME(java.lang.String aValue) {
		TP_NAME=aValue;
	}
	public void setATBEZ(java.lang.String aValue) {
		ATBEZ=aValue;
	}
	public void setATWTB(java.lang.String aValue) {
		ATWTB=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0052.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0052"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HOGI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHARACTERISTIC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHARACTERISTIC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SEQ"));
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
		elemField.setFieldName("POSNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MOWBS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MOWBS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CUOBJ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CUOBJ"));
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
		elemField.setFieldName("ERNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CR_DATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CR_DATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CR_TIME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CR_TIME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_DATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_DATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_TIME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_TIME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_NAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_NAME"));
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
		elemField.setFieldName("ATWTB");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWTB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("CHARACTERISTIC",(short)1,30);
		ds.addColumn("SEQ",(short)1,2);
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("POSNR",(short)1,6);
		ds.addColumn("MOWBS",(short)1,24);
		ds.addColumn("CUOBJ",(short)1,18);
		ds.addColumn("VALUE",(short)1,30);
		ds.addColumn("ERNAM",(short)1,12);
		ds.addColumn("CR_DATE",(short)1,8);
		ds.addColumn("CR_TIME",(short)1,15);
		ds.addColumn("TP_STATUS",(short)1,1);
		ds.addColumn("TP_DATE",(short)1,8);
		ds.addColumn("TP_TIME",(short)1,15);
		ds.addColumn("TP_NAME",(short)1,12);
		ds.addColumn("ATBEZ",(short)1,30);
		ds.addColumn("ATWTB",(short)1,30);
		return ds;
	}

}