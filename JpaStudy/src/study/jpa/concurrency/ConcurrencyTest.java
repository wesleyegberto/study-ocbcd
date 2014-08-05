package study.jpa.concurrency;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import study.utils.ResourceFactory;

/**
 * In JPA 2.0 there are 2 type: Optimistic and Pessimistic.
 * 
 * > Optimistic
 * . Locking is based on the assumption that most database transactions don’t conflict with
 *   other transactions, allowing concurrency to be as permissive as possible when allowing
 *   transactions to execute. Therefore, the decision to acquire a lock on the entity is
 *   actually made at the end of the transaction. This ensures that updates to an entity
 *   are consistent with the current state of the database;
 * . The use of a dedicated Version annotation on an entity allows the EntityManager to perform
 *   optimistic locking simply by comparing the value of the version attribute in the entity
 *   instance with the value of the column in the database. Without an attributed annotated
 *   with Version, the entity manager will not be able to do optimistic locking.
 * . A OptimisticLockException is throwed if either by explicitly locking the entity (passing
 *   LockModeType) or the Version of the entity is differente from database. 
 *   
 * > Pessimistic
 * . Locking is based in the opposite assumption, so a lock will be obtained
 *   on the resource before operating on it;
 * . The lock is done in the row in the database with low-level checks inside the database;
 * . A PessimisticLockException is throwed if the constraint is to be violated;
 * . In some applications with a higher risk of contentions, pessimist locking may
 *   be more appropriate, as the database lock is immediately obtained as opposed to the often
 *   late failure of optimistic transactions.
 * 
 * 
 * Type of locking:
 * > OPTIMISTIC: Uses optimistic locking;
 * > OPTIMISTIC_FORCE_INCREMENT: Uses optimistic locking and forces an increment to the entity’s
 *   version column (See the upcoming “Versioning” section.);
 * > PESSIMISTIC_READ: Uses pessimistic locking without the need to reread the data at the end
 *   of the transaction to obtain a lock;
 * > PESSIMISTIC_WRITE: Uses pessimistic locking and forces serialization among transactions
 *   attempting to update the entity;
 * > PESSIMISTIC_FORCE_INCREMENT: Uses pessimistic locking and forces an increment to the entity’s
 *   version column (See the upcoming “Versioning” section.);
 * > NONE: Specifies no locking mechanism should be used.
 */
public class ConcurrencyTest {
	public static void main(String[] args) {
		EntityManager em = ResourceFactory.getEM();
		
		Customer c = em.find(Customer.class, 1);
		// lock the row and increment the version
		em.lock(c, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		c.setName("Odair");
		em.persist(c);
		// or we can do the same in another way
		c = em.find(Customer.class, 1, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		c.setName("Odair Jose");
		em.persist(c);
	}
}
