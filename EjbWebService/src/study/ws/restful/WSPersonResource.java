package study.ws.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBElement;

import study.business.Person;

/**
 * We use JAX-RS to create Web Service RESTful.
 * 
 * To define a WS RS we only need annotate the class or a method with @Path,
 * the String passed is the URI to the resource.
 * 
 * We need to setup the Jersey servlet in the DD.
 *
 */
@Path("/person") // http://localhost:8080/person
public class WSPersonResource {
	
	@POST // HTTP method
	@Consumes({"application/xml", "application/json"}) // content type to consume
	@Produces({"application/xml", "application/json"}) // content type to produce
	public boolean registerPerson(JAXBElement<Person> elem) {
		Person p = elem.getValue();
		System.out.println("Registering: " + p);
		return true;
	}
	
	@GET
	@Produces({"application/xml", "application/json"})
	@Path("/{id}/")
	public Person getPersonById(@PathParam("id") int id) {
		// here we could find
		return new Person(id, "Wesley", 20);
	}

	@DELETE
	@Produces({"application/xml", "application/json"})
	@Path("/{id}/")
	public boolean deletePersonById(@PathParam("id") int id) {
		// here we could find
		return false;
	}
}
