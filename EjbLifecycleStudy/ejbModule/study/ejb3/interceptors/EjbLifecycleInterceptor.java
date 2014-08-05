package study.ejb3.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * We also can declare a interceptor method in a class to be
 * used by a bunch of beans, instead of only that bean in which
 * was declared. 
 */
public class EjbLifecycleInterceptor {
	@AroundInvoke
	public Object interceptorMethod(InvocationContext ic) throws Exception {
		System.out.println("A interceptor class: ");
		// prints the name of the method intercepted
		System.out.println(ic.getMethod().getName());
		// prints the parameters
		for(Object obj : ic.getParameters()) {
			System.out.print(" " + obj);
		}
		// return the return of another interceptor
		return ic.proceed();
	}
}
