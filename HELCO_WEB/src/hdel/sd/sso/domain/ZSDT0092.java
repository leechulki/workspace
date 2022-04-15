package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDT0092   
		implements java.io.Serializable {

	public ZSDT0092() {
	}
	public java.lang.String MANDT;
	public java.lang.String VBELN;
	public java.lang.String PSPID;
	public java.lang.String SEQ;
	public java.lang.String POSNR;
	public java.lang.String HOGI;
	public java.lang.String FKDAT;
	public java.lang.String MLBEZ;
	public java.lang.String ZTERM;
	public java.lang.String FKSAF;
	public java.lang.String FPLTR;
	public java.lang.String MSTXT;
	public java.math.BigDecimal FAKWR;
	public java.lang.String WAERK;
	public java.lang.String ERDAT;
	public java.lang.String ERZET;	
	public java.lang.String ERNAM;
	public java.lang.String AEDAT;
	public java.lang.String AEZET;	
	public java.lang.String AENAM;
	public java.lang.String getMANDT(){
		return MANDT;
	}
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getSEQ(){
		return SEQ;
	}
	public java.lang.String getPOSNR(){
		return POSNR;
	}
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getFKDAT(){
		return FKDAT;
	}
	public java.lang.String getMLBEZ(){
		return MLBEZ;
	}
	public java.lang.String getZTERM(){
		return ZTERM;
	}
	public java.lang.String getFKSAF(){
		return FKSAF;
	}
	public java.lang.String getFPLTR(){
		return FPLTR;
	}
	public java.lang.String getMSTXT(){
		return MSTXT;
	}
	public java.math.BigDecimal getFAKWR(){
		return FAKWR;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getERDAT(){
		return ERDAT;
	}
	public java.lang.String getERZET(){
		return ERZET;
	}	
	public java.lang.String getERNAM(){
		return ERNAM;
	}
	public java.lang.String getAEDAT(){
		return AEDAT;
	}
	public java.lang.String getAEZET(){
		return AEZET;
	}	
	public java.lang.String getAENAM(){
		return AENAM;
	}
	public void setMANDT(java.lang.String aValue) {
		MANDT=aValue;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setSEQ(java.lang.String aValue) {
		SEQ=aValue;
	}
	public void setPOSNR(java.lang.String aValue) {
		POSNR=aValue;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setFKDAT(java.lang.String aValue) {
		FKDAT=aValue;
	}
	public void setMLBEZ(java.lang.String aValue) {
		MLBEZ=aValue;
	}
	public void setZTERM(java.lang.String aValue) {
		ZTERM=aValue;
	}
	public void setFKSAF(java.lang.String aValue) {
		FKSAF=aValue;
	}
	public void setFPLTR(java.lang.String aValue) {
		FPLTR=aValue;
	}
	public void setMSTXT(java.lang.String aValue) {
		MSTXT=aValue;
	}
	public void setFAKWR(java.math.BigDecimal aValue) {
		FAKWR=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setERDAT(java.lang.String aValue) {
		ERDAT=aValue;
	}
	public void setERZET(java.lang.String aValue) {
		ERZET=aValue;
	}	
	public void setERNAM(java.lang.String aValue) {
		ERNAM=aValue;
	}
	public void setAEDAT(java.lang.String aValue) {
		AEDAT=aValue;
	}
	public void setAEZET(java.lang.String aValue) {
		AEZET=aValue;
	}	
	public void setAENAM(java.lang.String aValue) {
		AENAM=aValue;
	}	
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDT0092.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDT0092"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
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
		elemField.setFieldName("SEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSNR"));
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
		elemField.setFieldName("FKSAF");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKSAF"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FPLTR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FPLTR"));
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
		elemField.setFieldName("FAKWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FAKWR"));
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
		elemField.setFieldName("ERDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERZET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERZET"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ERNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ERNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AEDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AEDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AEZET");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AEZET"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AENAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AENAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("SEQ",(short)1,2);
		ds.addColumn("POSNR",(short)1,6);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("MLBEZ",(short)1,5);
		ds.addColumn("ZTERM",(short)1,4);
		ds.addColumn("FKSAF",(short)1,1);
		ds.addColumn("FPLTR",(short)1,6);
		ds.addColumn("MSTXT",(short)1,20);
		ds.addColumn("FAKWR",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("ERZET",(short)1,6);
		ds.addColumn("ERNAM",(short)1,12);
		ds.addColumn("AEDAT",(short)1,8);
		ds.addColumn("AEZET",(short)1,6);
		ds.addColumn("AENAM",(short)1,12);		
		return ds;
	}

}