package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0012   
		implements java.io.Serializable {

	public ZSDS0012() {
	}
	public java.lang.String VBELN;
	public java.lang.String POSNR;
	public java.lang.String MATNR;
	public java.lang.String HOGI;
	public java.lang.String CUOBJ;
	public java.lang.String ARKTX;
	public java.lang.String VDATU;
	public java.lang.String KUNNRT;
	public java.lang.String BSTKD;
	public java.lang.String TELEP;
	public java.lang.String KUNNRTZ2;
	public java.lang.String KUNNRTZ3;
	public java.lang.String DATUM;
	public java.lang.String UZEIT;
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPOSNR(){
		return POSNR;
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
	public java.lang.String getARKTX(){
		return ARKTX;
	}
	public java.lang.String getVDATU(){
		return VDATU;
	}
	public java.lang.String getKUNNRT(){
		return KUNNRT;
	}
	public java.lang.String getBSTKD(){
		return BSTKD;
	}
	public java.lang.String getTELEP(){
		return TELEP;
	}
	public java.lang.String getKUNNRTZ2(){
		return KUNNRTZ2;
	}
	public java.lang.String getKUNNRTZ3(){
		return KUNNRTZ3;
	}
	public java.lang.String getDATUM(){
		return DATUM;
	}
	public java.lang.String getUZEIT(){
		return UZEIT;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPOSNR(java.lang.String aValue) {
		POSNR=aValue;
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
	public void setARKTX(java.lang.String aValue) {
		ARKTX=aValue;
	}
	public void setVDATU(java.lang.String aValue) {
		VDATU=aValue;
	}
	public void setKUNNRT(java.lang.String aValue) {
		KUNNRT=aValue;
	}
	public void setBSTKD(java.lang.String aValue) {
		BSTKD=aValue;
	}
	public void setTELEP(java.lang.String aValue) {
		TELEP=aValue;
	}
	public void setKUNNRTZ2(java.lang.String aValue) {
		KUNNRTZ2=aValue;
	}
	public void setKUNNRTZ3(java.lang.String aValue) {
		KUNNRTZ3=aValue;
	}
	public void setDATUM(java.lang.String aValue) {
		DATUM=aValue;
	}
	public void setUZEIT(java.lang.String aValue) {
		UZEIT=aValue;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0012.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0012"));
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
		elemField.setFieldName("ARKTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARKTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VDATU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VDATU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BSTKD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTKD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TELEP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TELEP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNRTZ2");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNRTZ2"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNNRTZ3");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNRTZ3"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DATUM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DATUM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("UZEIT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UZEIT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("POSNR",(short)1,6);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("CUOBJ",(short)1,18);
		ds.addColumn("ARKTX",(short)1,40);
		ds.addColumn("VDATU",(short)1,8);
		ds.addColumn("KUNNRT",(short)1,40);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("TELEP",(short)1,20);
		ds.addColumn("KUNNRTZ2",(short)1,20);
		ds.addColumn("KUNNRTZ3",(short)1,20);
		ds.addColumn("DATUM",(short)1,8);
		ds.addColumn("UZEIT",(short)1,6);
		return ds;
	}

}