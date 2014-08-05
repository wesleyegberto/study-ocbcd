package study.ejb2.lifecycle.stateless;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * An EJB Stateless is created only when the Container think it need more instances.
 * The beans live in the pool. When a client calls a business method the Container
 * pulls one bean from the pool and links it to that client. During the execution
 * of the business method, the bean will remain linked with only that client.
 * Then, after the business method finishes, the bean will return to the pool.
 * 
 * A Statefull bean must have only one method create: ejbCreate()
 */
public class EjbStatelessBean implements SessionBean {
	private static final long serialVersionUID = -2969896322985179623L;

	/**
	 * In the constructor the bean isn't a really bean (doesn't has the advantages
	 * of be a bean - context, JNDI capabilities), it actually is only a
	 * normal Java object.
	 * We shouldn't do anything because is to early to do EJB's things.
	 * Normally will be always empty, is better wait until the ejbCreate().
	 */
	public EjbStatelessBean() {
	}
	
	/**
	 * This method is called after the constructor and before the
	 * ejbCreate() method.
	 * 
	 * Here we received the SessionContext, here the object is becoming a
	 * really EJB.
	 * 
	 * With the SessionContext we can:
	 * - get a reference to home
	 * - get a reference to EJB object
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - get a transaction and call methods on it (BMT)
	 * 
	 * But in this method is to early to do something because does not have 
	 * a "meaningful transaction context" (where the Container gives us some
	 * authority to do some things).
	 * 
	 * We CAN only:
	 * - get a reference to home
	 * - use JNDI environment
	 * 
	 * We CANNOT:
	 * - get a reference to EJB object
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - get a transaction and call methods on it (BMT)
	 * - use another bean's methods
	 * - use a resource manager (like a database) (we can ask a connection but
	 *   not make a JDBC call on it)
	 * 
	 * Usually we will only save the context.
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) {
		
	}

	/**
	 * This method is called when the Container creates a new bean.
	 * 
	 * We must have only on public void ejbCreate().
	 * 
	 * Finally we have full bean status (it really is an EJB), but we cannot
	 * access information about the client yet because the bean does not have
	 * a client linked to it.
	 * There we should put all initialization.
	 * 
	 * We CAN only:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - use JNDI environment
	 * 
	 * We CANNOT:
	 * - get security information about the client
	 * - get a transaction reference and call methods on it (BMT)
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 * 
	 */
	public void ejbCreate() {
		
	}

	/**
	 * This method is called by the Container to indicate that the bean will be
	 * destroyed, so here we should release any resource used by the bean.
	 * 
	 * We CAN only:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - use JNDI environment
	 * 
	 * We CANNOT:
	 * - get security information about the client
	 * - get a transaction reference and call methods on it (BMT)
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() {
		
	}
	
	/**
	 * A business method, here we can do everything, even thing related with
	 * the client because during the business method execution the bean has
	 * one client linked to it.
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - get security information about the client
	 * - get a transaction reference and call methods on it (BMT)
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use JNDI environment
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 */
	public void doStuff() {
		System.out.println("Inside a business method!");
	}

	/**
	 * This will never be called in a Statefull bean.
	 */
	public void ejbActivate() {
	}

	/**
	 * This will never be called in a Statefull bean.
	 */
	public void ejbPassivate() {
	}

}
