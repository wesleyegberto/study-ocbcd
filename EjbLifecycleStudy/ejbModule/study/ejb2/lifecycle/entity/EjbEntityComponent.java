package study.ejb2.lifecycle.entity;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * In Entity Beans, the component interface usually contain getters and setters
 * for values that correspond to columns in a database table.
 * 
 * For remote interface, every parameter and return type must be a RMI-IIOP compatible.
 */
public interface EjbEntityComponent extends EJBObject {
	public String getName() throws RemoteException;

	public void setName(String name) throws RemoteException;

	public int getAge() throws RemoteException;

	public void setAge(int age) throws RemoteException;
}
