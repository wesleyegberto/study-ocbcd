package study.jpa.relationship.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON_ONETOONE")
public class PersonOneToOne {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	// by default when we have reference to another entity, the default fetch is EAGER
	// CascadeType.ALL allows to insert/update/delete the entity Personality in cascade (doesn't need to insert personality first to get the ID)
	// orphanRemoval allows the JPA delete the personality whose is not referencered by no one
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	// the default name would be PERSONALITY_ID and nullable
	@JoinColumn(name = "PERSONALITY_FK", nullable = false)
	private Personality personality;

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

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

}
