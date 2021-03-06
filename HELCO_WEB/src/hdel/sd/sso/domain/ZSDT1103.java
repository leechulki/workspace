package hdel.sd.sso.domain;
import com.tobesoft.platform.data.Dataset;


/**
 * 수주생성_TEXT (ZSDS0060T) Serializable
 * @Comment 
 *     	- Ssc0060C(수주생성) 에서 사용
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */
 

public class ZSDT1103   
		implements java.io.Serializable {

	public ZSDT1103() {
	}
	public String FLAG;
	public String MANDT;
	public String QTNUM;
	public String QTSER;
	public java.lang.String VBELN;
	public java.lang.String SER;
	public java.lang.String TEXT;
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
	public java.lang.String getSER(){
		return SER;
	}
	public java.lang.String getTEXT(){
		return TEXT;
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
	public void setSER(java.lang.String aValue) {
		SER=aValue;
	}
	public void setTEXT(java.lang.String aValue) {
		TEXT=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0060T.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0060T"));
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
		elemField.setFieldName("SER");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SER"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("TEXT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TEXT"));
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
		ds.addColumn("SER",(short)1,5);
		ds.addColumn("TEXT",(short)1,132);
		return ds;
	}

}