package study.ejb2.security.ejbs;

import javax.ejb.EJBLocalObject;

public interface AccountComponent extends EJBLocalObject {
	
	public String getInformation();
	public double getBalance();
	public void withdraw(double amount) throws AccountBalanceException;
}
