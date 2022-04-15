package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;
  

/**
 * 매출채권 명세 및 채권 현황(청구) _법조치관리 (ZSDS0048) Serializable
 * @Comment 
 *     	- Ssa0022C(매출채권 명세 및 채권 현황(청구) _법조치관리) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZSDS0048   
		implements java.io.Serializable {

	public ZSDS0048() {
	}
	public java.lang.String MEKIND;
	public java.lang.String MEKINDT;
	public java.lang.String MEDAT;
	public java.lang.String DEBTR;
	public java.lang.String DEBTRT;
	public java.lang.String ERDAT;
	public java.lang.String ERZZT;
	public java.lang.String ERNAM;
	public java.lang.String AEDAT;
	public java.lang.String AEZZT;
	public java.lang.String AENAM;
	public java.lang.String MODI;
	public java.lang.String getMEKIND(){
		return MEKIND;
	}
	public java.lang.String getMEKINDT(){
		return MEKINDT;
	}
	public java.lang.String getMEDAT(){
		return MEDAT;
	}
	public java.lang.String getDEBTR(){
		return DEBTR;
	}
	public java.lang.String getDEBTRT(){
		return DEBTRT;
	}
	public java.lang.String getERDAT(){
		return ERDAT;
	}
	public java.lang.String getERZZT(){
		return ERZZT;
	}
	public java.lang.String getERNAM(){
		return ERNAM;
	}
	public java.lang.String getAEDAT(){
		return AEDAT;
	}
	public java.lang.String getAEZZT(){
		return AEZZT;
	}
	public java.lang.String getAENAM(){
		return AENAM;
	}
	public java.lang.String getMODI(){
		return MODI;
	}
	public void setMEKIND(java.lang.String aValue) {
		MEKIND=aValue;
	}
	public void setMEKINDT(java.lang.String aValue) {
		MEKINDT=aValue;
	}
	public void setMEDAT(java.lang.String aValue) {
		MEDAT=aValue;
	}
	public void setDEBTR(java.lang.String aValue) {
		DEBTR=aValue;
	}
	public void setDEBTRT(java.lang.String aValue) {
		DEBTRT=aValue;
	}
	public void setERDAT(java.lang.String aValue) {
		ERDAT=aValue;
	}
	public void setERZZT(java.lang.String aValue) {
		ERZZT=aValue;
	}
	public void setERNAM(java.lang.String aValue) {
		ERNAM=aValue;
	}
	public void setAEDAT(java.lang.String aValue) {
		AEDAT=aValue;
	}
	public void setAEZZT(java.lang.String aValue) {
		AEZZT=aValue;
	}
	public void setAENAM(java.lang.String aValue) {
		AENAM=aValue;
	}
	public void setMODI(java.lang.String aValue) {
		MODI=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0048.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0048"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEKIND");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEKIND"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEKINDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEKINDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DEBTRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DEBTRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERZZT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERZZT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AEDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AEDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AEZZT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AEZZT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AENAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AENAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MODI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MODI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MEKIND",(short)1,2);
		ds.addColumn("MEKINDT",(short)1,20);
		ds.addColumn("MEDAT",(short)1,8);
		ds.addColumn("DEBTR",(short)1,2);
		ds.addColumn("DEBTRT",(short)1,20);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("ERZZT",(short)1,6);
		ds.addColumn("ERNAM",(short)1,12);
		ds.addColumn("AEDAT",(short)1,8);
		ds.addColumn("AEZZT",(short)1,6);
		ds.addColumn("AENAM",(short)1,12);
		ds.addColumn("MODI",(short)1,1);
		return ds;
	}

}