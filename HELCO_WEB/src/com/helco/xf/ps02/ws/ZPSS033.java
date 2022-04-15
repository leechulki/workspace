package com.helco.xf.ps02.ws;
import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;
public class ZPSS033   
		implements java.io.Serializable {

	public ZPSS033() {
	}
	public java.lang.String POSID;
	public java.lang.String ZSITE_NM;
	public java.lang.String CDATE;
	public java.lang.String ZZACTSS;
	public java.lang.String TEXT30;
	public java.lang.String NAMET;
	public java.lang.String ZZPMJID;
	public java.math.BigDecimal TERM;

	
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getZSITE_NM(){
		return ZSITE_NM;
	}
	public java.lang.String getCDATE(){
		return CDATE;
	}
	public java.lang.String getZZACTSS(){
		return ZZACTSS;
	}
	public java.lang.String getTEXT30(){
		return TEXT30;
	}
	public java.lang.String getNAMET(){
		return NAMET;
	}
	public java.lang.String getZZPMJID(){
		return ZZPMJID;
	}
	public java.math.BigDecimal getTERM(){
		return TERM;
	}
	
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setZSITE_NM(java.lang.String aValue) {
		ZSITE_NM=aValue;
	}
	public void setCDATE(java.lang.String aValue) {
		CDATE=CallSAP.getFormatDate(aValue);
	}
	public void setZZACTSS(java.lang.String aValue) {
		ZZACTSS=aValue;
	}
	public void setTEXT30(java.lang.String aValue) {
		TEXT30=aValue;
	}
	public void setNAMET(java.lang.String aValue) {
		NAMET=aValue;
	}
	public void setZZPMJID(java.lang.String aValue) {
		ZZPMJID=CallSAP.getFormatDate(aValue);
	}
	public void setTERM(java.math.BigDecimal aValue) {
		TERM=aValue;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPSS033.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPSS033"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSITE_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSITE_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZACTSS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZACTSS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT30");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT30"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAMET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAMET"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZPMJID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPMJID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TERM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TERM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ZSITE_NM",(short)1,35);
		ds.addColumn("CDATE",(short)1,8);
		ds.addColumn("ZZACTSS",(short)1,3);
		ds.addColumn("TEXT30",(short)1,30);
		ds.addColumn("NAMET",(short)1,20);
		ds.addColumn("ZZPMJID",(short)1,8);
		ds.addColumn("TERM",(short)1,10);

		return ds;
	}

}