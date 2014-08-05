package study.jpa.concurrency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	// Version is used to control the version of entity, each insert and update will
	// increment the version. Also is used in the pessimistic lock mechanism.
	@Version
	private long version;

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

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
