package study.ws.soap.client;

public class PersonServiceProxy implements study.ws.soap.client.PersonService_PortType {
  private String _endpoint = null;
  private study.ws.soap.client.PersonService_PortType personService_PortType = null;
  
  public PersonServiceProxy() {
    _initPersonServiceProxy();
  }
  
  public PersonServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initPersonServiceProxy();
  }
  
  private void _initPersonServiceProxy() {
    try {
      personService_PortType = (new study.ws.soap.client.PersonService_ServiceLocator()).getRegisterPerson();
      if (personService_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)personService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)personService_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (personService_PortType != null)
      ((javax.xml.rpc.Stub)personService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public study.ws.soap.client.PersonService_PortType getPersonService_PortType() {
    if (personService_PortType == null)
      _initPersonServiceProxy();
    return personService_PortType;
  }
  
  public boolean registerPerson(study.ws.soap.client.Person person) throws java.rmi.RemoteException{
    if (personService_PortType == null)
      _initPersonServiceProxy();
    return personService_PortType.registerPerson(person);
  }
  
  
}