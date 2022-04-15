package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0003   
		implements java.io.Serializable {

	public ZSDS0003() {
	}
	public java.lang.String GUBUN;
	public java.lang.String RATE;
	public java.math.BigDecimal MONEY;
	public java.lang.String WAERS;
	public java.lang.String COND;
	public java.lang.String ASKDAY;
	public java.lang.String NETWRT;
	public java.lang.String TEXT1;
	public java.lang.String getGUBUN(){
		return GUBUN;
	}
	public java.lang.String getRATE(){
		return RATE;
	}
	public java.math.BigDecimal getMONEY(){
		return MONEY;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getCOND(){
		return COND;
	}
	public java.lang.String getASKDAY(){
		return ASKDAY;
	}
	public java.lang.String getNETWRT(){
		return NETWRT;
	}
	public java.lang.String getTEXT1(){
		return TEXT1;
	}
	public void setGUBUN(java.lang.String aValue) {
		GUBUN=aValue;
	}
	public void setRATE(java.lang.String aValue) {
		RATE=aValue;
	}
	public void setMONEY(java.math.BigDecimal aValue) {
		MONEY=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setCOND(java.lang.String aValue) {
		COND=aValue;
	}
	public void setASKDAY(java.lang.String aValue) {
		ASKDAY=aValue;
	}
	public void setNETWRT(java.lang.String aValue) {
		NETWRT=aValue;
	}
	public void setTEXT1(java.lang.String aValue) {
		TEXT1=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0003.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0003"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MONEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MONEY"));
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
		elemField.setFieldName("COND");
		elemField.setXmlName(new javax.xml.namespace.QName("", "COND"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ASKDAY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ASKDAY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NETWRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("GUBUN",(short)1,20);
		ds.addColumn("RATE",(short)1,1);
		ds.addColumn("MONEY",(short)4,13);
		ds.addColumn("WAERS",(short)1,5);
		ds.addColumn("COND",(short)1,10);
		ds.addColumn("ASKDAY",(short)1,8);
		ds.addColumn("NETWRT",(short)1,16);
		ds.addColumn("TEXT1",(short)1,50);
		return ds;
	}

}