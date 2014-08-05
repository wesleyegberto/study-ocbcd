package study.jpa.relationship.inheritance;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

// allows the JPA map the fields to the subclasses' tables (by default is not used)
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED) // will add the fields into the subclasses' tables
public class Item {
	private String name;
	private String description;
	private String picture;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
