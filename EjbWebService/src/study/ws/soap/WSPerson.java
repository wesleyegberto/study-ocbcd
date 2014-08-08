package study.ws.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import study.business.Person;

/**
 * We use JAX-WS to create Web Service SOAP.
 * 
 * The @WebService annotation marks a Java class or interface as being a web
 * service. If used directly on the class, the annotation processor of the
 * container will generate the interface. Mark a classe with a @WebService
 * is only what we need to create a WS.
 * We can have Servlet Endpoint and EJB Endpoint. With EJB Endpoint, the bean
 * must be a Stateless. A Servlet Endpoint is a regular POJO deployed in a WAR.
 * 
 * By default, if any @WebMethod is used all the public methods of a web service
 * are exposed in the WSDL and use all the default mapping rules.
 * 
 * We can override/declare the WS configuration in a webservices.xml file.
 * 
 * We use the tools wsimport and wsgen from JDK 6 or greater to generate WSDL from classes or
 * generate client classes from WSDL.
 * wsimport will generate all artifacts needed to consume a WS. We can also tell
 * to Maven does that.
 * wsgem will read a WS Endpoint and generate the WSDL.
 * 
 * We can have @PostContruct and @PreDestroy.
 */
@WebService(name = "PersonService", serviceName = "PersonService", portName = "RegisterPerson")
public class WSPerson {

	/**
	 * To customize some elements of this mapping, you can apply the @WebMethod
	 * annotation on methods.
	 */
	@WebMethod(operationName = "registerPerson")
	/**
	 * We use @WebResult to customize the name of the message returned in WSDL.
	 * We also can use @OneWay to specify a method which doesn't have a return
	 * value in WSDL. 
	 */
	@WebResult(name = "registered")
	/** we use @WebParam to customize the parameter in WSDL. */
	public boolean registerPerson(@WebParam(name = "person") Person person) {
		System.out.println("Registering: " + person);
		return true;
	}
	
	/** Exclude this methond from WS' interface */
	@WebMethod(exclude = true)
	public boolean validate(Person person) {
		return person != null;
	}
}
