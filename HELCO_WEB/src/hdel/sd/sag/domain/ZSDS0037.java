package hdel.sd.sag.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0037   
		implements java.io.Serializable {

	public ZSDS0037() {
	}
	public java.lang.String VBELN;
	public java.lang.String KUNNRT;
	public java.lang.String BSTKD;
	public java.math.BigDecimal NETWR;
	public java.lang.String ZQNTY;
	public java.lang.String VKBURT;
	public java.lang.String VKGRPT;
	public java.lang.String ZKUNNRT;
	public java.lang.String LIFNR;
	public java.lang.String KUNRZ1T;
	public java.lang.String ZFOREC;
	public java.lang.String FOREC;
	public java.lang.String COMMI_R;
	public java.math.BigDecimal COMMI;
	public java.math.BigDecimal TAMPA;
	public java.lang.String DATPA;
	public java.math.BigDecimal MIPAY;
	public java.math.BigDecimal REAMT;
	public java.lang.String WAERK;
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getKUNNRT(){
		return KUNNRT;
	}
	public java.lang.String getBSTKD(){
		return BSTKD;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.lang.String getZQNTY(){
		return ZQNTY;
	}
	public java.lang.String getVKBURT(){
		return VKBURT;
	}
	public java.lang.String getVKGRPT(){
		return VKGRPT;
	}
	public java.lang.String getZKUNNRT(){
		return ZKUNNRT;
	}
	public java.lang.String getLIFNR(){
		return LIFNR;
	}
	public java.lang.String getKUNRZ1T(){
		return KUNRZ1T;
	}
	public java.lang.String getZFOREC(){
		return ZFOREC;
	}
	public java.lang.String getFOREC(){
		return FOREC;
	}
	public java.lang.String getCOMMI_R(){
		return COMMI_R;
	}
	public java.math.BigDecimal getCOMMI(){
		return COMMI;
	}
	public java.math.BigDecimal getTAMPA(){
		return TAMPA;
	}
	public java.lang.String getDATPA(){
		return DATPA;
	}
	public java.math.BigDecimal getMIPAY(){
		return MIPAY;
	}
	public java.math.BigDecimal getREAMT(){
		return REAMT;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setKUNNRT(java.lang.String aValue) {
		KUNNRT=aValue;
	}
	public void setBSTKD(java.lang.String aValue) {
		BSTKD=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setZQNTY(java.lang.String aValue) {
		ZQNTY=aValue;
	}
	public void setVKBURT(java.lang.String aValue) {
		VKBURT=aValue;
	}
	public void setVKGRPT(java.lang.String aValue) {
		VKGRPT=aValue;
	}
	public void setZKUNNRT(java.lang.String aValue) {
		ZKUNNRT=aValue;
	}
	public void setLIFNR(java.lang.String aValue) {
		LIFNR=aValue;
	}
	public void setKUNRZ1T(java.lang.String aValue) {
		KUNRZ1T=aValue;
	}
	public void setZFOREC(java.lang.String aValue) {
		ZFOREC=aValue;
	}
	public void setFOREC(java.lang.String aValue) {
		FOREC=aValue;
	}
	public void setCOMMI_R(java.lang.String aValue) {
		COMMI_R=aValue;
	}
	public void setCOMMI(java.math.BigDecimal aValue) {
		COMMI=aValue;
	}
	public void setTAMPA(java.math.BigDecimal aValue) {
		TAMPA=aValue;
	}
	public void setDATPA(java.lang.String aValue) {
		DATPA=aValue;
	}
	public void setMIPAY(java.math.BigDecimal aValue) {
		MIPAY=aValue;
	}
	public void setREAMT(java.math.BigDecimal aValue) {
		REAMT=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0037.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0037"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VBELN");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VBELN"));
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
		elemField.setFieldName("NETWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQNTY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBURT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBURT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKGRPT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKGRPT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZKUNNRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZKUNNRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LIFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LIFNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KUNRZ1T");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KUNRZ1T"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZFOREC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZFOREC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FOREC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "FOREC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("COMMI_R");
		elemField.setXmlName(new javax.xml.namespace.QName("", "COMMI_R"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("COMMI");
		elemField.setXmlName(new javax.xml.namespace.QName("", "COMMI"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TAMPA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TAMPA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DATPA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DATPA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MIPAY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MIPAY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REAMT"));
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
		ds.addColumn("VBELN",(short)1,10);
		ds.addColumn("KUNNRT",(short)1,30);
		ds.addColumn("BSTKD",(short)1,35);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("ZQNTY",(short)1,3);
		ds.addColumn("VKBURT",(short)1,20);
		ds.addColumn("VKGRPT",(short)1,20);
		ds.addColumn("ZKUNNRT",(short)1,20);
		ds.addColumn("LIFNR",(short)1,10);
		ds.addColumn("KUNRZ1T",(short)1,20);
		ds.addColumn("ZFOREC",(short)1,1);
		ds.addColumn("FOREC",(short)1,1);
		ds.addColumn("COMMI_R",(short)1,1);
		ds.addColumn("COMMI",(short)4,13);
		ds.addColumn("TAMPA",(short)4,13);
		ds.addColumn("DATPA",(short)1,8);
		ds.addColumn("MIPAY",(short)4,13);
		ds.addColumn("REAMT",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}