package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0008   
		implements java.io.Serializable {

	public ZSDS0008() {
	}
	public java.lang.String VTEXT;
	public java.lang.String KWERTT;
	public java.lang.String VTEXT2;
	public java.lang.String KWERTT2;
	public java.lang.String getVTEXT(){
		return VTEXT;
	}
	public java.lang.String getKWERTT(){
		return KWERTT;
	}
	public java.lang.String getVTEXT2(){
		return VTEXT2;
	}
	public java.lang.String getKWERTT2(){
		return KWERTT2;
	}
	public void setVTEXT(java.lang.String aValue) {
		VTEXT=aValue;
	}
	public void setKWERTT(java.lang.String aValue) {
		KWERTT=aValue;
	}
	public void setVTEXT2(java.lang.String aValue) {
		VTEXT2=aValue;
	}
	public void setKWERTT2(java.lang.String aValue) {
		KWERTT2=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0008.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0008"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VTEXT"));
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
		elemField.setFieldName("VTEXT2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VTEXT2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWERTT2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWERTT2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("VTEXT",(short)1,40);
		ds.addColumn("KWERTT",(short)1,16);
		ds.addColumn("VTEXT2",(short)1,40);
		ds.addColumn("KWERTT2",(short)1,16);
		return ds;
	}

}