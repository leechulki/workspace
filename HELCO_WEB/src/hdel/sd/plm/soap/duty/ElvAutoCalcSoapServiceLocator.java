/**
 * ElvAutoCalcSoapServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package hdel.sd.plm.soap.duty;

import hdel.sd.plm.domain.PlmSoapConfig;

public class ElvAutoCalcSoapServiceLocator extends org.apache.axis.client.Service implements hdel.sd.plm.soap.duty.ElvAutoCalcSoapService {

    public ElvAutoCalcSoapServiceLocator() {
    }


    public ElvAutoCalcSoapServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ElvAutoCalcSoapServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ElvAutoCalcSoap
    private java.lang.String ElvAutoCalcSoap_address = PlmSoapConfig.getPlmSoapUrl()+"/services/ElvAutoCalcSoap";

    public java.lang.String getElvAutoCalcSoapAddress() {
        return ElvAutoCalcSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ElvAutoCalcSoapWSDDServiceName = "ElvAutoCalcSoap";

    public java.lang.String getElvAutoCalcSoapWSDDServiceName() {
        return ElvAutoCalcSoapWSDDServiceName;
    }

    public void setElvAutoCalcSoapWSDDServiceName(java.lang.String name) {
        ElvAutoCalcSoapWSDDServiceName = name;
    }

    public hdel.sd.plm.soap.duty.ElvAutoCalcSoap getElvAutoCalcSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ElvAutoCalcSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getElvAutoCalcSoap(endpoint);
    }

    public hdel.sd.plm.soap.duty.ElvAutoCalcSoap getElvAutoCalcSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            hdel.sd.plm.soap.duty.ElvAutoCalcSoapSoapBindingStub _stub = new hdel.sd.plm.soap.duty.ElvAutoCalcSoapSoapBindingStub(portAddress, this);
            _stub.setPortName(getElvAutoCalcSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setElvAutoCalcSoapEndpointAddress(java.lang.String address) {
        ElvAutoCalcSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (hdel.sd.plm.soap.duty.ElvAutoCalcSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                hdel.sd.plm.soap.duty.ElvAutoCalcSoapSoapBindingStub _stub = new hdel.sd.plm.soap.duty.ElvAutoCalcSoapSoapBindingStub(new java.net.URL(ElvAutoCalcSoap_address), this);
                _stub.setPortName(getElvAutoCalcSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ElvAutoCalcSoap".equals(inputPortName)) {
            return getElvAutoCalcSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://plm_sd_soap.plmetc.dyna", "ElvAutoCalcSoapService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://plm_sd_soap.plmetc.dyna", "ElvAutoCalcSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ElvAutoCalcSoap".equals(portName)) {
            setElvAutoCalcSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
