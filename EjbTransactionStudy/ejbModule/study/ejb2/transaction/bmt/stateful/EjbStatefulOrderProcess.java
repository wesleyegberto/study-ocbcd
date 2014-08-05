package study.ejb2.transaction.bmt.stateful;

import javax.ejb.EJBLocalObject;

public interface EjbStatefulOrderProcess extends EJBLocalObject {
	public void processOrder();
	
	public void processPayment();
}
