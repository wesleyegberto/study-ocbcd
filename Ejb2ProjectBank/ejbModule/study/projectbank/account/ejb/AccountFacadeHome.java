package study.projectbank.account.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AccountFacadeHome extends EJBHome {
	public AccountFacade create(long agency, long accountNumber) throws CreateException, RemoteException;
}
