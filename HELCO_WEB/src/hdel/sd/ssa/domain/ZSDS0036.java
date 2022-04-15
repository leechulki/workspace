package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;


/**
 * 매출채권 명세 및 채권현황(청구별) 수금리포트 조회 (ZSDS0036) Serializable
 * @Comment 
 *     	- Ssa0020C(매출채권 명세 및 채권현황(청구별)) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZSDS0036   
		implements java.io.Serializable {

	public ZSDS0036() {
	}
	public java.lang.String XREF1;
	public java.lang.String VKBUT;
	public java.lang.String XREF2;
	public java.lang.String VKGRT;
	public java.lang.String XREF3;
	public java.lang.String NAME;
	public java.lang.String KIDNO;
	public java.lang.String KIDNO_T;
	public java.lang.String KUNNR;
	public java.lang.String NAME1;
	public java.lang.String GJAHR;
	public java.lang.String WAERS;
	public java.math.BigDecimal BAMT;
	public java.math.BigDecimal AMT01;
	public java.math.BigDecimal AMT02;
	public java.math.BigDecimal AMT03;
	public java.math.BigDecimal AMT04;
	public java.math.BigDecimal AMT05;
	public java.math.BigDecimal AMT06;
	public java.math.BigDecimal AMT07;
	public java.math.BigDecimal AMT08;
	public java.math.BigDecimal AMT09;
	public java.math.BigDecimal AMT10;
	public java.math.BigDecimal AMT11;
	public java.math.BigDecimal AMT12;
	public java.math.BigDecimal SUMAMT;
	public java.lang.String getXREF1(){
		return XREF1;
	}
	public java.lang.String getVKBUT(){
		return VKBUT;
	}
	public java.lang.String getXREF2(){
		return XREF2;
	}
	public java.lang.String getVKGRT(){
		return VKGRT;
	}
	public java.lang.String getXREF3(){
		return XREF3;
	}
	public java.lang.String getNAME(){
		return NAME;
	}
	public java.lang.String getKIDNO(){
		return KIDNO;
	}
	public java.lang.String getKIDNO_T(){
		return KIDNO_T;
	}
	public java.lang.String getKUNNR(){
		return KUNNR;
	}
	public java.lang.String getNAME1(){
		return NAME1;
	}
	public java.lang.String getGJAHR(){
		return GJAHR;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.math.BigDecimal getBAMT(){
		return BAMT;
	}
	public java.math.BigDecimal getAMT01(){
		return AMT01;
	}
	public java.math.BigDecimal getAMT02(){
		return AMT02;
	}
	public java.math.BigDecimal getAMT03(){
		return AMT03;
	}
	public java.math.BigDecimal getAMT04(){
		return AMT04;
	}
	public java.math.BigDecimal getAMT05(){
		return AMT05;
	}
	public java.math.BigDecimal getAMT06(){
		return AMT06;
	}
	public java.math.BigDecimal getAMT07(){
		return AMT07;
	}
	public java.math.BigDecimal getAMT08(){
		return AMT08;
	}
	public java.math.BigDecimal getAMT09(){
		return AMT09;
	}
	public java.math.BigDecimal getAMT10(){
		return AMT10;
	}
	public java.math.BigDecimal getAMT11(){
		return AMT11;
	}
	public java.math.BigDecimal getAMT12(){
		return AMT12;
	}
	public java.math.BigDecimal getSUMAMT(){
		return SUMAMT;
	}
	public void setXREF1(java.lang.String aValue) {
		XREF1=aValue;
	}
	public void setVKBUT(java.lang.String aValue) {
		VKBUT=aValue;
	}
	public void setXREF2(java.lang.String aValue) {
		XREF2=aValue;
	}
	public void setVKGRT(java.lang.String aValue) {
		VKGRT=aValue;
	}
	public void setXREF3(java.lang.String aValue) {
		XREF3=aValue;
	}
	public void setNAME(java.lang.String aValue) {
		NAME=aValue;
	}
	public void setKIDNO(java.lang.String aValue) {
		KIDNO=aValue;
	}
	public void setKIDNO_T(java.lang.String aValue) {
		KIDNO_T=aValue;
	}
	public void setKUNNR(java.lang.String aValue) {
		KUNNR=aValue;
	}
	public void setNAME1(java.lang.String aValue) {
		NAME1=aValue;
	}
	public void setGJAHR(java.lang.String aValue) {
		GJAHR=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setBAMT(java.math.BigDecimal aValue) {
		BAMT=aValue;
	}
	public void setAMT01(java.math.BigDecimal aValue) {
		AMT01=aValue;
	}
	public void setAMT02(java.math.BigDecimal aValue) {
		AMT02=aValue;
	}
	public void setAMT03(java.math.BigDecimal aValue) {
		AMT03=aValue;
	}
	public void setAMT04(java.math.BigDecimal aValue) {
		AMT04=aValue;
	}
	public void setAMT05(java.math.BigDecimal aValue) {
		AMT05=aValue;
	}
	public void setAMT06(java.math.BigDecimal aValue) {
		AMT06=aValue;
	}
	public void setAMT07(java.math.BigDecimal aValue) {
		AMT07=aValue;
	}
	public void setAMT08(java.math.BigDecimal aValue) {
		AMT08=aValue;
	}
	public void setAMT09(java.math.BigDecimal aValue) {
		AMT09=aValue;
	}
	public void setAMT10(java.math.BigDecimal aValue) {
		AMT10=aValue;
	}
	public void setAMT11(java.math.BigDecimal aValue) {
		AMT11=aValue;
	}
	public void setAMT12(java.math.BigDecimal aValue) {
		AMT12=aValue;
	}
	public void setSUMAMT(java.math.BigDecimal aValue) {
		SUMAMT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0036.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0036"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("XREF1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "XREF1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("XREF2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "XREF2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("XREF3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "XREF3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME"));
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
		elemField.setFieldName("KIDNO_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KIDNO_T"));
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
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GJAHR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GJAHR"));
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
		elemField.setFieldName("BAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT09");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT09"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT10");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT10"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT11");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT11"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AMT12");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AMT12"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SUMAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SUMAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("XREF1",(short)1,12);
		ds.addColumn("VKBUT",(short)1,20);
		ds.addColumn("XREF2",(short)1,12);
		ds.addColumn("VKGRT",(short)1,20);
		ds.addColumn("XREF3",(short)1,20);
		ds.addColumn("NAME",(short)1,35);
		ds.addColumn("KIDNO",(short)1,30);
		ds.addColumn("KIDNO_T",(short)1,35);
		ds.addColumn("KUNNR",(short)1,10);
		ds.addColumn("NAME1",(short)1,35);
		ds.addColumn("GJAHR",(short)1,4);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("BAMT",(short)4,13);
		ds.addColumn("AMT01",(short)4,13);
		ds.addColumn("AMT02",(short)4,13);
		ds.addColumn("AMT03",(short)4,13);
		ds.addColumn("AMT04",(short)4,13);
		ds.addColumn("AMT05",(short)4,13);
		ds.addColumn("AMT06",(short)4,13);
		ds.addColumn("AMT07",(short)4,13);
		ds.addColumn("AMT08",(short)4,13);
		ds.addColumn("AMT09",(short)4,13);
		ds.addColumn("AMT10",(short)4,13);
		ds.addColumn("AMT11",(short)4,13);
		ds.addColumn("AMT12",(short)4,13);
		ds.addColumn("SUMAMT",(short)4,13);
		return ds;
	}

}