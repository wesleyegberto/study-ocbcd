package study.ejb2.lifecycle.entity;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * The home interface is more important for Entity bean. Because it provides methods
 * to create, find, remove and business method to use at/with entity beans.
 * 
 * > The createXXXX([...]) method:
 * . Are used to create Entity bean in the persistent store/database;
 * . We can have 0 or more. (Because we can provide home interface only for searches).
 * 
 * > The removeXXXX([...]) method:
 * . Are used to remove/delete Entity bean from the persistent store/database.
 *
 *> The findXXXX([...]) method:
 * . Are used to find existing Entity bean in the persistent store/database;
 * . Can return a EJB object to a specific Entity or can return a Collection with many
 * Entity (when find for city for examplo);
 * . We must have at least one: findByPrimaryKey(String pk);
 * . When the method does not find any Entity it must throw a ObjectNotFoundException. But, if
 * the method return a Collection so it don't have to, should only return the empty Collection;
 * . 
 *
 *> The business method
 * . In Entity bean, the home interface can have business method to manipulate
 * Entity beans;
 * . The business method ca have arbitrary names and return type (can ben stubs
 * or type RMI-IIOP compatible), but the name must not begin with: create,
 * find or remove. Also must declare a RemoteException;
 * . We can delete a bunch of entities, return Value Object (J2EE pattern) with
 * the entity's data when the client want only the data (because with stub
 * he would make a lot of call generating network overhead).
 *
 */
public interface EjbEntityHome extends EJBHome {
	/**
	 * Create method used to insert/create a new Entity bean.
	 */
	public EjbEntityComponent create(String name, int age) throws CreateException, RemoteException;
	
	/**
	 * The required findByPrimaryKey(String) method.
	 * Should return the Entity which have the exactly primary key received.
	 */
	public EjbEntityComponent findByPrimaryKey(String pk) throws FinderException, RemoteException;
	
	/**
	 * A finder method that return a Collection of EJB object.
	 * Will return a empty Collection if it does not find any Entity that match.
	 */
	public Collection findByRangeOfAge(int ageStart, int ageEnd) throws FinderException, RemoteException;
	
	/**
	 * A home business method.
	 */
	public void myHomeBusinessMethod() throws RemoteException;
	
}
