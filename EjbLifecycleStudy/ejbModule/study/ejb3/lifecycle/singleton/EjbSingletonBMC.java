package study.ejb3.lifecycle.singleton;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;

/**
 * A BMC Singleton EJB.
 * To declare a BMC is needed the @ConcurrencyManagement annotation, by
 * default all singleton are CMC.
 * With BMC we control the concurrency access through Java synchronization
 * primitives (synchronize, Lock and so on).
 */
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class EjbSingletonBMC {
	
	public synchronized void doSomething() {
		System.out.println("doSomething");
	}
	
	public void doAnotherThing() {
		synchronized(this) {
			System.out.println("doAnotherThing");
		}
	}
}
