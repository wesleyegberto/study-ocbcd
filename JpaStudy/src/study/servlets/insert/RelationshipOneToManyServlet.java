package study.servlets.insert;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.jpa.relationship.onetomany.Dog;
import study.jpa.relationship.onetomany.PersonOneToMany;
import study.utils.ResourceFactory;

/**
 * Servlet implementation class RelationshipOneToMany
 */
@WebServlet("/RelationshipOneToMany")
public class RelationshipOneToManyServlet extends HttpServlet {
	private static final long serialVersionUID = 1085544353253647270L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = ResourceFactory.getEM();
		
		String result = null;
		PersonOneToMany p = new PersonOneToMany();
		p.setName("Odair");
		p.addDog(new Dog("Bob", "Pitbull"));
		p.addDog(new Dog("Jason", "Rottweiler"));
		
		// get transation
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(p);
			// foreach is not need because the relationship uses cascade
			//for(Cat dog : p.getDogs()) {
			//	em.persist(dog);
			//}
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
