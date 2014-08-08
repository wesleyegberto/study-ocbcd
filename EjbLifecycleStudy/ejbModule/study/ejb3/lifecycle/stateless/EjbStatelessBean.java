package study.ejb3.lifecycle.stateless;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * In EJB 3.x, to create a Stateless we need only a class with @Stateless annotation, doing
 * that all public methods of the bean will be exposed to the client;
 * If the bean exposes at least one interface, it needs to specify that it exposes a no-interface 
 * view by using the @LocalBean annotation on the bean class. Otherwise, the client must use one
 * of its interface;
 * If we define the configuration of the bean with annotations and xml (ejb-jar.xml), the xml's
 * configurations will override the annotation's configurations;
 * The lifecycle callback methods is defined with Annotation, must be void and have no parameters;
 * We don't need the home interface anymore, the EJB is got from injection or JNDI lookup;
 * 
 * With EJB 3.1, JNDI names have been specified so the code could be portable:
 * java:global[/<app-name>]/<module-name>/<bean-name>[!<fully-qualified-interface-name>]
 * <app-name>: name of the .EAR file, if is not used can be ommited
 * <module-name>: name of the .JAR file or the .WAR file
 * <bean-name>: name of the bean (default is the name of the class)
 * [!<fully-qualified-interface-name>]: if the EJB expose more than one interface we must use
 * the fully name.
 * 
 * For this exemple will generate:
 * Remote: "java:global/EjbLifecycleStudy/EjbStatelessBean!study.ejb3.lifecycle.stateless.EjbStatelessRemote"
 * Local no-view: "java:global/EjbLifecycleStudy/EjbStatelessBean!study.ejb3.lifecycle.stateless.EjbStatelessBean"
 */
@Stateless
@LocalBean
// @TransactionAttribute(TransactionAttributeType.REQUIRED) is default
// if the EjbStatelessRemote wasn't annotated with @Remote we could do this
// @Remote(EjbStatelessRemote.class)
public class EjbStatelessBean implements EjbStatelessRemote {

	/**
	 * To get the SessionContext we can use the @Resource annotation.
	 * In EJB 3.x we have 3 new methods:
	 * > getTimerService: Returns the javax.ejb.TimerService interface (only
	 *   Stateless and Singleton can call, Stateful can't be timed object);
	 * > lookup: Enables the session bean to look up its environment entries
	 *   in the JNDI naming context;
	 * > wasCancelCalled: Checks whether a client invoked the cancel() method on the client 
	 *   Future object corresponding to the currently executing asynchronous business method.
	 */
	@Resource
	private SessionContext ctx;
	
	/**
	 * We can inject environment entries with @Resource annotation.
	 * The objects are injected before the Container call the method annotated with @PostConstruct.
	 */
	@Resource(name = "maxDiscount")
	private String maxDiscount;
	
	/**
	 * @PostConstruct: Marks a method to be invoked immediately after a bean instance is created and dependency
	 * injection is done by the Container.
	 * This method is like the old ejbCreate().
	 */
	@PostConstruct
	public void postConstruct() {
		System.out.println("postConstruct");
	}
	
	/**
	 * @PreDestroy: Marks a method to be invoked immediately before the bean instance is destroyed by the container.
	 * This method is called by the Container to indicate that the bean will be
	 * destroyed, so here we should release any resource used by the bean.
	 * This method with @PreDestroy annotation correspond to the ejbRemove() method used in EJB 2.x.
	 */
	@PreDestroy
	public void preDestroy() {
		System.out.println("preDestroy");
	}
	
	/**
	 * A business method.
	 */
	public void doStuff() {
		System.out.println("Inside a business method!");
	}

}
