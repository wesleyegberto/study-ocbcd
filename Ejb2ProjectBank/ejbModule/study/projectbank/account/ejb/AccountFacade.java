package study.projectbank.account.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import study.projectbank.account.AccountException;

public interface AccountFacade extends EJBObject {
	public double getBalance() throws RemoteException;

	public boolean withdraw(double amount) throws AccountException, RemoteException;
	
	public void deposit(double amount, String name) throws AccountException, RemoteException;
	
	//public void transferAmount(double amount, long agencyNumberDest, long accountNumberDest) throws AccountException, RemoteException;
}
