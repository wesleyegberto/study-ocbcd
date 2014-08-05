package study.servlets.insert;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.jpa.embeddables.Address;
import study.jpa.embeddables.Employee;
import study.utils.ResourceFactory;

@WebServlet(name = "EmbeddablesServlet", urlPatterns = { "/entity_embeddables" })
public class EntityEmbeddablesServlet extends HttpServlet {
	private static final long serialVersionUID = 5369426919462543806L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		EntityManager em = ResourceFactory.getEM();

		Employee emp = new Employee();
		emp.setName("Wesley Egberto");
		Address ad = new Address();
		ad.setAddress("Rua do meio");
		ad.setCity("Sao Paulo");
		ad.setCountry("Brasil");
		emp.setAddress(ad);

		// get transation
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(emp);
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
