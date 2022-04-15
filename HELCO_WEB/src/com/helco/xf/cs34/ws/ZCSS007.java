package com.helco.xf.cs34.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS007   
		implements java.io.Serializable {

	public ZCSS007() {
	}
	public java.lang.String SO_NO;
	public java.lang.String MSGTYP;
	public java.lang.String MSGID;
	public java.lang.String MSGNR;
	public java.lang.String getSO_NO(){
		return SO_NO;
	}
	public java.lang.String getMSGTYP(){
		return MSGTYP;
	}
	public java.lang.String getMSGID(){
		return MSGID;
	}
	public java.lang.String getMSGNR(){
		return MSGNR;
	}
	public void setSO_NO(java.lang.String aValue) {
		SO_NO=aValue;
	}
	public void setMSGTYP(java.lang.String aValue) {
		MSGTYP=aValue;
	}
	public void setMSGID(java.lang.String aValue) {
		MSGID=aValue;
	}
	public void setMSGNR(java.lang.String aValue) {
		MSGNR=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS007.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS007"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SO_NO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SO_NO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MSGTYP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSGTYP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MSGID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSGID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MSGNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSGNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("SO_NO",(short)1,10);
		ds.addColumn("MSGTYP",(short)1,1);
		ds.addColumn("MSGID",(short)1,20);
		ds.addColumn("MSGNR",(short)1,3);
		return ds;
	}

}