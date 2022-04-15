package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;


/**
 * 매출채권 명세 및 채권 현황(청구) _법조치관리 최종자료(ZSDS0049) Serializable
 * @Comment 
 *     	- Ssa0022C(매출채권 명세 및 채권 현황(청구) _법조치관리) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZSDS0049   
		implements java.io.Serializable {

	public ZSDS0049() {
	}
	public java.lang.String MEDAT01;
	public java.lang.String MEDAT02;
	public java.lang.String MEDAT03;
	public java.lang.String MEDAT04;
	public java.lang.String MEDAT05;
	public java.lang.String MEDAT06;
	public java.lang.String MEDAT07;
	public java.lang.String MEDAT08;
	public java.lang.String DEBTRT01;
	public java.lang.String DEBTRT02;
	public java.lang.String DEBTRT03;
	public java.lang.String DEBTRT04;
	public java.lang.String DEBTRT05;
	public java.lang.String DEBTRT06;
	public java.lang.String DEBTRT07;
	public java.lang.String DEBTRT08;
	public int QQ_CNT01;
	public int QQ_CNT02;
	public int QQ_CNT03;
	public int QQ_CNT04;
	public int QQ_CNT05;
	public int QQ_CNT06;
	public int QQ_CNT07;
	public int QQ_CNT08;
	public java.lang.String getMEDAT01(){
		return MEDAT01;
	}
	public java.lang.String getMEDAT02(){
		return MEDAT02;
	}
	public java.lang.String getMEDAT03(){
		return MEDAT03;
	}
	public java.lang.String getMEDAT04(){
		return MEDAT04;
	}
	public java.lang.String getMEDAT05(){
		return MEDAT05;
	}
	public java.lang.String getMEDAT06(){
		return MEDAT06;
	}
	public java.lang.String getMEDAT07(){
		return MEDAT07;
	}
	public java.lang.String getMEDAT08(){
		return MEDAT08;
	}
	public java.lang.String getDEBTRT01(){
		return DEBTRT01;
	}
	public java.lang.String getDEBTRT02(){
		return DEBTRT02;
	}
	public java.lang.String getDEBTRT03(){
		return DEBTRT03;
	}
	public java.lang.String getDEBTRT04(){
		return DEBTRT04;
	}
	public java.lang.String getDEBTRT05(){
		return DEBTRT05;
	}
	public java.lang.String getDEBTRT06(){
		return DEBTRT06;
	}
	public java.lang.String getDEBTRT07(){
		return DEBTRT07;
	}
	public java.lang.String getDEBTRT08(){
		return DEBTRT08;
	}
	public int getQQ_CNT01(){
		return QQ_CNT01;
	}
	public int getQQ_CNT02(){
		return QQ_CNT02;
	}
	public int getQQ_CNT03(){
		return QQ_CNT03;
	}
	public int getQQ_CNT04(){
		return QQ_CNT04;
	}
	public int getQQ_CNT05(){
		return QQ_CNT05;
	}
	public int getQQ_CNT06(){
		return QQ_CNT06;
	}
	public int getQQ_CNT07(){
		return QQ_CNT07;
	}
	public int getQQ_CNT08(){
		return QQ_CNT08;
	}
	public void setMEDAT01(java.lang.String aValue) {
		MEDAT01=aValue;
	}
	public void setMEDAT02(java.lang.String aValue) {
		MEDAT02=aValue;
	}
	public void setMEDAT03(java.lang.String aValue) {
		MEDAT03=aValue;
	}
	public void setMEDAT04(java.lang.String aValue) {
		MEDAT04=aValue;
	}
	public void setMEDAT05(java.lang.String aValue) {
		MEDAT05=aValue;
	}
	public void setMEDAT06(java.lang.String aValue) {
		MEDAT06=aValue;
	}
	public void setMEDAT07(java.lang.String aValue) {
		MEDAT07=aValue;
	}
	public void setMEDAT08(java.lang.String aValue) {
		MEDAT08=aValue;
	}
	public void setDEBTRT01(java.lang.String aValue) {
		DEBTRT01=aValue;
	}
	public void setDEBTRT02(java.lang.String aValue) {
		DEBTRT02=aValue;
	}
	public void setDEBTRT03(java.lang.String aValue) {
		DEBTRT03=aValue;
	}
	public void setDEBTRT04(java.lang.String aValue) {
		DEBTRT04=aValue;
	}
	public void setDEBTRT05(java.lang.String aValue) {
		DEBTRT05=aValue;
	}
	public void setDEBTRT06(java.lang.String aValue) {
		DEBTRT06=aValue;
	}
	public void setDEBTRT07(java.lang.String aValue) {
		DEBTRT07=aValue;
	}
	public void setDEBTRT08(java.lang.String aValue) {
		DEBTRT08=aValue;
	}
	public void setQQ_CNT01(int aValue) {
		QQ_CNT01=aValue;
	}
	public void setQQ_CNT02(int aValue) {
		QQ_CNT02=aValue;
	}
	public void setQQ_CNT03(int aValue) {
		QQ_CNT03=aValue;
	}
	public void setQQ_CNT04(int aValue) {
		QQ_CNT04=aValue;
	}
	public void setQQ_CNT05(int aValue) {
		QQ_CNT05=aValue;
	}
	public void setQQ_CNT06(int aValue) {
		QQ_CNT06=aValue;
	}
	public void setQQ_CNT07(int aValue) {
		QQ_CNT07=aValue;
	}
	public void setQQ_CNT08(int aValue) {
		QQ_CNT08=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0049.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0049"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT01");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT01"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT02");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT02"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT03");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT03"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT04");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT04"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT05");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT05"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT06");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT06"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT07");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT07"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QQ_CNT08");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QQ_CNT08"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MEDAT01",(short)1,8);
		ds.addColumn("MEDAT02",(short)1,8);
		ds.addColumn("MEDAT03",(short)1,8);
		ds.addColumn("MEDAT04",(short)1,8);
		ds.addColumn("MEDAT05",(short)1,8);
		ds.addColumn("MEDAT06",(short)1,8);
		ds.addColumn("MEDAT07",(short)1,8);
		ds.addColumn("MEDAT08",(short)1,8);
		ds.addColumn("DEBTRT01",(short)1,30);
		ds.addColumn("DEBTRT02",(short)1,30);
		ds.addColumn("DEBTRT03",(short)1,30);
		ds.addColumn("DEBTRT04",(short)1,30);
		ds.addColumn("DEBTRT05",(short)1,30);
		ds.addColumn("DEBTRT06",(short)1,30);
		ds.addColumn("DEBTRT07",(short)1,30);
		ds.addColumn("DEBTRT08",(short)1,30);
		ds.addColumn("QQ_CNT01",(short)2,10);
		ds.addColumn("QQ_CNT02",(short)2,10);
		ds.addColumn("QQ_CNT03",(short)2,10);
		ds.addColumn("QQ_CNT04",(short)2,10);
		ds.addColumn("QQ_CNT05",(short)2,10);
		ds.addColumn("QQ_CNT06",(short)2,10);
		ds.addColumn("QQ_CNT07",(short)2,10);
		ds.addColumn("QQ_CNT08",(short)2,10);
		return ds;
	}

}