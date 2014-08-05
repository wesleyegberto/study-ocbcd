package study.ejb2.lifecycle.stateful;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * The component interface.
 * Here we should redefine all business method that the bean does,
 * but declaring that it throws RemoteException.
 */
public interface EjbStatefulComponent extends EJBObject {
	public void doStuff() throws RemoteException;
}
