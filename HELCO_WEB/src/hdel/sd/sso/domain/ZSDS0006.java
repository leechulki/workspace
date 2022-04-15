package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0006   
		implements java.io.Serializable {

	public ZSDS0006() {
	}
	public java.lang.String ATTDOC;
	public java.lang.String CHEOR;
	public java.lang.String CHEORT;
	public java.lang.String BSTDK;
	public java.lang.String RECAD_DA;
	public java.lang.String CHAAD_DA;
	public java.lang.String getATTDOC(){
		return ATTDOC;
	}
	public java.lang.String getCHEOR(){
		return CHEOR;
	}
	public java.lang.String getCHEORT(){
		return CHEORT;
	}
	public java.lang.String getBSTDK(){
		return BSTDK;
	}
	public java.lang.String getRECAD_DA(){
		return RECAD_DA;
	}
	public java.lang.String getCHAAD_DA(){
		return CHAAD_DA;
	}
	public void setATTDOC(java.lang.String aValue) {
		ATTDOC=aValue;
	}
	public void setCHEOR(java.lang.String aValue) {
		CHEOR=aValue;
	}
	public void setCHEORT(java.lang.String aValue) {
		CHEORT=aValue;
	}
	public void setBSTDK(java.lang.String aValue) {
		BSTDK=aValue;
	}
	public void setRECAD_DA(java.lang.String aValue) {
		RECAD_DA=aValue;
	}
	public void setCHAAD_DA(java.lang.String aValue) {
		CHAAD_DA=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0006.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0006"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATTDOC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATTDOC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHEOR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHEOR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHEORT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHEORT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTDK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTDK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RECAD_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RECAD_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHAAD_DA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHAAD_DA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("ATTDOC",(short)1,20);
		ds.addColumn("CHEOR",(short)1,2);
		ds.addColumn("CHEORT",(short)1,20);
		ds.addColumn("BSTDK",(short)1,8);
		ds.addColumn("RECAD_DA",(short)1,8);
		ds.addColumn("CHAAD_DA",(short)1,8);
		return ds;
	}

}