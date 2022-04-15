package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0051   
		implements java.io.Serializable {

	public ZSDS0051() {
	}
	public java.lang.String CHPDT;
	public java.lang.String ZBEPJEO;
	public int CQNTY;
	public java.math.BigDecimal PROF_H;
	public java.lang.String RATE_B;
	public java.lang.String RATE_I;
	public java.lang.String LOAN_R;
	public java.lang.String RATE_PF;
	public java.math.BigDecimal PFCAP;
	public java.lang.String WAERK;
	public java.lang.String getCHPDT(){
		return CHPDT;
	}
	public java.lang.String getZBEPJEO(){
		return ZBEPJEO;
	}
	public int getCQNTY(){
		return CQNTY;
	}
	public java.math.BigDecimal getPROF_H(){
		return PROF_H;
	}
	public java.lang.String getRATE_B(){
		return RATE_B;
	}
	public java.lang.String getRATE_I(){
		return RATE_I;
	}
	public java.lang.String getLOAN_R(){
		return LOAN_R;
	}
	public java.lang.String getRATE_PF(){
		return RATE_PF;
	}
	public java.math.BigDecimal getPFCAP(){
		return PFCAP;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setCHPDT(java.lang.String aValue) {
		CHPDT=aValue;
	}
	public void setZBEPJEO(java.lang.String aValue) {
		ZBEPJEO=aValue;
	}
	public void setCQNTY(int aValue) {
		CQNTY=aValue;
	}
	public void setPROF_H(java.math.BigDecimal aValue) {
		PROF_H=aValue;
	}
	public void setRATE_B(java.lang.String aValue) {
		RATE_B=aValue;
	}
	public void setRATE_I(java.lang.String aValue) {
		RATE_I=aValue;
	}
	public void setLOAN_R(java.lang.String aValue) {
		LOAN_R=aValue;
	}
	public void setRATE_PF(java.lang.String aValue) {
		RATE_PF=aValue;
	}
	public void setPFCAP(java.math.BigDecimal aValue) {
		PFCAP=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0051.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0051"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHPDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHPDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZBEPJEO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZBEPJEO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CQNTY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PROF_H");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PROF_H"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RATE_B");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RATE_B"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RATE_I");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RATE_I"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LOAN_R");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LOAN_R"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RATE_PF");
		elemField.setXmlName(new javax.xml.namespace.QName("", "RATE_PF"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PFCAP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PFCAP"));
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
		ds.addColumn("CHPDT",(short)1,8);
		ds.addColumn("ZBEPJEO",(short)1,8);
		ds.addColumn("CQNTY",(short)2,10);
		ds.addColumn("PROF_H",(short)4,13);
		ds.addColumn("RATE_B",(short)1,12);
		ds.addColumn("RATE_I",(short)1,12);
		ds.addColumn("LOAN_R",(short)1,12);
		ds.addColumn("RATE_PF",(short)1,12);
		ds.addColumn("PFCAP",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}