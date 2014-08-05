package study.ejb2.lifecycle.entity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

/**
 * Entity bean represents a entity from database/persistent store. The Entity
 * must have its data sync with the database/persistent store, when the Entity bean
 * is a CMP, the Container does that and when the Entity bean is a BMP the Bean does
 * (actually the bean developer).
 * Usually when the entity bean is CMP, the class will be abstract and the getters and
 * setters methods for the virtual fields will also be abstract.
 * 
 * We the client calls a finder method, the Container go to database/persistence store to check
 * if the entity exists. If not an ObjectNotFoundException is throwed, otherwise the PK
 * of entity bean is returned and the stub of EJB Object. But, the entity bean's data
 * is never loaded in the find() because the Container always reloaded the data before
 * a business method run. So, to avoid the wasting of memory (in case the client just
 * want a reference) the entity bean's is really loaded when the client calls a business method. 
 * 
 * 1) The client calls a business method;
 * 2) Before it runs, the Container/Bean starts a transaction and
 * locks its record in the database/persistent store;
 * 3) The Container/Bean loads data from database to update the Entity bean;
 * 4) Runs the business method;
 * 5) Update its record in the database/persistent store and then ends the transaction and unlock
 * the its record.
 * 
 * There is a pool of entity bean, when the Container needs a new or finds
 * a entity bean it pull one from the pool and then call the create(...) to
 * set the new attributes (like creating one).
 * 
 * Must have a ejbCreate, ejbFind to match with the methdos from home interface.
 * For each createXXXX[XXXX] method in home we must have two method here: ejbCreateXXXX[XXXX]
 * and a ejbPostCreateXXXX[XXXX].
 * For each home business method we must have a method ejbHomeXXXX.
 * When the bean is CMP we do not have to provide a ejbFind to each find method in home, just
 * must have one: ejbFindByPrimaryKey(String).
 */
public abstract class EjbEntityBean implements EntityBean {
	private static final long serialVersionUID = 2551049309569552780L;

	/** With CMP we don't have to declare out attributes/fields of the entity/table */
	// Primary key
	//private String id;
	// Business attributes
	//private String name;
	//private int age;

	// abstract getters and setters to the Container
	public abstract String getId();
	public abstract void setId(String id);
	public abstract String getNameField();
	public abstract void setNameField(String name);
	public abstract int getAgeField();
	public abstract void setAgeField(int age);
	public abstract Collection getDocsField();
	public abstract void setDocsField(Collection docs);
	
	
	// getters and setters exposed in the component interface
	public String getName() {
		return this.getNameField();
	}

	public void setName(String name) {
		this.setNameField(name);
	}

	public int getAge() {
		return this.getAgeField();
	}

	public void setAge(int age) {
		this.setAgeField(age);
	}

	/**
	 * In the Entity Bean, the context is more important than in a Session Bean.
	 * This method is called after the constructor, only once in the entity bean's
	 * lifecycle.
	 * 
	 * We CAN only:
	 * - get a reference to home
	 * - use JNDI environment
	 * 
	 * We CANNOT:
	 * - get a reference to EJB object
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - get a transaction and call methods on it (BMT)
	 * - use another bean's methods
	 * - use a resource manager (like a database) (we can ask a connection but
	 *   not make a JDBC call on it)
	 * 
	 * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
	 */
	public void setEntityContext(EntityContext ctx) {
		System.out.println("setEntityContext");
		//this.context = ctx;
	}

	/**
	 * This method is called when the Container wants to reduce the numbers of
	 * the entity beans in the pool.
	 * 
	 * @see javax.ejb.EntityBean#unsetEntityContext()
	 */
	public void unsetEntityContext() {
		System.out.println("unsetEntityContext");
		//this.context = null;
		// release any other resouce
	}

	/**
	 * When the client wants to insert a new entity on database/persistence store he
	 * calls the create() method, the Container runs this method before insert the new
	 * entity in the persistent store/database.
	 * 
	 * The entity bean object is not created, actually the Container pull one from
	 * the pool and runs the ejbCreate(...) passing the new attributes.
	 * 
	 * Here we should our initialization code to prepare the bean to be inserted, in
	 * other words, we have too se the values for the persistent fields and create
	 * a PK. With CMP we're still responsible for the primary key.
	 * 
	 * The return must be the same type of the primary key (the type declared in DD).
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use JNDI environment
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 *
	 * We CANNOT:
	 * - get a reference to the EJB object
	 * - get the primary key
	 * - get a transaction reference and call methods on it (BMT)
	 * 
	 */
	public String ejbCreate(String name, int age) throws CreateException {
		System.out.println("ejbCreate");
		setName(name);
		setAge(age);
		setId(getNewPK());
		return null;
	}

