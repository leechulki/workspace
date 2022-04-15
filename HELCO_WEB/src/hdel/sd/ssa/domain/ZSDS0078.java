package hdel.sd.ssa.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0078   
		implements java.io.Serializable {

	public ZSDS0078() {
	}
	public java.lang.String MANDT;
	public java.lang.String PSPID;
	public java.lang.String PROTP;
	public java.lang.String ERDAT;
	public java.lang.String ZSEQ;
	public java.lang.String FKDAT;
	public java.lang.String CODAT;
	public java.math.BigDecimal DMBTR;
	public java.lang.String WAERK;
	public java.lang.String ERZZT;
	public java.lang.String ERNAM;
	public java.lang.String getMANDT(){
		return MANDT;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPROTP(){
		return PROTP;
	}
	public java.lang.String getERDAT(){
		return ERDAT;
	}
	public java.lang.String getZSEQ(){
		return ZSEQ;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.lang.String getCODAT(){
		return CODAT;
	}
	public java.math.BigDecimal getDMBTR(){
		return DMBTR;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getERZZT(){
		return ERZZT;
	}
	public java.lang.String getERNAM(){
		return ERNAM;
	}
	public void setMANDT(java.lang.String aValue) {
		MANDT=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPROTP(java.lang.String aValue) {
		PROTP=aValue;
	}
	public void setERDAT(java.lang.String aValue) {
		ERDAT=aValue;
	}
	public void setZSEQ(java.lang.String aValue) {
		ZSEQ=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setCODAT(java.lang.String aValue) {
		CODAT=aValue;
	}
	public void setDMBTR(java.math.BigDecimal aValue) {
		DMBTR=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setERZZT(java.lang.String aValue) {
		ERZZT=aValue;
	}
	public void setERNAM(java.lang.String aValue) {
		ERNAM=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0078.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDT0078"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PROTP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PROTP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZSEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CODAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CODAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DMBTR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DMBTR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERZZT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERZZT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("PROTP",(short)1,1);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("ZSEQ",(short)1,2);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("CODAT",(short)1,8);
		ds.addColumn("DMBTR",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("ERZZT",(short)1,6);
		ds.addColumn("ERNAM",(short)1,12);
		return ds;
	}

}