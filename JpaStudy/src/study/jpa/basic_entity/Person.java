package study.jpa.basic_entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * @Entity: informs the persistence provider that this class is an entity and that it should manage it.
 * The default table name is the same of class in uppercase.
 * @NamedQuery: static query defined at compile time, allow to make a specific query
 */
@Entity
@Table(name = "PERSON")
@NamedQueries({
	@NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p"),
	@NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.ID=?1"),
	@NamedQuery(name = "Person.findByNameAndAge", query = "SELECT p FROM Person p WHERE p.NAME = ?1 p.AGE = :age")
})
@NamedNativeQuery(name = "Person.getAllNative", query = "select * from PERSON")
public class Person {
	@Id // define the primary key (and by default define that the JPA will be AccessType.FIELD - use the fields to read the Annotations)
	@GeneratedValue // define that the database generates a unique key (if omitted the application must generate the key)
	private int id;
	
	@Column(length = 100) // is not require, just when needs change the default
	private String name;
	private short age;
	
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

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}
	
	public void setAge(int age) {
		setAge((short) age);
	}

	@PrePersist
	public void prePersist() {
		System.out.println("Inserting: " + name + " " + age);
	}
	
	@PostPersist
	public void postPersist() {
		System.out.println("Inserted: " + id + " " + name + " " + age);
	}
}
