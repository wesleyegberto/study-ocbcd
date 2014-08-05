package study.ejb2.security.ejbs;

import java.rmi.RemoteException;
import java.security.Principal;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * A Principal represents a user who has been authenticated by an authentication
 * system.
 * In EJB 2.x we define the security policies in the ejb-jar.xml.
 */
public class AccountBean implements SessionBean {
	private static final long serialVersionUID = -458936241760444750L;
	private SessionContext ctx;
	private String account;
	private String name;
	private double balance;

	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		this.ctx = ctx;
	}

	public void ejbActivate() throws EJBException, RemoteException {
		System.out.println("ejbActivate");
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		System.out.println("ejbPassivate");
	}

	public void ejbRemove() throws EJBException, RemoteException {
		System.out.println("ejbRemove");
		account = null;
		name = null;
	}

	public void ejbCreate(String account, String name) {
		this.account = account;
		this.name = name;
		// the load of the fake balance
		this.balance = Math.random() * 10000;
	}

	public void ejbCreate(String name, double balance) {
		int numAccount = (int) (Math.random() * 10000000);
		this.account = String.valueOf(numAccount);
		this.name = name;
		this.balance = balance;
	}

	// methods from component interface
	public String getInformation() {
		return account + " - " + name + ": " + balance;
	}

	public double getBalance() {
		return balance;
	}

	public void withdraw(double amount) throws AccountBalanceException {
		if (balance - amount < 0) {
			ctx.setRollbackOnly();
			throw new AccountBalanceException("There isn't enough money.");
		} else if (!ctx.isCallerInRole("EjbRoleCustomer")) {
			throw new AccountBalanceException("Not authorized.");
		} else {
			Principal princ = ctx.getCallerPrincipal();
			System.out.println("Somebody is withdrawing: " + princ.getName());
			balance -= amount;
		}
	}

}
