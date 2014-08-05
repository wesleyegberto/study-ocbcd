package study.ejb2.lifecycle.stateful;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * The home interface must have the methods with prefix "ejb" to match
 * with the bean's methods.
 * create() -> ejbCreate()
 * create(String) -> ejbCreate(String)
 * createAnyThing() -> ejbCreateAnyThing()
 * 
 * Every method must declare CreateException and RemoteException
 */
public interface EjbStatefulHome extends EJBHome {

	public EjbStatefulComponent create()
			throws CreateException, RemoteException;

	public EjbStatefulComponent create(String someInitial)
			throws CreateException, RemoteException;

	public EjbStatefulComponent createAnySufix()
			throws CreateException, RemoteException;
}
