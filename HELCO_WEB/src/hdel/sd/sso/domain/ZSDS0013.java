package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0013   
		implements java.io.Serializable {

	public ZSDS0013() {
	}
	public java.lang.String NAM_CHAR;
	public java.lang.String ATBEZ;
	public java.lang.String CHAR_VALUE;
	public java.lang.String ATWTB;
	public java.lang.String getNAM_CHAR(){
		return NAM_CHAR;
	}
	public java.lang.String getATBEZ(){
		return ATBEZ;
	}
	public java.lang.String getCHAR_VALUE(){
		return CHAR_VALUE;
	}
	public java.lang.String getATWTB(){
		return ATWTB;
	}
	public void setNAM_CHAR(java.lang.String aValue) {
		NAM_CHAR=aValue;
	}
	public void setATBEZ(java.lang.String aValue) {
		ATBEZ=aValue;
	}
	public void setCHAR_VALUE(java.lang.String aValue) {
		CHAR_VALUE=aValue;
	}
	public void setATWTB(java.lang.String aValue) {
		ATWTB=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0013.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0013"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAM_CHAR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAM_CHAR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATBEZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATBEZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHAR_VALUE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHAR_VALUE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATWTB");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATWTB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset[] getDataset() {
		Dataset[] ds = {new Dataset(), new Dataset(), new Dataset(), new Dataset(), new Dataset()};
		for (int i=0; i<ds.length; i++) {
			ds[i].addColumn("NAM_CHAR",(short)1,30);
			ds[i].addColumn("ATBEZ",(short)1,30);
			ds[i].addColumn("CHAR_VALUE",(short)1,60);
			ds[i].addColumn("ATWTB",(short)1,3);
		}
		return ds;
	}

}