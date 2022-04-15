package hdel.sd.scl.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0015E   
		implements java.io.Serializable {

	public ZSDS0015E() {
	}
	public java.lang.String VBELN;
	public java.lang.String HOGI;
	public java.lang.String FKDAT;
	public java.math.BigDecimal NETWR;
	public java.math.BigDecimal NTVAT;
	public java.math.BigDecimal VAT;
	public java.lang.String BUDAT;
	public java.math.BigDecimal DMBTR;
	public java.math.BigDecimal HWBAS;
	public java.math.BigDecimal HWSTE;
	public java.lang.String BELNR;
	public java.lang.String AUGBL;
	public java.lang.String WDATE;
	public java.lang.String ZFBDT;
	public java.lang.String WAERK;
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.math.BigDecimal getNTVAT(){
		return NTVAT;
	}
	public java.math.BigDecimal getVAT(){
		return VAT;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.math.BigDecimal getDMBTR(){
		return DMBTR;
	}
	public java.math.BigDecimal getHWBAS(){
		return HWBAS;
	}
	public java.math.BigDecimal getHWSTE(){
		return HWSTE;
	}
	public java.lang.String getBELNR(){
		return BELNR;
	}
	public java.lang.String getAUGBL(){
		return AUGBL;
	}
	public java.lang.String getWDATE(){
		return WDATE;
	}
	public java.lang.String getZFBDT(){
		return ZFBDT;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setNTVAT(java.math.BigDecimal aValue) {
		NTVAT=aValue;
	}
	public void setVAT(java.math.BigDecimal aValue) {
		VAT=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setDMBTR(java.math.BigDecimal aValue) {
		DMBTR=aValue;
	}
	public void setHWBAS(java.math.BigDecimal aValue) {
		HWBAS=aValue;
	}
	public void setHWSTE(java.math.BigDecimal aValue) {
		HWSTE=aValue;
	}
	public void setBELNR(java.lang.String aValue) {
		BELNR=aValue;
	}
	public void setAUGBL(java.lang.String aValue) {
		AUGBL=aValue;
	}
	public void setWDATE(java.lang.String aValue) {
		WDATE=aValue;
	}
	public void setZFBDT(java.lang.String aValue) {
		ZFBDT=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0015E.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0015E"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HOGI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HOGI"));
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
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NTVAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NTVAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BUDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUDAT"));
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
		elemField.setFieldName("HWBAS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HWBAS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HWSTE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HWSTE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BELNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BELNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUGBL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUGBL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WDATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WDATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZFBDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZFBDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("NTVAT",(short)4,13);
		ds.addColumn("VAT",(short)4,13);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("DMBTR",(short)4,13);
		ds.addColumn("HWBAS",(short)4,13);
		ds.addColumn("HWSTE",(short)4,13);
		ds.addColumn("BELNR",(short)1,10);
		ds.addColumn("AUGBL",(short)1,10);
		ds.addColumn("WDATE",(short)1,8);
		ds.addColumn("ZFBDT",(short)1,8);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}