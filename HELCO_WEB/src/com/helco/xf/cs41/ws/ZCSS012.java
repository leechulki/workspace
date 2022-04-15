package com.helco.xf.cs41.ws;
import com.tobesoft.platform.data.Dataset;
public class ZCSS012   
		implements java.io.Serializable {

	public ZCSS012() {
	}
	public java.lang.String MATNR;
	public java.lang.String MAKTX;
	public java.lang.String MBLNR;
	public java.lang.String REQDATE;
	public java.math.BigDecimal MENGE;
	public java.lang.String MEINS;
	public java.math.BigDecimal MAN;
	public java.math.BigDecimal JOBHOUR;
	public java.math.BigDecimal WON;
	public java.math.BigDecimal MAT_AMT0;
	public java.math.BigDecimal MAT_AMT;
	public java.math.BigDecimal MAN_AMT;
	public java.lang.String WAERK;
	
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getMAKTX(){
		return MAKTX;
	}
	public java.lang.String getMBLNR(){
		return MBLNR;
	}
	public java.lang.String getREQDATE(){
		return REQDATE;
	}
	public java.math.BigDecimal getMENGE(){
		return MENGE;
	}
	public java.lang.String getMEINS(){
		return MEINS;
	}
	public java.math.BigDecimal getMAN(){
		return MAN;
	}
	public java.math.BigDecimal getJOBHOUR(){
		return JOBHOUR;
	}
	public java.math.BigDecimal getWON(){
		return WON;
	}
	public java.math.BigDecimal getMAT_AMT0(){
		return MAT_AMT0;
	}
	public java.math.BigDecimal getMAT_AMT(){
		return MAT_AMT;
	}
	public java.math.BigDecimal getMAN_AMT(){
		return MAN_AMT;
	}

	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setMAKTX(java.lang.String aValue) {
		MAKTX=aValue;
	}
	public void setMBLNR(java.lang.String aValue) {
		MBLNR=aValue;
	}
	public void setREQDATE(java.lang.String aValue) {
		REQDATE=aValue;
	}
	public void setMENGE(java.math.BigDecimal aValue) {
		MENGE=aValue;
	}
	public void setMEINS(java.lang.String aValue) {
		MEINS=aValue;
	}
	public void setMAN(java.math.BigDecimal aValue) {
		MAN=aValue;
	}
	public void setJOBHOUR(java.math.BigDecimal aValue) {
		JOBHOUR=aValue;
	}
	public void setWON(java.math.BigDecimal aValue) {
		WON=aValue;
	}
	public void setMAT_AMT0(java.math.BigDecimal aValue) {
		MAT_AMT0=aValue;
	}
	public void setMAT_AMT(java.math.BigDecimal aValue) {
		MAT_AMT=aValue;
	}
	public void setMAN_AMT(java.math.BigDecimal aValue) {
		MAN_AMT=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCSS012.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCSS012"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAKTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAKTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MBLNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MBLNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REQDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REQDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEINS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEINS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JOBHOUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JOBHOUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WON");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WON"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAT_AMT0");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAT_AMT0"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAT_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAT_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MAN_AMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MAN_AMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("MAKTX",(short)1,40);
		ds.addColumn("MBLNR",(short)1,10);
		ds.addColumn("REQDATE",(short)1,8);
		ds.addColumn("MENGE",(short)6,10);		
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("MAN",(short)1,3);
		ds.addColumn("JOBHOUR",(short)6,3);
		ds.addColumn("WON",(short)4,13);
		ds.addColumn("MAT_AMT0",(short)4,13);
		ds.addColumn("MAT_AMT",(short)4,13);
		ds.addColumn("MAN_AMT",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}