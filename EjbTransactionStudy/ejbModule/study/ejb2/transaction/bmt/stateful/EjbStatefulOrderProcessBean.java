package study.ejb2.transaction.bmt.stateful;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.transaction.UserTransaction;

public class EjbStatefulOrderProcessBean implements SessionBean {
	private static final long serialVersionUID = -210741220707390263L;
	
	private SessionContext ctx;
	private UserTransaction ut;
	private String orderID;

	public void setSessionContext(SessionContext ctx) {
		System.out.println("setSessionContext");
		this.ctx = ctx;
	}
	
	public void ejbCreate(String orderID) {
		System.out.println("ejbCreate");
		this.orderID = orderID;
	}
	
	public void ejbActivate() {
		System.out.println("ejbActivate");
	}

	public void ejbPassivate() {
		System.out.println("ejbPassivate");
	}

	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	public void unsetEntityContext() {
		System.out.println("unsetEntityContext");
	}

	/**
	 * With Stateful BMT we can start a transaction in one method
	 * and finish in another.
	 */
	public void processOrder() {
		ut = ctx.getUserTransaction();
		System.out.println("Proccessing order " + orderID);
		try {
			ut.begin();
		} catch(Exception e) {
			System.out.println("Error at begin");
			e.printStackTrace();
		}
	}
	
	public void processPayment() {
		System.out.println("Proccessing payment order " + orderID);
		try {
			ut.commit();
		} catch(Exception e) {
			System.out.println("Error at commit");
			e.printStackTrace();
			try {
				ut.rollback();
			} catch(Exception e1) {
			}
		}
	}
}
