package study.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * We have to annotate the class with @XmlRootElement to allows it to be
 * marshalled/unmarshalled, without the annotation the JAXB will throw an
 * exception. It also define the Person is the root element of the XML.
 */
@XmlRootElement(name = "Person") // setting the name fixe the error of unexpected element
@Entity
@Table(name = "PERSON")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
	private static final long serialVersionUID = -8795837709119204104L;

	@XmlElement(name = "ID")
	@Id
	@GeneratedValue
	private int id;

	@XmlElement(required = true)
	@Column(nullable = false)
	private String name;

	@XmlElement(required = true)
	@Column(nullable = false)
	private int age;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
