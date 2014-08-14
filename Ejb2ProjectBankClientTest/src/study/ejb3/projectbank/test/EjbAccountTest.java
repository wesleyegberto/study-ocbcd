package study.ejb3.projectbank.test;

import java.rmi.RemoteException;

import study.ejb2.projectbank.pattern.ServiceLocator;
import study.projectbank.account.AccountException;
import study.projectbank.account.ejb.AccountFacade;
import study.projectbank.account.ejb.AccountFacadeHome;

public class EjbAccountTest {
	public static void main(String[] args) {
		try {
			
			AccountFacadeHome home = (AccountFacadeHome) ServiceLocator.getInstance().getHome("bank/Account", AccountFacadeHome.class);
			
			AccountFacade account = home.create(299012,5365854);
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
		} catch(RemoteException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
