package study.ejb3.transaction.bmt.stateless;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

/**
 * We can define a bean to be CMT or BMT through ejb-jar.xml or using
 * @TransactionManagement.
 * 
 * With BMT demarcation (programmatic transaction boundery), the application requests the transaction, and the EJB
 * container creates the physical transaction and takes care of a few low-level
 * details. Also, it does not propagate transactions from one BMT to another.
 * 
 * The UserTransaction is instantiated by the EJB container and made available
 * through dependency injection, JNDI lookup, or the SessionContext.
 * 
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
// all methods will use this transaction attribute
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EjbStatelessDiceBean {

	/**
	 * Now we can inject the UserTransaction
	 */
	@Resource
	private UserTransaction ut;
	
	/**
	 * This method uses the transaction attribute defined in the level-class.
	 */
	public int rollDDDice() {
		try {
			ut.begin();

			int dice = (int) (Math.random() * 12) + 1;
			// do some move
			if (dice == 7) { // you lost
				ut.rollback(); // rolls back the transaction
			}
			
			ut.commit();
			return dice;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (Exception e1) {
			}
		}
		return -1;
	}

	/**
	 * This method uses this transaction attribute defined (SUPPORTS).
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public int rollDiceInAcid() {
		return (int) (Math.random() * 6) + 1;
	}

}
