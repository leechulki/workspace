package com.helco.xf.bs01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0024   
		implements java.io.Serializable {

	public ZSDS0024() {
	}
	public java.lang.String GRP;
	public java.lang.String VTEXT;
	public java.lang.String VARCOND;
	public java.lang.String VARCONDT;
	public java.math.BigDecimal AMT;
	public java.math.BigDecimal TOTAMT;
	public java.lang.String getGRP(){
		return GRP;
	}
	public java.lang.String getVTEXT(){
		return VTEXT;
	}
	public java.lang.String getVARCOND(){
		return VARCOND;
	}
	public java.lang.String getVARCONDT(){
		return VARCONDT;
	}
	public java.math.BigDecimal getAMT(){
		return AMT;
	}
	public java.math.BigDecimal getTOTAMT(){
		return TOTAMT;
	}
	public void setGRP(java.lang.String aValue) {
		GRP=aValue;
	}
	public void setVTEXT(java.lang.String aValue) {
		VTEXT=aValue;
	}
	public void setVARCOND(java.lang.String aValue) {
		VARCOND=aValue;
	}
	public void setVARCONDT(java.lang.String aValue) {
		VARCONDT=aValue;
	}
	public void setAMT(java.math.BigDecimal aValue) {
		AMT=aValue;
	}
	public void setTOTAMT(java.math.BigDecimal aValue) {
		TOTAMT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0024.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0024"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GRP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GRP"));
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
		elemField.setFieldName("VARCOND");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VARCOND"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VARCONDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VARCONDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TOTAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TOTAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("GRP",(short)1,1);
		ds.addColumn("VTEXT",(short)1,40);
		ds.addColumn("VARCOND",(short)1,26);
		ds.addColumn("VARCONDT",(short)1,40);
		ds.addColumn("AMT",(short)4,13);
		ds.addColumn("TOTAMT",(short)4,13);
		return ds;
	}

}