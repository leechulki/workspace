package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;

 
/**
 * 매출채권 명세 및 채권현황(청구별) 미수/부도 수금계획 저장 (ZSDS0039) Serializable
 * @Comment 
 *     	- Ssa0020C(매출채권 명세 및 채권현황(청구별)) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 
 

public class ZSDS0039   
		implements java.io.Serializable {

	public ZSDS0039() {
	}
	public java.lang.String BUKRS;
	public java.lang.String KUNNR;
	public java.lang.String KIDNO;
	public java.lang.String BELNR;
	public java.lang.String PLDAT;
	public java.lang.String WAERS;
	public java.math.BigDecimal PLAN_A01;
	public java.math.BigDecimal PLAN_A02;
	public java.math.BigDecimal PLAN_A03;
	public java.math.BigDecimal PLAN_A04;
	public java.math.BigDecimal PLAN_A05;
	public java.math.BigDecimal PLAN_A06;
	public java.math.BigDecimal PLAN_A07;
	public java.math.BigDecimal PLAN_A08;
	public java.math.BigDecimal PLAN_A09;
	public java.math.BigDecimal PLAN_A10;
	public java.math.BigDecimal PLAN_A11;
	public java.math.BigDecimal PLAN_A12;
	public java.math.BigDecimal PLAN_A13;
	public java.lang.String getBUKRS(){
		return BUKRS;
	}
	public java.lang.String getKUNNR(){
		return KUNNR;
	}
	public java.lang.String getKIDNO(){
		return KIDNO;
	}
	public java.lang.String getBELNR(){
		return BELNR;
	}
	public java.lang.String getPLDAT(){
		return PLDAT;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	 
	public java.math.BigDecimal getPLAN_A01(){
		return PLAN_A01;
	}
	public java.math.BigDecimal getPLAN_A02(){
		return PLAN_A02;
	}
	public java.math.BigDecimal getPLAN_A03(){
		return PLAN_A03;
	}
	public java.math.BigDecimal getPLAN_A04(){
		return PLAN_A04;
	}
	public java.math.BigDecimal getPLAN_A05(){
		return PLAN_A05;
	}
	public java.math.BigDecimal getPLAN_A06(){
		return PLAN_A06;
	}
	public java.math.BigDecimal getPLAN_A07(){
		return PLAN_A07;
	}
	public java.math.BigDecimal getPLAN_A08(){
		return PLAN_A08;
	}
	public java.math.BigDecimal getPLAN_A09(){
		return PLAN_A09;
	}
	public java.math.BigDecimal getPLAN_A10(){
		return PLAN_A10;
	}
	public java.math.BigDecimal getPLAN_A11(){
		return PLAN_A11;
	}
	public java.math.BigDecimal getPLAN_A12(){
		return PLAN_A12;
	}
	public java.math.BigDecimal getPLAN_A13(){
		return PLAN_A13;
	}
	 
	
	
	public void setBUKRS(java.lang.String aValue) {
		BUKRS=aValue;
	}
	public void setKUNNR(java.lang.String aValue) {
		KUNNR=aValue;
	}
	public void setKIDNO(java.lang.String aValue) {
		KIDNO=aValue;
	}
	public void setBELNR(java.lang.String aValue) {
		BELNR=aValue;
	}
	public void setPLDAT(java.lang.String aValue) {
		PLDAT=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	 
	public void setPLAN_A01(java.math.BigDecimal aValue) {
		PLAN_A01=aValue;
	}
	public void setPLAN_A02(java.math.BigDecimal aValue) {
		PLAN_A02=aValue;
	}
	public void setPLAN_A03(java.math.BigDecimal aValue) {
		PLAN_A03=aValue;
	}
	public void setPLAN_A04(java.math.BigDecimal aValue) {
		PLAN_A04=aValue;
	}
	public void setPLAN_A05(java.math.BigDecimal aValue) {
		PLAN_A05=aValue;
	}
	public void setPLAN_A06(java.math.BigDecimal aValue) {
		PLAN_A06=aValue;
	}
	public void setPLAN_A07(java.math.BigDecimal aValue) {
		PLAN_A07=aValue;
	}
	public void setPLAN_A08(java.math.BigDecimal aValue) {
		PLAN_A08=aValue;
	}
	public void setPLAN_A09(java.math.BigDecimal aValue) {
		PLAN_A09=aValue;
	}
	public void setPLAN_A10(java.math.BigDecimal aValue) {
		PLAN_A10=aValue;
	}
	public void setPLAN_A11(java.math.BigDecimal aValue) {
		PLAN_A11=aValue;
	}
	public void setPLAN_A12(java.math.BigDecimal aValue) {
		PLAN_A12=aValue;
	}
	public void setPLAN_A13(java.math.BigDecimal aValue) {
		PLAN_A13=aValue;
	} 
	
	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0039.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0039"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUKRS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUKRS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KIDNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KIDNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BELNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BELNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLDAT"));
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
		elemField.setFieldName("PLAN_A01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A09");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A09"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A10");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A10"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A11");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A11"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A12");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A12"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLAN_A13");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLAN_A13"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField); 
		
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("BUKRS",(short)1,4);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("KIDNO",(short)1,30);
		ds.addColumn("BELNR",(short)1,10);
		ds.addColumn("PLDAT",(short)1,8);
		ds.addColumn("WAERS",(short)1,5); 
		ds.addColumn("PLAN_A01",(short)4,13);
		ds.addColumn("PLAN_A02",(short)4,13);
		ds.addColumn("PLAN_A03",(short)4,13);
		ds.addColumn("PLAN_A04",(short)4,13);
		ds.addColumn("PLAN_A05",(short)4,13);
		ds.addColumn("PLAN_A06",(short)4,13);
		ds.addColumn("PLAN_A07",(short)4,13);
		ds.addColumn("PLAN_A08",(short)4,13);
		ds.addColumn("PLAN_A09",(short)4,13);
		ds.addColumn("PLAN_A10",(short)4,13);
		ds.addColumn("PLAN_A11",(short)4,13);
		ds.addColumn("PLAN_A12",(short)4,13);
		ds.addColumn("PLAN_A13",(short)4,13); 
		return ds;
	}

}