	/**
	 * This method is called by the Container after the entity bean was inserted in the
	 * database/persistence store.
	 * This method should finish the initialization of the entity, here the method can
	 * get a reference to EJB object or container-managed relationships (CMR).
	 * The return type must be void.
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - get the primary key
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use JNDI environment
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 *
	 * We CANNOT:
	 * - get a transaction reference and call methods on it (BMT)
	 */
	public void ejbPostCreate(String name, int age) {
		System.out.println("ejbPostCreate");
		// get access to EJB object of other bean of the relationship
	}

	private String getNewPK() {
		return String.valueOf((int)(Math.random() * 1000));
	}

	/**
	 * This method is called by the Container when the entity bean is about to return
	 * to the pool, here we can release any heavy resource.
	 * Usually we will leave it empty.
	 * In the moment that it's running, the bean is not in a "meaningful transaction context"
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - get the primary key
	 * - use JNDI environment
	 *
	 * We CANNOT:
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - get a transaction reference and call methods on it (BMT)
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 * 
	 * @see javax.ejb.EntityBean#ejbActivate()
	 */
	public void ejbPassivate() {
		System.out.println("ejbPassivate");
	}
	
	/**
	 * This method is called when the bean is pulled out from the pool to service a client's
	 * business method call. It runs before the business method, thus we have a chance to
	 * prepare any resouces we will use.
	 * Usually we will leave it empty.
	 * In the moment that it's running, the bean is not in a "meaningful transaction context"
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - get the primary key
	 * - use JNDI environment
	 *
	 * We CANNOT:
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - get a transaction reference and call methods on it (BMT)
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 * 
	 * @see javax.ejb.EntityBean#ejbActivate()
	 */
	public void ejbActivate() {
		System.out.println("ejbActivate");
	}
	
	/**
	 * This method is called when the bean has been refreshed with data from the
	 * database/persistence store.
	 * In the moment that it's running, the bean is in a "meaningful transaction context"
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - get the primary key
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use JNDI environment
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 *
	 * We CANNOT:
	 * - get a transaction reference and call methods on it (BMT)
	 * 
	 * @see javax.ejb.EntityBean#ejbLoad()
	 */
	public void ejbLoad() {
		System.out.println("ejbLoad");
	}

	/**
	 * This method is called when the Container is about to update the database/persistence
	 * store to reflect the state of the bean.
	 * In the moment that it's running, the bean is in a "meaningful transaction context"
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get a reference to the EJB object
	 * - get the primary key
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use JNDI environment
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 *
	 * We CANNOT:
	 * - get a transaction reference and call methods on it (BMT)
	 * 
	 * 
	 * @see javax.ejb.EntityBean#ejbStore()
	 */
	public void ejbStore() {
		System.out.println("ejbStore");
	}


	/**
	 * When the client calls the remove(), the Container calls this method and
	 * then remove the entity from persistent store/database. Then return the entity
	 * to the pool.
	 * Anyone who has a reference to its EJB object will get a exception when call
	 * a business method.
	 * In the moment that it's running, the bean is not in a "meaningful transaction context"
	 * 
	 * @see javax.ejb.EntityBean#ejbRemove()
	 */
	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	/**
	 * Must have a ejbFindByPrimaryKey to match with the method from the home interface.
	 * We do not have to implement the ejbFindByRangeOfAge because the Container will.
	 * 
	 * The finder methods run in a bean which is in the pool, is not in an specific bean.
	 * 
	public String ejbFindByPrimaryKey(String pk) {
		return this.id;
	}
	public Collection ejbFindByRangeOfAge(int ageStart, int ageEnd) {
		return null;
	}
	*/

	/**
	 * This is a home business method. Must match that declared in the home interface with
	 * the prefix ejbHome.
	 * This method runs in a bean which is in the pool, is not in an specific bean.
	 * 
	 * We CAN:
	 * - get a reference to the home object
	 * - get security information about the client
	 * - force a transaction to rollback (CMT)
	 * - find out if the transaction has already been set to rollback (CMT)
	 * - use JNDI environment
	 * - use another bean's methods
	 * - use a resource manager (like a database)
	 *
	 * We CANNOT:
	 * - get a reference to the EJB object
	 * - get the primary key
	 * - get a transaction reference and call methods on it (BMT)
	 */
	public void ejbHomeMyHomeBusinessMethod() {
		System.out.println("ejbHomeMyHomeBusinessMethod");
	}
	
	
	/**
	 * Select method, the Container will use infomartion from the query tag in the DD
	 * to build the select statement and return the beans.
	 */
	public abstract EjbEntityComponent ejbSelectGellAllByDoc(String doc, String value);
}
