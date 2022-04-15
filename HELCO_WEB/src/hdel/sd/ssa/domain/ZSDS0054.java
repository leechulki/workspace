package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0054   
		implements java.io.Serializable {

	public ZSDS0054() {
	}
	public java.lang.String STATE;
	public java.lang.String STATUS;
	public java.lang.String APTYP;
	public java.lang.String APDAT;
	public java.lang.String getSTATE(){
		return STATE;
	}
	public java.lang.String getSTATUS(){
		return STATUS;
	}
	public java.lang.String getAPTYP(){
		return APTYP;
	}
	public java.lang.String getAPDAT(){
		return APDAT;
	}
	public void setSTATE(java.lang.String aValue) {
		STATE=aValue;
	}
	public void setSTATUS(java.lang.String aValue) {
		STATUS=aValue;
	}
	public void setAPTYP(java.lang.String aValue) {
		APTYP=aValue;
	}
	public void setAPDAT(java.lang.String aValue) {
		APDAT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0054.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0054"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATUS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("APTYP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "APTYP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("APDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "APDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("STATE",(short)1,1);
		ds.addColumn("STATUS",(short)1,4);
		ds.addColumn("APTYP",(short)1,6);
		ds.addColumn("APDAT",(short)1,8);
		return ds;
	}

}