package hdel.sd.scl.domain;
import com.tobesoft.platform.data.Dataset;


/**
 * 수금상세정보_상세정보 (ZSDS0015) Serializable
 * @Comment 
 *     	- Scl0041C(수금상세정보 조회) 에서 사용
 *     	
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */  


public class ZSDS0015   
		implements java.io.Serializable {

	public ZSDS0015() {
	}
	public java.lang.String PSPID;
	public java.lang.String MLBEZ;
	public java.lang.String ZTERM;
	public java.lang.String MSTXT;
	public java.lang.String BILLRT;
	public java.lang.String PLDAT;
	public java.lang.String FKDAT;
	public java.lang.String BUDAT;
	public java.lang.String ZFBDT;
	public java.math.BigDecimal FAKWR;
	public java.math.BigDecimal NETWR;
	public java.math.BigDecimal HWBAS;
	public java.math.BigDecimal DMBTR;
	public java.lang.String WAERK;
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getMLBEZ(){
		return MLBEZ;
	}
	public java.lang.String getZTERM(){
		return ZTERM;
	}
	public java.lang.String getMSTXT(){
		return MSTXT;
	}
	public java.lang.String getBILLRT(){
		return BILLRT;
	}
	public java.lang.String getPLDAT(){
		return PLDAT;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.lang.String getBUDAT(){
		return BUDAT;
	}
	public java.lang.String getZFBDT(){
		return ZFBDT;
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
	public java.math.BigDecimal getDMBTR(){
		return DMBTR;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setMLBEZ(java.lang.String aValue) {
		MLBEZ=aValue;
	}
	public void setZTERM(java.lang.String aValue) {
		ZTERM=aValue;
	}
	public void setMSTXT(java.lang.String aValue) {
		MSTXT=aValue;
	}
	public void setBILLRT(java.lang.String aValue) {
		BILLRT=aValue;
	}
	public void setPLDAT(java.lang.String aValue) {
		PLDAT=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setBUDAT(java.lang.String aValue) {
		BUDAT=aValue;
	}
	public void setZFBDT(java.lang.String aValue) {
		ZFBDT=aValue;
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
	public void setDMBTR(java.math.BigDecimal aValue) {
		DMBTR=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0015.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0015"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
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
		elemField.setFieldName("ZTERM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTERM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MSTXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSTXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BILLRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BILLRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLDAT"));
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
		elemField.setFieldName("BUDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BUDAT"));
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
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("MLBEZ",(short)1,5);
		ds.addColumn("ZTERM",(short)1,4);
		ds.addColumn("MSTXT",(short)1,40);
		ds.addColumn("BILLRT",(short)1,0);
		ds.addColumn("PLDAT",(short)1,0);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("BUDAT",(short)1,8);
		ds.addColumn("ZFBDT",(short)1,8);
		ds.addColumn("FAKWR",(short)4,13);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("HWBAS",(short)4,13);
		ds.addColumn("DMBTR",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}