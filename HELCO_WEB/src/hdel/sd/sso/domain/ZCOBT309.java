package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZCOBT309   
		implements java.io.Serializable {

	public ZCOBT309() {
	}
	public java.lang.String MANDT;
	public java.lang.String QTNUM;
	public java.lang.String QTSER;
	public java.lang.String QTSEQ;
	public java.lang.String PSPID;
	public java.lang.String POSID;
	public java.lang.String ATYPE;
	public java.lang.String GUBUN;
	public java.lang.String BLSEQ;
	public java.lang.String BLNUM;
	public java.lang.String BLNAM;
	public java.lang.String MESSG;
	public java.lang.String STATE;
	public java.lang.String getMANDT(){
		return MANDT;
	}
	public java.lang.String getQTNUM(){
		return QTNUM;
	}
	public java.lang.String getQTSER(){
		return QTSER;
	}
	public java.lang.String getQTSEQ(){
		return QTSEQ;
	}
	public java.lang.String getPSPID(){
		return PSPID;
	}
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getGUBUN(){
		return GUBUN;
	}
	public java.lang.String getBLSEQ(){
		return BLSEQ;
	}
	public java.lang.String getBLNUM(){
		return BLNUM;
	}
	public java.lang.String getBLNAM(){
		return BLNAM;
	}
	public java.lang.String getMESSG(){
		return MESSG;
	}
	public java.lang.String getSTATE(){
		return STATE;
	}
	public void setMANDT(java.lang.String aValue) {
		MANDT=aValue;
	}
	public void setQTNUM(java.lang.String aValue) {
		QTNUM=aValue;
	}
	public void setQTSER(java.lang.String aValue) {
		QTSER=aValue;
	}
	public void setQTSEQ(java.lang.String aValue) {
		QTSEQ=aValue;
	}
	public void setPSPID(java.lang.String aValue) {
		PSPID=aValue;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setGUBUN(java.lang.String aValue) {
		GUBUN=aValue;
	}
	public void setBLSEQ(java.lang.String aValue) {
		BLSEQ=aValue;
	}
	public void setBLNUM(java.lang.String aValue) {
		BLNUM=aValue;
	}
	public void setBLNAM(java.lang.String aValue) {
		BLNAM=aValue;
	}
	public void setMESSG(java.lang.String aValue) {
		MESSG=aValue;
	}
	public void setSTATE(java.lang.String aValue) {
		STATE=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZCOBT309.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZCOBT309"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MANDT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MANDT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTSER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("QTSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSEQ"));
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
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATYPE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATYPE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUN"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLSEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLNUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLNUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BLNAM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BLNAM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MESSG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MESSG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT",(short)1,3);
		ds.addColumn("QTNUM",(short)1,10);
		ds.addColumn("QTSER",(short)1,3);
		ds.addColumn("QTSEQ",(short)1,3);
		ds.addColumn("PSPID",(short)1,24);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("ATYPE",(short)1,30);
		ds.addColumn("GUBUN",(short)1,1);
		ds.addColumn("BLSEQ",(short)1,6);
		ds.addColumn("BLNUM",(short)1,10);
		ds.addColumn("BLNAM",(short)1,50);
		ds.addColumn("MESSG",(short)1,50);
		ds.addColumn("STATE",(short)1,1);
		return ds;
	}

}