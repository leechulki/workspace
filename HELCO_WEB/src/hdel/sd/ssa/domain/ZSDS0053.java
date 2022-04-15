package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0053   
		implements java.io.Serializable {

	public ZSDS0053() {
	}
	public java.lang.String ZTERM;
	public java.lang.String MLBEZ;
	public java.lang.String MLBET;
	public java.lang.String ZTERT;
	public java.math.BigDecimal FAKWR;
	public java.math.BigDecimal NETWR;
	public java.math.BigDecimal HWBAS;
	public java.lang.String WAERK;
	public java.lang.String getZTERM(){
		return ZTERM;
	}
	public java.lang.String getMLBEZ(){
		return MLBEZ;
	}
	public java.lang.String getMLBET(){
		return MLBET;
	}
	public java.lang.String getZTERT(){
		return ZTERT;
	}
	public java.math.BigDecimal getFAKWR(){
		return FAKWR;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.math.BigDecimal getHWBAS(){
		return HWBAS;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setZTERM(java.lang.String aValue) {
		ZTERM=aValue;
	}
	public void setMLBEZ(java.lang.String aValue) {
		MLBEZ=aValue;
	}
	public void setMLBET(java.lang.String aValue) {
		MLBET=aValue;
	}
	public void setZTERT(java.lang.String aValue) {
		ZTERT=aValue;
	}
	public void setFAKWR(java.math.BigDecimal aValue) {
		FAKWR=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setHWBAS(java.math.BigDecimal aValue) {
		HWBAS=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0053.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0053"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZTERM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTERM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MLBEZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MLBEZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MLBET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MLBET"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZTERT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTERT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FAKWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FAKWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HWBAS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HWBAS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ZTERM",(short)1,2);
		ds.addColumn("MLBEZ",(short)1,5);
		ds.addColumn("MLBET",(short)1,40);
		ds.addColumn("ZTERT",(short)1,50);
		ds.addColumn("FAKWR",(short)4,13);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("HWBAS",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}