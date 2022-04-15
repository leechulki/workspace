package com.helco.xf.qm01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZQMS026   
		implements java.io.Serializable {

	public ZQMS026() {
	}
	public java.lang.String STATUS;
	public java.lang.String TMODE;
	public java.lang.String WERKS;
	public java.lang.String EBELN;
	public java.lang.String EBELP;
	public java.lang.String INVNR;
	public java.lang.String INVITEM;
	public java.lang.String PARQDT;
	public java.lang.String LIFNR;
	public java.lang.String MATNR;
	public java.lang.String PSPID;
	public java.lang.String RPHOGI;
	public java.lang.String ATYPE;
	public java.lang.String SPEC;
	public java.lang.String ZEINR;
	public java.math.BigDecimal LOSMENGE;
	public java.lang.String MENGENEINH;
	public java.lang.String EINDT;
	public java.lang.String PRUEFLOS;
	public java.lang.String SLIFNR;
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public java.lang.String getTMODE(){
		return TMODE;
	}
	public java.lang.String getWERKS(){
		return WERKS;
	}
	public java.lang.String getEBELN(){
		return EBELN;
	}
	public java.lang.String getEBELP(){
		return EBELP;
	}
	public java.lang.String getINVNR(){
		return INVNR;
	}
	public java.lang.String getINVITEM(){
		return INVITEM;
	}
	public java.lang.String getPARQDT(){
		return PARQDT;
	}
	public java.lang.String getLIFNR(){
		return LIFNR;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getRPHOGI(){
		return RPHOGI;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.lang.String getZEINR(){
		return ZEINR;
	}
	public java.math.BigDecimal getLOSMENGE(){
		return LOSMENGE;
	}
	public java.lang.String getMENGENEINH(){
		return MENGENEINH;
	}
	public java.lang.String getEINDT(){
		return EINDT;
	}
	public java.lang.String getPRUEFLOS(){
		return PRUEFLOS;
	}
	public java.lang.String getSLIFNR(){
		return SLIFNR;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	public void setTMODE(java.lang.String aValue) {
		TMODE=aValue;
	}
	public void setWERKS(java.lang.String aValue) {
		WERKS=aValue;
	}
	public void setEBELN(java.lang.String aValue) {
		EBELN=aValue;
	}
	public void setEBELP(java.lang.String aValue) {
		EBELP=aValue;
	}
	public void setINVNR(java.lang.String aValue) {
		INVNR=aValue;
	}
	public void setINVITEM(java.lang.String aValue) {
		INVITEM=aValue;
	}
	public void setPARQDT(java.lang.String aValue) {
		PARQDT=aValue;
	}
	public void setLIFNR(java.lang.String aValue) {
		LIFNR=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setRPHOGI(java.lang.String aValue) {
		RPHOGI=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setZEINR(java.lang.String aValue) {
		ZEINR=aValue;
	}
	public void setLOSMENGE(java.math.BigDecimal aValue) {
		LOSMENGE=aValue;
	}
	public void setMENGENEINH(java.lang.String aValue) {
		MENGENEINH=aValue;
	}
	public void setEINDT(java.lang.String aValue) {
		EINDT=aValue;
	}
	public void setPRUEFLOS(java.lang.String aValue) {
		PRUEFLOS=aValue;
	}
	public void setSLIFNR(java.lang.String aValue) {
		SLIFNR=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZQMS026.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMS026"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TMODE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TMODE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WERKS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WERKS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EBELP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EBELP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INVNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INVNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INVITEM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INVITEM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PARQDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PARQDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LIFNR"));
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
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RPHOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RPHOGI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZEINR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZEINR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LOSMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LOSMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MENGENEINH");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGENEINH"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EINDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EINDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PRUEFLOS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PRUEFLOS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SLIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SLIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("STATUS",(short)1,1);
		ds.addColumn("TMODE",(short)1,1);
		ds.addColumn("WERKS",(short)1,4);
		ds.addColumn("EBELN",(short)1,10);
		ds.addColumn("EBELP",(short)1,5);
		ds.addColumn("INVNR",(short)1,10);
		ds.addColumn("INVITEM",(short)1,5);
		ds.addColumn("PARQDT",(short)1,8);
		ds.addColumn("LIFNR",(short)1,10);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("RPHOGI",(short)1,10);
		ds.addColumn("ATYPE",(short)1,4);
		ds.addColumn("SPEC",(short)1,30);
		ds.addColumn("ZEINR",(short)1,22);
		ds.addColumn("LOSMENGE",(short)4,13);
		ds.addColumn("MENGENEINH",(short)1,3);
		ds.addColumn("EINDT",(short)1,8);
		ds.addColumn("PRUEFLOS",(short)1,12);
		ds.addColumn("SLIFNR",(short)1,10);
		return ds;
	}

}