package study.ejb2.transaction.cmt.stateful;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;

/**
 * When using CMT we need declared the attributes which will role
 * the behavior.
 * 
 * The transaction attribute can be:
 * > Required
 *   If the method is called with an existing transaction context, the method
 *   runs in that transaction. Otherwise the Container will start a new one.
 * > RequiresNew
 *   The method will always run with a new transaction. If the method is called
 *   with an existing transaction context, the caller's transaction is suspended
 *   until this method completes.
 * > Mandatory
 *   The method requires an existing transaction. If the method is called without
 *   an existing transaction context, the Container will throw an exception.
 * > Supports
 *   Doesn't metter if the caller has or not a transaction context. If it has the
 *   method runs in the same transaction, otherwise runs with an "unspecified
 *   transaction context".
 * > NotSupported
 *   Always runs with an "unspecified transaction context". If the caller has a
 *   transaction, it will be suspended. 
 * > Never
 *   The method requires an "unspecified transaction context". If the method is
 *   called with an existing transaction context, the Container will throw an
 *   exception.
 * 
 * The Container will rollback only if we tell to do it (calling setRollbackOnly)
 * or if we throw a system exception (unchecked - runtime), like EJBException.
 * 
 * The interface SessionSynchronization allows the bean to have 3 new lifecycle
 * methods, those methods will be called when a transaction starts, ends and
 * commit/rollback.
 */
public class EjbStatefulDiceBean implements SessionBean, SessionSynchronization {
	private static final long serialVersionUID = -625509719560931198L;
	private SessionContext ctx;

	public void setSessionContext(SessionContext ctx) {
		System.out.println("setSessionContext");
		this.ctx = ctx;
	}
	
	public void ejbCreate() {
		System.out.println("ejbCreate");
	}
	
	public void ejbActivate() {
		System.out.println("ejbActive");
	}

	public void ejbPassivate() {
		System.out.println("ejbPassivate");
	}

	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	/**
	 * This method runs in a transactional state. If this beans will be deployed
	 * as a Stateless Bean so it MUST start and finish the transaction in the same
	 * method (only Entity Bean can start in one method and finish in another). 
	 */
	public int rollDiceInAcid() {
		// tells to Container rolls back
		ctx.setRollbackOnly();
		
		return (int) (Math.random() * 6) + 1;
	}

	/* 
	 * The following method are from SessionSynchronization, they extend the capability
	 * of the bean to know when the transaction start, end and commit or rollback.
	 * We can use only with Stateful Session Bean, because Stateful has state of a specific
	 * client, so we can persiste or load data from database before or after the business
	 * method (like ejbStore and ejbLoad of an Entity bean).
	 */
	/**
	 * This method is called just after the transaction has started and before the business
	 * method run.
	 * Here we can do anything, we have a "meaningful transaction context".
	 * @see javax.ejb.SessionSynchronization#afterBegin()
	 */
	public void afterBegin() {
		System.out.println("The transaction has started");
	}

	/**
	 * This method is called just before the transaction ends and after the business
	 * method completes.
	 * Here we can do anything, we still have a "meaningful transaction context".
	 * @see javax.ejb.SessionSynchronization#beforeCompletion()
	 */
	public void beforeCompletion() {
		System.out.println("About to end the transaction");
	}
	
	/**
	 * This method is called after the transaction has ended either in a commit or rollback.
	 * The given boolean indicates if the transaction was commited (when is true) or was
	 * rolled back.
	 * @see javax.ejb.SessionSynchronization#afterCompletion(boolean)
	 */
	public void afterCompletion(boolean committed) {
		System.out.println("Was committed? " + committed);
	}
}
