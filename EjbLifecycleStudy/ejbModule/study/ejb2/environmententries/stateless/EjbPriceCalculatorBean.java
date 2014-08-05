package study.ejb2.environmententries.stateless;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EjbPriceCalculatorBean implements SessionBean {
	private static final long serialVersionUID = 2654904532616632501L;
	// private SessionContext ctx;
	private Context myCtx;
	
	private double percDiscount;
	private double maxDiscount;

	public void ejbCreate() {
		System.out.println("ejbCreate");
	}

	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		// this.ctx = ctx;
		// after the setSessionContext() we can access the special place of the bean
		try {
			InitialContext ic = new InitialContext();
			// lookup the special place of the bean
			myCtx = (Context) ic.lookup("java:comp/env");

			// lookup the env-entry setted in the DD (just use cast, but when
			// looking up for stubs - remote
			// home interface - should use narrow)
			Double value = (Double) myCtx.lookup("percDiscount");
			this.percDiscount = value.doubleValue();
			value = (Double) myCtx.lookup("maxDiscount");
			this.maxDiscount = value.doubleValue();

		} catch(NamingException e) {
			System.err.println("Erro at: " + e.getLocalizedMessage());
		}

	}

	public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	public void ejbRemove() throws EJBException, RemoteException {
		System.out.println("ejbRemove");
	}

	public double calculateDiscount(int productId, double price) {
		double discount = price * (percDiscount / 100);
		if(discount > maxDiscount) {
			discount = maxDiscount;
		}
		return discount;
	}
	
	public void notifyMeWhenProductArrives(String email) {
		try {
			Object o = myCtx.lookup("jms/NotificationProductArrive");
			Queue notificator = (Queue) o; // we don't need to narrow because we know it's JMS
			// do what we need to do
			System.out.println(notificator.getQueueName());
		} catch(NamingException e) {
			e.printStackTrace();
		} catch(JMSException e) {
			e.printStackTrace();
		}
	}
}
