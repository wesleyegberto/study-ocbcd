package study.ejb3.lifecycle.stateful;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 * An EJB Stateful is created only when a client request one.
 * There is only one per client.
 * This class can be only a Statefull bean, because stateless bean's must not
 * have anything except the no-arg create() method.
 * Must NOT have a finalize() method.
 */
@Stateful
public class EjbStatefulBean implements EjbStatefulComponent {
	private String specificClientData;
	private Object someVarNoSerializable;

	/**
	 * A business method.
	 */
	public void doStuff() {
		System.out.println("Doing stuff: " + specificClientData + " (" +
				someVarNoSerializable + ")");
	}

	/**
	 * This method is like the old ejbCreate().
	 * Is called after the Container inject all objects.
	 */
	@PostConstruct
	public void postConstruct() {
		System.out.println("postConstruct");
	}

	/**
	 * @PrePassivate: Marks a method to be invoked before the container passivates the instance.
	 * This method is called before the bean is passivate by the Container.
	 * This method with @PrePassivate annotation correspond to the ejbPassivate() method used in EJB 2.x.
	 */
	@PrePassivate
	public void passivate() {
		System.out.println("passivate");
		someVarNoSerializable = null; // we must set null
		// myConnection = null;
	}
	
	/**
	 * @PostActivate: Marks a method to be invoked immediately after the container reactivates the instance.
	 * This method is called after the bean is deserialized and before the
	 * business methoed called by the client run.
	 * This method with @PostActivate annotation correspond to the ejbActivate() method used in EJB 2.x.
	 */
	@PostActivate
	public void activate() {
		System.out.println("activate");
		// reacquire the resources
		someVarNoSerializable = new Object();
	}

	/**
	 * This method is called by the client to indicate the ends of use of bean.
	 * A method annotated with @Remove is used to indicate to the Container to call a method with @PreDestroy,
	 * this method is like in EJB 2.x when a client calls remove() in the home or component interface.
	 */
	@Remove
	public void remove() {
		System.out.println("remove");
	}
	
	/**
	 * This method is called by the Container to indicate that the bean will be
	 * destroyed, so here we should release any resource used by the bean.
	 * This method with @PreDestroy annotation correspond to the ejbRemove() method used in EJB 2.x.
	 */
	@PreDestroy
	public void preDestroy() {
		System.out.println("preDestroy");
		specificClientData = null;
		someVarNoSerializable = null;
		// release the resources
		// myConnection.close();
		// dataSource = null;
	}
}
