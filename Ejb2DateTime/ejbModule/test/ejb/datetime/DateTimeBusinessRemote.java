package test.ejb.datetime;

import java.rmi.RemoteException;

/**
 * Interface to allow errors in compilation time when building
 * the EJB and its component interface.
 */
public interface DateTimeBusinessRemote {
	public String getDate() throws RemoteException;
	
	public String getTime() throws RemoteException;
	
	public String getTimestamp() throws RemoteException;
}
