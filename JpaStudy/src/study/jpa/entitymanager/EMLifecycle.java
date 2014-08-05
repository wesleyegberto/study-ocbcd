package study.jpa.entitymanager;

import javax.persistence.EntityManager;

import study.jpa.basic_entity.Person;
import study.utils.ResourceFactory;

/**
 * The Lifecycle:
 * > Managed: was persisted(object), find(...), merge(object);
 * > Detached: was clean(), clean(object), detached(object), refresh(object);
 * > Removed: was remove(object);
 * 
 * We can set listeners with callbacks method to do business thing before or
 * after a state change in the lifecycle;
 */
public class EMLifecycle {

	public static void main(String[] args) {
		EntityManager em = ResourceFactory.getEM();
		
		// Is just a POJO in JVM's memory
		Person p = new Person();
		p.setName("Luiz Sera");
		p.setAge(33);
		
		// Now is Managed by EM
		em.persist(p);
		
		// Now is Detached from EM but still existing in the database
		em.detach(p);
		
		// Now is Managed by EM again and synchronized
		em.merge(p);
		
		// Now is Removed from EM and also from database
		em.remove(p);
	}
}
