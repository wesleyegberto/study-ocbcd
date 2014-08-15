package study.ejb3.projectbank.interceptors;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import study.ejb3.projectbank.logger.AsyncLogger;

/**
 * Interceptor of AgencyFacadeBean to log the creation of a new account.
 * If it isn't needed more, we just remove the @Interceptors of Agency EJB
 * and done.
 */
public class AgencyLogger implements Serializable {
	private static final long serialVersionUID = 861306230736475994L;
	
	@Inject
	private AsyncLogger logger;
	
	@AroundInvoke
	public Object interceptorMethod(InvocationContext ic) throws Exception {
		System.out.println("\n==================");
		System.out.println("Logging the creating of Account\nValues received:");
		// prints the parameters
		for(Object obj : ic.getParameters()) {
			System.out.print(obj + " ");
		}
		System.out.println("\nInitializing");
		
		// just call anothe logger to show the asynchronous call
		logger.logInfo("Creating an Account.");
		
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
