package study.jpa.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import study.jpa.relationship.inheritance.StoreItem;
import study.utils.ResourceFactory;

/**
 * The entity manager is a first-level cache used to treat data in a comprehensive way for the
 * database and to cache short, live entities. The Persist Context (first-level cache) is used
 * on a per-transaction basis to reduce the number of SQL queries within a given transaction.
 * For example, if an object is modified several times within the same transaction, the entity
 * manager will generate only one UPDATE statement at the end of the transaction. A first-level
 * cache is not a performance cache.
 *
 * When using EJB with other thing, before we update or remove an entity we need to merge it.
 * Because we received the object from a client whose does not have the persistent context, therefore
 * the given object is out of the persistent context. Mergint it we synchronize it with the database
 * and thus we can manipulate it correctly.
 */
public class EMMethodsTest {
	public static void main(String[] args) {
		EntityManager em = ResourceFactory.getEM();

		EntityTransaction et = em.getTransaction();

		// create the object to be persisted
		StoreItem item = new StoreItem();
		item.setDescription("Head First EJB");
		item.setName("Head First EJB");
		item.setPrice(49.90);
		item.setDiscount(1.50);

		// persisting the entity
		et.begin();
		// after the persist(), the entity will remain in the persistent context
		// (first-level cache) until we/Container commit() the transaction or
		// flush() to flush the persistent context to the database
		em.persist(item);
		System.out.println("Persited in first-level cache");
		et.commit();
		System.out.println("Transaction commited");

		// flushing the persistent context to the database
		et.begin();
		item.setPicture("head-first-ejb-cover.png");
		em.merge(item);
		System.out.println("Updated in first-level cache");
		em.flush(); // will force to flush the persistent context
		System.out.println("Flushed to database");
		et.commit();
		System.out.println("Transaction commited");

		// refreshing the entity with data from database
		item.setName("A name which won't be persisted");
		em.refresh(item);
		System.out.println("The name refreshed was: " + item.getName());

		// reset the vars to search the entity
		int id = item.getId();
		StoreItem itemFound = null;
		// search the entity by primary key, is returned false if is not found
		itemFound = em.find(StoreItem.class, id);
		System.out.print("find(): ");
		if(itemFound != null) {
			System.out.println("Found");
		} else {
			System.out.println("Not found");
		}
		itemFound = null;

		// search the entity without load the data, is throwed a exception if is
		// not found
		try {
			System.out.print("getReference(): ");
			itemFound = em.getReference(StoreItem.class, id);
			System.out.println("Found");
		} catch(EntityNotFoundException ex) {
			System.out.println("Not found");
		}

		// detaching the entity
		// detach() will remove the item from the persistent context, so any
		// change in the entity
		// won't be reflected in the database
		em.detach(item);
		System.out.println("Is managed by EM: " + em.contains(item)); // return
																		// false
																		// because
																		// was
																		// detached
		item.setPrice(52.60); // the price will remain only in memory (and out
								// of the persistent context, thus won't be
								// flushed or commited)
		et.begin();
		// merge the entity to be managed by the EM again and synchronize the
		// data
		em.merge(item);
		et.commit();

		// remove the entity from database
		et.begin();
		em.remove(item);
		System.out.println("Removed");
		et.commit();

		em.close();
	}
}
