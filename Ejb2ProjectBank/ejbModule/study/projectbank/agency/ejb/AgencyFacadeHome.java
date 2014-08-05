package study.projectbank.agency.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AgencyFacadeHome extends EJBHome {
	public AgencyFacade create(long agency) throws CreateException, RemoteException;
}
