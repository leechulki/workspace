package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0056   
		implements java.io.Serializable {

	public ZSDS0056() {
	}
	public java.lang.String SALES_CONDI_T;
	public java.lang.String COLL_RATE_T;
	public java.lang.String LC_ZFISEDT_T;
	public java.lang.String getSALES_CONDI_T(){
		return SALES_CONDI_T;
	}
	public java.lang.String getCOLL_RATE_T(){
		return COLL_RATE_T;
	}
	public java.lang.String getLC_ZFISEDT_T(){
		return LC_ZFISEDT_T;
	}
	public void setSALES_CONDI_T(java.lang.String aValue) {
		SALES_CONDI_T=aValue;
	}
	public void setCOLL_RATE_T(java.lang.String aValue) {
		COLL_RATE_T=aValue;
	}
	public void setLC_ZFISEDT_T(java.lang.String aValue) {
		LC_ZFISEDT_T=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0056.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0056"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SALES_CONDI_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SALES_CONDI_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("COLL_RATE_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "COLL_RATE_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LC_ZFISEDT_T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LC_ZFISEDT_T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("SALES_CONDI_T",(short)1,100);
		ds.addColumn("COLL_RATE_T",(short)1,30);
		ds.addColumn("LC_ZFISEDT_T",(short)1,30);
		return ds;
	}

}