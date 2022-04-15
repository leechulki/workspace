package com.helco.xf.cs01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS016   
		implements java.io.Serializable {

	public ZPPS016() {
	}
	public java.lang.String SEQNO;
	public java.lang.String WOKNUM;
	public java.lang.String WOKNUM_NO;
	public java.lang.String MATNR;
	public java.lang.String CRDAT;
	public java.lang.String SER_NO;

	public java.lang.String getSEQNO() {
		return SEQNO;
	}

	public void setSEQNO(java.lang.String sEQNO) {
		SEQNO = sEQNO;
	}

	public java.lang.String getWOKNUM() {
		return WOKNUM;
	}

	public void setWOKNUM(java.lang.String wOKNUM) {
		WOKNUM = wOKNUM;
	}

	public java.lang.String getWOKNUM_NO() {
		return WOKNUM_NO;
	}

	public void setWOKNUM_NO(java.lang.String wOKNUM_NO) {
		WOKNUM_NO = wOKNUM_NO;
	}

	public java.lang.String getMATNR() {
		return MATNR;
	}

	public void setMATNR(java.lang.String mATNR) {
		MATNR = mATNR;
	}

	public java.lang.String getCRDAT() {
		return CRDAT;
	}

	public void setCRDAT(java.lang.String cRDAT) {
		CRDAT = cRDAT;
	}

	public java.lang.String getSER_NO() {
		return SER_NO;
	}

	public void setSER_NO(java.lang.String sER_NO) {
		SER_NO = sER_NO;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS016.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS016"));
		
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SEQNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SEQNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WOKNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WOKNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WOKNUM_NO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WOKNUM_NO"));
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
		elemField.setFieldName("CRDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CRDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SER_NO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SER_NO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("SEQNO",(short)1,4);
		ds.addColumn("WOKNUM",(short)1,24);
		ds.addColumn("WOKNUM_NO",(short)1,3);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("CRDAT",(short)1,8);
		ds.addColumn("SER_NO",(short)1,30);
		return ds;
	}

}