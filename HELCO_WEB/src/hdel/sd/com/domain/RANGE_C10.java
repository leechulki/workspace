package hdel.sd.com.domain;
import com.tobesoft.platform.data.Dataset;
 

/**
 * SO, 거래선용 코드범위 다중 선택 (RANGE_C10) Serializable
 * @Comment 
 *     	- 공통 
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public class RANGE_C10   
		implements java.io.Serializable {

	/*
	 * 
	 */
	public RANGE_C10() {
	}
	public java.lang.String SIGN;
	public java.lang.String OPTION;
	public java.lang.String LOW;
	public java.lang.String HIGH;
	public java.lang.String getSIGN(){
		return SIGN;
	}
	public java.lang.String getOPTION(){
		return OPTION;
	}
	public java.lang.String getLOW(){
		return LOW;
	}
	public java.lang.String getHIGH(){
		return HIGH;
	}
	public void setSIGN(java.lang.String aValue) {
		SIGN=aValue;
	}
	public void setOPTION(java.lang.String aValue) {
		OPTION=aValue;
	}
	public void setLOW(java.lang.String aValue) {
		LOW=aValue;
	}
	public void setHIGH(java.lang.String aValue) {
		HIGH=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(RANGE_C10.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "RANGE_C10"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SIGN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SIGN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OPTION");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OPTION"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LOW");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LOW"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HIGH");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HIGH"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("SIGN",(short)1,1);
		ds.addColumn("OPTION",(short)1,2);
		ds.addColumn("LOW",(short)1,10);
		ds.addColumn("HIGH",(short)1,10);
		return ds;
	}

}