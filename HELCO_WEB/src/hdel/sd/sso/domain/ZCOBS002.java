package hdel.sd.sso.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZCOBS002 implements java.io.Serializable {

	public ZCOBS002() {
	}

	private String QTNUM;
	private int QTSER;
	private int QTSEQ;
	private String PSPID;
	private String POSID;
	private String GUBUN;
	private String BLSEQ;
	private String BLNUM;
	private String BLNAM;
	private String MESSG;

	public String getQTNUM(){
		return QTNUM;
	}

	public int getQTSER(){
		return QTSER;
	}

	public int getQTSEQ(){
		return QTSEQ;
	}

	public String getPSPID(){
		return PSPID;
	}

	public String getPOSID(){
		return POSID;
	}

	public String getGUBUN(){
		return GUBUN;
	}

	public String getBLSEQ(){
		return BLSEQ;
	}

	public String getBLNUM(){
		return BLNUM;
	}

	public String getBLNAM(){
		return BLNAM;
	}

	public String getMESSG(){
		return MESSG;
	}

	public void setQTNUM(String aValue) {
		QTNUM=aValue;
	}

	public void setQTSER(int aValue) {
		QTSER=aValue;
	}

	public void setQTSEQ(int aValue) {
		QTSEQ=aValue;
	}

	public void setPSPID(String aValue) {
		PSPID=aValue;
	}

	public void setPOSID(String aValue) {
		POSID=aValue;
	}

	public void setGUBUN(String aValue) {
		GUBUN=aValue;
	}

	public void setBLSEQ(String aValue) {
		BLSEQ=aValue;
	}

	public void setBLNUM(String aValue) {
		BLNUM=aValue;
	}

	public void setBLNAM(String aValue) {
		BLNAM=aValue;
	}

	public void setMESSG(String aValue) {
		MESSG=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCOBS002.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCOBS002"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTSER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PSPID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PSPID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLSEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MESSG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MESSG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();

		ds.addColumn("QTNUM",(short)1,10);
		ds.addColumn("QTSER",(short)2,10);
		ds.addColumn("QTSEQ",(short)2,10);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("GUBUN",(short)1,1);
		ds.addColumn("BLSEQ",(short)1,6);
		ds.addColumn("BLNUM",(short)1,10);
		ds.addColumn("BLNAM",(short)1,50);
		ds.addColumn("MESSG",(short)1,50);
		return ds;
	}

}