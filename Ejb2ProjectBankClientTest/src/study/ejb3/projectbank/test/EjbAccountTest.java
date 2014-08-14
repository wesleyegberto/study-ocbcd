package study.ejb3.projectbank.test;

import javax.naming.Context;
import javax.naming.InitialContext;

import study.ejb3.projectbank.account.AccountException;
import study.ejb3.projectbank.account.ejb.AccountFacade;

public class EjbAccountTest {
	public static void main(String[] args) {
		try {
			// gets the entry point into the JNDI naming service
			Context ctx = new InitialContext(Util.getJndiJbossProperties());
			AccountFacade account = (AccountFacade) ctx.lookup("java:Ejb3ProjectBank/AccountFacadeBean!study.ejb3.projectbank.account.ejb.AccountFacade");
			
			account.initiateSession(299012, 5365854);
			
			System.out.println("Balance: " + account.getBalance());
			// withdraw
			System.out.println("Withdrawing 26.01");
			account.withdraw(26.01);
			System.out.println("New balance: " + account.getBalance());
			
			// deposit
			System.out.println("Depositing 68.01");
			account.deposit(68.01, "Thomas Anderson");
			// wait the MDB does the deposit
			Thread.sleep(2000);
			System.out.println("New balance: " + account.getBalance());
			
		} catch(AccountException e) {
			System.out.println("Error: " + e.getLocalizedMessage());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
