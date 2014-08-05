package study.ejb2.lifecycle.stateless;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * The component interface.
 * Here we should redefine all business method that the bean does,
 * but declaring that it throws RemoteException.
 */
public interface EjbStatelessComponent extends EJBObject {
	public void doStuff() throws RemoteException;
}
