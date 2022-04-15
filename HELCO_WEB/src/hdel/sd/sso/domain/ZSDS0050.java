package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0050   
		implements java.io.Serializable {

	public ZSDS0050() {
	}
	public java.lang.String VBELN;
	public java.lang.String POSNR;
	public java.lang.String ZZPJT_ID;
	public java.lang.String ZZPJT_NM;
	public java.lang.String MATNR;
	public java.lang.String HOGI;
	public java.lang.String CUOBJ;
	public java.lang.String TP_DATE1;
	public java.lang.String TP_DATE2;
	public java.lang.String TP_DATE3;
	public java.lang.String TP_DATE4;
	public java.lang.String CR_FLAG;
	public java.lang.String CR_FLAG2;
	public java.lang.String CR_FLAG3;
	public java.lang.String ERNAM;
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPOSNR(){
		return POSNR;
	}
	public java.lang.String getZZPJT_ID(){
		return ZZPJT_ID;
	}
	public java.lang.String getZZPJT_NM(){
		return ZZPJT_NM;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getCUOBJ(){
		return CUOBJ;
	}
	public java.lang.String getTP_DATE1(){
		return TP_DATE1;
	}
	public java.lang.String getTP_DATE2(){
		return TP_DATE2;
	}
	public java.lang.String getTP_DATE3(){
		return TP_DATE3;
	}
	public java.lang.String getTP_DATE4(){
		return TP_DATE4;
	}
	public java.lang.String getCR_FLAG(){
		return CR_FLAG;
	}
	public java.lang.String getCR_FLAG2(){
		return CR_FLAG2;
	}
	public java.lang.String getCR_FLAG3(){
		return CR_FLAG3;
	}
	public java.lang.String getERNAM(){
		return ERNAM;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPOSNR(java.lang.String aValue) {
		POSNR=aValue;
	}
	public void setZZPJT_ID(java.lang.String aValue) {
		ZZPJT_ID=aValue;
	}
	public void setZZPJT_NM(java.lang.String aValue) {
		ZZPJT_NM=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setCUOBJ(java.lang.String aValue) {
		CUOBJ=aValue;
	}
	public void setTP_DATE1(java.lang.String aValue) {
		TP_DATE1=aValue;
	}
	public void setTP_DATE2(java.lang.String aValue) {
		TP_DATE2=aValue;
	}
	public void setTP_DATE3(java.lang.String aValue) {
		TP_DATE3=aValue;
	}
	public void setTP_DATE4(java.lang.String aValue) {
		TP_DATE4=aValue;
	}
	public void setCR_FLAG(java.lang.String aValue) {
		CR_FLAG=aValue;
	}
	public void setCR_FLAG2(java.lang.String aValue) {
		CR_FLAG2=aValue;
	}
	public void setCR_FLAG3(java.lang.String aValue) {
		CR_FLAG3=aValue;
	}
	public void setERNAM(java.lang.String aValue) {
		ERNAM=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0050.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0050"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
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
		elemField.setFieldName("ZZPJT_ID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPJT_ID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZPJT_NM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZZPJT_NM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MATNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MATNR"));
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
		elemField.setFieldName("CUOBJ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CUOBJ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_DATE1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_DATE1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_DATE2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_DATE2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_DATE3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_DATE3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TP_DATE4");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TP_DATE4"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CR_FLAG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CR_FLAG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CR_FLAG2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CR_FLAG2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CR_FLAG3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CR_FLAG3"));
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
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("POSNR",(short)1,6);
		ds.addColumn("ZZPJT_ID",(short)1,24);
		ds.addColumn("ZZPJT_NM",(short)1,35);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("CUOBJ",(short)1,18);
		ds.addColumn("TP_DATE1",(short)1,8);
		ds.addColumn("TP_DATE2",(short)1,8);
		ds.addColumn("TP_DATE3",(short)1,8);
		ds.addColumn("TP_DATE4",(short)1,8);
		ds.addColumn("CR_FLAG",(short)1,1);
		ds.addColumn("CR_FLAG2",(short)1,1);
		ds.addColumn("CR_FLAG3",(short)1,1);
		ds.addColumn("ERNAM",(short)1,12);
		return ds;
	}

}