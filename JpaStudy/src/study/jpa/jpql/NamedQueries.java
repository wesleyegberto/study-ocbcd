package study.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import study.jpa.basic_entity.Person;
import study.utils.ResourceFactory;

/**
 * Named queries are used when we have static queries, like query which only the parameteres changes,
 * can be more efficient to execute because the persistence provider can translate the
 * JPQL string to SQL once the application starts.
 * The named query are declared in the declaration of the entity and here we just use the name used
 * in NamedQuery annotation. The name used must be unique by persist unit.
 * Native query also can be named or created dynamically
 */
public class NamedQueries {
	@SuppressWarnings({ "unused", "unchecked"})
	public static void main(String[] args) {
		EntityManager em = ResourceFactory.getEM();
		
		Query query = em.createQuery("Person.getAll");
		query.setFirstResult(1);
		query.setMaxResults(20);
		List<Person> people = query.getResultList();
		
		query = em.createQuery("Person.findByNameAndAge");
		query.setParameter(1, "Wesley");
		query.setParameter("age", 20);
		
		Person p = (Person) query.getSingleResult();
	}
}
