package study.jpa.basic_relationship;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CUSTOMER") // by default the attributes are mapped to primary
@Access(AccessType.FIELD) // define that the JPA will use the fields to read the Annotations
// @SecondaryTable(name = "CUSTOMER_PHONES") // use a secondary table which uses the Customer ID to join the tables (1:N) (will have its own PK)
public class Customer {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(length = 200, nullable = false)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	// map the attributes to secondary table (it won't have its own PK, will use from Customer)
	@ElementCollection(fetch = FetchType.LAZY) // define that the collection contains Java type and will be loaded when required
	@CollectionTable(name = "CUSTOMER_PHONES") // customize details of the collection table
	@Column(name = "number")
	private List<String> phones = new ArrayList<String>();

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

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public void addPhone(String phone) {
		this.phones.add(phone);
	}
}
