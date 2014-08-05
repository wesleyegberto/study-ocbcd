package study.ejb2.lifecycle.stateful;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * An EJB Stateful is created only when a client request one.
 * There is only one per client.
 * This class can be only a Statefull bean, because stateless bean's must not
 * have anything except the no-arg create() method.
 * Must NOT have a finalize() method.
 */
public class EjbStatefulBean implements SessionBean {
	private static final long serialVersionUID = 8339967245339132866L;
	private SessionContext context;
	private String specificClientData;
	
	private Object someVarNoSerializable;

	/* Methods of Creation */
	
	/**
	 * In the constructor the bean isn't a really bean (doesn't has the advantages
	 * of be a bean - context, JNDI capabilities), it actually is only a
	 * normal Java object.
	 * We shouldn't do anything because is to early to do EJB's things.
	 * Normally will be always empty, is better wait until the ejbCreate[XXXX].
	 */
	public EjbStatefulBean() {
	}

	/**
	 * This method is called after the constructor and before the
	 * ejbCreate[XXXX] method.
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
		this.context = ctx;
		// myConnection = dataSource.getConnection();
	}

	/**
	 * This method is called when the client calls the method on
	 * the EJBHome's stub.
	 * 
	 * We can have other ejbCreate overloaded or ejbCreateXXXXXX
	 * if we need, but in the home interface must have the
	 * correpondent method.
	 * Every method must be void, public and must not be marked final
	 * or static.
	 * We can declare exceptions if we actually throw one,
	 * but is often a good practice to declare CreateException.
	 * Must not declare RemoteException and must declare only application 
	 * exceptions (checked) declared in the home interface.
	 * 
	 * Finally we have full bean status (it really is an EJB).
	 * There we should put all initialization.
	 * 
	 * We CAN only:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - get security information about the client
	 * - get a transaction reference and call methods on it (BMT)
	 * - use JNDI environment
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 * 
	 * We CANNOT:
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * 
	 */
	public void ejbCreate() {
		System.out.println("Created through ejbCreate() from " +
				context.getEJBHome());
		specificClientData = "New one";
	}
	
	public void ejbCreate(String someInitial) {
		System.out.println("Created through ejbCreate(String) from " +
				context.getEJBHome());
		this.specificClientData = someInitial;
	}
	
	public void ejbCreateAnySufix() {
		System.out.println("Created through ejbCreatAnySufix() from " +
				context.getEJBHome());
		specificClientData = "Created a specific";
	}
	
	/**
	 * A business method, here we can do everything.
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
		System.out.println("Doing stuff: " + specificClientData + " (" +
				someVarNoSerializable + ")");
	}


	/* Methods of Passivation and Activation */
	
	/**
	 * Used only in a Statefull bean.
	 * This method is called before the bean is passivate by the Container.
	 * In this method we must leave the bean ready for that, we must sure
	 * every non-transient instance variable MUST be a reference to one of
	 * the following:
	 * - a serializable object
	 * - a null value
	 * - a bean's remote or local component or home interface, even if it isn't
	 *   serializable (teh Container will take care of)
	 * - a SessionContext, even if it isn't serializable (again the Container will)
	 * - JNDI context or its subcontexts
	 * - an UserTransaction interface
	 * - a resource manager connection factory (like a DataSource)
	 * 
	 * We also should release the resources because the Container can kill the
	 * bean when it is in the passivated (when the Container is gonna kill the
	 * bean it won't call the remote() method, thus we will have a leak of
	 * resources).
	 * 
	 * The bean will never be passivated while the bean is still in a transaction.
	 * 
	 * We CAN do the same thing as the thing we can do in an ejbCreate[XXXX].
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() {
		someVarNoSerializable = null; // we must set null
		
		// myConnection = null;
	}
	
	/**
	 * Used only in a Statefull bean.
	 * This method is called after the bean is deserialized and before the
	 * business methoed called by the client run.
	 * We must make sure that all resources needed by the methods are available
	 * again.
	 * 
	 * We should also initialize the transient variables because in the
	 * spec does not guarantee that will be set to default values (because
	 * the passivation cannot be a serialization).
	 * 
	 * We CAN do the same thing as the thing we can do in an ejbCreate[XXXX].
	 * 
	 * Usually, when the ejbPassivate() is empty this also will be.
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() {
		// reacquire the resources
		someVarNoSerializable = new Object();
		
		// myConnection = dataSource.getConnection();
		// we not have to restore the DataSource because it's one
		// of the things on the Container's "approved list" (it's a
		// resource manager factory, and the Container is required to
		// passivate it)
	}


	/**
	 * We should release all resources used by the bean.
	 * Is called before the Container kill the bean when:
	 * - Client calls remove() on EJBObject's stub
	 * - Client calls remote() on EJBHome's stub passing either the EJB's handler
	 *   or Primary Key
	 * - The bean times out (only if it's on an active)
	 * Isn't called when:
	 * - Container crash
	 * - The bean throws a runtime exception
	 * 
	 * We CAN do the same thing as the thing we can do in an ejbCreate[XXXX].
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() {
		specificClientData = null;
		someVarNoSerializable = null;
		// release the resources
		// myConnection.close();
		// dataSource = null;
	}
}
