package study.ejb2.transaction.cmt.stateless;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

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
 */
public class EjbStatelessDiceBean implements SessionBean {
	private static final long serialVersionUID = -625509719560931198L;
	//private SessionContext ctx;

	public void setSessionContext(SessionContext ctx) {
		System.out.println("setSessionContext");
		//this.ctx = ctx;
	}
	
	public void ejbCreate() {
		System.out.println("ejbCreate");
	}
	
	public void ejbActivate() {}

	public void ejbPassivate() {}

	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	/**
	 * This method runs in a transactional state. If this beans will be deployed
	 * as a Stateless Bean so it MUST start and finish the transaction in the same
	 * method (only Entity Bean can start in one method and finish in another). 
	 */
	public int rollDiceInAcid() {
		try {
			return (int) (Math.random() * 6) + 1;
		} catch(Exception e) {
			// this EJBException (a runtime) will make the Container rollback the transaction
			throw new EJBException("Some error occured.", e);
		}
		
	}
}
