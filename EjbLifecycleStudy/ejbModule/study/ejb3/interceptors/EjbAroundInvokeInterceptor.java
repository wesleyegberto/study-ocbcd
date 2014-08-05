package study.ejb3.interceptors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;

/**
 * Interceptors are like Filters in Servlets.
 * 
 * Interceptors are used to intercept methods call in EJB, can be used before or
 * after a method call and will be executed in the same transaction and security
 * context. We can have default interceptors to use to intercept all methods call
 * to any bean.
 * Interceptors are automatically triggered by the container when an
 * EJB method is invoked.
 * If there are more than one, the Container will call
 * the intercerptor from the largest scope to the smaller scop, sequentially
 * (default interceptor, then level-class interceptors and the method interceptors).
 * 
 * We can throw checked exceptions and the signature must be (can't be static or
 * final): <access modifier> Object <method>(InvocationContext ic) throws
 * Exception
 * 
 * The InvocationContext object allows interceptors to control the behavior of
 * the invocation chain. If several interceptors are chained, the same
 * InvocationContext instance is passed to each interceptor, which can add
 * contextual data to be processed by other interceptors.
 * 
 * Around-invoke declared in a EJB are used to intercept only method of that
 * EJB. We must call proceed() when we want to allow the chain and then the
 * business method (like in Filters).
 * 
 * We declare interceptors annotating a method with @AroundInvoke or in the
 * ejb-jar.xml with a tag interceptor-binding in the assembly-descriptor section
 * 
 */
@Stateless
@Interceptors(EjbLifecycleInterceptor.class)
public class EjbAroundInvokeInterceptor {

	@PostConstruct
	public void init() {
		System.out.println("init");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("destroy");
	}

	public void businessMethod() {
		System.out.println("businessMethod");
	}

	/**
	 * We can also reuse a interceptor class instand of declaring a interceptor
	 * method only for one EJB. If we use this annotation in the class
	 * declaration then all methods will be intercepted.
	 */
	@Interceptors(EjbMethodInterceptor.class)
	public void anotherBusinessMethod() {
		System.out.println("anotherBusinessMethod");
	}

	/**
	 * The @ExcludeClassInterceptors annotation will stop this method of to be
	 * intercepted and the @ExcludeDefaultInterceptors will the default interceptor
	 * declared in ejb-jar.xml.
	 */
	@ExcludeClassInterceptors
	@ExcludeDefaultInterceptors
	public void aBusinessMethod() {

	}

	@AroundInvoke
	public Object interceptorMethod(InvocationContext ic) throws Exception {
		// prints the name of the method intercepted
		System.out.println(ic.getMethod().getName());
		// prints the parameters
		for (Object obj : ic.getParameters()) {
			System.out.print(" " + obj);
		}
		// return the return of another interceptor
		return ic.proceed();
	}
}
