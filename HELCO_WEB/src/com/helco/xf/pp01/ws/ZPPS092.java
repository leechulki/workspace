package com.helco.xf.pp01.ws;
import com.tobesoft.platform.data.Dataset;
public class ZPPS092   
		implements java.io.Serializable {

	public ZPPS092() {
	}
	
	  public java.lang.String POSID  ;
	  public java.lang.String NAME1  ;
	  public java.lang.String ITEMNO ;
      public java.lang.String MATNR  ;
	  public java.lang.String BCDAT  ;
	  public java.lang.String AUFNR  ;
	  public java.lang.String LIFNR  ;
	  public java.lang.String BIGO  ;
	  public java.lang.String POST1 ;
	  public java.lang.String MSPEC ;
	  public java.lang.String JJ    ;
	  public java.lang.String MODEL ;
	  public java.lang.String MEINS ;
	  public java.lang.String GMENGE;
	  public java.lang.String GIDAT ;
	  public java.lang.String BMENGE;
	  public java.lang.String SEQ   ;
	  public java.lang.String BARCODE;
	  public java.lang.String SSCDAT;


	public java.lang.String getPOSID() {
		return POSID;
	}

	public void setPOSID(java.lang.String pOSID) {
		POSID = pOSID;
	}

	public java.lang.String getNAME1() {
		return NAME1;
	}

	public void setNAME1(java.lang.String nAME1) {
		NAME1 = nAME1;
	}

	public java.lang.String getITEMNO() {
		return ITEMNO;
	}

	public void setITEMNO(java.lang.String iTEMNO) {
		ITEMNO = iTEMNO;
	}

	public java.lang.String getMATNR() {
		return MATNR;
	}

	public void setMATNR(java.lang.String mATNR) {
		MATNR = mATNR;
	}

	public java.lang.String getBCDAT() {
		return BCDAT;
	}

	public void setBCDAT(java.lang.String bCDAT) {
		BCDAT = bCDAT;
	}

	public java.lang.String getAUFNR() {
		return AUFNR;
	}

	public void setAUFNR(java.lang.String aUFNR) {
		AUFNR = aUFNR;
	}

	public java.lang.String getLIFNR() {
		return LIFNR;
	}

	public void setLIFNR(java.lang.String lIFNR) {
		LIFNR = lIFNR;
	}

	public java.lang.String getBIGO() {
		return BIGO;
	}

	public void setBIGO(java.lang.String bIGO) {
		BIGO = bIGO;
	}

	public java.lang.String getPOST1() {
		return POST1;
	}

	public void setPOST1(java.lang.String pOST1) {
		POST1 = pOST1;
	}

	public java.lang.String getMSPEC() {
		return MSPEC;
	}

	public void setMSPEC(java.lang.String mSPEC) {
		MSPEC = mSPEC;
	}

	public java.lang.String getJJ() {
		return JJ;
	}

	public void setJJ(java.lang.String jJ) {
		JJ = jJ;
	}

	public java.lang.String getMODEL() {
		return MODEL;
	}

	public void setMODEL(java.lang.String mODEL) {
		MODEL = mODEL;
	}

	public java.lang.String getMEINS() {
		return MEINS;
	}

	public void setMEINS(java.lang.String mEINS) {
		MEINS = mEINS;
	}	

	public java.lang.String getBMENGE() {
		return BMENGE;
	}

	public void setBMENGE(java.lang.String bMENGE) {
		BMENGE = bMENGE;
	}

	public java.lang.String getSEQ() {
		return SEQ;
	}

	public void setSEQ(java.lang.String sEQ) {
		SEQ = sEQ;
	}

	public java.lang.String getGMENGE() {
		return GMENGE;
	}

	public void setGMENGE(java.lang.String gMENGE) {
		GMENGE = gMENGE;
	}

	public java.lang.String getGIDAT() {
		return GIDAT;
	}

	public void setGIDAT(java.lang.String gIDAT) {
		GIDAT = gIDAT;
	}

	public java.lang.String getBARCODE() {
		return BARCODE;
	}

	public void setBARCODE(java.lang.String bARCODE) {
		BARCODE = bARCODE;
	}
	
	public java.lang.String getSSCDAT() {
		return SSCDAT;
	}

	public void setSSCDAT(java.lang.String sSCDAT) {
		SSCDAT = sSCDAT;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZPPS092.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZPPS092"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("POSID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "POSID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NAME1");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ITEMNO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ITEMNO"));
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
		elemField.setFieldName("BCDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BCDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("AUFNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AUFNR"));
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
		elemField.setFieldName("BIGO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BIGO"));
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
		elemField.setFieldName("MSPEC");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MSPEC"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JJ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "JJ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MODEL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MODEL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("MEINS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "MEINS"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GMENGE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BMENGE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BMENGE"));
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
		elemField.setFieldName("GIDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GIDAT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BARCODE");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BARCODE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SSCDAT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BARCODE"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("POSID",(short)1,24);
		ds.addColumn("NAME1",(short)1,35);		
		ds.addColumn("ITEMNO",(short)1,9);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("BCDAT",(short)1,8);
		ds.addColumn("GIDAT",(short)1,8);
		ds.addColumn("AUFNR",(short)1,12);
		ds.addColumn("LIFNR",(short)1,10);
		ds.addColumn("GMENGE",(short)3,13);
		ds.addColumn("BMENGE",(short)3,13);
		ds.addColumn("MEINS",(short)1,3);
		ds.addColumn("BIGO",(short)1,50);
		ds.addColumn("POST1",(short)1,40);
		ds.addColumn("MSPEC",(short)1,60);
		ds.addColumn("JJ",(short)1,30);
		ds.addColumn("MODEL",(short)1,30);
		ds.addColumn("SEQ",(short)1,3);
		ds.addColumn("BARCODE",(short)1,19);
		ds.addColumn("SSCDAT",(short)1,8);
		
		
		return ds;
	}

}