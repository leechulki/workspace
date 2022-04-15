package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0002   
		implements java.io.Serializable {

	public ZSDS0002() {
	}
	public java.math.BigDecimal NETWR;
	public java.math.BigDecimal KWMENG;
	public java.lang.String VRKME;
	public java.math.BigDecimal KWERT;
	public java.lang.String FOREC;
	public java.lang.String CONTR_DA;
	public java.lang.String CODE;
	public java.lang.String INCO2;
	public java.lang.String WAERS;
	public java.lang.String KURSK;
	public java.lang.String REPMO;
	public java.lang.String GUAMO;
	public java.lang.String NETWRT;
	public java.lang.String KWERTT;
	public java.lang.String KWMENGT;
	public java.lang.String PROF;
	public java.lang.String PROFC;
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.math.BigDecimal getKWMENG(){
		return KWMENG;
	}
	public java.lang.String getVRKME(){
		return VRKME;
	}
	public java.math.BigDecimal getKWERT(){
		return KWERT;
	}
	public java.lang.String getFOREC(){
		return FOREC;
	}
	public java.lang.String getCONTR_DA(){
		return CONTR_DA;
	}
	public java.lang.String getCODE(){
		return CODE;
	}
	public java.lang.String getINCO2(){
		return INCO2;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getKURSK(){
		return KURSK;
	}
	public java.lang.String getREPMO(){
		return REPMO;
	}
	public java.lang.String getGUAMO(){
		return GUAMO;
	}
	public java.lang.String getNETWRT(){
		return NETWRT;
	}
	public java.lang.String getKWERTT(){
		return KWERTT;
	}
	public java.lang.String getKWMENGT(){
		return KWMENGT;
	}
	public java.lang.String getPROF(){
		return PROF;
	}
	public java.lang.String getPROFC(){
		return PROFC;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setKWMENG(java.math.BigDecimal aValue) {
		KWMENG=aValue;
	}
	public void setVRKME(java.lang.String aValue) {
		VRKME=aValue;
	}
	public void setKWERT(java.math.BigDecimal aValue) {
		KWERT=aValue;
	}
	public void setFOREC(java.lang.String aValue) {
		FOREC=aValue;
	}
	public void setCONTR_DA(java.lang.String aValue) {
		CONTR_DA=aValue;
	}
	public void setCODE(java.lang.String aValue) {
		CODE=aValue;
	}
	public void setINCO2(java.lang.String aValue) {
		INCO2=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setKURSK(java.lang.String aValue) {
		KURSK=aValue;
	}
	public void setREPMO(java.lang.String aValue) {
		REPMO=aValue;
	}
	public void setGUAMO(java.lang.String aValue) {
		GUAMO=aValue;
	}
	public void setNETWRT(java.lang.String aValue) {
		NETWRT=aValue;
	}
	public void setKWERTT(java.lang.String aValue) {
		KWERTT=aValue;
	}
	public void setKWMENGT(java.lang.String aValue) {
		KWMENGT=aValue;
	}
	public void setPROF(java.lang.String aValue) {
		PROF=aValue;
	}
	public void setPROFC(java.lang.String aValue) {
		PROFC=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0002.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0002"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWMENG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWMENG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VRKME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VRKME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWERT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWERT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FOREC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FOREC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONTR_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONTR_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CODE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CODE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INCO2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INCO2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KURSK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KURSK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REPMO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REPMO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUAMO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUAMO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWERTT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWERTT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWMENGT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWMENGT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PROF");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PROF"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PROFC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PROFC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("KWMENG",(short)4,13);
		ds.addColumn("VRKME",(short)1,3);
		ds.addColumn("KWERT",(short)4,13);
		ds.addColumn("FOREC",(short)1,10);
		ds.addColumn("CONTR_DA",(short)1,8);
		ds.addColumn("CODE",(short)1,20);
		ds.addColumn("INCO2",(short)1,28);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("KURSK",(short)1,5);
		ds.addColumn("REPMO",(short)1,2);
		ds.addColumn("GUAMO",(short)1,2);
		ds.addColumn("NETWRT",(short)1,16);
		ds.addColumn("KWERTT",(short)1,16);
		ds.addColumn("KWMENGT",(short)1,3);
		ds.addColumn("PROF",(short)1,16);
		ds.addColumn("PROFC",(short)1,10);
		return ds;
	}

}