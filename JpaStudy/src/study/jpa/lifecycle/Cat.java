package study.jpa.lifecycle;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ExcludeDefaultListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;

@ExcludeDefaultListeners // does not use the default listeners
@EntityListeners({CatLifecycleCallback.class}) // reuse the callback defined in another class
@Entity
public class Cat {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String toy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToy() {
		return toy;
	}

	public void setToy(String toy) {
		this.toy = toy;
	}
	
	public String toString() {
		return id + " - " + name;
	}

	/* method that will be called when Loaded, Merged, Persisted, Refreshed or Removed */
	@PostLoad
	public void postLoad() {
		System.out.println("A cat was loaded: " + this);
	}
	
}
