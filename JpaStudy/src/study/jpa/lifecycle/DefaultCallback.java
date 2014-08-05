package study.jpa.lifecycle;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * > We can have only one of each type by class;
 * > We can set callback with annotation in methods or a class with only callback methods
 *   and in the entity we can reuse it with annotation @EntityListeners;
 * > We can have a class with callback which can be used with any type of entity, this is
 *   called default callbacks, all entity managed by a persist unit will use this methods. To
 *   not use we can declare @ExcludeDefaultListeners;
 * > Life-Cycle Callback Annotations:
 * @PrePersist: Marks a method to be invoked before EntityManager.persist() is executed;
 * @PostPersist: Marks a method to be invoked after the entity has been persisted. If the
 *   entity autogenerates its primary key (with @GeneratedValue), the value is available in the method;
 * @PreUpdate: Marks a method to be invoked before a database update operation is performed
 *   (calling the entity setters or the EntityManager.merge() method);
 * @PostUpdate: Marks a method to be invoked after a database update operation is performed;
 * @PreRemove: Marks a method to be invoked before EntityManager.remove() is executed;
 * @PostRemove: Marks a method to be invoked after the entity has been removed;
 * @PostLoad: Marks a method to be invoked after an entity is loaded (with a JPQL query or
 *   an EntityManager.find()) or refreshed from the underlying database. There is no PreLoad
 *   annotation, as it doesn’t make sense to preload data on an entity that is not built yet.
 *
 */
public class DefaultCallback {
	@PrePersist
	public void prePersist(Object obj) {
		System.out.println("PrePersist: " + obj);
	}
	
	@PostPersist
	public void postPersist(Object obj) {
		System.out.println("PostPersist: " + obj);
	}
	
	@PostLoad
	public void postLoad(Object obj) {
		System.out.println("PostLoad: " + obj);
	}
	
	@PreUpdate
	public void preUpdate(Object obj) {
		System.out.println("PreUpdate: " + obj);
	}
	
	@PostUpdate
	public void postUpdate(Object obj) {
		System.out.println("PostUpdate: " + obj);
	}

	@PreRemove
	public void preRemove(Object obj) {
		System.out.println("PreRemove: " + obj);
	}
	
	@PostRemove
	public void postRemove(Object obj) {
		System.out.println("PostRemove: " + obj);
	}
}
