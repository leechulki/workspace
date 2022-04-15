package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0058   
		implements java.io.Serializable {

	public ZSDS0058() {
	}
	public java.lang.String DOCTPDESC;
	public java.lang.String SPDRFTDESC;
	public java.lang.String SSPLDT;
	
	public java.lang.String getDOCTPDESC(){
		return DOCTPDESC;
	}
	public java.lang.String getSPDRFTDESC(){
		return SPDRFTDESC;
	}
	public void setDOCTPDESC(java.lang.String aValue) {
		DOCTPDESC=aValue;
	}
	public void setSPDRFTDESC(java.lang.String aValue) {
		SPDRFTDESC=aValue;
	}
	public java.lang.String getSSPLDT() {
		return SSPLDT;
	}
	public void setSSPLDT(java.lang.String sSPLDT) {
		SSPLDT = sSPLDT;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0058.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0058"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DOCTPDESC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DOCTPDESC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPDRFTDESC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPDRFTDESC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SSPLDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SSPLDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("DOCTPDESC",(short)1,60);
		ds.addColumn("SPDRFTDESC",(short)1,10);
		ds.addColumn("SSPLDT",(short)1,8);
		return ds;
	}

}