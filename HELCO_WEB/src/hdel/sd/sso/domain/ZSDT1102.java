package hdel.sd.sso.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZSDT1102 implements java.io.Serializable {

	public ZSDT1102() {
	}

	private String FLAG;
	private String MANDT;
	private String QTNUM;
	private String QTSER;
	private String VBELN;
	private String POSNR;
	private String MLBEZ;
	private String FKDAT;
	private int    SEQ;
	private String ZTERM;
	private BigDecimal FAKWR;
	private String FAKSP;
	private String FKSAF;
	private String WAERK;

	public String getFLAG() {
	    return FLAG;	
	}

	public String getMANDT() {
	    return MANDT;	
	}

	public String getQTNUM() {
	    return QTNUM;	
	}

	public String getQTSER() {
	    return QTSER;	
	}
	
	public String getVBELN(){
		return VBELN;
	}

	public String getPOSNR(){
		return POSNR;
	}

	public String getMLBEZ(){
		return MLBEZ;
	}

	public String getFKDAT(){
		return FKDAT;
	}

	public int getSEQ(){
		return SEQ;
	}
	
	public String getZTERM(){
		return ZTERM;
	}

	public BigDecimal getFAKWR(){
		return FAKWR;
	}

	public String getFAKSP(){
		return FAKSP;
	}

	public String getFKSAF(){
		return FKSAF;
	}

	public String getWAERK(){
		return WAERK;
	}

	public void setFLAG(String aValue) {
	    FLAG=aValue;
	}

	public void setMANDT(String aValue) {
		MANDT=aValue;
	}
	
	public void setQTNUM(String aValue) {
		QTNUM=aValue;
	}
	
	public void setQTSER(String aValue) {
		QTSER=aValue;
	}
	
	public void setVBELN(String aValue) {
		VBELN=aValue;
	}

	public void setPOSNR(String aValue) {
		POSNR=aValue;
	}

	public void setMLBEZ(String aValue) {
		MLBEZ=aValue;
	}

	public void setFKDAT(String aValue) {
		FKDAT=aValue;
	}
	
	public void setSEQ(int aValue) {
		SEQ=aValue;
	}

	public void setZTERM(String aValue) {
		ZTERM=aValue;
	}

	public void setFAKWR(BigDecimal aValue) {
		FAKWR=aValue;
	}

	public void setFAKSP(String aValue) {
		FAKSP=aValue;
	}

	public void setFKSAF(String aValue) {
		FKSAF=aValue;
	}

	public void setWAERK(String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0062.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0062"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FLAG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FLAG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField.setFieldName("QTNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField.setFieldName("QTSER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MLBEZ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MLBEZ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZTERM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZTERM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FAKWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FAKWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FAKSP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FAKSP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FKSAF");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FKSAF"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();

		ds.addColumn("FLAG",(short)1,1);
		ds.addColumn("MANDT",(short)1,9);
		ds.addColumn("QTNUM",(short)1,30);
		ds.addColumn("QTSER",(short)1,5);
		
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("POSNR",(short)1,6);
		ds.addColumn("MLBEZ",(short)1,5);
		ds.addColumn("FKDAT",(short)1,8);
		ds.addColumn("ZTERM",(short)1,4);
		ds.addColumn("FAKWR",(short)4,15);
		ds.addColumn("FAKSP",(short)1,2);
		ds.addColumn("FKSAF",(short)1,1);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}