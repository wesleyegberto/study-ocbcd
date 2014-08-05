package test.ejb.datetime;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * EJB's home interface.
 * In a stateless bean there is only one method: create() and in the
 * EJB bean implementation must have ejbCreate() to match with this create().
 */
public interface DateTimeHome extends EJBHome  {
	public DateTime create() throws CreateException, RemoteException;
}
