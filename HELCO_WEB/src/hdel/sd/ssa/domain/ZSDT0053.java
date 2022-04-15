package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;
 

/**
 * 매출채권 명세 및 채권현황(청구별) 미수/부도 채권사유 저장 (ZSDT0053) Serializable
 * @Comment 
 *     	- Ssa0020C(매출채권 명세 및 채권현황(청구별)) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 
  

public class ZSDT0053   
		implements java.io.Serializable {

	public ZSDT0053() {
	}
	public java.lang.String MANDT;
	public java.lang.String PSPID;
	public java.lang.String REASON;
	public java.lang.String ERDAT;
	public java.lang.String ERZZT;
	public java.lang.String ERNAM;
	public java.lang.String AEDAT;
	public java.lang.String AEZZT;
	public java.lang.String AENAM;
	public java.lang.String getMANDT(){
		return MANDT;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getREASON(){
		return REASON;
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
	public void setMANDT(java.lang.String aValue) {
		MANDT=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setREASON(java.lang.String aValue) {
		REASON=aValue;
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
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDT0053.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDT0053"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
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
		elemField.setFieldName("REASON");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REASON"));
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
	}
	
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("REASON",(short)1,2);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("ERZZT",(short)1,6);
		ds.addColumn("ERNAM",(short)1,12);
		ds.addColumn("AEDAT",(short)1,8);
		ds.addColumn("AEZZT",(short)1,6);
		ds.addColumn("AENAM",(short)1,12);
		return ds;
	}

}