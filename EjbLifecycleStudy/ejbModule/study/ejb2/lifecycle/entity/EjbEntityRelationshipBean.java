package study.ejb2.lifecycle.entity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

public abstract class EjbEntityRelationshipBean implements EntityBean {
	private static final long serialVersionUID = 1L;

	// getters and setters to container
	public abstract String getId();
	public abstract void setId(String id);
	public abstract EjbEntityComponent getPerson();
	public abstract void setPerson(EjbEntityComponent person);
	public abstract String getDescriptionField();
	public abstract void setDescriptionField(String desc);
	public abstract String getValueField();
	public abstract void setValueField(String value);

	
	// getters and setters from component interface
	public String getDescription() {
		return this.getDescriptionField();
	}

	public void setDescription(String desc) {
		this.setDescriptionField(desc);
	}

	public String getValue() {
		return this.getValueField();
	}

	public void setValue(String value) {
		this.setValueField(value);
	}
	
	public String ejbCreate(String desc, String value) throws CreateException {
		System.out.println("ejbCreate");
		return null;
	}

	public void ejbPostCreate(String desc, String value) throws CreateException {
		System.out.println("ejbPostCreate");
	}	

	/**
	 * Select method
	 */
	public abstract Collection ejbSelectGetAllDocsByPerson(String personId) throws FinderException;
	
	
	// other lifecycle methods
	
	public void ejbPassivate() {
		System.out.println("ejbPassivate");
	}
	
	public void ejbActivate() {
		System.out.println("ejbActivate");
	}

	public void ejbLoad() {
		System.out.println("ejbLoad");
	}

	public void ejbStore() {
		System.out.println("ejbStore");
	}

	public void ejbRemove() {
		System.out.println("ejbRemove");
	}

	public void setEntityContext(EntityContext ctx) {
		System.out.println("setEntityContext");
	}

	public void unsetEntityContext() {
		System.out.println("unsetEntityContext");
	}

	
}
