package study.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import study.jpa.basic_entity.Person;
import study.utils.ResourceFactory;

/**
 * Dynamic queries are queries that are generated at runtime. The cost of translation of
 * the string to JPQL query every time can be an issue.
 */
public class DynamicQueries {
	@SuppressWarnings({ "unused", "unchecked"})
	public static void main(String[] args) {
		EntityManager em = ResourceFactory.getEM();
		
		// we can use aggregates function, unions, subqueries, joins, distinct,
		// group by, having and so on
		Query query = em.createQuery("SELECT p FROM Person p");
		query.setFirstResult(1);
		query.setMaxResults(20);
		List<Person> people = query.getResultList();
		
		query = em.createQuery("SELECT p FROM Person p WHERE p.NAME = ?1 p.AGE = :age");
		query.setParameter(1, "Wesley");
		query.setParameter("age", 20);
		
		// when we know that will result only in one row we should use getSingleResult()
		// because it avoid loop and caching needed to a list
		Person p = (Person) query.getSingleResult();
	}
}
