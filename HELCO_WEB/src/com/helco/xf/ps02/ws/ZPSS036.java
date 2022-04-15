package com.helco.xf.ps02.ws;
import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;
public class ZPSS036   
		implements java.io.Serializable {

	public ZPSS036() {
	}
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String ZZPMNUM;
	public java.lang.String ZZLIFNR;
	public java.lang.String ZZTEMNO;
	public java.lang.String ZZCOMP2;
	
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getZZPMNUM(){
		return ZZPMNUM;
	}
	public java.lang.String getZZLIFNR(){
		return ZZLIFNR;
	}
	public java.lang.String getZZTEMNO(){
		return ZZTEMNO;
	}
	public java.lang.String getZZCOMP2(){
		return ZZCOMP2;
	}
	
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setZZPMNUM(java.lang.String aValue) {
		ZZPMNUM=aValue;
	}
	public void setZZLIFNR(java.lang.String aValue) {
		ZZLIFNR=aValue;
	}
	public void setZZTEMNO(java.lang.String aValue) {
		ZZTEMNO=aValue;
	}
	public void setZZCOMP2(java.lang.String aValue) {
		ZZCOMP2=CallSAP.getFormatDate(aValue);
	}
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS036.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS036"));
		org.apache.axis.description.ElementDesc elemField =null;
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
		elemField.setFieldName("ZZPMNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPMNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZLIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZLIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZTEMNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZTEMNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZCOMP2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZCOMP2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ZZPMNUM",(short)1,6);
		ds.addColumn("ZZLIFNR",(short)1,10);
		ds.addColumn("ZZTEMNO",(short)1,6);
		ds.addColumn("ZZCOMP2",(short)1,8);
		return ds;
	}

}