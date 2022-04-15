package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0005   
		implements java.io.Serializable {

	public ZSDS0005() {
	}
	public java.lang.String KSCHL;
	public java.lang.String VTEXT;
	public java.lang.String VARCOND;
	public java.math.BigDecimal KWERT;
	public java.lang.String WAERS;
	public java.lang.String KWERTT;
	public java.math.BigDecimal TOTAL;
	public java.lang.String TOTALT;
	public java.lang.String getKSCHL(){
		return KSCHL;
	}
	public java.lang.String getVTEXT(){
		return VTEXT;
	}
	public java.lang.String getVARCOND(){
		return VARCOND;
	}
	public java.math.BigDecimal getKWERT(){
		return KWERT;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getKWERTT(){
		return KWERTT;
	}
	public java.math.BigDecimal getTOTAL(){
		return TOTAL;
	}
	public java.lang.String getTOTALT(){
		return TOTALT;
	}
	public void setKSCHL(java.lang.String aValue) {
		KSCHL=aValue;
	}
	public void setVTEXT(java.lang.String aValue) {
		VTEXT=aValue;
	}
	public void setVARCOND(java.lang.String aValue) {
		VARCOND=aValue;
	}
	public void setKWERT(java.math.BigDecimal aValue) {
		KWERT=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setKWERTT(java.lang.String aValue) {
		KWERTT=aValue;
	}
	public void setTOTAL(java.math.BigDecimal aValue) {
		TOTAL=aValue;
	}
	public void setTOTALT(java.lang.String aValue) {
		TOTALT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0005.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0005"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KSCHL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KSCHL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VARCOND");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VARCOND"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWERT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWERT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
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
		elemField.setFieldName("TOTAL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TOTAL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TOTALT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TOTALT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("KSCHL",(short)1,4);
		ds.addColumn("VTEXT",(short)1,40);
		ds.addColumn("VARCOND",(short)1,26);
		ds.addColumn("KWERT",(short)4,13);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("KWERTT",(short)1,16);
		ds.addColumn("TOTAL",(short)4,13);
		ds.addColumn("TOTALT",(short)1,15);
		return ds;
	}

}