package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;


/**
 * 매출채권 명세 및 채권 현황(청구) _진행사항관리 (ZSDS0042) Serializable
 * @Comment 
 *     	- Ssa0021C(매출채권 명세 및 채권 현황(청구) _진행사항관리) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 


public class ZSDS0042   
		implements java.io.Serializable {

	public ZSDS0042() {
	}
	public java.lang.String SERNO;
	public java.lang.String INGTEXT;
	public java.lang.String DISGBN;
	public java.lang.String UNAME;
	public java.lang.String DATUM;
	public java.lang.String CHAN_UNAME;
	public java.lang.String CHAN_DATUM;
	public java.lang.String MODI;
	public java.lang.String getSERNO(){
		return SERNO;
	}
	public java.lang.String getINGTEXT(){
		return INGTEXT;
	}
	public java.lang.String getDISGBN(){
		return DISGBN;
	}
	public java.lang.String getUNAME(){
		return UNAME;
	}
	public java.lang.String getDATUM(){
		return DATUM;
	}
	public java.lang.String getCHAN_UNAME(){
		return CHAN_UNAME;
	}
	public java.lang.String getCHAN_DATUM(){
		return CHAN_DATUM;
	}
	public java.lang.String getMODI(){
		return MODI;
	}
	public void setSERNO(java.lang.String aValue) {
		SERNO=aValue;
	}
	public void setINGTEXT(java.lang.String aValue) {
		INGTEXT=aValue;
	}
	public void setDISGBN(java.lang.String aValue) {
		DISGBN=aValue;
	}
	public void setUNAME(java.lang.String aValue) {
		UNAME=aValue;
	}
	public void setDATUM(java.lang.String aValue) {
		DATUM=aValue;
	}
	public void setCHAN_UNAME(java.lang.String aValue) {
		CHAN_UNAME=aValue;
	}
	public void setCHAN_DATUM(java.lang.String aValue) {
		CHAN_DATUM=aValue;
	}
	public void setMODI(java.lang.String aValue) {
		MODI=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0042.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0042"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SERNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SERNO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("INGTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "INGTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DISGBN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DISGBN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UNAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UNAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DATUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DATUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHAN_UNAME");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHAN_UNAME"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHAN_DATUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHAN_DATUM"));
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
		ds.addColumn("SERNO",(short)1,3);
		ds.addColumn("INGTEXT",(short)1,255);
		ds.addColumn("DISGBN",(short)1,1);
		ds.addColumn("UNAME",(short)1,12);
		ds.addColumn("DATUM",(short)1,8);
		ds.addColumn("CHAN_UNAME",(short)1,12);
		ds.addColumn("CHAN_DATUM",(short)1,8);
		ds.addColumn("MODI",(short)1,1);
		return ds;
	}

}