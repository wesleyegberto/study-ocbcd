package study.ejb3.projectbank.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Interceptor of AgencyFacadeBean to log the creation of a new account.
 * If it isn't needed more, we just remove the @Interceptors of Agency EJB
 * and done.
 */
public class AgencyLogger {
	@AroundInvoke
	public Object interceptorMethod(InvocationContext ic) throws Exception {
		System.out.println("\n==================");
		System.out.println("Logging the creating of Account\nValues received:");
		// prints the parameters
		for(Object obj : ic.getParameters()) {
			System.out.print(obj + " ");
		}
		System.out.println("\nInitializing");
		try {
			Object proceed = ic.proceed();
			System.out.println("Result: " + proceed);
			return proceed;
		} catch(Exception ex) {
			System.out.println("Error: " + ex.getLocalizedMessage());
			throw ex;
		} finally {
			System.out.println("==================\n");
		}
		
	}
}
