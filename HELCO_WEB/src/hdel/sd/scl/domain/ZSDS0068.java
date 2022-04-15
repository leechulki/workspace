package hdel.sd.scl.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0068   
		implements java.io.Serializable {

	public ZSDS0068() {
	}
	public java.lang.String VKBUR;
	public java.lang.String ZMANT;
	public java.lang.String LAND1;
	public java.lang.String ZZPJT_ID;
	public java.lang.String BSTNK;
	public java.lang.String ZQNTY;
	public java.math.BigDecimal NETWR;
	public java.lang.String IRATE;
	public java.lang.String GISUNG;
	public java.math.BigDecimal CHEONG;
	public java.lang.String CHEONGT;
	public java.math.BigDecimal CASH;
	public java.math.BigDecimal BILL;
	public java.lang.String BILLT;
	public java.math.BigDecimal MISU;
	public java.math.BigDecimal GSCG;
	public java.lang.String WAERK;
	public java.lang.String getVKBUR(){
		return VKBUR;
	}
	public java.lang.String getZMANT(){
		return ZMANT;
	}
	public java.lang.String getLAND1(){
		return LAND1;
	}
	public java.lang.String getZZPJT_ID(){
		return ZZPJT_ID;
	}
	public java.lang.String getBSTNK(){
		return BSTNK;
	}
	public java.lang.String getZQNTY(){
		return ZQNTY;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.lang.String getIRATE(){
		return IRATE;
	}
	public java.lang.String getGISUNG(){
		return GISUNG;
	}
	public java.math.BigDecimal getCHEONG(){
		return CHEONG;
	}
	public java.lang.String getCHEONGT(){
		return CHEONGT;
	}
	public java.math.BigDecimal getCASH(){
		return CASH;
	}
	public java.math.BigDecimal getBILL(){
		return BILL;
	}
	public java.lang.String getBILLT(){
		return BILLT;
	}
	public java.math.BigDecimal getMISU(){
		return MISU;
	}
	public java.math.BigDecimal getGSCG(){
		return GSCG;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public void setVKBUR(java.lang.String aValue) {
		VKBUR=aValue;
	}
	public void setZMANT(java.lang.String aValue) {
		ZMANT=aValue;
	}
	public void setLAND1(java.lang.String aValue) {
		LAND1=aValue;
	}
	public void setZZPJT_ID(java.lang.String aValue) {
		ZZPJT_ID=aValue;
	}
	public void setBSTNK(java.lang.String aValue) {
		BSTNK=aValue;
	}
	public void setZQNTY(java.lang.String aValue) {
		ZQNTY=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setIRATE(java.lang.String aValue) {
		IRATE=aValue;
	}
	public void setGISUNG(java.lang.String aValue) {
		GISUNG=aValue;
	}
	public void setCHEONG(java.math.BigDecimal aValue) {
		CHEONG=aValue;
	}
	public void setCHEONGT(java.lang.String aValue) {
		CHEONGT=aValue;
	}
	public void setCASH(java.math.BigDecimal aValue) {
		CASH=aValue;
	}
	public void setBILL(java.math.BigDecimal aValue) {
		BILL=aValue;
	}
	public void setBILLT(java.lang.String aValue) {
		BILLT=aValue;
	}
	public void setMISU(java.math.BigDecimal aValue) {
		MISU=aValue;
	}
	public void setGSCG(java.math.BigDecimal aValue) {
		GSCG=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0068.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS000068"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("VKBUR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "VKBUR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZMANT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZMANT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("LAND1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LAND1"));
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
		elemField.setFieldName("BSTNK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BSTNK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZQNTY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZQNTY"));
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
		elemField.setFieldName("IRATE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IRATE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GISUNG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GISUNG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHEONG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHEONG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CHEONGT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CHEONGT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CASH");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CASH"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BILL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BILL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BILLT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BILLT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MISU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MISU"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GSCG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GSCG"));
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
		ds.addColumn("VKBUR",(short)1,4);
		ds.addColumn("ZMANT",(short)1,20);
		ds.addColumn("LAND1",(short)1,3);
		ds.addColumn("ZZPJT_ID",(short)1,24);
		ds.addColumn("BSTNK",(short)1,50);
		ds.addColumn("ZQNTY",(short)1,0);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("IRATE",(short)1,0);
		ds.addColumn("GISUNG",(short)1,0);
		ds.addColumn("CHEONG",(short)4,13);
		ds.addColumn("CHEONGT",(short)1,10);
		ds.addColumn("CASH",(short)4,13);
		ds.addColumn("BILL",(short)4,13);
		ds.addColumn("BILLT",(short)1,50);
		ds.addColumn("MISU",(short)4,13);
		ds.addColumn("GSCG",(short)4,13);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}