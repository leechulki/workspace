package hdel.sd.sbp.domain;

import java.math.BigDecimal;
import com.tobesoft.platform.data.Dataset;

public class ZSDS0072 implements java.io.Serializable {

	public ZSDS0072() {
	}

	private String SONNR;			// 수주계획번호
	private String PLANTP;			// 계획구분 (SJ : 수주, MC : 매출, CH : 청구, SG : 수금)
	private String PLANYM;			// 계획년월
	private BigDecimal PLANAMT;		// 계획금액
	private String WAERK;			// SD 문서 통화

	public String getSONNR(){
		return SONNR;
	}

	public String getPLANTP(){
		return PLANTP;
	}

	public String getPLANYM(){
		return PLANYM;
	}

	public BigDecimal getPLANAMT(){
		return PLANAMT;
	}

	public String getWAERK(){
		return WAERK;
	}

	public void setSONNR(String aValue) {
		SONNR=aValue;
	}

	public void setPLANTP(String aValue) {
		PLANTP=aValue;
	}

	public void setPLANYM(String aValue) {
		PLANYM=aValue;
	}

	public void setPLANAMT(BigDecimal aValue) {
		PLANAMT=aValue;
	}

	public void setWAERK(String aValue) {
		WAERK=aValue;
	}
	// Type metadata 
	private static org.apache.axis.description.TypeDesc typeDesc 
		= new org.apache.axis.description.TypeDesc(ZSDS0072.class, true);

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
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSDS0072"));
		org.apache.axis.description.ElementDesc elemField =null;
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SONNR");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SONNR"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLANTP");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLANTP"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLANYM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLANYM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("PLANAMT");
		elemField.setXmlName(new javax.xml.namespace.QName("", "PLANAMT"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "BigDecimal"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("WAERK");
		elemField.setXmlName(new javax.xml.namespace.QName("", "WAERK"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "String"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	public static Dataset getDataset() {
		Dataset ds = new Dataset();

		ds.addColumn("SONNR",(short)1,10);
		ds.addColumn("PLANTP",(short)1,2);
		ds.addColumn("PLANYM",(short)1,6);
		ds.addColumn("PLANAMT",(short)4,15);
		ds.addColumn("WAERK",(short)1,5);
		return ds;
	}

}