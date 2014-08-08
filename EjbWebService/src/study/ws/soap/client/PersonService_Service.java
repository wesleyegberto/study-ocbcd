/**
 * PersonService_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package study.ws.soap.client;

public interface PersonService_Service extends javax.xml.rpc.Service {
    public java.lang.String getRegisterPersonAddress();

    public study.ws.soap.client.PersonService_PortType getRegisterPerson() throws javax.xml.rpc.ServiceException;

    public study.ws.soap.client.PersonService_PortType getRegisterPerson(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
