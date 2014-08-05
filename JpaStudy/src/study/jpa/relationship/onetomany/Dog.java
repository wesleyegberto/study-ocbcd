package study.jpa.relationship.onetomany;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOG")
public class Dog {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String race;

	public Dog(String name, String race) {
		super();
		this.name = name;
		this.race = race;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

}
