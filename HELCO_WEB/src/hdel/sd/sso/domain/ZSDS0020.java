package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0020   
		implements java.io.Serializable {

	public ZSDS0020() {
	}
	public java.math.BigDecimal MONEY;
	public java.math.BigDecimal TPCOST;
	public java.math.BigDecimal PCOST;
	public java.lang.String WAERS;
	public java.lang.String NETWRT;
	public java.lang.String KWERTT;
	public java.lang.String PWERTT;
	public java.math.BigDecimal getMONEY(){
		return MONEY;
	}
	public java.math.BigDecimal getTPCOST(){
		return TPCOST;
	}
	public java.math.BigDecimal getPCOST(){
		return PCOST;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getNETWRT(){
		return NETWRT;
	}
	public java.lang.String getKWERTT(){
		return KWERTT;
	}
	public java.lang.String getPWERTT(){
		return PWERTT;
	}
	public void setMONEY(java.math.BigDecimal aValue) {
		MONEY=aValue;
	}
	public void setTPCOST(java.math.BigDecimal aValue) {
		TPCOST=aValue;
	}
	public void setPCOST(java.math.BigDecimal aValue) {
		PCOST=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setNETWRT(java.lang.String aValue) {
		NETWRT=aValue;
	}
	public void setKWERTT(java.lang.String aValue) {
		KWERTT=aValue;
	}
	public void setPWERTT(java.lang.String aValue) {
		PWERTT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0020.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0020"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MONEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MONEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TPCOST");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TPCOST"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PCOST");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PCOST"));
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
		elemField.setFieldName("NETWRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWRT"));
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
		elemField.setFieldName("PWERTT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PWERTT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset[] getDataset() {
		Dataset[] ds = {new Dataset(), new Dataset(), new Dataset()};
		for (int i=0; i<ds.length; i++) {
			ds[i].addColumn("MONEY",(short)4,13);
			ds[i].addColumn("TPCOST",(short)4,13);
			ds[i].addColumn("PCOST",(short)4,13);
			ds[i].addColumn("WAERS",(short)1,5);
			ds[i].addColumn("NETWRT",(short)1,16);
			ds[i].addColumn("KWERTT",(short)1,16);
			ds[i].addColumn("PWERTT",(short)1,16);
		}
		return ds;
	}

}