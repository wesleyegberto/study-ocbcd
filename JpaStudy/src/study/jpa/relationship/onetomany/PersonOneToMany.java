package study.jpa.relationship.onetomany;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON_ONETOMANY")
public class PersonOneToMany {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	// by default when we have a list of reference to another entity and the fetch is LAZY
	// CascadeType.ALL allows to insert/update/delete the entity Cat in cascade (doesn't need to insert each one each time)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// by default a join table is used (like in ManyToMany), but is better to have a join collumn
	@JoinColumn(name = "PERSON_OWNER", nullable = false)
	private List<Dog> dogs = new LinkedList<Dog>();

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

	public List<Dog> getDogs() {
		return dogs;
	}

	public void addDog(Dog dog) {
		this.dogs.add(dog);
	}

}
