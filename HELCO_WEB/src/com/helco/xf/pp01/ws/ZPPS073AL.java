package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS073AL   
		implements java.io.Serializable {

	public ZPPS073AL() {
	}
	
	public java.lang.String GUBUNB;
	public java.lang.String ZMONTH;
	public java.lang.String ZDAY;
	public java.lang.String POSID;
	public java.lang.String POST1;
	public java.lang.String ITEMNO;
	public java.math.BigDecimal KWMENG;
	public java.lang.String ATYP;
	public java.lang.String ETM;
	public java.lang.String ILDAT;
	public java.lang.String SUGM;
	public java.lang.String SPEC;
	

	public java.lang.String getGUBUNB() {
		return GUBUNB;
	}

	public void setGUBUNB(java.lang.String gUBUNB) {
		GUBUNB = gUBUNB;
	}

	public java.lang.String getZMONTH() {
		return ZMONTH;
	}

	public void setZMONTH(java.lang.String zMONTH) {
		ZMONTH = zMONTH;
	}

	public java.lang.String getZDAY() {
		return ZDAY;
	}

	public void setZDAY(java.lang.String zDAY) {
		ZDAY = zDAY;
	}

	public java.lang.String getPOSID() {
		return POSID;
	}

	public void setPOSID(java.lang.String pOSID) {
		POSID = pOSID;
	}

	public java.lang.String getPOST1() {
		return POST1;
	}

	public void setPOST1(java.lang.String pOST1) {
		POST1 = pOST1;
	}

	public java.lang.String getITEMNO() {
		return ITEMNO;
	}

	public void setITEMNO(java.lang.String iTEMNO) {
		ITEMNO = iTEMNO;
	}

	public java.math.BigDecimal getKWMENG() {
		return KWMENG;
	}

	public void setKWMENG(java.math.BigDecimal kWMENG) {
		KWMENG = kWMENG;
	}

	public java.lang.String getATYP() {
		return ATYP;
	}

	public void setATYP(java.lang.String aTYP) {
		ATYP = aTYP;
	}

	public java.lang.String getETM() {
		return ETM;
	}

	public void setETM(java.lang.String eTM) {
		ETM = eTM;
	}

	public java.lang.String getILDAT() {
		return ILDAT;
	}

	public void setILDAT(java.lang.String iLDAT) {
		ILDAT = iLDAT;
	}

	public java.lang.String getSUGM() {
		return SUGM;
	}

	public void setSUGM(java.lang.String sUGM) {
		SUGM = sUGM;
	}

	public java.lang.String getSPEC() {
		return SPEC;
	}

	public void setSPEC(java.lang.String sPEC) {
		SPEC = sPEC;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS073AL.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS073AL"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GUBUNB");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUBUNB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZMONTH");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZMONTH"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZDAY");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZDAY"));
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
		elemField.setFieldName("POST1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POST1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);		
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWMENG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWMENG"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ATYP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ATYP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ETM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ETM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ILDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ILDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SUGM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SUGM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);		
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("GUBUNB",(short)1,20);
		ds.addColumn("ZMONTH",(short)1,2);
		ds.addColumn("ZDAY",(short)1,2);
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("KWMENG",(short)4,13);
		ds.addColumn("ATYP",(short)1,30);
		ds.addColumn("ETM",(short)1,30);
		ds.addColumn("ILDAT",(short)1,8);
		ds.addColumn("SUGM",(short)1,1);
		ds.addColumn("SPEC",(short)1,30);
		return ds;
	}

}