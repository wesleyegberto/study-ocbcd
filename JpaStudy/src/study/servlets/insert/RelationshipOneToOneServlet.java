package study.servlets.insert;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.jpa.relationship.onetoone.PersonOneToOne;
import study.jpa.relationship.onetoone.Personality;
import study.utils.ResourceFactory;

/**
 * Servlet implementation class RelationshipOneToOne
 */
@WebServlet("/RelationshipOneToOne")
public class RelationshipOneToOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1085544353253647270L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = ResourceFactory.getEM();
		String result = null;
		
		PersonOneToOne p = new PersonOneToOne();
		p.setName("Odair");
		p.setPersonality(new Personality(5.0, 5.0, 1.0));
		
		// get transation
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			//em.persist(p.getPersonality());
			em.persist(p);
			em.flush();
			tx.commit();
			result = "Saved with successfully!";
		} catch(Exception ex) {
			result = ex.getMessage();
			ex.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		response.getWriter().print("<html><body><h4>" + result + "</h4></body></html>");
	}

}
