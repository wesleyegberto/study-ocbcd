package study.ejb2.lifecycle.stateless;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * The home interface must have the method ejbCreate().
 * Every method must declare CreateException
 */
public interface EjbStatelessHome extends EJBHome {

	public EjbStatelessComponent create()
			throws CreateException, RemoteException;
}
