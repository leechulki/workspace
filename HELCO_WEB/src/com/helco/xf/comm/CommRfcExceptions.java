/**
 * ZWEB_QM_GET_OUT_REQUEST_LISTRfcExceptions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.helco.xf.comm;

public class CommRfcExceptions implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CommRfcExceptions(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NOT_FOUND_WBS = "NOT_FOUND_WBS";
    public static final java.lang.String _NO_DATA_FOUND = "NO_DATA_FOUND";
    public static final CommRfcExceptions NOT_FOUND_WBS = new CommRfcExceptions(_NOT_FOUND_WBS);
    public static final CommRfcExceptions NO_DATA_FOUND = new CommRfcExceptions(_NO_DATA_FOUND);
    public java.lang.String getValue() { return _value_;}
    public static CommRfcExceptions fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CommRfcExceptions enumeration = (CommRfcExceptions)
            _table_.get(value);
        if (enumeration==null) {
        	return new CommRfcExceptions( value );
        }
        return enumeration;
    }
    public static CommRfcExceptions fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
          return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommRfcExceptions.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "RfcExceptions"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
