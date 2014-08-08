/**
 * PersonService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package study.ws.soap.client;

public class PersonService_ServiceLocator extends org.apache.axis.client.Service implements study.ws.soap.client.PersonService_Service {

    public PersonService_ServiceLocator() {
    }


    public PersonService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PersonService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RegisterPerson
    private java.lang.String RegisterPerson_address = "http://localhost:8080/EjbWebService/PersonService";

    public java.lang.String getRegisterPersonAddress() {
        return RegisterPerson_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegisterPersonWSDDServiceName = "RegisterPerson";

    public java.lang.String getRegisterPersonWSDDServiceName() {
        return RegisterPersonWSDDServiceName;
    }

    public void setRegisterPersonWSDDServiceName(java.lang.String name) {
        RegisterPersonWSDDServiceName = name;
    }

    public study.ws.soap.client.PersonService_PortType getRegisterPerson() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RegisterPerson_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegisterPerson(endpoint);
    }

    public study.ws.soap.client.PersonService_PortType getRegisterPerson(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            study.ws.soap.client.PersonServiceSoapBindingStub _stub = new study.ws.soap.client.PersonServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRegisterPersonWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegisterPersonEndpointAddress(java.lang.String address) {
        RegisterPerson_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (study.ws.soap.client.PersonService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                study.ws.soap.client.PersonServiceSoapBindingStub _stub = new study.ws.soap.client.PersonServiceSoapBindingStub(new java.net.URL(RegisterPerson_address), this);
                _stub.setPortName(getRegisterPersonWSDDServiceName());
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
        if ("RegisterPerson".equals(inputPortName)) {
            return getRegisterPerson();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soap.ws.study/", "PersonService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soap.ws.study/", "RegisterPerson"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RegisterPerson".equals(portName)) {
            setRegisterPersonEndpointAddress(address);
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
