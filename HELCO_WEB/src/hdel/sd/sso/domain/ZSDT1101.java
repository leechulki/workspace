package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;
public class ZSDT1101   
		implements java.io.Serializable {

	public ZSDT1101() {
	}
	public String FLAG;
	public String MANDT;
	public String QTNUM;
	public String QTSER;
	public java.lang.String VBELN;
	public java.lang.String POSNR;
	public java.lang.String QTSEQ;
	public java.lang.String MATNR;
	public java.lang.String ARKTX; 
	public java.lang.String KWMENG;
	public java.lang.String HOGI;
	public java.lang.String WERKS;
	public java.math.BigDecimal NETWR;
	public java.math.BigDecimal WAVWR;
	public java.lang.String EDATU;
	public java.lang.String REPMO;
	public java.lang.String GUAMO;
	public java.lang.String WORMO;
	public java.lang.String WAERK;
	public java.lang.String CONDA;
	public java.lang.String STADA;
	public java.lang.String ZRMDA;
	public java.lang.String WWBLD;
	
	// 2020 브랜드
	public java.lang.String BRNDCD;
	public java.lang.String BRNDSEQ;

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
	public java.lang.String getVBELN(){
		return VBELN;
	}
	public java.lang.String getPOSNR(){
		return POSNR;
	}
	public java.lang.String getQTSEQ(){
		return QTSEQ;
	}
	public java.lang.String getMATNR(){
		return MATNR;
	}
	public java.lang.String getARKTX(){
		return ARKTX;
	}
	public java.lang.String getKWMENG(){
		return KWMENG;
	}
	public java.lang.String getHOGI(){
		return HOGI;
	}
	public java.lang.String getWERKS(){
		return WERKS;
	}
	public java.math.BigDecimal getNETWR(){
		return NETWR;
	}
	public java.math.BigDecimal getWAVWR(){
		return WAVWR;
	}
	public java.lang.String getEDATU(){
		return EDATU;
	}
	public java.lang.String getREPMO(){
		return REPMO;
	}
	public java.lang.String getGUAMO(){
		return GUAMO;
	}
	public java.lang.String getWORMO(){
		return WORMO;
	}
	public java.lang.String getWAERK(){
		return WAERK;
	}
	public java.lang.String getCONDA(){
		return CONDA;
	}
	public java.lang.String getSTADA(){
		return STADA;
	}
	public java.lang.String getZRMDA(){
		return ZRMDA;
	}
	public java.lang.String getWWBLD(){
		return WWBLD;
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

	public void setVBELN(java.lang.String aValue) {
		VBELN=aValue;
	}
	public void setPOSNR(java.lang.String aValue) {
		POSNR=aValue;
	}
	public void setQTSEQ(java.lang.String aValue) {
		QTSEQ=aValue;
	}
	public void setMATNR(java.lang.String aValue) {
		MATNR=aValue;
	}
	public void setARKTX(java.lang.String aValue) {
		ARKTX=aValue;
	}
	public void setKWMENG(java.lang.String aValue) {
		KWMENG=aValue;
	}
	public void setHOGI(java.lang.String aValue) {
		HOGI=aValue;
	}
	public void setWERKS(java.lang.String aValue) {
		WERKS=aValue;
	}
	public void setNETWR(java.math.BigDecimal aValue) {
		NETWR=aValue;
	}
	public void setWAVWR(java.math.BigDecimal aValue) {
		WAVWR=aValue;
	}
	public void setEDATU(java.lang.String aValue) {
		EDATU=aValue;
	}
	public void setREPMO(java.lang.String aValue) {
		REPMO=aValue;
	}
	public void setGUAMO(java.lang.String aValue) {
		GUAMO=aValue;
	}
	public void setWORMO(java.lang.String aValue) {
		WORMO=aValue;
	}
	public void setWAERK(java.lang.String aValue) {
		WAERK=aValue;
	}
	public void setCONDA(java.lang.String aValue) {
		CONDA=aValue;
	}
	public void setSTADA(java.lang.String aValue) {
		STADA=aValue;
	}
	public void setZRMDA(java.lang.String aValue) {
		ZRMDA=aValue;
	}
	public void setWWBLD(java.lang.String aValue) {
		WWBLD=aValue;
	}

	// 2020 브랜드
	public java.lang.String getBRNDCD() {
		return BRNDCD;
	}

	public void setBRNDCD(java.lang.String bRNDCD) {
		BRNDCD = bRNDCD;
	}

	public java.lang.String getBRNDSEQ() {
		return BRNDSEQ;
	}

	public void setBRNDSEQ(java.lang.String bRNDSEQ) {
		BRNDSEQ = bRNDSEQ;
	}

	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0061.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0061"));
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
		elemField.setFieldName("QTSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "QTSEQ"));
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
		elemField.setFieldName("ARKTX");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ARKTX"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("KWMENG");
		elemField.setXmlName(new javax.xml.namespace.QName("", "KWMENG"));
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
		elemField.setFieldName("WERKS");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WERKS"));
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
		elemField.setFieldName("WAVWR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAVWR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EDATU");
		elemField.setXmlName(new javax.xml.namespace.QName("", "EDATU"));
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
		elemField.setFieldName("GUAMO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "GUAMO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WORMO");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WORMO"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("CONDA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CONDA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("STADA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "STADA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZRMDA");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ZRMDA"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WWBLD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WWBLD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		// 2020 브랜드
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BRNDCD");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BRNDCD"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BRNDSEQ");
		elemField.setXmlName(new javax.xml.namespace.QName("", "BRNDSEQ"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
		ds.addColumn("QTSEQ",(short)1,3);
		ds.addColumn("MATNR",(short)1,18);
		ds.addColumn("ARKTX",(short)1,40);
		ds.addColumn("KWMENG",(short)1,5);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("WERKS",(short)1,4);
		ds.addColumn("NETWR",(short)4,13);
		ds.addColumn("WAVWR",(short)4,13);
		ds.addColumn("EDATU",(short)1,8);
		ds.addColumn("REPMO",(short)1,2);
		ds.addColumn("GUAMO",(short)1,2);
		ds.addColumn("WORMO",(short)1,2);
		ds.addColumn("WAERK",(short)1,5);
		ds.addColumn("CONDA",(short)1,8);
		ds.addColumn("STADA",(short)1,8);
		ds.addColumn("ZRMDA",(short)1,8);
		ds.addColumn("WWBLD",(short)1,2);

		// 2020 브랜드
		ds.addColumn("BRNDCD",(short)1,30);		
		ds.addColumn("BRNDSEQ",(short)1,3);
		return ds;
	}

}