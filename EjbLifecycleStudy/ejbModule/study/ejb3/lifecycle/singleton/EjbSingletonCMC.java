package study.ejb3.lifecycle.singleton;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * The EJB Singleton was introduced in EJB 3.1, is instantiated once per application.
 * The Container will make sure only one instance is created by JVM. 
 * Singleton and Stateless share the same lifecycle, but Singleton stay alive until
 * the Container is shut down. And the creation of Singleton depends whether it is
 * annotated with @Startup or is referencered with @DependsOn, so the Container
 * will create on deploytime, otherwise will be created when is used (a client calls
 * a method).
 *
 * We can tell to the Container instantiate the class at startup of the application
 * using @Startup.
 * In some cases, when you have several singleton beans, explicit initialization ordering
 * can be important. We can do that through @DependsOn.
 * 
 * Is possible to control the concurrency access to the Singleton EJB. We can do that (BMC)
 * or leave it to the Container (CMC);
 * CMC - Container Managed Concurrency:
 * @Lock(LockType.WRITE): A method associated with an exclusive lock will not allow
 * concurrent invocations until the method’s processing is completed.
 * @Lock(LockType.READ): A method associated with a shared lock will allow any number 
 * of other concurrent invocations to the bean’s instance.
 * We can annotate a class (all methods) or a specific method.
 * 
 * BMC - Bean Managed Concurrency:
 * We control the concurrency access through Java synchronization.
 */
@Singleton
@Startup
@Lock(LockType.READ)
@DependsOn("EjbSingletonBMC")
public class EjbSingletonCMC {
	
	public EjbSingletonCMC() {
		System.out.println("EjbSingletonBean");
	}
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("preDestroy");
	}
	
	@Lock(LockType.READ)
	public void doStuff() {
		System.out.println("doStuff");
	}
	
	// Already is applyed because of the annotation in the class.
	//@Lock(LockType.WRITE)
	// timeout to a concurrency access blocked, the client will
	// get a ConcurrentAccessTimeoutException
	@AccessTimeout(20000)
	public void doBar() {
		System.out.println("doBar");
	}
	
	
}
