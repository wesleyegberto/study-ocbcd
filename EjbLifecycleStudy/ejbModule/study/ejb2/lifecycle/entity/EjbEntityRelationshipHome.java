package study.ejb2.lifecycle.entity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EjbEntityRelationshipHome extends EJBLocalHome {
	public EjbEntityRelationshipComponent create(String desc, String value) throws CreateException;

	public EjbEntityRelationshipComponent findByPrimaryKey(String pk) throws FinderException;

	public Collection findByPerson(String pkPerson) throws FinderException;
}
