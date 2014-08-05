package study.ejb2.lifecycle.entity;

import javax.ejb.EJBLocalObject;

public interface EjbEntityRelationshipComponent extends EJBLocalObject {

	public String getDescription();
	public void setDescription(String desc);
	public String getValue();
	public void setValue(String value);
	
}
