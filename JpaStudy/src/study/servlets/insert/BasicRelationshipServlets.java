package study.servlets.insert;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.jpa.basic_relationship.Customer;
import study.utils.ResourceFactory;

@WebServlet(name = "BasicRelationshipServlet", urlPatterns = { "/basic_relationship" })
public class BasicRelationshipServlets extends HttpServlet {
	private static final long serialVersionUID = -5271470776678057647L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		EntityManager em = ResourceFactory.getEM();

		Customer c = new Customer();
		c.setName("Wesley");
		c.addPhone("982627222");
		c.addPhone("945682317");

		// get transation
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(c);
			em.flush();
			tx.commit();
			response.getWriter().print("<html><body><h4>Saved with successfully!</h4></body></html>");
		} catch(Exception ex) {
			try {
				response.getWriter().print("<html><body><h4>Error: " + ex.getMessage() + "</h4></body></html>");
			} catch(IOException e) {
			}
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
