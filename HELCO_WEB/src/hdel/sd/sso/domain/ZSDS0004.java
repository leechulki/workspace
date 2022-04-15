package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDS0004   
		implements java.io.Serializable {

	public ZSDS0004() {
	}
	public java.lang.String POSID;
	public java.lang.String ATYPE;
	public java.lang.String SPEC;
	public java.lang.String NUMBER;
	public java.lang.String OUTOR;
	public java.math.BigDecimal MONEY;
	public java.math.BigDecimal TPCOST;
	public java.math.BigDecimal PCOST;
	public java.lang.String WAERS;
	public java.lang.String VDATU;
	public java.lang.String NETWRT;
	public java.lang.String KWERTT;
	public java.lang.String PWERTT;
	public java.lang.String HTEXT;
	public java.lang.String REPMO;
	public java.lang.String WORMO;
	public java.lang.String getPOSID(){
		return POSID;
	}
	public java.lang.String getATYPE(){
		return ATYPE;
	}
	public java.lang.String getSPEC(){
		return SPEC;
	}
	public java.lang.String getNUMBER(){
		return NUMBER;
	}
	public java.lang.String getOUTOR(){
		return OUTOR;
	}
	public java.math.BigDecimal getMONEY(){
		return MONEY;
	}
	public java.math.BigDecimal getTPCOST(){
		return TPCOST;
	}
	public java.math.BigDecimal getPCOST(){
		return PCOST;
	}
	public java.lang.String getWAERS(){
		return WAERS;
	}
	public java.lang.String getVDATU(){
		return VDATU;
	}
	public java.lang.String getNETWRT(){
		return NETWRT;
	}
	public java.lang.String getKWERTT(){
		return KWERTT;
	}
	public java.lang.String getPWERTT(){
		return PWERTT;
	}
	public java.lang.String getHTEXT(){
		return HTEXT;
	}
	public java.lang.String getREPMO(){
		return REPMO;
	}
	public java.lang.String getWORMO(){
		return WORMO;
	}
	public void setPOSID(java.lang.String aValue) {
		POSID=aValue;
	}
	public void setATYPE(java.lang.String aValue) {
		ATYPE=aValue;
	}
	public void setSPEC(java.lang.String aValue) {
		SPEC=aValue;
	}
	public void setNUMBER(java.lang.String aValue) {
		NUMBER=aValue;
	}
	public void setOUTOR(java.lang.String aValue) {
		OUTOR=aValue;
	}
	public void setMONEY(java.math.BigDecimal aValue) {
		MONEY=aValue;
	}
	public void setTPCOST(java.math.BigDecimal aValue) {
		TPCOST=aValue;
	}
	public void setPCOST(java.math.BigDecimal aValue) {
		PCOST=aValue;
	}
	public void setWAERS(java.lang.String aValue) {
		WAERS=aValue;
	}
	public void setVDATU(java.lang.String aValue) {
		VDATU=aValue;
	}
	public void setNETWRT(java.lang.String aValue) {
		NETWRT=aValue;
	}
	public void setKWERTT(java.lang.String aValue) {
		KWERTT=aValue;
	}
	public void setPWERTT(java.lang.String aValue) {
		PWERTT=aValue;
	}
	public void setHTEXT(java.lang.String aValue) {
		HTEXT=aValue;
	}
	public void setREPMO(java.lang.String aValue) {
		REPMO=aValue;
	}
	public void setWORMO(java.lang.String aValue) {
		WORMO=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0004.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0004"));
		org.apache.axis.description.ElementDesc elemField =null;
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
		elemField.setFieldName("SPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NUMBER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NUMBER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("OUTOR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "OUTOR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MONEY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MONEY"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TPCOST");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TPCOST"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PCOST");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PCOST"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERS"));
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
		elemField.setFieldName("NETWRT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NETWRT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWERTT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWERTT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PWERTT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PWERTT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("HTEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "HTEXT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("REPMO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "REPMO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WORMO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WORMO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset[] getDataset() {
		Dataset[] ds = {new Dataset(), new Dataset()};
		for (int i=0; i<ds.length; i++) {
			ds[i].addColumn("POSID",(short)1,24);
			ds[i].addColumn("ATYPE",(short)1,5);
			ds[i].addColumn("SPEC",(short)1,30);
			ds[i].addColumn("NUMBER",(short)1,3);
			ds[i].addColumn("OUTOR",(short)1,12);
			ds[i].addColumn("MONEY",(short)4,13);
			ds[i].addColumn("TPCOST",(short)4,13);
			ds[i].addColumn("PCOST",(short)4,13);
			ds[i].addColumn("WAERS",(short)1,5);
			ds[i].addColumn("VDATU",(short)1,8);
			ds[i].addColumn("NETWRT",(short)1,16);
			ds[i].addColumn("KWERTT",(short)1,16);
			ds[i].addColumn("PWERTT",(short)1,16);
			ds[i].addColumn("HTEXT",(short)1,5);
			ds[i].addColumn("REPMO",(short)1,2);
			ds[i].addColumn("WORMO",(short)1,2);
		}
		return ds;
	}

}