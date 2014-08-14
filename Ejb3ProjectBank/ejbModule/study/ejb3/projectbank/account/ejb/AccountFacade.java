package study.ejb3.projectbank.account.ejb;

import javax.ejb.Remote;

import study.ejb3.projectbank.account.AccountException;

@Remote
public interface AccountFacade {
	public void initiateSession(long agencyNumber, long accountNumber) throws AccountException;
	
	public double getBalance();

	public boolean withdraw(double amount) throws AccountException;

	public void deposit(double amount, String name) throws AccountException;

}
