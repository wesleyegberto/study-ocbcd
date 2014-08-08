package study.ws.soap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.ws.soap.client.Person;
import study.ws.soap.client.PersonService_PortType;
import study.ws.soap.client.PersonService_ServiceLocator;

@WebServlet(name = "WSPersonSoapTest", urlPatterns = { "/WsPersonSoapTest" })
public class WSPersonSoapTest extends HttpServlet {
	private static final long serialVersionUID = -5062615159911779302L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PersonService_ServiceLocator locator = new PersonService_ServiceLocator();
		
		Person person = new Person();
		person.setID(171);
		person.setName("Wesley");
		person.setAge(20);
		PersonService_PortType registerPerson = null;
		try {
			registerPerson = locator.getRegisterPerson();
			System.out.println("Result: " + registerPerson.registerPerson(person));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
