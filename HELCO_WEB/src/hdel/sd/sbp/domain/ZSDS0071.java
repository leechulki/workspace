package hdel.sd.sbp.domain;
 
import com.tobesoft.platform.data.Dataset;
public class ZSDS0071   
		implements java.io.Serializable {

	public ZSDS0071() {
	}
	public java.lang.String SONNR;
	public java.lang.String PLANYM;
	public java.lang.String POSID;
	public java.lang.String VKBUR;
	public java.lang.String VKGRP;
	public java.lang.String AUART;
	public java.lang.String SPART;
	public java.lang.String GTYPE;
	public java.lang.String MATNR;
	public java.lang.String ZINTER;
	public java.lang.String SHANGH;
	public java.lang.String getSONNR(){
		return SONNR;
	}
	public java.lang.String getPLANYM(){
		return PLANYM;
	}
	public java.lang.String getPOSID(){
		return POSID;
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
	public java.lang.String getSPART(){
		return SPART;
	}
	public java.lang.String getGTYPE(){
		return GTYPE;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getZINTER(){
		return ZINTER;
	}
	public java.lang.String getSHANGH(){
		return SHANGH;
	}
	public void setSONNR(java.lang.String aValue) {
		SONNR=aValue;
	}
	public void setPLANYM(java.lang.String aValue) {
		PLANYM=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
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
	public void setSPART(java.lang.String aValue) {
		SPART=aValue;
	}
	public void setGTYPE(java.lang.String aValue) {
		GTYPE=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setZINTER(java.lang.String aValue) {
		ZINTER=aValue;
	}
	public void setSHANGH(java.lang.String aValue) {
		SHANGH=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0071.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0071"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SONNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SONNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLANYM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLANYM"));
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
		elemField.setFieldName("SPART");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPART"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GTYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GTYPE"));
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
		elemField.setFieldName("ZINTER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZINTER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SHANGH");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SHANGH"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("SONNR",(short)1,10);
		ds.addColumn("PLANYM",(short)1,6);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("VKGRP",(short)1,3);
		ds.addColumn("AUART",(short)1,4);
		ds.addColumn("SPART",(short)1,2);
		ds.addColumn("GTYPE",(short)1,20);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("ZINTER",(short)1,1);
		ds.addColumn("SHANGH",(short)1,4);
		return ds;
	}

} 