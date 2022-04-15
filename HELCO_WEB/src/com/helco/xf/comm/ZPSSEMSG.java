/**
 * ZPSSEMSG.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.helco.xf.comm;

public class ZPSSEMSG  implements java.io.Serializable {
    private org.apache.axis.types.UnsignedByte IDX;

    private java.lang.String ERRMSG;

    public ZPSSEMSG() {
    }

    public ZPSSEMSG(
           org.apache.axis.types.UnsignedByte IDX,
           java.lang.String ERRMSG) {
           this.IDX = IDX;
           this.ERRMSG = ERRMSG;
    }


    /**
     * Gets the IDX value for this ZQMSEMSG.
     * 
     * @return IDX
     */
    public org.apache.axis.types.UnsignedByte getIDX() {
        return IDX;
    }


    /**
     * Sets the IDX value for this ZQMSEMSG.
     * 
     * @param IDX
     */
    public void setIDX(org.apache.axis.types.UnsignedByte IDX) {
        this.IDX = IDX;
    }


    /**
     * Gets the ERRMSG value for this ZQMSEMSG.
     * 
     * @return ERRMSG
     */
    public java.lang.String getERRMSG() {
        return ERRMSG;
    }


    /**
     * Sets the ERRMSG value for this ZQMSEMSG.
     * 
     * @param ERRMSG
     */
    public void setERRMSG(java.lang.String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZPSSEMSG)) return false;
        ZPSSEMSG other = (ZPSSEMSG) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IDX==null && other.getIDX()==null) || 
             (this.IDX!=null &&
              this.IDX.equals(other.getIDX()))) &&
            ((this.ERRMSG==null && other.getERRMSG()==null) || 
             (this.ERRMSG!=null &&
              this.ERRMSG.equals(other.getERRMSG())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getIDX() != null) {
            _hashCode += getIDX().hashCode();
        }
        if (getERRMSG() != null) {
            _hashCode += getERRMSG().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZPSSEMSG.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZQMSEMSG"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IDX");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IDX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERRMSG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ERRMSG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

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
    }

}
