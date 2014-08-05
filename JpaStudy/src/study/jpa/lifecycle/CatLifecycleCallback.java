package study.jpa.lifecycle;

import javax.persistence.PostRemove;
import javax.persistence.PreRemove;

public class CatLifecycleCallback {

	@PreRemove
	public void preRemove() {
		System.out.println("A cat is about to be abandoned: " + this);
	}
	
	@PostRemove
	public void postRemove() {
		System.out.println("A cat was abandoned: " + this);
	}
}
