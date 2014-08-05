package study.ejb3.interceptors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.InvocationContext;

/**
 * We also can declare a interceptor method which will intercept a defined
 * moment in the lifecycle of the bean. Thus, we can declare a specific class
 * to do that and keep in the bean only business method. This methods is invoked
 * prior to bean's lifecycle methods.
 * 
 * We only need annotate the method with a lifecycle annotation and in the target
 * bean use the @Interceptors annotation.
 * The interceptor method must return void instead of Object and we can't declare
 * exception.
 */
public class EjbMethodInterceptor {
	@PostConstruct
	public void postCreatorIntercept(InvocationContext ic) {
		System.out.println("A bean was created: " + ic.getTarget());
	}
	
	@PreDestroy
	public void preDestroyIntercept(InvocationContext ic) {
		System.out.println("A bean will be destroyed: " + ic.getTarget());
	}
}
