package advice;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AdviceHome extends EJBHome {
	
	public Advice create(String name) throws CreateException, RemoteException;
	
}
