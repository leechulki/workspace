package com.helco.xf.bs05.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCABS007   
		implements java.io.Serializable {

	public ZCABS007() {
	}
	public java.lang.String ATKLA;
	public java.lang.String CD_KEY;
	public java.lang.String VL_KEY;
	public java.lang.String VTEXT;
	public java.lang.String AMT;
	public java.lang.String TOT;
	public java.lang.String QTY;
	public java.lang.String VAL1;
	public java.lang.String VAL2;
	public java.lang.String MSG1;
	public java.lang.String MSG2;
	public java.lang.String getATKLA(){
		return ATKLA;
	}
	public java.lang.String getCD_KEY(){
		return CD_KEY;
	}
	public java.lang.String getVL_KEY(){
		return VL_KEY;
	}
	public java.lang.String getVTEXT(){
		return VTEXT;
	}
	public java.lang.String getAMT(){
		return AMT;
	}
	public java.lang.String getTOT(){
		return TOT;
	}
	public java.lang.String getQTY(){
		return QTY;
	}
	public java.lang.String getVAL1(){
		return VAL1;
	}
	public java.lang.String getVAL2(){
		return VAL2;
	}
	public java.lang.String getMSG1(){
		return MSG1;
	}
	public java.lang.String getMSG2(){
		return MSG2;
	}
	public void setATKLA(java.lang.String aValue) {
		ATKLA=aValue;
	}
	public void setCD_KEY(java.lang.String aValue) {
		CD_KEY=aValue;
	}
	public void setVL_KEY(java.lang.String aValue) {
		VL_KEY=aValue;
	}
	public void setVTEXT(java.lang.String aValue) {
		VTEXT=aValue;
	}
	public void setAMT(java.lang.String aValue) {
		AMT=aValue;
	}
	public void setTOT(java.lang.String aValue) {
		TOT=aValue;
	}
	public void setQTY(java.lang.String aValue) {
		QTY=aValue;
	}
	public void setVAL1(java.lang.String aValue) {
		VAL1=aValue;
	}
	public void setVAL2(java.lang.String aValue) {
		VAL2=aValue;
	}
	public void setMSG1(java.lang.String aValue) {
		MSG1=aValue;
	}
	public void setMSG2(java.lang.String aValue) {
		MSG2=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCABS007.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCABS007"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATKLA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATKLA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CD_KEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CD_KEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VL_KEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VL_KEY"));
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
		elemField.setFieldName("AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TOT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TOT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAL2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAL2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MSG1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSG1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MSG2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSG2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ATKLA",(short)1,10);
		ds.addColumn("CD_KEY",(short)1,20);
		ds.addColumn("VL_KEY",(short)1,50);
		ds.addColumn("VTEXT",(short)1,50);
		ds.addColumn("AMT",(short)1,16);
		ds.addColumn("TOT",(short)1,16);
		ds.addColumn("QTY",(short)1,3);
		ds.addColumn("VAL1",(short)1,50);
		ds.addColumn("VAL2",(short)1,50);
		ds.addColumn("MSG1",(short)1,50);
		ds.addColumn("MSG2",(short)1,200);
		return ds;
	}

}